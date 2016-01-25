package com.scenario.robson.testforscenario.views.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.scenario.robson.testforscenario.R;
import com.scenario.robson.testforscenario.helpers.AppHelper;
import com.scenario.robson.testforscenario.models.entities.Project;

public class ProjectActivity extends AppCompatActivity {

    public static final int REQUEST_CAMERA = 0;
    public static final int SELECT_FILE = 1;

    private ImageView mImageViewImage;
    private EditText mTextViewName;
    private Button mButtonSave;
    private Project mProject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project);
        setTitle(R.string.Projects);

        bindObjcts();
        bindElements();
        setListenerElements();
    }

    private void bindObjcts() {
        mProject = new Project();
    }

    private void bindElements() {
        mTextViewName = (EditText) findViewById(R.id.editTextName);
        mImageViewImage = (ImageView) findViewById(R.id.imageViewImage);
        mButtonSave = (Button) findViewById(R.id.BtnSave);
    }

    private void setListenerElements() {
        mImageViewImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectImage();
            }
        });
        mButtonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveProject();
            }
        });
    }

    private void selectImage() {
        final CharSequence[] items = {"Tirar foto", "Carregar imgem"};
        final AlertDialog.Builder builder = new AlertDialog.Builder(ProjectActivity.this);
        menuImage(items, builder);
    }

    private void menuImage(final CharSequence[] items, AlertDialog.Builder builder) {
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (items[item].equals("Tirar foto")) {
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(intent, REQUEST_CAMERA);
                } else if (items[item].equals("Carregar imgem")) {
                    Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    intent.setType("image/*");
                    startActivityForResult(Intent.createChooser(intent, "Selecione o arquivo"), SELECT_FILE);
                }
            }
        });
        builder.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_CAMERA) {
                final Bitmap requestCamera = AppHelper.requestCamera(data);
                mImageViewImage.setImageBitmap(requestCamera);
            } else if (requestCode == SELECT_FILE) {
                final Bitmap selectFile = AppHelper.selectFile(data, ProjectActivity.this);
                mImageViewImage.setImageBitmap(selectFile);
            }
        }
    }

    private void saveProject() {
        boolean isValid = AppHelper.verifyMandatoryEditText(mTextViewName);

        if (isValid) {
            mImageViewImage.buildDrawingCache();
            mProject.setImage(AppHelper.getBitmapAsByteArray(mImageViewImage.getDrawingCache()));
            mProject.setName(mTextViewName.getText().toString().trim());
            mProject.save();

            super.setResult(RESULT_OK);

            AppHelper.returnHome(ProjectActivity.this);
        }
    }

}
