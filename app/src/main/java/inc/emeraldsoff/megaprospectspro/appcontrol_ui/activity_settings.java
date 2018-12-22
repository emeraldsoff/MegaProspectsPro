package inc.emeraldsoff.megaprospectspro.appcontrol_ui;

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import org.w3c.dom.Document;

import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.Objects;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import es.dmoral.toasty.Toasty;
import inc.emeraldsoff.megaprospectspro.BuildConfig;
import inc.emeraldsoff.megaprospectspro.R;
import inc.emeraldsoff.megaprospectspro.activity_main;
import inc.emeraldsoff.megaprospectspro.login.activity_login;

import static inc.emeraldsoff.megaprospectspro.Constants.github_app_link;
import static inc.emeraldsoff.megaprospectspro.Constants.github_app_link_test;
import static inc.emeraldsoff.megaprospectspro.Constants.play_store_app_link;
import static inc.emeraldsoff.megaprospectspro.Constants.version_code_fetch_link;
import static inc.emeraldsoff.megaprospectspro.Constants.version_code_fetch_link_test;

public class activity_settings extends activity_main {

    Switch lockswitch, admin_access1;
    Button pinreset, pinremove, privacy, logout, update;
    TextView appver;
    private SharedPreferences mpref;
    private Context mcontext;

    public static void update_check(final Context mcontext) {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        int update_version = 0;
        final int current_version = BuildConfig.VERSION_CODE;
        try {
            URL url;
            if (BuildConfig.BUILD_TYPE.toUpperCase().equals("DEBUG_PRO") || BuildConfig.BUILD_TYPE.toUpperCase().equals("DEBUG")) {
                url = new URL(version_code_fetch_link_test);
            } else {
                url = new URL(version_code_fetch_link);
            }
            URLConnection urlConnection = url.openConnection();
            InputStream in = urlConnection.getInputStream();
            //your code
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(in);
            in.close();
            doc.getDocumentElement().normalize();
            update_version = Integer.parseInt(doc.getDocumentElement().getAttribute("new_update"));
        } catch (Exception e) {
            e.printStackTrace();
        }
//        if(!update_version.equals(current_version))
        if (update_version > current_version) {
//            final int finalUpdate_version = update_version;
            final int finalUpdate_version = update_version;
            new AlertDialog.Builder(mcontext)
                    .setCancelable(false)
                    .setIcon(R.drawable.ic_warning_pink_24dp)
                    .setTitle("New Update Available..!!")
                    .setMessage("Do you want to checkout the latest version??\nCurrent Version: " + current_version +
                            "\nNew Version: " + update_version)
                    .setNegativeButton("Get Update From GitHub", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent i = new Intent(Intent.ACTION_VIEW);
                            if (BuildConfig.BUILD_TYPE.toUpperCase().equals("DEBUG_PRO") ||
                                    BuildConfig.BUILD_TYPE.toUpperCase().equals("DEBUG")) {
                                i.setData(Uri.parse(github_app_link_test + finalUpdate_version + "-1.apk"));
                            } else {
                                i.setData(Uri.parse(github_app_link + finalUpdate_version + "-1.apk"));
                            }
                            mcontext.startActivity(i);
                        }
                    })
                    .setPositiveButton("Get Update From PlayStore", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
//                            Toasty.info(mcontext,"Current Version: "+current_version+
//                                    " Final Version: "+finalUpdate_version,4,false).show();
                            Intent i = new Intent(Intent.ACTION_VIEW);
                            i.setData(Uri.parse(play_store_app_link));
                            mcontext.startActivity(i);
                        }
                    })
                    .setNeutralButton("Not Now", null)
                    .show();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        mcontext = this;
        super.menucreate();
        settingapply();

    }

    @SuppressLint("SetTextI18n")
    public void settingapply() {
        try {
            mpref = getSharedPreferences("User", MODE_PRIVATE);
            appver = findViewById(R.id.appversion);
            privacy = findViewById(R.id.privacy);
            logout = findViewById(R.id.logout);
            lockswitch = findViewById(R.id.lockswitch);
            pinreset = findViewById(R.id.pinreset);
            pinremove = findViewById(R.id.pinremove);
            admin_access1 = findViewById(R.id.admin_access1);
            update = findViewById(R.id.update);

            logout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    FirebaseAuth mAuth = FirebaseAuth.getInstance();
                    mAuth.signOut();
                    Intent y = new Intent(mcontext, activity_login.class);
                    y.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    y.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    try {
                        ((ActivityManager) Objects.requireNonNull(mcontext.getSystemService(ACTIVITY_SERVICE)))
                                .clearApplicationUserData();
                        // clearing app data

//                    String packageName = getApplicationContext().getPackageName();
//                    Runtime runtime = Runtime.getRuntime();
//                    runtime.exec("pm clear "+packageName);
                    } catch (Exception e) {
                        Toasty.error(mcontext, "Failed to clear app data..!!", Toast.LENGTH_LONG,
                                true).show();
                    }
                    startActivity(y);
                }
            });

            update.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    update_check(mcontext);
                }
            });

            privacy.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String url = "https://emeraldsoff.github.io/Mega-Prospects/privacy_policy.html";
                    Intent i = new Intent(Intent.ACTION_VIEW);
                    i.setData(Uri.parse(url));
                    startActivity(i);
                }
            });

            appver.setText("VERSION:\n" + BuildConfig.VERSION_NAME + " " + BuildConfig.BUILD_TYPE.toUpperCase());

            if (Objects.requireNonNull(mpref.getString("MobileNo", "")).equals("+917003564171") &&
                    Objects.requireNonNull(mpref.getString("EmailId", "")).equals("debanjanchakraborty17@gmail.com")) {
                admin_access1.setVisibility(View.VISIBLE);
                admin_access1.setChecked(mpref.getBoolean("admin_access", true));
                admin_access1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if (isChecked) {
                            mpref = getSharedPreferences("User", MODE_PRIVATE);
                            SharedPreferences.Editor editor = mpref.edit();
                            editor.putBoolean("admin_access", true);
                            editor.apply();
//                    mpref.getBoolean("admin_access",true);
                            Toasty.success(mcontext, "Admin access is now on.",
                                    Toast.LENGTH_LONG, false).show();
                        } else {
                            mpref = getSharedPreferences("User", MODE_PRIVATE);
                            SharedPreferences.Editor editor = mpref.edit();
                            editor.putBoolean("admin_access", false);
                            editor.apply();
//                    mpref.getBoolean("admin_access",true);
                            Toasty.success(mcontext, "Admin access is now off.",
                                    Toast.LENGTH_LONG, false).show();
                        }
                    }
                });
            } else {
                admin_access1.setVisibility(View.GONE);
            }

            if (mpref.getBoolean("IF_SECURE", true)) {
                lockswitch.setChecked(mpref.getBoolean("IF_SECURE", true));
            } else {
                lockswitch.setChecked(mpref.getBoolean("IF_SECURE", true));
                pinreset.setVisibility(View.GONE);
                pinremove.setVisibility(View.GONE);
            }
            pinreset.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mcontext, activity_pin.class);
                    startActivity(intent);
                }
            });

            pinremove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SharedPreferences.Editor editor = mpref.edit();
                    editor.putString("PIN", "");
                    editor.putBoolean("IF_SECURE", false);
