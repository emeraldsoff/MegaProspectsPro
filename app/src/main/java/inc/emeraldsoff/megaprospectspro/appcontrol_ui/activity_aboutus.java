package inc.emeraldsoff.megaprospectspro.appcontrol_ui;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;

import inc.emeraldsoff.megaprospectspro.R;
import inc.emeraldsoff.megaprospectspro.activity_main;
import inc.emeraldsoff.megaprospectspro.ui_data.fragment_Home.activity_home;

public class activity_aboutus extends activity_main {

    TextView appid;
    private SharedPreferences mpref;
    private Context mcontext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aboutus);
        mcontext = this;
        super.menucreate();
        setaboutus();
    }

    @SuppressLint("SetTextI18n")
    public void setaboutus() {
        mpref = getSharedPreferences("User", MODE_PRIVATE);
        appid = findViewById(R.id.appid);
        appid.setText("App ID: " + mpref.getString("userID", ""));
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(mcontext, activity_home.class));
        finish();
    }
}
