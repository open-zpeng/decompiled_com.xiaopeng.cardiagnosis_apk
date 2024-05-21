package com.xiaopeng.commonfunc.model.diagnosis;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.xiaopeng.commonfunc.Constant;
import com.xiaopeng.commonfunc.bean.aftersales.DiagnosisData;
import com.xiaopeng.commonfunc.bean.aftersales.DiagnosisErrorList;
import com.xiaopeng.commonfunc.utils.FileUtil;
import com.xiaopeng.lib.security.xmartv1.XmartV1Constants;
import com.xiaopeng.lib.utils.LogUtils;
import com.xiaopeng.xmlconfig.Support;
import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;
import org.apache.commons.net.ntp.NtpV3Packet;
/* loaded from: classes4.dex */
public class DiagnosisCodeModel {
    private static final int COLUMN_INDEX_ERROR_CODE = 0;
    private static final int COLUMN_INDEX_ERROR_MSG = 2;
    private static final int COLUMN_INDEX_TIME = 1;
    private static final int COLUMN_INDEX_VERSION = 3;
    private static final String DIAGNOSIS_ORDER_DESC_BY_ID = "ID DESC";
    private static final String PATH_ERRORCODE_JSON = "diagnosis_name.json";
    private static final String TAG = "DiagnosisCodeModel";
    private Context mContext;
    private List<DiagnosisErrorList> mDataList;
    private String[] mDiagnosisModuleNames;
    private SQLiteDatabase mSQLiteDatabase;
    private static final String PATH_DIAGNOSIS_DB = Support.Path.getFilePath(Support.Path.PATH_DIAGNOSIS_DB);
    private static final String[] DIAGNOSIS_SELECT_COLUMN = {"ERRORCODE", NtpV3Packet.TYPE_TIME, "ERRORMSG", "VERSION"};

    public DiagnosisCodeModel(Context context, int resid) {
        this.mContext = context;
        initDb();
        this.mDiagnosisModuleNames = this.mContext.getResources().getStringArray(resid);
    }

    public DiagnosisCodeModel(Context context) {
        this.mContext = context;
        initDb();
    }

    public void initDb() {
        try {
            if (FileUtil.isExistFilePath(PATH_DIAGNOSIS_DB)) {
                this.mSQLiteDatabase = SQLiteDatabase.openDatabase(PATH_DIAGNOSIS_DB, null, 1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<DiagnosisErrorList> getDataList() {
        return this.mDataList;
    }

    public String[] getDiagnosisModuleNames() {
        return this.mDiagnosisModuleNames;
    }

    public String getSelectedModule(int index) {
        return this.mDiagnosisModuleNames[index];
    }

    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:7:0x003a -> B:22:0x004a). Please submit an issue!!! */
    public void initData() {
        InputStream inputStream = null;
        try {
            try {
                try {
                    inputStream = this.mContext.getAssets().open(PATH_ERRORCODE_JSON);
                    int size = inputStream.available();
                    byte[] bytes = new byte[size];
                    inputStream.read(bytes);
                    String string = new String(bytes, XmartV1Constants.UTF8_ENCODING);
                    this.mDataList = (List) new Gson().fromJson(string, new TypeToken<List<DiagnosisErrorList>>() { // from class: com.xiaopeng.commonfunc.model.diagnosis.DiagnosisCodeModel.1
                    }.getType());
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } catch (IOException e2) {
                e2.printStackTrace();
                if (inputStream != null) {
                    inputStream.close();
                }
            }
        } catch (Throwable th) {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e3) {
                    e3.printStackTrace();
                }
            }
            throw th;
        }
    }

    public void getDiagnosisDataFromDB() {
        if (this.mSQLiteDatabase == null) {
            LogUtils.e(TAG, "mSQLiteDatabase is null");
            return;
        }
        for (int i = 0; i < Constant.DIAGNOSIS_SQL_TBS.length; i++) {
            this.mDataList.get(i).clearDiagnosisData();
            Cursor cursor = this.mSQLiteDatabase.query(Constant.DIAGNOSIS_SQL_TBS[i], DIAGNOSIS_SELECT_COLUMN, null, null, null, null, DIAGNOSIS_ORDER_DESC_BY_ID);
            try {
                int count = cursor.getCount();
                if (count > 0) {
                    while (cursor.moveToNext()) {
                        DiagnosisData data = new DiagnosisData(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getString(3));
                        this.mDataList.get(i).addDiagnosisData(data);
                    }
                }
                cursor.close();
            } catch (Throwable th) {
                cursor.close();
                throw th;
            }
        }
    }

    public List<Integer> getDiagnosisBetweenTime(String module, long startTime, long endTime) {
        List<Integer> errorCodeList = new LinkedList<>();
        if (this.mSQLiteDatabase == null) {
            LogUtils.e(TAG, "mSQLiteDatabase is null");
        } else {
            String[] whereArgs = {String.valueOf(startTime), String.valueOf(endTime)};
            Cursor cursor = this.mSQLiteDatabase.query(module, new String[]{"ERRORCODE"}, "TIMESTAMP between ? and ?", whereArgs, null, null, DIAGNOSIS_ORDER_DESC_BY_ID);
            try {
                int count = cursor.getCount();
                if (count > 0) {
                    while (cursor.moveToNext()) {
                        int errorcode = cursor.getInt(0);
                        if (!errorCodeList.contains(Integer.valueOf(errorcode))) {
                            errorCodeList.add(Integer.valueOf(errorcode));
                        }
                    }
                }
            } finally {
                cursor.close();
            }
        }
        return errorCodeList;
    }

    public List<DiagnosisData> getDiagnosisDataBetweenTime(String module, long startTime, long endTime) {
        List<DiagnosisData> errorCodeList = new LinkedList<>();
        if (this.mSQLiteDatabase == null) {
            LogUtils.e(TAG, "mSQLiteDatabase is null");
        } else {
            String[] whereArgs = {String.valueOf(startTime), String.valueOf(endTime)};
            Cursor cursor = this.mSQLiteDatabase.query(module, DIAGNOSIS_SELECT_COLUMN, "TIMESTAMP between ? and ?", whereArgs, null, null, DIAGNOSIS_ORDER_DESC_BY_ID);
            try {
                int count = cursor.getCount();
                if (count > 0) {
                    while (cursor.moveToNext()) {
                        DiagnosisData data = new DiagnosisData(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getString(3));
                        errorCodeList.add(data);
                    }
                }
            } finally {
                cursor.close();
            }
        }
        return errorCodeList;
    }

    public void onDestroy() {
        SQLiteDatabase sQLiteDatabase = this.mSQLiteDatabase;
        if (sQLiteDatabase != null) {
            sQLiteDatabase.close();
        }
        List<DiagnosisErrorList> list = this.mDataList;
        if (list != null && !list.isEmpty()) {
            this.mDataList.clear();
        }
    }
}
