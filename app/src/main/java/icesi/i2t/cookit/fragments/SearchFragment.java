package icesi.i2t.cookit.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import icesi.i2t.cookit.R;
import icesi.i2t.cookit.lists.RecyclerAdapterFeed;
import icesi.i2t.cookit.model.DataBase;
import icesi.i2t.cookit.model.Like;
import icesi.i2t.cookit.model.Recipe;
import icesi.i2t.cookit.model.modelLogic;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link SearchFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Favourites#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SearchFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private RecyclerView list;
    private RecyclerAdapterFeed adapterFeed;
    private View vista;
    private modelLogic logic;
    private DataBase dataBase;
    private String categorie;

    private FirebaseDatabase db;
    private FirebaseAuth auth;
    private ArrayList<Recipe> recipes;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public SearchFragment() {

    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Favourites.
     */
    // TODO: Rename and change types and number of parameters
    public static SearchFragment newInstance(String param1, String param2) {
        SearchFragment fragment = new SearchFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        db = FirebaseDatabase.getInstance();
        auth = FirebaseAuth.getInstance();
        vista = inflater.inflate(R.layout.fragment_favourites, container, false);
        recipes = new ArrayList<>();
//        Recipe r = new Recipe();
//        r.setName("Pizza");
//        r.setCost("Comida italiana");
//        Recipe r2 = new Recipe();
//        r2.setName("Arroz");
//        r2.setCost("Comida");
//        Recipe r3 = new Recipe();
//        r3.setName("Ramen");
//        r3.setCost("Comida tipica");
//        recipies.add(r);
//        recipies.add(r2);
//        recipies.add(r3);
        list = vista.findViewById(R.id.list_favs);
        getQueryCategoryList();
        return vista;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    public void getQueryCategoryList(){
        DatabaseReference recipies_ref = db.getReference().child("recipes");
        recipes = new ArrayList<>();
        recipies_ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Iterable<DataSnapshot> ds = dataSnapshot.getChildren();
                for (DataSnapshot data : ds){
                    Recipe rec = data.getValue(Recipe.class);
                    if(rec.getCategories().containsValue(categorie)){
                        recipes.add(rec);
                    }
                }
                Log.e("/////////////", recipes.size()+"");
                adapterFeed = new RecyclerAdapterFeed(vista.getContext(), recipes);
                list.setHasFixedSize(true);
                list.setAdapter(adapterFeed);
                list.setLayoutManager(new LinearLayoutManager(vista.getContext()));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public String getCategorie() {
        return categorie;
    }

    public void setCategorie(String categorie) {
        this.categorie = categorie;
    }
}
