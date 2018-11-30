package icesi.i2t.cookit.activities;

import android.content.Intent;
import android.content.res.AssetManager;
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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.StringTokenizer;

import icesi.i2t.cookit.R;
import icesi.i2t.cookit.fragments.Fragcategory;
import icesi.i2t.cookit.fragments.Favourites;
import icesi.i2t.cookit.fragments.Feed;
import icesi.i2t.cookit.model.DataBase;
import icesi.i2t.cookit.model.modelLogic;

public class MainActivity extends AppCompatActivity implements Feed.OnFragmentInteractionListener, Favourites.OnFragmentInteractionListener,
        Fragcategory.OnFragmentInteractionListener {

    private TextView mTextMessage;
    private Toolbar toolbar;
    private FirebaseAuth auth;
    private Feed feed;
    private Favourites favs;
    private Fragcategory fragcategory;
    private boolean toolbarMode;
    private modelLogic logic;
    private DataBase dataBase;

    public void addAllrec(modelLogic logic) {
        try {
            AssetManager assetManager = getAssets();
            BufferedReader in = new BufferedReader(new InputStreamReader(assetManager.open("Recetas.txt")));
            String line = in.readLine();
            while (line != null) {
                String nombre = line.split("Nombre:")[1];

                String ing = in.readLine().split("Ingredientes:")[1];
                StringTokenizer skt = new StringTokenizer(ing,",");
                ArrayList<String> ingredients = new ArrayList<>();
                while (skt.hasMoreTokens()){
                    ingredients.add(skt.nextToken());
                }
                ArrayList<String> categoria = new ArrayList<>();
                categoria.add(in.readLine().split("Categoria:")[1]);
                in.readLine();
                line = in.readLine();
                ArrayList<String> steps = new ArrayList<>();
                while (line!=null&&!line.equals("")){
                    steps.add(line);
                    line = in.readLine();
                }
                logic.addRecipie(steps,ingredients,categoria,"",nombre);
                line = in.readLine();
                logic.refresh();
            }
            Log.e("----------->", "agregadas");
        } catch (IOException e) {
            Log.e("----------->", getApplicationContext().getFilesDir().toString());

            Log.e("----------->", e.getMessage());
            e.printStackTrace();
        }
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    if (!toolbarMode){
                        setDefaultToolbar();
                    }
                    feed = new Feed();
                    setFragment(feed);
                    return true;
                case R.id.navigation_explore:
                    if (toolbarMode){
                        setSearchToolbar();
                    }
                    fragcategory = new Fragcategory();
                    setFragment(fragcategory);
                    return true;
                case R.id.navigation_favourite:
                    if (!toolbarMode){
                        setDefaultToolbar();
                    }
                    favs = new Favourites();
                    setFragment(favs);
                    Log.e("ke pasa", "prro");
                    return true;
                case R.id.navigation_new:
                    if (!toolbarMode){
                        setDefaultToolbar();
                    }
//                    toolbar.removeAllViews();
//                    addAllrec(logic);
//                    logic.wipeIngsAndCats();
                    return true;
            }
            return false;
        }
    };

    public void goToLogin() {
        Intent intent = new Intent(getApplicationContext(), Login.class);
        startActivity(intent);
        finish();
    }

    public void goToProfile() {
        Intent intent = new Intent(getApplicationContext(), Profile.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        auth = FirebaseAuth.getInstance();
        dataBase = DataBase.getInstance();
        if (auth.getCurrentUser() == null) {
            goToLogin();
        }

        logic = new modelLogic();
        dataBase = new DataBase(logic);
        logic.refresh();

        feed = new Feed();
        toolbar = (Toolbar) findViewById(R.id.tbar);
        setDefaultToolbar();


        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        setFragment(feed);

//        HashMap<String, User> a = dataBase.getUserList();
//        Log.e("-----------=", a.size()+"a");
//        HashMap<String, User> b = dataBase.getUserList();
//        Log.e("-----------=", b.size()+"b");
//        HashMap<String, User> c = dataBase.getUserList();
//        Log.e("-----------=", c.size()+"c");
//        HashMap<String, User> d = dataBase.getUserList();
//        Log.e("-----------=", d.size()+"d");

    }

    public void setDefaultToolbar(){
        toolbar = (Toolbar) findViewById(R.id.tbar);
        toolbar.getMenu().clear();
        toolbar.inflateMenu(R.menu.user_menu);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.navigation_profile:
                        goToProfile();
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
        toolbarMode = true;
    }

    public void setSearchToolbar(){
        toolbar = (Toolbar) findViewById(R.id.tbar);
        toolbar.getMenu().clear();
        toolbar.inflateMenu(R.menu.search_menu);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_search:
                        return true;
                    case R.id.action_filter:
                        return true;
                }
                return false;
            }
        });
        toolbarMode = false;
    }

    public void setFragment(Fragment fragment) {

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
//        fragmentTransaction.remove();
        fragmentTransaction.replace(R.id.frag_center, fragment);
        fragmentTransaction.commit();
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
