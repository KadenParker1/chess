package service;


import dataaccess.AuthDao;
import dataaccess.DataAccessException;
import dataaccess.UserDao;
import dataaccess.GameDao;
import model.*;


public class ClearService {
    private final AuthDao authDao;
    private final UserDao userDao;
    private final GameDao gameDao;



    public ClearService(AuthDao authDao, UserDao userDao, GameDao gameDao) {
        this.authDao = authDao;
        this.gameDao = gameDao;
        this.userDao = userDao;
    }


    public void clearApplication() throws DataAccessException {
        authDao.clearAuths();
        gameDao.clearGames();
        userDao.clearUsers();
    }


}
