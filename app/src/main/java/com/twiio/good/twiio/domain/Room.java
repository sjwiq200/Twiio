package com.twiio.good.twiio.domain;

public class Room {
	
	private String _id;
	private String roomName;
	private String roomKey;
	private String type; //말머리
	private String country;
	private String city;
	private String region;
	private int headCount;
	private String date;
	private int userNo; //방장
	private boolean open = true;
	private boolean fix = false;

	public Room() {
		// TODO Auto-generated constructor stub
	}

	public String get_id() {
		return _id;
	}

	public void set_id(String _id) {
		this._id = _id;
	}

	public String getRoomName() {
		return roomName;
	}

	public void setRoomName(String roomName) {
		this.roomName = roomName;
	}

	public String getRoomKey() {
		return roomKey;
	}

	public void setRoomKey(String roomKey) {
		this.roomKey = roomKey;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public int getHeadCount() {
		return headCount;
	}

	public void setHeadCount(int headCount) {
		this.headCount = headCount;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public int getUserNo() {
		return userNo;
	}

	public void setUserNo(int userNo) {
		this.userNo = userNo;
	}

	public boolean isOpen() {
		return open;
	}

	public void setOpen(boolean open) {
		this.open = open;
	}

	public boolean isFix() {
		return fix;
	}

	public void setFix(boolean fix) {
		this.fix = fix;
	}

	@Override
	public String toString() {
		return "Room [_id=" + _id + ", roomName=" + roomName + ", roomKey=" + roomKey + ", type=" + type + ", country="
				+ country + ", city=" + city + ", region=" + region + ", headCount=" + headCount + ", date=" + date
				+ ", userNo=" + userNo + ", open=" + open + ", fix=" + fix + "]";
	}

	
	
	
	
	

	

	

}
