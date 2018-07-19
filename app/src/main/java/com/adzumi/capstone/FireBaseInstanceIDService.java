package com.adzumi.capstone;

import android.util.Log;

import com.adzumi.capstone.ui.MainActivity;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

public class FireBaseInstanceIDService extends FirebaseInstanceIdService {
    public static final String TAG = FireBaseInstanceIDService.class.getSimpleName();
    //    private static final String TAG = "MyFirebaseIIDService";
    @Override
    public void onTokenRefresh() {
    //Getting registration token
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
    //Displaying token on logcat
        Log.v(TAG, "Refreshed token: " + refreshedToken);
    }
}
