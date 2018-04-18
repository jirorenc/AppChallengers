package com.appchallengers.appchallengers.model.response;

public class GetTrendsResponse {

    private long challengeId;
    private String headLine;
    private long counter;

    public GetTrendsResponse(long challengeId, String headLine, long counter) {
        this.challengeId = challengeId;
        this.headLine = headLine;
        this.counter = counter;
    }

    public GetTrendsResponse() {
    }

    public long getChallengeId() {
        return challengeId;
    }

    public void setChallengeId(long challengeId) {
        this.challengeId = challengeId;
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
