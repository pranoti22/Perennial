package perennialbank.excpt;

import java.util.Map;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class EmailAct implements Actions {
	
	public String performAction(Map<String, String> act) {
		Properties prop=new Properties();
		prop.put("mail.smtp.auth","true");
		prop.put("mail.smtp.starttls.enable","true");
		prop.put("mail.smtp.host","smtp.gmail.com");
		prop.put("mail.smtp.port","587");//465
		
		String Accountemail=act.get("from");
		String recipent=act.get("to");
		String pwd=act.get("pwd");
		Session sec=Session.getInstance(prop, new Authenticator(){
			protected PasswordAuthentication getPasswordAuthentication() {
			return new PasswordAuthentication(Accountemail,pwd);
			
		}
	});
		//Message msg= prepareMsg(sec,Accountemail,recipent);
		
		try {
			
			Message msg=new MimeMessage(sec);
			msg.setFrom(new InternetAddress(Accountemail));
			msg.setRecipient(Message.RecipientType.TO, new InternetAddress(recipent));
			msg.setSubject(act.get("subject"));
			msg.setText("error occured");
			Transport.send(msg);
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "mail sent!";
			
	}
}
	


