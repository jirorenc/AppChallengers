package com.appchallengers.appchallengers.fragments.login;


import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.appchallengers.appchallengers.R;
import com.appchallengers.appchallengers.helpers.setpages.SetLoginPages;
import com.appchallengers.appchallengers.helpers.util.CameraUtils;
import com.appchallengers.appchallengers.helpers.util.CheckPermissions;
import com.appchallengers.appchallengers.helpers.util.Constants;
import com.appchallengers.appchallengers.helpers.util.CustomToast;
import com.appchallengers.appchallengers.helpers.util.Instructions;
import com.appchallengers.appchallengers.helpers.util.Utils;
import com.appchallengers.appchallengers.webservice.remote.ApiClient;
import com.appchallengers.appchallengers.webservice.remote.UserClient;
import com.appchallengers.appchallengers.webservice.request.SignUpRequestModel;
import com.appchallengers.appchallengers.webservice.response.UserPreferencesData;
import com.dd.processbutton.iml.ActionProcessButton;
import com.labo.kaji.fragmentanimations.MoveAnimation;

import java.io.File;
import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;
import static com.appchallengers.appchallengers.helpers.util.Constants.MY_PREFS_NAME;


public class SignUpFragment extends Fragment implements View.OnClickListener {
    private View mRootView;
    private ImageView mSignUpProfileImage;
    private ImageView mSignUpBackArrow;
    private EditText mSignUpFullName;
    private EditText mSignUpEmail;
    private EditText mSignUpPassword;
    private EditText mSignUpCountry;
    private ActionProcessButton mSignUpButton;
    private LinearLayout mLinearLayout;
    private TextView mSignUpLinkLogin;
    private TextView mCustomAlertDialogCamera;
    private TextView mCustomAlertDialogGalery;
    private TextView mTermsAndCookie;
    private final int GALLERY = 159;
    private final int CAMERA = 158;
    private String mImageUrl = null;
    private Animation mShakeAnimation;
    private SharedPreferences mSharedPreferences;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {

        mRootView = inflater.inflate(R.layout.fragment_sign_up, container, false);
        initalView(mRootView);
        return mRootView;
    }

