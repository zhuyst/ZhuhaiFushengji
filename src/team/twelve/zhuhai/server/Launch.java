package team.twelve.zhuhai.server;
import java.io.IOException;

/**
 * Main类
 * @author barry
 * 启动服务器
 */

public class Launch {
	public static void main(String[] args) {
		try {
			new NServer().start();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}