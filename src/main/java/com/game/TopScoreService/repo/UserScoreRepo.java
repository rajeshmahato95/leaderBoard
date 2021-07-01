package com.game.TopScoreService.repo;

import com.game.TopScoreService.model.entity.User;
import com.game.TopScoreService.model.entity.UserScore;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UserScoreRepo extends CrudRepository<UserScore,Integer> {

    @Query("select us.userId,SUM(us.score) as ts from UserScore as us group by userId order by ts desc")
    public List<Object[]> getTopScores();

    List<UserScore> findTop100AllByOrderByScoreDesc();

}
