package inc.emeraldsoff.megaprospectspro.login;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import androidx.annotation.NonNull;
import es.dmoral.toasty.Toasty;
import inc.emeraldsoff.megaprospectspro.R;
import inc.emeraldsoff.megaprospectspro.splash;

//import java.text.ParseException;
//import java.time.Instant;

//import com.google.firebase.firestore.DocumentReference;
//import com.google.firebase.firestore.FirebaseFirestoreSettings;

public class loginactivity extends Activity {
    private static final String TAG = "EmailAuthActivity";
    @SuppressLint("SimpleDateFormat")
    private final SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
    private final
    //    private final long ONE_DAY = 24 * 60 * 60 * 1000;
            Date now = new Date();
    String InstallDate = formatter.format(now);
//    int validtime = 60;

    private final static int RC_SIGN_IN = 1;
    //    String nowdate = formatter.format(now);
    SignInButton gbtn;
    SharedPreferences mpref;
    // [END declare_auth]
    private FirebaseFirestore fdb = FirebaseFirestore.getInstance();
    Button mob_signin;
    //    private FirebaseAuth.AuthStateListener mAuthlistener;
    GoogleSignInClient mGoogleSignInClient;
    //    FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
//            .setPersistenceEnabled(true)
//            .build();
    private FirebaseAuth mAuth;
    TextView Brand;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loginactivity);
        Brand = findViewById(R.id.textView);
        Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/Harlow.ttf");
        Brand.setTypeface(typeface);

//        Brand.setTextSize(36,36);
        mob_signin = findViewById(R.id.other_signin);
        gbtn = findViewById(R.id.googlebutton);
//        fbtn=findViewById(R.id.facebookbutton);
        mAuth = FirebaseAuth.getInstance();
        gbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn();
            }
        });
        mob_signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(loginactivity.this, mobile_signin.class));
            }
        });
        // Configure Google Sign In
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        // Build a GoogleSignInClient with the options specified by gso.
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

    }

    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                if (account != null) {
                    firebaseAuthWithGoogle(account);
                }
                else
                {
                    Toasty.error(loginactivity.this,"Something went wrong..!!",
                            Toast.LENGTH_LONG,true).show();
                }
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                Log.w("TAG", "Google sign in failed", e);
                Toasty.error(loginactivity.this,"Google sign in failed..!!",
                        Toast.LENGTH_LONG,true).show();
                // ...
            }
        }
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        Log.d("TAG", "firebaseAuthWithGoogle:" + acct.getId());

        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d("TAG", "signInWithCredential:success");
                    final String userID = mAuth.getUid();
                    final FirebaseUser user = mAuth.getCurrentUser();
                    final String email = Objects.requireNonNull(mAuth.getCurrentUser()).getEmail();
//                    String uid = userID;
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(now);
                    calendar.add(Calendar.DAY_OF_YEAR, +30);
                    Date newDate = calendar.getTime();
                    final String ExpiryDate = formatter.format(newDate);

