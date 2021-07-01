package com.game.TopScoreService.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.game.TopScoreService.dao.UserDAO;
import com.game.TopScoreService.enums.Status;
import com.game.TopScoreService.model.ScoreEvent;
import com.game.TopScoreService.model.entity.User;
import com.game.TopScoreService.request.UserSaveRequest;
import com.game.TopScoreService.response.BaseResponse;
import com.game.TopScoreService.service.MessageQueue;
import com.game.TopScoreService.service.ScoreListener;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@AllArgsConstructor
public class UserController {

    @Autowired
    private ScoreListener scoreListener;

    private final UserDAO userDAO;
    private final MessageQueue messageQueue;

    @PostMapping("v1/user")
    public BaseResponse<String> saveUser(@RequestBody UserSaveRequest userSaveRequest){
        User user = userDAO.saveUser(new User(userSaveRequest.getUserId(),userSaveRequest.getUserName()));
        return new BaseResponse<>(Status.SUCCESS,"User Created Successfully with id: "+user.getId(),"");
    }

    @PostMapping("v1/score")
    public String createScoreEvent(@RequestBody List<ScoreEvent> scoreEventList) throws Exception{

        for(ScoreEvent scoreEvent1:scoreEventList) {
            String string = new ObjectMapper().writeValueAsString(scoreEvent1);
            messageQueue.sendMessage(string);
        }
        return "success";
    }

    @PostMapping("v2/score")
    public String createScoreEventDB(@RequestBody List<ScoreEvent> scoreEventList) throws Exception{

        for(ScoreEvent scoreEvent1:scoreEventList) {
            String string = new ObjectMapper().writeValueAsString(scoreEvent1);
            scoreListener.listenScoreEvents(string);
        }
        return "success";
    }
}
