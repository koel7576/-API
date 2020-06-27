package kakaopay.task.dto;

import java.util.Date;
import java.util.List;

public class SearchRoomKakaopayInfoData extends DefaultResponseDataVO {
 
    private Date sendDate;
    private Integer sendAmount;
    private Integer receiveAmount;    
    private List<ReceiveUserAmountVO> receiveDetailInfo;
    
	public Date getSendDate() {
		return sendDate;
	}
	public void setSendDate(Date sendDate) {
		this.sendDate = sendDate;
	}
	public Integer getSendAmount() {
		return sendAmount;
	}
	public void setSendAmount(Integer sendAmount) {
		this.sendAmount = sendAmount;
	}
	public Integer getReceiveAmount() {
		return receiveAmount;
	}
	public void setReceiveAmount(Integer receiveAmount) {
		this.receiveAmount = receiveAmount;
	}
	public List<ReceiveUserAmountVO> getReceiveDetailInfo() {
		return receiveDetailInfo;
	}
	public void setReceiveDetailInfo(List<ReceiveUserAmountVO> receiveDetailInfo) {
		this.receiveDetailInfo = receiveDetailInfo;
	}
    
}
