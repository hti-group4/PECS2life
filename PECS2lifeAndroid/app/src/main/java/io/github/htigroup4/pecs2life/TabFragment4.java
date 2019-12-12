

package io.github.htigroup4.pecs2life;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;


/**
 * A simple {@link Fragment} subclass.
 */
public class TabFragment4 extends Fragment {

    private DatabaseReference databaseReference;

    public TabFragment4() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.tab_fragment4, container, false);

        databaseReference = FirebaseDatabase.getInstance().getReference().child("Music");

        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("name","Song 1");
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
