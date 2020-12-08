package org.wzp.oauth2.email;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.wzp.oauth2.vo.EmailMsgLogVO;

import javax.annotation.Resource;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.util.Date;

/**
 * @Author: zp.wei
 * @DATE: 2020/11/19 11:40
 */
@Component
@Slf4j
public class SendEmailUtil {
    @Resource
    private JavaMailSenderImpl javaMailSender;

    @Value("${spring.mail.username}")
    private String sender;


    /**
     * 普通邮件
     *
     * @param emailMsgLogVO
     */
    public void sendEmail(EmailMsgLogVO emailMsgLogVO) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(sender);
        message.setTo(emailMsgLogVO.getEmail());
        message.setSentDate(new Date());
        message.setSubject(emailMsgLogVO.getTitle());
        message.setText(emailMsgLogVO.getContent());
        javaMailSender.send(message);
        log.info("已发送到{}邮箱", emailMsgLogVO.getEmail());
    }


    /**
     * 发送带附件的邮件
     *
     * @param emailMsgLogVO
     */
    public void sendAccessoryEmail(EmailMsgLogVO emailMsgLogVO) throws MessagingException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
        mimeMessageHelper.setFrom(sender);
        mimeMessageHelper.setTo(emailMsgLogVO.getEmail());
        mimeMessageHelper.setSentDate(new Date());
        mimeMessageHelper.setSubject(emailMsgLogVO.getTitle());
        mimeMessageHelper.setText(emailMsgLogVO.getContent());
        File file = new File(emailMsgLogVO.getFilePath());
        if (!file.exists()) {
            log.error("文件不存在");
            return;
        }
        mimeMessageHelper.addAttachment(file.getName(), file);
        javaMailSender.send(mimeMessage);
        log.info("已发送到{}邮箱", emailMsgLogVO.getEmail());
    }


}
