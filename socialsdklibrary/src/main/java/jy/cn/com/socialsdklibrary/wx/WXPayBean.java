package jy.cn.com.socialsdklibrary.wx;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Administrator
 * created at 2016/2/25 16:11
 * TODO:微信支付实体类
 */
public class WXPayBean {

    private Long money;//支付金额（单位分）
    private String nonceStr;//随机字符串
    private String sign;//签名
    private String partnerId;//商户号
    private String prepayId;//预支付交易会话标识
    private String packageValue;//固定为“Sign=wxpay”
    private String timeStamp;//时间戳
    private String orderId;//服务器订单号
    private String receiverUserId;//替代被人充值时用:金币实际接受者userId


    public Long getMoney() {
        return money;
    }

    public void setNonceStr(String nonceStr) {
        this.nonceStr = nonceStr;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public void setPartnerId(String partnerId) {
        this.partnerId = partnerId;
    }

    public void setPrepayId(String prepayId) {
        this.prepayId = prepayId;
    }

    public void setPackageValue(String packageValue) {
        this.packageValue = packageValue;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getNonceStr() {
        return nonceStr;
    }

    public String getSign() {
        return sign;
    }

    public String getPartnerId() {
        return partnerId;
    }

    public String getPrepayId() {
        return prepayId;
    }

    public String getPackageValue() {
        return packageValue;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setMoney(Long money) {
        this.money = money;
    }


    public String getOrderId() {
        return orderId;
    }

    public String getReceiverUserId() {
        return receiverUserId;
    }

    public void setReceiverUserId(String receiverUserId) {
        this.receiverUserId = receiverUserId;
    }

    public JSONObject buildJson() {
        JSONObject json = null;
        try {
            json = new JSONObject();

            json.put("money", money);
            json.put("nonceStr", nonceStr);
            json.put("sign", sign);
            json.put("partnerId", partnerId);
            json.put("prepayId", prepayId);
            json.put("packageValue", packageValue);
            json.put("timeStamp", timeStamp);
            json.put("orderId", orderId);
            if (null != receiverUserId && !"".equals(receiverUserId.trim())) {
                json.put("receiverUserId", receiverUserId);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return json;
    }

    public static WXPayBean parse(String jsonString) {
        try {
            JSONObject jsonObject = new JSONObject(jsonString);
            return WXPayBean.parse(jsonObject);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static WXPayBean parse(JSONObject jsonObject) {
        if (null == jsonObject) {
            return null;
        }
        WXPayBean wxPayBean = new WXPayBean();


        wxPayBean.money = jsonObject.optLong("money");
        wxPayBean.nonceStr = jsonObject.optString("nonceStr");
        wxPayBean.sign = jsonObject.optString("sign");
        wxPayBean.partnerId = jsonObject.optString("partnerId");
        wxPayBean.prepayId = jsonObject.optString("prepayId");
        wxPayBean.packageValue = jsonObject.optString("packageValue");
        wxPayBean.timeStamp = jsonObject.optString("timeStamp");
        wxPayBean.orderId = jsonObject.optString("orderId");
        wxPayBean.receiverUserId = jsonObject.optString("receiverUserId");

        return wxPayBean;
    }


    public static String getShortName() {
        return WXPayBean.class.getSimpleName().toLowerCase();
    }


}
