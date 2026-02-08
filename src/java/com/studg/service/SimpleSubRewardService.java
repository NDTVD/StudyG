package com.studg.service;

/**
 * Rewards specifically for learning/subjects. Different distribution from Mystery Box.
 */
public class SimpleSubRewardService implements SubRewardService {

    @Override
    public String rewardForLearning() {
        return "play:1";       // very rare item reward
    }
}
