package team.twelve.zhuhai.server;
import java.io.IOException;

/**
 * Main��
 * @author barry
 * ����������
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