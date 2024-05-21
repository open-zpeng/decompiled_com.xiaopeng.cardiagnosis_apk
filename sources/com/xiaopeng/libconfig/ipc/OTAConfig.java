package com.xiaopeng.libconfig.ipc;
/* loaded from: classes5.dex */
public class OTAConfig {

    /* loaded from: classes5.dex */
    public enum ECUEnum {
        NAME_CDU("CDU", "XmartOS"),
        NAME_PSU("PSU", "安全控制器"),
        NAME_ICM("ICM", "仪表"),
        NAME_SCU("SCU", "智能控制器"),
        NAME_AVM("AVM", "智能感应器"),
        NAME_IPU("IPU", "IPU"),
        NAME_VCU("VCU", "VCU"),
        NAME_BMS("BMS", "BMS"),
        NAME_HVAC("HVAC", "HVAC"),
        NAME_NONE("NONE", "NONE");
        
        private String id;
        private String name;

        ECUEnum(String id, String name) {
            this.id = id;
            this.name = name;
        }

        public String getId() {
            return this.id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return this.name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
