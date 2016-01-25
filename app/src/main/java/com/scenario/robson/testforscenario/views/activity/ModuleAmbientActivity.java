package com.scenario.robson.testforscenario.views.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;

import com.scenario.robson.testforscenario.R;
import com.scenario.robson.testforscenario.helpers.AppHelper;
import com.scenario.robson.testforscenario.models.entities.Ambient;
import com.scenario.robson.testforscenario.models.entities.Module;

import java.util.ArrayList;
import java.util.List;

import fr.ganfra.materialspinner.MaterialSpinner;

public class ModuleAmbientActivity extends AppCompatActivity {

    public static final String MODULE_ID = "module_id";

    private String mModuleName;
    private EditText mEditTexteditTextName;
    private MaterialSpinner mSpinnerAbient;
    private ArrayAdapter<String> mAdapter;
    private List<String> mListAmbients;
    private Button mButtonSave;
    private Module mModule;
    private Ambient mAmbient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_module_ambient);
        setTitle(R.string.Module);

        getExtras();
        bindObjects();
        listAmbients();
        bindElements();
        setElementsValues();
        setListenerElements();
    }

    private void getExtras() {
        final Intent intent = getIntent();
        final Bundle extras = intent.getExtras();
        mModuleName = extras.getString(MODULE_ID);
    }

    private void bindObjects() {
        mModule = new Module();
        mAmbient = new Ambient();
    }

    private void listAmbients() {
        final List<Ambient> ambients = mAmbient.getAll();
        mListAmbients = new ArrayList<>();

        for (Ambient m: ambients) {
            mListAmbients.add(m.getName());
        }
    }

    private void bindElements() {
        mEditTexteditTextName = (EditText) findViewById(R.id.editTextName);

        mSpinnerAbient = (MaterialSpinner) findViewById(R.id.spinerProject);
        mAdapter = new ArrayAdapter<>(ModuleAmbientActivity.this, android.R.layout.simple_spinner_item, mListAmbients);
        mAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinnerAbient.setAdapter(mAdapter);

        mButtonSave = (Button) findViewById(R.id.BtnSave);
    }

    private void setElementsValues() {
        mModule.setName(mModuleName);
        mModule = mModule.getModule(mModule);

        mEditTexteditTextName.setText(mModule.getName());
        mEditTexteditTextName.setEnabled(false);
        final Ambient ambient = mModule.getAmbient();
        if (ambient != null) {
            final String spinnerText = ambient.getName();
            mSpinnerAbient.setSelection(AppHelper.spinerSelection(mSpinnerAbient, spinnerText));
        }
    }

    private void setListenerElements() {
        mButtonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateModule();
            }
        });
    }

    private void updateModule() {
        final String spinnerValue = mSpinnerAbient.getSelectedItem().toString();
        mAmbient.setName(spinnerValue);

        boolean isValid = AppHelper.verifyMandatoryEditText(mEditTexteditTextName);
        isValid = isValid & AppHelper.verifyMandatorySpiner(mSpinnerAbient);

        if (isValid) {
            mModule.setName(mEditTexteditTextName.getText().toString().trim());
            mModule.setAmbient(mAmbient.getAmbient(mAmbient));
            mModule.update();

            super.setResult(RESULT_OK);

            AppHelper.returnHome(ModuleAmbientActivity.this);
        }
    }

}
