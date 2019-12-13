

package io.github.htigroup4.pecs2life;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Objects;


/**
 * A simple {@link Fragment} subclass.
 */
public class TabFragment3 extends Fragment {

    //Member variables
    private RecyclerView mRecyclerView;
    private ArrayList<MusicData> arrayList;
    private FirebaseRecyclerOptions<DataSetFire> options;
    private FirebaseRecyclerAdapter<DataSetFire, FirebaseViewHolder> adapter;
    private DatabaseReference databaseReference;


    public TabFragment3() {
        // Required empty public constructor
    }

    @Override
    public void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        adapter.stopListening();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.tab_fragment3, container, false);

        //Initialize the RecyclerView
        mRecyclerView = view.findViewById(R.id.recyclerView3);

        mRecyclerView.setHasFixedSize(true);

        // Get the appropriate column count.
        int gridColumnCount = getResources().getInteger(R.integer.grid_column_count);

        // Set the Layout Manager.
        mRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), gridColumnCount));

        arrayList = new ArrayList<>();
        for (int i = 1; i <= 4; i++) {
            arrayList.add(new MusicData("Laulu " + i));
        }


        databaseReference = FirebaseDatabase.getInstance().getReference().child("Music");
        databaseReference.keepSynced(true);

        options = new FirebaseRecyclerOptions.Builder<DataSetFire>().setQuery(databaseReference, DataSetFire.class).build();

        adapter = new FirebaseRecyclerAdapter<DataSetFire, FirebaseViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull FirebaseViewHolder holder, int position, @NonNull DataSetFire model) {
                holder.name.setText(model.getName());
                //Glide.with(getContext()).load(
                //        model.getImageResource()).into(holder.musicImage); // drawable/img_note_semiquaver

                Glide.with(Objects.requireNonNull(getContext())).load(
                        R.drawable.img_note_semiquaver).into(holder.musicImage);

                holder.itemView.setOnClickListener(view1 -> Toast.makeText(getContext(), "DEBUG: " + model.getName() + " clicked in position " + position, Toast.LENGTH_LONG).show());
            }

            @NonNull
            @Override
            public FirebaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                return new FirebaseViewHolder(LayoutInflater.from(getContext()).inflate(R.layout.row, parent, false));
            }
        };

        // set the adapter to the RecyclerView
        mRecyclerView.setAdapter(adapter);

        // Helper class for creating swipe to dismiss and drag and drop
        // functionality.
        ItemTouchHelper helper = new ItemTouchHelper(new ItemTouchHelper
                .SimpleCallback(
                0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {

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

                //String[] songs = {"Laulu 1", "Laulu 2", "Laulu 3", "Laulu 4"};

                // get the position of the card: e.g. 0th is the first one
                //int position = viewHolder.getAdapterPosition();

                int i = arrayList.size() - 1;

                DatabaseReference child = databaseReference.child("song" + i);
                child.removeValue();
                databaseReference.keepSynced(true);

                arrayList.remove(i);

                // 0th position -> song[last]


                // Remove the item from the dataset.
                //DatabaseReference child = databaseReference.child("song" + position);
                //child.removeValue();

                // Notify the adapter.
                adapter.notifyItemRemoved(viewHolder.getAdapterPosition());

                // Remove the item from the dataset.
                //mPECSCardsData.remove(viewHolder.getAdapterPosition());
                // Notify the adapter.
                //mAdapter.notifyItemRemoved(viewHolder.getAdapterPosition());
            }
        });

        helper.attachToRecyclerView(mRecyclerView);

        // Removes all the music cards and recreates them to the database:
        FloatingActionButton floatingActionButton = view.findViewById(R.id.floatingActionButton2);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //String[] songs = {"Laulu 1", "Laulu 2", "Laulu 3", "Laulu 4"};
                //databaseReference = FirebaseDatabase.getInstance().getReference().child("Music");
                databaseReference.removeValue();

                arrayList.clear();

                for (int i = 1; i <= 4; i++) {
                    MusicData musicData = new MusicData("Laulu " + i);
                    arrayList.add(musicData);

                    int j = i - 1;
                    DatabaseReference child = databaseReference.child("song" + j);
                    child.setValue(musicData);


                }

//                for (int i = 0; i < songs.length; i++) {
//                    MusicData musicData = new MusicData(songs[i]);
//                    DatabaseReference child = databaseReference.child("song" + i);
//                    child.setValue(musicData);
//                }
            }
        });

        return view;
    }

}
