package com.example.hasee.abapp.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.hasee.abapp.R;
import com.example.hasee.abapp.bean.ClassGroupBean;
import com.example.hasee.abapp.bean.ProcessWorkerBean;
import com.example.hasee.abapp.bean.WorkAllBean;

import java.util.List;

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
        if(view==null)view = mInflater.inflate(R.layout.item_work_list,viewGroup,false);
        LinearLayout llProgress  = ViewHolder.get(view,R.id.ll_progress);
        LinearLayout llWorkerInfo  = ViewHolder.get(view,R.id.ll_workerInfo);
        List<ClassGroupBean> classGroupBeanList = mList.get(i).classGroupBeanList;
        List<ProcessWorkerBean> processWorkerBeanList = mList.get(i).processWorkerBeanList;
        for(ProcessWorkerBean progressBean:processWorkerBeanList) {
            TextView tvProgressName = new TextView(mContext);

            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            tvProgressName.setLayoutParams(layoutParams);
            tvProgressName.setTextColor(Color.BLACK);
            tvProgressName.setTextSize(20);
            tvProgressName.setBackgroundColor(Color.YELLOW);
            tvProgressName.setText(progressBean.SPARTNAME);

            llProgress.addView(tvProgressName);
            LinearLayout linearLayoutWorkers=new LinearLayout(mContext);
            LinearLayout.LayoutParams layoutNameParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            linearLayoutWorkers.setLayoutParams(layoutNameParams);

            TextView tvWorkersName = new TextView(mContext);
            tvWorkersName.setTextColor(Color.BLACK);
            tvWorkersName.setTextSize(20);
            tvWorkersName.setBackgroundColor(Color.GREEN);
//           if( processWorkerBeanList.get(i).SPARTNAME.equals("卷下摆")){
//               tvWorkersName.setText(progressBean.SEMPLOYEENAMECN);
//               linearLayoutWorkers.addView(tvWorkersName);
//            }
            tvWorkersName.setText(progressBean.SEMPLOYEENAMECN);
            linearLayoutWorkers.addView(tvWorkersName);
            llProgress.addView(linearLayoutWorkers);


        }
//        for(ClassGroupBean classGroupBean:classGroupBeanList) {
//            TextView tvclassGroupName = new TextView(mContext);
//
//            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
//            tvclassGroupName.setLayoutParams(layoutParams);
//            tvclassGroupName.setTextColor(Color.BLACK);
//            tvclassGroupName.setTextSize(20);
//            tvclassGroupName.setBackgroundColor(Color.YELLOW);
//            tvclassGroupName.setText(classGroupBean.SEMPLOYEENAMECN);
//
//            llWorkerInfo.addView(tvclassGroupName);
//
//
//        }
        return view;

    }
}
