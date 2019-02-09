package com.pruthvi.droptoken.model;

import org.springframework.stereotype.Component;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Component
public class PostMoveRequest {

    private Integer column;

    public PostMoveRequest(Integer column) {
        this.column = column;
    }

    public PostMoveRequest() {
    }

    public Integer getColumn() {
        return column;
    }

    public void setColumn(Integer column) {
        this.column = column;
    }
}
