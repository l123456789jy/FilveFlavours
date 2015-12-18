package suzhou.dataup.cn.myapplication.listener;

import android.content.Context;
import android.content.res.Configuration;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

import suzhou.dataup.cn.myapplication.calback.OdrectionCallBack;

//方向传感器
public class TakePhotoSensorEventListener implements SensorEventListener {
    private float[] accelerometerValues = new float[3];
    private float[] magneticFieldValues = new float[3];
    public OdrectionCallBack mOdrectionCallBack;
    private Context context;
    private static float direction = 0;
    private float x = 0;
    private float y = 0;
    private float z = 0;
    private float angle;

    public TakePhotoSensorEventListener(Context context) {
        this.context = context;
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        if (sensorEvent.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            accelerometerValues = sensorEvent.values;
        }
        if (sensorEvent.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD) {
            magneticFieldValues = sensorEvent.values;
        }
        //计算手机的方位角
        calculateOrientation();
        if (sensorEvent.sensor.getType() == Sensor.TYPE_ORIENTATION) {
            x = sensorEvent.values[0];
            y = sensorEvent.values[1];
            z = sensorEvent.values[2];
            if (context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
                //横屏
                if (z > 0) {
                    //左侧边横屏
                    direction = x + 90;
                    if (direction > 360) {
                        direction = direction - 360;
                    }
                    if (y < 0 && y > -90) {
                        angle = -(90 - z);
                    } else if (y < -90 && y > -180) {
                        angle = 90 - z;
                    }
                } else {
                    direction = x - 90;
                    if (direction < 0) {
                        direction = direction + 360;
                    }
                    if (y > 0 && y < 90) {
                        angle = -(90 + z);
                    } else if (y > 90 && y < 180) {
                        angle = 90 + z;
                    }
                }
            } else {
                direction = x;
                if (y > -90 && y < 90) {
                    if (y < 0) {
                        angle = -90 - y;
                    } else {
                        angle = -90 + y;
                    }
                } else if (y > -180 && y < -90) {
                    angle = -(90 + y);
                } else if (y > 90 && y < 180) {
                    angle = -(90 - y);
                }
            }

        }
        mOdrectionCallBack.OnSucces(direction);
    }

    //设置方向的监听
    public void setOnOratLinstion(OdrectionCallBack mOdrectionCallBack) {
        this.mOdrectionCallBack = mOdrectionCallBack;

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    // 计算方向
    private void calculateOrientation() {
        float[] values = new float[3];
        float[] R = new float[9];
        SensorManager.getRotationMatrix(R, null, accelerometerValues,
                magneticFieldValues);
        SensorManager.getOrientation(R, values);
        values[0] = (float) Math.toDegrees(values[0]);
        if (values[0] >= -5 && values[0] < 5) {
        } else if (values[0] >= 5 && values[0] < 85) {
            // Log.i(TAG, "东北");
        } else if (values[0] >= 85 && values[0] <= 95) {
        } else if (values[0] >= 95 && values[0] < 175) {
            // Log.i(TAG, "东南");
        } else if ((values[0] >= 175 && values[0] <= 180)
                || (values[0]) >= -180 && values[0] < -175) {
            // Log.i(TAG, "正南");
        } else if (values[0] >= -175 && values[0] < -95) {
            // Log.i(TAG, "西南");
        } else if (values[0] >= -95 && values[0] < -85) {
            // Log.i(TAG, "正西");
        } else if (values[0] >= -85 && values[0] < -5) {
            // Log.i(TAG, "西北");
        }
    }
}