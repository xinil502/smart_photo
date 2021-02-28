package cn.xinill.smart_photo.pojo;

/**
 * @Author: Xinil
 * @Date: 2021/2/28 14:22
 */
public class LoginResponse {
    private boolean data;
    private boolean exist;

    public LoginResponse() {
    }

    public LoginResponse(boolean data, boolean exist) {
        this.data = data;
        this.exist = exist;
    }

    public boolean isData() {
        return data;
    }

    public void setData(boolean data) {
        this.data = data;
    }

    public boolean isExist() {
        return exist;
    }

    public void setExist(boolean exist) {
        this.exist = exist;
    }
}
