package com.friendlines.view.homefragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.friendlines.R;
import com.friendlines.controller.Controller;
import com.friendlines.controller.adapters.RequestsAdapter;
import com.friendlines.model.Friendship;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class NotificationsFragment extends Fragment {

    RecyclerView recyclerView;
    RequestsAdapter adapter;

    public NotificationsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_notifications, container, false);
        recyclerView = view.findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        List<Friendship> requests = filterFriendRequest(Controller.getInstance().getDto().getFriendships());
        adapter = new RequestsAdapter(requests,getContext());
        recyclerView.setAdapter(adapter);

        return view;



    }

    private List<Friendship> filterFriendRequest(List<Friendship> friendships)
    {
        List<Friendship> requests = new ArrayList<>();
        for(Friendship friendship : friendships)
        {
            if(friendship.getStatus().equals(Friendship.PENDING_STATUS) && friendship.getReceiver_id().equals(Controller.getInstance().getDto().getUser().getId()))
            {
                requests.add(friendship);
            }
        }
        return requests;
    }

}
