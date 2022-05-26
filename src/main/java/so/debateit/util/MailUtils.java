/**
 * The MailUtils class implements the feature for sending Mail for sign up
 * and listing the post
 *
 * @author  Jinyoung So
 * @version 1.0
 * @since   5/15/2022
 */
package so.debateit.util;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import javax.activation.DataSource;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;

public class MailUtils {
    private JavaMailSender mailSender;
    private MimeMessage message;
    private MimeMessageHelper messageHelper;

    public MailUtils(JavaMailSender mailSender) throws Exception {
        this.mailSender = mailSender;
        message = this.mailSender.createMimeMessage();
        messageHelper = new MimeMessageHelper(message, true, "UTF-8");
    }

    public void setSubject(String subject) throws Exception {
        messageHelper.setSubject(subject);
    }

    public void setText(String htmlContent) throws Exception {
        messageHelper.setText(htmlContent, true);
    }

    public void setFrom(String email, String name) throws UnsupportedEncodingException, Exception {
        messageHelper.setFrom(email, name);
    }

    public void setTo(String email) throws Exception {
        messageHelper.setTo(email);
    }

    public void addInline(String contentId, DataSource dataSource) throws Exception {
        messageHelper.addInline(contentId, dataSource);
    }

    public void send() {
        mailSender.send(message);
    }

}