package com.ibnsaad.thedcc.Model.DiagnosisModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResponseBulletin {

    @SerializedName("treatmentBulletinId")
    @Expose
    private Integer treatmentBulletinId;
    @SerializedName("drugName")
    @Expose
    private String drugName;
    @SerializedName("composition")
    @Expose
    private String composition;
    @SerializedName("indications")
    @Expose
    private String indications;
    @SerializedName("dosing")
    @Expose
    private String dosing;
    @SerializedName("sideEffects")
    @Expose
    private String sideEffects;

    public Integer getTreatmentBulletinId() {
        return treatmentBulletinId;
    }

    public void setTreatmentBulletinId(Integer treatmentBulletinId) {
        this.treatmentBulletinId = treatmentBulletinId;
    }

    public String getDrugName() {
        return drugName;
    }

    public void setDrugName(String drugName) {
        this.drugName = drugName;
    }

    public String getComposition() {
        return composition;
    }

    public void setComposition(String composition) {
        this.composition = composition;
    }

    public String getIndications() {
        return indications;
    }

    public void setIndications(String indications) {
        this.indications = indications;
    }

    public String getDosing() {
        return dosing;
    }

    public void setDosing(String dosing) {
        this.dosing = dosing;
    }

    public String getSideEffects() {
        return sideEffects;
    }

    public void setSideEffects(String sideEffects) {
        this.sideEffects = sideEffects;
    }
}
