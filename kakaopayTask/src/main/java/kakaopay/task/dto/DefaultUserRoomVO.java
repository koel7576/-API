package kakaopay.task.dto;

import com.google.gson.Gson;

public class DefaultUserRoomVO {
 
    private String userid;
    private String roomid;
    
    DefaultUserRoomVO(){
    	
    }
    
    DefaultUserRoomVO(String userid, String roomid){
    	setUserid(userid);
    	setRoomid(roomid);
    }
    
    public String getUserid() {
		return userid;
	}


	public void setUserid(String userid) {
		this.userid = userid;
	}


	public String getRoomid() {
		return roomid;
	}


	public void setRoomid(String roomid) {
		this.roomid = roomid;
	}


	public String toJson() {
		Gson gson = new Gson();
		return gson.toJson(this);
	}
 
}
