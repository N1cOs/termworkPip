package ru.ifmo.se.termwork.security;

import ru.ifmo.se.termwork.domain.Authority;

public enum Role {
    STUDENT(1, "ROLE_STUDENT"),
    WORKER(2, "ROLE_WORKER"),
    HEAD_WORKER(3, "ROLE_HEAD_WORKER");

    private String authority;

    private int databaseId;

    Role(int databaseId, String authority){
        this.databaseId = databaseId;
        this.authority = authority;
    }
    
    public Authority getAuthority(){
        return new Authority(databaseId, authority);
    }

    public String getValue(){
        return authority;
    }
}
