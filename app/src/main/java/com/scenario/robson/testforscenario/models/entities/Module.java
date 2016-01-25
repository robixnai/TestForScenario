package com.scenario.robson.testforscenario.models.entities;

import android.os.Parcel;
import android.os.Parcelable;

import com.scenario.robson.testforscenario.models.persistence.AmbientsRepository;
import com.scenario.robson.testforscenario.models.persistence.ModulesRepository;

import java.util.List;

/**
 * Created by robson on 21/01/16.
 */
public class Module implements Parcelable {

    private Integer mId;
    private Ambient mAmbient;
    private String mName;

    public Module() {}

    public Integer getId() {
        return mId;
    }

    public void setId(Integer id) {
        this.mId = id;
    }

    public Ambient getAmbient() {
        return mAmbient;
    }

    public void setAmbient(Ambient ambient) {
        this.mAmbient = ambient;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        this.mName = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Module module = (Module) o;

        if (!mId.equals(module.mId)) return false;
        if (!mAmbient.equals(module.mAmbient)) return false;
        return mName.equals(module.mName);

    }

    @Override
    public int hashCode() {
        int result = mId.hashCode();
        result = 31 * result + mAmbient.hashCode();
        result = 31 * result + mName.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Module{" +
                "mId=" + mId +
                ", mAmbient=" + mAmbient +
                ", mName='" + mName + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.mId);
        dest.writeParcelable(this.mAmbient, 0);
        dest.writeString(this.mName);
    }

    protected Module(Parcel in) {
        this.mId = (Integer) in.readValue(Integer.class.getClassLoader());
        this.mAmbient = in.readParcelable(Ambient.class.getClassLoader());
        this.mName = in.readString();
    }

    public static final Parcelable.Creator<Module> CREATOR = new Parcelable.Creator<Module>() {
        public Module createFromParcel(Parcel source) {
            return new Module(source);
        }

        public Module[] newArray(int size) {
            return new Module[size];
        }
    };

    public static List<Module> getAll() {
        return ModulesRepository.getInstance().getAll();
    }

    public Module getModule(Module module) {
        final Module moduleDb = ModulesRepository.getInstance().getModule(module);
        moduleDb.setAmbient(AmbientsRepository.getInstance().getAmbient(moduleDb.getAmbient()));
        return moduleDb;
    }

    public void save() {
        ModulesRepository.getInstance().save(this);
    }

    public void update() {
        ModulesRepository.getInstance().update(this);
    }

}
