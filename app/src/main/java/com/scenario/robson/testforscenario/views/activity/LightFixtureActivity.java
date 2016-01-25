package com.scenario.robson.testforscenario.views.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.scenario.robson.testforscenario.R;
import com.scenario.robson.testforscenario.helpers.AppHelper;
import com.scenario.robson.testforscenario.models.entities.LightFixture;
import com.scenario.robson.testforscenario.models.entities.Module;

public class LightFixtureActivity extends AppCompatActivity {

    public static final String LIGHT_FICTURE_ID = "light_fixture_id";

    private EditText mEditTexteditTextName;
    private Button mButtonSave;
    private LightFixture mLightFixture;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_light_fixture);
        setTitle(R.string.LightFixture);

        bindObjects();
        bindElements();
        setListenerElements();
    }

    private void bindObjects() {
        mLightFixture = new LightFixture();
    }

    private void bindElements() {
        mEditTexteditTextName = (EditText) findViewById(R.id.editTextName);
        mButtonSave = (Button) findViewById(R.id.BtnSave);
    }

    private void setListenerElements() {
        mButtonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveLightFixture();
            }
        });
    }

    private void saveLightFixture() {
        boolean isValid = AppHelper.verifyMandatoryEditText(mEditTexteditTextName);

        if (isValid) {
            mLightFixture.setName(mEditTexteditTextName.getText().toString().trim());
            mLightFixture.setModule(new Module());
            mLightFixture.save();

            if (RESULT_OK == -1) {
                super.setResult(RESULT_OK);

                final LightFixture lightFixture = mLightFixture.getLightFixture(mLightFixture);
                final String lightFixtureName = lightFixture.getName();
                final Intent intent = new Intent(LightFixtureActivity.this, LigtFixtureModuleActivity.class);
                final Bundle extras = new Bundle();

                extras.putString(LIGHT_FICTURE_ID, lightFixtureName);
                intent.putExtras(extras);
                startActivity(intent);
            }

            AppHelper.returnHome(LightFixtureActivity.this);
        }
    }

}
