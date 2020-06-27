package kakaopay.task.dto;

import com.google.gson.Gson; 

public class DefaultResponseDataVO {
 
    private String resultCode;
    private String resultMessage;
    
	public String getResultCode() {
		return resultCode;
	}
	
	public void setResultCode(String resultCode) {
		this.resultCode = resultCode;
	}
	
	public String getResultMessage() {
		return resultMessage;
	}
	
	public void setResultMessage(String resultMessage) {
		this.resultMessage = resultMessage;
	}
	
	public String toJson() {
		Gson gson = new Gson();
		return gson.toJson(this);
	}
 
}
