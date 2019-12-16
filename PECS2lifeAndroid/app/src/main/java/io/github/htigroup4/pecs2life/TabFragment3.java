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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;


public class TabFragment3 extends Fragment {

    StringBuffer sb = null;
    MyAdapter adapter;


    public TabFragment3() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.tab_fragment3, container, false);

        adapter = new MyAdapter(getContext(), getPlayers());

        FloatingActionButton floatingActionButton = view.findViewById(R.id.floatingActionButton3);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sb = new StringBuffer();

                for (Player p : adapter.checkedPlayers) {
                    sb.append(p.getName());
                    sb.append("\n");
                }

                if (adapter.checkedPlayers.size() > 0) {
                    Toast.makeText(getContext(), sb.toString(), Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getContext(), "Please Check Players", Toast.LENGTH_SHORT).show();
                }
            }
        });

        RecyclerView rv = view.findViewById(R.id.recyclerView4);

        int gridColumnCount = getResources().getInteger(R.integer.grid_column_count);

        rv.setLayoutManager(new GridLayoutManager(getContext(), gridColumnCount));
        rv.setItemAnimator(new DefaultItemAnimator());
        rv.setAdapter(adapter);

        return view;
    }

    private ArrayList<Player> getPlayers() {
        ArrayList<Player> players = new ArrayList<>();

        Player p = new Player("Item 1", R.drawable.img_banana);
        players.add(p);

        p = new Player("Item 2", R.drawable.img_apple);
        players.add(p);

        p = new Player("Item 3", R.drawable.img_carrot);
        players.add(p);

        p = new Player("Item 4", R.drawable.img_noodle_meal);
        players.add(p);

        p = new Player("Item 5", R.drawable.img_red_pepper);
        players.add(p);

        p = new Player("Item 6", R.drawable.img_sandwich);
        players.add(p);

        p = new Player("Item 7", R.drawable.img_hamburger);
        players.add(p);

        return players;
    }

}
