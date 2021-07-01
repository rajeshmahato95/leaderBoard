package com.game.TopScoreService.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.game.TopScoreService.dao.UserDAO;
import com.game.TopScoreService.dao.UserScoreDAO;
import com.game.TopScoreService.model.ScoreEvent;
import com.game.TopScoreService.model.entity.UserScore;
import lombok.AllArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import static com.game.TopScoreService.util.Utility.getUserScore;

/**
 * This class acts a listener for kafka queue and updates user's scores
 */

@Service
@AllArgsConstructor
public class ScoreListener {

    private final ObjectMapper objectMapper;
    private final UserDAO userDAO;
    private final UserScoreDAO userScoreDAO;

    @KafkaListener(topics = "${kafka.score.topic}", groupId = "${kafka.score.group.id}")
    public void listenScoreEvents(String message) {
        System.out.println("Received Message: " + message);
        try {
            ScoreEvent scoreEvent = objectMapper.readValue(message, ScoreEvent.class);
            if(validateScoreEvent(scoreEvent)) {
                UserScore userScore = getUserScore(scoreEvent);
                userScoreDAO.saveScore(userScore);
                System.out.println("Saved Successfully for user "+userScore);
            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private boolean validateScoreEvent(ScoreEvent scoreEvent) {
        if(scoreEvent==null){
            return false;
        }
        return scoreEvent.getScore()>0 && userDAO.getUser(scoreEvent.getUserId())!=null;
    }

}
