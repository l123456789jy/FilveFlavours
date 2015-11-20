package suzhou.dataup.cn.myapplication.bean;

import java.util.List;

/**
 * 作者：liujingyuan on 2015/11/20 18:01
 * 邮箱：906514731@qq.com
 */
public class WeatrherBean {

    /**
     * aqi : 113
     * city : 北京
     * forecast : [{"date":"20日星期五","fengli":"微风级","fengxiang":"无持续风向","high":"高温 2℃","low":"低温 0℃","type":"阴"},{"date":"21日星期六","fengli":"微风级","fengxiang":"无持续风向","high":"高温 0℃","low":"低温 -1℃","type":"大雪"},{"date":"22日星期天","fengli":"微风级","fengxiang":"无持续风向","high":"高温 0℃","low":"低温 -3℃","type":"大雪"},{"date":"23日星期一","fengli":"微风级","fengxiang":"无持续风向","high":"高温 -1℃","low":"低温 -4℃","type":"小雪"},{"date":"24日星期二","fengli":"微风级","fengxiang":"无持续风向","high":"高温 -2℃","low":"低温 -6℃","type":"多云"}]
     * ganmao : 天气寒冷，昼夜温差极大且空气湿度较大，易发生感冒，请注意适当增减衣服，加强自我防护避免感冒。
     * wendu : 2
     * yesterday : {"date":"19日星期四","fl":"微风","fx":"无持续风向","high":"高温 5℃","low":"低温 1℃","type":"小雨"}
     */

    public DataEntity data;
    /**
     * data : {"aqi":"113","city":"北京","forecast":[{"date":"20日星期五","fengli":"微风级","fengxiang":"无持续风向","high":"高温 2℃","low":"低温 0℃","type":"阴"},{"date":"21日星期六","fengli":"微风级","fengxiang":"无持续风向","high":"高温 0℃","low":"低温 -1℃","type":"大雪"},{"date":"22日星期天","fengli":"微风级","fengxiang":"无持续风向","high":"高温 0℃","low":"低温 -3℃","type":"大雪"},{"date":"23日星期一","fengli":"微风级","fengxiang":"无持续风向","high":"高温 -1℃","low":"低温 -4℃","type":"小雪"},{"date":"24日星期二","fengli":"微风级","fengxiang":"无持续风向","high":"高温 -2℃","low":"低温 -6℃","type":"多云"}],"ganmao":"天气寒冷，昼夜温差极大且空气湿度较大，易发生感冒，请注意适当增减衣服，加强自我防护避免感冒。","wendu":"2","yesterday":{"date":"19日星期四","fl":"微风","fx":"无持续风向","high":"高温 5℃","low":"低温 1℃","type":"小雨"}}
     * desc : OK
     * status : 1000
     */

    public String desc;
    public int status;

    public static class DataEntity {
        public String aqi;
        public String city;
        public String ganmao;
        public String wendu;
        /**
         * date : 19日星期四
         * fl : 微风
         * fx : 无持续风向
         * high : 高温 5℃
         * low : 低温 1℃
         * type : 小雨
         */

        public YesterdayEntity yesterday;
        /**
         * date : 20日星期五
         * fengli : 微风级
         * fengxiang : 无持续风向
         * high : 高温 2℃
         * low : 低温 0℃
         * type : 阴
         */

        public List<ForecastEntity> forecast;

        public static class YesterdayEntity {
            public String date;
            public String fl;
            public String fx;
            public String high;
            public String low;
            public String type;
        }

        public static class ForecastEntity {
            public String date;
            public String fengli;
            public String fengxiang;
            public String high;
            public String low;
            public String type;
        }
    }
}
