package com.example.covtest.entities;

import com.google.gson.annotations.SerializedName;

public class TestReport {
    @SerializedName("Fever")
    int fever;
    @SerializedName("Tiredness")
    int tiredness;
    @SerializedName("Dry-Cough")
    int dry_Cough;
    @SerializedName("Difficulty-in-Breathing")
    int difficulty_in_Breathing;
    @SerializedName("Sore-Throat")
    int sore_Throat;
    @SerializedName("None_Sympton")
    int none_Sympton;
    @SerializedName("Pains")
    int pains;
    @SerializedName("Nasal-Congestion")
    int nasal_Congestion;
    @SerializedName("Runny-Nose")
    int runny_Nose;
    @SerializedName("Diarrhea")
    int diarrhea;

    public TestReport(int fever, int tiredness, int dry_Cough, int difficulty_in_Breathing, int sore_Throat, int none_Sympton, int pains, int nasal_Congestion, int runny_Nose, int diarrhea) {
        this.fever = fever;
        this.tiredness = tiredness;
        this.dry_Cough = dry_Cough;
        this.difficulty_in_Breathing = difficulty_in_Breathing;
        this.sore_Throat = sore_Throat;
        this.none_Sympton = none_Sympton;
        this.pains = pains;
        this.nasal_Congestion = nasal_Congestion;
        this.runny_Nose = runny_Nose;
        this.diarrhea = diarrhea;
    }

    public int getFever() {
        return fever;
    }

    public void setFever(int fever) {
        this.fever = fever;
    }

    public int getTiredness() {
        return tiredness;
    }

    public void setTiredness(int tiredness) {
        this.tiredness = tiredness;
    }

    public int getDry_Cough() {
        return dry_Cough;
    }

    public void setDry_Cough(int dry_Cough) {
        this.dry_Cough = dry_Cough;
    }

    public int getDifficulty_in_Breathing() {
        return difficulty_in_Breathing;
    }

    public void setDifficulty_in_Breathing(int difficulty_in_Breathing) {
        this.difficulty_in_Breathing = difficulty_in_Breathing;
    }

    public int getSore_Throat() {
        return sore_Throat;
    }

    public void setSore_Throat(int sore_Throat) {
        this.sore_Throat = sore_Throat;
    }

    public int getNone_Sympton() {
        return none_Sympton;
    }

    public void setNone_Sympton(int none_Sympton) {
        this.none_Sympton = none_Sympton;
    }

    public int getPains() {
        return pains;
    }

    public void setPains(int pains) {
        this.pains = pains;
    }

    public int getNasal_Congestion() {
        return nasal_Congestion;
    }

    public void setNasal_Congestion(int nasal_Congestion) {
        this.nasal_Congestion = nasal_Congestion;
    }

    public int getRunny_Nose() {
        return runny_Nose;
    }

    public void setRunny_Nose(int runny_Nose) {
        this.runny_Nose = runny_Nose;
    }

    public int getDiarrhea() {
        return diarrhea;
    }

    public void setDiarrhea(int diarrhea) {
        this.diarrhea = diarrhea;
    }
}
