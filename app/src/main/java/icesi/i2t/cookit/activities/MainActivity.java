package icesi.i2t.cookit.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toolbar;

import com.google.firebase.auth.FirebaseAuth;

import icesi.i2t.cookit.R;
import icesi.i2t.cookit.fragments.Favourites;
import icesi.i2t.cookit.fragments.Feed;
import icesi.i2t.cookit.fragments.Search;

public class MainActivity extends AppCompatActivity implements Feed.OnFragmentInteractionListener, Favourites.OnFragmentInteractionListener,
        Search.OnFragmentInteractionListener{

    private TextView mTextMessage;
    private Toolbar toolbar;
    private FirebaseAuth auth;
    private Feed feed;
    private Favourites favs;
    private Search search;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    feed = new Feed();
                    setFragment(feed);
                    return true;
                case R.id.navigation_explore:
                    search = new Search();
                    setFragment(search);
                    return true;
                case R.id.navigation_favourite:
                    favs = new Favourites();
                    setFragment(favs);
                    Log.e("ke pasa", "prro");
                    return true;
                case R.id.navigation_new:
                    return true;
            }
            return false;
        }
    };

    public void goToLogin(){
        Intent intent = new Intent(getApplicationContext(), Login.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        auth = FirebaseAuth.getInstance();
        if(auth.getCurrentUser() == null){
            goToLogin();
        }
        feed = new Feed();
        toolbar = (Toolbar) findViewById(R.id.tbar);
        toolbar.inflateMenu(R.menu.user_menu);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.navigation_profile:
                        return true;
                    case R.id.navigation_orders:
                        return true;
                    case R.id.navigation_logout:
                        FirebaseAuth.getInstance().signOut();
                        goToLogin();
                        return true;
                }
                return false;
            }
        });


        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        setFragment(feed);
    }

    public void setFragment(Fragment fragment){

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
//        fragmentTransaction.remove();
        fragmentTransaction.replace(R.id.frag_center, fragment);
        fragmentTransaction.commit();
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
