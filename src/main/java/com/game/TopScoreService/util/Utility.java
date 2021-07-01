package com.game.TopScoreService.util;

import com.game.TopScoreService.model.ScoreEvent;
import com.game.TopScoreService.model.entity.User;
import com.game.TopScoreService.model.entity.UserScore;

import java.util.Date;

public class Utility {
    public static UserScore getUserScore(ScoreEvent scoreEvent){
        UserScore userScore = new UserScore();
        userScore.setUserId(scoreEvent.getUserId());
        userScore.setScore(scoreEvent.getScore());
        userScore.setCreatedAt(new Date());
        return userScore;
    }
}
