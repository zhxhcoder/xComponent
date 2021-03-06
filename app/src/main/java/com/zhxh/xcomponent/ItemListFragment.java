package com.zhxh.xcomponent;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.zhxh.xcomponent.dummy.DummyContent;


public class ItemListFragment extends Fragment {

    public ItemListFragment() {
    }

    @SuppressWarnings("unused")
    public static ItemListFragment newInstance() {
        ItemListFragment fragment = new ItemListFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list_layout, container, false);

        // Set the adapter
        if (view instanceof ListView) {
            ListView listView = (ListView) view;

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Toast.makeText(getActivity(), "onItemClick " + position, Toast.LENGTH_LONG).show();
                }
            });

            listView.setAdapter(new MListAdapter(DummyContent.INSTANCE.getItems()));
        }
        return view;
    }


}
