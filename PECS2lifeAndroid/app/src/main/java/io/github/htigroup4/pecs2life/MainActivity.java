
package io.github.htigroup4.pecs2life;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.android.volley.toolbox.JsonObjectRequest;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.messaging.FirebaseMessaging;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


/**
 * This app offers three view fragments and three tabs to
 * navigate to them.
 */
public class MainActivity extends AppCompatActivity {

    final private String FCM_API = "https://fcm.googleapis.com/fcm/send";
    final private String serverKey = "key=" + BuildConfig.SERVER_KEY;
    final private String contentType = "application/json";
    final String TAG = "NOTIFICATION TAG";

    String NOTIFICATION_TITLE;
    String NOTIFICATION_MESSAGE;
    String TOPIC;
    int LARGE_ICON;

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

        // TODO remove this section when settings for subscription handling has added to the app
        if (getResources().getBoolean(R.bool.isTablet)) { // the device is a tablet = a pupil uses it
            FirebaseMessaging.getInstance().subscribeToTopic("/topics/fromTeacherToPupil");
        } else { // the device is a mobile phone = a teacher uses it
            FirebaseMessaging.getInstance().subscribeToTopic("/topics/fromPupilToTeacher");
        }

        // Create an instance of the tab layout from the view.
        TabLayout tabLayout = findViewById(R.id.tab_layout);
        // Set the text for each tab.
        tabLayout.addTab(tabLayout.newTab().setText(R.string.tab_text_1));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.tab_text_2));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.tab_text_3));

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
    }

    public void sendHelpRequest(View view) {
        if (getResources().getBoolean(R.bool.isTablet)) { // the device is a tablet = a pupil uses it
            TOPIC = "/topics/fromPupilToTeacher"; //topic has to match what the receiver subscribed to
            NOTIFICATION_TITLE = getString(R.string.pupil_notification_title);
            NOTIFICATION_MESSAGE = getString(R.string.pupil_notification_message);
            LARGE_ICON = R.drawable.pupil;
        } else { // the device is a mobile phone = a teacher uses it
            TOPIC = "/topics/fromTeacherToPupil"; //topic has to match what the receiver subscribed to
            NOTIFICATION_TITLE = getString(R.string.teacher_notification_title);
            NOTIFICATION_MESSAGE = getString(R.string.teacher_notification_message);
            LARGE_ICON = R.drawable.teacher;
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
        sendNotification(notification);

//        Toast toast = Toast.makeText(this, "DEBUG: help clicked",
//                Toast.LENGTH_SHORT);
//        toast.show();
    }

    private void sendNotification(JSONObject notification) {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(FCM_API, notification,
                response -> {
                    Log.i(TAG, "onResponse: " + response.toString());
                    //edtTitle.setText("");
                    //edtMessage.setText("");
                },
                error -> {
                    Toast.makeText(MainActivity.this, "Request error", Toast.LENGTH_LONG).show();
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
}
