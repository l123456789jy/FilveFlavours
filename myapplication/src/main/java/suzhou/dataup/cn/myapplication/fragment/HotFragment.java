package suzhou.dataup.cn.myapplication.fragment;

import android.support.v4.app.Fragment;

import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;

import suzhou.dataup.cn.myapplication.R;
import suzhou.dataup.cn.myapplication.callback.MyHttpCallBcak;
import suzhou.dataup.cn.myapplication.constance.CountUri;
import suzhou.dataup.cn.myapplication.mangers.OkHttpClientManager;
import suzhou.dataup.cn.myapplication.utiles.LogUtil;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link HotFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link HotFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HotFragment extends HotBasFragmentImp {
    public HotFragment() {
        super(R.layout.fragment_hot);
    }
    @Override
    protected void initContent() {
        super.initContent();
        LogUtil.e("response");
        OkHttpClientManager.get("https://newapi.meipai.com/hot/feed_timeline.json?model=Coolpad 8675&device_id=867112026387733&language=zh-Hans&client_secret=38e8c5aet76d5c012e32&client_id=1089857302&count=20&page=1", new MyHttpCallBcak() {
            @Override
            public void onFailure(Request request, IOException e) {
                LogUtil.e("response"+e);
            }
            @Override
            public void onResponse(Response response) {
                try {
                    LogUtil.e("response"+response.body().string());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
