package orgm.androidtown.petlover;

import android.graphics.Bitmap;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
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
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageMetadata;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.R.id.message;

public class MainActivity extends AppCompatActivity {

    ImageView image;
    FirebaseDatabase database;
    DatabaseReference myRef;
    TextView text;
    UploadTask uploadTask ;
    StorageReference storageRef ;
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        Toast.makeText(getApplicationContext(), "경로를 save하였습니다. ",Toast.LENGTH_LONG).show();
        if(storageRef!=null){
            outState.putString("reference", storageRef.toString());
        }
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Toast.makeText(getApplicationContext(), "onRestoreInstanceState하였습니다. ",Toast.LENGTH_LONG).show();
        final String stringRef=savedInstanceState.getString("reference") ;
        if(stringRef==null){
            return ;
        }
        storageRef=FirebaseStorage.getInstance().getReferenceFromUrl(stringRef) ;
        Toast.makeText(getApplicationContext(), "upload 가 다시 진행중입니다. ",Toast.LENGTH_LONG).show();
        List tasks=storageRef.getActiveUploadTasks() ;
        if(tasks.size() >0){
            final UploadTask task= (UploadTask) tasks.get(0);

            task.addOnSuccessListener(this, new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    text.setText("upload 가 다시 진행해서 성공하였습니다.");
                }
            }) ;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FirebaseStorage storage = FirebaseStorage.getInstance();
        text = (TextView) findViewById(R.id.textView);
        image=(ImageView)findViewById(R.id.imageView) ;
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference();
        storageRef = storage.getReferenceFromUrl("gs://pet-lover-f5163.appspot.com");
        StorageReference imageref = storageRef.child("kaka/love.jpg");

  StorageReference gsreference=storage.getReferenceFromUrl("gs://pet-lover-f5163.appspot.com/kaka/20170408_135302.JPG");

        gsreference.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                text.setText("삭제됨");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                text.setText("uh oh! error occured!");
            }
        }) ;
//
//        try {
//            File localfile =File.createTempFile("images","jpg") ;
//            gsreference.getFile(localfile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
//                @Override
//                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
//                    text.setText("이미지를 읽어오는 것을 성공하였습니다. ");
//                    Glide.with(getApplicationContext()).using(new FirebaseImageLoader()).load(gsreference).into(image);
//                }
//            }).addOnFailureListener(new OnFailureListener() {
//                @Override
//                public void onFailure(@NonNull Exception e) {
//                    text.setText("이미지를 읽어오는 것을 실패하였습니다. ");
//                }
//            }) ;
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//
//        Uri file=Uri.fromFile(new File("/storage/emulated/0/Naver/2976787.JPG"));
//        StorageReference riversRef=imageref.child(file.getLastPathSegment()) ;
//        uploadTask=riversRef.putFile(file) ;
//        text.setText("준비중. ");
//uploadTask.addOnFailureListener(new OnFailureListener() {
//    @Override
//    public void onFailure(@NonNull Exception e) {
//        text.setText("업로드에 실패하였습니다. ");
//    }
//}).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
//    @Override
//    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//        text.setText("업로드에 성공하였습니다. ") ;
//        System.out.println(" "+taskSnapshot.getDownloadUrl().toString()) ;
//        taskSnapshot.getMetadata() ;
//    }
//}).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
//    @Override
//    public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
//      double progress=(100.0*taskSnapshot.getBytesTransferred())/taskSnapshot.getTotalByteCount() ;
//        text.setText("Upload is"+progress+"% done") ;
//    }
//});




//
//        InputStream stream=null;
//        try {
//            stream=new FileInputStream(new File("/storage/emulated/0/DCIM/CandyCam/IMG_20170407_210354.JPG")) ;
//            uploadTask=imageref.child(stream.toString()).putStream(stream)  ;
//            uploadTask.addOnFailureListener(new OnFailureListener() {
//                @Override
//                public void onFailure(@NonNull Exception e) {
//                    text.setText("업로드에 실패하였습니다. ");
//                }
//            }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
//                @Override
//                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//                    text.setText("업로드에 성공하였습니다. ") ;
//                    Uri downurl=taskSnapshot.getDownloadUrl() ;
//                }
//            }) ;
//
//        } catch (FileNotFoundException e) {
//            text.setText("업로드에 오류하였습니다. ") ;
//            e.printStackTrace();
//        }


    }

    public Map<String, Object> toMap(String a, String b) {
        HashMap<String, Object> result = new HashMap<>();
        result.put("email", a);
        result.put("username", b);
        return result;
    }

    public void writedata() {
        String key = "-Kghc_P1dGLJF8Hr_CJR";
        User user = new User("7", "hong7");
        Map<String, Object> childUpdates = new HashMap<>();
        Map<String, Object> postValues = toMap(user.getEmail(), user.getUsername());
        childUpdates.put("/users/" + key, postValues);
        myRef.updateChildren(childUpdates);
    }

    // private void writeNewUser( String uid,String name, String email) {
    //   Users user = new Users(name, email);
    //    myRef.child("users").child(uid).setValue(user);
    // }


}
