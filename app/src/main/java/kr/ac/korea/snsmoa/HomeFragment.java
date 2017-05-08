package kr.ac.korea.snsmoa;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import kr.ac.korea.intelligentgallery.R;
import kr.ac.korea.snsmoa.facebook.FacebookClient;
import kr.ac.korea.snsmoa.twitter.TwitterClient;

/**
 * Created by Noverish on 2017-03-21.
 */

public class HomeFragment extends Fragment implements View.OnClickListener {
    public CustomRecyclerView recyclerView;
    public TextView twitterNotLogin, facebookNotLogin;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_home, container, false);

        recyclerView = (CustomRecyclerView) view.findViewById(R.id.activity_home_recycler_view);
        twitterNotLogin = (TextView) view.findViewById(R.id.activity_home_twitter_not_login);
        facebookNotLogin = (TextView) view.findViewById(R.id.activity_home_facebook_not_login);

        twitterNotLogin.setOnClickListener(this);
        facebookNotLogin.setOnClickListener(this);

        TwitterClient.getInstance().setOnUserLogined(new TwitterClient.OnUserLogined() {
            @Override
            public void onUserLogined() {
                twitterNotLogin.setVisibility(View.GONE);
            }
        });
        FacebookClient.getInstance().setOnUserLogined(new FacebookClient.OnUserLogined() {
            @Override
            public void onUserLogined() {
                facebookNotLogin.setVisibility(View.GONE);
            }
        });

        return view;
    }

    @Override
    public void onClick(View v) {
        if (v.equals(twitterNotLogin)) {
            Intent braodcast = new Intent(MainActivity.ChangeScreenReceiver.ACTION);
            braodcast.putExtra(MainActivity.ChangeScreenReceiver.INTENT_KEY, MainActivity.Screen.twitter);
            getContext().sendBroadcast(braodcast);
        } else if (v.equals(facebookNotLogin)) {
            Intent braodcast = new Intent(MainActivity.ChangeScreenReceiver.ACTION);
            braodcast.putExtra(MainActivity.ChangeScreenReceiver.INTENT_KEY, MainActivity.Screen.facebook);
            getContext().sendBroadcast(braodcast);
        }
    }
}
