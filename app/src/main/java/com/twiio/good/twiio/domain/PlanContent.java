package com.twiio.good.twiio.domain;

import org.codehaus.jackson.annotate.JsonIgnore;

import java.sql.Date;

/**
 * Created by bitcamp on 2018-02-14.
 */

public class PlanContent {
    public PlanContent() {
        // TODO Auto-generated constructor stub
    }

    private int contentNo;
    private DailyPlan dailyPlan;
    private Scrap scrap;
    private String route;
    private String mapUrl;
    private String mapAddress;
    private String mapPhone;
    private String mapWebsite;
    private String mapName;
    private String mapImage;
    private String mapType;

    private String departureLocation;
    private String arrivalLocation;
    private String estimatedTime;
    private String routeType;
    private String routeDescription;
    private int orderNo;
    private String contentText;
    private String contentImage;
    @JsonIgnore
    private String uploadFile;
    private int contentType;

    public int getContentNo() {
        return contentNo;
    }

    public void setContentNo(int contentNo) {
        this.contentNo = contentNo;
    }

    public DailyPlan getDailyPlan() {
        return dailyPlan;
    }

    public void setDailyPlan(DailyPlan dailyPlan) {
        this.dailyPlan = dailyPlan;
    }

    public Scrap getScrap() {
        return scrap;
    }

    public void setScrap(Scrap scrap) {
        this.scrap = scrap;
    }

    public String getRoute() {
        return route;
    }

    public void setRoute(String route) {
        this.route = route;
    }

    public String getMapUrl() {
        return mapUrl;
    }

    public void setMapUrl(String mapUrl) {
        this.mapUrl = mapUrl;
    }

    public String getDepartureLocation() {
        return departureLocation;
    }

    public void setDepartureLocation(String departureLocation) {
        this.departureLocation = departureLocation;
    }

    public String getArrivalLocation() {
        return arrivalLocation;
    }

    public void setArrivalLocation(String arrivalLocation) {
        this.arrivalLocation = arrivalLocation;
    }

    public String getEstimatedTime() {
        return estimatedTime;
    }

    public void setEstimatedTime(String estimatedTime) {
        this.estimatedTime = estimatedTime;
    }

    public String getRouteType() {
        return routeType;
    }

    public void setRouteType(String routeType) {
        this.routeType = routeType;
    }

    public String getRouteDescription() {
        return routeDescription;
    }

    public void setRouteDescription(String routeDescription) {
        this.routeDescription = routeDescription;
    }

    public int getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(int orderNo) {
        this.orderNo = orderNo;
    }

    public String getContentText() {
        return contentText;
    }

    public void setContentText(String contentText) {
        this.contentText = contentText;
    }

    public String getContentImage() {
        return contentImage;
    }

    public void setContentImage(String contentImage) {
        this.contentImage = contentImage;
    }

    public int getContentType() {
        return contentType;
    }

    public void setContentType(int contentType) {
        this.contentType = contentType;
    }
    @JsonIgnore
    public String getUploadFile() {
        return uploadFile;
    }
    @JsonIgnore
    public void setUploadFile(String uploadFile) {
        this.uploadFile = uploadFile;
    }

    public String getMapAddress() {
        return mapAddress;
    }

    public void setMapAddress(String mapAddress) {
        this.mapAddress = mapAddress;
    }

    public String getMapPhone() {
        return mapPhone;
    }

    public void setMapPhone(String mapPhone) {
        this.mapPhone = mapPhone;
    }

    public String getMapWebsite() {
        return mapWebsite;
    }

    public void setMapWebsite(String mapWebsite) {
        this.mapWebsite = mapWebsite;
    }

    public String getMapName() {
        return mapName;
    }

    public void setMapName(String mapName) {
        this.mapName = mapName;
    }

    public String getMapImage() {
        return mapImage;
    }

    public void setMapImage(String mapImage) {
        this.mapImage = mapImage;
    }

    public String getMapType() {
        return mapType;
    }

    public void setMapType(String mapType) {
        this.mapType = mapType;
    }

    @Override
    public String toString() {
        return "PlanContent [contentNo=" + contentNo + ", dailyPlan=" + dailyPlan + ", scrap=" + scrap + ", route="
                + route + ", mapUrl=" + mapUrl + ", mapAddress=" + mapAddress + ", mapPhone=" + mapPhone
                + ", mapWebsite=" + mapWebsite + ", mapName=" + mapName + ", mapImage=" + mapImage + ", mapType="
                + mapType + ", departureLocation=" + departureLocation + ", arrivalLocation=" + arrivalLocation
                + ", estimatedTime=" + estimatedTime + ", routeType=" + routeType + ", routeDescription="
                + routeDescription + ", orderNo=" + orderNo + ", contentText=" + contentText + ", contentImage="
                + contentImage + ", uploadFile=" + uploadFile + ", contentType=" + contentType + "]";
    }

}
