package com.scenario.robson.testforscenario.models.entities;

import android.os.Parcel;
import android.os.Parcelable;

import com.scenario.robson.testforscenario.models.persistence.LightFixturesRepository;
import com.scenario.robson.testforscenario.models.persistence.ModulesRepository;

import java.util.List;

/**
 * Created by robson on 21/01/16.
 */
public class LightFixture implements Parcelable {

    private Integer mId;
    private Module mModule;
    private String mName;

    public LightFixture() {
    }

    public Integer getId() {
        return mId;
    }

    public void setId(Integer id) {
        this.mId = id;
    }

    public Module getModule() {
        return mModule;
    }

    public void setModule(Module module) {
        this.mModule = module;
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

        LightFixture that = (LightFixture) o;

        if (!mId.equals(that.mId)) return false;
        if (!mModule.equals(that.mModule)) return false;
        return mName.equals(that.mName);

    }

    @Override
    public int hashCode() {
        int result = mId.hashCode();
        result = 31 * result + mModule.hashCode();
        result = 31 * result + mName.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "LightFixture{" +
                "mId=" + mId +
                ", mModule=" + mModule +
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
        dest.writeParcelable(this.mModule, 0);
        dest.writeString(this.mName);
    }

    protected LightFixture(Parcel in) {
        this.mId = (Integer) in.readValue(Integer.class.getClassLoader());
        this.mModule = in.readParcelable(Module.class.getClassLoader());
        this.mName = in.readString();
    }

    public static final Creator<LightFixture> CREATOR = new Creator<LightFixture>() {
        public LightFixture createFromParcel(Parcel source) {
            return new LightFixture(source);
        }

        public LightFixture[] newArray(int size) {
            return new LightFixture[size];
        }
    };

    public static List<LightFixture> getAll() {
        return LightFixturesRepository.getInstance().getAll();
    }

    public LightFixture getLightFixture(LightFixture lightFixture) {
        final LightFixture lightFixtureDb = LightFixturesRepository.getInstance().getLightFixture(lightFixture);
        lightFixtureDb.setModule(ModulesRepository.getInstance().getModule(lightFixtureDb.getModule()));
        return lightFixtureDb;
    }

    public void save() {
        LightFixturesRepository.getInstance().save(this);
    }

    public void update() {
        LightFixturesRepository.getInstance().update(this);
    }

}
