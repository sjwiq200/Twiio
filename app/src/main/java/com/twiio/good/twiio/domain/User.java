package com.twiio.good.twiio.domain;



import android.os.Parcel;
import android.os.Parcelable;

import java.sql.Date;
import java.util.Properties;


import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;


@JsonIgnoreProperties(ignoreUnknown = true)

public class User {


	private int userNo;
	private String userId;
	private String password;
	private String passwordConfirm;
	private String userName;
	private String userBirthday;
	private String userGender;
	private String userPhone;
	private String userEmail;
	private String userAccount;
	private String userType;
	private double userEval;
	private double userEvalCredit;
	private String userImage;
	private Date regDate;
	private String profilePublic;
	private String userRegisterType;
	private String userLeave;
	private String userBank;
	private int attendanceRate;
	private int profileCredibility;
	private double starEvalHost;
	private String reviewHost;
	private String emailVer;
	private String mainPlanNoShared;
	@JsonIgnore


	private String phone1;
	private String phone2;
	private String phone3;

	/////////////////////// KakaoLogin Filed //////////////////////////
	private String kaccount_email;
	private String kaccount_email_verified;
	private Properties properties;
	////////////////////// kakao/googleLogin Filed /////////////////////
	private String id;
	////////////////////// GoogleLogin Filed ////////////////////////
	private String gender;
	private String name;
	private String link;
	private String verified_email;
	private String given_name;
	private String locale;
	private String family_name;
	private String email;
	private String picture;

	public User() {
	}


	public int getUserNo() {
		return userNo;
	}


	public void setUserNo(int userNo) {
		this.userNo = userNo;
	}


	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPasswordConfirm() {
		return passwordConfirm;
	}

