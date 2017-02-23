package team.twelve.zhuhai.server;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.URLDecoder;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;

import team.twelve.zhuhai.sql.Respond_game;

/**
 * @author barry
 * 负责服务器的请求与响应
 */

public class Handle {
	private String receivedString;   //http请求
	private String mode;    //请求方式，GET或POST
	private String uri;     //请求资源的路径
	private String type;    //请求资源的类型
	private String content; //请求正文
	private Respond_game respond_game;  //对游戏的响应内容
	
	Handle() {
		receivedString = null;
		mode = null;
		uri = null;
		type = null;
		content = null;
	}
	
	Handle(Respond_game respond_game){
		this();
		this.respond_game = respond_game;
	}
	
	//读取请求，并提取请求方式 ,资源路径, 文件类型 ,正文
	public void handleRead(SelectionKey key) throws IOException {  
		//System.out.println("Read\t" + Thread.currentThread().getName() + "正在工作......");
        SocketChannel clientChannel = (SocketChannel) key.channel(); 
        
        ByteBuffer buffer = (ByteBuffer) key.attachment();  
        buffer.clear();
        long bytesRead = clientChannel.read(buffer);
        if (bytesRead == -1) {
            clientChannel.close();  
        } else {  
            buffer.flip();
            receivedString = Charset.forName("UTF-8").newDecoder().decode(buffer).toString();
            receivedString = URLDecoder.decode(receivedString,"UTF-8");
            try {
    			mode = receivedString.split(" ",2)[0];
    			uri = receivedString.split(" ",3)[1];
    			
    			String[] typeTemp = uri.split("\\.");
    			type = typeTemp[typeTemp.length - 1];
    			//对于没有后缀的uri，默认当作html类型
    			if(type.equals(uri)){
    				type = "html";
    				//补上.html后缀
    				if(!uri.equals("/")){
    					uri += ".html";
    				}
    			}
    			
    			String[] contentTemp = receivedString.split("\r\n\r\n");
    			if(contentTemp.length - 1 > 0){
    				content = contentTemp[1];
    			}
                key.interestOps(SelectionKey.OP_WRITE); 
			} catch (ArrayIndexOutOfBoundsException e) {
				// TODO: handle exception
			}
            
//            System.out.println(receivedString);
//            System.out.println(uri);
//            System.out.println(type);
//            System.out.println(content);
        }
    }  
		
	//响应GET请求或POST请求
	public void handleWrite(SelectionKey key) throws IOException {  
		//System.out.println("Write\t" + Thread.currentThread().getName() + "正在工作......");
        SocketChannel clientChannel = (SocketChannel) key.channel();  
        
        if(mode.equals("GET")){
        	respondGET(key, clientChannel);
        }else if (mode.equals("POST")) {
        	Boolean ok;
        	if(content != null && content.length() >= 3) ok = true;
        	else ok = false;
        	respondPOST(key, clientChannel,ok);
		}
        clientChannel.close();
    }  
	
	private void respondGET(SelectionKey key, SocketChannel clientChannel) throws IOException{
		String path = "./zhuhai//";  //文件路径
		if(uri.equals("/")){
			path += "index.html";
		}else {
			path += uri.split("/",2)[1];
		}
		if (!new File(path).exists()) {
			return;			
		}
		RandomAccessFile afile = null;
        FileChannel fc = null;
        try{       	      	        	
            afile = new RandomAccessFile(path,"rw");
            fc = afile.getChannel();
            MappedByteBuffer mbb = fc.map(FileChannel.MapMode.PRIVATE, 0, afile.length());
            mbb.clear();
            fc.read(mbb);
            
            mbb.flip();
            setContentType(clientChannel, mbb);
            while (mbb.hasRemaining()) {
				clientChannel.write(mbb);
			}
        }catch(IOException e){
            e.printStackTrace();
        }finally{
            try{
                if(afile!=null){
                    afile.close();
                }
                if(fc!=null){
                    fc.close();
                }
            }catch(IOException e){
                e.printStackTrace();
            };
        }
	}
	
	private void respondPOST(SelectionKey key, SocketChannel clientChannel,Boolean ok) throws IOException{
		String response = "HTTP/1.1 200 OK\r\n";   
        response += "Content-Type: text/plain; charset=utf-8\r\n";
        response += "Access-Control-Allow-Origin:*\r\n\r\n";  //解决ajax跨域问题的头
		if(ok == true) response += respond_game.getRespond(content);
		else response += "0";
		ByteBuffer buffer = ByteBuffer.allocate((int)response.getBytes().length);
        buffer.clear();
        buffer.put(response.getBytes());
        buffer.flip();
        clientChannel.write(buffer);
	}
	
	//响应头
	private void setContentType(SocketChannel clientChannel, MappedByteBuffer mbb) throws IOException{
		String response = "";
		if(type.equals("html")){ 
			response += "HTTP/1.1 200 OK\r\n";   
	        response += "Content-Type: text/html; charset=utf-8\r\n";
	        response += "Content-Length: " + mbb.limit() + "\r\n";
	        response += "\r\n";
		}else if(type.equals("js")){ 
			response += "HTTP/1.1 200 OK\r\n";   
	        response += "Content-Type: application/x-javascript\r\n"; 
	        response += "Content-Length: " + mbb.limit() + "\r\n";
	        response += "\r\n";
		}else if(type.equals("png")){ 
			response += "HTTP/1.1 200 OK\r\n";   
	        response += "Content-Type: image/png\r\n"; 
	        response += "Content-Length: " + mbb.limit() + "\r\n";
	        response += "\r\n";
		}else if(type.equals("ico")){
			response += "HTTP/1.1 200 OK\r\n";   
	        response += "Content-Type: image/x-icon\r\n"; 
	        response += "Content-Length: " + mbb.limit() + "\r\n";
	        response += "\r\n";
		}else if(type.equals("appcache")){
			response += "HTTP/1.1 200 OK\r\n";   
	        response += "Content-Type: text/cache-manifest\r\n"; 
	        response += "Content-Length: " + mbb.limit() + "\r\n";
	        response += "\r\n";
		}else if(type.equals("json")){
			response += "HTTP/1.1 200 OK\r\n";   
	        response += "Content-Type: application/json\r\n"; 
	        response += "Content-Length: " + mbb.limit() + "\r\n";
	        response += "\r\n";
		}
		ByteBuffer buffer = ByteBuffer.allocate(response.length());
        buffer.clear();
        buffer.put(response.getBytes());
        buffer.flip();
        clientChannel.write(buffer);
	}
	
	public String getReceivedString() {
		return receivedString;
	}
	
	public String getUri() {
		return uri;
	}
	
	public String getMode() {
		return mode;
	}
	
	public String getType() {
		return type;
	}
	
	public String getContent() {
		return content;
	}
}
