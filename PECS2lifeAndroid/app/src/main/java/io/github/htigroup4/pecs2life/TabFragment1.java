/*
 * Copyright (C) of the original layout file: 2018 Google Inc.
 * Copyright (C) of the edited file: 2019 hti-group4 (Arttu Ylhävuori, Louis Sosa and Tamilselvi Jayavelu).
 * Changes made to this file: added the content for initializing data for the RecyclerView.
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

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class TabFragment1 extends Fragment {

    //Member variables
    private ArrayList<PECSCard> mPECSCardsData;
    private RecyclerView mRecyclerView;
    private PECSCardAdapter mAdapter;


    public TabFragment1() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.tab_fragment1, container, false);

        //Initialize the RecyclerView
        mRecyclerView = view.findViewById(R.id.recyclerView1);

        // Get the appropriate column count.
        int gridColumnCount = getResources().getInteger(R.integer.grid_column_count);

        // Set the Layout Manager.
        mRecyclerView.setLayoutManager(new GridLayoutManager(
                getContext(), gridColumnCount));

        //Initialize the ArrayList that will contain the data
        mPECSCardsData = new ArrayList<>();

        //Initialize the adapter and set it ot the RecyclerView
        mAdapter = new PECSCardAdapter(getContext(), mPECSCardsData);
        mRecyclerView.setAdapter(mAdapter);

        //Get the data
        initializeData();

        return view;
    }

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
        mAdapter.notifyDataSetChanged();
    }

}
