package inc.emeraldsoff.megaprospectspro;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import es.dmoral.toasty.Toasty;
import inc.emeraldsoff.megaprospectspro.appcontrol_ui.activity_aboutus;
import inc.emeraldsoff.megaprospectspro.appcontrol_ui.activity_settings;
import inc.emeraldsoff.megaprospectspro.billing_ui.activity_gopro;
import inc.emeraldsoff.megaprospectspro.ui_data.activity_addpeople;
import inc.emeraldsoff.megaprospectspro.ui_data.activity_searchpeople;
import inc.emeraldsoff.megaprospectspro.ui_data.diary.activity_diary_content;
import inc.emeraldsoff.megaprospectspro.ui_data.fragment_Home.activity_home;

//import static com.crashlytics.android.Crashlytics.TAG;

public class activity_main extends AppCompatActivity {
    //    Calendar myCalendar = Calendar.getInstance();
//    Calendar calendar = Calendar.getInstance();
//    Date now = new Date();
    private final int day = 1000 * 60 * 60 * 24;
    //    SimpleDateFormat dayFormat = new SimpleDateFormat("dd", Locale.US);
//    SimpleDateFormat monthFormat = new SimpleDateFormat("MM", Locale.US);
//    SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy", Locale.US);
//    SimpleDateFormat day_monFormat = new SimpleDateFormat("dd-MM", Locale.US);
    @SuppressLint("SimpleDateFormat")
    private final SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
    NavigationView navigationView;
    androidx.appcompat.widget.Toolbar toolbar;
    ActionBarDrawerToggle toggle;
    TextView user, phn, email, validinfo;
    //    SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd", Locale.US);
    SimpleDateFormat hh_mm_ss = new SimpleDateFormat("hh:mm:ss", Locale.US);
    //    Date futuredate = null;
//    Date currentdate = null;
    SimpleDateFormat fullFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
    private DrawerLayout drawer;
    private Context mcontext;
    private SharedPreferences mpref;
    private FirebaseFirestore fdb = FirebaseFirestore.getInstance();
//    private final long ONE_DAY = 24 * 60 * 60 * 1000;
//    Date now = new Date();
//    String dateString = formatter.format(now);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mcontext = this;
    }

    public void menucreate() {
        toolbar = findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
        drawer = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
//        View nav_header = navigationView.getHeaderView(0);
//        user = nav_header.findViewById(R.id.user);
//        phn = nav_header.findViewById(R.id.user_phn);
//        email = nav_header.findViewById(R.id.user_email);
//        validinfo = nav_header.findViewById(R.id.validinfo);
//        navigationView.setNavigationItemSelectedListener(this);

        toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);

        toggle.syncState();

        getactivityname();
        userdatafetch();
        Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/Harlow.ttf");
        navigationView.setItemIconTintList(null);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.home:
                        startActivity(new Intent(mcontext, activity_home.class));
                        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
//                getSupportFragmentManager()
//                        .beginTransaction()
//                        .setCustomAnimations(R.anim.fui_slide_in_right, R.anim.fui_slide_out_left)
//                        .replace(R.id.fragment_container, new fragment_home())
//                        .addToBackStack(null)
//                        .commit();
//                navigationView.setCheckedItem(R.id.home);
                        break;
                    case R.id.addpeople:
                        startActivity(new Intent(mcontext, activity_addpeople.class));
                        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                        break;
                    case R.id.searchpeople:
                        startActivity(new Intent(mcontext, activity_searchpeople.class));
                        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                        break;
                    case R.id.settings:
                        startActivity(new Intent(mcontext, activity_settings.class));
                        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                        break;
                    case R.id.aboutus:
                        startActivity(new Intent(mcontext, activity_aboutus.class));
                        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                        break;
                    case R.id.gopro:
                        startActivity(new Intent(mcontext, activity_gopro.class));
                        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                        break;
                    case R.id.diary_content:
                        startActivity(new Intent(mcontext, activity_diary_content.class));
                        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                        break;
                    case R.id.share:
                        Intent shareIntent = new Intent(Intent.ACTION_SEND);
                        shareIntent.setType("text/plain");
                        shareIntent.putExtra(Intent.EXTRA_TEXT, "Use EmeraldSoff Mega Prospects Pro" +
                                " to increase interaction with your friends, families and clients. " +
                                "Click on https://play.google.com/store/apps/details?id=inc.emeraldsoff.megaprospectspro" +
                                "to download the app");
                        shareIntent.putExtra(Intent.EXTRA_TITLE, "EmeraldSoff Mega Prospects Pro");
                        startActivity(Intent.createChooser(shareIntent, "Share Mega Prospects Pro"));

