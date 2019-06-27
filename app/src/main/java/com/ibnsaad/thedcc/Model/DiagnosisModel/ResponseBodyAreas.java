package com.ibnsaad.thedcc.Model.DiagnosisModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResponseBodyAreas {

    @SerializedName("bodyAreasId")
    @Expose
    private Integer bodyAreasId;
    @SerializedName("nameArea")
    @Expose
    private String nameArea;
    @SerializedName("symptoms")
    @Expose
    private Object symptoms;

    public ResponseBodyAreas() {
    }

    public ResponseBodyAreas(Integer bodyAreasId, String nameArea) {
        this.bodyAreasId = bodyAreasId;
        this.nameArea = nameArea;
    }


    public Integer getBodyAreasId() {
        return bodyAreasId;
    }

    public void setBodyAreasId(Integer bodyAreasId) {
        this.bodyAreasId = bodyAreasId;
    }

    public String getNameArea() {
        return nameArea;
    }

    public void setNameArea(String nameArea) {
        this.nameArea = nameArea;
    }

    public Object getSymptoms() {
        return symptoms;
    }

    public void setSymptoms(Object symptoms) {
        this.symptoms = symptoms;
    }

}
