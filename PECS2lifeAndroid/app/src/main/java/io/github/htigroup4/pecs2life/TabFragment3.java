/*
 * Copyright (C) of the original layout file: 2018 Google Inc.
 * Copyright (C) of the edited file: 2019 hti-group4 (Arttu Ylhävuori, Louis Sosa and Tamilselvi Jayavelu).
 * Changes made to this file: TODO
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.github.htigroup4.pecs2life;


import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * A simple {@link Fragment} subclass.
 */
//public class TabFragment3 extends Fragment implements MyRecyclerViewAdapter.ItemClickListener {
public class TabFragment3 extends Fragment {

//    private ArrayList<Integer> viewColors;
//    private MyRecyclerViewAdapter adapter;
//    private ArrayList<String> animalNames;

    public TabFragment3() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.tab_fragment3, container, false);

//        // data to populate the RecyclerView with
//        viewColors = new ArrayList<>();
//        viewColors.add(Color.BLUE);
//        viewColors.add(Color.YELLOW);
//        viewColors.add(Color.MAGENTA);
//        viewColors.add(Color.RED);
//        viewColors.add(Color.BLACK);
//        viewColors.add(Color.CYAN);
//
//        // data to populate the RecyclerView with
//        animalNames = new ArrayList<>();
//        animalNames.add("Horse");
//        animalNames.add("Cow");
//        animalNames.add("Camel");
//        animalNames.add("Sheep");
//        animalNames.add("Goat");
//        animalNames.add("Lamb");
//
//        // set up the RecyclerView
//        RecyclerView recyclerView = view.findViewById(R.id.rvAnimals);
//        LinearLayoutManager layoutManager
//                = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
//        recyclerView.setLayoutManager(layoutManager);
//        adapter = new MyRecyclerViewAdapter(getContext(), viewColors, animalNames);
//        adapter.setClickListener(this);
//        recyclerView.setAdapter(adapter);

        Button button1 = view.findViewById(R.id.buttonInsertItem);
        button1.setOnClickListener(view1 -> {

//            SharedPreferences prefs = getContext().getSharedPreferences("io.github.htigroup4.pecs2life", Context.MODE_PRIVATE);
//            Set<String> set = prefs.getStringSet("animalNames", null);
//            List<String> sample = new ArrayList<>(set);
//
//            Toast.makeText(getContext(), "DEBUG: " + sample.get(1), Toast.LENGTH_SHORT).show();

            // the original ones:
            String newItem = "Pig";
            int newColor = Color.GREEN;
            int insertIndex = 2;




            // the original ones:
//                animalNames.add(insertIndex, newItem);
//                viewColors.add(insertIndex, newColor);
//                adapter.notifyItemInserted(insertIndex);
        });

        return view;
    }

    //    @Override
//    public void onItemClick(View view, int position) {
//        Toast.makeText(getContext(), "You clicked " + adapter.getItem(position) + " on item position " + position, Toast.LENGTH_SHORT).show();
//    }
    public interface SubmitListener {

        void onSubmit();
    }

    private SubmitListener onSubmitListener;

    public void setSubmitListener(SubmitListener onSubmitListener) {
        this.onSubmitListener = onSubmitListener;
    }

    public SubmitListener getOnSubmitListener() {
        return onSubmitListener;
    }

}


