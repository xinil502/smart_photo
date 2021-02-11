package cn.xinill.smart_photo.mapper;

import cn.xinill.smart_photo.pojo.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface IUserMapper {

    int insertUser(User user);

    User findUserByPhone(String username);

    User findUserByUid(int uid);

    int updateUserInform(User user);

    int updateUserPwd(User user);
}
