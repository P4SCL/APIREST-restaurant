package com.api.restaurant.dtos;

public class EmailTemplateDTO {
	private Long id;
	private String template;
	private String subject;
	private String templateType;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTemplate() {
		return template;
	}

	public void setTemplate(String template) {
		this.template = template;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getTemplateType() {
		return templateType;
	}

	public void setTemplateCode(String templateType) {
		this.templateType = templateType;
	}

}
