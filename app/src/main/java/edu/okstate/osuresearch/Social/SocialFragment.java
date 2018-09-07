package edu.okstate.osuresearch.Social;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.DefaultLogger;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.Twitter;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterConfig;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.models.Tweet;
import com.twitter.sdk.android.tweetui.TimelineResult;
import com.twitter.sdk.android.tweetui.TweetTimelineListAdapter;
import com.twitter.sdk.android.tweetui.UserTimeline;

import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;


import java.util.Arrays;

import co.ceryle.radiorealbutton.RadioRealButton;
import co.ceryle.radiorealbutton.RadioRealButtonGroup;
import edu.okstate.osuresearch.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class SocialFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    TweetTimelineListAdapter adapter;
    SwipeRefreshLayout swipeLayout;
    RadioRealButtonGroup selectSocialTypeButtons;
    CallbackManager mCallbackManager;
    AccessToken mAccessToken;
    public static String social_email = "", social_id = "", social_feed = "", social_name = "", social = "";

    public SocialFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TwitterConfig twitterConfig = new TwitterConfig.Builder(getContext())
                .logger(new DefaultLogger(Log.DEBUG))
                .twitterAuthConfig(new TwitterAuthConfig(getString(R.string.com_twitter_sdk_android_CONSUMER_KEY), getString(R.string.com_twitter_sdk_android_CONSUMER_SECRET)))
                .debug(true)
                .build();

        Twitter.initialize(twitterConfig);

        loadTwitterFeeds();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment†
        View view = inflater.inflate(R.layout.fragment_social, container, false);
        ListView listView = (ListView) view.findViewById(R.id.androidlist);
        swipeLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_layout);
        selectSocialTypeButtons  = view.findViewById(R.id.radioButtonGroup_selectSocial);
        selectSocialTypeButtons.setOnClickedButtonListener(new RadioRealButtonGroup.OnClickedButtonListener() {
            @Override
            public void onClickedButton(RadioRealButton button, int position) {
                switch (position) {
                    case 0:
                        //Twitter
                        Log.e("Likhitha","Load Twitter feeds..");
                        loadTwitterFeeds();
                        break;
                        // For app crash. modified here
                    //case 1:
                        //Facebook
                    //    break;

                }
            }
        });
        selectSocialTypeButtons.setPosition(0);

        listView.setAdapter(adapter);
        swipeLayout.setOnRefreshListener(this);
        return view;
    }


    private void loadTwitterFeeds() {
        final UserTimeline userTimeline = new UserTimeline.Builder().screenName("OSU_Research").build();
        adapter = new TweetTimelineListAdapter.Builder(getContext()).setTimeline(userTimeline).build();
    }

    private void loadFacebookFeeds() {

    }

    @Override
    public void onRefresh() {
        swipeLayout.setRefreshing(true);
        adapter.refresh(new Callback<TimelineResult<Tweet>>() {
            @Override
            public void success(Result<TimelineResult<Tweet>> result) {
                swipeLayout.setRefreshing(false);
            }

            @Override
            public void failure(TwitterException exception) {
                // Toast or some other action
            }
        });
    }
}

