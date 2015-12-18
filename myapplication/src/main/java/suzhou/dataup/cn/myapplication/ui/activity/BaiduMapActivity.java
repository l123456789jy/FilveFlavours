package suzhou.dataup.cn.myapplication.ui.activity;

import android.content.Context;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;

import butterknife.InjectView;
import suzhou.dataup.cn.myapplication.R;
import suzhou.dataup.cn.myapplication.base.BaseActivity;
import suzhou.dataup.cn.myapplication.calback.OdrectionCallBack;
import suzhou.dataup.cn.myapplication.listener.TakePhotoSensorEventListener;

public class BaiduMapActivity extends BaseActivity implements OdrectionCallBack {
    public MapView mMapView;
    @InjectView(R.id.toolbar)
    Toolbar mToolbar;
    BaiduMap mBaiduMap;
    // 定位相关
    LocationClient mLocClient;
    SensorManager sensorManager;
    public BDLocation mlocation;
    private Sensor accelerometer; // 加速度传感器
    private Sensor magnetic; // 地磁场传感器
    public MyLocationListenner myListener = new MyLocationListenner();
    private TakePhotoSensorEventListener sensorEventListener;

    public BaiduMapActivity() {
        super(R.layout.activity_baidu_map);
    }

    @Override
    protected void initHead() {
        mMapView = (MapView) findViewById(R.id.bmapView);
        mToolbar.setTitle("地图");
        mToolbar.setTitleTextColor(Color.WHITE);
        mToolbar.setNavigationIcon(R.drawable.abc_ic_ab_back_mtrl_am_alpha);
    }

    @Override
    protected void initContent() {
        sensorEventListener = new TakePhotoSensorEventListener(getApplicationContext());//初始化传感器！
        sensorManager = (SensorManager) this.getSystemService(Context.SENSOR_SERVICE);
        // 初始化加速度传感器
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        // 初始化地磁场传感器
        magnetic = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        startSensorListerner();//开启传感器！
        sensorEventListener.setOnOratLinstion(this);
        mBaiduMap = mMapView.getMap();
        mBaiduMap.setBuildingsEnabled(true);
        // 开启定位图层
        mBaiduMap.setMyLocationEnabled(true);
        // 定位初始化
        mLocClient = new LocationClient(this);
        mLocClient.registerLocationListener(myListener);
        LocationClientOption option = new LocationClientOption();
        option.setOpenGps(true);// 打开gps
        option.setCoorType("bd09ll"); // 设置坐标类型
        //设置定位的频率
        option.setScanSpan(10000);
        mLocClient.setLocOption(option);
        //开始定位
        mLocClient.start();
        //设置百度地图的默认显示级别
        mBaiduMap.setMapStatus(MapStatusUpdateFactory.newMapStatus(new MapStatus.Builder().zoom(20).build()));
        //设置显示的模式为罗盘效果
        mBaiduMap.setMyLocationConfigeration(new MyLocationConfiguration(MyLocationConfiguration.LocationMode.COMPASS, true, null));
    }

    @Override
    protected void initLocation() {

    }

    @Override
    protected void initLogic() {
        //监听返回键
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BaiduMapActivity.this.onBackPressed();
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        // activity 暂停时同时暂停地图控件
        mMapView.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        // activity 恢复时同时恢复地图控件
        mMapView.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // activity 销毁时同时销毁地图控件
        mMapView.onDestroy();
        // 退出时销毁定位
        mLocClient.stop();
        // 关闭定位图层
        mBaiduMap.setMyLocationEnabled(false);
        //停止传感器监听
        stopSensorListerner();
    }

    //监听方向的回调
    @Override
    public void OnSucces(float direction) {
        // map view 销毁后不在处理新接收的位置
      /*  if (mlocation == null || mMapView == null){
        }else{
            try {
                new Thread().sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            // 此处设置开发者获取到的方向信息，顺时针0-360
            MyLocationData locData = new MyLocationData.Builder().accuracy(mlocation.getRadius()).direction(direction).latitude(mlocation.getLatitude())
                    .longitude(mlocation.getLongitude()).build();
            //设置当前显示的坐标位置
            mBaiduMap.setMyLocationData(locData);
            LogUtil.e("方向==="+direction);
        }*/
    }

    /**
     * 定位SDK监听函数
     */
    public class MyLocationListenner implements BDLocationListener {
        @Override
        public void onReceiveLocation(BDLocation location) {

            // map view 销毁后不在处理新接收的位置
            if (location == null || mMapView == null)
                return;
            mlocation = location;
            // 此处设置开发者获取到的方向信息，顺时针0-360
            MyLocationData locData = new MyLocationData.Builder().accuracy(location.getRadius()).direction(location.getOperators()).latitude(location.getLatitude())
                    .longitude(location.getLongitude()).build();
            //设置当前显示的坐标位置
            mBaiduMap.setMyLocationData(locData);
        }

        public void onReceivePoi(BDLocation poiLocation) {
        }
    }

    /**
     * 开始监听Sensor事件
     */
    private void startSensorListerner() {
        if (sensorManager != null && sensorEventListener != null) {
            sensorManager.registerListener(sensorEventListener, sensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION), SensorManager.SENSOR_DELAY_NORMAL);
            // 注册监听
            sensorManager.registerListener(sensorEventListener, accelerometer, Sensor.TYPE_ACCELEROMETER);
            sensorManager.registerListener(sensorEventListener, magnetic, Sensor.TYPE_MAGNETIC_FIELD);
        }
    }

    /**
     * 停止监听Sensor事件
     */
    private void stopSensorListerner() {
        if (sensorManager != null && sensorEventListener != null) {
            sensorManager.unregisterListener(sensorEventListener);
        }
    }
}
