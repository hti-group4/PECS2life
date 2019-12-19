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
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

/***
 * The adapter class for the RecyclerView, contains the PECSCard data
 */
public class PECSCardAdapter extends RecyclerView.Adapter<PECSCardAdapter.ViewHolder> {

    //Member variables
    private Context mContext;
    private ArrayList<PECSCard> mPECSCards;
    ArrayList<PECSCard> mCheckedPECSCards;

    /**
     * Constructor that passes in the PECSCard data and the context
     *
     * @param mPECSCards ArrayList containing the PECSCard data
     * @param context    Context of the application
     */
    PECSCardAdapter(Context context, ArrayList<PECSCard> mPECSCards) {
        this.mContext = context;
        this.mPECSCards = mPECSCards;
        this.mCheckedPECSCards = new ArrayList<>();
    }

    /**
     * Required method for creating the viewholder objects.
     *
     * @param parent   The ViewGroup into which the new View will be added after it is bound to an adapter position.
     * @param viewType The view type of the new View.
     * @return The newly create ViewHolder.
     */
    @NonNull
    @Override
    public PECSCardAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.list_item_with_checkbox, parent, false));
    }

    /**
     * Required method that binds the data to the viewholder.
     *
     * @param holder   The viewholder into which the data should be put.
     * @param position The adapter position.
     */
    @Override
    public void onBindViewHolder(@NonNull PECSCardAdapter.ViewHolder holder, int position) {

        //Get current PECSCard
        PECSCard currentPECSCard = mPECSCards.get(position);

        holder.bindTo(currentPECSCard);

        holder.setItemClickListener((v, pos) -> {
            CheckBox checkBox = (CheckBox) v;

            if (checkBox.isChecked()) {
                holder.setCheckedValueToTrue();
                mCheckedPECSCards.add(mPECSCards.get(pos));

                System.out.println("DEBUG: an item in position " + pos + " checked");
            } else if (!checkBox.isChecked()) {
                holder.setCheckedValueToFalse();
                mCheckedPECSCards.remove(mPECSCards.get(pos));

                System.out.println("DEBUG: an item in position " + pos + " unchecked");
            }
        });

    }

    /**
     * Required method for determining the size of the data set.
     *
     * @return Size of the data set.
     */
    @Override
    public int getItemCount() {
        return mPECSCards.size();
    }

    /**
     * ViewHolder class that represents each row of data in the RecyclerView
     */
    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        //Member Variables for the TextViews
        private TextView mTitleText;
        private ImageView mPECSCardImage;

        private ItemClickListener itemClickListener;

        private boolean mIsChecked;

        /**
         * Constructor for the ViewHolder, used in onCreateViewHolder().
         *
         * @param itemView The rootview of the list_item.xml layout file
         */
        ViewHolder(View itemView) {
            super(itemView);

            //Initialize the views
            mTitleText = itemView.findViewById(R.id.title2);
            mPECSCardImage = itemView.findViewById(R.id.PECSCardImage2);
            CheckBox mCheckBox = itemView.findViewById(R.id.checkBox);

            mCheckBox.setOnClickListener(this);

            mIsChecked = false;
        }

        void bindTo(PECSCard currentPECSCard) {
            //Populate the textviews with data
            mTitleText.setText(currentPECSCard.getTitle());

            // Load the images into the ImageView using the Glide library.
            Glide.with(mContext).load(
                    currentPECSCard.getImageResource()).into(mPECSCardImage);
        }

        void setItemClickListener(ItemClickListener ic) {
            this.itemClickListener = ic;
        }

        /**
         * Handle click to show the AR activity
         *
         * @param view View that is clicked.
         */
        @Override
        public void onClick(View view) {
            this.itemClickListener.onItemClick(view, getLayoutPosition());

            if (this.mIsChecked) {
                Intent arPreviewIntent = new Intent(mContext, ARPreviewActivity.class);
                arPreviewIntent.putExtra("position", getAdapterPosition());
                mContext.startActivity(arPreviewIntent);
            }
        }

        void setCheckedValueToTrue() {
            this.mIsChecked = true;
        }

        void setCheckedValueToFalse() {
            this.mIsChecked = false;
        }
    }
}
