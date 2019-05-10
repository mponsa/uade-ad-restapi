package com.distribuidas.mywebserver.message;


public class ReturnMessage {

	public ReturnMessage(int errorCode, String developerMessage, String clientMessage, Object result) {
		super();
		this.errorCode = errorCode;
		DeveloperMessage = developerMessage;
		ClientMessage = clientMessage;
		Result = result;
	}
	public ReturnMessage() {}
	
	int errorCode;
	String DeveloperMessage;
	String ClientMessage;
	Object Result;
	
	
	public int getErrorCode() {
		return errorCode;
	}
	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}

	
	public Object getResult() {
		return Result;
	}
	public void setResult(Object result) {
		Result = result;
	}

	public String getDeveloperMessage() {
		return DeveloperMessage;
	}
	public void setDeveloperMessage(String developerMessage) {
		DeveloperMessage = developerMessage;
	}
	public String getClientMessage() {
		return ClientMessage;
	}
	public void setClientMessage(String clientMessage) {
		ClientMessage = clientMessage;
	}

	
	
	
}
