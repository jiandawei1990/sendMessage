package com.gmcc.yzcardmessage;
import com.gmcc.yzcardmessage.service.SendCodeMessage;
import com.gmcc.yzcardmessage.service.SendKafkaMessageService;
import com.gmcc.yzcardmessage.service.SendTradeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;


import java.util.concurrent.*;


@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class YzcardmessageApplication implements CommandLineRunner {

	@Autowired
	SendTradeMessage sendTradeMessage;

    @Autowired
	SendCodeMessage sendCodeMessage;

    @Autowired
	SendKafkaMessageService sendKafkaMessageService;

	public static void main(String[] args) {
		SpringApplication.run(YzcardmessageApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
	//	 ExecutorService service =  Executors.newFixedThreadPool (2);
		//自定义的线程池避免阻塞情况
		ExecutorService service =  new ThreadPoolExecutor(2,3,60L, TimeUnit.SECONDS,new ArrayBlockingQueue<>(10));
		//循环发送实体卡消息数据
		service.execute(() -> {
			try {
				sendTradeMessage.sendQrtForAlipayTradeMessage();
			} catch (Exception e) {
				e.printStackTrace();
			}
		});

		//循环发送二维码数据
//		service.execute(new Runnable() {
//			@Override
//			public void run() {
//				try {
//					sendCodeMessage.sendGongAnCodeTradeMessage();
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		});
//
	}

}
