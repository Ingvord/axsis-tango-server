package com.github.ingvord.axsis;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Map;

public class AxsisMessage {
    public String ip;
    public String port;
    public String action;
    @JsonIgnore
    public String traceparent;
    public Map<String, Double> value;

    public AxsisMessage withIp(String v){
        this.ip = v;
        return this;
    }

    public AxsisMessage withPort(String v){
        this.port = v;
        return this;
    }

    public AxsisMessage withAction(String v){
        this.action = v;
        return this;
    }

    public AxsisMessage withValue(Map<String, Double> v){
        this.value = v;
        return this;
    }

    public AxsisMessage withTraceparent(String v) {
        this.traceparent =  v;
        return this;
    }
}
