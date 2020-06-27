package kakaopay.task.dto;

import com.google.gson.Gson; 

public class SendKakaopayResponseDataVO extends DefaultResponseDataVO {
 
    private String token;

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
 
}
