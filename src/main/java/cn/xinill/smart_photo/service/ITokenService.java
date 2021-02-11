package cn.xinill.smart_photo.service;

public interface ITokenService{
    /**
     * 由id生成token
     * @param id
     * @return
     */
    String creatUserToken(int id);

    /**
     * 由token转为id
     * @param token
     * @return
     */
    int verifyUserToken(String token);
}
