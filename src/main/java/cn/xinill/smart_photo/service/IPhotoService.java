package cn.xinill.smart_photo.service;

import cn.xinill.smart_photo.pojo.Photo;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;

public interface IPhotoService {
    /**
     * 上传图片
     * @param uid
     * @param date
     * @param msg
     * @param name
     * @return
     */
    boolean upload(int uid, Date date, String msg, String name);

    /**
     * 获取用户的图片
     * @param uid
     * @param page
     * @param pgaenum
     * @return
     */
    List<Photo> selectPhotoByUserList(int uid, int page, int pgaenum);

    /**
     * 统计用户照片数量
     * @param uid
     * @return
     */
    int countPhotoByUser(int uid);
}
