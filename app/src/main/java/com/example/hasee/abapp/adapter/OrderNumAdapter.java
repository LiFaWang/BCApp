package com.example.hasee.abapp.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.hasee.abapp.R;
import com.example.hasee.abapp.bean.TopDataBean;

import java.util.List;

import huansi.net.qianjingapp.adapter.HsBaseAdapter;
import huansi.net.qianjingapp.utils.ViewHolder;

/**
 * Created by Administrator on 2017/7/10 0010.
 */

public class OrderNumAdapter extends HsBaseAdapter<TopDataBean> {
    public OrderNumAdapter(List<TopDataBean> list, Context context) {
        super(list, context);
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if(view==null){
            view = mInflater.inflate(R.layout.item_ordernum_list,viewGroup,false);
        }
        TextView tv  = ViewHolder.get(view,R.id.text);
        tv.setText(mList.get(i).SORDERNO);
        return view;
    }
}
