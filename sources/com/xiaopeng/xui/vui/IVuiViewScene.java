package com.xiaopeng.xui.vui;

import com.xiaopeng.vui.commons.IVuiElementListener;
import com.xiaopeng.vui.commons.IVuiEngine;
import java.util.List;
/* loaded from: classes5.dex */
public interface IVuiViewScene {
    void initVuiScene(String str, IVuiEngine iVuiEngine);

    void setCustomViewIdList(List<Integer> list);

    void setVuiElementListener(IVuiElementListener iVuiElementListener);

    void setVuiEngine(IVuiEngine iVuiEngine);

    void setVuiSceneId(String str);
}
