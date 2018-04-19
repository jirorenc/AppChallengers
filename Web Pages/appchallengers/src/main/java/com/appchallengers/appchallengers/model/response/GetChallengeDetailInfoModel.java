package com.appchallengers.appchallengers.model.response;

public class GetChallengeDetailInfoModel {

    private String headLine;
    private long counter;

    public GetChallengeDetailInfoModel(String headLine, long counter) {
        this.headLine = headLine;
        this.counter = counter;
    }

    public GetChallengeDetailInfoModel() {
    }

    public String getHeadLine() {
        return headLine;
    }

    public void setHeadLine(String headLine) {
        this.headLine = headLine;
    }

    public long getCounter() {
        return counter;
    }

    public void setCounter(long counter) {
        this.counter = counter;
    }
}
