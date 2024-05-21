package com.xiaopeng.lib.apirouter.server;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Pair;
/* loaded from: classes4.dex */
public class ApiPublisherProvider extends ContentProvider {
    public static Context CONTEXT;
    private AutoCodeMatcher mMatcher;

    @Override // android.content.ContentProvider
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }

    @Override // android.content.ContentProvider
    @Nullable
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Override // android.content.ContentProvider
    @Nullable
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        return null;
    }

    @Override // android.content.ContentProvider
    public boolean onCreate() {
        CONTEXT = getContext();
        this.mMatcher = new AutoCodeMatcher();
        return true;
    }

    @Override // android.content.ContentProvider
    @Nullable
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        return null;
    }

    @Override // android.content.ContentProvider
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }

    @Override // android.content.ContentProvider
    @Nullable
    public Bundle call(@NonNull String service, @Nullable String arg, @Nullable Bundle extras) {
        Pair<IBinder, String> pair = this.mMatcher.match(service);
        Bundle bundle = new Bundle();
        if (pair != null) {
            bundle.putBinder("binder", (IBinder) pair.first);
            bundle.putString("manifest", (String) pair.second);
        }
        return bundle;
    }

    public static void setContext(Context context) {
        CONTEXT = context;
    }

    public static void addManifestHandler(IManifestHandler manifestHandler) {
        AutoCodeMatcher.addManifestHandler(manifestHandler);
    }
}
