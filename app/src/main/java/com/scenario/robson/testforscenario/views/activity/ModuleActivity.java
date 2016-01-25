package com.scenario.robson.testforscenario.views.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.scenario.robson.testforscenario.R;
import com.scenario.robson.testforscenario.helpers.AppHelper;
import com.scenario.robson.testforscenario.models.entities.Ambient;
import com.scenario.robson.testforscenario.models.entities.Module;
import com.scenario.robson.testforscenario.storages.external.async.LoadImageInternetTask;
import com.scenario.robson.testforscenario.storages.external.listener.LoadImageInternetListener;

public class ModuleActivity extends AppCompatActivity {

    public static final String MODULE_ID = "module_id";

    private EditText mEditTexteditTextName;
    private Module mModule;
    private Button mButtonSave;

    private ImageView mImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_module);
        setTitle(R.string.Module);

        bindObjects();
        bindElements();
        setListenerElements();
    }

    @Override
    protected void onResume() {
        super.onResume();

        downloadImage();
    }

    private void bindObjects() {
        mModule = new Module();
    }

    private void bindElements() {
        mImage = (ImageView)findViewById(R.id.download_image);
        mEditTexteditTextName = (EditText) findViewById(R.id.editTextName);
        mButtonSave = (Button) findViewById(R.id.BtnSave);
    }

    private void setListenerElements() {
        mButtonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveModule();
            }
        });
    }

    private void saveModule() {
        boolean isValid = AppHelper.verifyMandatoryEditText(mEditTexteditTextName);

        if (isValid) {
            mModule.setName(mEditTexteditTextName.getText().toString().trim());
            mModule.setAmbient(new Ambient());
            mModule.save();

            super.setResult(RESULT_OK);

            final Module module = mModule.getModule(mModule);
            final String moduleName = module.getName();
            final Intent intent = new Intent(ModuleActivity.this, ModuleAmbientActivity.class);
            final Bundle extras = new Bundle();

            extras.putString(MODULE_ID, moduleName);
            intent.putExtras(extras);
            startActivity(intent);
        }
    }

    private void downloadImage() {
        final String urlImage = "https://dl.dropboxusercontent.com/u/32944129/Scenario.png";

        new LoadImageInternetTask(ModuleActivity.this, new LoadImageInternetListener() {
            @Override
            public void onPostExecute(Bitmap image) {
                if(image != null){
                    mImage.setVisibility(View.VISIBLE);
                    mImage.setImageBitmap(image);
                }else{
                    Toast.makeText(ModuleActivity.this, R.string.error_image, Toast.LENGTH_SHORT).show();
                }
            }
        }, false).execute(urlImage);
    }

}
