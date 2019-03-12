package cn.itcast.erp.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

/**
 * @ClassName MailUtil
 * @Description TODD 发送邮件工具类
 * @Auther whz
 * @Date 2019/1/21 12:34
 * @Version 1.0
 **/
@Component
public class MailUtil {
    //Spring 邮件发送类
    @Autowired
    private JavaMailSender sender;
    //发件人
    @Value("m15037511286@163.com")
    private String from;
    /**
     * @Author whz
     * @Description //TODO 发送邮件方法
     * @Date 2019/1/21
     * @Param [to 接收人 , subject 主题, text 正文]
     * @return void
     **/
    public void sendMail(String to,String subject,String text)
            throws MessagingException {

        //创建邮件
        MimeMessage mimeMessage = sender.createMimeMessage();
        //邮件包装工具
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);
        //发件人
        helper.setFrom(from);
        //收件人
        helper.setTo(to);
        //邮件主题
        helper.setSubject(subject);
        //邮件正文
        helper.setText(text);
        //发送邮件
        sender.send(mimeMessage);
    }

}