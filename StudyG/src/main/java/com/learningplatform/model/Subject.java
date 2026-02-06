package com.learningplatform.model;

import java.io.Serializable;
import java.time.LocalDateTime;

public class Subject implements Serializable {
    private static final long serialVersionUID = 1L;

    private int subjectId;
    private String subjectName;
    private String description;
    private int points;
    private LocalDateTime createdAt;

    public Subject() {}

    public Subject(String subjectName, String description, int points) {
        this.subjectName = subjectName;
        this.description = description;
        this.points = points;
    }

    // Getters and Setters
    public int getSubjectId() { return subjectId; }
    public void setSubjectId(int subjectId) { this.subjectId = subjectId; }

    public String getSubjectName() { return subjectName; }
    public void setSubjectName(String subjectName) { this.subjectName = subjectName; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public int getPoints() { return points; }
    public void setPoints(int points) { this.points = points; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    @Override
    public String toString() {
        return "Subject{" +
                "subjectId=" + subjectId +
                ", subjectName='" + subjectName + '\'' +
                ", points=" + points +
                '}';
    }
}
