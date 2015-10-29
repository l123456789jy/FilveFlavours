package suzhou.dataup.cn.myapplication.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import butterknife.InjectView;
import suzhou.dataup.cn.myapplication.R;
import suzhou.dataup.cn.myapplication.base.BaseFragment;
import suzhou.dataup.cn.myapplication.utiles.LogUtil;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link WealFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link WealFragment#newInstance} factory method to
 * create an instance of this fragment.
 * 福利的界面
 */
public class WealFragment extends BaseFragment {

    @InjectView(R.id.my_recycler_view)
    RecyclerView mMyRecyclerView;

    public WealFragment() {
        super(R.layout.fragment_weal);
    }

    @Override
    protected void initHead() {
    }

    @Override
    protected void initContent() {

    }

    @Override
    protected void initLocation() {

    }

    @Override
    protected void initLogic() {

    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }
}
