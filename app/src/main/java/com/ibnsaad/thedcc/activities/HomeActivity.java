package com.ibnsaad.thedcc.activities;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.Toast;

import com.ibnsaad.thedcc.R;
import com.ibnsaad.thedcc.adapter.UsersAdapterGridScrollProgress;
import com.ibnsaad.thedcc.model.User;
import com.ibnsaad.thedcc.utils.Tools;
import com.ibnsaad.thedcc.widget.SpacingItemDecoration;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {

    private int item_per_display = 5;
    private UsersAdapterGridScrollProgress mAdapter;
    private RecyclerView recyclerView;
    private List<User> users;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        initComponent();
    }


    private void initComponent() {
        users = new ArrayList<>();

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        recyclerView.addItemDecoration(new SpacingItemDecoration(2, Tools.dpToPx(this, 8), true));
        recyclerView.setHasFixedSize(true);

        //set data and list adapter
        mAdapter = new UsersAdapterGridScrollProgress(this, item_per_display, generateListItems(item_per_display));
        recyclerView.setAdapter(mAdapter);

        mAdapter.setOnLoadMoreListener(new UsersAdapterGridScrollProgress.OnLoadMoreListener() {
            @Override
            public void onLoadMore(int current_page) {
                loadNextData();
            }
        });

        mAdapter.setOnItemClickListener(new UsersAdapterGridScrollProgress.OnItemClickListener() {
            @Override
            public void onItemClick(View view, User obj, int position) {
                Toast.makeText(HomeActivity.this, obj.getKnownAs(), Toast.LENGTH_SHORT).show();
            }
        });

    }


    private List<User> generateListItems(int count) {
        List<User> users = new ArrayList<>();
        users.add(new User("Bassel faisal"));
        users.add(new User("Bassel faisal"));
        users.add(new User("eslam faisal"));
        users.add(new User("Bassel faisal"));
        users.add(new User("eslam faisal"));

        return users;
    }

    private void loadNextData() {
        mAdapter.setLoading();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mAdapter.insertData(generateListItems(item_per_display));
            }
        }, 2000);
    }

}