    private void initalView(View view) {
        mSignUpProfileImage = (ImageView) view.findViewById(R.id.sign_up_fragment_profile_image);
        mSignUpBackArrow = (ImageView) view.findViewById(R.id.sign_up_fragment_back_arrow_imageview);
        mSignUpFullName = (EditText) view.findViewById(R.id.sign_up_fragment_user_fullname_edittext);
        mSignUpEmail = (EditText) view.findViewById(R.id.sign_up_fragment_email_edittext);
        mSignUpPassword = (EditText) view.findViewById(R.id.sign_up_fragment_password_edittext);
        mSignUpCountry = (EditText) view.findViewById(R.id.sign_up_fragment_country_edittext);
        mSignUpButton = (ActionProcessButton) view.findViewById(R.id.sign_up_fragment_sign_up_button);
        mSignUpLinkLogin = (TextView) view.findViewById(R.id.sign_up_fragment_login_link_textview);
        mTermsAndCookie = (TextView) view.findViewById(R.id.sign_up_fragment_profile_terms_cookie);
        mLinearLayout = (LinearLayout) view.findViewById(R.id.sign_up_fragment_profile_image_ll);
        mShakeAnimation = AnimationUtils.loadAnimation(getActivity(), R.anim.shake);
        mSharedPreferences = getActivity().getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        mSignUpButton.setMode(ActionProcessButton.Mode.ENDLESS);
        mSignUpBackArrow.setOnClickListener(this);
        mSignUpLinkLogin.setOnClickListener(this);
        mSignUpProfileImage.setOnClickListener(this);
        mSignUpButton.setOnClickListener(this);
        mTermsAndCookie.setOnClickListener(this);
        mSignUpCountry.setOnClickListener(this);

    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.sign_up_fragment_back_arrow_imageview: {
                SetLoginPages.getInstance().constructor(getActivity(), 0);
                break;
            }
            case R.id.sign_up_fragment_login_link_textview: {
                SetLoginPages.getInstance().constructor(getActivity(), 0);
                break;
            }
            case R.id.sign_up_fragment_profile_image: {
                if (checkPermission())
                    showPictureDialog();
                break;
            }
            case R.id.sign_up_fragment_profile_terms_cookie:{
                Instructions.getInstance().constructor(getActivity(), Constants.TERMS_AND_COOKİE);
                break;
            }
            case R.id.sign_up_fragment_sign_up_button: {
                if (checkValidation()) {
                    if (mImageUrl==null){
                        ButtonActionActive();
                        createAccountWithoutImage();
                    }else{
                        ButtonActionActive();
                        ceateAccountWithImage();
                    }
                }
                break;
            }
        }
    }

    private void ButtonActionActive(){
        mSignUpButton.setProgress(1);
    }
    private void ButtonActionPasif(){
        mSignUpButton.setProgress(0);
    }
    private boolean checkPermission() {
        return CheckPermissions.getInstance().hasPermissions(getActivity(), new String[]{Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE});
    }

    private void showPictureDialog() {
        final Dialog dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.custom_alert_dialog_select_image_provider);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        mCustomAlertDialogCamera = (TextView) dialog.findViewById(R.id.custom_alert_dialog_image_provider_camera);
        mCustomAlertDialogGalery = (TextView) dialog.findViewById(R.id.custom_alert_dialog_image_provider_galery);
        mCustomAlertDialogGalery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                photoPickerIntent.setType("image/*");
                startActivityForResult(photoPickerIntent, GALLERY);
                dialog.dismiss();
            }
        });
        mCustomAlertDialogCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent kamera = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE); // Resim çekme isteği ve activity başlatılıp id'si tanımlandı
                startActivityForResult(kamera, CAMERA);
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == getActivity().RESULT_CANCELED) {
            return;
        }
        if (requestCode == GALLERY) {
            if (data != null) {
                Uri contentURI = data.getData();
                String[] filePathColumn = {MediaStore.Images.Media.DATA};
                Cursor cursor = getActivity().getContentResolver().query(contentURI, filePathColumn, null, null, null);
                if (cursor != null) {
                    cursor.moveToFirst();
                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    mImageUrl = cursor.getString(columnIndex);
                    Log.e("galery",mImageUrl);
                    cursor.close();
                }
                mSignUpProfileImage.setImageURI(contentURI);
            }

        } else if (requestCode == CAMERA) {
            Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
            try {
                mImageUrl = CameraUtils.getOutputMediaFileUri(getActivity().getBaseContext(), thumbnail);
                Log.e("kamera",mImageUrl);
            } catch (IOException e) {
                e.printStackTrace();
            }
            mSignUpProfileImage.setImageBitmap(thumbnail);
        }
    }
    private boolean checkValidation() {
        String email = mSignUpEmail.getText().toString();
        String fullName = mSignUpFullName.getText().toString();
        String password = mSignUpPassword.getText().toString();
        String country = mSignUpCountry.getText().toString();
        return Utils.checkValidation(new String[]{email,fullName,password,country},mLinearLayout,mShakeAnimation,getActivity(),mRootView);
    }

    private void ceateAccountWithImage() {
        UserClient userClient= ApiClient.getUserClient();
        File file = new File(mImageUrl);
        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        RequestBody fullName = RequestBody.create(MultipartBody.FORM,mSignUpFullName.getText().toString());
        RequestBody email = RequestBody.create(MultipartBody.FORM,mSignUpEmail.getText().toString());
        RequestBody password = RequestBody.create(MultipartBody.FORM,mSignUpPassword.getText().toString());
        RequestBody country = RequestBody.create(MultipartBody.FORM,mSignUpCountry.getText().toString());
        MultipartBody.Part body = MultipartBody.Part.createFormData("image",file.getName(), requestFile);
        Call<UserPreferencesData> createAccountResponseModelCall=userClient.createAccountWithImage(fullName,email,password,country,body);
        createAccountResponseModelCall.enqueue(new Callback<UserPreferencesData>() {
            @Override
            public void onResponse(Call<UserPreferencesData> call, Response<UserPreferencesData> response) {
                if (response.body().getStatusCode()==200){
                    Utils.sharedPreferences = mSharedPreferences;
                    Utils.setSharedPreferences("token",response.body().getToken());
                    Utils.setSharedPreferences("id",response.body().getId()+"");
                    Utils.setSharedPreferences("email",response.body().getEmail());
                    Utils.setSharedPreferences("active",response.body().getActive()+"");
                    ButtonActionPasif();
                    SetLoginPages.getInstance().constructor(getActivity(),2);
                }else if(response.body().getStatusCode()==250){
                    new CustomToast().Show_Toast(getContext(),mRootView,getString(R.string.error_250));
                    ButtonActionPasif();
                }else{
                    new CustomToast().Show_Toast(getContext(),mRootView,getString(R.string.error_290));
                    ButtonActionPasif();
                }
            }
            @Override
            public void onFailure(Call<UserPreferencesData> call, Throwable t) {
                new CustomToast().Show_Toast(getContext(),mRootView,getString(R.string.error_290));
            }
        });
    }
    private void createAccountWithoutImage() {
        UserClient userClient=ApiClient.getUserClient();
        SignUpRequestModel signUpRequestModel=new SignUpRequestModel(
             mSignUpFullName.getText().toString(),
             mSignUpEmail.getText().toString(),
             mSignUpPassword.getText().toString(),
             mSignUpCountry.getText().toString()
        );
        Call<UserPreferencesData> createAccountResponseModelCall=userClient.createAccountWithoutImage(signUpRequestModel);
        createAccountResponseModelCall.enqueue(new Callback<UserPreferencesData>() {
            @Override
            public void onResponse(Call<UserPreferencesData> call, Response<UserPreferencesData> response) {
                if (response.body().getStatusCode()==200){
                    Utils.sharedPreferences = mSharedPreferences;
                    Utils.setSharedPreferences("token",response.body().getToken());
                    Utils.setSharedPreferences("id",response.body().getId()+"");
                    Utils.setSharedPreferences("email",response.body().getEmail());
                    Utils.setSharedPreferences("active",response.body().getActive()+"");
                    ButtonActionPasif();
                    SetLoginPages.getInstance().constructor(getActivity(),2);
                }else if(response.body().getStatusCode()==250){
                    new CustomToast().Show_Toast(getContext(),mRootView,getString(R.string.error_250));
                    ButtonActionPasif();
                }else{
                    new CustomToast().Show_Toast(getContext(),mRootView,getString(R.string.error_290));
                    ButtonActionPasif();
                }
            }

            @Override
            public void onFailure(Call<UserPreferencesData> call, Throwable t) {
                new CustomToast().Show_Toast(getContext(),mRootView,getString(R.string.error_290));
            }
        });
    }
    @Override
    public Animation onCreateAnimation(int transit, boolean enter, int nextAnim) {
        if (enter) {
            return MoveAnimation.create(MoveAnimation.LEFT, enter, 500);
        } else {
            return MoveAnimation.create(MoveAnimation.RIGHT, enter, 500);
        }
    }

}
