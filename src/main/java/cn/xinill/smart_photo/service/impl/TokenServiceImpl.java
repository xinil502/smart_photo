package cn.xinill.smart_photo.service.impl;

import cn.xinill.smart_photo.service.ITokenService;
import cn.xinill.smart_photo.utils.JWTTokenUtil;

import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.Claim;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class TokenServiceImpl implements ITokenService {
    @Override
    public String creatUserToken(int id) {
        return JWTTokenUtil.createToken(id);
    }

    @Override
    public int verifyUserToken(String token) {
        try {
            Map<String, Claim> map = JWTTokenUtil.verifyToken(token);
            if(map == null){
                return 0;
            }
            return map.get("userId").asInt();
        } catch (TokenExpiredException e) {
            System.err.println("登录过期");
            return -1;
        } catch (Exception e) {
            e.printStackTrace();
            return -10;
        }
    }
}
