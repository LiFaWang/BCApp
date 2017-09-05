package com.example.hasee.abapp.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.hasee.abapp.R;
import com.example.hasee.abapp.bean.ClassGroupBean;
import com.example.hasee.abapp.utils.Base64BitmapUtils;

import java.util.List;

import huansi.net.qianjingapp.adapter.HsBaseAdapter;
import huansi.net.qianjingapp.utils.ViewHolder;

/**
 * Created by Tony on 2017/8/28.
 * 11:33
 */

public class GroupAdapter extends HsBaseAdapter<ClassGroupBean> {
    public GroupAdapter(List<ClassGroupBean> list, Context context) {
        super(list, context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) convertView = mInflater.inflate(R.layout.grouplist, parent, false);
        TextView tvWorkerName = ViewHolder.get(convertView, R.id.tv_worker_name);
        TextView tvWorkerNO = ViewHolder.get(convertView, R.id.tv_worker_no);
        TextView tvWorkerType = ViewHolder.get(convertView, R.id.tv_worker_type);
        ImageView ivHeadImg = ViewHolder.get(convertView, R.id.iv_head_img);
        tvWorkerName.setText("姓名:" + mList.get(position).SEMPLOYEENAMECN+"("+mList.get(position).SWORKTEAMNAME+")");
        tvWorkerNO.setText("工号:" + mList.get(position).SEMPLOYEENO);
        tvWorkerType.setText("工种:" + mList.get(position).SWORKTYPE);
        Glide.with(mContext)
                .load(Base64BitmapUtils.base64ToBitmap(mList.get(position).GPIC))
                .placeholder(R.mipmap.ic_launcher) //设置占位图
                .error(R.mipmap.wuyanzu)//设置错误图片
                .into(ivHeadImg);


        if (mList.get(position).isSelected) {
            convertView.setBackgroundColor(Color.GREEN);
        } else {
            convertView.setBackgroundColor(Color.TRANSPARENT);
        }
        return convertView;
    }


}
