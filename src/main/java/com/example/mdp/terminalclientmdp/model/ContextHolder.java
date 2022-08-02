package com.example.mdp.terminalclientmdp.model;

import lombok.Data;

@Data
public class ContextHolder {

    private static ContextHolder instance;

    private String loggedInCheckpointID;

    private ContextHolder()
    {

    }

    public static ContextHolder getInstance()
    {
        if(instance==null)
        {
            instance = new ContextHolder();
        }
        return instance;
    }
}
