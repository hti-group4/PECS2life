/*
 * Copyright (C) of the original file: 2018 Google Inc.
 * Copyright (C) of the edited file: 2019-2020 hti-group4 (Arttu Ylhävuori, Louis Sosa and Tamilselvi Jayavelu).
 * Changes made to this file: use word "Card" instead of "Word".
 * Didn't added unnecessary "deleteAll" method.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.github.htigroup4.pecs2life;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

/**
 * View Model to keep a reference to the card repository and
 * an up-to-date list of all cards.
 * <p>
 * The CardViewModel provides the interface between the UI and the data layer of the app,
 * represented by the repository
 */

public class CardViewModel2 extends AndroidViewModel {
    private CardRepository2 mRepository;
    // Using LiveData and caching what getAlphabetizedCards returns has several benefits:
    // - We can put an observer on the data (instead of polling for changes) and only update the
    //   the UI when the data actually changes.
    // - Repository is completely separated from the UI through the ViewModel.
    private LiveData<List<Card>> mAllCards;

    public CardViewModel2(@NonNull Application application) {
        super(application);
        mRepository = new CardRepository2(application);
        mAllCards = mRepository.getAllCards();
    }

    public LiveData<List<Card>> getAllCards() {
        return mAllCards;
    }

    public void insert(Card card) {
        mRepository.insert(card);
    }

    void deleteCard(Card card) {
        mRepository.deleteCard(card);
    }
}
