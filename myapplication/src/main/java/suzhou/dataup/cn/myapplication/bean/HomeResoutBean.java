package suzhou.dataup.cn.myapplication.bean;

import java.util.List;

/**
 * 作者：liujingyuan on 2015/10/25 20:04
 * 邮箱：906514731@qq.com
 */
public class HomeResoutBean {

    /**
     * error : false
     * results : [{"createdAt":"2015-10-21T16:19:17.326Z","desc":"OpenGL示例代码","objectId":"5627bb0500b08a6c421e8c5e","publishedAt":"2015-10-23T04:01:16.218Z","type":"Android","updatedAt":"2015-10-23T04:01:18.484Z","url":"https://github.com/JimSeker/opengl","used":true,"who":"Jason"},{"createdAt":"2015-10-21T16:16:45.905Z","desc":"侧滑菜单流动效果","objectId":"5627ba6d60b27cc2d69a8724","publishedAt":"2015-10-23T04:01:16.217Z","type":"Android","updatedAt":"2015-10-23T04:01:16.802Z","url":"https://github.com/mxn21/FlowingDrawer","used":true,"who":"Jason"},{"createdAt":"2015-10-21T02:13:56.240Z","desc":"开源选型之 Android 三大图片缓存原理、特性对比","objectId":"5626f4e400b0ee7f82239df8","publishedAt":"2015-10-23T04:01:16.214Z","type":"Android","updatedAt":"2015-10-23T04:01:16.847Z","url":"http://mp.weixin.qq.com/s?__biz=MzAxNjI3MDkzOQ==&mid=400055274&idx=1&sn=89005ccb6b4317675c54ccf61cdb89b5#rd","used":true,"who":"有时放纵"},{"createdAt":"2015-10-21T02:09:15.406Z","desc":"Android对话框Dialog，PopupWindow，Toast的实现机制","objectId":"5626f3cb00b08a6c420729f9","publishedAt":"2015-10-23T04:01:16.213Z","type":"Android","updatedAt":"2015-10-23T04:01:17.063Z","url":"http://blog.csdn.net/feiduclear_up/article/details/49080587","used":true,"who":"有时放纵"},{"createdAt":"2015-10-22T03:33:20.470Z","desc":"使用DataBinding在xml中自定义字体","objectId":"5628590060b28da5c99b97c4","publishedAt":"2015-10-23T04:01:15.660Z","type":"Android","updatedAt":"2015-10-23T04:01:16.974Z","url":"https://github.com/lisawray/fontbinding","used":true,"who":"鲍永章"},{"createdAt":"2015-10-21T07:34:42.793Z","desc":"Android平台下的富文本显示控件,有了他妈妈再也不用担心加载HTML5了","objectId":"5627401200b0ee7f822ba914","publishedAt":"2015-10-22T02:06:07.750Z","type":"Android","updatedAt":"2015-10-22T02:06:08.372Z","url":"https://github.com/zzhoujay/RichText","used":true,"who":"AllenJuns"},{"createdAt":"2015-10-21T02:51:12.288Z","desc":"一个小巧的支持 Gif 的图片查看库","objectId":"5626fda000b09f851ff0941b","publishedAt":"2015-10-22T02:06:07.749Z","type":"Android","updatedAt":"2015-10-22T02:06:08.383Z","url":"https://github.com/kymjs/KJGallery","used":true,"who":"mthli"},{"createdAt":"2015-10-21T02:48:52.806Z","desc":"InfoQ: 利用Buck进行高效Android编译(包含Vine团队的使用体验)","objectId":"5626fd1460b2260e0641b0dd","publishedAt":"2015-10-22T02:06:07.747Z","type":"Android","updatedAt":"2015-10-22T02:06:08.376Z","url":"http://www.infoq.com/cn/news/2015/06/buck-android-build","used":true,"who":"狄家怡"},{"createdAt":"2015-10-21T02:34:20.351Z","desc":"皮筋式弹性下拉即刷新组件，效果很赞","objectId":"5626f9ac60b28045caed6b83","publishedAt":"2015-10-22T02:06:07.743Z","type":"Android","updatedAt":"2015-10-22T02:06:08.392Z","url":"https://github.com/gontovnik/DGElasticPullToRefresh","used":true,"who":"Dear宅学长"},{"createdAt":"2015-10-21T02:10:38.793Z","desc":"Android无需权限显示悬浮窗, 兼谈逆向分析app","objectId":"5626f41e60b27cc2d6834ab6","publishedAt":"2015-10-22T02:06:07.742Z","type":"Android","updatedAt":"2015-10-22T02:06:08.386Z","url":"http://www.jianshu.com/p/167fd5f47d5c","used":true,"who":"有时放纵"}]
     */

    public boolean error;
    public List<ResultsEntity> results;

    public static class ResultsEntity {
        /**
         * createdAt : 2015-10-21T16:19:17.326Z
         * desc : OpenGL示例代码
         * objectId : 5627bb0500b08a6c421e8c5e
         * publishedAt : 2015-10-23T04:01:16.218Z
         * type : Android
         * updatedAt : 2015-10-23T04:01:18.484Z
         * url : https://github.com/JimSeker/opengl
         * used : true
         * who : Jason
         */

        public String createdAt;
        public String desc;
        public String objectId;
        public String publishedAt;
        public String type;
        public String updatedAt;
        public String url;
        public boolean used;
        public String who;
    }
}
