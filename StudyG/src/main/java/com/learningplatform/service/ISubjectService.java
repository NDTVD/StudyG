package com.learningplatform.service;

import com.learningplatform.model.Subject;
import java.util.List;

public interface ISubjectService {
    /**
     * Get all available subjects
     */
    List<Subject> getAllSubjects() throws Exception;

    /**
     * Get subject by ID
     */
    Subject getSubjectById(int subjectId) throws Exception;

    /**
     * Get list of subjects the user has completed
     */
    List<Subject> getCompletedSubjects(int userId) throws Exception;

    /**
     * Get list of available subjects for user (not completed yet)
     */
    List<Subject> getAvailableSubjects(int userId) throws Exception;

    /**
     * Add subject completion record
     */
    boolean markSubjectComplete(int userId, int subjectId) throws Exception;

    /**
     * Check if user completed a subject
     */
    boolean isSubjectCompleted(int userId, int subjectId) throws Exception;

    /**
     * Get random subject from learned subjects for weekly review
     */
    Subject getRandomLearnedSubject(int userId) throws Exception;
}
