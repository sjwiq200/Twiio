package com.twiio.good.twiio.domain;

import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Schedule {
	
	private String roomKey;
	private String scheduleTitle;
	private String scheduleDate;
	private String scheduleAddress;
	private String country;
	private String city;
	private List<Integer> userNo;
	private String userNoString;
	private String mapImg;
	

	public Schedule() {
		// TODO Auto-generated constructor stub
	}


	public String getRoomKey() {
		return roomKey;
	}


	public void setRoomKey(String roomKey) {
		this.roomKey = roomKey;
	}


	public String getScheduleTitle() {
		return scheduleTitle;
	}


	public void setScheduleTitle(String scheduleTitle) {
		this.scheduleTitle = scheduleTitle;
	}


	public String getScheduleDate() {
		return scheduleDate;
	}


	public void setScheduleDate(String scheduleDate) {
		this.scheduleDate = scheduleDate;
	}


	public String getScheduleAddress() {
		return scheduleAddress;
	}


	public void setScheduleAddress(String scheduleAddress) {
		this.scheduleAddress = scheduleAddress;
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


	public List<Integer> getUserNo() {
		return userNo;
	}


	public void setUserNo(List<Integer> userNo) {
		this.userNo = userNo;
	}
	

	public String getUserNoString() {
		return userNoString;
	}


	public void setUserNoString(String userNoString) {
		this.userNoString = userNoString;
	}


	public String getMapImg() {
		return mapImg;
	}


	public void setMapImg(String mapImg) {
		this.mapImg = mapImg;
	}


	@Override
	public String toString() {
		return "Schedule [roomKey=" + roomKey + ", scheduleTitle=" + scheduleTitle + ", scheduleDate=" + scheduleDate
				+ ", scheduleAddress=" + scheduleAddress + ", country=" + country + ", city=" + city + ", userNo="
				+ userNo + ", userNoString=" + userNoString + ", mapImg=" + mapImg + "]";
	}
	
	


	
	
	

}
