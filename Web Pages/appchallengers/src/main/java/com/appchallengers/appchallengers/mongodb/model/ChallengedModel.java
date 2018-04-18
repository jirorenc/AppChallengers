package com.appchallengers.appchallengers.mongodb.model;

public class ChallengedModel {

    //public static enum Type{CHALLENGED,FRIEND_REQUEST,ACCEPT_REQUEST,LIKE}

    private long fromId;
    private String fromUserFullName;
    private String fromUserProfilePicture;
    private long toId;
    private int type;
    private String challengeHeadLine;
    private long actionId;

    public ChallengedModel(long fromId, String fromUserFullName, String fromUserProfilePicture, long toId, int type, String challengeHeadLine, long actionId) {
        this.fromId = fromId;
        this.fromUserFullName = fromUserFullName;
        this.fromUserProfilePicture = fromUserProfilePicture;
        this.toId = toId;
        this.type = type;
        this.challengeHeadLine = challengeHeadLine;
        this.actionId = actionId;
    }

    public ChallengedModel() {
    }

    public String getFromUserProfilePicture() {
        return fromUserProfilePicture;
    }

    public void setFromUserProfilePicture(String fromUserProfilePicture) {
        this.fromUserProfilePicture = fromUserProfilePicture;
    }

    public String getFromUserFullName() {
        return fromUserFullName;
    }

    public void setFromUserFullName(String fromUserFullName) {
        this.fromUserFullName = fromUserFullName;
    }

    public String getChallengeHeadLine() {
        return challengeHeadLine;
    }

    public void setChallengeHeadLine(String challengeHeadLine) {
        this.challengeHeadLine = challengeHeadLine;
    }

    public long getFromId() {
        return fromId;
    }

    public void setFromId(long fromId) {
        this.fromId = fromId;
    }

    public long getToId() {
        return toId;
    }

    public void setToId(long toId) {
        this.toId = toId;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public long getActionId() {
        return actionId;
    }

    public void setActionId(long actionId) {
        this.actionId = actionId;
    }
}
