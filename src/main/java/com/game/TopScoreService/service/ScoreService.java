package com.game.TopScoreService.service;

import com.game.TopScoreService.dao.UserScoreDAO;
import com.game.TopScoreService.repo.UserScoreRepo;
import com.game.TopScoreService.response.TopScoreResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
/**
 * This class is basically for getting top scorers
 */
@Service
@AllArgsConstructor
public class ScoreService {
    private final UserScoreDAO userScoreDAO;
    public List<TopScoreResponse> getTopScores(int top){
        return userScoreDAO.getTopScores(top);
    }
}
