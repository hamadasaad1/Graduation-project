package com.ibnsaad.thedcc.Model.DiagnosisModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResponseGetSympotByBodyAreasId {
    @SerializedName("symptomId")
    @Expose
    private Integer symptomId;
    @SerializedName("symptomName")
    @Expose
    private String symptomName;

    public Integer getSymptomId() {
        return symptomId;
    }

    public void setSymptomId(Integer symptomId) {
        this.symptomId = symptomId;
    }

    public String getSymptomName() {
        return symptomName;
    }

    public void setSymptomName(String symptomName) {
        this.symptomName = symptomName;
    }
}
