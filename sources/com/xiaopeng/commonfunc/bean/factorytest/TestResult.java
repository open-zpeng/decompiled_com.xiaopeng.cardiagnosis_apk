package com.xiaopeng.commonfunc.bean.factorytest;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
@Table(name = "t_factory_test_result")
/* loaded from: classes4.dex */
public class TestResult extends Model {
    @Column(name = "is_success")
    private boolean isSuccess;
    @Column(name = "items_result")
    private String itemsResult;
    @Column(name = "target")
    private String target;

    public String getTarget() {
        return this.target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public boolean isSuccess() {
        return this.isSuccess;
    }

    public void setSuccess(boolean success) {
        this.isSuccess = success;
    }

    public String getItemsResult() {
        return this.itemsResult;
    }

    public void setItemsResult(String itemsResult) {
        this.itemsResult = itemsResult;
    }

    @Override // com.activeandroid.Model
    public String toString() {
        return "TestResult{target='" + this.target + "', isSuccess=" + this.isSuccess + ", itemsResult='" + this.itemsResult + "'}";
    }
}
