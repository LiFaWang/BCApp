package com.example.hasee.abapp.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.hasee.abapp.R;
import com.example.hasee.abapp.bean.ClassGroupBean;
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
//        String[] str = new String[]{"流水线1", "流水线2", "流水线3", "流水线4", "流水线5"};
        if (view == null) view = mInflater.inflate(R.layout.item_work_list, viewGroup, false);
        LinearLayout llProgress = ViewHolder.get(view, R.id.ll_progress);
        LinearLayout llWorkerInfo = ViewHolder.get(view, R.id.ll_workerInfo);
        llProgress.removeAllViews();
        llWorkerInfo.removeAllViews();
        List<ClassGroupBean> classGroupBeanList = mList.get(i).classGroupBeanList;
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
            tvTitle.setText(partSubList.get(0).SPARTNAME);
            llProgress.addView(tvTitle);
            tvTitle.setTextColor(Color.RED);
            for(int k=0;k<partSubList.size();k++){
                TextView tvWorkName=new TextView(mContext);
                tvWorkName.setText(partSubList.get(k).SWORKTEAMNAME);
                llProgress.addView(tvWorkName);
            }
        }
        for(int j=0;j<classGroupBeanList.size();j++){
            ClassGroupBean classGroupBean=classGroupBeanList.get(j);
            View v=mInflater.inflate(R.layout.grouplist,null);
            ImageView ivHead=v.findViewById(R.id.ivHead);
            TextView gv1=v.findViewById(R.id.gv1);
            TextView gv2=v.findViewById(R.id.gv2);
            TextView gv3=v.findViewById(R.id.gv3);
//            Glide.with(mContext)
//                    .load
            gv1.setText(classGroupBean.SEMPLOYEENO);
            gv2.setText(classGroupBean.SEMPLOYEENAMECN);
            gv3.setText(classGroupBean.SWORKTYPE);
            llWorkerInfo.addView(v);
        }




//        List<ProcessWorkerBean> filterProcessWorkerBeen = new ArrayList<>();
//        for (int j = 0; j < processWorkerBeanList.size(); j++) {
//            if (processWorkerBeanList.get(j).SWORKTEAMNAME.equals(str[0])) {
//                filterProcessWorkerBeen.add(processWorkerBeanList.get(j));
//            }
//
//        }
//
//        for (ProcessWorkerBean progressBean : filterProcessWorkerBeen) {
//            TextView tvProgressName = new TextView(mContext);
//            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
//            tvProgressName.setLayoutParams(layoutParams);
//            tvProgressName.setTextColor(Color.BLACK);
//            tvProgressName.setTextSize(20);
//            tvProgressName.setBackgroundColor(Color.YELLOW);
//            tvProgressName.setText(progressBean.SPARTNAME);
//
//            llProgress.addView(tvProgressName);
//            LinearLayout linearLayoutWorkers = new LinearLayout(mContext);
//            LinearLayout.LayoutParams layoutNameParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
//                    LinearLayout.LayoutParams.WRAP_CONTENT);
//            linearLayoutWorkers.setLayoutParams(layoutNameParams);
//
//            TextView tvWorkersName = new TextView(mContext);
//            tvWorkersName.setTextColor(Color.BLACK);
//            tvWorkersName.setTextSize(20);
//            tvWorkersName.setBackgroundColor(Color.GREEN);
////           if( processWorkerBeanList.get(i).SPARTNAME.equals("卷下摆")){
////               tvWorkersName.setText(progressBean.SEMPLOYEENAMECN);
////               linearLayoutWorkers.addView(tvWorkersName);
////            }
//
//            tvWorkersName.setText(progressBean.SEMPLOYEENAMECN);
//            linearLayoutWorkers.addView(tvWorkersName);
//            llProgress.addView(linearLayoutWorkers);
//
//        }
//        View v = mInflater.inflate(R.layout.grouplist, llWorkerInfo);
//        TextView workerNub = v.findViewById(R.id.gv1);
//        TextView workerNam = v.findViewById(R.id.gv2);
//        TextView workerTyp = v.findViewById(R.id.gv3);
//        ImageView workerPic = v.findViewById(R.id.ivHead);
//
//        for (ClassGroupBean classGroupBean : classGroupBeanList) {
//
////            TextView tvclassGroupName = new TextView(mContext);
//
////            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
////            tvclassGroupName.setLayoutParams(layoutParams);
////            tvclassGroupName.setTextColor(Color.BLACK);
////            tvclassGroupName.setTextSize(20);
////            tvclassGroupName.setBackgroundColor(Color.YELLOW);
//            workerNub.setText(classGroupBean.SEMPLOYEENO);
//            workerNam.setText(classGroupBean.SEMPLOYEENAMECN);
//            workerTyp.setText(classGroupBean.SEMPLOYEENAMECN);
//            workerPic.setImageResource(R.drawable.ic_launcher);
////            llWorkerInfo.addView(workerNub);
////            llWorkerInfo.addView(workerNam);
////            llWorkerInfo.addView(workerTyp);
////            llWorkerInfo.addView(workerPic);
//        }
        return view;

    }
}
