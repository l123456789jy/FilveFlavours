
package suzhou.dataup.cn.myapplication.ui.activity;

import android.annotation.SuppressLint;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnErrorListener;
import android.media.MediaPlayer.OnInfoListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.net.Uri;
import android.os.Handler;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import android.widget.VideoView;

import com.lidroid.xutils.DbUtils;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.http.HttpHandler;
import com.lidroid.xutils.util.LogUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import suzhou.dataup.cn.myapplication.R;
import suzhou.dataup.cn.myapplication.base.BaseActivity;
import suzhou.dataup.cn.myapplication.contex.ApplicationData;
import suzhou.dataup.cn.myapplication.utiles.LayoutUtilHor;
import suzhou.dataup.cn.myapplication.utiles.PreferencesUtils;
import suzhou.dataup.cn.myapplication.utiles.TimeUtils;
import suzhou.dataup.cn.myapplication.utiles.ToastUtils;

//视频播放
public class PlayVideoFragmentActivity extends BaseActivity {
    public static final String TAG = "VideoPlayer";

    private final String TAG1 = "main";
    private EditText et_path;
    private Button btn_play, btn_pause;
    private SeekBar seekBar;
    private VideoView vv_video;
    private boolean isPlaying;
    private HttpHandler handler;
    boolean isPlay = true;

    private TextView now_time;

    private TextView total_time;

    private HttpUtils httpUtils;

    private TextView update;
    private int progress;// 当前播放的的进度！
    private int eroww_progress;// 记录下错误是的进度！
    private int current;// 正在播放的进度！
    private CheckBox btn_dowald;
    private RelativeLayout rl;
    private ProgressBar pr;
    public String uri;
    private ImageView down_iv;
    private DbUtils dbuties;
    public boolean IsFirseDolode = true;//如果是用户第一次点击下载，就可以下载，防止多次点击下载！
    public boolean IsFillWapVide = true;//是否全屏播放视频！
    ArrayList<String> tvidList = new ArrayList<String>();//保存视频的Id
    ArrayList<String> timeList = new ArrayList<String>();//保存用户收藏的时间！
    private View title;
    private LayoutUtilHor hor;
    private boolean is_longing;
    private String plan;
    public Handler mhandler = new Handler() {
        public void handleMessage(android.os.Message msg) {

        }

    };

    public PlayVideoFragmentActivity() {
        super(R.layout.play_videio);
    }

