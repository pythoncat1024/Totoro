package com.pythoncat.totoro.fragment.content;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.pythoncat.totoro.R;
import com.pythoncat.totoro.adapter.listadapter.ToolAdapter;
import com.pythoncat.totoro.model.ToolModel;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ToolsFragment extends Fragment {


    private ListView mListView;

    public ToolsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tools, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mListView = (ListView) view.findViewById(R.id.tools_list);
//        mListView.setAdapter();
        List<ToolModel> data = Moni.createData();
        mListView.setAdapter(new ToolAdapter(getActivity(),mListView, data));
    }

    static class Moni {
        public static List<ToolModel> createData() {
            ArrayList<ToolModel> toolModels = new ArrayList<>();
            for (int i = 0; i < 20; i++) {
                ToolModel object = new ToolModel();
                object.checked = false;
                object.name = "我上了：" + i + " 个totoro";
                toolModels.add(object);
            }
            return toolModels;
        }
    }
}
