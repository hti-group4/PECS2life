/*
 * Copyright (C) of the original file: 2018 Google Inc.
 * Copyright (C) of the edited file: 2019-2020 hti-group4 (Arttu Ylhävuori, Louis Sosa and Tamilselvi Jayavelu).
 * Changes made to this file: use word "Card" instead of "Word".
 * Added ItemClickListener feature for selecting cards.
 * Use also imageItemView for viewing card images.
 * Added getCardAtPosition helper method.
 * Use also Glide library for caching images. Added Context member variable for Glide library.
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

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class CardListAdapter2 extends RecyclerView.Adapter<CardListAdapter2.CardViewHolder> {

    private final LayoutInflater mInflater;
    private List<Card> mCards; // Cached copy of cards
    private ItemClickListener mClickListener;
    private Context mContext;

    CardListAdapter2(Context context) {
        mInflater = LayoutInflater.from(context);
        mContext = context;
    }

    @NonNull
    @Override
    public CardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.list_item_new2, parent, false);
        return new CardViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CardViewHolder holder, int position) {
        if (mCards != null) {
            Card current = mCards.get(position);
            holder.cardItemView.setText(current.getTitle());
            Glide.with(mContext).load(
                    current.getImageResource()).into(holder.imageItemView);
        } else {
            // Covers the case of data not being ready yet.
            holder.cardItemView.setText(R.string.no_card_text);
            Glide.with(mContext).load(
                    R.drawable.logo_splash).into(holder.imageItemView);
        }

    }

    void setCards(List<Card> cards) {
        mCards = cards;
        notifyDataSetChanged();
    }

    // getItemCount() is called many times, and when it is first called,
    // mCards has not been updated (means initially, it's null, and we can't return null).
    @Override
    public int getItemCount() {
        if (mCards != null) {
            return mCards.size();
        } else return 0;
    }

    class CardViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private final TextView cardItemView;
        private final ImageView imageItemView;

        CardViewHolder(@NonNull View itemView) {
            super(itemView);
            cardItemView = itemView.findViewById(R.id.foodTitle);
            imageItemView = itemView.findViewById(R.id.foodImage);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }
    }

    // convenience method for getting data at click position
    Card getCardAtPosition(int position) {
        return mCards.get(position);
    }

    // allows clicks events to be caught
    void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}
