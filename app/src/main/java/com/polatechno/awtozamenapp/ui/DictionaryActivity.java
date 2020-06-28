package com.polatechno.awtozamenapp.ui;

import android.content.Context;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.polatechno.awtozamenapp.R;
import com.polatechno.awtozamenapp.ui.adapter.SearchAdapter;
import com.polatechno.awtozamenapp.database.RealmController;
import com.polatechno.awtozamenapp.database.model.DictionaryItem;

import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

public class DictionaryActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText searchEditText;
    private ArrayList<DictionaryItem> words = new ArrayList<>();
    private SwipeRefreshLayout swipeToRefresh;


    private RealmController realmController;

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dictionary);


        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Words Dictionary");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        realmController = RealmController.getInstance();


        searchEditText = findViewById(R.id.searchText);
        Button searchButton = findViewById(R.id.searchButton);
        mRecyclerView = findViewById(R.id.recyclerView);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);


        swipeToRefresh = findViewById(R.id.swipeToRefresh);

        // Set event listener on search button
        searchButton.setOnClickListener(this);

        swipeToRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                search();
            }
        });

        search();


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.searchButton:
                search();
                break;
            default:
                break;
        }
    }

    private void search() {
        if (swipeToRefresh.isRefreshing()) {
            swipeToRefresh.setRefreshing(false);
        }

        String searchWord = searchEditText.getText().toString().trim();

        words = new ArrayList<>();
        words = realmController.getWordsByQueery(searchWord);


        if (words.size() > 0) {
            // specify an adapter
            mAdapter = new SearchAdapter(words, this);
            mRecyclerView.setAdapter(mAdapter);
            mAdapter.notifyDataSetChanged();

            // Hide Keyboard
            hideKeyboard();
        } else {
            Toast.makeText(this, getString(R.string.no_result), Toast.LENGTH_LONG).show();
        }

    }

    private void hideKeyboard() {
        // Check if no view has focus:
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            assert imm != null;
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }
}
