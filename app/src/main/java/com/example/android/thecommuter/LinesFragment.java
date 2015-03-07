package com.example.android.thecommuter;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Spinner;

/**
 * Created by Shubhang on 2/16/2015.
 */
public class LinesFragment extends Fragment {
    int[] icons = {R.drawable.red_circle, R.drawable.blue_circle, R.drawable.brown_circle, R.drawable.green_circle,
            R.drawable.orange_circle, R.drawable.purple_circle, R.drawable.pink_circle, R.drawable.yellow_circle};
    String[] lines = {"Red Line", "Blue Line", "Brown Line", "Green Line", "Orange Line", "Purple Line", "Pink Line", "Yellow Line"};

    ListView list;
    Spinner spinner;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_list, container, false);

        LineAdapter adapter = new LineAdapter(getActivity().getApplicationContext(), icons, lines);
        list = (ListView) rootView.findViewById(R.id.lines_list_view);
        list.setAdapter(adapter);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                long positionId = parent.getItemIdAtPosition(position);
                Intent intent = new Intent(getActivity(), StopsActivity.class);
                intent.putExtra(Intent.EXTRA_TEXT, positionId);

                startActivity(intent);
            }
        });

        return rootView;
    }
}