//                Toasty.success(mcontext, "Message Shared..!!", 4).show();
                        break;
                }
                drawer.closeDrawer(GravityCompat.START, true);
                return false;
            }
        });
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);

        toggle.syncState();
    }

//    @Override
//    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//        switch (item.getItemId()) {
//            case R.id.home:
//                startActivity(new Intent(mcontext, Main2Activity.class));
//                getSupportFragmentManager()
//                        .beginTransaction()
//                        .setCustomAnimations(R.anim.fui_slide_in_right, R.anim.fui_slide_out_left)
//                        .replace(R.id.fragment_container, new fragment_home())
//                        .addToBackStack(null)
//                        .commit();
//                navigationView.setCheckedItem(R.id.home);
//                break;
//            case R.id.addpeople:
//                getSupportFragmentManager()
//                        .beginTransaction()
//                        .setCustomAnimations(R.anim.fui_slide_in_right, R.anim.fui_slide_out_left)
//                        .replace(R.id.fragment_container, new fragment_addpeople())
//                        .addToBackStack(null)
//                        .commit();
////                navigationView.setCheckedItem(R.id.addpeople);
//                break;
//            case R.id.searchpeople:
//                getSupportFragmentManager()
//                        .beginTransaction()
//                        .setCustomAnimations(R.anim.fui_slide_in_right, R.anim.fui_slide_out_left)
//                        .replace(R.id.fragment_container, new fragment_searchpeople())
//                        .addToBackStack(null)
//                        .commit();
////                navigationView.setCheckedItem(R.id.searchpeople);
//                break;
//            case R.id.settings:
//                getSupportFragmentManager()
//                        .beginTransaction()
//                        .setCustomAnimations(R.anim.fui_slide_in_right, R.anim.fui_slide_out_left)
//                        .replace(R.id.fragment_container, new fragment_settings())
//                        .addToBackStack(null)
//                        .commit();
////                navigationView.setCheckedItem(R.id.settings);
//                break;
//            case R.id.aboutus:
//                getSupportFragmentManager()
//                        .beginTransaction()
//                        .setCustomAnimations(R.anim.fui_slide_in_right, R.anim.fui_slide_out_left)
//                        .replace(R.id.fragment_container, new fragment_aboutus())
//                        .addToBackStack(null)
//                        .commit();
////                navigationView.setCheckedItem(R.id.aboutus);
//                break;
//            case R.id.gopro:
//                getSupportFragmentManager()
//                        .beginTransaction()
//                        .setCustomAnimations(R.anim.fui_slide_in_right, R.anim.fui_slide_out_left)
//                        .replace(R.id.fragment_container, new fragment_gopro())
//                        .addToBackStack(null)
//                        .commit();
////                navigationView.setCheckedItem(R.id.gopro);
//                break;
//            case R.id.share:
//                Intent shareIntent = new Intent(Intent.ACTION_SEND);
//                shareIntent.setType("text/plain");
//                shareIntent.putExtra(Intent.EXTRA_TEXT, "Use EmeraldSoff Mega Prospects Pro" +
//                        " to increase interaction with your friends, families and clients. " +
//                        "Click on https://play.google.com/store/apps/details?id=inc.emeraldsoff.megaprospectspro" +
//                        "to download the app");
//                shareIntent.putExtra(Intent.EXTRA_TITLE, "EmeraldSoff Mega Prospects Pro");
//                startActivity(Intent.createChooser(shareIntent, "Share Mega Prospects Pro"));
//                getSupportFragmentManager()
//                        .beginTransaction()
//                        .setCustomAnimations(R.anim.fui_slide_in_right, R.anim.fui_slide_out_left)
//                        .commit();
////                Toasty.success(mcontext, "Message Shared..!!", 4).show();
//                break;
//        }
//        drawer.closeDrawer(GravityCompat.START, true);
//        return false;
//    }


