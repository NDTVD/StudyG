package com.learningplatform.service;

public interface IGameService {
    /**
     * Types of rewards from Mystery Box
     */
    enum RewardType {
        POINTS,
        GAME_TICKET,
        RARE_ITEM
    }

    /**
     * Represents a reward from the game
     */
    class GameReward {
        public RewardType rewardType;
        public int amount; // points or ticket quantity
        public String itemName; // for rare items
        public String message;

        public GameReward(RewardType type, int amount, String message) {
            this.rewardType = type;
            this.amount = amount;
            this.message = message;
        }
    }

    /**
     * Play the Mystery Box game
     * Returns reward based on random selection
     */
    GameReward playMysteryBox(int userId) throws Exception;

    /**
     * Check if user has game tickets available
     */
    boolean hasGameTickets(int userId) throws Exception;

    /**
     * Deduct a game ticket from user
     */
    boolean deductGameTicket(int userId) throws Exception;

    /**
     * Get random reward from the mystery box
     */
    GameReward getRandomReward() throws Exception;
}
