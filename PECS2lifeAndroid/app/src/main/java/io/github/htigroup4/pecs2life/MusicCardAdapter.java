package io.github.htigroup4.pecs2life;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

/***
 * The adapter class for the RecyclerView, contains the MusicCard data
 */
public class MusicCardAdapter extends RecyclerView.Adapter<MusicCardAdapter.ViewHolder> {

    //Member variables
    private ArrayList<PECSCard> mPECSCardData;
    private Context mContext;
    private PECSCard selected;

    /**
     * Constructor that passes in the MusicCard data and the context
     *
     * @param PECSCardData ArrayList containing the MusicCard data
     * @param context      Context of the application
     */
    MusicCardAdapter(Context context, ArrayList<PECSCard> PECSCardData) {
        this.mPECSCardData = PECSCardData;
        this.mContext = context;
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
    public MusicCardAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.list_item, parent, false));
    }

    /**
     * Required method that binds the data to the viewholder.
     *
     * @param holder   The viewholder into which the data should be put.
     * @param position The adapter position.
     */
    @Override
    public void onBindViewHolder(MusicCardAdapter.ViewHolder holder, int position) {
        //Get current MusicCard
        PECSCard currentPECSCard = mPECSCardData.get(position);

        //Populate the textviews with data
        holder.bindTo(currentPECSCard, currentPECSCard == selected);
    }

    /**
     * Required method for determining the size of the data set.
     *
     * @return Size of the data set.
     */
    @Override
    public int getItemCount() {
        return mPECSCardData.size();
    }

    /**
     * ViewHolder class that represents each row of data in the RecyclerView
     */
    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        //Member Variables for the views
        private TextView mTitleText;
        private ImageView mPECSCardImage;

        /**
         * Constructor for the ViewHolder, used in onCreateViewHolder().
         *
         * @param itemView The rootview of the list_item.xml layout file
         */
        ViewHolder(View itemView) {
            super(itemView);

            //Initialize the views
            mTitleText = itemView.findViewById(R.id.title);
            mPECSCardImage = itemView.findViewById(R.id.PECSCardImage);

            // Set the OnClickListener to the entire view.
            itemView.setOnClickListener(this);
        }

        void bindTo(PECSCard currentPECSCard, boolean isSelected) {
            //Populate the textviews with data
            mTitleText.setText(currentPECSCard.getTitle());

            // Load the images into the ImageView using the Glide library.
            Glide.with(mContext).load(
                    currentPECSCard.getImageResource()).into(mPECSCardImage);

        }

        /**
         * Handle click to show the AR activity
         *
         * @param view View that is clicked.
         */
        @Override
        public void onClick(View view) {
            PECSCard currentPECSCard = mPECSCardData.get(getAdapterPosition());
            String title = currentPECSCard.getTitle();

//            Intent arPreviewIntent = new Intent(mContext, ARPreviewActivity.class);
//            arPreviewIntent.putExtra("position", getAdapterPosition());
//            mContext.startActivity(arPreviewIntent);

            Toast toast = Toast.makeText(mContext, "DEBUG: " + title + " clicked",
                    Toast.LENGTH_SHORT);
            toast.show();
        }


//        @Override
//        public void onClick(View view) {
//            // Get the position of the item that was clicked.
//            int mPosition = getLayoutPosition();
//            // Use that to access the affected item in mWordList.
//            String element = mWordList.get(mPosition);
//            // Change the word in the mWordList.
//            mWordList.set(mPosition, "Clicked! " + element);
//            // Notify the adapter, that the data has changed so it can
//            // update the RecyclerView to display the data.
//            mAdapter.notifyDataSetChanged();
//        }
    }
}


