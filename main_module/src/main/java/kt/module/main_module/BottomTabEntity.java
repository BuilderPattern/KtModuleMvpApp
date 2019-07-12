package kt.module.main_module;

import android.os.Parcel;
import android.os.Parcelable;
import com.flyco.tablayout.listener.CustomTabEntity;

public class BottomTabEntity implements CustomTabEntity, Parcelable {

    private String title;
    private int selectedIcon;
    private int unSelectedIcon;

    protected BottomTabEntity(Parcel in) {
        title = in.readString();
        selectedIcon = in.readInt();
        unSelectedIcon = in.readInt();
    }

    public BottomTabEntity() {
    }

    public BottomTabEntity(String title, int selectedIcon, int unSelectedIcon) {
        this.title = title;
        this.selectedIcon = selectedIcon;
        this.unSelectedIcon = unSelectedIcon;
    }

    @Override
    public String toString() {
        return "BottomTabEntity{" +
                "title='" + title + '\'' +
                ", selectedIcon=" + selectedIcon +
                ", unSelectedIcon=" + unSelectedIcon +
                '}';
    }

    public static final Creator<BottomTabEntity> CREATOR = new Creator<BottomTabEntity>() {
        @Override
        public BottomTabEntity createFromParcel(Parcel in) {
            return new BottomTabEntity(in);
        }

        @Override
        public BottomTabEntity[] newArray(int size) {
            return new BottomTabEntity[size];
        }
    };

    @Override
    public String getTabTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public int getTabSelectedIcon() {
        return selectedIcon;
    }

    public void setSelectedIcon(int selectedIcon) {
        this.selectedIcon = selectedIcon;
    }

    @Override
    public int getTabUnselectedIcon() {
        return unSelectedIcon;
    }

    public void setUnSelectedIcon(int unSelectedIcon) {
        this.unSelectedIcon = unSelectedIcon;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeInt(selectedIcon);
        dest.writeInt(unSelectedIcon);
    }
}
