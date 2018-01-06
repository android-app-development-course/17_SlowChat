package com.example.dell.slowchat.HttpReqeust;


/**
 * Created by dell on 2017/12/27.
 */



public class SendResult {
    private int code;
    private String msg;

    public SendResult(int id,String data) {
        this.code=id;
        this.msg = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String data) {
        this.msg = data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int id) {
        this.code = id;
    }
}
