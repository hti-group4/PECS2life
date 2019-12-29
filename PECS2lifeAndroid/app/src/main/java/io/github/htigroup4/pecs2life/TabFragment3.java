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


import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

/**
 * A simple {@link Fragment} subclass.
 */

public class TabFragment3 extends Fragment {

    //private WordViewModel mWordViewModel;
    private CardViewModel cardViewModel;

    public TabFragment3() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.tab_fragment3, container, false);

        cardViewModel = ViewModelProviders.of(this).get(CardViewModel.class);

        //mWordViewModel = ViewModelProviders.of(this).get(WordViewModel.class);

        Button button1 = view.findViewById(R.id.button1);
        button1.setOnClickListener(view1 -> {
            String newItem = "Apple";
            int newImage = R.drawable.img_apple;

            Card card = new Card(newItem, newImage);
            cardViewModel.insert(card);

            //String newItem = "BMW";
            //int newColor = Color.MAGENTA;

            //Word word = new Word(newItem, newColor);
            //mWordViewModel.insert(word);
        });

        return view;
    }
}


