package kakaopay.task.dao.impl;

import java.util.List;

import javax.inject.Inject;
 
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import kakaopay.task.dto.ReceiveKakaopayResponseDataVO;
import kakaopay.task.dto.ReceiveRoomKakaopayVO;
import kakaopay.task.dto.ReceiveUserAmountVO;
import kakaopay.task.dto.SearchRoomKakaopayInfoData;
import kakaopay.task.dto.SendReceiveKakaopayVO;
import kakaopay.task.dto.SendRoomKakaopayVO;
import kakaopay.task.dao.RoomKakaopayDAO;

 
@Repository
public class RoomKakaopayDAOImpl implements RoomKakaopayDAO {
 
    @Inject
    private SqlSession sqlSession;
    
    private static final String Namespace = "kakaopay.task.mapper.roomKakaopayMapper";
    
    @Override
    public List<SendRoomKakaopayVO> selectSendRoomKakaopay(SendRoomKakaopayVO sendRoomKakaopayVO) throws Exception {
    	
        return sqlSession.selectList(Namespace+".selectSendRoomKakaopay", sendRoomKakaopayVO);
    }
    
    @Override
    public int insertSendRoomKakaopay(SendRoomKakaopayVO sendRoomKakaopayVO) throws Exception{
    	return sqlSession.insert(Namespace+".insertSendRoomKakaopay", sendRoomKakaopayVO);
    }
    
    @Override
    public int insertReceiveRoomKakaopay(List<ReceiveRoomKakaopayVO> receiveRoomKakaopayList) throws Exception{
    	int count = 0;
    	for(ReceiveRoomKakaopayVO receiveRoomKakaopayVO : receiveRoomKakaopayList) {
    		count += sqlSession.insert(Namespace+".insertReceiveRoomKakaopay", receiveRoomKakaopayVO);
    	}
    	return count;
    }
    
    @Override
    public int subtractKakaopay(SendRoomKakaopayVO sendRoomKakaopayVO) throws Exception{
    	return sqlSession.update(Namespace+".subtractKakaopay", sendRoomKakaopayVO);
    }
    
    @Override
    public List<SendReceiveKakaopayVO> selectSendReceiveKakaopay(ReceiveRoomKakaopayVO receiveRoomKakaopayVO) throws Exception{
    	return sqlSession.selectList(Namespace+".selectSendReceiveKakaopay", receiveRoomKakaopayVO);
    }
    
    @Override
    public List<ReceiveRoomKakaopayVO> selectReceiveKakaopay(ReceiveRoomKakaopayVO receiveRoomKakaopayVO) throws Exception{
    	return sqlSession.selectList(Namespace+".selectReceiveRoomKakaopay", receiveRoomKakaopayVO);
    }
    
    @Override
    public int updateReceiveRoomKakaopay(ReceiveRoomKakaopayVO receiveRoomKakaopayVO) throws Exception{
    	return sqlSession.update(Namespace+".updateReceiveRoomKakaopay", receiveRoomKakaopayVO);
    }
    
    @Override
    public int plusKakaopay(ReceiveRoomKakaopayVO receiveRoomKakaopayVO) throws Exception{
    	return sqlSession.update(Namespace+".plusKakaopay", receiveRoomKakaopayVO);
    }
    
    @Override
    public List<SearchRoomKakaopayInfoData> searchRoomKakaopayBaseInfo(ReceiveRoomKakaopayVO receiveRoomKakaopayVO) throws Exception{
    	return sqlSession.selectList(Namespace+".searchRoomKakaopayBaseInfo", receiveRoomKakaopayVO);
    }
    
    @Override
    public List<ReceiveUserAmountVO> searchRoomKakaopaydetailesInfo(ReceiveRoomKakaopayVO receiveRoomKakaopayVO) throws Exception{
    	return sqlSession.selectList(Namespace+".searchRoomKakaopaydetailesInfo", receiveRoomKakaopayVO);
    }
 
}
 