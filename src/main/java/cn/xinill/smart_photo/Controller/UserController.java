package cn.xinill.smart_photo.Controller;

import cn.xinill.smart_photo.common.ServerResponse;
import cn.xinill.smart_photo.mapper.ICodeMapper;
import cn.xinill.smart_photo.pojo.LoginResponse;
import cn.xinill.smart_photo.pojo.User;
import cn.xinill.smart_photo.service.ITokenService;
import cn.xinill.smart_photo.service.IUserService;
import cn.xinill.smart_photo.utils.ImgException;
import cn.xinill.smart_photo.utils.OSSClientUtil;
import cn.xinill.smart_photo.utils.SMSUtils;

import com.sun.org.apache.xpath.internal.operations.Bool;
import com.tencentcloudapi.sms.v20190711.models.SendSmsResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Controller
@RequestMapping(value = "/user", produces="application/json;charset=UTF-8")
public class UserController {

    private OSSClientUtil ossClientUtil = new OSSClientUtil();
    private IUserService userService;
    private ITokenService tokenService;
    private ICodeMapper codeMapper;

    @Autowired(required = false)
    public void setCodeMapper(ICodeMapper codeMapper) {
        this.codeMapper = codeMapper;
    }

    @Autowired
    public void setUserService(IUserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setTokenService(ITokenService tokenService) {
        this.tokenService = tokenService;
    }

    @ResponseBody
    @RequestMapping(method = RequestMethod.POST , value = "/test")
    public ServerResponse<String> test(String msg){
        return ServerResponse.createBySuccessData(msg);
    }

    /**
     * 用户手机号密码登陆
     */
    @ResponseBody
    @RequestMapping(method = RequestMethod.POST , value = "/login/password")
    public ServerResponse<Boolean> logIn(@RequestParam String phone,
                                         @RequestParam String password,
                                         HttpServletResponse response){
        try {
            int uid = userService.logIn(phone, password);
            if(uid == -1){
                return ServerResponse.createBySuccessMsgData("密码错误", false);
            }else{
                response.addHeader("token", tokenService.creatUserToken(uid));
                return ServerResponse.createBySuccessMsgData("密码正确", true);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ServerResponse.createByErrorMsgData("服务器异常，请赶快通知任!!!", false);
        }
    }

    /**
     * 用户登陆-发送验证码
     */
    @ResponseBody
    @RequestMapping(method = RequestMethod.POST, value = "/login/getCode")
    public ServerResponse<Boolean> logInGetCode(@RequestParam String phone) {
        //手机号合法性验证
        if(phone == null || phone.length() != 11){
            return ServerResponse.createByErrorCodeMsgData(2, "手机号不合法", false);
        }

        //发送验证码
        try {
            String code = SMSUtils.sendLOG_IN(phone);
            codeMapper.addCode(phone, code);
            return ServerResponse.createBySuccessMsgData("验证码已发送", true);
        }catch (Exception e) {
            e.printStackTrace();
            return ServerResponse.createByErrorMsgData("服务器异常，请赶快通知任!!!", false);
        }
    }

    /**
     * 用户登陆-验证验证码
     */
    @ResponseBody
    @RequestMapping(method = RequestMethod.POST, value = "/login/judgeCode")
    public ServerResponse<LoginResponse> logInJudgeCode(@RequestParam String phone,
                                                        @RequestParam String code,
                                                        HttpServletResponse response) {
        //手机号合法性验证
        if(phone == null || phone.length() != 11){
            return ServerResponse.createByErrorCodeMsgData(2, "手机号不合法", new LoginResponse(false,false));
        }

        //验证登陆
        try {
            if(code.equals(codeMapper.getCode(phone))) { //验证码验证成功
                boolean exist = userService.phoneExist(phone);
                if (!exist){
                    userService.logOn(phone);//手机号注册
                }

                int uid = userService.logIn(phone);
                response.addHeader("token", tokenService.creatUserToken(uid));//获取uid
                return ServerResponse.createBySuccessData(new LoginResponse(true, exist));
            } else {
                return ServerResponse.createByErrorMsgData("验证码不正确", new LoginResponse(false,false));
            }
        }catch (Exception e) {
            e.printStackTrace();
            return ServerResponse.createByErrorMsgData("服务器异常，请赶快通知任!!!", new LoginResponse(false,false));
        }
    }



    /**
     * 找回密码-发送验证码
     */
    @ResponseBody
    @RequestMapping(method = RequestMethod.POST , value = "/updatepassword/getCode")
    public ServerResponse<Boolean> findPassword(@RequestParam String phone){
        //手机号合法性验证
        if(phone == null || phone.length() != 11){
            return ServerResponse.createByErrorCodeMsgData(2, "手机号不合法", false);
        }

        //发送验证码
        try {
            String code = SMSUtils.sendUPDATE(phone);
            codeMapper.addCode(phone, code);
            return ServerResponse.createBySuccessMsgData("验证码已发送", true);
        }catch (Exception e) {
            e.printStackTrace();
            return ServerResponse.createByErrorMsgData("服务器异常，请赶快通知任!!!", false);
        }
    }

    /**
     * 找回密码-验证验证码，未注册进行注册，修改密码
     */
    @ResponseBody
    @RequestMapping(method = RequestMethod.POST , value = "/updatepassword/judgeCode")
    public ServerResponse<Boolean> findPassword(@RequestParam String phone,
                                                @RequestParam String code,
                                                @RequestParam String password){
        try {
            //手机号合法性验证
            if(phone == null || phone.length() != 11){
                return ServerResponse.createByErrorCodeMsgData(2, "手机号不合法", false);
            }

            //处理密码长度
            if(password.length() < 6){
                return ServerResponse.createByErrorMsgData("用户密码长度不得少于6", false);
            }else if(password.length() >20){
                return ServerResponse.createByErrorMsgData("用户密码长度不得大于20", false);
            }

            //验证验证码
            if(code.equals(codeMapper.getCode(phone))) { //验证码验证成功
                //判断是否需要注册
                if (!userService.phoneExist(phone)){
                    userService.logOn(phone);//手机号注册
                }

                //获取用户 uid
                int uid = userService.logIn(phone);
                System.out.println("用户"+uid+"修改密码为"+password);

                //修改密码
                if(userService.updateUserPwd(uid, password)){
                    return ServerResponse.createBySuccessMsgData("密码修改成功", true);
                }
                System.err.println("用户"+uid+"修改密码失败");
                return ServerResponse.createByErrorMsgData("服务器异常，请赶快通知任!!!", false);
            } else {
                return ServerResponse.createByErrorMsgData("验证码不正确", false);
            }
        }catch (Exception e) {
            e.printStackTrace();
            return ServerResponse.createByErrorMsgData("服务器异常，请赶快通知任!!!", false);
        }
    }


        /**
         * 重要信息修改-发送验证码
         */
    @ResponseBody
    @RequestMapping(method = RequestMethod.POST, value = "/inform/importantChange/getCode")
    public ServerResponse<Boolean> importantInformGetCode(ServletRequest request) {
        try {
            //处理token验证
            int uid = (Integer)request.getAttribute("uid");
            if(uid == -1){
                return ServerResponse.createByErrorCodeMsgData(10, "需要token登陆", false);
            }

            //查询用户信息
            User user = userService.findUserByUid(uid);
            //发送验证码
            String code = SMSUtils.sendUPDATE(user.getPhone());
            codeMapper.addCode(user.getPhone(), code);
            return ServerResponse.createBySuccessMsgData("验证码已发送", true);
        }catch (Exception e) {
            e.printStackTrace();
            return ServerResponse.createByErrorMsgData("服务器异常，请赶快通知任!!!", false);
        }
    }

    /**
     * 重要信息修改-检验验证码
     */
    @ResponseBody
    @RequestMapping(method = RequestMethod.POST, value = "/inform/importantChange/judgeCode")
    public ServerResponse<Boolean> importantInformJudgeCode(@RequestParam String code,
                                                            ServletRequest request) {
        try {
            //处理token验证
            int uid = (Integer)request.getAttribute("uid");
            if(uid == -1){
                return ServerResponse.createByErrorCodeMsgData(10, "需要token登陆", false);
            }

            //查询用户信息
            User user = userService.findUserByUid(uid);
            if(code.equals(codeMapper.getCode(user.getPhone()))){ //验证码验证成功
                return ServerResponse.createBySuccessData(true);
            }else{
                return ServerResponse.createByErrorMsgData("验证码不正确", false);
            }
        }catch (Exception e) {
            e.printStackTrace();
            return ServerResponse.createByErrorMsgData("服务器异常，请赶快通知任!!!", false);
        }
    }

    /**
     * 修改用户密码
     */
    @ResponseBody
    @RequestMapping(method = RequestMethod.POST, value = "/inform/updatepwd")
    public ServerResponse<Boolean> updatePwd(@RequestParam String password, ServletRequest request) {
        try {
            //处理token验证
            int uid = (Integer)request.getAttribute("uid");
            if(uid == -1){
                return ServerResponse.createByErrorCodeMsgData(10, "需要token登陆", false);
            }

            //处理密码长度
            if(password.length() < 6){
                return ServerResponse.createByErrorMsgData("用户密码长度不得少于6", false);
            }else if(password.length() >20){
                return ServerResponse.createByErrorMsgData("用户密码长度不得大于20", false);
            }

            //修改密码
            System.out.println("用户"+uid+"修改密码为"+password);
            if(userService.updateUserPwd(uid, password)){
                return ServerResponse.createBySuccessMsgData("密码修改成功", true);
            }
            System.err.println("用户"+uid+"修改密码失败");
            return ServerResponse.createByErrorMsgData("服务器异常，请赶快通知任!!!", false);
        } catch (Exception e) {
            e.printStackTrace();
            return ServerResponse.createByErrorMsgData("服务器异常，请赶快通知任!!!", false);
        }
   }

    /**
     * 查看用户信息
     */
    @ResponseBody
    @RequestMapping(method = RequestMethod.POST, value = "/inform/get")
    public ServerResponse<User> getUserMessage(ServletRequest request){
        try {
            //处理token验证
            int uid = (Integer)request.getAttribute("uid");
            if(uid == -1){
                return ServerResponse.createByErrorCodeMsgData(10, "需要token登陆", null);
            }

            //查询用户信息
            User user = userService.findUserByUid(uid);
            if(user!=null){
                user.setPassword(null);
                user.setUid(null);
                user.setPortrait("https://xinil.oss-cn-shanghai.aliyuncs.com/smart_photo/"+user.getPortrait());
                return ServerResponse.createBySuccessData(user);
            }else{
                return ServerResponse.createByErrorMsg("无该用户信息");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ServerResponse.createByErrorMsg("服务器异常，请赶快通知任!!!");
        }
    }


    /**
     * 修改用户信息
     */
    @ResponseBody
    @RequestMapping(method = RequestMethod.POST, value = "/inform/update")
    public ServerResponse<Boolean> updateInform(ServletRequest request,
                                                String username,
                                                MultipartFile portrait,
                                                String gender,
                                                String birthday,
                                                Integer age,
                                                String introduce) {
        //处理token验证
        int uid = (Integer)request.getAttribute("uid");
        if(uid == -1){
            return ServerResponse.createByErrorCodeMsgData(10, "需要token登陆", false);
        }

        //处理昵称
        if (username != null) {
            if(username.length() < 1){
                System.err.println("用户"+uid+":昵称过短");
                return ServerResponse.createByErrorCodeMsgData(2,"昵称过短", false);
            }else if(username.length() > 20){
                System.err.println("用户"+uid+":昵称长度不能超过20字符");
                return ServerResponse.createByErrorCodeMsgData(2,"昵称长度不能超过20字符", false);
            }
        }

        //处理头像
        String url = null;
        if (portrait != null) {
            url = null;
            try {
                url =  ossClientUtil.uploadImg2Oss(portrait);
            } catch (ImgException e) {
                e.printStackTrace();
                return ServerResponse.createByErrorMsgData("无法更新头像", false);
            }
        }

        //处理性别
        if (gender != null) {
            if(gender.length() > 1){
                System.err.println("用户"+uid+":性别格式不正确");
                return ServerResponse.createByErrorCodeMsgData(2,"性别格式不对", false);
            }
        }

        //处理日期
        Date date = null;
        if (birthday != null) {
            DateFormat simpleDateFormat;
            String[] b = birthday.split("-");
            StringBuilder birth = new StringBuilder();
            if(b.length == 3 && b[0].length() == 4 && (b[1].length() == 1 || b[1].length() == 2) && (b[2].length() == 1 || b[2].length() == 2)){
                birth.append(b[0]);
                birth.append("-");
                if(b[1].length() == 1){
                    birth.append("0");
                }
                birth.append(b[1]);
                birth.append("-");
                if(b[2].length() == 1){
                    birth.append("0");
                }
                birth.append(b[2]);
                birth.append(" 09"); //+ 9小时。解决java.Date 与 MySQL时区不一致的问题
                simpleDateFormat= new SimpleDateFormat("yyyy-MM-dd HH");
            }else{
                System.err.println("用户"+uid+"日期格式不正确");
                return ServerResponse.createByErrorCodeMsgData(2,"日期格式不正确", false);
            }

            try {
                date = simpleDateFormat.parse(birth.toString());   //格式化后的时间
            } catch (ParseException e) {
                System.err.println("用户"+uid+"日期无法格式化");
                return ServerResponse.createByErrorMsgData("日期无法格式化", false);
            }
            System.out.println(date.toString());
        }
        //处理个人简介
        if(introduce != null) {
            System.out.println("简介："+introduce);
            if(introduce.length() > 255){
                System.err.println("用户"+uid+"个人简介信息超长");
                return ServerResponse.createByErrorMsgData("个人简介信息最长为255字符", false);
            }
        }


        try{
            System.out.println("用户"+uid+": 信息处理完毕");
            if(userService.updateUserInform(uid, username, url, gender, date, age, introduce)){
                return ServerResponse.createBySuccessData(true);
            }
            return ServerResponse.createByErrorMsg("修改信息失败");
        }catch (Exception e){
            e.printStackTrace();
            return ServerResponse.createByErrorMsg("服务器异常，请赶快通知任!!!");
        }
    }
}
