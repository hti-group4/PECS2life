package io.github.htigroup4.pecs2life;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CardListAdapter2 extends RecyclerView.Adapter<CardListAdapter2.CardViewHolder> {

    private final LayoutInflater mInflater;
    private List<Card2> mCards; // Cached copy of cards
    private ItemClickListener mClickListener;

    CardListAdapter2(Context context) {
        mInflater = LayoutInflater.from(context);
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
            Card2 current = mCards.get(position);
            holder.cardItemView.setText(current.getTitle());
            holder.imageItemView.setImageResource(current.getImageResource());
        } else {
            // Covers the case of data not being ready yet.
            holder.cardItemView.setText(R.string.no_card_text);
            holder.imageItemView.setImageResource(R.drawable.logo_splash);
        }

    }

    void setCards(List<Card2> cards) {
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

        public CardViewHolder(@NonNull View itemView) {
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
    public Card2 getItem(int id) {
        return mCards.get(id);
    }

    // allows clicks events to be caught
    public void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}
