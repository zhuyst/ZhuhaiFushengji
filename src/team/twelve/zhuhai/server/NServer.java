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
 * ������������
 * @author barry
 */
public class NServer {
	private Selector selector = null;
    private static final int PORT = 8080;  //�˿ں�
    private Respond_game respond_game;  //����Ϸ����Ӧ����
    
    public NServer() {}
    
    public void start() throws IOException {  
    	selector = Selector.open();
    	ServerSocketChannel server = ServerSocketChannel.open();
    	server.socket().setReuseAddress(true);
    	server.bind(new InetSocketAddress(PORT));
    	server.configureBlocking(false); 
        server.register(selector, SelectionKey.OP_ACCEPT);
        respond_game = new Respond_game();
        
        //handleʵ����map���ϣ�ÿ��key��Ӧһ��handleʵ��
        Map<String , Handle> handlesMap = new HashMap<String, Handle>();
        while(selector.select() > 0){
        	Iterator<SelectionKey> selectedKeys = selector.selectedKeys() .iterator();  
            while (selectedKeys.hasNext()) {  
              SelectionKey key = selectedKeys.next();              
              try {
    			  //���Ӿ���
    			  if(key.isAcceptable()){
    				  //System.out.println("Accept\t" + Thread.currentThread().getName() + "���ڹ���......");
    			      SocketChannel clientChannel = ((ServerSocketChannel) key.channel()).accept();  
    			      clientChannel.configureBlocking(false);  
    			      clientChannel.register(key.selector(), SelectionKey.OP_READ, ByteBuffer.allocate(1024));
    			      //������
    		      }else if (key.isReadable()&&key.isValid()) {
    		    	  Handle handle = new Handle(respond_game);
    				  handlesMap.put(key.toString(), handle);
    				  handle.handleRead(key);
    				  //д����
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