    @Override
    protected void initHead() {
        seekBar = (SeekBar) view.findViewById(R.id.seekBar);
        // et_path = (EditText) findViewById(R.id.et_path);
        vv_video = (VideoView) view.findViewById(R.id.video_view);

        btn_play = (Button) view.findViewById(R.id.btn_play);
        btn_pause = (Button) view.findViewById(R.id.btn_pause);
        now_time = (TextView) view.findViewById(R.id.now_time);// 播放的时间！
        total_time = (TextView) view.findViewById(R.id.total_time);// 总的时间！
        update = (TextView) view.findViewById(R.id.update);// 下载的进度！
        btn_dowald = findViewByIds(R.id.btn_dowald);//收藏按钮！
        title = view.findViewById(R.id.title);//顶部的标题！

        rl = findViewByIds(R.id.rl);
        pr = findViewByIds(R.id.pr);//进度条！
        down_iv = findViewByIds(R.id.down_iv);//开始下载提示！


        btn_play.setOnClickListener(click);
        btn_pause.setOnClickListener(click);
        // 为进度条添加进度更改事件
        seekBar.setOnSeekBarChangeListener(change);
        //收藏按钮！
        btn_dowald.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

            }
        });
    }

    @SuppressLint("NewApi")
    @Override
    protected void initContent() {
        //得到播放视频的地址！
        String uriTitle = PlayVideoFragmentActivity.this.getIntent().getStringExtra("uri");
        LogUtils.i("传到视频界面的" + uriTitle);
        if (uriTitle != null) {//如果地址不为空就播放！
            String[] uriAndTitle = uriTitle.split(",");
            uri = uriAndTitle[0];
            //设置播放视频的名字！

            //获取到播放的进度！
            plan = PreferencesUtils.getString(PlayVideoFragmentActivity.this, ApplicationData.VideId);
            if (plan != null) {
                if (plan.equals("0") != true) {
                    int cournet = Integer.parseInt(plan);//获取到缓存的视频的进度！
                    play(cournet);//如果缓存里面有视频的进度就按照视频的进度播放！
                } else {//没有就直接播放！
                    play(0);// 播放视频！如果文件里面已经下载好了就直接播放了！
                }
            } else {
                play(0);// 播放视频！如果文件里面已经下载好了就直接播放了！
            }
        } else {
            ToastUtils.show("视频地址获取失败！");
        }
        //开始播放的时候隐藏进度条！
        vv_video.setOnPreparedListener(new OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                pr.setVisibility(View.GONE);
            }
        });//这个是用来检测播放的缓冲状态的！
        vv_video.setOnInfoListener(new OnInfoListener() {

            @Override
            public boolean onInfo(MediaPlayer mp, int what, int extra) {
                if (what == MediaPlayer.MEDIA_INFO_BUFFERING_START) {  //表示正在缓冲！
                    pr.setVisibility(View.VISIBLE);
                } else if (what == MediaPlayer.MEDIA_INFO_BUFFERING_END) {  //表示缓冲完毕！
                    //此接口每次回调完START就回调END,若不加上判断就会出现缓冲图标一闪一闪的卡顿现象
                    if (mp.isPlaying()) {
                        pr.setVisibility(View.GONE);
                    }
                }
                return false;
            }
        });
        //当用户点击视频的时候全屏显示!
        vv_video.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if (IsFillWapVide) {//全屏显示
                    title.setVisibility(View.GONE);
                    rl.setVisibility(View.GONE);//底部标题！
                    hor.drawViewLayout(vv_video, 1.2f, 1.2f, 0f, 0f);//视频的控件！
                    IsFillWapVide = false;
                } else {//不全屏显示
                    title.setVisibility(View.VISIBLE);
                    rl.setVisibility(View.VISIBLE);
                    hor.drawViewLayout(vv_video, 1.2f, 1 - ApplicationData.ScreenTitle - 0.168f, 0f, 0f);//视频的控件！
                    IsFillWapVide = true;
                }
                return false;
            }
        });
    }

    @Override
    protected void initLocation() {
        hor = new LayoutUtilHor();
        mLayoutUtil.drawViewLayout(down_iv, 0.31875f, 0.095f, 0f, 0f);//提示下载的位置！

        hor.drawViewLayout(rl, 0f, 0.168f, 0f, 0.029f);//下面按钮总布局！
        hor.drawViewLayout(btn_play, 0.0616f, 0.109f, 0.066f, 0f);//开始
        hor.drawViewLayout(btn_dowald, 0.0616f, 0.109f, 0.871f, 0f);//下载
        hor.drawViewLayout(btn_pause, 0.0616f, 0.109f, 0.066f, 0f);//暂停
        hor.drawViewLayout(now_time, 0f, 0f, 0.196f, 0f);//播放的时间
        hor.drawViewLayout(total_time, 0f, 0f, 0.743f, 0f);// 总的时间！
        hor.drawViewLayout(seekBar, 0.452f, 0.08f, 0.270f, 0f);//进度条
        hor.drawViewLayout(vv_video, 1.2f, 1 - ApplicationData.ScreenTitle - 0.168f, 0f, 0f);//视频的控件！
    }

    @Override
    protected void initLogic() {
       /* ApplicationData.getPlayDataBean().setUrl(ApplicationData.HeardUri);//播放视频的uri，通过！他来进行播放视频！
        ApplicationData.getPlayDataBean().setTvId(ApplicationData.VideId);//这个是播放视频的id通过他来进行查找！
        ApplicationData.getPlayDataBean().setTitle(ApplicationData.Title);//标题
        ApplicationData.getPlayDataBean().setImageUrl(ApplicationData.ImaeView);//图片的路径！
        ApplicationData.getPlayDataBean().setUpdata(System.currentTimeMillis() + "");//保存收藏的时间！
        dbuties = ApplicationData.getDbuties();//得到数据库的对象！*/

    }

    /**
     * 去下载视频！
     */
    @SuppressWarnings("static-access")
    private void DolawMovies() {
        //文件的名字用路径的md5,保存在sd卡下！
        //fileLoaderManagers.download(uri, "mnt/sdcard/menmen/"+ com.huidf.oldversion.util.MD5Utils.md5(uri), 3,null);//表示同时3个线程去下载视频！
    /*	//保存在sd卡了！
        httpUtils = new HttpUtils();
		handler = httpUtils.download(uri, "mnt/sdcard/menmen/"+ com.huidf.oldversion.util.MD5Utils.md5(uri), true, false,
				new RequestCallBack<File>() {
					@SuppressWarnings("deprecation")
					@Override
					public void onStart() {
						// ToastUtils.show(MainActivity.this, "开始下载！");
					}
					@Override
					public void onLoading(long total, long current,
							boolean isUploading) {
						super.onLoading(total, current, isUploading);
						
						ApplicationData.getPlayDataBean().setTotal((total/1024/1024)+"");//视频的总大小！
						ApplicationData.getPlayDataBean().setUpdata((current/1024/1024)+"");//已经下载的大小！
						
						
						System.out.println("视频总的大小"+total/1024/1024);//的到的单位是M
						System.out.println("已经下载"+current/1024/1024);//的到的单位是M
						System.out.println((int) (current * 100 / total) + "%");
						update.setText((int) (current * 100 / total) + "%");
						
						//handler.cancel();//取消下载!
						
						 * File file = new File("mnt/sdcard/menmen/pwd.mp4"); if
						 * (file.length()>0) { if (isPlay) { //
						 * play(0);//如果有文件就开始播放！ isPlay=false; } }
						 
						int count = ((int) (current * 100 / total));
						if (count > 0) {
							if (isPlay) {
								play(0);// 如果有文件就开始播放！
								isPlay = false;
							}
						}
					}

					@Override
					public void onSuccess(ResponseInfo<File> responseInfo) {
						//只有下载成功之后我采取保存数据信息！
					try {
							dbuties.save(ApplicationData.getPlayDataBean());
						} catch (DbException e) {
							System.out.println("保存失败");
							e.printStackTrace();
						}
					}

					@Override
					public void onFailure(HttpException error, String msg) {
						ToastUtils
								.show(PlayVideoFragmentActivity.this, "下载失败！");
						// play(0);//如果有文件就开始播放！
						// tv.setText(msg);
						// btn_down.setText("暂停<span style="font-family: Arial,
						// Helvetica, sans-serif;">");</span>
					}
				});*/
    }

    private OnSeekBarChangeListener change = new OnSeekBarChangeListener() {
        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {
            progress = seekBar.getProgress();
            if (vv_video != null && vv_video.isPlaying()) {
                // 设置当前播放的位置
                vv_video.seekTo(progress);
            }
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onProgressChanged(SeekBar seekBar, int progress,
                                      boolean fromUser) {

        }
    };
    private View.OnClickListener click = new View.OnClickListener() {

        @Override
        public void onClick(View v) {

            switch (v.getId()) {
                case R.id.btn_play:
                    play(0);
                    break;
                case R.id.btn_pause:
                    pause();
                    break;
                case R.id.btn_dowald://下载按钮！
                    if (uri != null) {
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                if (IsFirseDolode) {//第一次点击！
                                    DolawMovies();//下载视频
                                    IsFirseDolode = false;
                                }
                            }
                        }).start();
                        if (IsFirseDolode) {
                            down_iv.setVisibility(View.VISIBLE);//显示提示按钮!
                            mhandler.postDelayed(runnable, 500);
                        } else {
                            ToastUtils.show("已经点过了！");
                        }
                    } else {
                        ToastUtils.show("视频路径错误！");
                    }

                    break;
                default:
                    break;
            }
        }
    };

    Runnable runnable = new Runnable() {

        @Override
        public void run() {
            down_iv.setVisibility(View.GONE);
        }
    };


    protected void play(int msec) {

        //File file = new File("mnt/sdcard/menmen/awp.mp4");
        //File file = new File("mnt/sdcard/menmen/"+com.huidf.oldversion.util.MD5Utils.md5(url));
        Uri uri1 = Uri.parse(uri);
        vv_video.setVideoURI(uri1);
        LogUtils.i("视频的地址" + uri);
        LogUtils.i("视频的进度" + msec);
		/*if (!file.exists()) {
			Toast.makeText(this, "还没有缓存文件！", 0).show();
			return;
		}*/
		
	/*	if (file.exists()){//如果第一次去查看sd卡上时候有这个文件这个文件如果存在的情况下就去播放这个地址上的地址！
			vv_video.setVideoPath(file.getAbsolutePath());
			ToastUtils.show(PlayVideoFragmentActivity.this, "使用本地的资源播放！");
		}else {//如果该文件不存在那么就去网上在线看！
			vv_video.setVideoURI(uri);
			ToastUtils.show(PlayVideoFragmentActivity.this, "网上在线观看！");
		}*/
        Log.i(TAG, "开始播放");
        vv_video.start();

        // 按照初始位置播放
        vv_video.seekTo(msec);

        // 开始线程，更新进度条的刻度
        new Thread() {

            @Override
            public void run() {
                try {
                    isPlaying = true;
                    while (isPlaying) {
                        //因为是要更新uide
                        PlayVideoFragmentActivity.this.runOnUiThread(new Runnable() {
                            //其实是在这个时候要判断网络的状态！是不是中间断网！

                            @Override
                            public void run() {
                                // 设置进度条的最大进度为视频流的最大播放时长,这个获取视频的总长度需要不断的去更新因为有些手机第一次是获取不到的，只有不断的去获取！
                                seekBar.setMax(vv_video.getDuration());
                                total_time.setText(TimeUtils.getTime(vv_video.getDuration(), new SimpleDateFormat("mm:ss")));
                                current = vv_video.getCurrentPosition();//正在播放的进度！
                                now_time.setText(TimeUtils.getTime(vv_video.getCurrentPosition(), new SimpleDateFormat("mm:ss")));

                                PreferencesUtils.putString(PlayVideoFragmentActivity.this, ApplicationData.VideId, vv_video.getCurrentPosition() + "");//正在播放的视频进度！
                                LogUtils.i("存放视频的进度" + vv_video.getCurrentPosition());
                                seekBar.setProgress(current);
                            }
                        });
                        sleep(1000);//每隔一秒执行一次！
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();
        // 播放之后设置播放按钮不可用
        btn_play.setEnabled(false);

        vv_video.setOnCompletionListener(new OnCompletionListener() {

            @Override
            public void onCompletion(MediaPlayer mp) {
                // 在播放完毕被回调 如果播放完毕了接着在重复播放！
                //btn_play.setEnabled(true);
                play(0);// 播放视频！如果文件里面已经下载好了就直接播放了！
            }
        });
        vv_video.setOnErrorListener(new OnErrorListener() {

            @Override
            public boolean onError(MediaPlayer mp, int what, int extra) {
                // 设置当前播放的位置
                vv_video.pause();//暂停！
                vv_video.start();//继续！
                play(current);//设置播放的进度！当播放出错的时候！
                //isPlaying = false;
                return true;//如果设置true就可以防止他弹出错误的提示框！
            }
        });
    }

    /**
     * 162 * 重新开始播放 163
     */
    protected void replay() {
        if (vv_video != null && vv_video.isPlaying()) {
            vv_video.seekTo(0);
            btn_pause.setText("暂停");
            btn_pause.setBackgroundResource(R.drawable.stop_play_bt);
            return;
        }
        isPlaying = false;
        play(0);

    }

    /**
     * 暂停或继续
     */
    protected void pause() {
        if (btn_pause.getText().toString().trim().equals("继续")) {
            btn_pause.setText("暂停");
            btn_pause.setBackgroundResource(R.drawable.stop_play_bt);
            vv_video.start();
            return;
        }
        if (vv_video != null && vv_video.isPlaying()) {
            vv_video.pause();
            btn_pause.setText("继续");
            btn_pause.setBackgroundResource(R.drawable.start_play_bt);
        }
    }

    @Override
    protected void onStop() {
        // TODO Auto-generated method stub
        super.onStop();
        isPlaying = false;//停止循环！用来保存视频的播放进度！
    }

    /*
     * 停止播放
     */
    protected void stop() {
        if (vv_video != null && vv_video.isPlaying()) {
            vv_video.stopPlayback();
            btn_play.setEnabled(true);
            isPlaying = false;
        }
    }

    /* (non-Javadoc)
     * @see android.support.v4.app.FragmentActivity#onDestroy()
     */
    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
        vv_video = null;
    }
}
