package cn.xinill.smart_photo.pojo;

import java.util.List;

/**
 * @Author: Xinil
 * @Date: 2021/1/31 21:24
 */
public class Re {
    private int sum;
    private List<RePhoto> page_data;

    public Re() {
    }

    public Re(int sum, List<RePhoto> page_data) {
        this.sum = sum;
        this.page_data = page_data;
    }

    public int getSum() {
        return sum;
    }

    public void setSum(int sum) {
        this.sum = sum;
    }

    public List<RePhoto> getPage_data() {
        return page_data;
    }

    public void setPage_data(List<RePhoto> page_data) {
        this.page_data = page_data;
    }

    @Override
    public String toString() {
        return "Re{" +
                "sum=" + sum +
                ", page_data=" + page_data +
                '}';
    }
}
