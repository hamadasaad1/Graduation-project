package com.ibnsaad.thedcc.Model.DiagnosisModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResponseUserHistory {
    @SerializedName("drugName")
    @Expose
    private String drugName;
    @SerializedName("requestId")
    @Expose
    private Integer requestId;
    @SerializedName("bodyAreasName")
    @Expose
    private String bodyAreasName;
    @SerializedName("symptomName")
    @Expose
    private String symptomName;
    @SerializedName("timeCreated")
    @Expose
    private String timeCreated;

    public String getDrugName() {
        return drugName;
    }

    public void setDrugName(String drugName) {
        this.drugName = drugName;
    }

    public Integer getRequestId() {
        return requestId;
    }

    public void setRequestId(Integer requestId) {
        this.requestId = requestId;
    }

    public String getBodyAreasName() {
        return bodyAreasName;
    }

    public void setBodyAreasName(String bodyAreasName) {
        this.bodyAreasName = bodyAreasName;
    }

    public String getSymptomName() {
        return symptomName;
    }

    public void setSymptomName(String symptomName) {
        this.symptomName = symptomName;
    }

    public String getTimeCreated() {
        return timeCreated;
    }

    public void setTimeCreated(String timeCreated) {
        this.timeCreated = timeCreated;
    }
}
