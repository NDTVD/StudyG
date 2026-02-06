package com.learningplatform.model;

import java.io.Serializable;

public class Test implements Serializable {
    private static final long serialVersionUID = 1L;

    private int testId;
    private int userId;
    private int subjectId;
    private int section;
    private int score;
    private boolean passed;
    private String testDate;

    public Test() {}

    public Test(int userId, int subjectId, int section, int score, boolean passed) {
        this.userId = userId;
        this.subjectId = subjectId;
        this.section = section;
        this.score = score;
        this.passed = passed;
    }

    // Getters and Setters
    public int getTestId() { return testId; }
    public void setTestId(int testId) { this.testId = testId; }

    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }

    public int getSubjectId() { return subjectId; }
    public void setSubjectId(int subjectId) { this.subjectId = subjectId; }

    public int getSection() { return section; }
    public void setSection(int section) { this.section = section; }

    public int getScore() { return score; }
    public void setScore(int score) { this.score = score; }

    public boolean isPassed() { return passed; }
    public void setPassed(boolean passed) { this.passed = passed; }

    public String getTestDate() { return testDate; }
    public void setTestDate(String testDate) { this.testDate = testDate; }

    @Override
    public String toString() {
        return "Test{" +
                "testId=" + testId +
                ", userId=" + userId +
                ", subjectId=" + subjectId +
                ", score=" + score +
                ", passed=" + passed +
                '}';
    }
}
