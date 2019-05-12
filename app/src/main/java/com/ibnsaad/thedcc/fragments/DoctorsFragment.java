package com.ibnsaad.thedcc.fragments;


import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.ibnsaad.thedcc.R;
import com.ibnsaad.thedcc.activities.HomeActivity;
import com.ibnsaad.thedcc.adapter.UsersAdapterGridScrollProgress;
import com.ibnsaad.thedcc.model.User;
import com.ibnsaad.thedcc.utils.Tools;
import com.ibnsaad.thedcc.widget.SpacingItemDecoration;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class DoctorsFragment extends Fragment {

    private int item_per_display = 10;
    private UsersAdapterGridScrollProgress mAdapter;
    private RecyclerView recyclerView;
    private List<User> users;

    public static DoctorsFragment doctorsFragment;
    public DoctorsFragment() {
        // Required empty public constructor
    }

    public static Fragment getInstance() {
        if (doctorsFragment==null){
            return new DoctorsFragment();
        }else return doctorsFragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_doctors, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initComponent(view);
    }

    private void initComponent(View view) {
        users = new ArrayList<>();

        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        recyclerView.addItemDecoration(new SpacingItemDecoration(2, Tools.dpToPx(getActivity(), 8), true));
        recyclerView.setHasFixedSize(true);

        //set data and list adapter
        mAdapter = new UsersAdapterGridScrollProgress(getActivity(), item_per_display, generateListItems(item_per_display));
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
                Toast.makeText(getActivity(), obj.getKnownAs(), Toast.LENGTH_SHORT).show();
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
