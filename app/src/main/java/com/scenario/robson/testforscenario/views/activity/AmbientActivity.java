package com.scenario.robson.testforscenario.views.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.scenario.robson.testforscenario.R;
import com.scenario.robson.testforscenario.helpers.AppHelper;
import com.scenario.robson.testforscenario.models.entities.Ambient;
import com.scenario.robson.testforscenario.models.entities.Project;
import com.scenario.robson.testforscenario.storages.external.async.LoadImageInternetTask;
import com.scenario.robson.testforscenario.storages.external.listener.LoadImageInternetListener;

import java.util.ArrayList;
import java.util.List;

import fr.ganfra.materialspinner.MaterialSpinner;

public class AmbientActivity extends AppCompatActivity {

    public static final String AMBIENT_ID = "ambient_id";

    private EditText mEditTexteditTextName;
    private MaterialSpinner mSpinnerProject;
    private ArrayAdapter<String> mAdapter;
    private List<String> mListProjects;
    private LinearLayout mLinearLayout;
    private Project mProject;
    private Ambient mAmbient;
    private Button mButtonSave;
    private CheckBox[] mCheckboxes;

    private ImageView mImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ambient);
        setTitle(R.string.Ambient);

        bindObjects();
        listProjects();
        bindElements();
        setListenerElements();
    }

    @Override
    protected void onResume() {
        super.onResume();

        downloadImage();
    }

    private void bindObjects() {
        mProject = new Project();
        mAmbient = new Ambient();
    }

    private void listProjects() {
        final List<Project> projects = mProject.getAll();
        mListProjects = new ArrayList<>();

        for (Project p: projects) {
            mListProjects.add(p.getName());
        }
    }

    private void bindElements() {
        mImage = (ImageView)findViewById(R.id.download_image);
        mEditTexteditTextName = (EditText) findViewById(R.id.editTextName);

        mSpinnerProject = (MaterialSpinner) findViewById(R.id.spinerProject);
        mAdapter = new ArrayAdapter<>(AmbientActivity.this, android.R.layout.simple_spinner_item, mListProjects);
        mAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinnerProject.setAdapter(mAdapter);

        mLinearLayout = (LinearLayout) findViewById(R.id.ambientsd);
        mButtonSave = (Button) findViewById(R.id.BtnSave);
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
                saveAmbient();
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

            if (total > 0) {
                mCheckboxes = new CheckBox[total];
                for (int i = 0; i < total; i++) {
                    CheckBox checkBox = new CheckBox(this);
                    checkBox.setText(ambients.get(i).getName());
                    checkBox.setId(ambients.get(i).getId());
                    mLinearLayout.addView(checkBox);
                    mCheckboxes[i] = checkBox;
                }
            }
        }
    }

    private void removeListCheckBoxAmbient() {
        mLinearLayout.removeAllViews();
    }

    private void saveAmbient() {
        final String spinnerValue = mSpinnerProject.getSelectedItem().toString();
        mProject.setName(spinnerValue);

        boolean isValid = AppHelper.verifyMandatoryEditText(mEditTexteditTextName);
        isValid = isValid & AppHelper.verifyMandatorySpiner(mSpinnerProject);

        if (isValid) {
            mAmbient.setProject(mProject.getProject(mProject));
            mAmbient.setName(mEditTexteditTextName.getText().toString().trim());

            mAmbient.save();

            if (mCheckboxes != null) {
                List<Ambient> ambients = new ArrayList<>();
                for (CheckBox checkBox : mCheckboxes) {
                    if (checkBox.isChecked()) {
                        Ambient ambient = new Ambient();
                        ambient.setName(checkBox.getText().toString());
                        ambient.setId(mAmbient.getAmbient(ambient).getId());
                        ambients.add(ambient);
                    }
                }

                mAmbient.setId(mAmbient.getAmbient(mAmbient).getId());
                mAmbient.setAmbients(ambients);
                mAmbient.saveAmbients();
            }

            super.setResult(RESULT_OK);

            AppHelper.returnHome(AmbientActivity.this);
        }
    }

    private void downloadImage() {
        final String urlImage = "https://dl.dropboxusercontent.com/u/32944129/large_leaves_70mp.jpg";

        new LoadImageInternetTask(AmbientActivity.this, new LoadImageInternetListener() {
            @Override
            public void onPostExecute(Bitmap image) {
                if(image != null){
                    mImage.setVisibility(View.VISIBLE);
                    mImage.setImageBitmap(image);
                }else{
                    Toast.makeText(AmbientActivity.this, R.string.error_image, Toast.LENGTH_SHORT).show();
                }
            }
        }, true).execute(urlImage);
    }

}
