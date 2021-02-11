package cn.xinill.smart_photo.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class User {
    private Integer uid;
    private String username;
    private String password;
    private String portrait;
    private String phone;
    private Integer age;
    private String gender;
    private String email;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date birth;
    private String introduce;

    public User() {
    }

    public User(Integer uid, String username, String password, String portrait, Integer age, String gender, String phone, String email, Date birth, String introduce) {
        this.uid = uid;
        this.username = username;
        this.password = password;
        this.portrait = portrait;
        this.age = age;
        this.gender = gender;
        this.phone = phone;
        this.email = email;
        this.birth = birth;
        this.introduce = introduce;
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPortrait() {
        return portrait;
    }

    public void setPortrait(String portrait) {
        this.portrait = portrait;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getBirth() {
        return birth;
    }

    public void setBirth(Date birth) {
        this.birth = birth;
    }

    public String getIntroduce() {
        return introduce;
    }

    public void setIntroduce(String introduce) {
        this.introduce = introduce;
    }

    @Override
    public String toString() {
        return "User{" +
                "uid=" + uid +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", portrait='" + portrait + '\'' +
                ", age=" + age +
                ", gender='" + gender + '\'' +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                ", birth=" + birth +
                ", introduce='" + introduce + '\'' +
                '}';
    }
}
