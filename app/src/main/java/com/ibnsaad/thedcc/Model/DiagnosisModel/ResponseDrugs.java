package com.ibnsaad.thedcc.Model.DiagnosisModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResponseDrugs {

    @SerializedName("drugId")
    @Expose
    private Integer drugId;
    @SerializedName("drugName")
    @Expose
    private String drugName;
    @SerializedName("treatmentBulletin")
    @Expose
    private Object treatmentBulletin;


    public Integer getDrugId() {
        return drugId;
    }

    public void setDrugId(Integer drugId) {
        this.drugId = drugId;
    }

    public String getDrugName() {
        return drugName;
    }

    public void setDrugName(String drugName) {
        this.drugName = drugName;
    }

    public Object getTreatmentBulletin() {
        return treatmentBulletin;
    }

    public void setTreatmentBulletin(Object treatmentBulletin) {
        this.treatmentBulletin = treatmentBulletin;
    }
}
