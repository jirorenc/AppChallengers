package com.appchallengers.appchallengers.fragments.camera;


import android.app.Activity;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.appchallengers.appchallengers.R;
import com.appchallengers.appchallengers.helpers.adapters.FriendsListAdapter;
import com.appchallengers.appchallengers.helpers.component.ButtonSFUITextRegularComponent;
import com.appchallengers.appchallengers.helpers.setpages.SetCameraPages;
import com.appchallengers.appchallengers.helpers.util.Utils;
import com.appchallengers.appchallengers.webservice.remote.ApiClientWithCache;
import com.appchallengers.appchallengers.webservice.remote.UserRelationshipClient;
import com.appchallengers.appchallengers.webservice.response.FriendsList;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;

import java.io.File;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;
import static com.appchallengers.appchallengers.helpers.util.Constants.MY_PREFS_NAME;

public class VideoPlayerFragment extends Fragment implements MediaPlayer.OnPreparedListener,
        View.OnTouchListener, View.OnClickListener {

    private View mRootView;
    private VideoView mVideoPlayerVideoView;
    private int mVideoPlayerCurrentPosition;
    private ImageView mVideoPlayerClose;
    private String mPath;
    private Button mChallengeButton;
    private SharedPreferences mSharedPreferences;
    private List<FriendsList> mFriendList;
    private FriendsListAdapter mFriendsListAdapter;
    private ListView mSelectFriendsListview;
    private EditText mSearchEdittext;
    private TextView mGetSelectedItems;
    private TextView mSelectAll;
    private boolean isShowTheSelected;
    private boolean isSelectAll;
    private Observable<Response<List<FriendsList>>> mResponseObservable;
    private CompositeDisposable mCompositeDisposable;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.fragment_video_player, container, false);
        initialView(mRootView);
        return mRootView;
    }

    private void initialView(View mRootView) {
        mCompositeDisposable=new CompositeDisposable();
        isShowTheSelected = false;
        isSelectAll = false;
        mSharedPreferences = getActivity().getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        mVideoPlayerVideoView = (VideoView) mRootView.findViewById(R.id.video_player_fragment_videoview);
        mVideoPlayerClose = (ImageView) mRootView.findViewById(R.id.video_player_fragment_close);
        mChallengeButton = (Button) mRootView.findViewById(R.id.video_player_fragment_challenge_button);
        mChallengeButton.setOnClickListener(this);
        mVideoPlayerVideoView.setOnTouchListener(this);
        mVideoPlayerClose.setOnClickListener(this);
        initialVideoPlayer();

    }

    private void initialVideoPlayer() {
        Bundle bundle = getArguments();
        mPath = bundle.getString("path");
        if (mPath.equals(null) || mPath.equals("")) {
            SetCameraPages.getInstance().constructor(getActivity(), 0);
        } else {
            mVideoPlayerVideoView.setVideoURI(Uri.parse(mPath));
            mVideoPlayerVideoView.setOnPreparedListener(this);
            mVideoPlayerVideoView.requestFocus();
            mVideoPlayerVideoView.start();
        }

    }

    @Override
    public void onPrepared(MediaPlayer mediaPlayer) {
        mediaPlayer.setLooping(true);
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        if (mVideoPlayerVideoView.isPlaying()) {
            mVideoPlayerVideoView.pause();
            mVideoPlayerCurrentPosition = mVideoPlayerVideoView.getCurrentPosition();
            return false;
        } else {
            mVideoPlayerVideoView.seekTo(mVideoPlayerCurrentPosition);
            mVideoPlayerVideoView.start();
            return false;
        }
    }

    @Override
    public void onPause() {
        mCompositeDisposable.dispose();
        super.onPause();
        mVideoPlayerVideoView.pause();
        mVideoPlayerCurrentPosition = mVideoPlayerVideoView.getCurrentPosition();
    }

    @Override
    public void onResume() {
        super.onResume();
        mVideoPlayerVideoView.seekTo(mVideoPlayerCurrentPosition);
        mVideoPlayerVideoView.start();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.video_player_fragment_close: {
                Toast.makeText(getContext(), deleteVideoFile() + "", Toast.LENGTH_LONG).show();
                SetCameraPages.getInstance().constructor(getActivity(), 0);
                break;
            }
            case R.id.video_player_fragment_challenge_button: {
                mVideoPlayerVideoView.pause();
                mVideoPlayerCurrentPosition = mVideoPlayerVideoView.getCurrentPosition();
                friendsDialog();
                /*Bundle bundle=new Bundle();
                bundle.putString("path",mPath);
                SetCameraPages.getInstance().constructorWithBundle(getActivity(),1,bundle);*/
                break;
            }
        }
    }

    private void friendsDialog() {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((Activity) getContext()).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int height = displayMetrics.heightPixels;
        final DialogPlus dialogPlus = DialogPlus.newDialog(getContext())
                .setContentHolder(new ViewHolder(R.layout.fragment_friend_select))
                .setCancelable(true)
                .setInAnimation(R.anim.down_to_up_animation)
                .setContentHeight(height)
                .create();
        mSelectFriendsListview = (ListView) dialogPlus.getHolderView().findViewById(R.id.select_Friends_fragment_listview);
        getFriendList();
        mSelectFriendsListview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (view != null) {
                    CheckBox checkBox = (CheckBox) view.findViewById(R.id.friend_list_checkbox);
                    checkBox.setChecked(!checkBox.isChecked());
                }
                if (isShowTheSelected) {
                    if (!mFriendsListAdapter.getSelectedItems()) {
                        mGetSelectedItems.setText("show the selected");
                        isShowTheSelected = false;
                    }
                    mSelectFriendsListview.invalidate();
                }
            }
        });
        dialogPlus.getHolderView().setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if (i == KeyEvent.KEYCODE_BACK) {
                    dialogPlus.dismiss();
                    mVideoPlayerVideoView.seekTo(mVideoPlayerCurrentPosition);
                    mVideoPlayerVideoView.start();
                    return true;
                }
                return false;
            }
        });
        mSearchEdittext = (EditText) dialogPlus.getHolderView().findViewById(R.id.select_friends_fragment_edittext);
        mSearchEdittext.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if (i == KeyEvent.KEYCODE_BACK) {
                    mVideoPlayerVideoView.seekTo(mVideoPlayerCurrentPosition);
                    mVideoPlayerVideoView.start();
                    dialogPlus.dismiss();
                    return true;
                }
                return false;
            }
        });
        mSearchEdittext.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (isShowTheSelected) {
                    mFriendsListAdapter.filterTheSelected(charSequence.toString());
                    mSelectFriendsListview.invalidate();
                } else {
                    mFriendsListAdapter.filter(charSequence.toString());
                    mSelectFriendsListview.invalidate();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        mGetSelectedItems = (TextView) dialogPlus.getHolderView().findViewById(R.id.select_friends_fragment_show_all_edittext);
        mGetSelectedItems.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isShowTheSelected) {
                    isShowTheSelected = false;
                    mGetSelectedItems.setText("show the selected");
                    mFriendsListAdapter.getAll();
                    mSelectFriendsListview.invalidate();
                } else {
                    isShowTheSelected = true;
                    mGetSelectedItems.setText("Show all");
                    mFriendsListAdapter.getSelectedItems();
                    mSelectFriendsListview.invalidate();
                }

            }
        });
        mSelectAll = (TextView) dialogPlus.getHolderView().findViewById(R.id.select_friends_fragment_select_all_edittext);
        mSelectAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isSelectAll){
                    isSelectAll=false;
                    isShowTheSelected=false;
                    mGetSelectedItems.setText("show the selected");
                    mSelectAll.setText("select all");
                    mFriendsListAdapter.unSelectAll();
                    mSelectFriendsListview.invalidate();
                }else{
                    isSelectAll=true;
                    mSelectAll.setText("unselect all");
                    mFriendsListAdapter.selectAll();
                    mSelectFriendsListview.invalidate();
                }
            }
        });
        RelativeLayout layout = (RelativeLayout) dialogPlus.getHolderView().findViewById(R.id.select_friends_fragment_ll);
        Button button = addButton();
        layout.addView(button);
        dialogPlus.show();
    }

    private Button addButton() {
        Button challengeButton = new ButtonSFUITextRegularComponent(getContext());
        RelativeLayout.LayoutParams layoutParams=new RelativeLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        challengeButton.setLayoutParams(layoutParams);
        challengeButton.setText("CHALLENGE");
        challengeButton.setBackgroundResource(R.color.colorPrimary);
        challengeButton.setTextColor(getActivity().getResources().getColor(R.color.white));
        return challengeButton;
    }

    private void getFriendList() {
        Utils.sharedPreferences = mSharedPreferences;
        String token = Utils.getPref("token");
        UserRelationshipClient userRelationshipClient = ApiClientWithCache.getUserRelationshipClient();
        mResponseObservable=userRelationshipClient.getFriendList(token);
        mResponseObservable.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Response<List<FriendsList>>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        mCompositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(Response<List<FriendsList>> value) {
                        mFriendList = value.body();
                        mFriendsListAdapter = new FriendsListAdapter(getContext(), mFriendList);
                        mSelectFriendsListview.setAdapter(mFriendsListAdapter);
                    }

                    @Override
                    public void onError(Throwable e) {
                        //  TODO video player fragment firends list on eror
                    }

                    @Override
                    public void onComplete() {
                        //  TODO video player fragment firends list on complete
                    }
                });
    }

    public boolean deleteVideoFile() {
        File file = new File(mPath);
        return file.delete();
    }

}
