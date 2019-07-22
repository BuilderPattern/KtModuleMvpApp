package kt.module.base_module.data.db.table;

import android.os.Parcel;
import android.os.Parcelable;
import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

@Entity
public class RvData implements Parcelable {
    @Id
    private Long id;
    private String name;
    private int age;

    protected RvData(Parcel in) {
        if (in.readByte() == 0) {
            id = null;
        } else {
            id = in.readLong();
        }
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    @Generated(hash = 287697888)
    public RvData(Long id, String name, int age) {
        this.id = id;
        this.name = name;
        this.age = age;
    }

    public RvData(String name, int age) {
        this.name = name;
        this.age = age;
    }

    @Generated(hash = 1506817874)
    public RvData() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (id == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeLong(id);
        }
        dest.writeString(name);
        dest.writeInt(age);
    }
}
