package kt.module.base_module.data;

import android.os.Parcel;
import android.os.Parcelable;

public class RvData implements Parcelable {
    private String name;
    private int age;

    public RvData() {
    }

    public RvData(String name, int age) {
        this.name = name;
        this.age = age;
    }

    @Override
    public String toString() {
        return "RvData{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }

    protected RvData(Parcel in) {
        name = in.readString();
        age = in.readInt();
    }

    public static final Creator<RvData> CREATOR = new Creator<RvData>() {
        @Override
        public RvData createFromParcel(Parcel in) {
            return new RvData(in);
        }

        @Override
        public RvData[] newArray(int size) {
            return new RvData[size];
        }
    };

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeInt(age);
    }
}
