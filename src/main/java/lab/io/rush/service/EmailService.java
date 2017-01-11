package lab.io.rush.service;

import lab.io.rush.entity.Order;

/**
 * Created by liyang on 17/1/4.
 */
public interface EmailService {

    void sendEmail(String emailTo, String title, String content);

    String createEmailContent(Order order);

}
