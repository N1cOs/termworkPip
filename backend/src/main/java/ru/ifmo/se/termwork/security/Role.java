package ru.ifmo.se.termwork.security;

import ru.ifmo.se.termwork.domain.Authority;

public enum Role {
    STUDENT(1, "ROLE_STUDENT"),
    WORKER(2, "ROLE_WORKER"),
    HEAD_WORKER(3, "ROLE_HEAD_WORKER");

    private String value;

    private int databaseId;

    Role(int databaseId, String value){
        this.databaseId = databaseId;
        this.value = value;
    }
    
    public Authority getAuthority(){
        return new Authority(databaseId, value);
    }

    public String getValue(){
        return value;
    }
}
