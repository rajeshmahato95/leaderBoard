package com.game.TopScoreService.dao;

import com.game.TopScoreService.model.entity.User;
import com.game.TopScoreService.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Component;

@Component
public class UserDAO {

    @Autowired
    private UserRepo userRepo;
    @Autowired
    private CacheManager cacheManager;

    public User getUser(int id){
        Cache cache = cacheManager.getCache("user-cache");
        User user = cache.get(id,User.class);
        if(user==null){
            user = userRepo.findById(id).orElse(null);
        }
        if(user!=null){
            cache.put(id,user);
        }
        return user;
    }
    public User saveUser(User user){
        Cache cache = cacheManager.getCache("user-cache");

        user = userRepo.save(user);
        cache.put(user.getId(),user);
        return user;
    }

}
