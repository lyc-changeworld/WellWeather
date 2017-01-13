package com.example.achuan.wellweather.gson;

import com.google.gson.annotations.SerializedName;

/**
 * Created by achuan on 17-1-11.
 * 功能：GSON数据包中Suggestion部分的数据模型的实体类
 * suggestion: {
 air: {
 brf: "中",
 txt: "气象条件对空气污染物稀释、扩散和清除无明显影响，易感人群应适当减少室外活动时间。"
 },
                   comf: {
 brf: "较舒适",
                  txt: "白天会有降雨，这种天气条件下，人们会感到有些凉意，但大部分人完全可以接受。"
 },
                   cw: {
 brf: "不宜",
                  txt: "不宜洗车，未来24小时内有雨，如果在此期间洗车，雨水和路上的泥水可能会再次弄脏您的爱车。"
 },
 drsg: {
 brf: "较冷",
 txt: "建议着厚外套加毛衣等服装。年老体弱者宜着大衣、呢外套加羊毛衫。"
 },
 flu: {
 brf: "易发",
 txt: "天冷空气湿度大，易发生感冒，请注意适当增加衣服，加强自我防护避免感冒。"
 },
                  sport: {
 brf: "较不宜",
                  txt: "有降水，推荐您在室内进行各种健身休闲运动，若坚持户外运动，须注意保暖并携带雨具。"
 },
 trav: {
 brf: "适宜",
 txt: "稍凉，但是有较弱降水和微风作伴，会给您的旅行带来意想不到的景象，适宜旅游，可不要错过机会呦！"
 },
 uv: {
 brf: "最弱",
 txt: "属弱紫外线辐射天气，无需特别防护。若长期在户外，建议涂擦SPF在8-12之间的防晒护肤品。"
 }
 }
 */

public class Suggestion {
    //定义内部类,待会添加映射关系
    public class Comfort{
        @SerializedName("txt")
        public String info;
    }
    public class CarWash{
        @SerializedName("txt")
        public String info;
    }
    public class Sport{
        @SerializedName("txt")
        public String info;
    }
    //添加内部类的映射关系
    @SerializedName("comf")
    public Comfort comfort;
    @SerializedName("cw")
    public CarWash carWash;

    public Sport sport;

}
