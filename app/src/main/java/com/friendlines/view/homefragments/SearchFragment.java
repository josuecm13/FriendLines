package com.friendlines.view.homefragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.friendlines.R;
import com.friendlines.controller.ControlException;
import com.friendlines.controller.Controller;
import com.friendlines.controller.adapters.SearchAdapter;
import com.friendlines.controller.listeners.TaskListener;
import com.friendlines.model.User;
import com.friendlines.view.MainActivity;


/**
 * A simple {@link Fragment} subclass.
 */
public class SearchFragment extends Fragment {


    RecyclerView recyclerView;
    SearchAdapter adapter;
    EditText searchTextfield;

    public SearchFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        recyclerView = view.findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new SearchAdapter(getContext());
        recyclerView.setAdapter(adapter);
        searchTextfield = view.findViewById(R.id.search_edit_text);
        searchTextfield.setOnEditorActionListener(new EditText.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent event) {
                if(actionId == EditorInfo.IME_ACTION_DONE ||
                   actionId == EditorInfo.IME_ACTION_SEARCH ||
                   event != null &&
                   event.getAction() == KeyEvent.ACTION_DOWN &&
                   event.getKeyCode() == KeyEvent.KEYCODE_ENTER){
                    if(event == null || !event.isShiftPressed()) {
                        adapter.reset();
                        String text = searchTextfield.getText().toString();
                        try {
                            Log.d(Controller.TAG, "onEditorAction: " + text);
                            Controller.getInstance().queryUsers(SearchFragment.this.getActivity(), text, new TaskListener<User>() {
                                @Override
                                public void onSuccess(User user) {
                                    adapter.addUser(user);
                                }

                                @Override
                                public void onFailure(ControlException exception) {
                                    Log.d(Controller.TAG, exception.getMessage());
                                }
                            });
                        } catch(ControlException ex) {
                            Log.d(Controller.TAG, ex.getMessage());
                        }
                    }
                }
                return false;
            }
        });
        return view;
    }

}
