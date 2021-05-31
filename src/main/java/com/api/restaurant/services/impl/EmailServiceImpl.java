package com.api.restaurant.services.impl;

import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.api.restaurant.dtos.EmailTemplateDTO;
import com.api.restaurant.entities.Notification;
import com.api.restaurant.excepciones.BookingException;
import com.api.restaurant.excepciones.InternalServerErrorException;
import com.api.restaurant.excepciones.NotFountException;
import com.api.restaurant.repositories.NotificationRepository;
import com.api.restaurant.services.EmailService;

@Service
public class EmailServiceImpl implements EmailService {

	@Autowired
	private JavaMailSender javaMailSender;
	
	@Autowired
	private NotificationRepository notificationRepository;
	
	@Override
	public String processSendEmail(String receiver, String templateType, String currentName)
			throws BookingException {
		EmailTemplateDTO emailTemplateDTO = findTemplateAndReplace(currentName, templateType);
		this.sendEmail(receiver, emailTemplateDTO.getSubject(), emailTemplateDTO.getTemplate());
		return "EMAIL_SENT";
	}
	
	private void sendEmail(final String receiver, final String subject,final String template) throws BookingException {
		final MimeMessage message = javaMailSender.createMimeMessage();
		
		final MimeMessageHelper content;
		
		try {
			content = new MimeMessageHelper(message, true);
			content.setSubject(subject);
			content.setTo(receiver);
			content.setText(template, true);
		}catch(Exception e) {
			throw new InternalServerErrorException("INTERNAL_SERVER_ERROR", "INTERNAL_SERVER_ERROR");
		}
		
		javaMailSender.send(message);
	}
	
	private EmailTemplateDTO findTemplateAndReplace(final String currentName, final String templateType) throws BookingException{
		
		final Notification notification = notificationRepository.findByTemplateType(templateType)
				.orElseThrow(() -> new NotFountException("TEMPLATE-NOT-FOUND", "TEMPLATE-NOT-FOUND"));
		
		EmailTemplateDTO emailTemplateDTO = new EmailTemplateDTO();
		emailTemplateDTO.setSubject(notification.getTemplateType());
		emailTemplateDTO.setTemplate(notification.getTemplate().replaceAll("\\{"+"name"+"\\}", currentName));
		
		return emailTemplateDTO;
	}

}
