package com.game.TopScoreService.dao;

import com.game.TopScoreService.model.entity.User;
import com.game.TopScoreService.model.entity.UserScore;
import com.game.TopScoreService.repo.UserRepo;
import com.game.TopScoreService.repo.UserScoreRepo;
import com.game.TopScoreService.response.TopScoreResponse;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class UserScoreDAO {

    private UserScoreRepo userScoreRepo;

    @Autowired
    public UserScoreDAO(UserScoreRepo userScoreRepo){
        this.userScoreRepo = userScoreRepo;
    }
    private Map<Integer,TopScoreResponse> map = new HashMap<>();
    private PriorityQueue<TopScoreResponse> queue  = new PriorityQueue<>((a,b)->{
        if(a.getTotalScore()==b.getTotalScore()){
            return a.getUserId()-b.getUserId();
        }
        if(a.getTotalScore()>b.getTotalScore()){
            return 1;
        }else{
            return -1;
        }
    });
    private static final int totalSize = 100;

    @PostConstruct
    void updateQueue(){
        List<UserScore> userScoreList = (List<UserScore>) userScoreRepo.findTop100AllByOrderByScoreDesc();

        List<TopScoreResponse> topScoreResponseList = userScoreList.stream().map(userScore ->
                new TopScoreResponse(userScore.getUserId(),userScore.getScore())).collect(Collectors.toList());

        //int size = topScoreResponseList.size()>100?100:topScoreResponseList.size();
        if(topScoreResponseList.size()>100)
            topScoreResponseList = topScoreResponseList.subList(0,99);
        for(TopScoreResponse topScoreResponse : topScoreResponseList) {
            boolean foundInMap = map.containsKey(topScoreResponse.getUserId());
            if(foundInMap){
                topScoreResponse = map.get(topScoreResponse.getUserId());
                topScoreResponse.setTotalScore(topScoreResponse.getTotalScore()+topScoreResponse.getTotalScore());
                queue.remove(topScoreResponse);
            }else{
                map.put(topScoreResponse.getUserId(),topScoreResponse);
            }
            if(queue.size()<totalSize){
                queue.add(topScoreResponse);
            }else{
                if(queue.peek().getTotalScore()<topScoreResponse.getTotalScore()) {
                    queue.add(topScoreResponse);
                }
            }
        }
    }

    public void saveScore(UserScore userScore){
        //add in queue
        addInQueue(userScore);
        userScoreRepo.save(userScore);
    }

    private void addInQueue(UserScore userScore) {
        boolean foundInMap = map.containsKey(userScore.getUserId());
        TopScoreResponse topScoreResponse;
        if(foundInMap){
            topScoreResponse = map.get(userScore.getUserId());
            topScoreResponse.setTotalScore(topScoreResponse.getTotalScore()+userScore.getScore());
            queue.remove(topScoreResponse);
        }else{
            topScoreResponse = new TopScoreResponse(userScore.getUserId(),userScore.getScore());
            map.put(userScore.getUserId(),topScoreResponse);
        }

        if(queue.size()<totalSize){
          queue.add(topScoreResponse);
        }else{
            if(queue.peek().getTotalScore()<topScoreResponse.getTotalScore()) {
                queue.add(topScoreResponse);
            }
        }

    }

    public List<TopScoreResponse> getTopScores(int top) {
        List<TopScoreResponse> list = new ArrayList<>();
        Iterator<TopScoreResponse> iterator = queue.iterator();
        while(iterator.hasNext()){
            list.add(iterator.next());
        }
        list.sort((a,b)->{
            if(a.getTotalScore()==b.getTotalScore()){
                return a.getUserId()-b.getUserId();
            }
            if(a.getTotalScore()>b.getTotalScore()){
                return -1;
            }else{
                return 1;
            }
        });
        if(list.size()<top){
            return list;
        }
        return list.subList(0,top);
    }
}
