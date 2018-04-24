package com.appchallengers.appchallengers.fragments.show_setting_user_activity_fragment;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.appchallengers.appchallengers.R;
import com.appchallengers.appchallengers.helpers.adapters.ShowLikesAdapter;
import com.appchallengers.appchallengers.helpers.util.ErrorHandler;
import com.appchallengers.appchallengers.helpers.util.Utils;
import com.appchallengers.appchallengers.webservice.remote.GetProfileApiClient;
import com.appchallengers.appchallengers.webservice.remote.GetProfileInfo;
import com.appchallengers.appchallengers.webservice.remote.UserAccount;
import com.appchallengers.appchallengers.webservice.response.UserBaseDataModel;
import com.fasterxml.jackson.core.sym.Name;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import android.widget.EditText;
import android.widget.TextView;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;
import static com.appchallengers.appchallengers.helpers.util.Constants.MY_PREFS_NAME;


public class SettingProfileFragment extends Fragment implements View.OnClickListener {
    private CompositeDisposable mCompositeDisposable;
    private View mRootView;
    private CircleImageView mProfileImage;
    private String mProfileImageUrl;
    private EditText mFullName;
    private EditText mCountry;
    private EditText mEmail;
    private TextView mProfilImageChangeLink;
    private SharedPreferences mSharedPreferences;
    Observable<Response<List<UserBaseDataModel>>> getProfileSetting;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.fragment_setting_profile, container, false);
        initialView(mRootView);
        return mRootView;
    }

    private void initialView(View mRootView) {
        mCompositeDisposable = new CompositeDisposable();
        mProfileImage=(CircleImageView) mRootView.findViewById(R.id.fragment_setting_profile_image);
        mProfilImageChangeLink=(TextView) mRootView.findViewById(R.id.fragment_setting_profile_change_link);
        mFullName=(EditText)mRootView.findViewById(R.id.fragment_setting_profile_name_edittext);
        mCountry=(EditText)mRootView.findViewById(R.id.fragment_setting_profile_country_edittext);
        mEmail=(EditText)mRootView.findViewById(R.id.fragment_setting_profile_email_edittext);
        mSharedPreferences = getActivity().getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        getProfilSettingInfo();
    }
    private void getProfilSettingInfo(){
        Utils.sharedPreferences = mSharedPreferences;
        mFullName.setText(Utils.getPref("fullName"));
        mCountry.setText(Utils.getPref("country"));
        mEmail.setText(Utils.getPref("email"));
        mProfileImageUrl=Utils.getPref("imageUrl");
        if (mProfileImageUrl!=null){
            Picasso.with(getContext()).load(mProfileImageUrl).into(mProfileImage);
        }
        mCountry.setOnClickListener(this);
        mEmail.setOnClickListener(this);
        mProfileImage.setOnClickListener(this);
        mProfilImageChangeLink.setOnClickListener(this);
    }
    @Override
    public void onDestroy() {
        if (mCompositeDisposable != null && !mCompositeDisposable.isDisposed()) {
            mCompositeDisposable.dispose();
        }
        super.onDestroy();
    }


    @Override
    public void onClick(View view) {

    }
}
