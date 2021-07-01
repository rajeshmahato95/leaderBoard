package com.game.TopScoreService.repo;

import com.game.TopScoreService.model.entity.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepo extends CrudRepository<User,Integer> {
}
