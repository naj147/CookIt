package com.example.naj_t.cookitv1;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.provider.CalendarContract;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ShoppingListFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ShoppingListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ShoppingListFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    ShoppingListAdapter mAdapter;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public ShoppingListFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ShoppingListFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ShoppingListFragment newInstance(String param1, String param2) {
        ShoppingListFragment fragment = new ShoppingListFragment();
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
        // Inflate the layout for this fragment

        View v = inflater.inflate(R.layout.fragment_shopping_list, container, false);
        ImageView addToCalendar= v.findViewById(R.id.calendar);
        final ImageView imageView = v.findViewById(R.id.nts);
        imageView.setVisibility(View.VISIBLE);
        final SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getContext());
        RecyclerView mRecyclerView = (RecyclerView) v.findViewById(R.id.my_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(v.getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new ShoppingListAdapter(null);
        mRecyclerView.setAdapter(mAdapter);
        Set<String> ingredientsSet = sharedPref.getStringSet("ingredients",null);
        final ArrayList<String> ingredients=new ArrayList<>() ;
        if(ingredientsSet!=null){
            for(String str : ingredientsSet){
                Log.v("myFrag", str + "\n");
                mAdapter.addIngredient(str);
                imageView.setVisibility(View.GONE);
//                Toast.makeText(v.getContext(),"Not so empty list",Toast.LENGTH_LONG).show();
            }
        }


        final Handler handler = new Handler();
        Timer timer = new Timer();
        TimerTask backtask = new TimerTask() {
            @Override
            public void run() {
                handler.post(new Runnable() {
                    public void run() {
                        try {
                            //To task in this. Can do network operation Also
                            Set<String> ingredientsSet = sharedPref.getStringSet("ingredients",null);
                            final ArrayList<String> ingredients=new ArrayList<>() ;
                            if(ingredientsSet!=null){
                                for(String str : ingredientsSet){
                                    mAdapter.addIngredient(str);
                                    imageView.setVisibility(View.GONE);
//                Toast.makeText(v.getContext(),"Not so empty list",Toast.LENGTH_LONG).show();
                                }
                            }
                            Log.d("check","Check Run" );
                        } catch (Exception e) {
                            // TODO Auto-generated catch block
                        }
                    }
                });
            }
        };
        timer.schedule(backtask , 0, 5000);
        addToCalendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if( mAdapter.getChecked()!=null &&  mAdapter.getChecked().size()>0){
                    Intent calIntent = new Intent(Intent.ACTION_INSERT);
                    calIntent.setData(CalendarContract.Events.CONTENT_URI);
                    calIntent.putExtra(CalendarContract.Events.TITLE, "Groceries to Buy for CookIT App Recipe");
                    calIntent.putExtra(CalendarContract.Events.EVENT_LOCATION, "Nearby Grocery Store");
                    StringBuilder eventDesc = new StringBuilder("I loved this recipe that I've just saw on CookIt app but i'm lacking some ingredients, which are :\n");
                    for(String str : mAdapter.getChecked()){
                        eventDesc.append("- ").append(str).append("\n");
                    }
                    SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getContext());
                    SharedPreferences.Editor edit = sharedPref.edit();
                    edit.clear();
                    edit.apply();
                    calIntent.putExtra(CalendarContract.Events.DESCRIPTION, eventDesc.toString());
                    Calendar startTime = Calendar.getInstance();
                    startTime.setTime(Calendar.getInstance().getTime());
                    Calendar endTime = Calendar.getInstance();
                    endTime.setTimeInMillis(Calendar.getInstance().getTimeInMillis()+3600);
                    calIntent.putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME,
                            startTime.getTimeInMillis());
                    calIntent.putExtra(CalendarContract.EXTRA_EVENT_END_TIME,
                            endTime.getTimeInMillis());
                    startActivity(calIntent);
                }
                else{
                    Toast.makeText(getContext(),"You have no ingredients in your shoppingList",Toast.LENGTH_SHORT).show();
                    SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getContext());
                    SharedPreferences.Editor edit = sharedPref.edit();
                    edit.clear();
                    edit.apply();
                    mAdapter.clean();
                }
//                Handler handler= new Handler();
//                handler.postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        android.os.Process.killProcess(android.os.Process.myPid());
//                        System.exit(1);
//                    }
//                }, 500);
            }
        });
        mAdapter.addIngredients(ingredients);



        return v;
/*
 // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_recipes, container, false);
        mRecyclerView = (RecyclerView) v.findViewById(R.id.my_recycler_view);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(v.getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        // specify an adapter (see also next example)
        mAdapter = new RecipesAdapter(null);
        mRecyclerView.setAdapter(mAdapter);
        RecipeSearchController controller = new RecipeSearchController();
        controller.start(mAdapter);
        return v;
* */
        /*
        *   Toast.makeText(v.getContext(),"go to the webview",Toast.LENGTH_SHORT).show();
                Intent calIntent = new Intent(Intent.ACTION_INSERT);
                calIntent.setData(CalendarContract.Events.CONTENT_URI);
                calIntent.putExtra(CalendarContract.Events.TITLE, "Google IO Afterparty");
                calIntent.putExtra(CalendarContract.Events.EVENT_LOCATION, "The W Hotel Bar on Third Street");
                calIntent.putExtra(CalendarContract.Events.DESCRIPTION, "Hang out after Google IO for a drink and geeky conversation.");
                Calendar startTime = Calendar.getInstance();
                startTime.set(2018, 5, 29, 18, 0);
                Calendar endTime = Calendar.getInstance();
                endTime.set(2018, 5, 29, 22, 30);
                calIntent.putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME,
                        startTime.getTimeInMillis());
                calIntent.putExtra(CalendarContract.EXTRA_EVENT_END_TIME,
                        endTime.getTimeInMillis());
                startActivity(calIntent);
        * */
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

//    @Override
//    public void onAttach(Context context) {
//        super.onAttach(context);
//        if (context instanceof OnFragmentInteractionListener) {
//            mListener = (OnFragmentInteractionListener) context;
//        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnFragmentInteractionListener");
//        }
//    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

//    @Override
//    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
//
//    }

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
}
