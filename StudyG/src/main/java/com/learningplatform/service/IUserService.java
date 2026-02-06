package com.learningplatform.service;

import com.learningplatform.model.User;

public interface IUserService {
    /**
     * Authenticate user with username and password
     */
    User authenticate(String username, String password) throws Exception;

    /**
     * Register a new user
     */
    boolean registerUser(User user) throws Exception;

    /**
     * Get user by username
     */
    User getUserByUsername(String username) throws Exception;

    /**
     * Get user by ID
     */
    User getUserById(int userId) throws Exception;

    /**
     * Update user profile
     */
    boolean updateUser(User user) throws Exception;

    /**
     * Add points to user
     */
    boolean addPoints(int userId, int points) throws Exception;

    /**
     * Add game tickets to user
     */
    boolean addGameTickets(int userId, int quantity) throws Exception;

    /**
     * Get user's total game tickets
     */
    int getGameTickets(int userId) throws Exception;
}
