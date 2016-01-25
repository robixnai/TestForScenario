package com.scenario.robson.testforscenario.views.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;

import com.scenario.robson.testforscenario.R;
import com.scenario.robson.testforscenario.helpers.AppHelper;
import com.scenario.robson.testforscenario.models.entities.LightFixture;
import com.scenario.robson.testforscenario.models.entities.Module;

import java.util.ArrayList;
import java.util.List;

import fr.ganfra.materialspinner.MaterialSpinner;

public class LigtFixtureModuleActivity extends AppCompatActivity {

    public static final String LIGHT_FICTURE_ID = "light_fixture_id";

    private String mLightFixtureName;
    private EditText mEditTexteditTextName;
    private MaterialSpinner mSpinnerModule;
    private Button mButtonSave;
    private ArrayAdapter<String> mAdapter;
    private List<String> mListModules;
    private LightFixture mLightFixture;
    private Module mModule;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ligt_fixture_module);
        setTitle(R.string.LightFixture);

        getExtras();
        bindObjects();
        listModules();
        bindElements();
        setElementsValues();
        setListenerElements();
    }

    private void getExtras() {
        final Intent intent = getIntent();
        final Bundle extras = intent.getExtras();
        mLightFixtureName = extras.getString(LIGHT_FICTURE_ID);
    }

    private void bindObjects() {
        mLightFixture = new LightFixture();
        mModule = new Module();
    }

    private void listModules() {
        final List<Module> modules = mModule.getAll();
        mListModules = new ArrayList<>();

        for (Module m: modules) {
            mListModules.add(m.getName());
        }
    }

    private void bindElements() {
        mEditTexteditTextName = (EditText) findViewById(R.id.editTextName);

        mSpinnerModule = (MaterialSpinner) findViewById(R.id.spinerProject);
        mAdapter = new ArrayAdapter<>(LigtFixtureModuleActivity.this, android.R.layout.simple_spinner_item, mListModules);
        mAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinnerModule.setAdapter(mAdapter);

        mButtonSave = (Button) findViewById(R.id.BtnSave);
    }

    private void setElementsValues() {
        mLightFixture.setName(mLightFixtureName);
        mLightFixture = mLightFixture.getLightFixture(mLightFixture);

        mEditTexteditTextName.setText(mLightFixture.getName());
        mEditTexteditTextName.setEnabled(false);
        final Module module = mLightFixture.getModule();
        if (module != null) {
            final String spinnerText = module.getName();
            mSpinnerModule.setSelection(AppHelper.spinerSelection(mSpinnerModule, spinnerText));
        }
    }

    private void setListenerElements() {
        mButtonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateLightFixture();
            }
        });
    }

    private void updateLightFixture() {
        final String spinnerValue = mSpinnerModule.getSelectedItem().toString();
        mModule.setName(spinnerValue);

        boolean isValid = AppHelper.verifyMandatoryEditText(mEditTexteditTextName);
        isValid = isValid & AppHelper.verifyMandatorySpiner(mSpinnerModule);

        if (isValid) {
            mLightFixture.setName(mEditTexteditTextName.getText().toString().trim());
            mLightFixture.setModule(mModule.getModule(mModule));
            mLightFixture.update();

            super.setResult(RESULT_OK);

            AppHelper.returnHome(LigtFixtureModuleActivity.this);
        }
    }

}
