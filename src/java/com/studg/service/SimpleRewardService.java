package com.studg.service;

import java.util.Random;

public class SimpleRewardService implements RewardService {
    private final Random rnd = new Random();

    // 16 boxes, each box has its own probability distribution for four outcomes:
    // 0 - points:50, 1 - play:1, 2 - play:2, 3 - item:rare_profile_sticker
    // probabilities are expressed as percentages and must sum to <=100; remaining is treated as "no reward" (rare).
    private static final int[][] BOX_PROB = new int[][]{
            {60,25,5,10}, {55,30,5,10}, {50,30,10,10}, {40,40,10,10},
            {65,20,5,10}, {60,25,5,10}, {45,35,10,10}, {30,50,10,10},
            {70,15,5,10}, {50,35,5,10}, {55,30,5,10}, {60,20,10,10},
            {40,40,10,10}, {35,45,10,10}, {80,10,0,10}, {20,60,10,10}
    };

    @Override
    public String openMysteryBox() {
        // default fallback: random simple box
        return openBox(0);
    }

    public int getBoxCount() { return BOX_PROB.length; }

    public String openBox(int boxIndex) {
        if (boxIndex < 0 || boxIndex >= BOX_PROB.length) boxIndex = 0;
        int[] probs = BOX_PROB[boxIndex];
        // jackpot with probability 1/1000
        int r1000 = rnd.nextInt(1000);
        if (r1000 == 0) return "play:100";
        // otherwise use 0-99 percent scale
        int r = rnd.nextInt(100);
        int cum = 0;
        cum += probs[0]; if (r < cum) return "points:50";
        cum += probs[1]; if (r < cum) return "play:1";
        cum += probs[2]; if (r < cum) return "play:2";
        cum += probs[3]; if (r < cum) return "item:rare_profile_sticker";
        return "none:nothing";
    }

    /**
     * Open box without allowing jackpot. Uses percent scale 0-99.
     */
    public String openBoxNoJackpot(int boxIndex) {
        if (boxIndex < 0 || boxIndex >= BOX_PROB.length) boxIndex = 0;
        int[] probs = BOX_PROB[boxIndex];
        int r = rnd.nextInt(100);
        int cum = 0;
        cum += probs[0]; if (r < cum) return "points:50";
        cum += probs[1]; if (r < cum) return "play:1";
        cum += probs[2]; if (r < cum) return "play:2";
        cum += probs[3]; if (r < cum) return "item:rare_profile_sticker";
        return "none:nothing";
    }
}
