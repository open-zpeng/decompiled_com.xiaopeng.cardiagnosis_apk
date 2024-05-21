package com.xiaopeng.lib.apirouter.server;

import android.os.IBinder;
import android.util.Log;
import android.util.Pair;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
/* loaded from: classes4.dex */
class AutoCodeMatcher {
    private static HashMap<String, Pair<IBinder, String>> mapping;
    private static List<IManifestHandler> sManifestHandlerList = new LinkedList();
    private static Object sLock = new Object();

    public Pair<IBinder, String> match(String service) {
        if (mapping == null) {
            mapping = ManifestHelperMapping.reflectMapping();
            initManifestHandler();
        }
        HashMap<String, Pair<IBinder, String>> hashMap = mapping;
        Pair<IBinder, String> pair = hashMap == null ? null : hashMap.get(service);
        return pair == null ? new Pair<>(null, null) : pair;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static void addManifestHandler(IManifestHandler manifestHandler) {
        synchronized (sLock) {
            if (!sManifestHandlerList.contains(manifestHandler)) {
                sManifestHandlerList.add(manifestHandler);
            }
        }
    }

    private void initManifestHandler() {
        synchronized (sLock) {
            if (!sManifestHandlerList.isEmpty()) {
                for (IManifestHandler manifestHandler : sManifestHandlerList) {
                    initManifestHandler(manifestHandler);
                }
            }
        }
    }

    private void initManifestHandler(IManifestHandler manifestHandler) {
        IManifestHelper[] manifestHelpers;
        if (manifestHandler == null || (manifestHelpers = manifestHandler.getManifestHelpers()) == null || manifestHelpers.length == 0) {
            return;
        }
        if (mapping == null) {
            mapping = new HashMap<>();
        }
        HashMap<String, Pair<IBinder, String>> currentMapping = mapping;
        for (IManifestHelper manifestHelper : manifestHelpers) {
            try {
                HashMap<String, Pair<IBinder, String>> mapping2 = manifestHelper.getMapping();
                if (mapping2 != null && !mapping2.isEmpty()) {
                    currentMapping.putAll(mapping2);
                }
            } catch (Exception e) {
                Log.e("AutoCodeMatcher", "initManifestHandler:" + manifestHelper.getClass(), e);
            }
        }
    }
}
