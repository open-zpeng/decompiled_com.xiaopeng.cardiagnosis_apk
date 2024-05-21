package com.xiaopeng.commonfunc.bean.car;

import com.xiaopeng.commonfunc.utils.DataHelp;
/* loaded from: classes4.dex */
public class BatteryInfo {
    private int cell_connect;
    private int dc_impedance;
    private int discharge_current;
    private int incharging;
    private int indischarging;
    private int soc;
    private int soh;
    private int stepup_state;
    private int stepup_volt;
    private int voltage;

    public BatteryInfo(int incharging, int soc, int voltage, int dc_impedance, int soh, int cell_connect, int stepup_state, int stepup_volt, int indischarging, int discharge_current) {
        this.incharging = incharging;
        this.soc = soc;
        this.voltage = voltage;
        this.dc_impedance = dc_impedance;
        this.soh = soh;
        this.cell_connect = cell_connect;
        this.stepup_state = stepup_state;
        this.stepup_volt = stepup_volt;
        this.indischarging = indischarging;
        this.discharge_current = discharge_current;
    }

    public BatteryInfo(byte[] values) {
        try {
            this.incharging = DataHelp.byteToInt(values[0]);
            this.soc = DataHelp.byteToInt(values[1]);
            this.soh = DataHelp.byteToInt(values[2]);
            this.cell_connect = DataHelp.byteToInt(values[3]);
            this.stepup_state = DataHelp.byteToInt(values[4]);
            this.indischarging = DataHelp.byteToInt(values[5]);
            this.discharge_current = DataHelp.unsignedBytes2ToInt(DataHelp.byteSub(values, 6, 2));
            this.voltage = DataHelp.unsignedBytes2ToInt(DataHelp.byteSub(values, 8, 2));
            this.dc_impedance = DataHelp.unsignedBytes2ToInt(DataHelp.byteSub(values, 10, 2));
            this.stepup_volt = DataHelp.unsignedBytes2ToInt(DataHelp.byteSub(values, 12, 2));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int getIncharging() {
        return this.incharging;
    }

    public void setIncharging(int incharging) {
        this.incharging = incharging;
    }

    public int getSoc() {
        return this.soc;
    }

    public void setSoc(int soc) {
        this.soc = soc;
    }

    public int getVoltage() {
        return this.voltage;
    }

    public void setVoltage(int voltage) {
        this.voltage = voltage;
    }

    public int getDc_impedance() {
        return this.dc_impedance;
    }

    public void setDc_impedance(int dc_impedance) {
        this.dc_impedance = dc_impedance;
    }

    public int getSoh() {
        return this.soh;
    }

    public void setSoh(int soh) {
        this.soh = soh;
    }

    public int getCell_connect() {
        return this.cell_connect;
    }

    public void setCell_connect(int cell_connect) {
        this.cell_connect = cell_connect;
    }

    public int getStepup_state() {
        return this.stepup_state;
    }

    public void setStepup_state(int stepup_state) {
        this.stepup_state = stepup_state;
    }

    public int getStepup_volt() {
        return this.stepup_volt;
    }

    public void setStepup_volt(int stepup_volt) {
        this.stepup_volt = stepup_volt;
    }

    public int getIndischarging() {
        return this.indischarging;
    }

    public void setIndischarging(int indischarging) {
        this.indischarging = indischarging;
    }

    public int getDischarge_current() {
        return this.discharge_current;
    }

    public void setDischarge_current(int discharge_current) {
        this.discharge_current = discharge_current;
    }

    public String toString() {
        return "BatteryInfo{incharging=" + this.incharging + ", soc=" + this.soc + ", voltage=" + this.voltage + ", dc_impedance=" + this.dc_impedance + ", soh=" + this.soh + ", cell_connect=" + this.cell_connect + ", stepup_state=" + this.stepup_state + ", stepup_volt=" + this.stepup_volt + ", indischarging=" + this.indischarging + ", discharge_current=" + this.discharge_current + '}';
    }
}
