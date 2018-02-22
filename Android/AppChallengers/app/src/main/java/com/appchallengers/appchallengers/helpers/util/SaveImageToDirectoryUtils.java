package com.appchallengers.appchallengers.helpers.util;

import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;

import com.appchallengers.appchallengers.BuildConfig;
import com.squareup.picasso.OkHttpDownloader;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


public class SaveImageToDirectoryUtils {

    //Get Uri Of captured Image
    public static String getOutputMediaFileUri(Context context,Bitmap thumbnail) throws IOException {
        File mediaStorageDir = new File(
                context.getExternalFilesDir(Environment.DIRECTORY_PICTURES), "Camera");
        if (!mediaStorageDir.exists()) {
            if (mediaStorageDir.mkdir())
                Log.e("Create Directory", "Main Directory Created : " + mediaStorageDir);
        }
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss",
                Locale.getDefault()).format(new Date());//Get Current timestamp
        File mediaFile = new File(mediaStorageDir.getPath() + File.separator
                + "IMG_" + timeStamp + ".jpg");
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        thumbnail.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        mediaFile.createNewFile();
        FileOutputStream fo = new FileOutputStream(mediaFile);
        //5
        fo.write(bytes.toByteArray());
        fo.close();
        return mediaFile.getPath();

    }
}
