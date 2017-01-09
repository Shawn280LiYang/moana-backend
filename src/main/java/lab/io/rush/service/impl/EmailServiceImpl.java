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

    public void sendEmail(String emailTo, String title, String content) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(emailFrom);
        message.setTo(emailTo);
        message.setSubject(title);
        message.setText(content);

        javaMailSender.send(message);
    }

    public String createEmailContent(Order order){
        String content = "Hello %s,\n\n您于 %s 成功抢购 <%s> 电影票1张.";
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String createtime =  df.format(order.getCreatetime());
        content=String.format(content, userDao.find(order.getUserid()).getUsername(), createtime, movieService.find(order.getMovieid()).getName());

        return content;
    }
}
