package com.xiaopeng.commonfunc.model.factorytest.hardwaretest;

import android.bluetooth.BluetoothDevice;
import android.content.Context;
import com.xiaopeng.commonfunc.model.test.BluetoothTest;
import com.xiaopeng.commonfunc.model.test.callback.BluetoothCallback;
import com.xiaopeng.lib.utils.LogUtils;
/* loaded from: classes4.dex */
public class BluetoothModel {
    private static final String TAG = "BluetoothModel";
    private final BluetoothTest mBluetoothTest;

    public BluetoothModel(Context context, BluetoothCallback callback) {
        this.mBluetoothTest = new BluetoothTest(context);
        this.mBluetoothTest.registerCallback(callback);
        this.mBluetoothTest.registerReceiver();
    }

    public void onInit() {
    }

    public String getBluetoothAddr() {
        String str = this.mBluetoothTest.getMacAddress();
        LogUtils.i(TAG, "getBluetoothAddr = " + str);
        return str;
    }

    public void getProfileProxy(int profile) {
        LogUtils.i(TAG, "getProfileProxy");
        this.mBluetoothTest.getProfileProxy(profile);
    }

    public void startScan() {
        LogUtils.i(TAG, "startScan");
        this.mBluetoothTest.startDiscovery();
    }

    public void powerOnBluetooth() {
        LogUtils.i(TAG, "powerOnBluetooth");
        this.mBluetoothTest.enable();
    }

    public void powerOffBluetooth() {
        LogUtils.i(TAG, "powerOffBluetooth");
        this.mBluetoothTest.disable();
    }

    public boolean enterBtTestMode() {
        return false;
    }

    public int getBtPowerStatus() {
        int status = this.mBluetoothTest.getState();
        LogUtils.i(TAG, "getBtPowerStatus = " + status);
        return status;
    }

    public void stopScan() {
        LogUtils.i(TAG, "stopScan");
        this.mBluetoothTest.cancelDiscovery();
    }

    public BluetoothDevice getConnectedDevice() {
        return this.mBluetoothTest.getConnectedDevice();
    }

    public void unPairDevice(String address) {
        LogUtils.i(TAG, "unPairDevice address = " + address);
        BluetoothTest bluetoothTest = this.mBluetoothTest;
        bluetoothTest.unpair(bluetoothTest.getRemoteDevice(address));
    }

    public void pairDevice(String address) {
        LogUtils.i(TAG, "pairDevice address = " + address);
        BluetoothTest bluetoothTest = this.mBluetoothTest;
        bluetoothTest.pairOrAcceptPair(bluetoothTest.getRemoteDevice(address), true);
    }

    public void pairDeviceWithoutScan(String address) {
        BluetoothTest bluetoothTest = this.mBluetoothTest;
        bluetoothTest.pairOrAcceptPair(bluetoothTest.getRemoteDevice(address), true);
    }

    public void connectDevice(String address, int profile) {
        BluetoothTest bluetoothTest = this.mBluetoothTest;
        bluetoothTest.connectProfile(bluetoothTest.getRemoteDevice(address), profile);
    }

    public void disconnectDevice(String address) {
        BluetoothTest bluetoothTest = this.mBluetoothTest;
        bluetoothTest.disconnectDevice(bluetoothTest.getRemoteDevice(address));
    }

    public void disconnectDevice(String address, int profile) {
        BluetoothTest bluetoothTest = this.mBluetoothTest;
        bluetoothTest.disconnectProfile(bluetoothTest.getRemoteDevice(address), profile);
    }

    public boolean isConnected() {
        return this.mBluetoothTest.isConnected();
    }

    public boolean isBtScanning() {
        return this.mBluetoothTest.isDiscovering();
    }

    public void onDestroy() {
        this.mBluetoothTest.unregisterReceiver();
        this.mBluetoothTest.releaseCallback();
        this.mBluetoothTest.closeProfileProxy();
    }
}
