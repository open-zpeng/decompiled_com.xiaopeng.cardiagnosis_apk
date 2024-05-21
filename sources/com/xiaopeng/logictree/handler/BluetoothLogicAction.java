package com.xiaopeng.logictree.handler;

import android.annotation.SuppressLint;
import android.app.Application;
import android.bluetooth.BluetoothDevice;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import com.xiaopeng.commonfunc.model.factorytest.hardwaretest.BluetoothModel;
import com.xiaopeng.commonfunc.model.test.callback.BluetoothCallback;
import com.xiaopeng.lib.utils.ThreadUtils;
import com.xiaopeng.logictree.IssueInfo;
import com.xiaopeng.logictree.LogicTreeHelper;
/* loaded from: classes5.dex */
public class BluetoothLogicAction extends LogicActionHandler {
    private static final int DUMMY = 0;
    private static final int NEXT_STEP_DISABLE_BLUETOOTH = 1002;
    private static final int NEXT_STEP_ENABLE_BLUETOOTH = 1001;
    private static final int NEXT_STEP_RESPONSE_RESULT = 1000;
    private static final int TIME_OUT = 100001;
    private static final int WAITING_TIMES_FOR_RESTART_BLUETOOTH = 10000;
    @SuppressLint({"HandlerLeak"})
    private static final Handler mHandler = new Handler(ThreadUtils.getLooper(1)) { // from class: com.xiaopeng.logictree.handler.BluetoothLogicAction.1
        @Override // android.os.Handler
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            if (msg.what == 100001) {
                LogicTreeHelper.responseNG();
            }
        }
    };
    private BluetoothCallback mBluetoothCallback;
    private BluetoothModel mBluetoothModel;
    private int mNextAction;

    public BluetoothLogicAction(Application application) {
        super(application);
        this.mNextAction = 0;
        this.mBluetoothCallback = new BluetoothCallback() { // from class: com.xiaopeng.logictree.handler.BluetoothLogicAction.2
            @Override // com.xiaopeng.commonfunc.model.test.callback.BluetoothCallback
            public void onBtPower(boolean isOn) {
                if (!isOn && BluetoothLogicAction.this.mNextAction == 1001) {
                    BluetoothLogicAction.this.mNextAction = 1000;
                    BluetoothLogicAction.this.mBluetoothModel.powerOnBluetooth();
                } else if (isOn && BluetoothLogicAction.this.mNextAction == 1000) {
                    BluetoothLogicAction.this.mNextAction = 0;
                    if (BluetoothLogicAction.mHandler.hasMessages(100001)) {
                        BluetoothLogicAction.mHandler.removeMessages(100001);
                        LogicTreeHelper.responseOK();
                    }
                }
            }

            @Override // com.xiaopeng.commonfunc.model.test.callback.BluetoothCallback
            public void onBtPairStatus(int status, BluetoothDevice device) {
            }

            @Override // com.xiaopeng.commonfunc.model.test.callback.BluetoothCallback
            public void onBtConnectStatus(BluetoothDevice device, int state, int preState, int profileType) {
            }

            @Override // com.xiaopeng.commonfunc.model.test.callback.BluetoothCallback
            public void onScanCallback(BluetoothDevice device, int rssi) {
            }

            @Override // com.xiaopeng.commonfunc.model.test.callback.BluetoothCallback
            public void onScanStatus(boolean isDiscovering) {
            }

            @Override // com.xiaopeng.commonfunc.model.test.callback.BluetoothCallback
            public void onBindSuccess() {
            }

            @Override // com.xiaopeng.commonfunc.model.test.callback.BluetoothCallback
            public void onPairRequest(BluetoothDevice device, int variant, int passkey) {
            }
        };
        this.CLASS_NAME = "BluetoothLogicAction";
        this.mBluetoothModel = new BluetoothModel(this.context, this.mBluetoothCallback);
    }

    @Override // com.xiaopeng.logictree.handler.LogicActionHandler
    public synchronized String handleCommand(IssueInfo issueInfo) {
        super.handleCommand(issueInfo);
        if (checkArgu(this.argus, new String[]{"1"})) {
            int btPowerStatus = this.mBluetoothModel.getBtPowerStatus();
            if (btPowerStatus == 10) {
                this.mNextAction = 1000;
                this.mBluetoothModel.powerOnBluetooth();
                mHandler.sendEmptyMessageDelayed(100001, 10000L);
            } else if (btPowerStatus == 12) {
                this.mNextAction = 1001;
                this.mBluetoothModel.powerOffBluetooth();
                mHandler.sendEmptyMessageDelayed(100001, 10000L);
            } else {
                LogicTreeHelper.responseNG();
            }
        }
        return null;
    }

    @Override // com.xiaopeng.logictree.handler.LogicActionHandler
    public void destroy() {
        super.destroy();
        this.mBluetoothModel.onDestroy();
    }
}
