package io.github.htigroup4.pecs2life;

import android.app.Activity;
import android.os.Handler;
import android.os.Message;

public class MyBaseActivity extends Activity {
    public static final long DISCONNECT_TIMEOUT = 10000; // 10 sec = 10 * 1000 ms


    private Handler disconnectHandler = new Handler(msg -> {
        // todo
        return true;
    });

    // Perform any required operation on disconnect
    private Runnable disconnectCallback = this::finish;

    public void resetDisconnectTimer() {
        disconnectHandler.removeCallbacks(disconnectCallback);
        disconnectHandler.postDelayed(disconnectCallback, DISCONNECT_TIMEOUT);
    }

    public void stopDisconnectTimer() {
        disconnectHandler.removeCallbacks(disconnectCallback);
    }

    @Override
    public void onUserInteraction() {
        resetDisconnectTimer();
    }

    @Override
    public void onResume() {
        super.onResume();
        resetDisconnectTimer();
    }

    @Override
    public void onStop() {
        super.onStop();
        stopDisconnectTimer();
    }
}
