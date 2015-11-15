package suzhou.dataup.cn.myapplication.bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * 作者：liujingyuan on 2015/11/15 10:11
 * 邮箱：906514731@qq.com
 */
public class CountViewPagerBean {

    /**
     * cate : http://news.ifeng.com/app/api/slides/index-json.shtml
     * commentsUrl : http://news.ifeng.com/a/20151115/46248932_0.shtml
     * documentId : imcp_102995400
     * editTime : 2015/11/15 07:46:25
     * next : {"id":102995006,"title":"祸不单行！法国高铁出轨致10死32伤","type":"slide"}
     * pre : {"id":"102995439","title":"男子被自动门撞伤头部 获赔1.3亿元","type":"slide"}
     * recommend : [{"id":"102975942","links":"http://api.iclient.ifeng.com/ipadtestdoc?aid=102975942","thumbnail":"http://d.ifengimg.com/w318_h222_q75/y2.ifengimg.com/a/2015_46/f8da1c0da793826.jpg","title":"刘銮雄7岁女儿坐拥12.61亿身家 揭其奢华生活","type":"slide"},{"id":"102974394","links":"http://api.iclient.ifeng.com/ipadtestdoc?aid=102974394","thumbnail":"http://d.ifengimg.com/w318_h222_q75/y1.ifengimg.com/ifengimcp/pic/20151114/2a5be7c59543ee5a5149_size39_w752_h458.jpeg","title":"巴黎枪击爆炸案现场","type":"slide"},{"id":"102979467","links":"http://api.iclient.ifeng.com/ipadtestdoc?aid=102979467","thumbnail":"http://d.ifengimg.com/w318_h222_q75/y0.ifengimg.com/a/2015_46/ce839f65ed80511.jpg","title":"美女老师再晒照 身材好奢侈品多","type":"slide"},{"id":"102938529","links":"http://api.iclient.ifeng.com/ipadtestdoc?aid=102938529","thumbnail":"http://d.ifengimg.com/w318_h222_q75/y3.ifengimg.com/a/2015_46/d590f30c38dc7fd.jpg","title":"卡戴珊小妹真空出街 躲狗仔遭咸猪手","type":"slide"},{"id":"102980316","links":"http://api.iclient.ifeng.com/ipadtestdoc?aid=102980316","thumbnail":"http://d.ifengimg.com/w318_h222_q75/y1.ifengimg.com/ifengimcp/pic/20151114/390d39c0374590144d0d_size134_w950_h575.jpg","title":"美哭了！冬雨过后大悲寺罕见奇景 犹如琉璃世界","type":"slide"},{"id":"102940556","links":"http://api.iclient.ifeng.com/ipadtestdoc?aid=102940556","thumbnail":"http://d.ifengimg.com/w318_h222_q75/y2.ifengimg.com/a/2015_46/6c2e711c0f33ae1.jpg","title":"江西森林公安缴获象牙制品600余公斤","type":"slide"}]
     * shareurl : http://i.ifeng.com/news/sharenews.f?aid=102995400
     * slides : [{"description":"11月14日报道，比利时警方11月14日对首都布鲁塞尔的莫伦贝克区展开突袭行动，逮捕多名与巴黎恐袭案有关的嫌犯。比利时司法部长称，多名与法国恐怖袭击事件有关联的嫌犯被逮捕。图为一名嫌犯被逮捕。","image":"http://d.ifengimg.com/mw640_q75/y2.ifengimg.com/a/2015_47/4e27c803767473c.jpg","title":"比利时警方逮捕巴黎恐袭案凶嫌一幕"},{"description":"巴黎市中心连环爆炸袭击案发后，土耳其出动大批警力对该国移民聚集区展开搜捕行动。越来越多的消息来源显示，这次袭击事件组织策划地很可能在比利时境内。此前，有目击者称，袭击巴黎的恐怖分子乘坐的是悬挂比利时牌号的车辆。","image":"http://d.ifengimg.com/mw640_q75/y1.ifengimg.com/a/2015_47/65a4d93f6ba9f39.jpg","title":"比利时警方逮捕巴黎恐袭案凶嫌一幕"},{"description":"比利时司法大臣科恩·吉恩斯(Koen Geens)通过比利时法语电视台RTBF向外界确认，比利时警方的此次行动与巴黎恐袭案中发现的两辆比利时牌照车辆有关。此前，一名目击者对法国媒体说，恐怖分子袭击巴黎巴塔克兰剧院时，停靠在剧院旁边的车辆中的一部使用的是比利时牌照。同时，法国警方在首个案发酒吧附近街区发现了另外一辆比利时牌照轿车。","image":"http://d.ifengimg.com/mw640_q75/y0.ifengimg.com/a/2015_47/75fc58990c587a0.jpg","title":"比利时警方逮捕巴黎恐袭案凶嫌一幕"},{"description":"比利时法语电视台RTBF在报道中指出，有一名男性嫌疑犯在搜捕行动中曾企图逃跑。目前，这名嫌犯已被比利时警方控制。莫伦贝克区区长弗朗丝瓦·舍伯曼透露，比警方质询了两名嫌疑人并掌控了一辆嫌疑车辆。","image":"http://d.ifengimg.com/mw640_q75/y3.ifengimg.com/a/2015_47/cc61e362ae21558.jpg","title":"比利时警方逮捕巴黎恐袭案凶嫌一幕"},{"description":"此间媒体在报道中指出，布鲁塞尔莫伦贝克区是摩洛哥、土耳其移民的聚集区。比利时警方在当天的行动中出动了防爆警察。","image":"http://d.ifengimg.com/mw640_q75/y0.ifengimg.com/a/2015_47/b242953d74113ab.jpg","title":"比利时警方逮捕巴黎恐袭案凶嫌一幕"},{"description":"比利时当局尚未证实在布鲁塞尔有多少人被逮捕。","image":"http://d.ifengimg.com/mw640_q75/y2.ifengimg.com/a/2015_47/8b591cd5e472179.jpg","title":"比利时警方逮捕巴黎恐袭案凶嫌一幕"},{"description":"其中一个恐怖分子离开叙利亚后，穿越土耳其抵达法国的路线。","image":"http://d.ifengimg.com/mw640_q75/y2.ifengimg.com/a/2015_47/c79b1817b029dce.jpg","title":"比利时警方逮捕巴黎恐袭案凶嫌一幕"},{"description":"国际刑警组织也加入了调查。","image":"http://d.ifengimg.com/mw640_q75/y0.ifengimg.com/a/2015_47/5e7424c83aa8f82.jpg","title":"比利时警方逮捕巴黎恐袭案凶嫌一幕"}]
     * source :
     * text :
     * title : 比利时警方逮捕巴黎恐袭案凶嫌一幕
     * url : http://news.ifeng.com/a/20151115/46248932_0.shtml
     * wwwurl : http://news.ifeng.com/a/20151115/46248932_0.shtml
     */

