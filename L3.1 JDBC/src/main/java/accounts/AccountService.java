package accounts;

import dbService.DBException;
import dbService.DBService;

import java.util.HashMap;
import java.util.Map;

/**
 * @author v.chibrikov
 *         <p>
 *         Пример кода для курса на https://stepic.org/
 *         <p>
 *         Описание курса и лицензия: https://github.com/vitaly-chibrikov/stepic_java_webserver
 */
public class AccountService {
    private final DBService registeredUsersDataBase;
    private final Map<String, UserProfile> sessionIdToProfile;

    public AccountService() {
        registeredUsersDataBase = new DBService();
        sessionIdToProfile = new HashMap<>();
    }

    public void addNewUser(UserProfile userProfile) throws DBException {
        registeredUsersDataBase.addUser(userProfile);
    }

    public UserProfile getUserByLogin(String login) throws DBException {
        return registeredUsersDataBase.getUserByLogin(login);
    }

    public UserProfile getUserBySessionId(String sessionId) {
        return sessionIdToProfile.get(sessionId);
    }

    public void addSession(String sessionId, UserProfile userProfile) {
        sessionIdToProfile.put(sessionId, userProfile);
    }

    public void deleteSession(String sessionId) {
        sessionIdToProfile.remove(sessionId);
    }
}
