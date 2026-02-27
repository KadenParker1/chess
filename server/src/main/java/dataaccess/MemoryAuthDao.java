package dataaccess;

import io.javalin.http.UnauthorizedResponse;
import model.AuthData;
import java.util.HashMap;
import java.util.UUID;

public class MemoryAuthDao implements AuthDao {
    final private HashMap<String, AuthData> auths = new HashMap<>();

    public static String generateToken() {
        return UUID.randomUUID().toString();
    }

    public AuthData createAuth(String username) {
        String authToken = generateToken();
        AuthData auth = new AuthData(authToken, username);
        auths.put(authToken, auth);
        return auth;
    }

    public AuthData getAuth(String authToken) {
        return auths.get(authToken);
    }

    public void deleteAuth(String AuthToken){
        auths.remove(AuthToken);
    }

    public void clearAuths(){
        auths.clear();
    }

}
