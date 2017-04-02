package orgm.androidtown.petlover;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.IgnoreExtraProperties;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Query;
import com.google.firebase.database.Transaction;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

import static android.R.id.message;

public class MainActivity extends AppCompatActivity {

    FirebaseDatabase database;
    DatabaseReference myRef;
    DatabaseReference testref ;
    TextView text ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // https://firebase.google.com/docs/database/android/save-data
        text=(TextView)findViewById(R.id.textView)  ;

        database = FirebaseDatabase.getInstance();
        myRef=database.getReference() ;
        String put="second day1" ;
        myRef.setValue(put) ;

        writeNewUser("123","hong","ji") ;
       //writedata() ;
       // Query first=myRef.orderByChild("uid") ;
    }
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("uid", "kkk");
        return result;
    }
public void writedata(){
    String key=myRef.push().getKey() ;
    Map<String, Object> childUpdates = new HashMap<>();
    Map<String, Object> postValues=toMap() ;
    childUpdates.put(key, postValues) ;
    myRef.updateChildren(childUpdates) ;
}
    @IgnoreExtraProperties
    public class Users {

        public String username;
        public String email;

        public Users() {
            // Default constructor required for calls to DataSnapshot.getValue(User.class)
        }

        public Users( String username, String email) {
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

    private void writeNewUser( String uid,String name, String email) {
        Users user = new Users(name, email);
        myRef.child("users").child(uid).setValue(user);
    }



}
