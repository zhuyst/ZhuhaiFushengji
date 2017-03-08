package zhuhai_ssm;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.alibaba.fastjson.JSON;

import indi.zhuhai.service.PlayerItemService;

@RunWith(SpringJUnit4ClassRunner.class)		//表示继承了SpringJUnit4ClassRunner类
@ContextConfiguration(locations = {"classpath*:spring-mybatis.xml"})

public class TestMyBatis {
	private static Logger logger = Logger.getLogger(TestMyBatis.class);
	@Resource
	private PlayerItemService playerItemService;

	@Test
	public void test1() {
		for(int i=0;i<13;i++){
			logger.info(JSON.toJSONString(playerItemService.getItemNumber(6, i+1)));
		}
	}
}