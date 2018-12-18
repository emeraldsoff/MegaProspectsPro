package inc.emeraldsoff.megaprospectspro;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import androidx.annotation.NonNull;
//import inc.emeraldsoff.megaprospectspro.appcontrol_ui.EntryActivity;
import inc.emeraldsoff.megaprospectspro.login.loginactivity;
import inc.emeraldsoff.megaprospectspro.login.user_regActivity;
//import inc.emeraldsoff.megaprospectspro.ui_data.MainActivity;

public class splash extends Activity {

    TextView brand;
    FirebaseAuth.AuthStateListener mAuthlistener;
    FirebaseAuth mAuth;
    String userid;
    private FirebaseFirestore fdb = FirebaseFirestore.getInstance();
    SharedPreferences mpref;
//    MainActivity x;

    @Override
    protected void onStart() {
        super.onStart();
        mAuth = FirebaseAuth.getInstance();
        userid = mAuth.getUid();
        int SPLASH_DISPLAY_LENGTH = 3000;
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mpref = getSharedPreferences("User", MODE_PRIVATE);
                final String fname, lname, mob;
                fname = mpref.getString("FirstName", "");
                lname = mpref.getString("LastName", "");
                mob = mpref.getString("MobileNo", "");
//                Logger.d("Start splash screen");
                mAuthlistener = new FirebaseAuth.AuthStateListener() {
                    @Override
                    public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                        FirebaseUser user = firebaseAuth.getCurrentUser();
                        if (user != null) {
                            userid = mAuth.getUid();
                            if (fname.equals("") || lname.equals("") || mob.equals("")) {
                                try {
                                    basic_check();
                                }catch (Exception e){
//                                    Crashlytics.getInstance();
//                                    Crashlytics.log(e.getMessage());
                                }
                            }
                            else {
//                                if(mpref.getBoolean("IF_SECURE",true)){
//                                    if(mpref.getString("PIN","").isEmpty()||mpref.getString("PIN","").equals("")){
////                                        startActivity(new Intent(splash.this, MainActivity.class));
//                                        finish();
//                                    }else{
////                                        startActivity(new Intent(splash.this, EntryActivity.class));
//                                        finish();
//                                    }
//                                }
//                                else{
                                    startActivity(new Intent(splash.this, MainActivity.class));
                                    finish();
//                                }
                            }
                        } else {
                            startActivity(new Intent(splash.this, loginactivity.class));
                            finish();
                        }
                    }
                };
                mAuth.addAuthStateListener(mAuthlistener);
            }
        }, SPLASH_DISPLAY_LENGTH);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        brand = findViewById(R.id.textView);
        Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/Harlow.ttf");
        brand.setTypeface(typeface);
        mpref = getSharedPreferences("User", MODE_PRIVATE);
    }

    private void datasync(){
        DocumentReference docref = fdb.document("prospect_users" + "/" + userid);
        docref.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot doc = task.getResult();
                    if (doc != null && doc.exists()) {
//                        Log.d(TAG, "DocumentSnapshot data: " + doc.getData());
//                                            StringBuilder uidf = new StringBuilder();
//                                            StringBuilder usrf = new StringBuilder();
//                                            StringBuilder validtime = new StringBuilder();
                        StringBuilder ufname = new StringBuilder();
                        StringBuilder umname = new StringBuilder();
                        StringBuilder ulname = new StringBuilder();
                        StringBuilder validitydate = new StringBuilder();
                        StringBuilder ifval = new StringBuilder();
                        StringBuilder user = new StringBuilder();
                        StringBuilder expire = new StringBuilder();
                        StringBuilder install = new StringBuilder();
                        StringBuilder uemail = new StringBuilder();
                        StringBuilder uphone = new StringBuilder();

                        validitydate.append(doc.get("ValidityDate"));
                        install.append(doc.get("InstallDate"));
                        expire.append(doc.get("ExpiryDate"));
                        ufname.append(doc.get("FirstName"));
                        umname.append(doc.get("MiddleName"));
                        ulname.append(doc.get("LastName"));
                        user.append(doc.get("user"));
                        uemail.append(doc.get("EmailId"));
                        uphone.append(doc.get("MobileNo"));
                        ifval.append(doc.getBoolean("IF_VALID"));

                        Boolean b = Boolean.parseBoolean(String.valueOf(ifval));

                        String pno = "+917003564171";
                        if (pno.equals(String.valueOf(uphone))) {
                            mpref = getSharedPreferences("User", MODE_PRIVATE);
                            SharedPreferences.Editor editor = mpref.edit();
                            editor.putString("userID", userid);
                            editor.putString("user", String.valueOf(user));
                            editor.putString("FirstName", String.valueOf(ufname));
                            editor.putString("MiddleName", String.valueOf(umname));
                            editor.putString("LastName", String.valueOf(ulname));
                            editor.putString("MobileNo", String.valueOf(uphone));
                            editor.putString("EmailId", String.valueOf(uemail));
                            editor.putString("ValidityDate", String.valueOf(validitydate));
                            editor.putString("InstallDate", String.valueOf(install));
                            editor.putString("ExpiryDate", String.valueOf(expire));
                            editor.putBoolean("IF_VALID", b);
                            editor.apply();
                        } else {
                            mpref = getSharedPreferences("User", MODE_PRIVATE);
                            SharedPreferences.Editor editor = mpref.edit();
                            editor.putString("userID", userid);
                            editor.putString("user", String.valueOf(user));
                            editor.putString("FirstName", String.valueOf(ufname));
                            editor.putString("MiddleName", String.valueOf(umname));
                            editor.putString("LastName", String.valueOf(ulname));
                            editor.putString("MobileNo", String.valueOf(uphone));
                            editor.putString("EmailId", String.valueOf(uemail));
                            editor.putString("ValidityDate", String.valueOf(validitydate));
                            editor.putString("InstallDate", String.valueOf(install));
                            editor.putString("ExpiryDate", String.valueOf(expire));
                            editor.putBoolean("IF_VALID", b);
                            editor.apply();
                        }
                    }
                }
            }
        });
    }

    private void basic_check(){
        datasync();
        mpref = getSharedPreferences("User", MODE_PRIVATE);
        final String fname, lname, mob;
        fname = mpref.getString("FirstName", "");
        lname = mpref.getString("LastName", "");
        mob = mpref.getString("MobileNo", "");
        if (fname.equals("") || lname.equals("") || mob.equals("")) {
            basic_check1();
        }
    }
    private void basic_check1(){
        datasync();
        mpref = getSharedPreferences("User", MODE_PRIVATE);
        final String fname, lname, mob;
        fname = mpref.getString("FirstName", "");
        lname = mpref.getString("LastName", "");
        mob = mpref.getString("MobileNo", "");
        if (fname.equals("") || lname.equals("") || mob.equals("")) {
            startActivity(new Intent(splash.this, user_regActivity.class));
        }
    }
}