	public void setPasswordConfirm(String passwordConfirm) {
		this.passwordConfirm = passwordConfirm;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserBirthday() {
		return userBirthday;
	}

	public void setUserBirthday(String userBirthday) {
		this.userBirthday = userBirthday;
	}

	public String getUserGender() {
		return userGender;
	}

	public void setUserGender(String userGender) {
		this.userGender = userGender;
	}

	public String getUserPhone() {
		return userPhone;
	}

	public void setUserPhone(String userPhone) {
		this.userPhone = userPhone;

		if(userPhone != null && userPhone.length() !=0 ){
			phone1 = userPhone.split("-")[0];
			phone2 = userPhone.split("-")[1];
			phone3 = userPhone.split("-")[2];
		}
	}

	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	public String getUserAccount() {
		return userAccount;
	}

	public void setUserAccount(String userAccount) {
		this.userAccount = userAccount;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public double getUserEval() {
		return userEval;
	}

	public void setUserEval(double userEval) {
		this.userEval = userEval;
	}

	public String getUserImage() {
		return userImage;
	}

	public void setUserImage(String userImage) {
		this.userImage = userImage;
	}

	public Date getRegDate() {
		return regDate;
	}

	public void setRegDate(Date regDate) {
		this.regDate = regDate;
	}

	public String isProfilePublic() {
		return profilePublic;
	}

	public void setProfilePublic(String profilePublic) {
		this.profilePublic = profilePublic;
	}

	public String getUserRegisterType() {
		return userRegisterType;
	}

	public void setUserRegisterType(String userRegisterType) {
		this.userRegisterType = userRegisterType;
	}

	public String getUserLeave() {
		return userLeave;
	}

	public void setUserLeave(String userLeave) {
		this.userLeave = userLeave;
	}

	public String getUserBank() {
		return userBank;
	}

	public void setUserBank(String userBank) {
		this.userBank = userBank;
	}

	public int getAttendanceRate() {
		return attendanceRate;
	}

	public void setAttendanceRate(int attendanceRate) {
		this.attendanceRate = attendanceRate;
	}

	public int getProfileCredibility() {
		return profileCredibility;
	}

	public void setProfileCredibility(int profileCredibility) {
		this.profileCredibility = profileCredibility;
	}

	public double getStarEvalHost() {
		return starEvalHost;
	}

	public void setStarEvalHost(double starEvalHost) {
		this.starEvalHost = starEvalHost;
	}

	public String getReviewHost() {
		return reviewHost;
	}

	public void setReviewHost(String reviewHost) {
		this.reviewHost = reviewHost;
	}

	public String getEmailVer() {
		return emailVer;
	}


	public void setEmailVer(String emailVer) {
		this.emailVer = emailVer;
	}


	public String getPhone1() {
		return phone1;
	}
	public String getPhone2() {
		return phone2;
	}
	public String getPhone3() {
		return phone3;
	}


	///////////////////////////// kakaoLogin getter/setter /////////////////////////////
	public String getKaccount_email() {
		return kaccount_email;

	}

	public void setKaccount_email(String kaccount_email) {
		this.kaccount_email = kaccount_email;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getKaccount_email_verified() {
		return kaccount_email_verified;
	}

	public void setKaccount_email_verified(String kaccount_email_verified) {
		this.kaccount_email_verified = kaccount_email_verified;
	}

	public Properties getProperties() {
		return properties;
	}

	public void setProperties(Properties properties) {
		this.properties = properties;
	}

	/////////////////////////////// googleLogin getter/setter ///////////////////////////////	
	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public String getVerified_email() {
		return verified_email;
	}

	public void setVerified_email(String verified_email) {
		this.verified_email = verified_email;
	}

	public String getGiven_name() {
		return given_name;
	}

	public void setGiven_name(String given_name) {
		this.given_name = given_name;
	}

	public String getLocale() {
		return locale;
	}

	public void setLocale(String locale) {
		this.locale = locale;
	}

	public String getFamily_name() {
		return family_name;
	}

	public void setFamily_name(String family_name) {
		this.family_name = family_name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPicture() {
		return picture;
	}

	public void setPicture(String picture) {
		this.picture = picture;
	}

	public double getUserEvalCredit() {
		return userEvalCredit;
	}


	public void setUserEvalCredit(double userEvalCredit) {
		this.userEvalCredit = userEvalCredit;
	}


	public String toString() {
		return "User [userNo=" + userNo + ", userId=" + userId + ", password=" + password + ", passwordConfirm="
				+ passwordConfirm + ", userName=" + userName + ", userBirthday=" + userBirthday + ", userGender="
				+ userGender + ", userPhone=" + userPhone + ", userEmail=" + userEmail + ", userAccount=" + userAccount
				+ ", userType=" + userType + ", userEval=" + userEval + ", userImage=" + userImage + ", regDate="
				+ regDate + ", profilePublic=" + profilePublic + ", userRegisterType=" + userRegisterType
				+ ", userLeave=" + userLeave + ", userBank=" + userBank
				+ ", attendanceRate=" + attendanceRate + ", profileCredibility="
				+ profileCredibility + ", starEvalHost=" + starEvalHost + ", reviewHost=" + reviewHost + "]";
	}


	public String getMainPlanNoShared() {
		return mainPlanNoShared;
	}


	public void setMainPlanNoShared(String mainPlanNoShared) {
		this.mainPlanNoShared = mainPlanNoShared;
	}

//	@Override
//	public int describeContents() {
//		return 0;
//	}
//
//	@Override
//	public void writeToParcel(Parcel parcel, int i) {
//		parcel.writeInt(userNo);
//		parcel.writeString(userId);
//		parcel.writeString(password);
//		parcel.writeString(passwordConfirm);
//		parcel.writeString(userName);
//		parcel.writeString(userBirthday);
//		parcel.writeString(userGender);
//		parcel.writeString(userPhone);
//		parcel.writeString(userEmail);
//		parcel.writeString(userAccount);
//		parcel.writeString(userType);
//		parcel.writeDouble(userEval);
//		parcel.writeDouble(userEvalCredit);
//		parcel.writeString(userImage);
//		parcel.writeValue(regDate);
//		parcel.writeString(profilePublic);
//		parcel.writeString(userRegisterType);
//		parcel.writeString(userLeave);
//		parcel.writeString(userBank);
//		parcel.writeInt(attendanceRate);
//		parcel.writeInt(profileCredibility);
//		parcel.writeDouble(starEvalHost);
//		parcel.writeString(reviewHost);
//		parcel.writeString(emailVer);
//		parcel.writeString(mainPlanNoShared);
//		parcel.writeString(phone1);
//		parcel.writeString(phone2);
//		parcel.writeString(phone3);
//		parcel.writeString(kaccount_email);
//		parcel.writeString(kaccount_email_verified);
//		parcel.writeValue(properties);
//
//		parcel.writeString(id);
//		parcel.writeString(gender);
//		parcel.writeString(name);
//		parcel.writeString(link);
//		parcel.writeString(verified_email);
//		parcel.writeString(given_name);
//		parcel.writeString(locale);
//		parcel.writeString(family_name);
//		parcel.writeString(email);
//		parcel.writeString(picture);
//	}



}
