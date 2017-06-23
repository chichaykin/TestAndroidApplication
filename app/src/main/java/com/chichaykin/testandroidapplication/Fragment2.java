package com.chichaykin.testandroidapplication;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnFragmentInteractionListener} interface
 * to handle interaction events.
 */
public class Fragment2 extends Fragment {

    private OnFragmentInteractionListener mListener;
    private TextView coutries;
    private TextView matrixSize;
    private View resultView;
    private View noDataView;

    public Fragment2() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_fragment2, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        coutries = (TextView) view.findViewById(R.id.countries);
        matrixSize = (TextView) view.findViewById(R.id.size);
        resultView = view.findViewById(R.id.result_view);
        noDataView = view.findViewById(R.id.no_data);
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

    public void setData(Result result) {
        if (result != null) {
            noDataView.setVisibility(View.GONE);
            resultView.setVisibility(View.VISIBLE);
            matrixSize.setText(getString(R.string.matrix, result.getRows(), result.getColumns()));
            coutries.setText(getString(R.string.countries, result.getCountries()));
        } else {
            noDataView.setVisibility(View.VISIBLE);
            resultView.setVisibility(View.GONE);
        }
    }
}
