package com.twiio.good.twiio.domain;

import org.codehaus.jackson.annotate.JsonIgnore;

/**
 * Created by bitcamp on 2018-02-14.
 */

public class Scrap {
    public Scrap() {
        // TODO Auto-generated constructor stub
    }

    private int scrapNo;
    private User user;
    private MainPlan mainPlan;
    private Community community;
    private String scrapTitle;
    private String scrapContent;
    private String scrapType;



    public int getScrapNo() {
        return scrapNo;
    }
    public void setScrapNo(int scrapNo) {
        this.scrapNo = scrapNo;
    }
    public User getUser() {
        return user;
    }
    public void setUser(User user) {
        this.user = user;
    }
    public MainPlan getMainPlan() {
        return mainPlan;
    }
    public void setMainPlan(MainPlan mainPlan) {
        this.mainPlan = mainPlan;
    }
    public Community getCommunity() {
        return community;
    }
    public void setCommunity(Community community) {
        this.community = community;
    }
    public String getScrapTitle() {
        return scrapTitle;
    }
    public void setScrapTitle(String scrapTitle) {
        this.scrapTitle = scrapTitle;
    }
    public String getScrapContent() {
        return scrapContent;
    }
    public void setScrapContent(String scrapContent) {
        this.scrapContent = scrapContent;
    }
    public String getScrapType() {
        return scrapType;
    }
    public void setScrapType(String scrapType) {
        this.scrapType = scrapType;
    }

    @Override
    public String toString() {
        return "Scrap [scrapNo=" + scrapNo + ", user=" + user + ", mainPlan=" + mainPlan + ", community=" + community
                + ", scrapTitle=" + scrapTitle + ", scrapContent=" + scrapContent + ", scrapType=" + scrapType + "]";
    }

}
