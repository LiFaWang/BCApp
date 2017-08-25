package com.example.hasee.abapp.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.hasee.abapp.R;
import com.example.hasee.abapp.bean.RealTimeProcessBarBean;

import java.util.List;

import huansi.net.qianjingapp.adapter.HsBaseAdapter;
import huansi.net.qianjingapp.utils.ViewHolder;

/**
 * Created by Tony on 2017/8/24.
 * 15:32
 */

public class RealTimeProcessAdapter extends HsBaseAdapter<RealTimeProcessBarBean> {
    public RealTimeProcessAdapter(List<RealTimeProcessBarBean> list, Context context) {
        super(list, context);
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if(view==null)view = mInflater.inflate(R.layout.item_home_adapter,viewGroup,false);
        TextView tv1  = ViewHolder.get(view,R.id.listFactory);
        TextView tv2 = ViewHolder.get(view,R.id.listArea);
        TextView tv3  = ViewHolder.get(view,R.id.listDVNum);
        tv1.setText(mList.get(i).SPROCEDURENAME);
        tv2.setText(mList.get(i).IQTY);
        tv3.setText(mList.get(i).IPROCEDURESEQ);
        return view;

    }
}
