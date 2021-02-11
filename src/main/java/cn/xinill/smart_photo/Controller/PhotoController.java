package cn.xinill.smart_photo.Controller;

import cn.xinill.smart_photo.common.ServerResponse;
import cn.xinill.smart_photo.pojo.Photo;
import cn.xinill.smart_photo.pojo.Re;
import cn.xinill.smart_photo.pojo.RePhoto;
import cn.xinill.smart_photo.service.IPhotoService;
import cn.xinill.smart_photo.utils.ImgException;
import cn.xinill.smart_photo.utils.OSSClientUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping(value = "/img")
public class PhotoController {

    OSSClientUtil ossClientUtil = new OSSClientUtil();
    private IPhotoService photoService;

    @Autowired
    public void setPhotoService(IPhotoService photoService) {
        this.photoService = photoService;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/upload")
    @ResponseBody
    public ServerResponse<Boolean> upload(@RequestParam String time,
                                          @RequestParam String text,
                                          @RequestParam(required = false) MultipartFile img,
                                          HttpServletRequest request){
        try {
            //处理日期
            Date date;
            DateFormat simpleDateFormat;
            if(time.length() == 19){
                simpleDateFormat= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            }else{
                System.err.println("日期格式不正确");
                return ServerResponse.createByErrorCodeMsgData(2,"日期格式不正确", false);
            }
            try {
                date = simpleDateFormat.parse(time);   //格式化后的时间
            } catch (ParseException e) {
                System.err.println("日期无法格式化");
                return ServerResponse.createByErrorMsgData("日期无法格式化", false);
            }

            if(text.length() > 255){
                System.err.println("text信息不得超过255");
                return ServerResponse.createByErrorMsgData("text信息不得超过255", false);
            }

            StringBuilder sb = new StringBuilder();
//            if (img != null) {
//                for(int i=0; i<img.size(); ++i){
//                    sb.append(ossClientUtil.uploadImg2Oss(img.get(i))).append(";");
//                }
//            }
            sb.append(ossClientUtil.uploadImg2Oss(img)).append(";");


            boolean re = photoService.upload((Integer)request.getAttribute("uid"),
                    date, text, sb.toString());
            return ServerResponse.createBySuccessData(re);
        } catch (ImgException e) {
            return ServerResponse.createByErrorMsgData(e.getMessage(), false);
        }
    }

    @ResponseBody
    @RequestMapping(method = RequestMethod.POST, value = "/user")
    public ServerResponse<Re> getAllPhotoByUser(@RequestParam int page,
                                                @RequestParam(value = "pagenum") int sum,
                                                HttpServletRequest request){
        if(page<0 || sum<0){
            return ServerResponse.createByErrorCodeMsg(2,"参数不合法");
        }
        int uid = (Integer)request.getAttribute("uid");
        try {
            Re re = new Re();
            List<Photo> photos = photoService.selectPhotoByUserList(
                    uid, page, sum);
            List<RePhoto> list = new ArrayList<>();
            for(Photo p: photos){
                RePhoto rp = new RePhoto();
                rp.setTime(p.getTime());
                rp.setText(p.getText());
                String url = p.getImg_url();
                String[] urls;
                if(url == null || url.length() == 0){
                    urls = new  String[0];
                }else{
                     urls = url.split(";");
                    for(int i=0; i<urls.length; ++i){
                        urls[i] = "https://xinil.oss-cn-shanghai.aliyuncs.com/smart_photo/"+urls[i];
                    }
                }
                rp.setImg_url(urls);
                list.add(rp);
            }
            re.setPage_data(list);
            re.setSum(photoService.countPhotoByUser(uid));
            return ServerResponse.createBySuccessData(re);
        } catch (Exception e) {
            e.printStackTrace();
            return ServerResponse.createByErrorMsg("服务器未知异常，请赶快通知任!!!");
        }
    }
}
