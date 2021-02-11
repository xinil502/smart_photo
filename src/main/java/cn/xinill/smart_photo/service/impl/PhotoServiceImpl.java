package cn.xinill.smart_photo.service.impl;

import cn.xinill.smart_photo.mapper.IPhotoMapper;
import cn.xinill.smart_photo.pojo.Photo;
import cn.xinill.smart_photo.service.IPhotoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

@Service
public class PhotoServiceImpl implements IPhotoService {

    @Autowired(required = false)
    IPhotoMapper photoMapper;

    @Override
    public boolean upload(int uid, Date date, String text, String url) {
        Photo photo = new Photo();
        photo.setUid(uid);
        photo.setTime(date);
        photo.setText(text);
        photo.setImg_url(url);
        return 1 == photoMapper.insertPhoto(photo);
    }

    @Override
    public List<Photo> selectPhotoByUserList(int uid, int page, int pgaenum) {
        int start = (page-1)*pgaenum;
        int length = pgaenum;
        return photoMapper.selectPhotoByUserList(uid, start, length);
    }

    public int countPhotoByUser(int uid){
        return photoMapper.countPhotoByUser(uid);
    }
}
