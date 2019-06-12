package com.friendlines.view.homefragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.friendlines.R;
import com.friendlines.controller.adapters.FriendsAdapter;
import com.friendlines.controller.adapters.RequestsAdapter;
import com.friendlines.model.Friendship;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class FriendsFragment extends Fragment {


    private RecyclerView recyclerView;
    private FriendsAdapter adapter;

    public FriendsFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_friends, container, false);
        recyclerView = view.findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        Friendship item = new Friendship();
        item.setSender_name("Pepito Pérez Rodriguez");
        List<Friendship> requests = new ArrayList<>();
        for (int i = 0; i < 9; i++) {
            requests.add(item);
        }
        adapter = new FriendsAdapter(requests,getContext());
        recyclerView.setAdapter(adapter);

        return view;
    }

}
