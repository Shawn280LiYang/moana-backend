package lab.io.rush.service.impl;

import lab.io.rush.dao.UserDao;
import lab.io.rush.entity.Order;
import lab.io.rush.service.EmailService;
import lab.io.rush.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;

/**
 * Created by liyang on 16/12/31.
 */
@Service
public class EmailServiceImpl implements EmailService {
    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private UserDao userDao;

    @Autowired
    private MovieService movieService;

    @Value(value = "${spring.mail.username}")
    private String emailFrom;


//    @Autowired
//    ThreadPoolTaskExecutor taskExecutor;

//    public EmailService(){
//        taskExecutor = new ThreadPoolTaskExecutor();
//        // 线程池所使用的缓冲队列
//        taskExecutor.setQueueCapacity(200);
//        // 线程池维护线程的最少数量(核心线程数)
//        taskExecutor.setCorePoolSize(5);
//        // 线程池维护线程的最大数量
//        taskExecutor.setMaxPoolSize(100);
//        // 线程池维护线程所允许的空闲时间
//        taskExecutor.setKeepAliveSeconds(30000);
//        taskExecutor.initialize();
//    }
//
//    public void sendEmail(String emailTo, String title, String content) throws Exception{
//        taskExecutor.execute(() -> {
//            SimpleMailMessage message = prepareMessage(emailTo,title,content);
//            javaMailSender.send(message);
//        });
//    }

    public void sendEmail(String emailTo, String title, String content) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(emailFrom);
        message.setTo(emailTo);
        message.setSubject(title);
        message.setText(content);

        javaMailSender.send(message);
    }

    // TODO 增加:影片将于...放映,欢迎观看.
    public String createEmailContent(Order order){
        String content = "Hello %s,\n\n您于 %s 成功抢购 <%s> 电影票1张.";
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String createtime =  df.format(order.getCreatetime());
        content=String.format(content, userDao.find(order.getUserid()).getUsername(), createtime, movieService.getMovieNoTag(order.getMovieid()).getName());

        return content;
    }
}
