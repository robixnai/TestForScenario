package com.scenario.robson.testforscenario.models.entities;

import android.os.Parcel;
import android.os.Parcelable;

import com.scenario.robson.testforscenario.models.persistence.AmbientsRepository;
import com.scenario.robson.testforscenario.models.persistence.ProjectsRepository;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by robson on 20/01/16.
 */
public class Ambient implements Parcelable {

    private Integer mId;
    private Project mProject;
    private String mName;
    private List<Ambient> mAmbients;

    public Ambient() {}

    public Integer getId() {
        return mId;
    }

    public void setId(Integer id) {
        this.mId = id;
    }

    public Project getProject() {
        return mProject;
    }

    public void setProject(Project project) {
        this.mProject = project;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        this.mName = name;
    }

    public List<Ambient> getAmbients() {
        return mAmbients;
    }

    public void setAmbients(List<Ambient> ambients) {
        this.mAmbients = ambients;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Ambient ambient = (Ambient) o;

        if (!mId.equals(ambient.mId)) return false;
        if (!mProject.equals(ambient.mProject)) return false;
        if (!mName.equals(ambient.mName)) return false;
        return mAmbients.equals(ambient.mAmbients);

    }

    @Override
    public int hashCode() {
        int result = mId.hashCode();
        result = 31 * result + mProject.hashCode();
        result = 31 * result + mName.hashCode();
        result = 31 * result + mAmbients.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Ambient{" +
                "mId=" + mId +
                ", mProject=" + mProject +
                ", mName='" + mName + '\'' +
                ", mAmbients=" + mAmbients +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.mId);
        dest.writeParcelable(this.mProject, 0);
        dest.writeString(this.mName);
        dest.writeList(this.mAmbients);
    }

    protected Ambient(Parcel in) {
        this.mId = (Integer) in.readValue(Integer.class.getClassLoader());
        this.mProject = in.readParcelable(Project.class.getClassLoader());
        this.mName = in.readString();
        this.mAmbients = new ArrayList<Ambient>();
        in.readList(this.mAmbients, List.class.getClassLoader());
    }

    public static final Parcelable.Creator<Ambient> CREATOR = new Parcelable.Creator<Ambient>() {
        public Ambient createFromParcel(Parcel source) {
            return new Ambient(source);
        }

        public Ambient[] newArray(int size) {
            return new Ambient[size];
        }
    };

    public static List<Ambient> getAll() {
        return AmbientsRepository.getInstance().getAll();
    }

    public Ambient getAmbient(Ambient ambient) {
        final Ambient ambientDb = AmbientsRepository.getInstance().getAmbient(ambient);
        ambientDb.setProject(ProjectsRepository.getInstance().getProject(ambientDb.getProject()));
        ambientDb.setAmbients(AmbientsRepository.getInstance().getAmbients(ambientDb));
        return ambientDb;
    }

    public void save() {
        AmbientsRepository.getInstance().save(this);
    }

    public void saveAmbients() {
        AmbientsRepository.getInstance().saveAmbients(this);
    }

    public void update() {
        AmbientsRepository.getInstance().update(this);
    }

    public List<Ambient> getAmbientsProject(Integer projectId) {
        return AmbientsRepository.getInstance().getAmbientsProject(projectId);
    }

}
