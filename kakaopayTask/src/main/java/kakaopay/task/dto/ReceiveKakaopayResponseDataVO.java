package kakaopay.task.dto;

import com.google.gson.Gson; 

public class ReceiveKakaopayResponseDataVO extends DefaultResponseDataVO {
 
    private Integer amount;

	public Integer getAmount() {
		return amount;
	}

	public void setAmount(Integer amount) {
		this.amount = amount;
	}
	 
}
