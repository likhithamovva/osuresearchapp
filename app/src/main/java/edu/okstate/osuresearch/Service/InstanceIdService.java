package edu.okstate.osuresearch.Service;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

public class InstanceIdService extends FirebaseInstanceIdService {

    private static String TAG = InstanceIdService.class.getSimpleName();
    public InstanceIdService() {
        super();
    }

    @Override
    public void onTokenRefresh() {
        super.onTokenRefresh();
        String token = FirebaseInstanceId.getInstance().getToken();
        Log.d(TAG,"Token is   ->  "+token);
        //Send this token to Server, if we want to save.
    }
}
