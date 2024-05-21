package com.xiaopeng.logictree.handler;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import com.xiaopeng.logictree.R;
import com.xiaopeng.xui.widget.XButton;
/* loaded from: classes5.dex */
public class InterActiveAdapter extends BaseAdapter {
    private LogicActionCallback mCallBack;
    private String[] mInterActiveResult;

    public InterActiveAdapter(String[] interActiveResult, LogicActionCallback callback) {
        this.mInterActiveResult = interActiveResult;
        this.mCallBack = callback;
    }

    @Override // android.widget.Adapter
    public int getCount() {
        String[] strArr = this.mInterActiveResult;
        if (strArr == null) {
            return 0;
        }
        int count = strArr.length;
        return count;
    }

    @Override // android.widget.Adapter
    public String getItem(int position) {
        String[] strArr = this.mInterActiveResult;
        if (strArr == null) {
            return null;
        }
        String res = strArr[position];
        return res;
    }

    @Override // android.widget.Adapter
    public long getItemId(int position) {
        return position;
    }

    @Override // android.widget.Adapter
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder vh;
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_button, parent, false);
            vh = new ViewHolder();
            vh.mButton = (XButton) convertView.findViewById(R.id.item_button);
            convertView.setTag(vh);
        } else {
            vh = (ViewHolder) convertView.getTag();
        }
        vh.mButton.setText(getItem(position));
        vh.mButton.setOnClickListener(new View.OnClickListener() { // from class: com.xiaopeng.logictree.handler.-$$Lambda$InterActiveAdapter$gqws_yNvetkVEZuWMjG6a_SM4-M
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                InterActiveAdapter.this.lambda$getView$0$InterActiveAdapter(position, view);
            }
        });
        return convertView;
    }

    public /* synthetic */ void lambda$getView$0$InterActiveAdapter(int position, View v) {
        LogicActionCallback logicActionCallback = this.mCallBack;
        if (logicActionCallback != null) {
            logicActionCallback.onResult(getItem(position));
        }
    }

    /* loaded from: classes5.dex */
    private class ViewHolder {
        XButton mButton;

        private ViewHolder() {
        }
    }
}
