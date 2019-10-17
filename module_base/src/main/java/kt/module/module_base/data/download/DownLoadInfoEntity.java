package kt.module.module_base.data.download;

import android.os.Parcel;
import android.os.Parcelable;

public class DownLoadInfoEntity implements Parcelable {
    private int fileId;
    private String url;
    private String fileName;
    private String fileType;
    private String progress;
    private boolean isComplete;

    public DownLoadInfoEntity() {
    }

    public DownLoadInfoEntity(int fileId, String url, String fileName, String fileType, String progress, boolean isComplete) {
        this.fileId = fileId;
        this.url = url;
        this.fileName = fileName;
        this.fileType = fileType;
        this.progress = progress;
        this.isComplete = isComplete;
    }

    protected DownLoadInfoEntity(Parcel in) {
        fileId = in.readInt();
        url = in.readString();
        fileName = in.readString();
        fileType = in.readString();
        progress = in.readString();
        isComplete = in.readByte() != 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(fileId);
        dest.writeString(url);
        dest.writeString(fileName);
        dest.writeString(fileType);
        dest.writeString(progress);
        dest.writeByte((byte) (isComplete ? 1 : 0));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<DownLoadInfoEntity> CREATOR = new Creator<DownLoadInfoEntity>() {
        @Override
        public DownLoadInfoEntity createFromParcel(Parcel in) {
            return new DownLoadInfoEntity(in);
        }

        @Override
        public DownLoadInfoEntity[] newArray(int size) {
            return new DownLoadInfoEntity[size];
        }
    };

    @Override
    public String toString() {
        return "DownLoadInfoEntity{" +
                "fileId=" + fileId +
                ", url='" + url + '\'' +
                ", fileName='" + fileName + '\'' +
                ", fileType='" + fileType + '\'' +
                ", progress='" + progress + '\'' +
                ", isComplete='" + isComplete + '\'' +
                '}';
    }

    public int getFileId() {
        return fileId;
    }

    public void setFileId(int fileId) {
        this.fileId = fileId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public String getProgress() {
        return progress;
    }

    public void setProgress(String progress) {
        this.progress = progress;
    }

    public boolean isComplete() {
        return isComplete;
    }

    public void setComplete(boolean complete) {
        isComplete = complete;
    }
}
