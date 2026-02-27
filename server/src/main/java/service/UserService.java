package service;

import dataaccess.AuthDao;
import dataaccess.DataAccessException;
import dataaccess.UserDao;
import model.AuthData;
import model.UserData;
import server.handlers.exceptions.AlreadyTakenException;
import server.handlers.exceptions.BadRequestException;
import server.handlers.exceptions.UnAuthorizedException;
import server.request.RegisterRequest;
import server.result.LogoutResult;
import server.result.RegisterResult;


public class UserService {
    private final AuthDao authDao;
    private final UserDao userDao;


    public UserService(AuthDao authDao, UserDao userDao){
        this.authDao = authDao;
        this.userDao = userDao;
    }
    public RegisterResult register(RegisterRequest request) throws BadRequestException, AlreadyTakenException, DataAccessException {
        String username = request.username();
        String password = request.password();
        String email = request.email();

        if (username==null || password==null || email==null){
            throw new BadRequestException("Error: bad request");
        }
        if (userDao.getUser(username) != null){
            throw new AlreadyTakenException("Error: already taken");
        }
        userDao.createUser(new UserData(username, password, email));
        AuthData auth = authDao.createAuth(username);
        return new RegisterResult(username, auth.authToken());

    }

    public void logout(String authToken) throws UnAuthorizedException, DataAccessException {
        if (authDao.getAuth(authToken) == null){
            throw new UnAuthorizedException(("Error: unauthorized"));
        }

        authDao.deleteAuth(authToken);
    }


}
