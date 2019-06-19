package com.ibnsaad.thedcc.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResponseMessagesWithId {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("senderId")
    @Expose
    private Integer senderId;
    @SerializedName("sender")
    @Expose
    private Object sender;
    @SerializedName("recipientId")
    @Expose
    private Integer recipientId;
    @SerializedName("recipient")
    @Expose
    private Object recipient;
    @SerializedName("content")
    @Expose
    private Object content;
    @SerializedName("isRead")
    @Expose
    private Boolean isRead;
    @SerializedName("dateRead")
    @Expose
    private Object dateRead;
    @SerializedName("messageSent")
    @Expose
    private String messageSent;
    @SerializedName("senderDeleted")
    @Expose
    private Boolean senderDeleted;
    @SerializedName("recipientDeleted")
    @Expose
    private Boolean recipientDeleted;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getSenderId() {
        return senderId;
    }

    public void setSenderId(Integer senderId) {
        this.senderId = senderId;
    }

    public Object getSender() {
        return sender;
    }

    public void setSender(Object sender) {
        this.sender = sender;
    }

    public Integer getRecipientId() {
        return recipientId;
    }

    public void setRecipientId(Integer recipientId) {
        this.recipientId = recipientId;
    }

    public Object getRecipient() {
        return recipient;
    }

    public void setRecipient(Object recipient) {
        this.recipient = recipient;
    }

    public Object getContent() {
        return content;
    }

    public void setContent(Object content) {
        this.content = content;
    }

    public Boolean getIsRead() {
        return isRead;
    }

    public void setIsRead(Boolean isRead) {
        this.isRead = isRead;
    }

    public Object getDateRead() {
        return dateRead;
    }

    public void setDateRead(Object dateRead) {
        this.dateRead = dateRead;
    }

    public String getMessageSent() {
        return messageSent;
    }

    public void setMessageSent(String messageSent) {
        this.messageSent = messageSent;
    }

    public Boolean getSenderDeleted() {
        return senderDeleted;
    }

    public void setSenderDeleted(Boolean senderDeleted) {
        this.senderDeleted = senderDeleted;
    }

    public Boolean getRecipientDeleted() {
        return recipientDeleted;
    }

    public void setRecipientDeleted(Boolean recipientDeleted) {
        this.recipientDeleted = recipientDeleted;
    }

}
