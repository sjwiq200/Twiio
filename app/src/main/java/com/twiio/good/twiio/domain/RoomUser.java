package com.twiio.good.twiio.domain;

public class RoomUser {
	
	private String roomKey;
	private int userNo;
	

	public RoomUser() {
		// TODO Auto-generated constructor stub
		System.out.println(this.getClass());
	}


	public String getRoomKey() {
		return roomKey;
	}


	public void setRoomKey(String roomKey) {
		this.roomKey = roomKey;
	}


	public int getUserNo() {
		return userNo;
	}


	public void setUserNo(int userNo) {
		this.userNo = userNo;
	}


	@Override
	public String toString() {
		return "RoomUser [roomKey=" + roomKey + ", userNo=" + userNo + "]";
	}
	
	
	
	

}
