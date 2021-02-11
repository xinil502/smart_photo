package cn.xinill.smart_photo.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class Photo {
    private Integer pid;
    private Integer uid;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date time;
    private String text;
    private String img_url;

    public Photo() {
    }

    public Photo(Integer pid, Integer uid, Date time, String text, String img_url) {
        this.pid = pid;
        this.uid = uid;
        this.time = time;
        this.text = text;
        this.img_url = img_url;
    }

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getImg_url() {
        return img_url;
    }

    public void setImg_url(String img_url) {
        this.img_url = img_url;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "Photo{" +
                "pid=" + pid +
                ", uid=" + uid +
                ", time=" + time +
                ", text='" + text + '\'' +
                ", img_url='" + img_url + '\'' +
                '}';
    }
}
