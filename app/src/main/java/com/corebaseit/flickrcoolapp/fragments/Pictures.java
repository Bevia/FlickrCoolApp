package com.corebaseit.flickrcoolapp.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.corebaseit.flickrcoolapp.R;
import com.corebaseit.flickrcoolapp.adapters.PhotoAdapter;
import com.corebaseit.flickrcoolapp.models.Photos;
import com.corebaseit.flickrcoolapp.restful.SearchJSONObjects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


/**
 * A simple {@link Fragment} subclass.
 */
public class Pictures extends Fragment implements SearchJSONObjects.OnPhotosReceivedListener{

    private Unbinder gridViewBinder;

    @BindView(R.id.recycler_view)
    RecyclerView myRecyclerView;
    private SearchJSONObjects photoSearch;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_pictures, container, false);
        gridViewBinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //setHasOptionsMenu(true);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        myRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        initView();
    }

    private void initView(){

        photoSearch = new SearchJSONObjects(getActivity(), this);

        ((EditText)getActivity().findViewById(R.id.editSearch)).addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                if(!TextUtils.isEmpty(s)){
                    LinearLayoutManager llm = new LinearLayoutManager(getActivity());
                    llm.setOrientation(LinearLayoutManager.VERTICAL);
                    myRecyclerView.setLayoutManager(llm);
                    myRecyclerView.setAdapter(null);
                    photoSearch.search(s.toString());
                }
            }
        });
    }

    @Override
    public void OnPhotosReceived(Photos photos) {
        if(photos == null || photos.getTotal() == 0){return;}
        PhotoAdapter adapter = new PhotoAdapter(getActivity(), photos.getPhotos());
        myRecyclerView.setAdapter(adapter);
    }

    @Override
    public void onStop() {
        photoSearch.stop();
        super.onStop();
    }
}