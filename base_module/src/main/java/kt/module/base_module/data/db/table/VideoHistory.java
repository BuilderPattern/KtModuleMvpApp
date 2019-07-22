package kt.module.base_module.data.db.table;

import android.os.Parcel;
import android.os.Parcelable;
import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

@Entity
public class VideoHistory implements Parcelable {
    private String imageSource;

    @Id
    private Long videoId;
    private String videoName;
    private Long createTime;
    private String columnType;
    private Integer videoType;
    private String score;
    private String shareUrl;
    private Integer playType;
    private String image;
    private Integer cpId;
    private Integer cnl_flag_fav;
    private Long fav_createtime;
    private String director;
    private Long cnl_play_time;
    private String actor;
    private String duration;
    private String imageTv;
    private Integer showId;
    private String showName;
    private Integer videoPermission;
    private Integer video_category;
    private Integer isReview;
    private String url;
    private String videoImageY;
    private String contentPictrues;
    private byte[] data;

    @Generated(hash = 524051933)
    public VideoHistory() {
    }

    public VideoHistory(Long videoId) {
        this.videoId = videoId;
    }

    @Generated(hash = 434256393)
    public VideoHistory(String imageSource, Long videoId, String videoName, Long createTime, String columnType, Integer videoType, String score, String shareUrl, Integer playType, String image, Integer cpId, Integer cnl_flag_fav, Long fav_createtime, String director, Long cnl_play_time, String actor, String duration, String imageTv, Integer showId, String showName, Integer videoPermission, Integer video_category, Integer isReview, String url, String videoImageY, String contentPictrues, byte[] data) {
        this.imageSource = imageSource;
        this.videoId = videoId;
        this.videoName = videoName;
        this.createTime = createTime;
        this.columnType = columnType;
        this.videoType = videoType;
        this.score = score;
        this.shareUrl = shareUrl;
        this.playType = playType;
        this.image = image;
        this.cpId = cpId;
        this.cnl_flag_fav = cnl_flag_fav;
        this.fav_createtime = fav_createtime;
        this.director = director;
        this.cnl_play_time = cnl_play_time;
        this.actor = actor;
        this.duration = duration;
        this.imageTv = imageTv;
        this.showId = showId;
        this.showName = showName;
        this.videoPermission = videoPermission;
        this.video_category = video_category;
        this.isReview = isReview;
        this.url = url;
        this.videoImageY = videoImageY;
        this.contentPictrues = contentPictrues;
        this.data = data;
    }

    protected VideoHistory(Parcel in) {
        imageSource = in.readString();
        if (in.readByte() == 0) {
            videoId = null;
        } else {
            videoId = in.readLong();
        }
        videoName = in.readString();
        if (in.readByte() == 0) {
            createTime = null;
        } else {
            createTime = in.readLong();
        }
        columnType = in.readString();
        if (in.readByte() == 0) {
            videoType = null;
        } else {
            videoType = in.readInt();
        }
        score = in.readString();
        shareUrl = in.readString();
        if (in.readByte() == 0) {
            playType = null;
        } else {
            playType = in.readInt();
        }
        image = in.readString();
        if (in.readByte() == 0) {
            cpId = null;
        } else {
            cpId = in.readInt();
        }
        if (in.readByte() == 0) {
            cnl_flag_fav = null;
        } else {
            cnl_flag_fav = in.readInt();
        }
        if (in.readByte() == 0) {
            fav_createtime = null;
        } else {
            fav_createtime = in.readLong();
        }
        director = in.readString();
        if (in.readByte() == 0) {
            cnl_play_time = null;
        } else {
            cnl_play_time = in.readLong();
        }
        actor = in.readString();
        duration = in.readString();
        imageTv = in.readString();
        if (in.readByte() == 0) {
            showId = null;
        } else {
            showId = in.readInt();
        }
        showName = in.readString();
        if (in.readByte() == 0) {
            videoPermission = null;
        } else {
            videoPermission = in.readInt();
        }
        if (in.readByte() == 0) {
            video_category = null;
        } else {
            video_category = in.readInt();
        }
        if (in.readByte() == 0) {
            isReview = null;
        } else {
            isReview = in.readInt();
        }
        url = in.readString();
        videoImageY = in.readString();
        contentPictrues = in.readString();
        data = in.createByteArray();
    }

