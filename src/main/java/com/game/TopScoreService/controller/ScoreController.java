package com.game.TopScoreService.controller;

import com.game.TopScoreService.enums.Status;
import com.game.TopScoreService.response.BaseResponse;
import com.game.TopScoreService.response.TopScoreResponse;
import com.game.TopScoreService.service.MessageQueue;
import com.game.TopScoreService.service.ScoreService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
public class ScoreController {

    private final ScoreService scoreService;
    @GetMapping("v1/top-score/{top}")
    BaseResponse<List<TopScoreResponse>> getTopScoreResponseKafka(@PathVariable int top){
        List<TopScoreResponse> list = scoreService.getTopScores(top);
        return new BaseResponse<List<TopScoreResponse>>(Status.SUCCESS,"Request Processed",list);
    }

}
