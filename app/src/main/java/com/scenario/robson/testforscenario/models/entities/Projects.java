package com.scenario.robson.testforscenario.models.entities;

import com.scenario.robson.testforscenario.models.persistence.AmbientsRepository;
import com.scenario.robson.testforscenario.models.persistence.ProjectsRepository;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by robson on 24/01/16.
 */
public class Projects {

    private Ambient mAmbient;
    private List<Module> mModules;

    public Projects() {}

    public Ambient getAmbient() {
        return mAmbient;
    }

    public void setAmbient(Ambient ambient) {
        this.mAmbient = ambient;
    }

    public List<Module> getModules() {
        return mModules;
    }

    public void setModules(List<Module> modules) {
        this.mModules = modules;
    }

    public List<Projects> getProjects(Integer projectId) {
        final List<Projects> projectsList = new ArrayList<>();

        final List<Ambient> ambientsProject = AmbientsRepository.getInstance().getAmbientsProject(projectId);
        for (Ambient ambient : ambientsProject) {
            ambient.setProject(ProjectsRepository.getInstance().getProject(ambient.getProject()));
            Projects projects = new Projects();
            projects.setModules(AmbientsRepository.getInstance().getAmbientModule(ambient));
            projects.setAmbient(ambient);
            projectsList.add(projects);
        }

        return projectsList;
    }

}
