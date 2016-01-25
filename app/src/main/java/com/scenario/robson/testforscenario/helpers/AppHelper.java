package com.scenario.robson.testforscenario.helpers;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.CursorLoader;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.Spinner;

import com.scenario.robson.testforscenario.R;
import com.scenario.robson.testforscenario.models.entities.Project;
import com.scenario.robson.testforscenario.utils.AppUtil;
import com.scenario.robson.testforscenario.views.activity.NavigationDrawerActivity;
import com.scenario.robson.testforscenario.views.activity.ProjectActivity;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by robson on 23/01/16.
 */
public final class AppHelper {

    public static Bitmap requestCamera(Intent data) {
        Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        thumbnail.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
        File destination = new File(Environment.getExternalStorageDirectory(), System.currentTimeMillis() + ".jpg");
        FileOutputStream fo;
        try {
            destination.createNewFile();
            fo = new FileOutputStream(destination);
            fo.write(bytes.toByteArray());
            fo.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return thumbnail;
    }

    public static Bitmap selectFile(Intent data, Context context) {
        Uri selectedImageUri = data.getData();
        String[] projection = {MediaStore.Images.Media.DATA};

        CursorLoader cursorLoader = new CursorLoader(context, selectedImageUri, projection, null, null, null);
        Cursor cursor = cursorLoader.loadInBackground();
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
        cursor.moveToFirst();

        String selectedImagePath = cursor.getString(column_index);

        Bitmap bitmap;
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(selectedImagePath, options);
        final int REQUIRED_SIZE = 200;
        int scale = 1;
        while (options.outWidth / scale / 2 >= REQUIRED_SIZE
                && options.outHeight / scale / 2 >= REQUIRED_SIZE)
            scale *= 2;
        options.inSampleSize = scale;
        options.inJustDecodeBounds = false;
        bitmap = BitmapFactory.decodeFile(selectedImagePath, options);

        return bitmap;
    }

    public static byte[] getBitmapAsByteArray(Bitmap bitmap) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, outputStream);
        return outputStream.toByteArray();
    }

    public static Bitmap getByteArrayAsBitmap(byte[] bytesImage) {
        return BitmapFactory.decodeByteArray(bytesImage, 0, bytesImage.length);
    }

    public static boolean verifyMandatoryEditText(EditText... fields) {
        boolean isValid = true;
        for (EditText field : fields) {
            field.setError(null);
            if (TextUtils.isEmpty(field.getText())) {
                field.setError(AppUtil.CONTEXT.getString(R.string.msg_mandatory));
                if (isValid) {
                    isValid = false;
                }
            }
        }
        return isValid;
    }

    public static boolean verifyMandatorySpiner(Spinner... spinners) {
        boolean isValid = true;
        /*for (Spinner spinner : spinners) {
            spinner.setError(null);
            if (TextUtils.isEmpty(spinner.getText())) {
                spinner.setError(AppUtil.CONTEXT.getString(R.string.msg_mandatory));
                if (isValid) {
                    isValid = false;
                }
            }
        }*/
        return isValid;
    }

    public static int spinerSelection(Spinner spinners, String string) {
        int position = 0;
        for (int i = 0; i < spinners.getCount(); i++){
            if (spinners.getItemAtPosition(i).toString().equalsIgnoreCase(string)){
                position = i;
                break;
            }
        }
        return position;
    }

    public static void insertProject(Context context) {
        final Project project = new Project();
        if (project.getAll().size() == 0) {
            Intent intent = new Intent(context, ProjectActivity.class);
            context.startActivity(intent);
        }
    }

    public static void returnHome(Context context) {
        Intent intent = new Intent(context, NavigationDrawerActivity.class);
        context.startActivity(intent);
    }

}
