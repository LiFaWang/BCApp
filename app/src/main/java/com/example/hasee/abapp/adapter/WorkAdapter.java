package com.example.hasee.abapp.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.hasee.abapp.R;
import com.example.hasee.abapp.bean.WorkGroupBean;

import java.util.List;

import huansi.net.qianjingapp.adapter.HsBaseAdapter;
import huansi.net.qianjingapp.utils.ViewHolder;

/**
 * Created by Tony on 2017/8/23.
 * 21:54
 */

public class WorkAdapter extends HsBaseAdapter<WorkGroupBean.ProgressBean> {
    public WorkAdapter(List<WorkGroupBean.ProgressBean> list, Context context) {
        super(list, context);
        System.out.println(list);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) convertView = mInflater.inflate(R.layout.item_work_list, parent, false);
        TextView tvProgressName = ViewHolder.get(convertView, R.id.tv_progress_name);
        LinearLayout llWorkerNameContainer = ViewHolder.get(convertView, R.id.ll_worker_name_container);
        tvProgressName.setText(mList.get(position).progressName);
        List<String> workerNames = mList.get(position).workerNames;
        llWorkerNameContainer.removeAllViews();
        for (String workerName : workerNames) {
            System.out.println(position+workerName);
            TextView tv = new TextView(mContext);
            tv.setTextColor(Color.BLACK);
            tv.setTextSize(20);
            tv.setText(workerName);
            tv.setPadding(0,5,5,5);
            llWorkerNameContainer.addView(tv);
        }
        return convertView;
    }


//    @Override
//    public View getView(int i, View view, ViewGroup viewGroup) {
//        if (view == null) view = mInflater.inflate(R.layout.item_work_list, viewGroup, false);
//        LinearLayout llProgress = ViewHolder.get(view, R.id.llWorkersName);
//        llProgress.removeAllViews();
//        View v = mInflater.inflate(R.layout.item_work_list, null);
//        String progressName = mList.get(i).progressName;
//        List<String> workerNames = mList.get(i).workerNames;
//        TextView tvProgressName = (TextView) v.findViewById(R.id.tvProgressName);
//        tvProgressName.setTextSize(22);
//        tvProgressName.setPadding(10,10,0,10);
//        tvProgressName.setTextColor(Color.BLACK);
//        tvProgressName.setBackgroundColor(Color.GREEN);
//        tvProgressName.setText(progressName);
//        System.out.println("tvProgressName:"+tvProgressName.toString());
//        LinearLayout llWorkersName = (LinearLayout) v.findViewById( R.id.llWorkersName);
//        LinearLayout. LayoutParams param = new LinearLayout.LayoutParams(
//                LinearLayout.LayoutParams.MATCH_PARENT,
//                LinearLayout.LayoutParams.MATCH_PARENT, 1.0f);
//        for (String str:workerNames ) {
//            TextView workerName =new TextView(mContext);
//            workerName.setTextSize(22);
//            workerName.setPadding(10,10,10,10);
//            workerName.setTextColor(Color.GREEN);
//            workerName.setText(str);
//            workerName.setLayoutParams(param);
//            llWorkersName.addView(workerName);
//        }
//        llProgress.addView(v);
////        llWorkerInfo.removeAllViews();
////        List<ClassGroupBean> classGroupBeanList = mList.get(i).classGroupBeanList;
////        List<ProcessWorkerBean> processWorkerBeanList = mList.get(i).processWorkerBeanList;
////        Map<String,List<ProcessWorkerBean>> progressWorkerMap=new HashMap<>();
////        for(int j=0;j<processWorkerBeanList.size();j++){
////            ProcessWorkerBean processWorkerBean=processWorkerBeanList.get(j);
////            List<ProcessWorkerBean> subList=progressWorkerMap.get(processWorkerBean.SPARTNAME);
////            if(subList==null) subList=new ArrayList<>();
////            subList.add(processWorkerBean);
////            progressWorkerMap.put(processWorkerBean.SPARTNAME,subList);
////        }
//////        Iterator<Map.Entry<String,List<ProcessWorkerBean>>> it=progressWorkerMap.entrySet().iterator();
//////        List<List<ProcessWorkerBean>> partList=new ArrayList<>();//工序的数组
//////        while (it.hasNext()){
//////            Map.Entry<String,List<ProcessWorkerBean>> entry=it.next();
//////            List<ProcessWorkerBean> subList=entry.getValue();
//////            partList.add(subList);
//////        }
//////        Collections.sort(partList, new Comparator<List<ProcessWorkerBean>>() {
//////            @Override
//////            public int compare(List<ProcessWorkerBean> t1, List<ProcessWorkerBean> t2) {
//////                try {
//////                    return Integer.parseInt(t1.get(0).ICUPROCEDUREID)-Integer.parseInt(t2.get(0).ICUPROCEDUREID);
//////                }catch (Exception e){
//////                    return 0;
//////                }
//////            }
//////        });
//////        for(int j=0;j<partList.size();j++){
//////            List<ProcessWorkerBean> partSubList=partList.get(j);
//////            TextView tvTitle=new TextView(mContext);
//////            tvTitle.setBackgroundColor(Color.YELLOW);
//////            tvTitle.setTextSize(22);
//////            tvTitle.setPadding(0,10,0,10);
//////            tvTitle.setText(partSubList.get(0).SPARTNAME);
//////            llProgress.addView(tvTitle);
//////            tvTitle.setTextColor(Color.BLACK);
//////            LinearLayout layout=new LinearLayout(mContext);
//////            layout.setOrientation(LinearLayout.VERTICAL);
//////            List<LinearLayout> layoutList=new ArrayList<>();
//////            for(int k=0;k<partSubList.size();k++){
//////                LinearLayout subLayout;
//////                if(i%2==0){
//////                    subLayout=new LinearLayout(mContext);
//////                }else {
//////                    subLayout=layoutList.get(i/2);
//////                }
//////                subLayout.setOrientation(LinearLayout.HORIZONTAL);
//////                TextView tvWorkName=new TextView(mContext);
//////                tvWorkName.setTextSize(20);
//////                tvWorkName.setPadding(0,10,0,10);
//////                tvWorkName.setTextColor(Color.BLACK);
//////                tvWorkName.setText(partSubList.get(k).SEMPLOYEENAMECN);
//////                tvWorkName.setLayoutParams(new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT,1));
//////                subLayout.addView(tvWorkName);
//////                if(i%2==0){
//////                   layoutList.add(subLayout);
//////                }else {
//////                    layoutList.set(i/2,subLayout);
//////                }
//////            }
//////            for(int k=0;k<layoutList.size();k++){
//////                layout.addView(layoutList.get(k));
//////            }
//////            llProgress.addView(layout);
//////        }
//
//        return view;
//    }

}
