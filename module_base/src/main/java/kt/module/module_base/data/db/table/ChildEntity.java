package kt.module.module_base.data.db.table;

import org.greenrobot.greendao.annotation.*;

@Entity
public class ChildEntity{

    private Long objectId;
    @Id
    private Long id;
    private String title;
    private String uname;
    private String avatar;

    private String avatars;
    private String type_description;
    private String url;
    private String img;
    private int status;
    private String bgavatar;
    private int tcount;
    private String count;
    private String scount;
    private String type;
    private String subtitle;
    private String subtitle_new;
    private String title_img_new;
    private String type_new;
    private String thumb_img;
    private int ppp;
    private int splay_count;
    private int play_count;
    private int small_num;

    private String content;
    private String start_time;
    private String end_time;
    private String bm_start_time;
    private String bm_end_time;
    private int num;
    private int day_num;
    private String created_at;
    private String updated_at;
    private int ka_num;
    private String can_created_at;
    private int buy_count;
    private int course_num;
    private String listen_users_count;
    private String img_new;
    private String tex;
    private int sequence;
    private int baoming;

    private String type_course;
    private String body_img_new;

    public Long getObjectId() {
        return objectId;
    }

    public void setObjectId(Long objectId) {
        this.objectId = objectId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getAvatars() {
        return avatars;
    }

    public void setAvatars(String avatars) {
        this.avatars = avatars;
    }

    public String getType_description() {
        return type_description;
    }

    public void setType_description(String type_description) {
        this.type_description = type_description;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getBgavatar() {
        return bgavatar;
    }

    public void setBgavatar(String bgavatar) {
        this.bgavatar = bgavatar;
    }

    public int getTcount() {
        return tcount;
    }

    public void setTcount(int tcount) {
        this.tcount = tcount;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getScount() {
        return scount;
    }

    public void setScount(String scount) {
        this.scount = scount;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public String getSubtitle_new() {
        return subtitle_new;
    }

    public void setSubtitle_new(String subtitle_new) {
        this.subtitle_new = subtitle_new;
    }

    public String getTitle_img_new() {
        return title_img_new;
    }

    public void setTitle_img_new(String title_img_new) {
        this.title_img_new = title_img_new;
    }

    public String getType_new() {
        return type_new;
    }

    public void setType_new(String type_new) {
        this.type_new = type_new;
    }

    public String getThumb_img() {
        return thumb_img;
    }

    public void setThumb_img(String thumb_img) {
        this.thumb_img = thumb_img;
    }

    public int getPpp() {
        return ppp;
    }

    public void setPpp(int ppp) {
        this.ppp = ppp;
    }

    public int getSplay_count() {
        return splay_count;
    }

    public void setSplay_count(int splay_count) {
        this.splay_count = splay_count;
    }

    public int getPlay_count() {
        return play_count;
    }

    public void setPlay_count(int play_count) {
        this.play_count = play_count;
    }

    public int getSmall_num() {
        return small_num;
    }

    public void setSmall_num(int small_num) {
        this.small_num = small_num;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getStart_time() {
        return start_time;
    }

    public void setStart_time(String start_time) {
        this.start_time = start_time;
    }

    public String getEnd_time() {
        return end_time;
    }

    public void setEnd_time(String end_time) {
        this.end_time = end_time;
    }

    public String getBm_start_time() {
        return bm_start_time;
    }

    public void setBm_start_time(String bm_start_time) {
        this.bm_start_time = bm_start_time;
    }

    public String getBm_end_time() {
        return bm_end_time;
    }

    public void setBm_end_time(String bm_end_time) {
        this.bm_end_time = bm_end_time;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public int getDay_num() {
        return day_num;
    }

    public void setDay_num(int day_num) {
        this.day_num = day_num;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    public int getKa_num() {
        return ka_num;
    }

    public void setKa_num(int ka_num) {
        this.ka_num = ka_num;
    }

    public String getCan_created_at() {
        return can_created_at;
    }

    public void setCan_created_at(String can_created_at) {
        this.can_created_at = can_created_at;
    }

    public int getBuy_count() {
        return buy_count;
    }

    public void setBuy_count(int buy_count) {
        this.buy_count = buy_count;
    }

    public int getCourse_num() {
        return course_num;
    }

    public void setCourse_num(int course_num) {
        this.course_num = course_num;
    }

    public String getListen_users_count() {
        return listen_users_count;
    }

    public void setListen_users_count(String listen_users_count) {
        this.listen_users_count = listen_users_count;
    }

    public String getImg_new() {
        return img_new;
    }

    public void setImg_new(String img_new) {
        this.img_new = img_new;
    }

    public String getTex() {
        return tex;
    }

    public void setTex(String tex) {
        this.tex = tex;
    }

    public int getSequence() {
        return sequence;
    }

    public void setSequence(int sequence) {
        this.sequence = sequence;
    }

    public int getBaoming() {
        return baoming;
    }

    public void setBaoming(int baoming) {
        this.baoming = baoming;
    }

    public String getType_course() {
        return type_course;
    }

    public void setType_course(String type_course) {
        this.type_course = type_course;
    }

    public String getBody_img_new() {
        return body_img_new;
    }

    public void setBody_img_new(String body_img_new) {
        this.body_img_new = body_img_new;
    }

    @Generated(hash = 846249299)
    public ChildEntity(Long objectId, Long id, String title, String uname, String avatar, String avatars, String type_description, String url, String img, int status, String bgavatar, int tcount, String count, String scount, String type, String subtitle, String subtitle_new, String title_img_new, String type_new, String thumb_img, int ppp, int splay_count, int play_count, int small_num, String content, String start_time, String end_time, String bm_start_time, String bm_end_time, int num, int day_num, String created_at, String updated_at, int ka_num, String can_created_at, int buy_count, int course_num, String listen_users_count, String img_new, String tex, int sequence, int baoming, String type_course, String body_img_new) {
        this.objectId = objectId;
        this.id = id;
        this.title = title;
        this.uname = uname;
        this.avatar = avatar;
        this.avatars = avatars;
        this.type_description = type_description;
        this.url = url;
        this.img = img;
        this.status = status;
        this.bgavatar = bgavatar;
        this.tcount = tcount;
        this.count = count;
        this.scount = scount;
        this.type = type;
        this.subtitle = subtitle;
        this.subtitle_new = subtitle_new;
        this.title_img_new = title_img_new;
        this.type_new = type_new;
        this.thumb_img = thumb_img;
        this.ppp = ppp;
        this.splay_count = splay_count;
        this.play_count = play_count;
        this.small_num = small_num;
        this.content = content;
        this.start_time = start_time;
        this.end_time = end_time;
        this.bm_start_time = bm_start_time;
        this.bm_end_time = bm_end_time;
        this.num = num;
        this.day_num = day_num;
        this.created_at = created_at;
        this.updated_at = updated_at;
        this.ka_num = ka_num;
        this.can_created_at = can_created_at;
        this.buy_count = buy_count;
        this.course_num = course_num;
        this.listen_users_count = listen_users_count;
        this.img_new = img_new;
        this.tex = tex;
        this.sequence = sequence;
        this.baoming = baoming;
        this.type_course = type_course;
        this.body_img_new = body_img_new;
    }

    @Generated(hash = 1879970608)
    public ChildEntity() {
    }

    @Override
    public String toString() {
        return "ChildEntity{" +
                "objectId=" + objectId +
                ", id=" + id +
                ", title='" + title + '\'' +
                ", uname='" + uname + '\'' +
                ", avatar='" + avatar + '\'' +
                ", avatars='" + avatars + '\'' +
                ", type_description='" + type_description + '\'' +
                ", url='" + url + '\'' +
                ", img='" + img + '\'' +
                ", status=" + status +
                ", bgavatar='" + bgavatar + '\'' +
                ", tcount=" + tcount +
                ", count='" + count + '\'' +
                ", scount='" + scount + '\'' +
                ", type='" + type + '\'' +
                ", subtitle='" + subtitle + '\'' +
                ", subtitle_new='" + subtitle_new + '\'' +
                ", title_img_new='" + title_img_new + '\'' +
                ", type_new='" + type_new + '\'' +
                ", thumb_img='" + thumb_img + '\'' +
                ", ppp=" + ppp +
                ", splay_count=" + splay_count +
                ", play_count=" + play_count +
                ", small_num=" + small_num +
                ", content='" + content + '\'' +
                ", start_time='" + start_time + '\'' +
                ", end_time='" + end_time + '\'' +
                ", bm_start_time='" + bm_start_time + '\'' +
                ", bm_end_time='" + bm_end_time + '\'' +
                ", num=" + num +
                ", day_num=" + day_num +
                ", created_at='" + created_at + '\'' +
                ", updated_at='" + updated_at + '\'' +
                ", ka_num=" + ka_num +
                ", can_created_at='" + can_created_at + '\'' +
                ", buy_count=" + buy_count +
                ", course_num=" + course_num +
                ", listen_users_count='" + listen_users_count + '\'' +
                ", img_new='" + img_new + '\'' +
                ", tex='" + tex + '\'' +
                ", sequence=" + sequence +
                ", baoming=" + baoming +
                ", type_course='" + type_course + '\'' +
                ", body_img_new='" + body_img_new + '\'' +
                '}';
    }
}
