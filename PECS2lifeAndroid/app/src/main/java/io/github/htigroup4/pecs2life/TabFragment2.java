

package io.github.htigroup4.pecs2life;


import android.content.res.TypedArray;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Collections;


/**
 * A simple {@link Fragment} subclass.
 */
public class TabFragment2 extends Fragment {

    //Member variables
    private ArrayList<PECSCard> mPECSCardsData;
    private RecyclerView mRecyclerView;
    private PECSCardAdapter mAdapter;

    public TabFragment2() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.tab_fragment2, container, false);

        //Initialize the RecyclerView
        mRecyclerView = view.findViewById(R.id.recyclerView2);

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
        ItemTouchHelper helper = new ItemTouchHelper(new ItemTouchHelper
                .SimpleCallback(
                0,
                ItemTouchHelper.LEFT |
                        ItemTouchHelper.RIGHT) {

            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView,
                                  @NonNull RecyclerView.ViewHolder viewHolder,
                                  @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            /**
             * Defines the swipe to dismiss functionality.
             *
             * @param viewHolder The viewholder being swiped.
             * @param direction The direction it is swiped in.
             */
            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder,
                                 int direction) {
                // Remove the item from the dataset.
                mPECSCardsData.remove(viewHolder.getAdapterPosition());
                // Notify the adapter.
                mAdapter.notifyItemRemoved(viewHolder.getAdapterPosition());
            }
        });

        // Attach the helper to the RecyclerView.
        helper.attachToRecyclerView(mRecyclerView);


        FloatingActionButton floatingActionButton = view.findViewById(R.id.floatingActionButton);
        floatingActionButton.setOnClickListener(view1 -> initializeData());

        return view;
    }

    /**
     * Method for initializing the PECSCards data from resources.
     */
    private void initializeData() {
        //Get the resources from the XML file
        String[] PECSCardsList = getResources().getStringArray(R.array.music_titles);

        TypedArray PECSCardsImageResources =
                getResources().obtainTypedArray(R.array.music_images);

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
