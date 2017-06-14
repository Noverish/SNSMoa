package kr.ac.korea.snsmoa;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.widget.FrameLayout;

import java.util.ArrayList;

import kr.ac.korea.intelligentgallery.R;
import kr.ac.korea.snsmoa.facebook.FacebookClient;
import kr.ac.korea.snsmoa.facebook.FacebookWebView;
import kr.ac.korea.snsmoa.twitter.TwitterClient;
import kr.ac.korea.snsmoa.twitter.TwitterWebView;
import kr.ac.korea.snsmoa.util.Essentials;
import kr.ac.korea.snsmoa.youtube.YoutubeActivity;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private HomeFragment homeFragment;

    private ArrayList<String> categorySubMenuItems = new ArrayList<>();
    private SubMenu categorySubMenu;

    private FrameLayout homeFragmentLayout;
    private FacebookWebView facebookWebView;
    private TwitterWebView twitterWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_snsmoa);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        categorySubMenu = navigationView.getMenu().addSubMenu("Category");
        categorySubMenu.add("ALL");

        Essentials.replaceFragment(this, R.id.activity_main_fragment_home, homeFragment = new HomeFragment());

        homeFragmentLayout = (FrameLayout) findViewById(R.id.activity_main_fragment_home);
        facebookWebView = (FacebookWebView) findViewById(R.id.facebook_web_view);
        twitterWebView = (TwitterWebView) findViewById(R.id.twitter_web_view);

        FacebookClient.getInstance().setWebView(facebookWebView);
        TwitterClient.getInstance().setWebView(twitterWebView);

        FacebookClient.getInstance().setOnIsNotLogined(new FacebookClient.OnIsNotLogined() {
            @Override
            public void isNotLogined() {
                homeFragment.facebookNotLogin.setVisibility(View.VISIBLE);
            }
        });
        TwitterClient.getInstance().setOnIsNotLogined(new TwitterClient.OnIsNotLogined() {
            @Override
            public void isNotLogined() {
                homeFragment.twitterNotLogin.setVisibility(View.VISIBLE);
            }
        });

        registerReceiver(new AddCategoryToMenuRecevier(), new IntentFilter(AddCategoryToMenuRecevier.ACTION));
        registerReceiver(new ChangeScreenReceiver(), new IntentFilter(ChangeScreenReceiver.ACTION));

        System.out.println(getApplicationContext().getPackageName());
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        /* if return value is true, event is consumed here */
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_home) {
            changeScreen(Screen.home);
            return true;
        } else if (id == R.id.action_facebook) {
            changeScreen(Screen.facebook);
            return true;
        } else if (id == R.id.action_twitter) {
            changeScreen(Screen.twitter);
            return true;
        } else if (id == R.id.action_youtube) {
            startActivity(new Intent(this, YoutubeActivity.class));
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

//        if (id == R.id.nav_camera) {
//            // Handle the camera action
//        } else if (id == R.id.nav_gallery) {
//
//        } else if (id == R.id.nav_slideshow) {
//
//        } else {
            homeFragment.recyclerView.setCategory((String) item.getTitle());
//        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void changeScreen(Screen screen) {
        switch (screen) {
            case home:
                homeFragmentLayout.setVisibility(View.VISIBLE);
                facebookWebView.setVisibility(View.VISIBLE);
                twitterWebView.setVisibility(View.VISIBLE);
                break;
            case twitter:
                homeFragmentLayout.setVisibility(View.INVISIBLE);
                facebookWebView.setVisibility(View.INVISIBLE);
                twitterWebView.setVisibility(View.VISIBLE);
                break;
            case facebook:
                homeFragmentLayout.setVisibility(View.INVISIBLE);
                facebookWebView.setVisibility(View.VISIBLE);
                twitterWebView.setVisibility(View.INVISIBLE);
                break;
        }
    }

    public class AddCategoryToMenuRecevier extends BroadcastReceiver {
        public static final String ACTION = "kr.ac.korea.snsmoa.MainActivity.AddCategoryToMenuRecevier";
        public static final String INTENT_KEY = "fullCategory";

        @Override
        public void onReceive(Context context, Intent intent) {
            String category = intent.getStringExtra(INTENT_KEY);
            if(!categorySubMenuItems.contains(category)) {
                categorySubMenuItems.add(category);
                categorySubMenu.add(category);
            }
        }
    }

    public enum Screen {
        home,
        twitter,
        facebook
    }

    public class ChangeScreenReceiver extends BroadcastReceiver {
        public static final String ACTION = "kr.ac.korea.snsmoa.MainActivity.ChangeScreenReceiver";
        public static final String INTENT_KEY = "screen";

        @Override
        public void onReceive(Context context, Intent intent) {
            changeScreen((Screen) intent.getSerializableExtra(INTENT_KEY));
        }
    }
}
