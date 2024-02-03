package com.example.mymdassistantf110645.database.model;

public class Vital {
    private double temperature;
    private int pulse;
    private String bloodPressure;
    private int bloodGlucose;
    private int respiratoryRate;
    private double weight;
    private double height;

    public Vital() {}

    public Vital(double temperature, int pulse, String bloodPressure, int bloodGlucose, int respiratoryRate, double weight, double height) {
        this.temperature = temperature;
        this.pulse = pulse;
        this.bloodPressure = bloodPressure;
        this.bloodGlucose = bloodGlucose;
        this.respiratoryRate = respiratoryRate;
        this.weight = weight;
        this.height = height;
    }

    public double getTemperature() {
        return temperature;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    public int getPulse() {
        return pulse;
    }

    public void setPulse(int pulse) {
        this.pulse = pulse;
    }

    public String getBloodPressure() {
        return bloodPressure;
    }

    public void setBloodPressure(String bloodPressure) {
        this.bloodPressure = bloodPressure;
    }

    public int getBloodGlucose() {
        return bloodGlucose;
    }

    public void setBloodGlucose(int bloodGlucose) {
        this.bloodGlucose = bloodGlucose;
    }

    public int getRespiratoryRate() {
        return respiratoryRate;
    }

    public void setRespiratoryRate(int respiratoryRate) {
        this.respiratoryRate = respiratoryRate;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }
}
