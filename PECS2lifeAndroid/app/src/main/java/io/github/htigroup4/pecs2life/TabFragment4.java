

package io.github.htigroup4.pecs2life;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class TabFragment4 extends Fragment {

    private DatabaseReference databaseReference;
    private ArrayList<MusicData> arrayList;

    public TabFragment4() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.tab_fragment4, container, false);


        String[] songs = {"Song 1", "Song 2", "Song 3", "Song 4"};

        databaseReference = FirebaseDatabase.getInstance().getReference().child("Music");
        //databaseReference.removeValue();

        for (int i = 0; i < songs.length; i++) {
            MusicData musicData = new MusicData(songs[i]);
            DatabaseReference child = databaseReference.child("song" + i);
            child.setValue(musicData);
        }

//        databaseReference.orderByChild("name").equalTo("Song 6").addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                System.out.println("DEBUG INFO: " + dataSnapshot.getValue());
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });

        //DatabaseReference list = databaseReference.child("list");
        //list.removeValue();

        //databaseReference.removeValue();

        //arrayList = new ArrayList<>();

//        for (String song : songs) {
//            MusicData musicData = new MusicData(song);
//
//            databaseReference.push().setValue(musicData);
//
//            //arrayList.add(musicData);
//        }

        //databaseReference.child("list").setValue(arrayList);

        //HashMap<String, String> hashMap = new HashMap<>();
        //hashMap.put("name","Song 1");
        //hashMap.put("name","Song 2");


        //databaseReference = FirebaseDatabase.getInstance().getReference().child("Test");

        //HashMap<String, String> hashMap = new HashMap<>();
        //hashMap.put("name", "Arttu");

        //databaseReference.push().setValue(hashMap);

        //databaseReference.child("user1").push().setValue(hashMap);

        //databaseReference.setValue(new HashMap<String, String>().put("like", "Facebook"));

        //DatabaseReference user1 = databaseReference.child("user1");

//        String key = user1.getKey();
//
//        System.out.println("DEBUG key: " + key);
//
//        String key2 = databaseReference.getKey();
//
//        System.out.println("DEBUG key: " + key2);

        //user1.removeValue();


        return view;
    }
}
