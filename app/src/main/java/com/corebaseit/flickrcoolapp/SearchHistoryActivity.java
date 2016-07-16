package com.corebaseit.flickrcoolapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.corebaseit.flickrcoolapp.restful.SearchJSONObjects;

/**
 * Created by vbevia on 15/07/16.
 */

public class SearchHistoryActivity extends Activity {

    public static final int RESULT_CODE = 101;
    public static String HISTORY_ITEM_EXTRA = "extra.history.item";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_history);

        initView();
    }

    private void initView(){
        String[] array = new String[SearchJSONObjects.FlickrCoolApp.historyList.size()];
        SearchJSONObjects.FlickrCoolApp.historyList.toArray(array);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, array);
        ListView listView = (ListView) findViewById(R.id.listView);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent result = new Intent();
                result.putExtra(HISTORY_ITEM_EXTRA, (String)parent.getItemAtPosition(position));
                setResult(RESULT_OK, result);
                finish();
            }
        });
    }
}
