package com.friendlines.model;

public class Education extends Identifiable
{
    private String institution;
    private String type;

    public Education(){}

    public Education(String institution, String type){
        this.institution = institution;
        this.type = type;
    }

    public String getInstitution() {
        return institution;
    }

    public String getType() {
        return type;
    }

    public void setInstitution(String institution) {
        this.institution = institution;
    }

    public void setType(String type) {
        this.type = type;
    }
}