package com.appchallengers.appchallengers.helpers.adapters;

import com.appchallengers.appchallengers.R;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import android.widget.TextView;

import com.google.android.exoplayer2.ui.SimpleExoPlayerView;





import im.ene.toro.ToroPlayer;
import im.ene.toro.ToroUtil;
import im.ene.toro.exoplayer.SimpleExoPlayerViewHelper;
import im.ene.toro.media.PlaybackInfo;
import im.ene.toro.widget.Container;

/**
 * Created by jir on 10.3.2018.
 */

public class UserFeedAdapterHelper extends RecyclerView.ViewHolder implements ToroPlayer {
    public TextView like;
    public TextView likebutton;
    public TextView fullname;
    public ImageView imageview;
    private SimpleExoPlayerView playerView;
    private SimpleExoPlayerViewHelper helper;
    private Uri mediaUri;

    public UserFeedAdapterHelper(View itemView) {
        super(itemView);
        playerView = (SimpleExoPlayerView) itemView.findViewById(R.id.flow_video_cardview_videoview);
        imageview = (ImageView) itemView.findViewById(R.id.flow_video_cardview_profil_picture);
        fullname = (TextView) itemView.findViewById(R.id.flow_video_cardview_fullname_and_headline);
        like = (TextView) itemView.findViewById(R.id.flow_video_cardview_like);
        likebutton = (TextView) itemView.findViewById(R.id.flow_video_cardview_like_button);

    }

    @Override public View getPlayerView() {
        return playerView;
    }

    @Override public PlaybackInfo getCurrentPlaybackInfo() {
        return helper != null ? helper.getLatestPlaybackInfo() : new PlaybackInfo();
    }

    @Override
    public void initialize(Container container, PlaybackInfo playbackInfo) {
        if (helper == null) {
            helper = new SimpleExoPlayerViewHelper(container, this, mediaUri);
        }
        helper.initialize(playbackInfo);
    }


    @Override public void play() {
        if (helper != null) helper.play();
    }

    @Override public void pause() {
        if (helper != null) helper.pause();
    }

    @Override public boolean isPlaying() {
        return helper != null && helper.isPlaying();
    }

    @Override public void release() {
        if (helper != null) {
            helper.release();
            helper = null;
        }
    }

    @Override public boolean wantsToPlay() {
        return ToroUtil.visibleAreaOffset(this, itemView.getParent()) >= 0.85;
    }

    @Override public int getPlayerOrder() {
        return getAdapterPosition();
    }



    void bind(Uri media) {
        this.mediaUri = media;
    }

}