package cn.xinill.smart_photo.service.impl;

import cn.xinill.smart_photo.mapper.IUserMapper;
import cn.xinill.smart_photo.pojo.User;
import cn.xinill.smart_photo.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class UserServiceImpl implements IUserService {

    private IUserMapper userMapper;

    @Autowired(required = false)
    public void setUserMapper(IUserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Override
    public boolean phoneExist(String phone) {
        if(userMapper.findUserByPhone(phone)!=null){ //手机号已经被注册了
            return true;
        }
        return false;
    }

    @Override
    public boolean logOn(String phone) {
        try {
            User user = new User();
            user.setPhone(phone);
            return 1 == userMapper.insertUser(user);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public int logIn(String phone) {
        User findUser = userMapper.findUserByPhone(phone);
        if(findUser!=null){
            return findUser.getUid();
        }
        return -1;
    }

    @Override
    public int logIn(String phone, String passpword) {
        User findUser = userMapper.findUserByPhone(phone);
        if(findUser!=null && findUser.getPassword().equals(passpword)){
            return findUser.getUid();
        }
        return -1;
    }

    @Override
    public User findUserByUid(int id) {
        return userMapper.findUserByUid(id);
    }

    @Override
    public boolean updateUserInform(int uid, String username, String portrait, String gender, Date birth, Integer age, String introduce) {
        User user = new User();
        user.setUid(uid);
        user.setUsername(username);
        user.setPortrait(portrait);
        user.setGender(gender);
        user.setBirth(birth);
        user.setAge(age);
        user.setIntroduce(introduce);
        return 1 == userMapper.updateUserInform(user);
    }
    @Override
    public boolean updateUserPwd(int uid, String password) {
        User user = new User();
        user.setUid(uid);
        user.setPassword(password);
        return 1 == userMapper.updateUserPwd(user);
    }
}
