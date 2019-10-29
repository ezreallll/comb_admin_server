package com.shp.comb.service;

import com.shp.comb.modle.common.EmailDto;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.exception.VelocityException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by shp on 18/1/25.
 */
@Service
public class MailSenderService {


    private static final Logger logger = LoggerFactory.getLogger(MailSenderService.class);

    @Resource(name="mailSenderImpl")
    private JavaMailSender javaMailSender;

    @Resource(name = "velocityEngine")
    private VelocityEngine velocityEngine;	//模板解析


    /**
     * @param
     * @return
     * @throws MessagingException
     */
    public boolean send(String content,String title,String toEmail) throws MessagingException {
        EmailDto emailDto=new EmailDto();
        emailDto.setFrom("timerrrrr@163.com");
        //模板数据
        Map data=new HashMap();
        data.put("content",content);
        emailDto.setData(data);
        //设置模板记录
        String commonTemplate=null;
        try {
            commonTemplate = StringUtils.join(IOUtils.readLines(MailSenderService.class.getClassLoader().getResourceAsStream("common-email-template.html")), " ");
        } catch (IOException e) {
            e.printStackTrace();
        }
        emailDto.setTemplate(commonTemplate);
        emailDto.setToEmails(new String[]{toEmail});
        //设置主题
        emailDto.setSubject(title);

        MimeMessage msg = createMimeMessage(emailDto);
        if(msg == null){
            return false;
        }

        this.sendMail(msg, emailDto);

        return true;
    }

    private void sendMail(MimeMessage msg, EmailDto emailDto){
        javaMailSender.send(msg);

    }



    /**
     * 根据 emailDto 创建 MimeMessage
     */
    private MimeMessage createMimeMessage(EmailDto emailDto) throws MessagingException {
        if (!checkEmailDto(emailDto)) {
            return null;
        }
        String text = getMessage(emailDto);
        if(text == null){
            logger.warn("@@@ warn mail text is null (Thread name="
                    + Thread.currentThread().getName() + ") @@@ " +  emailDto.getSubject());
            return null;
        }
        MimeMessage msg = javaMailSender.createMimeMessage();
        MimeMessageHelper messageHelper = new MimeMessageHelper(msg, true, "UTF-8");
        //messageHelper.setFrom(emailDto.getFrom());
        try {
            messageHelper.setFrom(emailDto.getFrom(), emailDto.getFromName());
        } catch (UnsupportedEncodingException e) {
            logger.error("ex",e);

        }
        messageHelper.setSubject(emailDto.getSubject());
        messageHelper.setTo(emailDto.getToEmails());
        messageHelper.setText(text, true); // html: true

        return msg;
    }

    /**
     * 模板解析
     * @param emailDto
     * @return
     */
    private String getMessage(EmailDto emailDto) {
        StringWriter writer = null;
        try {

            writer = new StringWriter();
            VelocityContext context = new VelocityContext(emailDto.getData());

            velocityEngine.evaluate(context, writer, "", emailDto.getTemplate());

            return writer.toString();
        } catch (VelocityException e) {
            logger.error(" VelocityException : " + emailDto.getSubject() + "\n" + e);
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    logger.error("###StringWriter close error ... ");
                }
            }
        }
        return null;
    }

    /**
     * check 邮件
     */
    private boolean checkEmailDto(EmailDto emailDto){
        if (emailDto == null) {
            logger.warn("@@@ warn emailDto is null (Thread name="
                    + Thread.currentThread().getName() + ") ");
            return false;
        }
        if (emailDto.getSubject() == null) {
            logger.warn("@@@ warn emailDto.getSubject() is null (Thread name="
                    + Thread.currentThread().getName() + ") ");
            return false;
        }
        if (emailDto.getToEmails() == null) {
            logger.warn("@@@ warn emailDto.getToEmails() is null (Thread name="
                    + Thread.currentThread().getName() + ") ");
            return false;
        }
        if (emailDto.getTemplate() == null) {
            logger.warn("@@@ warn emailDto.getTemplate() is null (Thread name="
                    + Thread.currentThread().getName() + ") ");
            return false;
        }
        return true;
    }

}
