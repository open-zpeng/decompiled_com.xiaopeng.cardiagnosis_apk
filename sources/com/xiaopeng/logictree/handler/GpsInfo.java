package com.xiaopeng.logictree.handler;

import android.annotation.SuppressLint;
import android.app.Application;
import android.car.hardware.CarPropertyValue;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.OnNmeaMessageListener;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import com.alibaba.sdk.android.oss.common.RequestParameters;
import com.amap.api.services.geocoder.GeocodeSearch;
import com.xiaopeng.commonfunc.Constant;
import com.xiaopeng.commonfunc.bean.GpsSensorData;
import com.xiaopeng.commonfunc.model.car.CarEventChangedListener;
import com.xiaopeng.commonfunc.model.car.ImuModel;
import com.xiaopeng.lib.utils.LogUtils;
import com.xiaopeng.lib.utils.ThreadUtils;
import com.xiaopeng.lib.utils.info.BuildInfoUtils;
import com.xiaopeng.logictree.IssueInfo;
import com.xiaopeng.logictree.LogicTreeHelper;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
/* loaded from: classes5.dex */
public class GpsInfo extends LogicActionHandler {
    private static final int COLLECT_GPS_DATA_TIME = 10000;
    private static final int GPS_GET_IMU_SCULOCAT_CARSPEED = 1003;
    private static final int GPS_GET_IMU_SCULOCAT_CARSPEED_CAN = 1004;
    private static final int GPS_GET_IMU_SYSST_CAN = 1005;
    private static final int GPS_GET_LOCATION_CHANGE_TIMES = 1001;
    private static final int GPS_GET_NMEA_GNGSV = 1002;
    private static final String TAG_GPS_GNGSV = "$GNGSV,";
    private final CarEventChangedListener mCarEventChangedListener;
    @SuppressLint({"HandlerLeak"})
    private final Handler mHandler;
    private List<GpsSensorData> mImuDataList;
    private ImuModel mImuModel;
    private int mLocationChangeTimes;
    private final LocationListener mLocationListener;
    private LocationManager mLocationManager;
    @SuppressLint({"NewApi"})
    private final OnNmeaMessageListener mNmeaListener;
    private LinkedList<String> mQueue;

    static /* synthetic */ int access$208(GpsInfo x0) {
        int i = x0.mLocationChangeTimes;
        x0.mLocationChangeTimes = i + 1;
        return i;
    }

