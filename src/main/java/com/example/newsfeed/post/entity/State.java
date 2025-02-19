package com.example.newsfeed.post.entity;

public enum State {//post 필드에 추가
    PUBLIC("공개"),
    PRIVATE("비공개"),
    DELETE("삭제");

    private final String value;

    State(String value){
        this.value= value;
    }

    public String getValue(){
        return value;
    }
}
