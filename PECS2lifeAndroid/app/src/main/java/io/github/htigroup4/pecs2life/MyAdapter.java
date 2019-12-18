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

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

    //Member variables
    private Context mContext;
    private ArrayList<PECSCard> mPECSCards;
    ArrayList<PECSCard> mCheckedPECSCards;

    MyAdapter(Context context, ArrayList<PECSCard> mPECSCards) {
        this.mContext = context;
        this.mPECSCards = mPECSCards;
        this.mCheckedPECSCards = new ArrayList<>();
    }

    @NonNull
    @Override
    public MyAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        //View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.model, null);

        return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.list_item_with_checkbox, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyAdapter.ViewHolder holder, int position) {

        //Get current PECSCard
        PECSCard currentPECSCard = mPECSCards.get(position);

        holder.bindTo(currentPECSCard);

        //holder.nameTxt.setText(mPECSCards.get(position).getName());
        //holder.img.setImageResource(mPECSCards.get(position).getImage());

        holder.setItemClickListener((v, pos) -> {
            CheckBox checkBox = (CheckBox) v;

            if (checkBox.isChecked()) {
                mCheckedPECSCards.add(mPECSCards.get(pos));

                System.out.println(pos + " checked");
            } else if (!checkBox.isChecked()) {
                mCheckedPECSCards.remove(mPECSCards.get(pos));

                System.out.println(pos + " unchecked");
            }
        });

    }

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
        private CheckBox mCheckBox;

        private ItemClickListener itemClickListener;

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
            mCheckBox = itemView.findViewById(R.id.checkBox);

            mCheckBox.setOnClickListener(this);

            // Set the OnClickListener to the entire view.
            itemView.setOnClickListener(this);
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

            Intent arPreviewIntent = new Intent(mContext, ARPreviewActivity.class);
            arPreviewIntent.putExtra("position", getAdapterPosition());
            mContext.startActivity(arPreviewIntent);
        }
    }
}
