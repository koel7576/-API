package kakaopay.task.controller;

import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import kakaopay.task.dto.DefaultResponseDataVO;
import kakaopay.task.dto.DefaultUserRoomVO;
import kakaopay.task.dto.ReceiveKakaopayResponseDataVO;
import kakaopay.task.dto.ReceiveRoomKakaopayVO;
import kakaopay.task.dto.SearchRoomKakaopayInfoData;
import kakaopay.task.dto.SendKakaopayResponseDataVO;
import kakaopay.task.dto.SendRoomKakaopayVO;
import kakaopay.task.service.RoomKakaopayService;

@Controller
@RequestMapping(value = "/rest")
public class RestController {
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
    
    @Inject
    private RoomKakaopayService sendRoomKakaopayService;
    
    /**
     * Simply selects the home view to render by returning its name.
     */
    @ResponseBody
    @RequestMapping(value = "/sendRoomKakaopay", method = {RequestMethod.GET, RequestMethod.POST}) 
    public String sendRoomKakaopay(HttpServletRequest request) throws Exception{
    	SendKakaopayResponseDataVO resultData = new SendKakaopayResponseDataVO();
    	
    	logger.info("-----------------------------------------------------------------------------------");
        logger.info("X-USER-ID : " + request.getHeader("X-USER-ID"));
        logger.info("X-ROOM-ID : " + request.getHeader("X-ROOM-ID"));
        
        int count = 0;
        Integer sendAmount = 0; 
        try {
        	count = Integer.valueOf(request.getParameter("count").toString()).intValue();
        	sendAmount = Integer.valueOf(request.getParameter("amount").toString());
        }catch(Exception e) {
        	logger.info("Parameter Parse Error ");
        	
        	resultData.setResultCode("E002");
        	resultData.setResultMessage("Parameter Parse Error");
        	
        	return resultData.toJson();
        }
        
        if(count <= 0 || sendAmount <= 0) {
        	resultData.setResultCode("E004");
        	resultData.setResultMessage("Parameter under 0");
        	
        	return resultData.toJson();
        }
        
        if(sendAmount < count) {
        	resultData.setResultCode("E003");
        	resultData.setResultMessage("sendAmount is smaller");
        	
        	return resultData.toJson();
        }
        
        
        SendRoomKakaopayVO sendRoomKakaopayVo = new SendRoomKakaopayVO();
        sendRoomKakaopayVo.setUserid(request.getHeader("X-USER-ID"));
        sendRoomKakaopayVo.setRoomid(request.getHeader("X-ROOM-ID"));
        sendRoomKakaopayVo.setCount(count);
        sendRoomKakaopayVo.setSendAmount(sendAmount);
        sendRoomKakaopayVo.setDate(new Date());
        
        resultData = sendRoomKakaopayService.sendRoomKakaopay(sendRoomKakaopayVo);
        
        logger.info("-----------------------------------------------------------------------------------");
 
        return resultData.toJson();
    }
    
    @ResponseBody
    @RequestMapping(value = "/receiveRoomKakaopay", method = {RequestMethod.GET, RequestMethod.POST}) 
    public String receiveRoomKakaopay(HttpServletRequest request) throws Exception{
    	ReceiveKakaopayResponseDataVO resultData = new ReceiveKakaopayResponseDataVO();
    	
    	logger.info("-----------------------------------------------------------------------------------");
        logger.info("X-USER-ID : " + request.getHeader("X-USER-ID"));
        logger.info("X-ROOM-ID : " + request.getHeader("X-ROOM-ID"));
        
        String token = request.getParameter("token") == null ? "" : request.getParameter("token").toString();
        
        if("".equals(token)) {
        	resultData.setResultCode("E005");
        	resultData.setResultMessage("Parameter is Empty");
        	
        	return resultData.toJson();
        }
        
        ReceiveRoomKakaopayVO receiveRoomKakaopayVo = new ReceiveRoomKakaopayVO();
        receiveRoomKakaopayVo.setUserid(request.getHeader("X-USER-ID"));
        receiveRoomKakaopayVo.setRoomid(request.getHeader("X-ROOM-ID"));
        receiveRoomKakaopayVo.setSendToken(token);
        
        resultData = sendRoomKakaopayService.receiveRoomKakaopay(receiveRoomKakaopayVo);
        
        logger.info("-----------------------------------------------------------------------------------");
 
        return resultData.toJson();
    }
    
    @ResponseBody
    @RequestMapping(value = "/searchRoomKakaopay", method = {RequestMethod.GET, RequestMethod.POST}) 
    public String searchRoomKakaopay(HttpServletRequest request) throws Exception{
    	SearchRoomKakaopayInfoData resultData = new SearchRoomKakaopayInfoData();
    	
    	logger.info("-----------------------------------------------------------------------------------");
        logger.info("X-USER-ID : " + request.getHeader("X-USER-ID"));
        logger.info("X-ROOM-ID : " + request.getHeader("X-ROOM-ID"));
        
        String token = request.getParameter("token") == null ? "" : request.getParameter("token").toString();
        
        if("".equals(token)) {
        	resultData.setResultCode("E005");
        	resultData.setResultMessage("Parameter is Empty");
        	
        	return resultData.toJson();
        }
        
        ReceiveRoomKakaopayVO receiveRoomKakaopayVo = new ReceiveRoomKakaopayVO();
        receiveRoomKakaopayVo.setUserid(request.getHeader("X-USER-ID"));
        receiveRoomKakaopayVo.setRoomid(request.getHeader("X-ROOM-ID"));
        receiveRoomKakaopayVo.setSendToken(token);
        
        resultData = sendRoomKakaopayService.searchRoomKakaopay(receiveRoomKakaopayVo);
        
        logger.info("-----------------------------------------------------------------------------------");
 
        return resultData.toJson();
    }
    
}
