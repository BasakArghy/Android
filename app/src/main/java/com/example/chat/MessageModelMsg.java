package com.example.chat;

public class MessageModelMsg {
    private String msgId;
    private String senderId;
    private String message;
    private String time;

    public MessageModelMsg(String msgId, String senderId, String message, String time) {
        this.msgId = msgId;
        this.senderId = senderId;
        this.message = message;
        this.time = time;
    }



    public MessageModelMsg(String msgId, String senderId, String message) {
        this.msgId = msgId;
        this.senderId = senderId;
        this.message = message;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public MessageModelMsg() {
    }

    public String getMsgId() {
        return msgId;
    }

    public void setMsgId(String msgId) {
        this.msgId = msgId;
    }

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