//                    DocumentReference docref = fdb.document("prospect_users" + "/" + userID);
                    fdb.document("prospect_users" + "/" + userID)
                            .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            if (task.isSuccessful()) {
                                DocumentSnapshot doc = task.getResult();
                                if (doc!=null && doc.exists()) {
                                    Log.d(TAG, "DocumentSnapshot data: " + doc.getData());
                                    //                                    StringBuilder uidf = new StringBuilder();
                                    //                                    StringBuilder usrf = new StringBuilder();
                                    //                                    StringBuilder validtime = new StringBuilder();
                                    StringBuilder ufname = new StringBuilder();
                                    StringBuilder umname = new StringBuilder();
                                    StringBuilder ulname = new StringBuilder();
                                    StringBuilder validitydate = new StringBuilder();
                                    StringBuilder expire = new StringBuilder();
                                    StringBuilder install = new StringBuilder();
                                    StringBuilder ifval = new StringBuilder();
                                    StringBuilder uemail = new StringBuilder();
                                    StringBuilder uphone = new StringBuilder();
                                    //                                    StringBuilder secure = new StringBuilder();
                                    //                                    StringBuilder pin = new StringBuilder();

                                    //                                    uidf.append(doc.get("UserID"));
                                    //                                    usrf.append(doc.get("user"));
                                    //                                    validtime.append(doc.get("ValidTime"));
                                    validitydate.append(doc.get("ValidityDate"));
                                    install.append(doc.get("InstallDate"));
                                    expire.append(doc.get("ExpiryDate"));
                                    ufname.append(doc.get("FirstName"));
                                    umname.append(doc.get("MiddleName"));
                                    ulname.append(doc.get("LastName"));
                                    uemail.append(doc.get("EmailId"));
                                    uphone.append(doc.get("MobileNo"));
                                    ifval.append(doc.getBoolean("IF_VALID"));
                                    //                                    secure.append(doc.getBoolean("IF_SECURE"));
                                    //                                    pin.append(doc.get("PIN"));

                                    Boolean b = Boolean.parseBoolean(String.valueOf(ifval));
                                    //                                    Boolean s = Boolean.parseBoolean(String.valueOf(secure));

                                    //                                    SharedPreferences mpref;
                                    if (String.valueOf(uphone).equals("+917003564171")||
                                            String.valueOf(uemail).equals("debanjanchakraborty17@gmail.com")){
                                        SharedPreferences mpref;
                                        mpref = getSharedPreferences("User", MODE_PRIVATE);
                                        SharedPreferences.Editor editor = mpref.edit();
                                        editor.putString("userID", userID);
                                        editor.putString("user", String.valueOf(user));
                                        editor.putString("FirstName", String.valueOf(ufname));
                                        editor.putString("MiddleName", String.valueOf(umname));
                                        editor.putString("LastName", String.valueOf(ulname));
                                        editor.putString("MobileNo", String.valueOf(uphone));
                                        editor.putString("EmailId", String.valueOf(uemail));
//                                            editor.putString("ValidTime", String.valueOf(validtime));
                                        editor.putString("ValidityDate", String.valueOf(validitydate));
                                        editor.putString("InstallDate", String.valueOf(install));
                                        editor.putString("ExpiryDate", String.valueOf(expire));
                                        editor.putBoolean("IF_VALID", b);
                                        editor.putBoolean("IF_SECURE", false);
                                        editor.putString("PIN", "");
                                        editor.putBoolean("admin_access",false);
                                        editor.apply();
                                    }
                                    else {
                                        SharedPreferences mpref;
                                        mpref = getSharedPreferences("User", MODE_PRIVATE);
                                        SharedPreferences.Editor editor = mpref.edit();
                                        editor.putString("userID", userID);
                                        editor.putString("user", String.valueOf(user));
                                        editor.putString("FirstName", String.valueOf(ufname));
                                        editor.putString("MiddleName", String.valueOf(umname));
                                        editor.putString("LastName", String.valueOf(ulname));
                                        editor.putString("MobileNo", String.valueOf(uphone));
                                        editor.putString("EmailId", String.valueOf(uemail));
//                                            editor.putString("ValidTime", String.valueOf(validtime));
                                        editor.putString("ValidityDate", String.valueOf(validitydate));
                                        editor.putString("InstallDate", String.valueOf(install));
                                        editor.putString("ExpiryDate", String.valueOf(expire));
                                        editor.putBoolean("IF_VALID", b);
                                        editor.putBoolean("IF_SECURE", false);
                                        editor.putString("PIN", "");
                                        editor.apply();
                                    }

                                    new Handler().postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            Intent y = new Intent(loginactivity.this, splash.class);
                                            y.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                            y.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                            startActivity(y);
                                        }
                                    }, 0);
                                } else {
                                    Map<String, Object> client = new HashMap<>();
                                    //                                    client.put("PIN", "");
                                    //                                    client.put("IF_SECURE", false);
                                    client.put("IF_VALID", true);
                                    //                                    client.put("ValidTime", validtime);
                                    client.put("ValidityDate", InstallDate);
                                    client.put("InstallDate", InstallDate);
                                    client.put("ExpiryDate", ExpiryDate);
                                    client.put("FirstName", "");
                                    client.put("MiddleName", "");
                                    client.put("LastName", "");
                                    assert email != null;
                                    client.put("EmailId", email);
                                    client.put("MobileNo", "");
                                    assert userID != null;
                                    client.put("userID", userID);
                                    client.put("user", String.valueOf(user));
                                    fdb.collection("prospect_users").document(userID)
                                            .set(client)
                                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void aVoid) {
                                                    Log.d(TAG, "DocumentSnapshot successfully written!");
                                                    //                                    Toast.makeText(addclient_activity.this, "Client Added To Database...", Toast.LENGTH_LONG).show();

                                                    //                                                    SharedPreferences mpref;
                                                    String pno = "+917003564171";
                                                    String email_check = "debanjanchakraborty17@gmail.com";
                                                    if (email.equals(email_check)){
                                                        SharedPreferences mpref;
                                                        mpref = getSharedPreferences("User", MODE_PRIVATE);
                                                        SharedPreferences.Editor editor = mpref.edit();
                                                        editor.putString("userID", userID);
                                                        editor.putString("user", String.valueOf(user));
                                                        editor.putString("MobileNo", "");
//                                                            editor.putString("ValidTime", String.valueOf(validtime));
                                                        editor.putString("ValidityDate", InstallDate);
                                                        editor.putString("InstallDate", InstallDate);
                                                        editor.putString("ExpiryDate", ExpiryDate);
                                                        editor.putString("FirstName", "");
                                                        editor.putString("MiddleName", "");
                                                        editor.putString("LastName", "");
                                                        editor.putString("EmailId", "");
                                                        editor.putBoolean("IF_VALID", true);
                                                        editor.putBoolean("IF_SECURE", false);
                                                        editor.putString("PIN", "");
                                                        editor.putBoolean("admin_access",false);
                                                        editor.apply();
                                                    }
                                                    else {
                                                        SharedPreferences mpref;
                                                        mpref = getSharedPreferences("User", MODE_PRIVATE);
                                                        SharedPreferences.Editor editor = mpref.edit();
                                                        editor.putString("userID", userID);
                                                        editor.putString("user", String.valueOf(user));
                                                        editor.putString("MobileNo", "");
//                                                            editor.putString("ValidTime", String.valueOf(validtime));
                                                        editor.putString("ValidityDate", InstallDate);
                                                        editor.putString("InstallDate", InstallDate);
                                                        editor.putString("ExpiryDate", ExpiryDate);
                                                        editor.putString("FirstName", "");
                                                        editor.putString("MiddleName", "");
                                                        editor.putString("LastName", "");
                                                        editor.putString("EmailId", "");
                                                        editor.putBoolean("IF_VALID", true);
                                                        editor.putBoolean("IF_SECURE", false);
                                                        editor.putString("PIN", "");
                                                        editor.apply();
                                                    }

                                                    Toasty.success(loginactivity.this, "Logged In Successfully..!!",
                                                            Toast.LENGTH_LONG, true).show();
                                                    //

                                                    new Handler().postDelayed(new Runnable() {
                                                        @Override
                                                        public void run() {
                                                            Intent y = new Intent(loginactivity.this, splash.class);
                                                            y.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                                            y.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                                            startActivity(y);
                                                        }
                                                    }, 0);
                                                }
                                            }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Log.w(TAG, "Error writing document", e);
                                            //                                    Toast.makeText(addclient_activity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                                            Toasty.error(loginactivity.this, "Failed To Sign Up..!!",
                                                    Toast.LENGTH_LONG, true).show();
                                        }
                                    });
                                }
                            } else {
                                Log.d(TAG, "get failed with ", task.getException());
                                Toasty.error(loginactivity.this, "Something went wrong..!!",
                                        Toast.LENGTH_LONG, true).show();
                            }
                        }
                    });

//                            updateUI(user);
                } else {
                    Toasty.error(loginactivity.this, "Something Went Wrong..!!",
                            Toast.LENGTH_LONG, true).show();
                }
            }
        });
    }
}