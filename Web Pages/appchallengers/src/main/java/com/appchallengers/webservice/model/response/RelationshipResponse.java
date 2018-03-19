package com.appchallengers.webservice.model.response;

public class RelationshipResponse {

    private Integer statusCode;
    private Integer firstUserId;
    private Integer secondUserId;
    private Integer actionUserId;
    private Integer relationshipStatus;

    public RelationshipResponse(Integer statusCode, Integer firstUserId, Integer secondUserId, Integer actionUserId, Integer relationshipStatus) {
        this.statusCode = statusCode;
        this.firstUserId = firstUserId;
        this.secondUserId = secondUserId;
        this.actionUserId = actionUserId;
        this.relationshipStatus = relationshipStatus;
    }

    public RelationshipResponse(Integer statusCode) {
        this.statusCode = statusCode;
    }
}
