package com.game.TopScoreService;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.game.TopScoreService.dao.UserScoreDAO;
import com.game.TopScoreService.repo.UserScoreRepo;
import com.game.TopScoreService.response.TopScoreResponse;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.internal.matchers.Any;

import java.util.ArrayList;
import java.util.List;

public class UserScoreDaoTest {

    private UserScoreDAO userScoreDAO;

    @Mock
    private UserScoreRepo userScoreRepo;



    @Before
    void setUp() throws Exception {
        userScoreDAO = new UserScoreDAO(userScoreRepo);
    }

    @Test
    void testMultiply() {
         List<TopScoreResponse> topScoreResponse = new ArrayList<>();
        UserScoreRepo userScoreRepo = mock(UserScoreRepo.class);

       // when(userScoreRepo.getTopScores()).thenReturn(topScoreResponse);

        //userScoreDAO.getTopScores()

        //assertEquals( "Regular multiplication should work", userScoreDAO.getTopScores(3), 20);
    }
}