    public GpsInfo(Application application) {
        super(application);
        this.mHandler = new Handler(ThreadUtils.getLooper(0)) { // from class: com.xiaopeng.logictree.handler.GpsInfo.1
            @Override // android.os.Handler
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what) {
                    case 1001:
                        if (GpsInfo.this.mLocationManager != null) {
                            GpsInfo.this.mLocationManager.removeUpdates(GpsInfo.this.mLocationListener);
                        }
                        LogicTreeHelper.responseResult(Integer.valueOf(GpsInfo.this.mLocationChangeTimes));
                        return;
                    case 1002:
                        if (GpsInfo.this.mLocationManager != null) {
                            GpsInfo.this.mLocationManager.removeNmeaListener(GpsInfo.this.mNmeaListener);
                        }
                        GpsInfo.this.calNmeaValue();
                        return;
                    case 1003:
                        Collection<Integer> ids = new ArrayList<>();
                        ids.add(560018957);
                        GpsInfo.this.mImuModel.unregisterPropCallback(ids);
                        GpsInfo.this.checkImuData();
                        return;
                    case 1004:
                        Collection<Integer> ids2 = new ArrayList<>();
                        ids2.add(560018959);
                        GpsInfo.this.mImuModel.unregisterPropCallback(ids2);
                        GpsInfo.this.checkImuData();
                        return;
                    case 1005:
                        Collection<Integer> ids3 = new ArrayList<>();
                        ids3.add(560018958);
                        GpsInfo.this.mImuModel.unregisterPropCallback(ids3);
                        GpsInfo.this.checkImuData();
                        return;
                    default:
                        return;
                }
            }
        };
        this.mLocationListener = new LocationListener() { // from class: com.xiaopeng.logictree.handler.GpsInfo.2
            @Override // android.location.LocationListener
            public void onLocationChanged(Location location) {
                LogUtils.i(GpsInfo.this.CLASS_NAME, location.toString());
                GpsInfo.access$208(GpsInfo.this);
            }

            @Override // android.location.LocationListener
            public void onStatusChanged(String provider, int status, Bundle extras) {
            }

            @Override // android.location.LocationListener
            public void onProviderEnabled(String provider) {
            }

            @Override // android.location.LocationListener
            public void onProviderDisabled(String provider) {
            }
        };
        this.mNmeaListener = new OnNmeaMessageListener() { // from class: com.xiaopeng.logictree.handler.GpsInfo.3
            @Override // android.location.OnNmeaMessageListener
            public void onNmeaMessage(String message, long timestamp) {
                LogUtils.i(GpsInfo.this.CLASS_NAME, message);
                GpsInfo.this.mQueue.add(message);
            }
        };
        this.mCarEventChangedListener = new CarEventChangedListener() { // from class: com.xiaopeng.logictree.handler.-$$Lambda$GpsInfo$Jz1Ox_z84bm_TX5AZRfN-1a-fj4
            @Override // com.xiaopeng.commonfunc.model.car.CarEventChangedListener
            public final void onChangeEvent(CarPropertyValue carPropertyValue) {
                GpsInfo.this.lambda$new$0$GpsInfo(carPropertyValue);
            }
        };
        this.CLASS_NAME = "GpsInfo";
        this.mLocationManager = (LocationManager) this.context.getSystemService(RequestParameters.SUBRESOURCE_LOCATION);
        this.mImuModel = new ImuModel(this.CLASS_NAME);
        this.mImuDataList = new LinkedList();
        this.mQueue = new LinkedList<>();
    }

    public /* synthetic */ void lambda$new$0$GpsInfo(CarPropertyValue carPropertyValue) {
        int id = carPropertyValue.getPropertyId();
        Object propertyValue = carPropertyValue.getValue();
        switch (id) {
            case 560018957:
                if (propertyValue instanceof Float[]) {
                    Float[] values = (Float[]) propertyValue;
                    if (values != null && values.length > 36) {
                        this.mImuDataList.add(new GpsSensorData(values[18].floatValue(), values[22].floatValue(), values[26].floatValue(), values[20].floatValue(), values[24].floatValue(), values[28].floatValue()));
                        return;
                    }
                    return;
                }
                return;
            case 560018958:
                if (propertyValue instanceof Float[]) {
                    Float[] values2 = (Float[]) propertyValue;
                    if (values2 != null && values2.length > 6) {
                        this.mImuDataList.add(new GpsSensorData(values2[1].floatValue(), values2[2].floatValue(), values2[3].floatValue(), values2[4].floatValue(), values2[5].floatValue(), values2[6].floatValue()));
                        return;
                    }
                    return;
                }
                return;
            case 560018959:
                if (propertyValue instanceof Float[]) {
                    Float[] values3 = (Float[]) propertyValue;
                    if (values3 != null && values3.length > 9) {
                        this.mImuDataList.add(new GpsSensorData(values3[1].floatValue(), values3[2].floatValue(), values3[3].floatValue(), values3[4].floatValue(), values3[5].floatValue(), values3[6].floatValue()));
                        return;
                    }
                    return;
                }
                return;
            default:
                return;
        }
    }

    @Override // com.xiaopeng.logictree.handler.LogicActionHandler
    public synchronized String handleCommand(IssueInfo issueInfo) {
        super.handleCommand(issueInfo);
        if (checkArgu(this.argus, new String[]{"1"})) {
            this.mLocationChangeTimes = 0;
            this.mLocationManager.requestLocationUpdates(GeocodeSearch.GPS, 500L, 0.0f, this.mLocationListener, ThreadUtils.getLooper(0));
            this.mHandler.sendEmptyMessageDelayed(1001, 10000L);
        } else if (checkArgu(this.argus, new String[]{"2"})) {
            if (!this.mQueue.isEmpty()) {
                this.mQueue.clear();
            }
            this.mLocationManager.addNmeaListener(this.mNmeaListener, this.mHandler);
            this.mHandler.sendEmptyMessageDelayed(1002, 10000L);
        } else if (checkArgu(this.argus, new String[]{"3"})) {
            this.mImuDataList.clear();
            Collection<Integer> ids = new ArrayList<>();
            ids.add(560018957);
            this.mImuModel.registerPropCallback(ids, this.mCarEventChangedListener);
            this.mHandler.sendEmptyMessageDelayed(1003, 10000L);
        } else if (checkArgu(this.argus, new String[]{BuildInfoUtils.BID_LAN})) {
            this.mImuDataList.clear();
            Collection<Integer> ids2 = new ArrayList<>();
            ids2.add(560018959);
            this.mImuModel.registerPropCallback(ids2, this.mCarEventChangedListener);
            this.mHandler.sendEmptyMessageDelayed(1004, 10000L);
        } else if (checkArgu(this.argus, new String[]{BuildInfoUtils.BID_PT_SPECIAL_1})) {
            this.mImuDataList.clear();
            Collection<Integer> ids3 = new ArrayList<>();
            ids3.add(560018958);
            this.mImuModel.registerPropCallback(ids3, this.mCarEventChangedListener);
            this.mHandler.sendEmptyMessageDelayed(1005, 10000L);
        }
        return null;
    }

    @Override // com.xiaopeng.logictree.handler.LogicActionHandler
    public void destroy() {
        super.destroy();
        removeMessage();
        this.mLocationManager.removeNmeaListener(this.mNmeaListener);
        this.mLocationManager.removeUpdates(this.mLocationListener);
        this.mImuModel.onDestroy();
        this.mImuDataList.clear();
        if (!this.mQueue.isEmpty()) {
            this.mQueue.clear();
        }
    }

    private void removeMessage() {
        if (this.mHandler.hasMessages(1001)) {
            this.mHandler.removeMessages(1001);
        }
        if (this.mHandler.hasMessages(1002)) {
            this.mHandler.removeMessages(1002);
        }
        if (this.mHandler.hasMessages(1003)) {
            this.mHandler.removeMessages(1003);
        }
        if (this.mHandler.hasMessages(1004)) {
            this.mHandler.removeMessages(1004);
        }
        if (this.mHandler.hasMessages(1005)) {
            this.mHandler.removeMessages(1005);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Removed duplicated region for block: B:9:0x0023  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public void checkImuData() {
        /*
            r9 = this;
            java.lang.String[] r0 = r9.argus
            int r0 = r0.length
            r1 = 4
            if (r0 <= r1) goto Lab
            java.util.List<com.xiaopeng.commonfunc.bean.GpsSensorData> r0 = r9.mImuDataList
            int r0 = r0.size()
            java.lang.String[] r2 = r9.argus
            r3 = 1
            r2 = r2[r3]
            int r2 = java.lang.Integer.parseInt(r2)
            if (r0 <= r2) goto Lab
            java.util.List<com.xiaopeng.commonfunc.bean.GpsSensorData> r0 = r9.mImuDataList
            java.util.Iterator r0 = r0.iterator()
        L1d:
            boolean r2 = r0.hasNext()
            if (r2 == 0) goto La7
            java.lang.Object r2 = r0.next()
            com.xiaopeng.commonfunc.bean.GpsSensorData r2 = (com.xiaopeng.commonfunc.bean.GpsSensorData) r2
            double r3 = r2.getGyro_X()
            double r3 = java.lang.Math.abs(r3)
            java.lang.String[] r5 = r9.argus
            r6 = 2
            r5 = r5[r6]
            double r7 = java.lang.Double.parseDouble(r5)
            int r3 = (r3 > r7 ? 1 : (r3 == r7 ? 0 : -1))
            if (r3 > 0) goto La3
            double r3 = r2.getGyro_Y()
            double r3 = java.lang.Math.abs(r3)
            java.lang.String[] r5 = r9.argus
            r5 = r5[r6]
            double r7 = java.lang.Double.parseDouble(r5)
            int r3 = (r3 > r7 ? 1 : (r3 == r7 ? 0 : -1))
            if (r3 > 0) goto La3
            double r3 = r2.getGyro_Z()
            double r3 = java.lang.Math.abs(r3)
            java.lang.String[] r5 = r9.argus
            r5 = r5[r6]
            double r5 = java.lang.Double.parseDouble(r5)
            int r3 = (r3 > r5 ? 1 : (r3 == r5 ? 0 : -1))
            if (r3 > 0) goto La3
            double r3 = r2.getAcc_X()
            r5 = 4611686018427387904(0x4000000000000000, double:2.0)
            double r3 = java.lang.Math.pow(r3, r5)
            double r7 = r2.getAcc_Y()
            double r7 = java.lang.Math.pow(r7, r5)
            double r3 = r3 + r7
            double r7 = r2.getAcc_Z()
            double r5 = java.lang.Math.pow(r7, r5)
            double r3 = r3 + r5
            double r3 = java.lang.Math.sqrt(r3)
            java.lang.String[] r5 = r9.argus
            r6 = 3
            r5 = r5[r6]
            double r5 = java.lang.Double.parseDouble(r5)
            double r3 = r3 - r5
            double r3 = java.lang.Math.abs(r3)
            java.lang.String[] r5 = r9.argus
            r5 = r5[r1]
            double r5 = java.lang.Double.parseDouble(r5)
            int r3 = (r3 > r5 ? 1 : (r3 == r5 ? 0 : -1))
            if (r3 <= 0) goto La1
            goto La3
        La1:
            goto L1d
        La3:
            com.xiaopeng.logictree.LogicTreeHelper.responseNG()
            return
        La7:
            com.xiaopeng.logictree.LogicTreeHelper.responseOK()
            goto Lba
        Lab:
            java.util.List<com.xiaopeng.commonfunc.bean.GpsSensorData> r0 = r9.mImuDataList
            int r0 = r0.size()
            if (r0 != 0) goto Lb7
            com.xiaopeng.logictree.LogicTreeHelper.responseNoResult()
            goto Lba
        Lb7:
            com.xiaopeng.logictree.LogicTreeHelper.responseNG()
        Lba:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaopeng.logictree.handler.GpsInfo.checkImuData():void");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void calNmeaValue() {
        int i = 1;
        int cal = 0;
        List<Integer> gngsvList = new LinkedList<>();
        if (!this.mQueue.isEmpty() || this.argus.length <= 1) {
            int standardSnr = Integer.parseInt(this.argus[1]);
            while (!this.mQueue.isEmpty()) {
                String nmea = this.mQueue.remove();
                if (nmea.contains(TAG_GPS_GNGSV)) {
                    String[] values = nmea.substring(nmea.indexOf(TAG_GPS_GNGSV)).split(Constant.SEPARATOR_STRING);
                    try {
                        if (values.length > 1 && Integer.parseInt(values[1]) == i) {
                            if (values.length > 6 && Integer.parseInt(values[6]) > standardSnr) {
                                cal++;
                            }
                            if (values.length > 10 && Integer.parseInt(values[10]) > standardSnr) {
                                cal++;
                            }
                            if (values.length > 14 && Integer.parseInt(values[14]) > standardSnr) {
                                cal++;
                            }
                            if (values.length > 18 && Integer.parseInt(values[18]) > standardSnr) {
                                cal++;
                            }
                            if (Integer.parseInt(values[0]) == i) {
                                gngsvList.add(Integer.valueOf(cal));
                                i = 1;
                                cal = 0;
                            } else {
                                i++;
                            }
                        } else {
                            i = 1;
                            cal = 0;
                        }
                    } catch (NumberFormatException e) {
                        e.printStackTrace();
                    }
                }
            }
            int cal2 = 0;
            if (gngsvList.size() > 0) {
                for (Integer value : gngsvList) {
                    cal2 += value.intValue();
                }
                LogicTreeHelper.responseResult(Integer.valueOf(cal2 / gngsvList.size()));
                return;
            }
            LogicTreeHelper.responseResult((Integer) 0);
            return;
        }
        LogicTreeHelper.responseNoResult();
    }
}