//                            editor.apply();
                    editor.apply();
                    lockswitch.setChecked(false);
                    pinreset.setVisibility(View.GONE);
                    pinremove.setVisibility(View.GONE);
                }
            });


            lockswitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        if (Objects.requireNonNull(mpref.getString("PIN", "")).equals("") ||
                                Objects.requireNonNull(mpref.getString("PIN", "")).isEmpty()) {
                            Intent intent = new Intent(mcontext, activity_pin.class);
                            startActivity(intent);
                        } else {
                            SharedPreferences.Editor editor = mpref.edit();
                            editor.putBoolean("IF_SECURE", true);
                            editor.apply();
                            pinreset.setVisibility(View.VISIBLE);
                            pinremove.setVisibility(View.VISIBLE);
//                        Toast.makeText(this,"App State: "+mpref.getBoolean("IF_SECURE",false),Toast.LENGTH_LONG).show();
                            Toasty.success(mcontext, "App is now secure...",
                                    Toast.LENGTH_LONG, false).show();
                        }
                    } else {
                        SharedPreferences.Editor editor = mpref.edit();
                        editor.putBoolean("IF_SECURE", false);
                        editor.apply();
                        pinset();
                        pinreset.setVisibility(View.GONE);
                        pinremove.setVisibility(View.GONE);
                    }
                }
            });
        } catch (Exception e) {
            Toasty.error(mcontext, "Something went wrong..!!",
                    Toast.LENGTH_LONG, false).show();
        }
    }

    private void pinset() {
//        boolean b=mpref.getBoolean("IF_SECURE",false);
//        Toast.makeText(this,"App State"+mpref.getBoolean("IF_SECURE",false),Toast.LENGTH_LONG).show();
        if (!mpref.getBoolean("IF_SECURE", true)) {
            Toasty.error(this, "App Is Not Secure..!!",
                    Toast.LENGTH_LONG, false).show();
//            Toasty.error(this, "App State: " + mpref.getBoolean("IF_SECURE", false),
//                    Toast.LENGTH_LONG,false).show();
//            Toast.makeText(this,"App Security Has Been Turned Off",Toast.LENGTH_LONG).show();
//            StyleableToast.makeText(this,"App Security Has Been Turned Off...!!!",
//                    Toast.LENGTH_LONG, R.style.notification).show();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        lockswitch.setChecked(mpref.getBoolean("IF_SECURE", true));
        if (mpref.getBoolean("IF_SECURE", true)) {
            pinreset.setVisibility(View.VISIBLE);
            pinremove.setVisibility(View.VISIBLE);
        } else {
            pinreset.setVisibility(View.GONE);
            pinremove.setVisibility(View.GONE);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        lockswitch.setChecked(mpref.getBoolean("IF_SECURE", true));
        if (mpref.getBoolean("IF_SECURE", true)) {
            pinreset.setVisibility(View.VISIBLE);
            pinremove.setVisibility(View.VISIBLE);
        } else {
            pinreset.setVisibility(View.GONE);
            pinremove.setVisibility(View.GONE);
        }
//        if(mpref.getBoolean("admin_access",true))
//        {
//            admin_access1.setChecked(true);
//        }
//        else
//        {
//            admin_access1.setChecked(false);
//        }
//        act_ads();
    }
}
