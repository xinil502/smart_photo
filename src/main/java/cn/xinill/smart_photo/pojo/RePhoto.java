package cn.xinill.smart_photo.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Arrays;
import java.util.Date;

/**
 * @Author: Xinil
 * @Date: 2021/1/31 21:21
 */
public class RePhoto {
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date time;
    private String text;
    private String[] img_url;

    public RePhoto() {
    }

    public RePhoto(Date time, String text, String[] img_url) {
        this.time = time;
        this.text = text;
        this.img_url = img_url;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String[] getImg_url() {
        return img_url;
    }

    public void setImg_url(String[] img_url) {
        this.img_url = img_url;
    }

    @Override
    public String toString() {
        return "RePhoto{" +
                "time=" + time +
                ", text='" + text + '\'' +
                ", img_url=" + Arrays.toString(img_url) +
                '}';
    }
}
