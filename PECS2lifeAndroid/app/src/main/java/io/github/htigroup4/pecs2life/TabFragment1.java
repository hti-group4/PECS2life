/*
 * Copyright (C) 2018 Google Inc.
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
        mRecyclerView = view.findViewById(R.id.recyclerView);

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

        // Helper class for creating swipe to dismiss and drag and drop
        // functionality.
//        ItemTouchHelper helper = new ItemTouchHelper(new ItemTouchHelper
//                .SimpleCallback(
//                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT |
//                        ItemTouchHelper.DOWN | ItemTouchHelper.UP,
//                0) {
//            /**
//             * Defines the drag and drop functionality.
//             *
//             * @param recyclerView The RecyclerView that contains the list items
//             * @param viewHolder The PECSCardViewHolder that is being moved
//             * @param target The PECSCardViewHolder that you are switching the
//             *               original one with.
//             * @return true if the item was moved, false otherwise
//             */
//            @Override
//            public boolean onMove(@NonNull RecyclerView recyclerView,
//                                  @NonNull RecyclerView.ViewHolder viewHolder,
//                                  @NonNull RecyclerView.ViewHolder target) {
//                // Get the from and to positions.
//                int from = viewHolder.getAdapterPosition();
//                int to = target.getAdapterPosition();
//
//                // Swap the items and notify the adapter.
//                Collections.swap(mPECSCardsData, from, to);
//                mAdapter.notifyItemMoved(from, to);
//                return true;
//            }
//
//            /**
//             * Defines the swipe to dismiss functionality.
//             *
//             * @param viewHolder The viewholder being swiped.
//             * @param direction The direction it is swiped in.
//             */
//            @Override
//            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder,
//                                 int direction) {
//                // Remove the item from the dataset.
//                mPECSCardsData.remove(viewHolder.getAdapterPosition());
//                // Notify the adapter.
//                mAdapter.notifyItemRemoved(viewHolder.getAdapterPosition());
//            }
//        });
//
//        // Attach the helper to the RecyclerView.
//        helper.attachToRecyclerView(mRecyclerView);

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
