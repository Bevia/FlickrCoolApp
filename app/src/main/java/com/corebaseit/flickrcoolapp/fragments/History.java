package com.corebaseit.flickrcoolapp.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.corebaseit.flickrcoolapp.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class History extends Fragment {

    public static String HISTORY_ITEM_EXTRA = "extra.history.item";

    public History() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_history, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // Reload current fragment
       /* Fragment frg = null;
        frg = getActivity().getSupportFragmentManager()
                .findFragmentByTag("Your_Fragment_TAG");
        final FragmentTransaction ft =
                getActivity().getSupportFragmentManager().beginTransaction();
        ft.detach(frg);
        ft.attach(frg);
        ft.commit();*/

        initView();
    }

    private void initView(){

    }

}
