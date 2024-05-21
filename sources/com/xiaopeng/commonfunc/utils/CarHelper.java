package com.xiaopeng.commonfunc.utils;

import android.car.Car;
import android.content.ComponentName;
import android.content.Context;
import android.content.ServiceConnection;
import android.os.IBinder;
import com.xiaopeng.lib.utils.LogUtils;
import org.greenrobot.eventbus.EventBus;
/* loaded from: classes4.dex */
public class CarHelper {
    private static final String TAG = "CarHelper";
    private static Car sCar;
    private static final ServiceConnection sServiceConnection = new ServiceConnection() { // from class: com.xiaopeng.commonfunc.utils.CarHelper.1
        @Override // android.content.ServiceConnection
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            LogUtils.i(CarHelper.TAG, "onServiceConnected, name: " + componentName + ", service: " + iBinder);
            EventBus.getDefault().post(Integer.valueOf((int) EventBusUtil.CAR_SERVICE_CONNECTED));
        }

        @Override // android.content.ServiceConnection
        public void onServiceDisconnected(ComponentName componentName) {
            LogUtils.i(CarHelper.TAG, "onServiceDisconnected, name: " + componentName);
        }
    };

    public static Car getCar() {
        return sCar;
    }

    public static void init(Context context) {
        sCar = Car.createCar(context, sServiceConnection);
        sCar.connect();
    }

    public static void deinit() {
        Car car = sCar;
        if (car != null) {
            car.disconnect();
        }
    }
}
