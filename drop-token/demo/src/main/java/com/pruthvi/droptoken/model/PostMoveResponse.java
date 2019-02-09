package com.pruthvi.droptoken.model;

import org.springframework.stereotype.Component;

@Component
public class PostMoveResponse {

    private String moveLink;

    @Override
    public String toString() {
        return "PostMoveResponse{" +
                "moveLink='" + moveLink + '\'' +
                '}';
    }

    public PostMoveResponse() {
    }

    public PostMoveResponse(String moveLink) {
        this.moveLink = moveLink;
    }

    public String getMoveLink() {
        return moveLink;
    }

    public void setMoveLink(String moveLink) {
        this.moveLink = moveLink;
    }
}
