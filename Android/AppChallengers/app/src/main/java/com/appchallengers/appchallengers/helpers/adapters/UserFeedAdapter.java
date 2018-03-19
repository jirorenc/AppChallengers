package com.appchallengers.appchallengers.helpers.adapters;

import com.appchallengers.appchallengers.R;
import com.appchallengers.appchallengers.helpers.onitemclick.RecyclerItemClickListener;
import com.appchallengers.appchallengers.webservice.response.UserChallengeFeedListModel;

import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ImageSpan;
import android.view.ViewGroup;
import android.view.View;
import android.view.LayoutInflater;
import android.net.Uri;
import android.content.Context;
import com.squareup.picasso.Picasso;
import android.graphics.Color;


import java.util.List;

public class UserFeedAdapter extends RecyclerView.Adapter<UserFeedAdapterHelper>  {
    private Context mContext;
    private List<UserChallengeFeedListModel> mCardList;



    @Override
    public UserFeedAdapterHelper onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.flow_video_cardview, parent, false);

        return new UserFeedAdapterHelper(view);
    }

    public UserFeedAdapter(Context mContext, List<UserChallengeFeedListModel> mCardList) {
        this.mContext = mContext;
        this.mCardList = mCardList;

    }
    @Override public void onBindViewHolder(final UserFeedAdapterHelper holder, int position) {
       final UserChallengeFeedListModel cardlist = mCardList.get(position);
        holder.bind(Uri.parse(cardlist.getChallenge_url()));
        holder.fullname.setText(spannableStringModel(cardlist.getFullname()+" "," "+cardlist.getHeadline()));
        holder.like.setText(cardlist.getLikes()+" like");
        if(cardlist.getVote()==1){
            holder.likebutton.setTextColor(Color.parseColor("#FD5739"));
            holder.likebutton.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.ic_like_orange,0,0,0);
        }

       holder.likebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(cardlist.getVote()==1){
                    cardlist.setVote(0);
                    holder.likebutton.setTextColor(Color.parseColor("#24243D"));
                    holder.likebutton.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.ic_like_grey,0,0,0);
                }else if (cardlist.getVote()==0){
                    cardlist.setVote(1);
                    holder.likebutton.setTextColor(Color.parseColor("#FD5739"));
                    holder.likebutton.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.ic_like_orange,0,0,0);
                }
            }
        });

        Picasso.with(mContext).load(cardlist.getProfilepicture()).into(holder.imageview);


    }

    @Override
    public int getItemCount() {
        return mCardList.size();
    }

    private SpannableString spannableStringModel(String name,String headline){
        Drawable drawable = mContext.getResources().getDrawable(R.drawable.ic_keyboard_arrow_right_grey_24dp);
        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
        ImageSpan span = new ImageSpan(drawable, ImageSpan.ALIGN_BOTTOM);
        SpannableString spannableString = new SpannableString(name+headline);
        spannableString.setSpan(name, 0,name.length(),Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(span, name.length(),name.length()+1,Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(headline, name.length()+1,headline.length()+name.length(),Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return spannableString;
    }
}
