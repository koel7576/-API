<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<title>Home</title>

<script src="https://code.jquery.com/jquery-1.12.4.min.js"></script>
<script>

$(document).ready(function(){
	$("#excute").click(function(){
		var count = -20;
		var amount = -100000;
		var token = "OMK";
		
		//var data = "count="+count+"&amount="+amount;
		var data = "token="+token;
		
		console.log(data);
	    $.ajax({
	        type: "POST",
	        //url: "/rest/sendRoomKakaopay",
	        url: "/rest/receiveRoomKakaopay",
	        //url: "/rest/searchRoomKakaopay",
	        data: data,
	        beforeSend: function (xhr) {
	            //xhr.setRequestHeader("X-USER-ID","koel7576");
	            xhr.setRequestHeader("X-USER-ID","simgoon");
	            xhr.setRequestHeader("X-ROOM-ID","KAKAO1");
	        },
	        success: function (res) {
	            console.log(res);
	            $("#resultArea").text(res);
	        }
	    });
	});	 

});

</script>
</head>
<body>
    <h1>Hello kakao!</h1>

<form>
	<div>
		<input type="button" id="excute" name="excute" value="실행"></input>
	</div>
	<div id="resultArea" name="resultArea">
	
	</div>
</form> 
 
 
</body>
</html>
