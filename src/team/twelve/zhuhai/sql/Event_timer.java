package team.twelve.zhuhai.sql;

import java.util.Timer;
import java.util.TimerTask;

import team.twelve.zhuhai.sql.global.Global_enum;

/**
 * @author zhuyst
 * ���������۱䶯�¼��ļ�ʱ��
 */

public class Event_timer {
	private String event_message;  //�¼���Ϣ
	private Main_controll main_controll;  //���ݿ��������� 
	private int active_number;  //Ŀǰ������¼���ID
	private final int interval = 600000;  //�����¼���ʱ����
	private Timer timer = new Timer();
	private TimerTask timerTask = new TimerTask() {
		
		@Override
		public void run() {
			int event_number = main_controll.getGlobal_controll().get_data(Global_enum.Event_item_number);
			int number = (int)(1 + Math.random() * event_number);
			
			if(active_number != 0){
				main_controll.getEvent_item_controll().passtive(active_number);
			}
			main_controll.getEvent_item_controll().active(number);
			active_number = number;
			event_message = main_controll.getEvent_item_controll().get_Event_item_data(active_number).getMessage();
			main_controll.getGlobal_controll().update_data(Global_enum.Event_item_active_ID,String.valueOf(active_number));
		}
		
	};
	
	public Event_timer(Main_controll main_controll){
		this.main_controll = main_controll;
		active_number = main_controll.getGlobal_controll().get_data(Global_enum.Event_item_active_ID);
	}
	
	//������ʱ��
	public void start(){
		timer.schedule(timerTask, 0,interval);
	}
	
	public String getEvent_message() {
		return event_message;
	}
}
