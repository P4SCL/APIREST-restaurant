package com.api.restaurant.controllers.responses;

import java.io.Serializable;

public class BookingResponse<T> implements Serializable {

	private String status;
	private String code;
	private String message;
	private T data;

	public BookingResponse(String status, String code, String message) {
		this.status = status;
		this.code = code;
		this.message = message;
	}

	public BookingResponse(String status, String code, String message, T data) {
		this.status = status;
		this.code = code;
		this.message = message;
		this.data = data;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	private static final long serialVersionUID = -8904856033226891323L;
}
