package com.dliberty.liberty.weixin.vo;


import java.util.Map;

/**
 * 微信模板消息vo
 * @author LG
 *
 */
public class TemplateMessageVo {

	private String touser;
	
	private String template_id;
	
	private String page;
	
	private String form_id;
	
	private Map<String,Map<String,String>> data;

	public String getTouser() {
		return touser;
	}

	public void setTouser(String touser) {
		this.touser = touser;
	}

	public String getTemplate_id() {
		return template_id;
	}

	public void setTemplate_id(String template_id) {
		this.template_id = template_id;
	}

	public String getPage() {
		return page;
	}

	public void setPage(String page) {
		this.page = page;
	}

	public String getForm_id() {
		return form_id;
	}

	public void setForm_id(String form_id) {
		this.form_id = form_id;
	}

	public Map<String, Map<String, String>> getData() {
		return data;
	}

	public void setData(Map<String, Map<String, String>> data) {
		this.data = data;
	}

	
}
