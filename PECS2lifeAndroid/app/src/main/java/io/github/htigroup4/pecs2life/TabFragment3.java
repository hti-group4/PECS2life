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


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * A simple {@link Fragment} subclass.
 */

public class TabFragment3 extends Fragment implements CardListAdapter2.ItemClickListener {

    private CardViewModel2 cardViewModel;
    private CardViewModel cardViewModelSlot;
    private CardListAdapter2 cardListAdapter;

    public TabFragment3() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.tab_fragment3, container, false);

        //Initialize the RecyclerView
        RecyclerView recyclerView = view.findViewById(R.id.recyclerView10);

        cardListAdapter = new CardListAdapter2(getContext());
        cardListAdapter.setClickListener(this);

        recyclerView.setAdapter(cardListAdapter);

        // Get the appropriate column count.
        int gridColumnCount = getResources().getInteger(R.integer.grid_column_count);

        // Set the Layout Manager.
        recyclerView.setLayoutManager(new GridLayoutManager(
                getContext(), gridColumnCount));

        cardViewModel = ViewModelProviders.of(this).get(CardViewModel2.class);

        cardViewModelSlot = ViewModelProviders.of(this).get(CardViewModel.class);

        cardViewModel.getAllCards().observe(this, cards -> {
            // Update the cached copy of the words in the adapter.
            cardListAdapter.setCards(cards);
        });
        return view;
    }

    @Override
    public void onItemClick(View view, int position) {

        Card2 item = cardListAdapter.getCardAtPosition(position);

        Toast.makeText(getContext(), "You clicked " + item.getTitle()
                + " on item position " + position, Toast.LENGTH_SHORT).show();

        cardViewModelSlot.insert(new Card(item.getTitle(), item.getImageResource()));

        // delete the clicked card
        cardViewModel.deleteCard(item);
    }
}


