package com.tictactoe;

import java.util.Date;
import java.util.List;

import javax.inject.Named;
import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;

@Api(name = "tictactoe")
public class ScoreEndpoint {
  @ApiMethod(name = "scores.get")
  public Score get(@Named("key") String key) {
    Key k = KeyFactory.createKey(Score.class.getSimpleName(), key);
    PersistenceManager pm = getPersistenceManager();
    Score score = pm.getObjectById(Score.class, k);
    pm.close();
    return score;
  }
  
  @ApiMethod(name = "scores.list")
  @SuppressWarnings("unchecked")
  public List<Score> list() {
    PersistenceManager pm = getPersistenceManager();
    Query query = pm.newQuery(Score.class);
    return (List<Score>) pm.newQuery(query).execute();
  }
  
  @ApiMethod(name = "scores.insert")
  public Score insert(Score score) {
    score.setPlayed(new Date());
    PersistenceManager pm = getPersistenceManager();
    pm.makePersistent(score);
    pm.close();
    return score;
  }
  
  private static PersistenceManager getPersistenceManager() {
    return PMF.get().getPersistenceManager();
  }
}
