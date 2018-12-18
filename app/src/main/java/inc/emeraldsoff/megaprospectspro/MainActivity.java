package inc.emeraldsoff.megaprospectspro;

import android.content.Context;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import es.dmoral.toasty.Toasty;
import inc.emeraldsoff.megaprospectspro.appcontrol_ui.fragment_settings;
import inc.emeraldsoff.megaprospectspro.billing_ui.fragment_gopro;
import inc.emeraldsoff.megaprospectspro.ui_data.fragment_addpeople;
import inc.emeraldsoff.megaprospectspro.ui_data.fragment_home;
import inc.emeraldsoff.megaprospectspro.ui_data.fragment_searchpeople;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout drawer;
    private Context mcontext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mcontext = this;
        androidx.appcompat.widget.Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawer = findViewById(R.id.drawer_layout);

        NavigationView navigationView = findViewById(R.id.nav_view);

        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);

        toggle.syncState();

        if(savedInstanceState==null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, new fragment_home())
                    .commit();
            navigationView.setCheckedItem(R.id.home);
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.home:
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, new fragment_home())
                        .commit();
                break;
            case R.id.addpeople:
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, new fragment_addpeople())
                        .commit();
                break;
            case R.id.searchpeople:
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, new fragment_searchpeople())
                        .commit();
                break;
            case R.id.settings:
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, new fragment_settings())
                        .commit();
                break;
            case R.id.gopro:
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, new fragment_gopro())
                        .commit();
                break;
            case R.id.share:
                Toasty.success(mcontext,"Message Shared..!!",4).show();
                break;
        }
        drawer.closeDrawer(GravityCompat.START);
        return false;
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();

        }
    }
}
