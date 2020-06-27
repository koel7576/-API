package kakaopay.task.dto;

import java.util.Date;
import java.util.Random;

public class ReceiveRoomKakaopayVO extends DefaultUserRoomVO {
	private String sendToken;	
	private Integer amount;
	private Date sendDate;	
	private Date receiveDate;
	private int seq;
	
	public ReceiveRoomKakaopayVO() {
		
	}
	
	public ReceiveRoomKakaopayVO(SendRoomKakaopayVO sendRoomKakaopayVO, Integer amount) {
		this.setRoomid(sendRoomKakaopayVO.getRoomid());
		this.setUserid(sendRoomKakaopayVO.getUserid());
		this.setSendToken(sendRoomKakaopayVO.getSendToken());
		this.setSendDate(sendRoomKakaopayVO.getDate());
		this.setAmount(amount);
	}
	
	public String getSendToken() {
		return sendToken;
	}
	public void setSendToken(String sendToken) {
		this.sendToken = sendToken;
	}
	public Integer getAmount() {
		return amount;
	}
	public void setAmount(Integer amount) {
		this.amount = amount;
	}
	
	public Date getSendDate() {
		return sendDate;
	}

	public void setSendDate(Date sendDate) {
		this.sendDate = sendDate;
	}

	public Date getReceiveDate() {
		return receiveDate;
	}

	public void setReceiveDate(Date receiveDate) {
		this.receiveDate = receiveDate;
	}

	public int getSeq() {
		return seq;
	}
	public void setSeq(int seq) {
		this.seq = seq;
	}
	
}
