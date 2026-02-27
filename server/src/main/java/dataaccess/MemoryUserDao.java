package dataaccess;
import model.UserData;
import java.util.HashMap;

public class MemoryUserDao implements UserDao{
    final private HashMap<String, UserData> users = new HashMap<>();

    public UserData getUser(String username){
        return users.get(username);
    }

    public void createUser(UserData userData) throws DataAccessException {
        if (users.get(userData.username()) != null){
            throw new DataAccessException("Username already exists");
        }
        else {
            users.put(userData.username(), userData);
        }
    }
    public void clearUsers() {
        users.clear();
    }

}
