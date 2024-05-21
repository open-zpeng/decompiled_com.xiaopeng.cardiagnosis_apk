package com.xiaopeng.libconfig.ipc.bean;

import java.util.ArrayList;
import java.util.List;
/* loaded from: classes5.dex */
public class MessageContentBean {
    public static final int COMMING_SOUND_DEFAULT = 0;
    public static final int COMMING_SOUND_NONE = 2;
    public static final int COMMING_SOUND_WARNNING = 1;
    public static final int EXECUTE_AFTER_TTS = 1;
    public static final int EXECUTE_DELAY = 2;
    public static final int EXECUTE_USER = 0;
    public static final int PERMANENT_MSG = 1;
    public static final String SENSING_TYPE_BEHAVIOR = "行为感知";
    public static final String SENSING_TYPE_ENTERTAINMENT = "娱乐感知";
    public static final String SENSING_TYPE_LIFE = "生活感知";
    public static final String SENSING_TYPE_NATURAL = "环境感知";
    public static final String SENSING_TYPE_ROAD = "路况感知";
    public static final String SENSING_TYPE_SOCIAL = "社交感知";
    public static final String SENSING_TYPE_SYSTEM = "系统感知";
    private String backgroundUrl;
    private List<MsgButton> buttons;
    private String callback;
    private boolean cancelFeedbackSound;
    private int comingSoundType;
    private String conditions;
    private int executeDelayTime;
    private int executeType;
    private boolean neverShow = false;
    private int permanent;
    private List<MsgPic> pics;
    private String sensingType;
    private List<String> titles;
    private String tts;
    private int type;
    private long validTime;

    public int getType() {
        return this.type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public List<String> getTitles() {
        return this.titles;
    }

    public void setTitles(List<String> titles) {
        this.titles = titles;
    }

    public List<MsgButton> getButtons() {
        return this.buttons;
    }

    public void setButtons(List<MsgButton> buttons) {
        this.buttons = buttons;
    }

    public List<MsgPic> getPics() {
        return this.pics;
    }

    public void setPics(List<MsgPic> pics) {
        this.pics = pics;
    }

    public int getPermanent() {
        return this.permanent;
    }

    public void setPermanent(int permanent) {
        this.permanent = permanent;
    }

    public String getTts() {
        return this.tts;
    }

    public void setTts(String tts) {
        this.tts = tts;
    }

    public long getValidTime() {
        return this.validTime;
    }

    public void setValidTime(long validTime) {
        this.validTime = validTime;
    }

    public String getCallback() {
        return this.callback;
    }

    public void setCallback(String callback) {
        this.callback = callback;
    }

    public String getSensingType() {
        return this.sensingType;
    }

    public void setSensingType(String sensingType) {
        this.sensingType = sensingType;
    }

    public int getComingSoundType() {
        return this.comingSoundType;
    }

    public void setComingSoundType(int comingSoundType) {
        this.comingSoundType = comingSoundType;
    }

    public int getExecuteType() {
        return this.executeType;
    }

    public void setExecuteType(int executeType) {
        this.executeType = executeType;
    }

    public int getExecuteDelayTime() {
        return this.executeDelayTime;
    }

    public void setExecuteDelayTime(int executeDelayTime) {
        this.executeDelayTime = executeDelayTime;
    }

    public boolean isCancelFeedbackSound() {
        return this.cancelFeedbackSound;
    }

    public void setCancelFeedbackSound(boolean cancelFeedbackSound) {
        this.cancelFeedbackSound = cancelFeedbackSound;
    }

    public boolean isNeverShow() {
        return this.neverShow;
    }

    public void setNeverShow(boolean neverShow) {
        this.neverShow = neverShow;
    }

    public String getConditions() {
        return this.conditions;
    }

    public void setConditions(String conditions) {
        this.conditions = conditions;
    }

    public String getBackgroundUrl() {
        return this.backgroundUrl;
    }

    public void setBackgroundUrl(String backgroundUrl) {
        this.backgroundUrl = backgroundUrl;
    }

    public MsgButton getButton(int index) {
        if (getButtons() != null && getButtons().size() > index) {
            return getButtons().get(index);
        }
        return null;
    }

    /* loaded from: classes5.dex */
    public static class MsgButton {
        String content;
        String name;
        String notResponseWord;
        String pack;
        String responseGuideTTS;
        String responseTTS;
        String responseWord;
        boolean speechResponse;

        public static MsgButton create(String name, String pack, String content) {
            return create(name, pack, content, false);
        }

        public static MsgButton create(String name, String pack, String content, boolean speechResponse) {
            MsgButton button = new MsgButton();
            button.name = name;
            button.pack = pack;
            button.content = content;
            button.speechResponse = speechResponse;
            return button;
        }

        public String getName() {
            return this.name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPack() {
            return this.pack;
        }

        public void setPack(String pack) {
            this.pack = pack;
        }

        public String getContent() {
            return this.content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public boolean isSpeechResponse() {
            return this.speechResponse;
        }

        public void setSpeechResponse(boolean speechResponse) {
            this.speechResponse = speechResponse;
        }

        public String getResponseWord() {
            return this.responseWord;
        }

        public void setResponseWord(String responseWord) {
            this.responseWord = responseWord;
        }

        public String getNotResponseWord() {
            return this.notResponseWord;
        }

        public void setNotResponseWord(String notResponseWord) {
            this.notResponseWord = notResponseWord;
        }

        public String getResponseTTS() {
            return this.responseTTS;
        }

        public void setResponseTTS(String responseTTS) {
            this.responseTTS = responseTTS;
        }

        public String getResponseGuideTTS() {
            return this.responseGuideTTS;
        }

        public void setResponseGuideTTS(String responseGuideTTS) {
            this.responseGuideTTS = responseGuideTTS;
        }
    }

    /* loaded from: classes5.dex */
    public static class MsgPic {
        String content;
        String pack;
        String url;

        public static MsgPic createPic(String url, String pack, String content) {
            MsgPic msgPic = new MsgPic();
            msgPic.setUrl(url);
            msgPic.setPack(pack);
            msgPic.setContent(content);
            return msgPic;
        }

        public String getUrl() {
            return this.url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getPack() {
            return this.pack;
        }

        public void setPack(String pack) {
            this.pack = pack;
        }

        public String getContent() {
            return this.content;
        }

        public void setContent(String content) {
            this.content = content;
        }
    }

    public static MessageContentBean createContent() {
        return new MessageContentBean();
    }

    public MessageContentBean addTitle(String title) {
        if (this.titles == null) {
            this.titles = new ArrayList();
        }
        this.titles.add(title);
        return this;
    }

    public MessageContentBean addPic(MsgPic pic) {
        if (this.pics == null) {
            this.pics = new ArrayList();
        }
        this.pics.add(pic);
        return this;
    }

    public MessageContentBean addButton(MsgButton button) {
        if (this.buttons == null) {
            this.buttons = new ArrayList();
        }
        this.buttons.add(button);
        return this;
    }
}
