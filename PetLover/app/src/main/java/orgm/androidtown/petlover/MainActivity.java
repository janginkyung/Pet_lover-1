package orgm.androidtown.petlover;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.IgnoreExtraProperties;

public class MainActivity extends AppCompatActivity {

    FirebaseDatabase database;
    DatabaseReference myRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // https://firebase.google.com/docs/database/android/save-data

        // Write a message to the database
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference();
        writeNewUser( "jang" ,"dkwmzk1016@naver.com");
    }

    @IgnoreExtraProperties
    public class Users {

        public String username;
        public String email;

        public Users() {
            // Default constructor required for calls to DataSnapshot.getValue(User.class)
        }

        public Users(String username, String email) {
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
    private void writeNewUser( String name, String email) {
        Users user = new Users(name, email);

        myRef.push().setValue(user);
    }
}
