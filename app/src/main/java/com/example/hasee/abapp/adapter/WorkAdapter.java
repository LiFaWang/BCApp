package com.example.hasee.abapp.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.hasee.abapp.R;
import com.example.hasee.abapp.bean.ProcessWorkerBean;
import com.example.hasee.abapp.bean.WorkAllBean;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import huansi.net.qianjingapp.adapter.HsBaseAdapter;
import huansi.net.qianjingapp.utils.ViewHolder;

/**
 * Created by Tony on 2017/8/23.
 * 21:54
 */

public class WorkAdapter extends HsBaseAdapter<WorkAllBean> {
    public WorkAdapter(List<WorkAllBean> list, Context context) {
        super(list, context);
    }



    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null) view = mInflater.inflate(R.layout.item_work_list, viewGroup, false);
        LinearLayout llProgress = ViewHolder.get(view, R.id.ll_progress);
//        LinearLayout llWorkerInfo = ViewHolder.get(view, R.id.ll_workerInfo);
        llProgress.removeAllViews();
//        llWorkerInfo.removeAllViews();
//        List<ClassGroupBean> classGroupBeanList = mList.get(i).classGroupBeanList;
        List<ProcessWorkerBean> processWorkerBeanList = mList.get(i).processWorkerBeanList;
        Map<String,List<ProcessWorkerBean>> progressWorkerMap=new HashMap<>();
        for(int j=0;j<processWorkerBeanList.size();j++){
            ProcessWorkerBean processWorkerBean=processWorkerBeanList.get(j);
            List<ProcessWorkerBean> subList=progressWorkerMap.get(processWorkerBean.SPARTNAME);
            if(subList==null) subList=new ArrayList<>();
            subList.add(processWorkerBean);
            progressWorkerMap.put(processWorkerBean.SPARTNAME,subList);
        }
        Iterator<Map.Entry<String,List<ProcessWorkerBean>>> it=progressWorkerMap.entrySet().iterator();
        List<List<ProcessWorkerBean>> partList=new ArrayList<>();//工序的数组
        while (it.hasNext()){
            Map.Entry<String,List<ProcessWorkerBean>> entry=it.next();
            List<ProcessWorkerBean> subList=entry.getValue();
            partList.add(subList);
        }
        Collections.sort(partList, new Comparator<List<ProcessWorkerBean>>() {
            @Override
            public int compare(List<ProcessWorkerBean> t1, List<ProcessWorkerBean> t2) {
                try {
                    return Integer.parseInt(t1.get(0).ICUPROCEDUREID)-Integer.parseInt(t2.get(0).ICUPROCEDUREID);
                }catch (Exception e){
                    return 0;
                }
            }
        });
        for(int j=0;j<partList.size();j++){
            List<ProcessWorkerBean> partSubList=partList.get(j);
            TextView tvTitle=new TextView(mContext);
            tvTitle.setBackgroundColor(Color.YELLOW);
            tvTitle.setTextSize(22);
            tvTitle.setPadding(0,10,0,10);
            tvTitle.setText(partSubList.get(0).SPARTNAME);
            llProgress.addView(tvTitle);
            tvTitle.setTextColor(Color.BLACK);
            LinearLayout layout=new LinearLayout(mContext);
            layout.setOrientation(LinearLayout.VERTICAL);
            List<LinearLayout> layoutList=new ArrayList<>();
            for(int k=0;k<partSubList.size();k++){
                LinearLayout subLayout;
                if(i%2==0){
                    subLayout=new LinearLayout(mContext);
                }else {
                    subLayout=layoutList.get(i/2);
                }
                subLayout.setOrientation(LinearLayout.HORIZONTAL);
                TextView tvWorkName=new TextView(mContext);
                tvWorkName.setTextSize(20);
                tvWorkName.setPadding(0,10,0,10);
                tvWorkName.setTextColor(Color.BLACK);
                tvWorkName.setText(partSubList.get(k).SEMPLOYEENAMECN);
                tvWorkName.setLayoutParams(new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT,1));
                subLayout.addView(tvWorkName);
                if(i%2==0){
                   layoutList.add(subLayout);
                }else {
                    layoutList.set(i/2,subLayout);
                }
            }
            for(int k=0;k<layoutList.size();k++){
                layout.addView(layoutList.get(k));
            }
            llProgress.addView(layout);
        }

//        for(int j=0;j<classGroupBeanList.size();j++){
//            ClassGroupBean classGroupBean=classGroupBeanList.get(j);
//            View v=mInflater.inflate(R.layout.grouplist,null);
//            ImageView ivHead=v.findViewById(R.id.ivHead);
//            TextView tvGroup = v.findViewById(R.id.tvGroup);
//            TextView gv1=v.findViewById(R.id.gv1);
//            TextView gv2=v.findViewById(R.id.gv2);
//            TextView gv3=v.findViewById(R.id.gv3);
////            Glide.with(mContext)
////                    .load
//            gv1.setText("工号:"+classGroupBean.SEMPLOYEENO);
//            gv2.setText("姓名:"+classGroupBean.SEMPLOYEENAMECN);
//            gv3.setText("工种:"+classGroupBean.SWORKTYPE);
//            tvGroup.setText(classGroupBean.SWORKTEAMNAME);
//            llWorkerInfo.addView(v);
//        }
        return view;
    }

}
