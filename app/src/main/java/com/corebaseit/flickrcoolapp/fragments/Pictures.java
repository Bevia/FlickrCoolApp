package com.corebaseit.flickrcoolapp.fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

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

    @BindView(R.id.editSearch)
    EditText editSearch;

    @BindView(R.id.fab)
    FloatingActionButton fab;

    private PhotoAdapter adapter;
    private SearchJSONObjects photoSearch;
    private String EXTRA_PHOTO_TRANSFER = "PHOTO_URL";
    private String EXTRA_TEXT_TRANSFER = "TITLE_JSON";

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
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        myRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        initView();

        fab.setVisibility(View.GONE);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hideTheSoftKeyboardIfStillShown();
            }
        });
    }

    private void initView(){

        photoSearch = new SearchJSONObjects(getActivity(), this);

        editSearch.setOnTouchListener(new View.OnTouchListener(){
            public boolean onTouch(View view, MotionEvent motionEvent) {
                // make fab visible againg...
                fab.setVisibility(View.VISIBLE);
                return false;
            }
        });

        editSearch.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { fab.setVisibility(View.VISIBLE);}

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
    public void OnPhotosReceived(final Photos photos) {

        if(photos == null || photos.getTotal() == 0){
            Toast.makeText(getActivity(), R.string.no_results, Toast.LENGTH_SHORT).show();
            return;
        }

        adapter = new PhotoAdapter(getActivity(), photos.getPhotos());
        myRecyclerView.setAdapter(adapter);

    }

    public void hideTheSoftKeyboardIfStillShown() {
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(editSearch.getWindowToken(), 0);
        fab.setVisibility(View.GONE);
        }

    @Override
    public void onResume() {
        super.onResume();
        fab.setVisibility(View.GONE);
    }

    @Override
    public void onStop() {
        photoSearch.stop();
        super.onStop();
    }
}