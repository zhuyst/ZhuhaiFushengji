package team.twelve.zhuhai.server;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import team.twelve.zhuhai.sql.Respond_game;

/**
 * 服务器的主类
 * @author barry
 */
public class NServer {
	private Selector selector = null;
    private static final int PORT = 8080;  //端口号
    private Respond_game respond_game;  //对游戏的响应内容
    
    public NServer() {}
    
    public void start() throws IOException {  
    	selector = Selector.open();
    	ServerSocketChannel server = ServerSocketChannel.open();
    	server.socket().setReuseAddress(true);
    	server.bind(new InetSocketAddress(PORT));
    	server.configureBlocking(false); 
        server.register(selector, SelectionKey.OP_ACCEPT);
        respond_game = new Respond_game();
        
        //handle实例的map集合，每个key对应一个handle实例
        Map<String , Handle> handlesMap = new HashMap<String, Handle>();
        while(selector.select() > 0){
        	Iterator<SelectionKey> selectedKeys = selector.selectedKeys() .iterator();  
            while (selectedKeys.hasNext()) {  
              SelectionKey key = selectedKeys.next();              
              try {
    			  //连接就绪
    			  if(key.isAcceptable()){
    				  //System.out.println("Accept\t" + Thread.currentThread().getName() + "正在工作......");
    			      SocketChannel clientChannel = ((ServerSocketChannel) key.channel()).accept();  
    			      clientChannel.configureBlocking(false);  
    			      clientChannel.register(key.selector(), SelectionKey.OP_READ, ByteBuffer.allocate(1024));
    			      //读就绪
    		      }else if (key.isReadable()&&key.isValid()) {
    		    	  Handle handle = new Handle(respond_game);
    				  handlesMap.put(key.toString(), handle);
    				  handle.handleRead(key);
    				  //写就绪
    			  }else if (key.isWritable()&&key.isValid()) {
    				  if(handlesMap.get(key.toString()) != null){
    					  handlesMap.get(key.toString()).handleWrite(key);
    					  handlesMap.remove(key.toString());
    				  }
    		      }
    			  selectedKeys.remove();
    		  }catch (IOException e) {	
    			  selectedKeys.remove();
    			  continue;
    		  }
    		}
         }
     }
}
