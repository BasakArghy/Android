package com.example.chat;

public class MessageModel {
    private String msgId;
    private String senderId;
    private String message;
    private String time;
    private String receiverId;
    private String img;
    public MessageModel(String msgId, String senderId, String message, String time,String receiverId,String img) {
        this.msgId = msgId;
        this.senderId = senderId;
        this.message = message;
        this.time = time;
        this.receiverId=receiverId;
        this.img=img;
    }
    public MessageModel(String msgId, String senderId, String message, String time,String receiverId) {
        this.msgId = msgId;
        this.senderId = senderId;
        this.message = message;
        this.time = time;
        this.receiverId=receiverId;

    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(String receiverId) {
        this.receiverId = receiverId;
    }

    public MessageModel(String msgId, String senderId, String message) {
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

    public MessageModel() {
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
