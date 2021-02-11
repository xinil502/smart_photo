package cn.xinill.smart_photo.mapper;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @Author: Xinil
 * @Date: 2021/2/10 13:26
 */
@Mapper
public interface ICodeMapper {
    int addCode(String phone, String code);

    String getCode(String phone);
}
