package com.learningplatform.service;

import com.learningplatform.model.Test;

public interface ITestService {
    /**
     * Record test result
     */
    boolean recordTest(Test test) throws Exception;

    /**
     * Get test by test ID
     */
    Test getTestById(int testId) throws Exception;

    /**
     * Check if user passed test for a subject section
     */
    boolean hasUserPassedSection(int userId, int subjectId, int section) throws Exception;

    /**
     * Get total points from passed tests
     */
    int getTotalPointsFromTests(int userId) throws Exception;

    /**
     * Get number of game tickets earned from tests (100 points = 1 ticket)
     */
    int getTicketsFromPoints(int totalPoints) throws Exception;
}
