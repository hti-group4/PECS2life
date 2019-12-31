/*
 * Copyright (C) of the original layout file: 2018 Google Inc.
 * Copyright (C) of the edited file: 2019 hti-group4 (Arttu Ylhävuori, Louis Sosa and Tamilselvi Jayavelu).
 * Changes made to this file: added FirebaseMessaging features for the button & removed the 3rd tab.
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

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.android.volley.toolbox.JsonObjectRequest;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.messaging.FirebaseMessaging;
import com.vlstr.blurdialog.BlurDialog;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * This app offers three view fragments and two tabs to
 * navigate to them.
 */
public class MainActivity extends AppCompatActivity implements CardListAdapter.ItemClickListener {

    private CardViewModel mCardViewModel;
    private CardListAdapter cardListAdapter;
    private CardViewModel2 cardViewModelTab3;

    final private String FCM_API = "https://fcm.googleapis.com/fcm/send";
    final private String serverKey = "key=" + BuildConfig.SERVER_KEY;
    final private String contentType = "application/json";
    final String TAG = "NOTIFICATION TAG";

    String NOTIFICATION_TITLE;
    String NOTIFICATION_MESSAGE;
    String TOPIC;
    int LARGE_ICON;
    int SENDER_ICON;
    String RESPONSE_MESSAGE;

    Handler handler;
    Runnable r;

