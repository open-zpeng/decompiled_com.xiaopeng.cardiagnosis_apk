package com.xiaopeng.commonfunc.model.car;

import android.car.CarNotConnectedException;
import android.car.hardware.CarEcuManager;
import android.car.hardware.CarPropertyValue;
import com.xiaopeng.commonfunc.Constant;
import com.xiaopeng.commonfunc.utils.CarHelper;
import com.xiaopeng.commonfunc.utils.EventBusUtil;
import com.xiaopeng.lib.utils.LogUtils;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
/* loaded from: classes4.dex */
public abstract class CommonCarModel<T extends CarEcuManager> {
    protected final String TAG;
    private volatile T mCarEcuManager;
    private CarEventChangedListener mCarEventChangedListener;
    private final Collection<Integer> mIdslist = new ArrayList();
    private final Collection<Integer> mIdsWithCachelist = new ArrayList();
    protected CarEcuManager.CarEcuEventCallback mCarEcuEventCallback = new CarEcuManager.CarEcuEventCallback() { // from class: com.xiaopeng.commonfunc.model.car.CommonCarModel.1
        public void onChangeEvent(CarPropertyValue value) {
            if (CommonCarModel.this.mCarEventChangedListener != null) {
                CommonCarModel.this.mCarEventChangedListener.onChangeEvent(value);
            }
        }

        public void onErrorEvent(int propertyId, int errorCode) {
            String str = CommonCarModel.this.TAG;
            LogUtils.e(str, "onErrorEvent propertyId: " + propertyId + " , errorCode: " + errorCode);
        }
    };

    protected abstract String getServiceName();

    public CommonCarModel(String name) {
        this.TAG = name + Constant.HYPHEN + getClass().getSimpleName();
        EventBusUtil.registerEventBus(this);
        initCarManager();
    }

    public T getCarManager() {
        initCarManager();
        return this.mCarEcuManager;
    }

    private void initCarManager() {
        if (this.mCarEcuManager == null) {
            synchronized (this) {
                if (this.mCarEcuManager == null) {
                    if (CarHelper.getCar() != null && CarHelper.getCar().isConnected()) {
                        try {
                            this.mCarEcuManager = (T) CarHelper.getCar().getCarManager(getServiceName());
                        } catch (CarNotConnectedException e) {
                            e.printStackTrace();
                        }
                    } else {
                        LogUtils.e(this.TAG, "Car is not connected yet");
                    }
                }
            }
        }
    }

    @Subscribe(threadMode = ThreadMode.BACKGROUND)
    public void onEvent(Integer event) {
        if (event.intValue() == 66442200) {
            LogUtils.d(this.TAG, "onEvent CAR_SERVICE_CONNECTED");
            initCarManager();
            synchronized (this.mIdslist) {
                if (!this.mIdslist.isEmpty()) {
                    registerPropCallback(this.mIdslist);
                }
            }
            synchronized (this.mIdsWithCachelist) {
                if (!this.mIdsWithCachelist.isEmpty()) {
                    registerPropCallbackWithCache(this.mIdsWithCachelist);
                }
            }
            CarPropertyValue value = new CarPropertyValue(1, (Object) null);
            CarEventChangedListener carEventChangedListener = this.mCarEventChangedListener;
            if (carEventChangedListener != null) {
                carEventChangedListener.onChangeEvent(value);
            }
        }
    }

    public void registerPropCallback(Collection<Integer> ids, CarEventChangedListener listener) {
        LogUtils.d(this.TAG, "registerPropCallback listener");
        synchronized (this.mIdslist) {
            this.mIdslist.addAll(ids);
            this.mCarEventChangedListener = listener;
            registerPropCallback(ids);
        }
    }

    public void registerPropCallbackWithCache(Collection<Integer> ids, CarEventChangedListener listener) {
        LogUtils.d(this.TAG, "registerPropCallbackWithCache listener");
        synchronized (this.mIdsWithCachelist) {
            this.mIdsWithCachelist.addAll(ids);
            this.mCarEventChangedListener = listener;
            registerPropCallbackWithCache(ids);
        }
    }

    private void registerPropCallback(Collection<Integer> ids) {
        String str = this.TAG;
        LogUtils.i(str, "registerPropCallback" + Arrays.toString(ids.toArray()));
        try {
            initCarManager();
            if (this.mCarEcuManager != null) {
                this.mCarEcuManager.registerPropCallback(ids, this.mCarEcuEventCallback);
            } else {
                LogUtils.e(this.TAG, "registerPropCallback carEcuManager == null");
            }
        } catch (CarNotConnectedException e) {
            e.printStackTrace();
        }
    }

    public void unregisterPropCallback(Collection<Integer> ids) {
        String str = this.TAG;
        LogUtils.i(str, "unregisterPropCallback" + Arrays.toString(ids.toArray()));
        try {
            initCarManager();
            if (this.mCarEcuManager != null) {
                this.mCarEcuManager.unregisterPropCallback(ids, this.mCarEcuEventCallback);
            } else {
                LogUtils.e(this.TAG, "registerPropCallback carEcuManager == null");
            }
        } catch (CarNotConnectedException e) {
            e.printStackTrace();
        }
    }

    private void registerPropCallbackWithCache(Collection<Integer> ids) {
        LogUtils.d(this.TAG, "registerPropCallbackWithCache");
        try {
            initCarManager();
            if (this.mCarEcuManager != null) {
                this.mCarEcuManager.registerPropCallbackWithFlag(ids, this.mCarEcuEventCallback, 1);
            } else {
                LogUtils.e(this.TAG, "registerPropCallbackWithCache carEcuManager == null");
            }
        } catch (CarNotConnectedException e) {
            e.printStackTrace();
        }
    }

    public void onDestroy() {
        EventBusUtil.unregisterEventBus(this);
        try {
            if (this.mCarEcuManager != null) {
                this.mCarEcuManager.unregisterCallback(this.mCarEcuEventCallback);
            }
            synchronized (this.mIdslist) {
                this.mIdslist.clear();
            }
            synchronized (this.mIdsWithCachelist) {
                this.mIdsWithCachelist.clear();
            }
            this.mCarEventChangedListener = null;
            this.mCarEcuManager = null;
            LogUtils.d(this.TAG, "disconnect CarEcuManager");
        } catch (CarNotConnectedException e) {
            String str = this.TAG;
            LogUtils.e(str, "disconnect CarEcuManager failed!" + e);
        }
    }
}
