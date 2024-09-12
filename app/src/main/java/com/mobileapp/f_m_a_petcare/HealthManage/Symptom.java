package com.mobileapp.f_m_a_petcare.HealthManage;

import java.io.Serializable;

public class Symptom implements Serializable {
    private int imageResId;
    private String title;
    private String shortDescription;
    private String cause;
    private String diagnosis;
    private String solution;
    private String url;  // Thêm thuộc tính url

    public Symptom(int imageResId, String title, String shortDescription, String cause, String diagnosis, String solution, String url) {
        this.imageResId = imageResId;
        this.title = title;
        this.shortDescription = shortDescription;
        this.cause = cause;
        this.diagnosis = diagnosis;
        this.solution = solution;
        this.url = url;  // Khởi tạo thuộc tính url
    }

    // Getters
    public int getImageResId() { return imageResId; }
    public String getTitle() { return title; }
    public String getShortDescription() { return shortDescription; }
    public String getCause() { return cause; }
    public String getDiagnosis() { return diagnosis; }
    public String getSolution() { return solution; }
    public String getUrl() { return url; }  // Getter cho url
}
