package com.example.naj_t.cookitv1;

import android.app.FragmentManager;
import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.naj_t.cookitv1.API.StoveStatusController;
import com.example.naj_t.cookitv1.API.TempStatusController;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.taishi.flipprogressdialog.FlipProgressDialog;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link StoveFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link StoveFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class StoveFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private PieChart mChart;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    FragmentManager fi ;
    private OnFragmentInteractionListener mListener;

    public StoveFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment StoveFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static StoveFragment newInstance(String param1, String param2) {

        StoveFragment fragment = new StoveFragment();
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
        View v = inflater.inflate(R.layout.fragment_stove, container, false);
        ImageView imageView = v.findViewById(R.id.switchOff);
        mChart = (PieChart) v.findViewById(R.id.pieChart1);
        mChart.getDescription().setEnabled(false);

        mChart.setCenterTextSize(15f);
        mChart.setHoleRadius(80f);
        mChart.setTransparentCircleRadius(50f);
        Legend l = mChart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        l.setOrientation(Legend.LegendOrientation.VERTICAL);
        l.setDrawInside(false);
//        mChart.setCenterText(generateCenterText());
//        mChart.setData(generatePieData());
        final Handler handler = new Handler();
        Timer timer = new Timer();
        TimerTask backtask = new TimerTask() {
            @Override
            public void run() {
                handler.post(new Runnable() {
                    public void run() {
                        try {
                            //To task in this. Can do network operation Also
                            new TempStatusController().start(mChart);
                            Log.d("check","Check Run" );
                        } catch (Exception e) {
                            // TODO Auto-generated catch block
                        }
                    }
                });
            }
        };
        timer.schedule(backtask , 0, 20000);
        new TempStatusController().start(mChart);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar();
                new StoveStatusController().start(getContext());
            }
        });
        return v;

    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }
    private SpannableString generateCenterText() {
        SpannableString s = new SpannableString("24°C\nTemperature");
        s.setSpan(new RelativeSizeSpan(6f), 0, 4, 0);
        s.setSpan(new ForegroundColorSpan(Color.GRAY), 4, s.length(), 0);
        return s;
    }
    protected PieData generatePieData() {

        int count = 1;

        ArrayList<PieEntry> entries1 = new ArrayList<PieEntry>();

        for(int i = 0; i < count; i++) {
            entries1.add(new PieEntry((float) ((Math.random() * 60) + 40), "Medium Temp"));
        }

        PieDataSet ds1 = new PieDataSet(entries1, "24 C");
        ds1.setColors(ColorTemplate.VORDIPLOM_COLORS);
        ds1.setSliceSpace(2f);
        ds1.setValueTextColor(Color.WHITE);
        ds1.setValueTextSize(0f);
        PieData d = new PieData(ds1);
        return d;
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
public void progressBar(){
    List<Integer> imageList = new ArrayList<Integer>();
    imageList.add(R.drawable.souchef_medium);
    final FlipProgressDialog border = new FlipProgressDialog();
    border.setImageList(imageList);
    border.setOrientation("rotationY");
    border.setBorderStroke(10);
    border.setDuration(800);
    border.setBorderColor(Color.parseColor("#02A8F3"));
    border.setBackgroundColor(Color.parseColor("#FFFFFF"));
    border.show(getActivity().getFragmentManager(),"");
    Handler handler= new Handler();
    handler.postDelayed(new Runnable() {
        @Override
        public void run() {
            border.dismiss();
        }
    }, 2000);

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
}
