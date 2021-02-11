package cn.xinill.smart_photo.mapper;

import cn.xinill.smart_photo.pojo.Photo;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;

@Mapper
public interface IPhotoMapper {

    int insertPhoto(Photo photo);

    List<Photo> selectPhotoByUserList(int uid, int start, int length);

    int countPhotoByUser(int uid);
}
