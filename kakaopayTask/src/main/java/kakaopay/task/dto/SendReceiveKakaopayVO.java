package kakaopay.task.dto;


public class SendReceiveKakaopayVO extends DefaultUserRoomVO {
	private String tablename;
	private String sendToken;
	private Integer amount;
	
	public String getTablename() {
		return tablename;
	}
	public void setTablename(String tablename) {
		this.tablename = tablename;
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
	
	
	
}
