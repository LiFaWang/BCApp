package com.example.hasee.abapp.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.hasee.abapp.R;
import com.example.hasee.abapp.bean.ClassGroupBean;
import com.example.hasee.abapp.bean.WorkAllBean;

import java.util.List;

import huansi.net.qianjingapp.adapter.HsBaseAdapter;
import huansi.net.qianjingapp.utils.ViewHolder;

/**
 * Created by Tony on 2017/8/28.
 * 11:33
 */

public class GroupAdapter extends HsBaseAdapter<WorkAllBean> {
    public GroupAdapter(List<WorkAllBean> list, Context context) {
        super(list, context);
    }


    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null) view = mInflater.inflate(R.layout.grouplist, viewGroup, false);
        LinearLayout llWorkerInfo = ViewHolder.get(view, R.id.ll_workerInfo);
        llWorkerInfo.removeAllViews();
        List<ClassGroupBean> classGroupBeanList = mList.get(i).classGroupBeanList;

        for (int j = 0; j < classGroupBeanList.size(); j++) {
            ClassGroupBean classGroupBean = classGroupBeanList.get(j);
            View v = mInflater.inflate(R.layout.grouplist, null);
            ImageView ivHead = v.findViewById(R.id.ivHead);
            TextView tvGroup = v.findViewById(R.id.tvGroup);
            TextView gv1 = v.findViewById(R.id.gv1);
            TextView gv2 = v.findViewById(R.id.gv2);
            TextView gv3 = v.findViewById(R.id.gv3);
//            Glide.with(mContext)
//                    .load
            gv1.setText("工号:" + classGroupBean.SEMPLOYEENO);
            gv2.setText("姓名:" + classGroupBean.SEMPLOYEENAMECN);
            gv3.setText("工种:" + classGroupBean.SWORKTYPE);
            tvGroup.setText(classGroupBean.SWORKTEAMNAME);
            llWorkerInfo.addView(v);
        }
//        for (int j = 0; j < mList.size(); j++) {
//            ClassGroupBean classGroupBean = mList.get(j);
//            System.out.println("classGroupBean:"+classGroupBean.SEMPLOYEENAMECN);
//            View v = mInflater.inflate(R.layout.grouplist, null);
//            ImageView ivHead = v.findViewById(R.id.ivHead);
//            TextView tvGroup = v.findViewById(R.id.tvGroup);
//            TextView gv1 = v.findViewById(R.id.gv1);
//            TextView gv2 = v.findViewById(R.id.gv2);
//            TextView gv3 = v.findViewById(R.id.gv3);
////           Glide.with(mContext)
////                   .load
//            gv1.setText("工号:" + classGroupBean.SEMPLOYEENO);
//            gv2.setText("姓名:" + classGroupBean.SEMPLOYEENAMECN);
//            gv3.setText("工种:" + classGroupBean.SWORKTYPE);
//            tvGroup.setText(classGroupBean.SWORKTEAMNAME);
//            llWorkerInfo.addView(v);

//        }
        return view;

    }
}
