package kt.module.base_module.data;

import android.os.Parcel;
import android.os.Parcelable;
import com.chad.library.adapter.base.entity.MultiItemEntity;

import java.util.List;

public class ObjectEntity implements Parcelable, MultiItemEntity {

    private String title;
    private int show_template;
    private int type;
    private List<ChildEntity> child;

    private boolean padding;
    private String text;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public ObjectEntity() {
    }

    public boolean isPadding() {
        return padding;
    }

    public void setPadding(boolean padding) {
        this.padding = padding;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getShow_template() {
        return show_template;
    }

    public void setShow_template(int show_template) {
        this.show_template = show_template;
    }

    public List<ChildEntity> getChild() {
        return child;
    }

    public void setChild(List<ChildEntity> child) {
        this.child = child;
    }

    @Override
    public String toString() {
        return "ObjectEntity{" +
                "title='" + title + '\'' +
                ", show_template=" + show_template +
                ", child=" + child +
                '}';
    }

    public static Creator<ObjectEntity> getCREATOR() {
        return CREATOR;
    }

    protected ObjectEntity(Parcel in) {
        title = in.readString();
        show_template = in.readInt();
    }

    public ObjectEntity(String title, int show_template, List<ChildEntity> child) {
        this.title = title;
        this.show_template = show_template;
        this.child = child;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeInt(show_template);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ObjectEntity> CREATOR = new Creator<ObjectEntity>() {
        @Override
        public ObjectEntity createFromParcel(Parcel in) {
            return new ObjectEntity(in);
        }

        @Override
        public ObjectEntity[] newArray(int size) {
            return new ObjectEntity[size];
        }
    };

    @Override
    public int getItemType() {
        return type;
    }
}
