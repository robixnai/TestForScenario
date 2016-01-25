package com.scenario.robson.testforscenario.views.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.scenario.robson.testforscenario.R;
import com.scenario.robson.testforscenario.helpers.AppHelper;
import com.scenario.robson.testforscenario.models.entities.Ambient;
import com.scenario.robson.testforscenario.models.entities.Project;

import java.util.ArrayList;
import java.util.List;

import fr.ganfra.materialspinner.MaterialSpinner;

public class AmbientEditActivity extends AppCompatActivity {

    public static final String AMBIENT_ID = "ambient_id";

    private String mAmbientName;
    private EditText mEditTexteditTextName;
    private MaterialSpinner mSpinnerProject;
    private ArrayAdapter<String> mAdapter;
    private List<String> mListProjects;
    private LinearLayout mLinearLayout;
    private CheckBox[] mCheckboxes;
    private Button mButtonSave;
    private Project mProject;
    private Ambient mAmbient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ambient_edit);
        setTitle(R.string.Ambient);

        getExtras();
        bindObjects();
        listProjects();
        bindElements();
        setElementsValues();
        setListenerElements();
    }

    private void getExtras() {
        final Intent intent = getIntent();
        final Bundle extras = intent.getExtras();
        mAmbientName = extras.getString(AMBIENT_ID);
    }

    private void bindObjects() {
        mAmbient = new Ambient();
        mProject = new Project();
    }

    private void listProjects() {
        final List<Project> projects = mProject.getAll();
        mListProjects = new ArrayList<>();

        for (Project p: projects) {
            mListProjects.add(p.getName());
        }
    }

    private void bindElements() {
        mEditTexteditTextName = (EditText) findViewById(R.id.editTextName);

        mSpinnerProject = (MaterialSpinner) findViewById(R.id.spinerProject);
        mAdapter = new ArrayAdapter<>(AmbientEditActivity.this, android.R.layout.simple_spinner_item, mListProjects);
        mAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinnerProject.setAdapter(mAdapter);

        mLinearLayout = (LinearLayout) findViewById(R.id.ambientsd);
        mButtonSave = (Button) findViewById(R.id.BtnSave);
    }

    private void setElementsValues() {
        mAmbient.setName(mAmbientName);
        mAmbient = mAmbient.getAmbient(mAmbient);

        mEditTexteditTextName.setText(mAmbient.getName());
        final Project project = mAmbient.getProject();
        if (project != null) {
            final String spinnerText = project.getName();
            mSpinnerProject.setSelection(AppHelper.spinerSelection(mSpinnerProject, spinnerText));
        }
    }

    private void setListenerElements() {
        mSpinnerProject.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                final String value = mSpinnerProject.getSelectedItem().toString();

                removeListCheckBoxAmbient();
                addListCheckBoxAmbient(value);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                removeListCheckBoxAmbient();
            }
        });
        mButtonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateAmbient();
            }
        });
    }

    private void addListCheckBoxAmbient(String projectName) {
        final Project project = new Project();
        project.setName(projectName);
        mProject = project.getProject(project);

        if (mProject != null) {
            final List<Ambient> ambients = mAmbient.getAmbientsProject(mProject.getId());
            final int total = ambients.size();


            List<Ambient> checkAmbients = mAmbient.getAmbients();
            if (total > 0) {
                mCheckboxes = new CheckBox[total];
                for (int i = 0; i < total; i++) {
                    CheckBox checkBox = new CheckBox(this);
                    checkBox.setText(ambients.get(i).getName());
                    checkBox.setId(ambients.get(i).getId());

                    if (checkAmbients != null || checkAmbients.size() > 0) {
                        for (Ambient ambient : checkAmbients) {
                            if (ambients.get(i).getId() == ambient.getId()) {
                                checkBox.setChecked(true);
                            }
                        }
                    }

                    mLinearLayout.addView(checkBox);
                    mCheckboxes[i] = checkBox;
                }
            }
        }
    }

    private void removeListCheckBoxAmbient() {
        mLinearLayout.removeAllViews();
    }

    private void updateAmbient() {
        final String spinnerValue = mSpinnerProject.getSelectedItem().toString();
        mProject.setName(spinnerValue);

        boolean isValid = AppHelper.verifyMandatoryEditText(mEditTexteditTextName);
        isValid = isValid & AppHelper.verifyMandatorySpiner(mSpinnerProject);

        if (isValid) {
            mAmbient.setProject(mProject.getProject(mProject));
            mAmbient.setName(mEditTexteditTextName.getText().toString().trim());
            mAmbient.update();

            List<Ambient> ambients = new ArrayList<>();
            for (CheckBox checkBox : mCheckboxes) {
                if (checkBox.isChecked()) {
                    Ambient ambient = new Ambient();
                    ambient.setName(checkBox.getText().toString());
                    ambient.setId(mAmbient.getAmbient(ambient).getId());
                    ambients.add(ambient);
                }
            }

            mAmbient.setAmbients(ambients);
            mAmbient.saveAmbients();

            super.setResult(RESULT_OK);

            AppHelper.returnHome(AmbientEditActivity.this);
        }
    }

}
