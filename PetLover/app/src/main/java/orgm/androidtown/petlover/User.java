package orgm.androidtown.petlover;

import com.google.firebase.database.IgnoreExtraProperties;

/*** Created by InKyung on 2017-04-02.
 */

public class User {

    private String username;
    private String email;

    public User() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }
    public User( String username, String email) {
        this.username = username;
        this.email = email;
    }
    public String getUsername() {
        return username;
    }
    public String getEmail() {
        return email;
    }
}