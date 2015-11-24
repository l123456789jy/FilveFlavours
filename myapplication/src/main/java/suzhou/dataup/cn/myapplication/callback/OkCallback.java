package suzhou.dataup.cn.myapplication.callback;

import android.os.Handler;
import android.os.Looper;

import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;

import suzhou.dataup.cn.myapplication.paser.OkBaseParser;

/**
 * 负责对okhttpcallback返回的封装，使其直接返回解析的对象！
 */
public abstract class OkCallback<T> implements Callback {

    private static Handler mHandler = new Handler(Looper.getMainLooper());

    private OkBaseParser<T> mParser;

    public OkCallback(OkBaseParser<T> mParser) {
        if (mParser == null) {
            throw new IllegalArgumentException("Parser can't be null");
        }
        this.mParser = mParser;
    }

    @Override
    public void onFailure(Request request, final IOException e) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                onFailure(e);
            }
        });
    }

    @Override
    public void onResponse(final Response response) {
        final int code = mParser.getCode();
        try {
            final T t = mParser.parseResponse(response);
            if (response.isSuccessful() && t != null) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        onSuccess(code, t);
                    }
                });
            } else {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        onFailure(new Exception(Integer.toString(code)));
                    }
                });
            }
        } catch (final Exception e) {
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    onFailure(e);
                }
            });
        }
    }

    public abstract void onSuccess(int code, T t);

    public abstract void onFailure(Throwable e);

    public void onStart() {
    }

}