//    public void onBackPress() {
//        if (drawer.isDrawerOpen(GravityCompat.START)) {
//            drawer.closeDrawer(GravityCompat.START);
//        } else {
////            return onBackPressedListener.onBackPressed();
//            super.onBackPressed();
////            finish();
//        }
//    }


    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
//            return onBackPressedListener.onBackPressed();
            super.onBackPressed();
//            finish();
        }
    }

    public void getactivityname() {
        String activityname = this.getClass().getSimpleName();
        switch (activityname) {
            case "activity_home":
                navigationView.setCheckedItem(R.id.home);
                toolbar.setTitle(Objects.requireNonNull(navigationView.getCheckedItem()).getTitle());
                break;
            case "activity_addpeople":
                navigationView.setCheckedItem(R.id.addpeople);
                toolbar.setTitle(Objects.requireNonNull(navigationView.getCheckedItem()).getTitle());
                break;
            case "activity_searchpeople":
                navigationView.setCheckedItem(R.id.searchpeople);
                toolbar.setTitle(Objects.requireNonNull(navigationView.getCheckedItem()).getTitle());
                break;
            case "activity_aboutus":
                navigationView.setCheckedItem(R.id.aboutus);
                toolbar.setTitle(Objects.requireNonNull(navigationView.getCheckedItem()).getTitle());
                break;
            case "activity_settings":
                navigationView.setCheckedItem(R.id.settings);
                toolbar.setTitle(Objects.requireNonNull(navigationView.getCheckedItem()).getTitle());
                break;
            case "activity_gopro":
                navigationView.setCheckedItem(R.id.gopro);
                toolbar.setTitle(Objects.requireNonNull(navigationView.getCheckedItem()).getTitle());
                break;
            case "activity_diary_content":
                navigationView.setCheckedItem(R.id.diary_content);
                toolbar.setTitle(Objects.requireNonNull(navigationView.getCheckedItem()).getTitle());
                break;
            case "activity_add_diary_content":
                toolbar.setTitle("New Diary Document");
                break;
        }
//        else if(activityname.equals("activity_editpeople"))
//        {
//            toolbar.setTitle("Person");
//        }
//        else if(activityname.equals("activity_showpeopledetails"))
//        {
//            toolbar.setTitle("");
//        }
//        else if(activityname.equals("activity_"))
//        {
//            toolbar.setTitle("");
//        }
//        else
//        {
//            navigationView.
//        }
    }

    @SuppressLint("SetTextI18n")
    public void userdatafetch() {
        navigationView = findViewById(R.id.nav_view);
        View nav_header = navigationView.getHeaderView(0);
        user = nav_header.findViewById(R.id.user);
        phn = nav_header.findViewById(R.id.user_phn);
        email = nav_header.findViewById(R.id.user_email);
        validinfo = nav_header.findViewById(R.id.validinfo);
        mpref = getSharedPreferences("User", MODE_PRIVATE);


        if (mpref.getString("MiddleName", "") != null ||
                !Objects.requireNonNull(mpref.getString("MiddleName", "")).isEmpty() ||
                !Objects.equals(mpref.getString("MiddleName", ""), "")) {
            user.setText(mpref.getString("FirstName", "") + " " + mpref.getString("MiddleName", "") +
                    " " + mpref.getString("LastName", ""));
        } else {
            user.setText(mpref.getString("FirstName", "") +
                    " " + mpref.getString("LastName", ""));
        }

        phn.setText(mpref.getString("MobileNo", ""));

        if (mpref.getString("EmailId", "") != null ||
                !Objects.requireNonNull(mpref.getString("EmailId", "")).isEmpty() ||
                !Objects.equals(mpref.getString("EmailId", ""), "")) {
            email.setText(mpref.getString("EmailId", ""));
        } else {
            email.setVisibility(View.GONE);

        }
//        Date finalexp;
//        String exp_date = "";
//        String time = "";
//        try {
//            exp_date=fullFormat.format(formatter.parse(mpref.getString("ExpiryDate", "")));
//            time = hh_mm_ss.format(formatter.parse(mpref.getString("ExpiryDate", "")));
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//
//        Date now = new Date();

        try {
            datcal();
        } catch (ParseException e) {
            e.printStackTrace();
            Toasty.error(mcontext, "Something went wrong..!!1",
                    Toast.LENGTH_LONG, true).show();
//                                    e.printStackTrace();
        }

    }

