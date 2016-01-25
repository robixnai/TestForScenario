package com.scenario.robson.testforscenario.models.entities;

import android.os.Parcel;
import android.os.Parcelable;

import com.scenario.robson.testforscenario.models.persistence.ProjectsRepository;

import java.util.Arrays;
import java.util.List;


/**
 * Created by robson on 18/01/16.
 */
public class Project implements Parcelable {

    private Integer mId;
    private String mName;
    private byte[] mImage;

    public Project() {}

    public Integer getId() {
        return mId;
    }

    public void setId(Integer id) {
        this.mId = id;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        this.mName = name;
    }

    public byte[] getImage() {
        return mImage;
    }

    public void setImage(byte[] image) {
        this.mImage = image;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Project project = (Project) o;

        if (!mId.equals(project.mId)) return false;
        if (!mName.equals(project.mName)) return false;
        return Arrays.equals(mImage, project.mImage);

    }

    @Override
    public int hashCode() {
        int result = mId.hashCode();
        result = 31 * result + mName.hashCode();
        result = 31 * result + Arrays.hashCode(mImage);
        return result;
    }

    @Override
    public String toString() {
        return "Project{" +
                "mId=" + mId +
                ", mName='" + mName + '\'' +
                ", mImage=" + Arrays.toString(mImage) +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.mId);
        dest.writeString(this.mName);
        dest.writeByteArray(this.mImage);
    }

    protected Project(Parcel in) {
        this.mId = (Integer) in.readValue(Integer.class.getClassLoader());
        this.mName = in.readString();
        this.mImage = in.createByteArray();
    }

    public static final Creator<Project> CREATOR = new Creator<Project>() {
        public Project createFromParcel(Parcel source) {
            return new Project(source);
        }

        public Project[] newArray(int size) {
            return new Project[size];
        }
    };

    public static List<Project> getAll() {
        return ProjectsRepository.getInstance().getAll();
    }

    public Project getProject(Project project) {
        return ProjectsRepository.getInstance().getProject(project);
    }

    public void save() {
        ProjectsRepository.getInstance().save(this);
    }

}
