package com.fechin;

import common.utils.MailUtil;
import org.springframework.stereotype.Component;

import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.MessageListener;

@Component
public class EmailJmsService implements MessageListener {

    @Override
    public void onMessage(Message message) {
        MapMessage activeMQMessage = (MapMessage) message;
        try {
            String to = activeMQMessage.getStringProperty("to");
            String subject = activeMQMessage.getStringProperty("subject");
            String content = activeMQMessage.getStringProperty("content");
            MailUtil.sendMsg(to, subject, content);
            System.out.println("邮件发送成功");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