//    public void sync() {
//        fdb.document("prospect_users" + "/" + mpref.getString("userID", ""))
//                .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
//            @Override
//            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
//                if (task.isSuccessful()) {
//                    DocumentSnapshot doc = task.getResult();
//                    if (doc != null && doc.exists()) {
////                        Log.d(TAG, "DocumentSnapshot data: " + doc.getData());
//                        StringBuilder ufname = new StringBuilder();
//                        StringBuilder umname = new StringBuilder();
//                        StringBuilder ulname = new StringBuilder();
//                        StringBuilder validitydate = new StringBuilder();
//                        StringBuilder ifval = new StringBuilder();
//                        StringBuilder expire = new StringBuilder();
//                        StringBuilder install = new StringBuilder();
//                        StringBuilder uemail = new StringBuilder();
//                        StringBuilder uphone = new StringBuilder();
//
//                        validitydate.append(doc.get("ValidityDate"));
//                        install.append(doc.get("InstallDate"));
//                        expire.append(doc.get("ExpiryDate"));
//                        ufname.append(doc.get("FirstName"));
//                        umname.append(doc.get("MiddleName"));
//                        ulname.append(doc.get("LastName"));
//                        uemail.append(doc.get("EmailId"));
//                        uphone.append(doc.get("MobileNo"));
//                        ifval.append(doc.getBoolean("IF_VALID"));
//
//                        Boolean b = Boolean.parseBoolean(String.valueOf(ifval));
//
//                        mpref = getSharedPreferences("User", MODE_PRIVATE);
//                        SharedPreferences.Editor editor = mpref.edit();
//
//                        editor.putString("FirstName", String.valueOf(ufname));
//                        editor.putString("MiddleName", String.valueOf(umname));
//                        editor.putString("LastName", String.valueOf(ulname));
//                        editor.putString("MobileNo", String.valueOf(uphone));
//                        editor.putString("EmailId", String.valueOf(uemail));
//
//                        editor.putString("ValidityDate", String.valueOf(validitydate));
//                        editor.putString("InstallDate", String.valueOf(install));
//                        editor.putString("ExpiryDate", String.valueOf(expire));
//                        editor.putBoolean("IF_VALID", b);
//                        editor.apply();
//                        try {
//                            datcal();
//                        } catch (ParseException e) {
//                            e.printStackTrace();
//                            Toasty.error(mcontext, "Something went wrong..!!2",
//                                    Toast.LENGTH_LONG, true).show();
////                                    e.printStackTrace();
//                        }
//                    }
//                } else {
////                    Log.d(TAG, "get failed with ", task.getException());
//                    Toasty.error(mcontext, "Something went wrong..!!3",
//                            Toast.LENGTH_LONG, true).show();
//                }
//            }
//        });
//    }

    @SuppressLint("SetTextI18n")
    private void datcal() throws ParseException {
        navigationView = findViewById(R.id.nav_view);
        View nav_header = navigationView.getHeaderView(0);
//        user = nav_header.findViewById(R.id.user);
//        phn = nav_header.findViewById(R.id.user_phn);
//        email = nav_header.findViewById(R.id.user_email);
        validinfo = nav_header.findViewById(R.id.validinfo);
        mpref = getSharedPreferences("User", MODE_PRIVATE);
//        @SuppressLint("SimpleDateFormat") SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
//
        String exp_date = fullFormat.format(formatter.parse(mpref.getString("ExpiryDate", "")));
        Date finalexp = fullFormat.parse(exp_date);
        String time = hh_mm_ss.format(finalexp);
        Date now = new Date();

        int daysleft = (int) ((finalexp.getTime() - now.getTime()) / day);

        if (daysleft >= 0) {
            if (daysleft > 0) {
                validinfo.setText("Expiry Date: " + exp_date + "\nDays Left: " +
                        daysleft + " days");
            } else {
                validinfo.setText("Expiry Date: " + exp_date + "\nDays Left: " +
                        daysleft + " days");
            }
        } else {
            SharedPreferences.Editor editor = mpref.edit();
            editor.putBoolean("IF_VALID", false);
            editor.apply();
//            subscription.setTouchscreenBlocksFocus(true);
            validinfo.setText("Your app has expired..!!");
        }
    }

    @SuppressWarnings("deprecation")
    public boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager) mcontext.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }
}