    public BodyEntity body;
    /**
     * class : 50018512
     * documentId : imcp_102995400
     * id : http://api.iclient.ifeng.com/ipadtestdoc?aid=102995400&channel=%E6%96%B0%E9%97%BB
     * o : 1
     * type : slides
     */

    public MetaEntity meta;

    public static class BodyEntity {
        public String cate;
        public String commentsUrl;
        public String documentId;
        public String editTime;
        /**
         * id : 102995006
         * title : 祸不单行！法国高铁出轨致10死32伤
         * type : slide
         */

        public NextEntity next;
        /**
         * id : 102995439
         * title : 男子被自动门撞伤头部 获赔1.3亿元
         * type : slide
         */

        public PreEntity pre;
        public String shareurl;
        public String source;
        public String text;
        public String title;
        public String url;
        public String wwwurl;
        /**
         * id : 102975942
         * links : http://api.iclient.ifeng.com/ipadtestdoc?aid=102975942
         * thumbnail : http://d.ifengimg.com/w318_h222_q75/y2.ifengimg.com/a/2015_46/f8da1c0da793826.jpg
         * title : 刘銮雄7岁女儿坐拥12.61亿身家 揭其奢华生活
         * type : slide
         */

        public List<RecommendEntity> recommend;
        /**
         * description : 11月14日报道，比利时警方11月14日对首都布鲁塞尔的莫伦贝克区展开突袭行动，逮捕多名与巴黎恐袭案有关的嫌犯。比利时司法部长称，多名与法国恐怖袭击事件有关联的嫌犯被逮捕。图为一名嫌犯被逮捕。
         * image : http://d.ifengimg.com/mw640_q75/y2.ifengimg.com/a/2015_47/4e27c803767473c.jpg
         * title : 比利时警方逮捕巴黎恐袭案凶嫌一幕
         */

        public List<SlidesEntity> slides;

        public static class NextEntity {
            public int id;
            public String title;
            public String type;
        }

        public static class PreEntity {
            public String id;
            public String title;
            public String type;
        }

        public static class RecommendEntity {
            public String id;
            public String links;
            public String thumbnail;
            public String title;
            public String type;
        }

        public static class SlidesEntity {
            public String description;
            public String image;
            public String title;
        }
    }

    public static class MetaEntity {
        @SerializedName("class")
        public String classX;
        public String documentId;
        public String id;
        public int o;
        public String type;
    }
}
