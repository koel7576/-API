package kakaopay.task.dto;

import java.util.Date;
import java.util.Random;

public class SendRoomKakaopayVO extends DefaultUserRoomVO {
	private int count;
	private Integer sendAmount;
	private StringBuffer sendToken;
	private Date date;
	
	public SendRoomKakaopayVO(){
		this.createSendToken();
	}
	
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public Integer getSendAmount() {
		return sendAmount;
	}
	public void setSendAmount(Integer sendAmount) {
		this.sendAmount = sendAmount;
	}
	public String getSendToken() {
		return sendToken.toString();
	}	
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	
	public void createSendToken() {
		Random rnd = new Random();
		
		sendToken = new StringBuffer();		
		for(int i=0; i<3; i++) {
			String str = String.valueOf((char) ((int) (rnd.nextInt(26)) + 65));
			this.sendToken.append(str);
		}
	}
	
}
