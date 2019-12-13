

package io.github.htigroup4.pecs2life;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;
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
public class TabFragment2 extends Fragment {

    //Member variables
    private RecyclerView mRecyclerView;
    private ArrayList<MusicData> arrayList;
    private FirebaseRecyclerOptions<DataSetFire> options;
    private FirebaseRecyclerAdapter<DataSetFire, FirebaseViewHolder> adapter;
    private DatabaseReference databaseReference;


    public TabFragment2() {
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
        View view = inflater.inflate(R.layout.tab_fragment2, container, false);

        //Initialize the RecyclerView
        mRecyclerView = view.findViewById(R.id.recyclerView3);

        mRecyclerView.setHasFixedSize(true);

        // Get the appropriate column count.
        int gridColumnCount = getResources().getInteger(R.integer.grid_column_count);

        // Set the Layout Manager.
        mRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), gridColumnCount));

        arrayList = new ArrayList<>();
        for (int i = 1; i <= 4; i++) {
            arrayList.add(new MusicData(getString(R.string.song_string) + " " + i));
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

                holder.itemView.setOnClickListener(view1 -> Toast.makeText(getContext(), getString(R.string.music_toast_1) + model.getName() + getString(R.string.music_toast_2) + position, Toast.LENGTH_LONG).show());
            }

            @NonNull
            @Override
            public FirebaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                return new FirebaseViewHolder(LayoutInflater.from(getContext()).inflate(R.layout.row, parent, false));
            }
        };

        // set the adapter to the RecyclerView
        mRecyclerView.setAdapter(adapter);

        // If there is more than one column, disable swipe to dismiss
        int swipeDirs;

        if (gridColumnCount > 1) {
            swipeDirs = 0;
        } else {
            swipeDirs = ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;
        }

        // Helper class for creating swipe to dismiss and drag and drop
        // functionality.
        ItemTouchHelper helper = new ItemTouchHelper(new ItemTouchHelper
                .SimpleCallback(
                0,
                swipeDirs) {

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
                // get the index of the last card
                int i = arrayList.size() - 1;

                DatabaseReference child = databaseReference.child("song" + i);
                child.removeValue();
                databaseReference.keepSynced(true);

                arrayList.remove(i);

                // Notify the adapter.
                adapter.notifyItemRemoved(viewHolder.getAdapterPosition());
            }
        });

        helper.attachToRecyclerView(mRecyclerView);

        // Removes all the music cards and recreates them to the database:
        FloatingActionButton floatingActionButton = view.findViewById(R.id.floatingActionButton2);

        // if the device is NOT a tablet (ie. a mobile device = a teacher device), allow the functionality
        if (!getResources().getBoolean(R.bool.isTablet)) {
            floatingActionButton.setOnClickListener(view12 -> {
                databaseReference.removeValue();

                arrayList.clear();

                for (int i = 1; i <= 4; i++) {
                    MusicData musicData = new MusicData(getString(R.string.song_string) + " " + i);
                    arrayList.add(musicData);

                    int j = i - 1;
                    DatabaseReference child = databaseReference.child("song" + j);
                    child.setValue(musicData);
                }
            });
        } else {
            floatingActionButton.hide();
        }
        return view;
    }

}
