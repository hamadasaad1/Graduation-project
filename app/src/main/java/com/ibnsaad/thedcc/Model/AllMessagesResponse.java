package com.ibnsaad.thedcc.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AllMessagesResponse {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("senderId")
    @Expose
    private Integer senderId;
    @SerializedName("senderKnownAs")
    @Expose
    private String senderKnownAs;
    @SerializedName("senderPhotoUrl")
    @Expose
    private Object senderPhotoUrl;
    @SerializedName("recipientPhotoUrl")
    @Expose
    private String recipientPhotoUrl;
    @SerializedName("recipientId")
    @Expose
    private Integer recipientId;
    @SerializedName("recipientKnownAs")
    @Expose
    private String recipientKnownAs;
    @SerializedName("content")
    @Expose
    private String content;
    @SerializedName("isRead")
    @Expose
    private Boolean isRead;
    @SerializedName("dateRead")
    @Expose
    private Object dateRead;
    @SerializedName("messageSent")
    @Expose
    private String messageSent;

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

    public String getSenderKnownAs() {
        return senderKnownAs;
    }

    public void setSenderKnownAs(String senderKnownAs) {
        this.senderKnownAs = senderKnownAs;
    }

    public Object getSenderPhotoUrl() {
        return senderPhotoUrl;
    }

    public void setSenderPhotoUrl(Object senderPhotoUrl) {
        this.senderPhotoUrl = senderPhotoUrl;
    }

    public String getRecipientPhotoUrl() {
        return recipientPhotoUrl;
    }

    public void setRecipientPhotoUrl(String recipientPhotoUrl) {
        this.recipientPhotoUrl = recipientPhotoUrl;
    }

    public Integer getRecipientId() {
        return recipientId;
    }

    public void setRecipientId(Integer recipientId) {
        this.recipientId = recipientId;
    }

    public String getRecipientKnownAs() {
        return recipientKnownAs;
    }

    public void setRecipientKnownAs(String recipientKnownAs) {
        this.recipientKnownAs = recipientKnownAs;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
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
}
