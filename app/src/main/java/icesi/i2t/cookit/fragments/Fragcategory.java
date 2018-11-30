package icesi.i2t.cookit.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

import icesi.i2t.cookit.R;
import icesi.i2t.cookit.lists.RecyclerAdapterCategory;
import icesi.i2t.cookit.model.Category;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Fragcategory.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Fragcategory#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Fragcategory extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private RecyclerView list;
    private RecyclerAdapterCategory adapterCat;
    private View vista;
    private static FirebaseDatabase db;
    private ArrayList<Category> categories;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public Fragcategory() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Fragcategory.
     */
    // TODO: Rename and change types and number of parameters
    public static Fragcategory newInstance(String param1, String param2) {
        Fragcategory fragment = new Fragcategory();
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
        vista = inflater.inflate(R.layout.fragment_favourites, container, false);
        db = FirebaseDatabase.getInstance();
//        ArrayList<icesi.i2t.cookit.model.Category> categories = new ArrayList<>();
//        icesi.i2t.cookit.model.Category r = new icesi.i2t.cookit.model.Category();
//        r.setName("Comida\nRapida");
//        icesi.i2t.cookit.model.Category r2 = new icesi.i2t.cookit.model.Category();
//        r2.setName("Comida\nSana");
//        icesi.i2t.cookit.model.Category r3 = new icesi.i2t.cookit.model.Category();
//        r3.setName("Comida\nMexicana");
//        categories.add(r);
//        categories.add(r2);
//        categories.add(r3);

        list = vista.findViewById(R.id.list_favs);
        getCategoriesList();
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

    public void getCategoriesList(){
        DatabaseReference category_ref = db.getReference().child("categories");
        categories = new ArrayList<>();

        category_ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Iterable<DataSnapshot> ds = dataSnapshot.getChildren();
                for (DataSnapshot data : ds){
                    Category cat = data.getValue(Category.class);
                    categories.add(cat);
                }
                adapterCat = new RecyclerAdapterCategory(vista.getContext(), categories);
                list.setHasFixedSize(true);
                list.setAdapter(adapterCat);
                list.setLayoutManager(new GridLayoutManager(vista.getContext(), 2));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

}
