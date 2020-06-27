package kakaopay.task.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Random;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import kakaopay.task.dao.impl.RoomKakaopayDAOImpl;
import kakaopay.task.dto.ReceiveKakaopayResponseDataVO;
import kakaopay.task.dto.ReceiveRoomKakaopayVO;
import kakaopay.task.dto.SearchRoomKakaopayInfoData;
import kakaopay.task.dto.SendKakaopayResponseDataVO;
import kakaopay.task.dto.SendReceiveKakaopayVO;
import kakaopay.task.dto.SendRoomKakaopayVO;
import kakaopay.task.service.RoomKakaopayService;
 
@Service
public class RoomKakaopayServiceImpl implements RoomKakaopayService {
	@Inject
	private RoomKakaopayDAOImpl roomKakaopayDAO;
	
	private static final Logger logger = LoggerFactory.getLogger(RoomKakaopayServiceImpl.class);
 
    @Override
    public SendKakaopayResponseDataVO sendRoomKakaopay(SendRoomKakaopayVO sendRoomKakaopayVo) throws Exception{
    	SendKakaopayResponseDataVO resultData = new SendKakaopayResponseDataVO();
    	// 뿌릴 금액 랜덤 분배
    	List<Integer> amountList = DivideKakaopay(sendRoomKakaopayVo.getSendAmount(), sendRoomKakaopayVo.getCount());
    	
    	// token 중복 값 조회
    	for(int i=0; i < 10; i++) {
    		List<SendRoomKakaopayVO> searchSendKakaopay = roomKakaopayDAO.selectSendRoomKakaopay(sendRoomKakaopayVo);
    		
    		if(searchSendKakaopay.size() > 0) {
    			if(i == 9) {
    				// 에러 처리
    				resultData.setResultCode("E001");
    		    	resultData.setResultMessage("Create Token Fail");
    		    	
    		    	return resultData;
    			}
    			sendRoomKakaopayVo.createSendToken();
    			continue;
    		}else break;
    	}
    	
    	List<ReceiveRoomKakaopayVO> inserReceiveRoomKAkaopayList = new ArrayList<ReceiveRoomKakaopayVO>();
    	for(int i=0; i < amountList.size(); i++) {
    		ReceiveRoomKakaopayVO receiveRoomKAkaopayVO = new ReceiveRoomKakaopayVO(sendRoomKakaopayVo, amountList.get(i));
    		receiveRoomKAkaopayVO.setSeq(i);
    		
    		inserReceiveRoomKAkaopayList.add(receiveRoomKAkaopayVO);
    	}
    	// DB 저장
    	try {
    		saveSendRoomKakaopay(sendRoomKakaopayVo, inserReceiveRoomKAkaopayList);
    		resultData.setResultCode("0000");
        	resultData.setResultMessage("Success");
        	resultData.setToken(sendRoomKakaopayVo.getSendToken());
    	}catch(Exception e) {
    		logger.info("Data Save Error");
    		
    		resultData.setResultCode("E000");
        	resultData.setResultMessage("Data Save Error");
        	resultData.setToken(sendRoomKakaopayVo.getSendToken());
    	}
 
        return resultData;
    }
    
    @Override
    public List<Integer> DivideKakaopay(Integer amount, int count) throws Exception{
    	List<Integer> amountList = new ArrayList<Integer>();
    	Random rnd = new Random();
    	
    	if(count == 1) {
    		amountList.add(amount);
    		return amountList;
    	}
    		
    	amountList.add(rnd.nextInt(amount - count + 1)+1);
    	amountList.add(amount - amountList.get(0));
    	logger.info(amountList.toString());
    	for(int i=0; i < count - 2 ; i++) {
    		Collections.sort(amountList, Collections.reverseOrder());    		
    		amountList.add(rnd.nextInt(amountList.get(0) - (count - 2 - i))+1);
    		amountList.set(0, amountList.get(0) - amountList.get(amountList.size() - 1));
    	}
    	
    	Collections.shuffle(amountList);
    	
    	logger.info(amountList.toString());
    	
    	return amountList;
    }
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveSendRoomKakaopay(SendRoomKakaopayVO sendRoomKakaopayVo, List<ReceiveRoomKakaopayVO> inserReceiveRoomKAkaopayList) throws Exception{
    	int count = 0;
		count = roomKakaopayDAO.insertSendRoomKakaopay(sendRoomKakaopayVo);
		if(count == 0) throw new Exception("insert SendRoomKakaopay Error");		
		count = roomKakaopayDAO.insertReceiveRoomKakaopay(inserReceiveRoomKAkaopayList);
		if(count == 0) throw new Exception("insert ReceiveRoomKakaopay Error");
		count = roomKakaopayDAO.subtractKakaopay(sendRoomKakaopayVo);
		if(count == 0) throw new Exception("update remainpay Error");
    }
    