    /**
     * Creates the content view, sets up the tab layout, and sets up
     * a page adapter to manage views in fragments. The user clicks a tab and
     * navigates to the view fragment.
     *
     * @param savedInstanceState Saved instance state bundle.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Code for the local database:
        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        cardListAdapter = new CardListAdapter(this);
        cardListAdapter.setClickListener(this);
        recyclerView.setAdapter(cardListAdapter);

        recyclerView.setLayoutManager(new LinearLayoutManager(
                this, LinearLayoutManager.HORIZONTAL, false));

        mCardViewModel = ViewModelProviders.of(this).get(CardViewModel.class);

        cardViewModelTab3 = ViewModelProviders.of(this).get(CardViewModel2.class);

        // TODO remove this section when settings for subscription handling has added to the app
        if (getResources().getBoolean(R.bool.isTablet)) { // the device is a tablet = a pupil uses it
            FirebaseMessaging.getInstance().subscribeToTopic("/topics/fromTeacherToPupil");
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE); // if the device is a tablet, use only the landscape orientation:
        } else { // the device is a mobile phone = a teacher uses it
            FirebaseMessaging.getInstance().subscribeToTopic("/topics/fromPupilToTeacher");
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT); // if the device is a mobile phone, use only the portrait orientation:
        }

        // Create an instance of the tab layout from the view.
        TabLayout tabLayout = findViewById(R.id.tab_layout);
        // Set the text for each tab.
        tabLayout.addTab(tabLayout.newTab().setText(R.string.tab_text_1));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.tab_text_2));

        // Set the tabs to fill the entire layout.
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        // Use PagerAdapter to manage page views in fragments.
        // Each page is represented by its own fragment.
        final ViewPager viewPager = findViewById(R.id.pager);
        final PagerAdapter adapter = new PagerAdapter
                (getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(adapter);

        // Setting a listener for clicks.
        viewPager.addOnPageChangeListener(new
                TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(
                new TabLayout.OnTabSelectedListener() {
                    @Override
                    public void onTabSelected(TabLayout.Tab tab) {
                        viewPager.setCurrentItem(tab.getPosition());
                    }

                    @Override
                    public void onTabUnselected(TabLayout.Tab tab) {
                    }

                    @Override
                    public void onTabReselected(TabLayout.Tab tab) {
                    }
                });

        mCardViewModel.getAllCards().observe(this, cards -> {
            // Update the cached copy of the words in the adapter.
            cardListAdapter.setCards(cards);

            // wrap content of the viewPager if there are no cards:
            if (cards.isEmpty()) {
                viewPager.getLayoutParams().height = ViewPager.LayoutParams.WRAP_CONTENT;
                viewPager.requestLayout();
            } else if (getResources().getBoolean(R.bool.isTablet)) {
                // otherwise, use a tested height for viewPager depending on the device (here it's tablet):
                viewPager.getLayoutParams().height = 660; //290
                viewPager.requestLayout();
            } else {
                // a mobile device: use a tested height for viewPager
                viewPager.getLayoutParams().height = 960; //320
                viewPager.requestLayout();
            }
        });

        handler = new Handler();

        r = () -> {
            StringBuilder cardTitles = new StringBuilder();
            int cardsSize = cardListAdapter.getItemCount();
            List<Card> cards = cardListAdapter.getmCards();

            for (int i = 0; i < cards.size(); i++) {
                Card card = cards.get(i);
                String cardTitle = card.getTitle();
                if (cards.size() == 1) {
                    cardTitles = new StringBuilder(cardTitle);
                } else if (i == cards.size() - 1) {
                    cardTitles.append(" and ").append(cardTitle);
                } else if (i != 0) {
                    cardTitles.append(", ").append(cardTitle);
                } else {
                    cardTitles.append(cardTitle);
                }
            }

            if (cardsSize == 0) {
                Toast.makeText(this, "Please select cards to send for the teacher", Toast.LENGTH_LONG).show();
            } else {
                String resultText = "Selected cards (" + cardsSize + " pcs): " + cardTitles;
                Toast.makeText(this, resultText, Toast.LENGTH_LONG).show();

                TOPIC = "/topics/fromPupilToTeacher"; //topic has to match what the receiver subscribed to
                NOTIFICATION_TITLE = getString(R.string.pupil_notification_title);
                NOTIFICATION_MESSAGE = resultText;
                LARGE_ICON = R.drawable.img_pupil;
                SENDER_ICON = R.drawable.img_teacher;
                RESPONSE_MESSAGE = getString(R.string.response_message_help);

                JSONObject notification = new JSONObject();
                JSONObject notificationBody = new JSONObject();

                try {
                    notificationBody.put("title", NOTIFICATION_TITLE);
                    notificationBody.put("message", NOTIFICATION_MESSAGE);
                    notificationBody.put("largeIcon", LARGE_ICON);

                    notification.put("to", TOPIC);
                    notification.put("data", notificationBody);
                } catch (JSONException e) {
                    Log.e(TAG, "onCreate: " + e.getMessage());
                }
                sendNotification(notification, false);
            }
        };

        startHandler();
    }

    @Override
    public void onUserInteraction() {
        super.onUserInteraction();
        stopHandler();
        startHandler();
    }

    public void stopHandler() {
        handler.removeCallbacks(r);
    }

    public void startHandler() {
        handler.postDelayed(r, 10 * 1000); // 10 seconds = 10 * 1000 ms
    }

    public void sendHelpRequest(View view) {
        if (getResources().getBoolean(R.bool.isTablet)) { // the device is a tablet = a pupil uses it
            TOPIC = "/topics/fromPupilToTeacher"; //topic has to match what the receiver subscribed to
            NOTIFICATION_TITLE = getString(R.string.pupil_notification_title);
            NOTIFICATION_MESSAGE = getString(R.string.pupil_notification_message);
            LARGE_ICON = R.drawable.img_pupil;
            SENDER_ICON = R.drawable.img_teacher;
            RESPONSE_MESSAGE = getString(R.string.response_message_help);
        } else { // the device is a mobile phone = a teacher uses it
            TOPIC = "/topics/fromTeacherToPupil"; //topic has to match what the receiver subscribed to
            NOTIFICATION_TITLE = getString(R.string.teacher_notification_title);
            NOTIFICATION_MESSAGE = getString(R.string.teacher_notification_message);
            LARGE_ICON = R.drawable.img_teacher;
            SENDER_ICON = R.drawable.img_pupil;
            RESPONSE_MESSAGE = getString(R.string.response_message_response);
        }

        JSONObject notification = new JSONObject();
        JSONObject notificationBody = new JSONObject();

        try {
            notificationBody.put("title", NOTIFICATION_TITLE);
            notificationBody.put("message", NOTIFICATION_MESSAGE);
            notificationBody.put("largeIcon", LARGE_ICON);

            notification.put("to", TOPIC);
            notification.put("data", notificationBody);
        } catch (JSONException e) {
            Log.e(TAG, "onCreate: " + e.getMessage());
        }
        sendNotification(notification, true);
    }

    private void sendNotification(JSONObject notification, boolean showBlurDialog) {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(FCM_API, notification,
                response -> {
                    Log.i(TAG, "onResponse: " + response.toString());

                    if (showBlurDialog) {
                        final BlurDialog blurDialog = (BlurDialog) findViewById(R.id.blurView);
                        blurDialog.create(getWindow().getDecorView(), 20);
                        blurDialog.setTitle(RESPONSE_MESSAGE);
                        blurDialog.setIcon(getDrawable(SENDER_ICON));
                        blurDialog.show();
                    }
                },
                error -> {
                    Toast.makeText(this, R.string.request_error_message, Toast.LENGTH_LONG).show();
                    Log.i(TAG, "onErrorResponse: Didn't work");
                }) {
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> params = new HashMap<>();
                params.put("Authorization", serverKey);
                params.put("Content-Type", contentType);
                return params;
            }
        };
        MySingleton.getInstance(getApplicationContext()).addToRequestQueue(jsonObjectRequest);
    }

    @Override
    public void onItemClick(View view, int position) {
        Card card = cardListAdapter.getCardAtPosition(position);

        cardViewModelTab3.insert(new Card(card.getTitle(), card.getImageResource(), card.getId()));

        mCardViewModel.deleteCard(card);
    }
}
