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


import android.content.res.TypedArray;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;


public class TabFragment3 extends Fragment {

    private StringBuffer sb = null;
    private MyAdapter adapter;

    private ArrayList<PECSCard> mPECSCardsData; // Player -> PECSCard
    private RecyclerView mRecyclerView;
    //private PECSCardAdapter mAdapter;


    public TabFragment3() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.tab_fragment3, container, false);

        //Initialize the RecyclerView
        mRecyclerView = view.findViewById(R.id.recyclerView3);

        // Get the appropriate column count.
        int gridColumnCount = getResources().getInteger(R.integer.grid_column_count);

        // Set the Layout Manager.
        mRecyclerView.setLayoutManager(new GridLayoutManager(
                getContext(), gridColumnCount));

        //Initialize the ArrayList that will contain the data
        mPECSCardsData = new ArrayList<>();

        //Initialize the adapter and set it ot the RecyclerView
        adapter = new MyAdapter(getContext(), mPECSCardsData);
        mRecyclerView.setAdapter(adapter);

        //Get the data
        initializeData();

        //adapter = new MyAdapter(getContext(), getPlayers());

        FloatingActionButton floatingActionButton = view.findViewById(R.id.floatingActionButton3);
        floatingActionButton.setOnClickListener(view1 -> {
            sb = new StringBuffer();

            for (PECSCard card : adapter.mCheckedPECSCards) {
                sb.append(card.getTitle());
                sb.append("\n");
            }

//            for (Player p : adapter.mCheckedPECSCards) {
//                sb.append(p.getName());
//                sb.append("\n");
//            }

            if (adapter.mCheckedPECSCards.size() > 0) {
                Toast.makeText(getContext(), sb.toString(), Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(getContext(), "Please check PECSCard(s)", Toast.LENGTH_LONG).show();
            }
        });

        //RecyclerView rv = view.findViewById(R.id.recyclerView3);

        //RecyclerView rv2 = view.findViewById(R.id.recyclerViewFooter);

//        int gridColumnCount = getResources().getInteger(R.integer.grid_column_count);
//
//        rv.setLayoutManager(new GridLayoutManager(getContext(), gridColumnCount));
//        rv.setItemAnimator(new DefaultItemAnimator());
//        rv.setAdapter(adapter);

        //rv2.setLayoutManager(new GridLayoutManager(getContext(), gridColumnCount));
        //MyAdapter newAdapter = new MyAdapter(getContext(), adapter.mCheckedPECSCards);
        //rv2.setAdapter(newAdapter);


        return view;
    }

//    private ArrayList<Player> getPlayers() {
//        ArrayList<Player> players = new ArrayList<>();
//
//        Player p = new Player("Item 1", R.drawable.img_banana);
//        players.add(p);
//
//        p = new Player("Item 2", R.drawable.img_apple);
//        players.add(p);
//
//        p = new Player("Item 3", R.drawable.img_carrot);
//        players.add(p);
//
//        p = new Player("Item 4", R.drawable.img_kebab_meal);
//        players.add(p);
//
//        p = new Player("Item 5", R.drawable.img_red_pepper);
//        players.add(p);
//
//        p = new Player("Item 6", R.drawable.img_sandwich);
//        players.add(p);
//
//        p = new Player("Item 7", R.drawable.img_hamburger);
//        players.add(p);
//
//        return players;
//    }

    /**
     * Method for initializing the PECSCards data from resources.
     */
    private void initializeData() {
        //Get the resources from the XML file
        String[] PECSCardsList = getResources().getStringArray(R.array.food_titles);

        TypedArray PECSCardsImageResources =
                getResources().obtainTypedArray(R.array.food_images);

        //Clear the existing data (to avoid duplication)
        mPECSCardsData.clear();

        //Create the ArrayList of PECSCards objects with the titles and information about each PECSCard
        for (int i = 0; i < PECSCardsList.length; i++) {
            mPECSCardsData.add(new PECSCard(PECSCardsList[i], PECSCardsImageResources.getResourceId(i, 0)));
        }

        // Recycle the typed array.
        PECSCardsImageResources.recycle();

        //Notify the adapter of the change
        adapter.notifyDataSetChanged();
    }

}
