package kakaopay.task.service;

import java.util.List;

import kakaopay.task.dto.ReceiveKakaopayResponseDataVO;
import kakaopay.task.dto.ReceiveRoomKakaopayVO;
import kakaopay.task.dto.SearchRoomKakaopayInfoData;
import kakaopay.task.dto.SendKakaopayResponseDataVO;
import kakaopay.task.dto.SendRoomKakaopayVO;
 
public interface RoomKakaopayService {
    
    public SendKakaopayResponseDataVO sendRoomKakaopay(SendRoomKakaopayVO sendRoomKakaopayVo) throws Exception;
    public List<Integer> DivideKakaopay(Integer amount, int count) throws Exception;
    public void saveSendRoomKakaopay(SendRoomKakaopayVO sendRoomKakaopayVo, List<ReceiveRoomKakaopayVO> inserReceiveRoomKAkaopayList) throws Exception;
    public ReceiveKakaopayResponseDataVO receiveRoomKakaopay(ReceiveRoomKakaopayVO sendRoomKakaopayVo) throws Exception;
    public int updateReceiveRoomKakaopay(List<ReceiveRoomKakaopayVO> sendRoomKakaopayVo) throws Exception;
    
    public SearchRoomKakaopayInfoData searchRoomKakaopay(ReceiveRoomKakaopayVO sendRoomKakaopayVo) throws Exception;
    
    
    
    
}
