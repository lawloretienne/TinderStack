package com.etiennelawlor.tinderstack.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.etiennelawlor.tinderstack.R;
import com.etiennelawlor.tinderstack.models.User;
import com.etiennelawlor.tinderstack.ui.TinderCardView;
import com.etiennelawlor.tinderstack.ui.TinderStackLayout;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;

public class MainActivity extends AppCompatActivity {

    // region Constants
    private static final int STACK_SIZE = 4;
    // endregion

    // region Views
    private TinderStackLayout tinderStackLayout;
    // endregion

    // region Member Variables
    private String[] displayNames, userNames, avatarUrls;
    private int index = 0;
    // endregion

    // region Listeners
    // endregion
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        displayNames = getResources().getStringArray(R.array.display_names);
        userNames = getResources().getStringArray(R.array.usernames);
        avatarUrls = getResources().getStringArray(R.array.avatar_urls);

        tinderStackLayout = (TinderStackLayout) findViewById(R.id.tsl);

        TinderCardView tc;
        for(int i=index; index<i+STACK_SIZE; index++){
            tc = new TinderCardView(this);
            tc.bind(getUser(index));
            tinderStackLayout.addCard(tc);
        }

        tinderStackLayout.getPublishSubject()
                .observeOn(AndroidSchedulers.mainThread()) // UI Thread
                .subscribe(new Subscriber<Integer>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Integer integer) {
                        if(integer == 1){
                            TinderCardView tc;
                            for(int i=index; index<i+(STACK_SIZE-1); index++){
                                tc = new TinderCardView(MainActivity.this);
                                tc.bind(getUser(index));
                                tinderStackLayout.addCard(tc);
                            }
                        }
                    }
                });

    }

    // region Helper Methods
    private User getUser(int index){
        User user = new User();
        user.setAvatarUrl(avatarUrls[index]);
        user.setDisplayName(displayNames[index]);
        user.setUsername(userNames[index]);
        return user;
    }
    // endregion
}