    @Override    
    public ReceiveKakaopayResponseDataVO receiveRoomKakaopay(ReceiveRoomKakaopayVO receiveRoomKakaopayVO) throws Exception{
    	ReceiveKakaopayResponseDataVO resultData = new ReceiveKakaopayResponseDataVO();
    	
    	List<SendReceiveKakaopayVO> sendReceiveKakaopayList = roomKakaopayDAO.selectSendReceiveKakaopay(receiveRoomKakaopayVO);
    	
    	if(sendReceiveKakaopayList.size() > 0) {
    		for(SendReceiveKakaopayVO sendReceiveKakaopayVO : sendReceiveKakaopayList) {
    			if("sendRoomKakaopay".equals(sendReceiveKakaopayVO.getTablename())) {
    				resultData.setResultCode("E005");
    	        	resultData.setResultMessage("Send by yourself");
    	        	
    			}else{
    				resultData.setResultCode("E006");
    	        	resultData.setResultMessage("Already received");
    			}
    			resultData.setAmount(sendReceiveKakaopayVO.getAmount());
    			
    			return resultData;
    		}
    	}
    	
    	List<ReceiveRoomKakaopayVO> receiveRoomKakaopay = roomKakaopayDAO.selectReceiveKakaopay(receiveRoomKakaopayVO);
    	
    	if(receiveRoomKakaopay.size() > 0) {
    		Date now = new Date();
    		long diffTime = (now.getTime() - receiveRoomKakaopay.get(0).getSendDate().getTime()) / (1000*60);
    		
    		if(diffTime > 10) {
    			resultData.setResultCode("E007");
	        	resultData.setResultMessage("Receive Time Over");
	        	resultData.setAmount(receiveRoomKakaopay.get(0).getAmount());
	        	
	        	return resultData;
    		}
    		
    		try {
    			int amount = updateReceiveRoomKakaopay(receiveRoomKakaopay);
    			
    			resultData.setResultCode("0000");
            	resultData.setResultMessage("Success");
            	resultData.setAmount(amount);
    		}catch(Exception e) {
    			resultData.setResultCode("E000");
            	resultData.setResultMessage("Data Save Error");
            	resultData.setAmount(receiveRoomKakaopay.get(0).getAmount());
    			
    		}
    		
    	}	
    	
    	return resultData;
    }
    
    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor={Exception.class})
    public int updateReceiveRoomKakaopay(List<ReceiveRoomKakaopayVO> receiveRoomKakaopayVO) throws Exception{
    	int count = 0;
    	int amount = 0;
    	ReceiveRoomKakaopayVO successReceiveRoomKakaopay = null;
    	for(ReceiveRoomKakaopayVO sendRoomKakaopay : receiveRoomKakaopayVO) {
    		count = roomKakaopayDAO.updateReceiveRoomKakaopay(sendRoomKakaopay);
    		
    		if(count > 1) throw new Exception();
    		else if(count == 1) {
    			amount = sendRoomKakaopay.getAmount();
    			successReceiveRoomKakaopay = sendRoomKakaopay;
    			break;
    		}
    	}
		if(count == 0) throw new Exception();
		count = roomKakaopayDAO.plusKakaopay(successReceiveRoomKakaopay);
		if(count == 0) throw new Exception();		 
		return amount;
    }
    
    @Override
    public SearchRoomKakaopayInfoData searchRoomKakaopay(ReceiveRoomKakaopayVO receiveRoomKakaopayVO) throws Exception{
    	List<SearchRoomKakaopayInfoData> receiveKakaopayResponseData = new ArrayList<SearchRoomKakaopayInfoData>();
    	SearchRoomKakaopayInfoData resultData = new SearchRoomKakaopayInfoData();
    	
    	try {
    		receiveKakaopayResponseData = roomKakaopayDAO.searchRoomKakaopayBaseInfo(receiveRoomKakaopayVO);
    		if(receiveKakaopayResponseData.size() == 0) {
    			resultData.setResultCode("E010");
    			resultData.setResultMessage("is Empty Data");
    			return resultData;
    		}
    		
    		resultData = receiveKakaopayResponseData.get(0);
    		resultData.setReceiveDetailInfo(roomKakaopayDAO.searchRoomKakaopaydetailesInfo(receiveRoomKakaopayVO));
    		
    		resultData.setResultCode("0000");
			resultData.setResultMessage("Success");
    	}catch(Exception e) {
    		resultData.setResultCode("E011");
			resultData.setResultMessage("Data Search Error");
    	}
    	
    	return resultData;
    }
 
}
