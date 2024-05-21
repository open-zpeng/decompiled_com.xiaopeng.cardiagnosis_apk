package com.xiaopeng.libconfig.ipc.bean;

import com.xiaopeng.libconfig.ipc.bean.MessageContentBean;
import java.util.List;
import java.util.UUID;
/* loaded from: classes5.dex */
public class MessageCenterBean {
    public static final int CONTENT_POSITION_NOTIFY = 2;
    public static final int CONTENT_POSITION_NOTIFY_BOX = 1;
    public static final int OPPORTUNITY_GETOFF_SCENE = 4;
    public static final int OPPORTUNITY_GETON_MOMENT = 3;
    public static final int OPPORTUNITY_GETON_SCENE = 1;
    public static final int OPPORTUNITY_OTHER = 0;
    public static final int OPPORTUNITY_RUNNING_SCENE = 2;
    public static final int OPSITION_ACCOUNT = 9;
    public static final int OPSITION_AI = 1;
    public static final int OPSITION_CHARGE = 5;
    public static final int OPSITION_CONFIGURE = 12;
    public static final int OPSITION_DC = 14;
    public static final int OPSITION_MUSIC = 16;
    public static final int OPSITION_NAV = 2;
    public static final int OPSITION_OTA = 11;
    public static final int OPSITION_RESOURCE_CENTER = 15;
    public static final int OPSITION_WASH = 3;
    public static final int TYPE_ACC = 12;
    public static final int TYPE_AUDIOBOOKS = 3;
    public static final int TYPE_BIRTHDAY = 11;
    public static final int TYPE_EASTER_EGG = 100;
    public static final int TYPE_HAZE = 20;
    public static final int TYPE_HEAVY_FOG = 19;
    public static final int TYPE_HEAVY_RAINS = 18;
    public static final int TYPE_HEAVY_SNOWFALL = 17;
    public static final int TYPE_MAP = 10;
    public static final int TYPE_MAP_PARK = 14;
    public static final int TYPE_MAP_PATH = 13;
    public static final int TYPE_MUSIC = 2;
    public static final int TYPE_MUSIC_VIP = 22;
    public static final int TYPE_NORMAL = 1;
    public static final int TYPE_OTA_FAILED = 7;
    public static final int TYPE_OVERTIME = 15;
    public static final int TYPE_PATH_SELECT = 16;
    public static final int TYPE_PM_25 = 9;
    public static final int TYPE_RAINS = 21;
    public static final int TYPE_RECHARGE = 4;
    public static final int TYPE_RECHARGE_FULL = 8;
    public static final int TYPE_SUCCESS = 6;
    public static final int TYPE_WELCOME = 5;
    private int bizType;
    private int carState;
    private String content;
    private Content contentObject;
    private String messageId;
    private int messageType;
    private String packName;
    private int priority;
    private int read_state;
    private int scene;
    private long ts;
    private int type;
    private int uid;
    private long validEndTs;
    private long validStartTs;

    public String getMessageId() {
        return this.messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public int getMessageType() {
        return this.messageType;
    }

    public void setMessageType(int messageType) {
        this.messageType = messageType;
    }

    public int getType() {
        return this.type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public long getValidStartTs() {
        return this.validStartTs;
    }

    public void setValidStartTs(long validStartTs) {
        this.validStartTs = validStartTs;
    }

    public long getValidEndTs() {
        return this.validEndTs;
    }

    public void setValidEndTs(long validEndTs) {
        this.validEndTs = validEndTs;
    }

    public long getTs() {
        return this.ts;
    }

    public void setTs(long ts) {
        this.ts = ts;
    }

    public int getBizType() {
        return this.bizType;
    }

    public void setBizType(int bizType) {
        this.bizType = bizType;
    }

    public int getScene() {
        return this.scene;
    }

    public void setScene(int scene) {
        this.scene = scene;
    }

    public int getPriority() {
        return this.priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public int getUid() {
        return this.uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getPackName() {
        return this.packName;
    }

    public void setPackName(String packName) {
        this.packName = packName;
    }

    public int getCarState() {
        return this.carState;
    }

    public void setCarState(int carState) {
        this.carState = carState;
    }

    public int getRead_state() {
        return this.read_state;
    }

    public void setRead_state(int read_state) {
        this.read_state = read_state;
    }

    public String getContent() {
        return this.content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Content getContentObject() {
        return this.contentObject;
    }

    public void setContentObject(Content contentObject) {
        this.contentObject = contentObject;
    }

    public MessageContentBean getContentBean() {
        Content content = this.contentObject;
        if (content == null) {
            return null;
        }
        return content.getContent();
    }

    public List<MessageContentBean.MsgButton> getButtonList() {
        if (getContentBean() != null) {
            return getContentBean().getButtons();
        }
        return null;
    }

    public MessageContentBean.MsgButton getButton(int index) {
        if (getContentBean() != null) {
            return getContentBean().getButton(index);
        }
        return null;
    }

    /* loaded from: classes5.dex */
    public static class Content {
        MessageContentBean content;
        String contentStr;
        int opportunity;
        int position;
        int type;

        public int getType() {
            return this.type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public int getPosition() {
            return this.position;
        }

        public void setPosition(int position) {
            this.position = position;
        }

        public int getOpportunity() {
            return this.opportunity;
        }

        public void setOpportunity(int opportunity) {
            this.opportunity = opportunity;
        }

        public MessageContentBean getContent() {
            return this.content;
        }

        public void setContent(MessageContentBean content) {
            this.content = content;
        }

        public String getContentStr() {
            return this.contentStr;
        }

        public void setContentStr(String contentStr) {
            this.contentStr = contentStr;
        }
    }

    public static MessageCenterBean create(int bizType, MessageContentBean messageContentBean) {
        MessageCenterBean bean = new MessageCenterBean();
        bean.setMessageId(UUID.randomUUID().toString());
        long currentTime = System.currentTimeMillis();
        bean.setValidStartTs(currentTime);
        bean.setValidEndTs(3600000 + currentTime);
        bean.setMessageType(101);
        bean.setType(1);
        bean.setTs(currentTime);
        bean.setBizType(bizType);
        bean.setPriority(1);
        Content content = new Content();
        content.setType(1);
        content.setOpportunity(0);
        content.setPosition(1);
        content.setContent(messageContentBean);
        bean.setContentObject(content);
        return bean;
    }

    public String toString() {
        return "MessageCenterBean{messageId='" + this.messageId + "'}";
    }
}
