package com.example.demo1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.os.Bundle;
import android.widget.SearchView;

import java.util.ArrayList;
import java.util.List;

public class SearchListActivity extends AppCompatActivity {
    private SearchView mSearchView;
    private String mSearchValue;
    private RecyclerView mRecyclerView;
    private SearchAdapter mSearchAdapter=new SearchAdapter();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_list);

        mSearchView=(SearchView) findViewById(R.id.searchView);
        mSearchView.setOnCloseListener(new SearchView.OnCloseListener(){
            @Override
            public boolean onClose() {
                mSearchValue="";
                return false;
            }
        });
        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener(){
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                mSearchValue=newText;
                mSearchAdapter.getFilter().filter(mSearchValue);
                return false;
            }
        });

        mRecyclerView=(RecyclerView)findViewById(R.id.recycleView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mSearchAdapter);

        List<String> items=new ArrayList<>();
        for(int i=0;i<100;++i){
            items.add("this is line "+String.valueOf(i));
        }
        mSearchAdapter.notifyItems(items);
    }
}
