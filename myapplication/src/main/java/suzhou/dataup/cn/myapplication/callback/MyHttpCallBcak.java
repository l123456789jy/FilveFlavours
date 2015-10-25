package suzhou.dataup.cn.myapplication.callback;

import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;

/**
 * 作者：liujingyuan on 2015/10/25 21:07
 * 邮箱：906514731@qq.com
 */
public interface MyHttpCallBcak {
    void onFailure(Request request, IOException e);

    void onResponse(Response response);
}