    public static final Creator<VideoHistory> CREATOR = new Creator<VideoHistory>() {
        @Override
        public VideoHistory createFromParcel(Parcel in) {
            return new VideoHistory(in);
        }

        @Override
        public VideoHistory[] newArray(int size) {
            return new VideoHistory[size];
        }
    };

    public String getImageSource() {
        return imageSource;
    }

    public void setImageSource(String imageSource) {
        this.imageSource = imageSource;
    }

    public Long getVideoId() {
        return videoId;
    }

    public void setVideoId(Long videoId) {
        this.videoId = videoId;
    }

    public String getVideoName() {
        return videoName;
    }

    public void setVideoName(String videoName) {
        this.videoName = videoName;
    }

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    public String getColumnType() {
        return columnType;
    }

    public void setColumnType(String columnType) {
        this.columnType = columnType;
    }

    public Integer getVideoType() {
        return videoType;
    }

    public void setVideoType(Integer videoType) {
        this.videoType = videoType;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getShareUrl() {
        return shareUrl;
    }

    public void setShareUrl(String shareUrl) {
        this.shareUrl = shareUrl;
    }

    public Integer getPlayType() {
        return playType;
    }

    public void setPlayType(Integer playType) {
        this.playType = playType;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Integer getCpId() {
        return cpId;
    }

    public void setCpId(Integer cpId) {
        this.cpId = cpId;
    }

    public Integer getCnl_flag_fav() {
        return cnl_flag_fav;
    }

    public void setCnl_flag_fav(Integer cnl_flag_fav) {
        this.cnl_flag_fav = cnl_flag_fav;
    }

    public Long getFav_createtime() {
        return fav_createtime;
    }

    public void setFav_createtime(Long fav_createtime) {
        this.fav_createtime = fav_createtime;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public Long getCnl_play_time() {
        return cnl_play_time;
    }

    public void setCnl_play_time(Long cnl_play_time) {
        this.cnl_play_time = cnl_play_time;
    }

    public String getActor() {
        return actor;
    }

    public void setActor(String actor) {
        this.actor = actor;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getImageTv() {
        return imageTv;
    }

    public void setImageTv(String imageTv) {
        this.imageTv = imageTv;
    }

    public Integer getShowId() {
        return showId;
    }

    public void setShowId(Integer showId) {
        this.showId = showId;
    }

    public String getShowName() {
        return showName;
    }

    public void setShowName(String showName) {
        this.showName = showName;
    }

    public Integer getVideoPermission() {
        return videoPermission;
    }

    public void setVideoPermission(Integer videoPermission) {
        this.videoPermission = videoPermission;
    }

    public Integer getVideo_category() {
        return video_category;
    }

    public void setVideo_category(Integer video_category) {
        this.video_category = video_category;
    }

    public Integer getIsReview() {
        return isReview;
    }

    public void setIsReview(Integer isReview) {
        this.isReview = isReview;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getVideoImageY() {
        return videoImageY;
    }

    public void setVideoImageY(String videoImageY) {
        this.videoImageY = videoImageY;
    }

    public String getContentPictrues() {
        return contentPictrues;
    }

    public void setContentPictrues(String contentPictrues) {
        this.contentPictrues = contentPictrues;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(imageSource);
        if (videoId == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeLong(videoId);
        }
        dest.writeString(videoName);
        if (createTime == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeLong(createTime);
        }
        dest.writeString(columnType);
        if (videoType == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(videoType);
        }
        dest.writeString(score);
        dest.writeString(shareUrl);
        if (playType == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(playType);
        }
        dest.writeString(image);
        if (cpId == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(cpId);
        }
        if (cnl_flag_fav == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(cnl_flag_fav);
        }
        if (fav_createtime == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeLong(fav_createtime);
        }
        dest.writeString(director);
        if (cnl_play_time == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeLong(cnl_play_time);
        }
        dest.writeString(actor);
        dest.writeString(duration);
        dest.writeString(imageTv);
        if (showId == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(showId);
        }
        dest.writeString(showName);
        if (videoPermission == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(videoPermission);
        }
        if (video_category == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(video_category);
        }
        if (isReview == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(isReview);
        }
        dest.writeString(url);
        dest.writeString(videoImageY);
        dest.writeString(contentPictrues);
        dest.writeByteArray(data);
    }
}
