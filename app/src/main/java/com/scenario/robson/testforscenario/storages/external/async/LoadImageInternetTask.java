package com.scenario.robson.testforscenario.storages.external.async;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import com.scenario.robson.testforscenario.R;
import com.scenario.robson.testforscenario.storages.external.listener.LoadImageInternetListener;

import java.io.InputStream;
import java.net.URL;

/**
 * Created by robson on 23/01/16.
 */
public class LoadImageInternetTask extends AsyncTask<String, String, Bitmap> {

    private Context mContext;
    private Bitmap mBitmap;
    private ProgressDialog mProgressDialog;
    private boolean mSowProgress;

    private LoadImageInternetListener mLoadImageInternetListener;

    public LoadImageInternetTask(Context context, LoadImageInternetListener loadImageInternetListener, boolean sowProgress) {
        mContext = context;
        mLoadImageInternetListener = loadImageInternetListener;
        mSowProgress = sowProgress;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        if (mSowProgress) {
            mProgressDialog = new ProgressDialog(mContext);
            mProgressDialog.setMessage(mContext.getString(R.string.loading));
            mProgressDialog.show();
            mProgressDialog.setCancelable(false);
        }
    }

    @Override
    protected Bitmap doInBackground(String... params) {
        try {
            BitmapFactory.Options opts = new BitmapFactory.Options();
            opts.inSampleSize = 4;
            mBitmap = BitmapFactory.decodeStream((InputStream) new URL(params[0]).getContent(), null, opts);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mBitmap;
    }

    protected void onPostExecute(Bitmap image) {
        if (mLoadImageInternetListener != null) {
            mLoadImageInternetListener.onPostExecute(image);
        }
        if (mSowProgress) {
            mProgressDialog.dismiss();
        }
    }

}
