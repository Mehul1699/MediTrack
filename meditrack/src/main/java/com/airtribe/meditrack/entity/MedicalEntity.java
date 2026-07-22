package com.airtribe.meditrack.entity;

public abstract class MedicalEntity {

    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "MedicalEntity{" +
                "id=" + id +
                '}';
    }
}
