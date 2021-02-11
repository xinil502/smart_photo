package cn.xinill.smart_photo.service;

import cn.xinill.smart_photo.pojo.User;
import java.util.Date;

public interface IUserService {
    /**
     * 验证用户手机号是否已经被注册
     * @param phone
     */
    boolean phoneExist(String phone);

    /**
     * 用户注册
     * @param phone
     * @return
     */
    boolean logOn(String phone);


    /**
     * 手机号登陆
     * @param phone
     * @return
     */
    int logIn(String phone);

    /**
     * 账号密码登陆
     * @param phone
     * @param passpword
     * @return
     */
    int logIn(String phone, String passpword);

    /**
     * 根据 uid查找用户
     * @param id
     * @return
     */
    User findUserByUid(int id);

    /**
     * 修改用户信息
     * @return
     */
    boolean updateUserInform(int uid, String username, String portrait, String gender, Date birth, Integer age, String introduce);

    /**
     * 修改用户密码
     * @param password
     * @return
     */
    boolean updateUserPwd(int uid, String password);
}
