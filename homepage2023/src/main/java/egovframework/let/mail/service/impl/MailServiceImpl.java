package egovframework.let.mail.service.impl;

import java.util.Properties;

import javax.annotation.Resource;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.stereotype.Service;

import egovframework.let.mail.service.MailService;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import egovframework.rte.fdl.property.EgovPropertyService;

@Service("mailService")
public class MailServiceImpl extends EgovAbstractServiceImpl implements MailService{

	@Resource(name ="propertiesService")
	protected EgovPropertyService propertyService;
	//EgovPropertyService는 스프링 프레임워크에서 제공하는 속성(Property) 관련 기능을 사용할 수 있는 서비스
	//이를 통해 프로퍼티 파일에서 설정된 값을 읽어와 사용.
	
	final String encoding = "UTF-8";
	final String port ="465";
	final String smtpHost ="smtp.gmail.com";
	
	//메일session값 셋팅(javax.mail.Session)
	@Override
	public Session mailSetting(Properties props) throws Exception {
		Session session = null;
		
		try {
			props.put("mail.transport.protocol", "smtp");
			props.put("mail.smtp.host", smtpHost);//*
			props.put("mail.smtp.port", port);//*
			props.put("mail.smtp.auth", true);//*
			props.put("mail.smtp.ssl.enable", true);//*
			props.put("mail.smtp.ssl.trust", smtpHost);//*
			props.put("mail.smtp.starttls.required", true);
			props.put("mail.smtp.ssl.protocols", "TLSv1.2");
			props.put("mail.smtp.quit-wait", "false");
			props.put("mail.smtp.socketFactory.port", port);
			props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
			props.put("mail.smtp.socketFactory.fallback", "false");
			
			//javax.mail.Session; import 받을 때 
			session = Session.getInstance(props, new Authenticator() {
			  
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(propertyService.getString("mail.gmailUser"), propertyService.getString("mail.gmailPassword"));
				}
			
			});
		} catch (Exception e) {
               System.out.println("실패");
		}
		
		return session;
	}
   
	//메일보내기
	@Override
	public void sendMail(Session session, String title, String content, String receiver) throws Exception {
        Message msg = new MimeMessage(session); //MimeMessage 클래스는 이메일 메시지를 표현하는 클래스
        
        try {
        	msg.setFrom(new InternetAddress(propertyService.getString("mail.gmailUser"), propertyService.getString("mail.gmailUserName"), encoding));
        	//메일 발신자의 주소(mail.gmailUser)와 이름(gmailUserName)을 설정. propertyService를 사용하여 프로퍼티 파일에서 Gmail 발신자의 이메일과 이름을 읽어옴.
        	msg.addRecipient(Message.RecipientType.TO, new InternetAddress(receiver)); //addRecipient() 메소드는 이메일 메시지에 수신자(Recipient) 주소를 추가하는 역할
         	msg.setSubject(title);
            msg.setContent(content, "text/html; charset=utf-8");
            
            Transport.send(msg);
            
        }catch (Exception e) {
           System.out.println("실패");
        }
		 
	}
}















