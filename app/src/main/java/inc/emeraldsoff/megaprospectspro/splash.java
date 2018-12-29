package inc.emeraldsoff.megaprospectspro;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
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

import java.util.Objects;

import androidx.annotation.NonNull;
import inc.emeraldsoff.megaprospectspro.appcontrol_ui.activity_entry;
import inc.emeraldsoff.megaprospectspro.login.activity_login;
import inc.emeraldsoff.megaprospectspro.login.activity_user_reg;
import inc.emeraldsoff.megaprospectspro.ui_data.fragment_Home.activity_home;

//import inc.emeraldsoff.megaprospectspro.appcontrol_ui.activity_entry;
//import inc.emeraldsoff.megaprospectspro.ui_data.activity_main;

public class splash extends Activity {

    private Context mcontext;
    TextView brand;
    FirebaseAuth.AuthStateListener mAuthlistener;
    FirebaseAuth mAuth;
    String userid;
    private FirebaseFirestore fdb = FirebaseFirestore.getInstance();
    SharedPreferences mpref;
//    activity_main x;

    @Override
    protected void onStart() {
        super.onStart();
        if (isOnline()) {

            check_auth();

        } else {

            recheck_network();

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        brand = findViewById(R.id.textView);
        Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/Harlow.ttf");
        brand.setTypeface(typeface);
        mpref = getSharedPreferences("User", MODE_PRIVATE);
        mcontext = this;
    }

    private void datasync() {
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

    private void basic_check() {
        datasync();
        mpref = getSharedPreferences("User", MODE_PRIVATE);
        final String fname, lname, mob;
        fname = mpref.getString("FirstName", "");
        lname = mpref.getString("LastName", "");
        mob = mpref.getString("MobileNo", "");
        if (Objects.requireNonNull(fname).equals("") ||
                Objects.requireNonNull(lname).equals("") ||
                Objects.requireNonNull(mob).equals("")) {
            basic_check1();
        }
    }

    private void basic_check1() {
        datasync();
        mpref = getSharedPreferences("User", MODE_PRIVATE);
        final String fname, lname, mob;
        fname = mpref.getString("FirstName", "");
        lname = mpref.getString("LastName", "");
        mob = mpref.getString("MobileNo", "");
        if (Objects.requireNonNull(fname).equals("") ||
                Objects.requireNonNull(lname).equals("") ||
                Objects.requireNonNull(mob).equals("")) {
            startActivity(new Intent(splash.this, activity_user_reg.class));
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        }
    }

    public void check_auth() {
        mAuth = FirebaseAuth.getInstance();
        userid = mAuth.getUid();
        mpref = getSharedPreferences("User", MODE_PRIVATE);
        final String fname, lname, mob;
        fname = mpref.getString("FirstName", "");
        lname = mpref.getString("LastName", "");
        mob = mpref.getString("MobileNo", "");
        mAuthlistener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    userid = mAuth.getUid();
                    if (Objects.requireNonNull(fname).equals("") ||
                            Objects.requireNonNull(lname).equals("") ||
                            Objects.requireNonNull(mob).equals("")) {
                        try {
                            basic_check();
                        } catch (Exception e) {
//                                    Crashlytics.getInstance();
//                                    Crashlytics.log(e.getMessage());
                        }
                    } else {
                        if (mpref.getBoolean("IF_SECURE", true)) {
                            if (Objects.requireNonNull(mpref.getString("PIN", "")).isEmpty() ||
                                    Objects.requireNonNull(mpref.getString("PIN", "")).equals("")) {
                                startActivity(new Intent(splash.this, activity_home.class));
                                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                                finish();
                            } else {
                                int SPLASH_DISPLAY_LENGTH = 3000;
                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        startActivity(new Intent(splash.this, activity_entry.class));
                                        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                                        finish();
                                    }
                                }, SPLASH_DISPLAY_LENGTH);
                            }
                        } else {
                            int SPLASH_DISPLAY_LENGTH = 3000;
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    startActivity(new Intent(splash.this, activity_home.class));
                                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                                    finish();
                                }
                            }, SPLASH_DISPLAY_LENGTH);

                        }
                    }
                } else {
                    int SPLASH_DISPLAY_LENGTH = 3000;
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            startActivity(new Intent(splash.this, activity_login.class));
                            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                            finish();
                        }
                    }, SPLASH_DISPLAY_LENGTH);
                }
            }
        };
        mAuth.addAuthStateListener(mAuthlistener);
    }

    public void recheck_network() {
        new AlertDialog.Builder(mcontext)
                .setTitle("Connectivity Error..!!")
                .setMessage("Check your net connection..!!")
                .setPositiveButton("Retry..!!", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (isOnline()) {
                            check_auth();
                        } else {
                            startActivity(new Intent(mcontext, splash.class));
                            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                            finish();
                        }
                    }
                })
                .setNegativeButton("Exit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                }).show();
    }

    @SuppressWarnings("deprecation")
    private boolean isOnline() {
//        ConnectivityManager cm = (ConnectivityManager) mcontext.getSystemService(Context.CONNECTIVITY_SERVICE);
//        NetworkInfo netInfo = cm.getActiveNetworkInfo();
//        return netInfo != null && netInfo.isConnectedOrConnecting();
        return true;
    }
}
