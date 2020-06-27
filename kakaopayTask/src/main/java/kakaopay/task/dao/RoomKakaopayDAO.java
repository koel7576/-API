package kakaopay.task.dao;

import java.util.List;

import kakaopay.task.dto.ReceiveKakaopayResponseDataVO;
import kakaopay.task.dto.ReceiveRoomKakaopayVO;
import kakaopay.task.dto.ReceiveUserAmountVO;
import kakaopay.task.dto.SearchRoomKakaopayInfoData;
import kakaopay.task.dto.SendReceiveKakaopayVO;
import kakaopay.task.dto.SendRoomKakaopayVO;
 
public interface RoomKakaopayDAO {
    
    public List<SendRoomKakaopayVO> selectSendRoomKakaopay(SendRoomKakaopayVO sendRoomKakaopayVO) throws Exception;
    public int insertSendRoomKakaopay(SendRoomKakaopayVO sendRoomKakaopayVO) throws Exception;
    public int insertReceiveRoomKakaopay(List<ReceiveRoomKakaopayVO> receiveRoomKakaopayList) throws Exception;
    public int subtractKakaopay(SendRoomKakaopayVO sendRoomKakaopayVO) throws Exception;
    
    public List<SendReceiveKakaopayVO> selectSendReceiveKakaopay(ReceiveRoomKakaopayVO receiveRoomKakaopayVO) throws Exception;
    public List<ReceiveRoomKakaopayVO> selectReceiveKakaopay(ReceiveRoomKakaopayVO receiveRoomKakaopayVO) throws Exception;
    public int updateReceiveRoomKakaopay(ReceiveRoomKakaopayVO receiveRoomKakaopayVO) throws Exception;
    public int plusKakaopay(ReceiveRoomKakaopayVO receiveRoomKakaopayVO) throws Exception;
    public List<SearchRoomKakaopayInfoData> searchRoomKakaopayBaseInfo(ReceiveRoomKakaopayVO receiveRoomKakaopayVO) throws Exception;
    public List<ReceiveUserAmountVO> searchRoomKakaopaydetailesInfo(ReceiveRoomKakaopayVO receiveRoomKakaopayVO) throws Exception;
    
    
}

