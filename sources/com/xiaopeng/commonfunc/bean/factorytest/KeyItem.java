package com.xiaopeng.commonfunc.bean.factorytest;
/* loaded from: classes4.dex */
public class KeyItem {
    private final boolean mIsNeedResultButton;
    private String mKeyName;
    private boolean mKeyResult;

    public KeyItem(String mKeyName, boolean mKeyResult, boolean mIsNeedResultButton) {
        this.mKeyName = mKeyName;
        this.mKeyResult = mKeyResult;
        this.mIsNeedResultButton = mIsNeedResultButton;
    }

    public String getKeyName() {
        return this.mKeyName;
    }

    public void setKeyName(String mKeyName) {
        this.mKeyName = mKeyName;
    }

    public boolean isKeyPass() {
        return this.mKeyResult;
    }

    public void setKeyResult(boolean mKeyResult) {
        this.mKeyResult = mKeyResult;
    }

    public boolean isNeedResultButton() {
        return this.mIsNeedResultButton;
    }
}
