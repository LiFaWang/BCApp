package com.example.hasee.abapp;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;

import com.example.hasee.abapp.adapter.BatchNumAdapter;
import com.example.hasee.abapp.adapter.GroupAdapter;
import com.example.hasee.abapp.adapter.GroupNumAdapter;
import com.example.hasee.abapp.adapter.OrderNumAdapter;
import com.example.hasee.abapp.adapter.StyleNumAdapter;
import com.example.hasee.abapp.adapter.WorkAdapter;
import com.example.hasee.abapp.bean.ClassGroupBean;
import com.example.hasee.abapp.bean.ProcessWorkerBean;
import com.example.hasee.abapp.bean.RealTimeProcessBarBean;
import com.example.hasee.abapp.bean.TopDataBean;
import com.example.hasee.abapp.bean.WorkAllBean;
import com.example.hasee.abapp.bean.WorkGroupBean;
import com.example.hasee.abapp.databinding.ActivityMainBinding;
import com.example.hasee.abapp.utils.MyUtils;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import huansi.net.qianjingapp.base.NotWebBaseActivity;
import huansi.net.qianjingapp.entity.HsWebInfo;
import huansi.net.qianjingapp.entity.WsEntity;
import huansi.net.qianjingapp.imp.SimpleHsWeb;
import huansi.net.qianjingapp.utils.NewRxjavaWebUtils;
import huansi.net.qianjingapp.utils.OthersUtil;
import rx.functions.Func1;

import static huansi.net.qianjingapp.utils.NewRxjavaWebUtils.getJsonData;
import static huansi.net.qianjingapp.utils.WebServices.WebServiceType.CUS_SERVICE;

public class MainActivity extends NotWebBaseActivity {

    private ActivityMainBinding mActivityMainBinding;
    private int[] length;//屏幕长宽;
    private OrderNumAdapter mOrderNumAdapter;//订单号adapter
    private StyleNumAdapter mStyleNumAdapter;//款号adapter
    private BatchNumAdapter mBatchNumAdapter;//批次号adapter
    private GroupNumAdapter mGrouNumAdapter;//批次号adapter
    //    private List<StyleNumBean> mFliterStyleNumList;//筛选款号数据集合
//    private List<TopDataBean> mFliterBatchNumList;//筛选的批次号集合
//    private List<StyleNumBean> listFliterStyleItem;//筛选款号的数据
//    private List<TopDataBean> listFliterBatchItem;//筛选批次号的数据
//    private List<TopDataBean> mBatchNumBeanList;//批次号数据
//    private List<TableHeadBean> mTableHeadBeanList;//订单号数据
//    private List<StyleNumBean> mStyleNumBeanList;//款号数据
    private List<BarDataSet> barChart;//存储BarDataSet
    private List<ProcessWorkerBean> mProcessWorkerBeanList;

    private List<WorkAllBean> mWorkAllBeanList;//工序组和班组整在一起
//    private List<ClassGroupBean> mClassGroupBeanList;

    private XAxis xAxis;//x轴
    private YAxis yAxis;//y轴
    private String xData[];//x轴描述
    //    private List<RealTimeProcessBarBean> data;//实时监控设备数据
    private BarDataSet allPointsName, errorPointsName;//所有点数，异常点数名字
    private ArrayList<BarEntry> allPoints, errorPoints;//所有点数，异常点数
    //    private RealTimeProcessAdapter mRealTimeProcessAdapter;
//    private List<RealTimeProcessBarBean> mGroupBeen;
    private WorkAdapter mWorkAdapter;
    private GroupAdapter mGroupAdapter;
    //    private View mHead;
    private BarChart mBarChart;
//        private List<TopDataBean> mTopDataBeanList;
    private List<RealTimeProcessBarBean> mRealTimeProcessBarBeanList;
    private LinearLayout mLlBalance;
    private List<ClassGroupBean> mClassGroupBeenList;
    //    private List<ProcessWorkerBean> mProcessWorkerBeanList;
    private float mY;
    private float mCurrentY;
    private float mCurrentX;
    private float mX;
    private List<String> mWorkTeamNames;//pop用的组名集合
    private String mCurrentSelectedWorkTeam;
    private List<List<ClassGroupBean>> mClassGroupListByWorkTeamName;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void init() {
        mActivityMainBinding = (ActivityMainBinding) viewDataBinding;
//        mProcessWorkerBeanList=new ArrayList<>();
        mWorkAllBeanList = new ArrayList<>();
//        mTopDataBeanList=new ArrayList<>();
        mRealTimeProcessBarBeanList = new ArrayList<>();
//        mClassGroupBeanList=new ArrayList<>();
//        mFliterStyleNumList = new ArrayList<>(
// );
//        listFliterStyleItem = new ArrayList<>();
//        listFliterBatchItem = new ArrayList<>();
//        mFliterBatchNumList = new ArrayList<>();
//        mBatchNumBeanList = new ArrayList<>();
//        mTableHeadBeanList = new ArrayList<>();
//        mStyleNumBeanList = new ArrayList<>();
//        mProcessWorkerBeanList=new ArrayList<>();
        allPoints = new ArrayList<>();
        errorPoints = new ArrayList<>();
        barChart = new ArrayList<>();

//        mActivityMainBinding.tvOrderNum.setOnClickListener(this);
//        mActivityMainBinding.tvStyleNum.setOnClickListener(this);
//        mActivityMainBinding.tvBatchNum.setOnClickListener(this);
        length = MyUtils.getScreenSize(this);
        initHead();

        initData();
//        mWorkAdapter = new WorkAdapter(mWorkAllBeanList, this);
//        mGroupAdapter = new GroupAdapter(mClassGroupBeenList, this);
//        mGroupAdapter = new GroupAdapter(mWorkAllBeanList, this);
//        mActivityMainBinding.lvWork.setAdapter(mWorkAdapter);
//        mActivityMainBinding.gvGroup.setAdapter(mGroupAdapter);
        mActivityMainBinding.gvGroup.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        mX = event.getX();
                        mY = event.getY();
                        break;

                    case MotionEvent.ACTION_MOVE:
                        mCurrentX = event.getX();
                        mCurrentY = event.getY();

                        break;
                    case MotionEvent.ACTION_UP:
                        if (mCurrentY - mY > 0 && (Math.abs(mCurrentY - mY) > 25)) {
                            //向下滑动
                            mActivityMainBinding.llOrder.setVisibility(View.VISIBLE);
                            mActivityMainBinding.llCount.setVisibility(View.VISIBLE);
                            mActivityMainBinding.ivPicture.setVisibility(View.VISIBLE);
                            mLlBalance.setVisibility(View.VISIBLE);


                        } else if (mCurrentY - mY < 0 && (Math.abs(mCurrentY - mY) > 25)) {
                            //向上滑动
                            mActivityMainBinding.llOrder.setVisibility(View.GONE);
                            mActivityMainBinding.llCount.setVisibility(View.GONE);
                            mActivityMainBinding.ivPicture.setVisibility(View.GONE);
                            mLlBalance.setVisibility(View.GONE);
                        }
                        break;

                    default:
                        break;
                }
                return false;

            }
        });
        mActivityMainBinding.lvWork.setOnTouchListener(new View.OnTouchListener() {



            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        mX = motionEvent.getX();
                        mY = motionEvent.getY();
                        break;

                    case MotionEvent.ACTION_MOVE:
                        mCurrentX = motionEvent.getX();
                        mCurrentY = motionEvent.getY();

                        break;
                    case MotionEvent.ACTION_UP:
                        if (mCurrentY - mY > 0 && (Math.abs(mCurrentY - mY) > 25)) {
                            //向下滑动
                            mActivityMainBinding.llOrder.setVisibility(View.VISIBLE);
                            mActivityMainBinding.llCount.setVisibility(View.VISIBLE);
                            mActivityMainBinding.ivPicture.setVisibility(View.VISIBLE);
                            mLlBalance.setVisibility(View.VISIBLE);


                        } else if (mCurrentY - mY < 0 && (Math.abs(mCurrentY - mY) > 25)) {
                            //向上滑动
                            mActivityMainBinding.llOrder.setVisibility(View.GONE);
                            mActivityMainBinding.llCount.setVisibility(View.GONE);
                            mActivityMainBinding.ivPicture.setVisibility(View.GONE);
                            mLlBalance.setVisibility(View.GONE);
                        }
                        break;

                    default:
                        break;
                }
                return false;

            }
        });
        mActivityMainBinding.tvClass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mGrouNumAdapter = new GroupNumAdapter(mWorkTeamNames, MainActivity.this);
                showPop(v);

            }
        });


    }

    private void initHead() {
//        View head = View.inflate(this, R.layout.head_barchart_list, null);
//        mBarChart = head.findViewById(R.id.barChart);
        mLlBalance = (LinearLayout) findViewById(R.id.llBalance);
        mBarChart = (BarChart) findViewById(R.id.barChart);
//        mActivityMainBinding.lvWork.addHeaderView(head);
    }


    @SuppressWarnings("unchecked")
    private void initData() {
        OthersUtil.showLoadDialog(mDialog);

        NewRxjavaWebUtils.getUIThread(NewRxjavaWebUtils.getObservable(this, "")
                        //订单号
                        .map(new Func1<String, HsWebInfo>() {
                            @Override
                            public HsWebInfo call(String s) {
                                HsWebInfo hsWebInfo = NewRxjavaWebUtils.getJsonData(getApplicationContext(), CUS_SERVICE,
                                        "spappAssignment", "iIndex =0",
                                        TopDataBean.class.getName(), true, "查询无结果！");
                                Map<String, Object> map = new HashMap<>();
                                if (!hsWebInfo.success) return hsWebInfo;
                                map.put("TopDataBean", hsWebInfo.wsData.LISTWSDATA);
                                hsWebInfo.object = map;
                                return hsWebInfo;
                            }
                        })
                        //柱状图
                        .map(new Func1<HsWebInfo, HsWebInfo>() {
                            @Override
                            public HsWebInfo call(HsWebInfo hsWebInfo) {
                                if (!hsWebInfo.success) return hsWebInfo;
                                Map<String, Object> map = (Map<String, Object>) hsWebInfo.object;
                                hsWebInfo = getJsonData(getApplicationContext(), CUS_SERVICE,
                                        "spappAssignment", "iIndex =1",
                                        RealTimeProcessBarBean.class.getName(), true, "");
                                if (!hsWebInfo.success) return hsWebInfo;
                                map.put("RealTimeProcessBarBean", hsWebInfo.wsData.LISTWSDATA);
                                hsWebInfo.object = map;
                                return hsWebInfo;
                            }
                        })
                        //工序人员-左侧
                        .map(new Func1<HsWebInfo, HsWebInfo>() {
                            @Override
                            public HsWebInfo call(HsWebInfo hsWebInfo) {
                                if (!hsWebInfo.success) return hsWebInfo;
                                Map<String, Object> map = (Map<String, Object>) hsWebInfo.object;
                                hsWebInfo = getJsonData(getApplicationContext(), CUS_SERVICE,
                                        "spappAssignment", "iIndex =2",
                                        ProcessWorkerBean.class.getName(), true, "");
                                if (!hsWebInfo.success) return hsWebInfo;
                                map.put("ProcessWorkerBean", hsWebInfo.wsData.LISTWSDATA);
                                hsWebInfo.object = map;
                                return hsWebInfo;
                            }
                        })
                        //班组人员-右侧
                        .map(new Func1<HsWebInfo, HsWebInfo>() {
                            @Override
                            public HsWebInfo call(HsWebInfo hsWebInfo) {
                                if (!hsWebInfo.success) return hsWebInfo;
                                Map<String, Object> map = (Map<String, Object>) hsWebInfo.object;
                                hsWebInfo = getJsonData(getApplicationContext(), CUS_SERVICE,
                                        "spappAssignment", "iIndex =3",
                                        ClassGroupBean.class.getName(), true, "");
                                if (!hsWebInfo.success) return hsWebInfo;
                                map.put("ClassGroupBean", hsWebInfo.wsData.LISTWSDATA);
                                hsWebInfo.object = map;
                                return hsWebInfo;
                            }
                        })
                , getApplicationContext(), mDialog, new SimpleHsWeb() {


                    @Override
                    public void success(HsWebInfo hsWebInfo) {
                        Map<String, Object> map = (Map<String, Object>) hsWebInfo.object;
                        List<WsEntity> topDataBeanEntityList = (List<WsEntity>) map.get("TopDataBean");
                        List<WsEntity> realTimeProcessBarBeanEntityList = (List<WsEntity>) map.get("RealTimeProcessBarBean");
                        List<WsEntity> processWorkerBeanEntityList = (List<WsEntity>) map.get("ProcessWorkerBean");
                        List<WsEntity> classGroupBeanEntityList = (List<WsEntity>) map.get("ClassGroupBean");
                        //表头信息

                        TopDataBean topDataBean = (TopDataBean) topDataBeanEntityList.get(0);

                        //TODO
                        showTopData(topDataBean);
                        //柱状图信息
                        showBarData(realTimeProcessBarBeanEntityList);
                        //工序组成员通过班组名称分组
                        List<ProcessWorkerBean> processWorkerBeanList = new ArrayList<>();//工序组人员集合
                        for (WsEntity entity : processWorkerBeanEntityList) {
                            processWorkerBeanList.add((ProcessWorkerBean) entity);//
                        }
                        List<List<ProcessWorkerBean>> listByWorkTeamName = getListByWorkTeamName(processWorkerBeanList);
                        //通过组名过滤

                        List<String> partNames = new ArrayList<>();//工序名称集合
                        List<String> distinctPartNames;
                        WorkGroupBean workGroupBean = new WorkGroupBean();
                        for (List<ProcessWorkerBean> list : listByWorkTeamName) {//组名集合
                            for (ProcessWorkerBean bean : list) {
                                partNames.add(bean.SPARTNAME);//工序名字
                            }
                            distinctPartNames = distinctData(partNames);
                            for (String keyName : distinctPartNames) {
                                WorkGroupBean.ProgressBean progressBean = new WorkGroupBean.ProgressBean();
                                progressBean.progressName = keyName;
                                for (ProcessWorkerBean bean : list) {
                                    if (keyName.equals(bean.SPARTNAME)) {
                                        progressBean.workerNames.add(bean.SEMPLOYEENAMECN);
                                    }
                                }
                                workGroupBean.progressBeen.add(progressBean);
                            }
                        }
//                        System.out.println(workGroupBean.progressBeen);
                        mWorkAdapter = new WorkAdapter(workGroupBean.progressBeen, getApplicationContext());
                        mActivityMainBinding.lvWork.setAdapter(mWorkAdapter);
                        //工序组成员通过班组名称分组
                        List<ClassGroupBean> classGroupBeanList = new ArrayList<>();
                        for (WsEntity entity : classGroupBeanEntityList) {
                            classGroupBeanList.add((ClassGroupBean) entity);
                        }
                        mClassGroupListByWorkTeamName = getListByWorkTeamName(classGroupBeanList);

                        mCurrentSelectedWorkTeam = processWorkerBeanList.get(0).SWORKTEAMNAME;
                        List<ClassGroupBean> classGroupBeen = getClassGroupBeenByWorkTeamName(mCurrentSelectedWorkTeam, mClassGroupListByWorkTeamName);
                        if (classGroupBeen != null) {
                            mGroupAdapter=new GroupAdapter(classGroupBeen,getApplicationContext());
                            mActivityMainBinding.gvGroup.setAdapter(mGroupAdapter);
                            mActivityMainBinding.tvClass.setText(classGroupBeen.get(0).SWORKTEAMNAME);
                        }

                    }

                    @Override
                    public void error(HsWebInfo hsWebInfo, Context context) {
                        super.error(hsWebInfo, context);
                        mBarChart.clear();
                        mBarChart.invalidate();
                    }
                });
//        //款号
//        NewRxjavaWebUtils.getUIThread(NewRxjavaWebUtils.getObservable(this, "")
//                        .map(new Func1<String, HsWebInfo>() {
//                                 @Override
//                                 public HsWebInfo call(String s) {
//                                     return getJsonData(getApplicationContext(), CUS_SERVICE,
//                                             "spappAssignment", "iIndex =0",
//                                             StyleNumBean.class.getName(), true, "");
//
//                                 }
//                             }
//                        )
//                , getApplicationContext(), mDialog, new SimpleHsWeb() {
//                    @Override
//                    public void success(HsWebInfo hsWebInfo) {
//                        List<WsEntity> entities = hsWebInfo.wsData.LISTWSDATA;
//                        mStyleNumBeanList.clear();//款号集合
//                        for (int i = 0; i < entities.size(); i++) {
//                            StyleNumBean styleNumBean = (StyleNumBean) entities.get(i);
//                            mStyleNumBeanList.add(styleNumBean);
//                        }
//                    }
//
//                    @Override
//                    public void error(HsWebInfo hsWebInfo, Context context) {
//                        super.error(hsWebInfo, context);
//
//                    }
//                });
//        //批次号
//        NewRxjavaWebUtils.getUIThread(NewRxjavaWebUtils.getObservable(this, "")
//                        .map(new Func1<String, HsWebInfo>() {
//                                 @Override
//                                 public HsWebInfo call(String s) {
//                                     return getJsonData(getApplicationContext(), CUS_SERVICE,
//                                             "spappAssignment", "iIndex =0",
//                                             TopDataBean.class.getName(), true, "");
//
//                                 }
//                             }
//                        )
//                , getApplicationContext(), mDialog, new SimpleHsWeb() {
//                    @Override
//                    public void success(HsWebInfo hsWebInfo) {
//                        List<WsEntity> entities = hsWebInfo.wsData.LISTWSDATA;
//                        mBatchNumBeanList.clear();
//                        for (int i = 0; i < entities.size(); i++) {
//                            TopDataBean batchNumBean = (TopDataBean) entities.get(i);
//                            mBatchNumBeanList.add(batchNumBean);
//                        }
//                    }
//
//                    @Override
//                    public void error(HsWebInfo hsWebInfo, Context context) {
//                        super.error(hsWebInfo, context);
//
//                    }
//                });
    }

    private <V extends WsEntity> List<List<V>> getListByWorkTeamName(List<V> originList) {
        List<String> workTeamNames = new ArrayList<>();
        List<String> distinctNames = null;
        for (V entity : originList) {
            if (entity instanceof ProcessWorkerBean) {
                workTeamNames.add(((ProcessWorkerBean) entity).SWORKTEAMNAME);
            } else if (entity instanceof ClassGroupBean) {
                workTeamNames.add(((ClassGroupBean) entity).SWORKTEAMNAME);
            }
            distinctNames = distinctData(workTeamNames);
            if (distinctNames.size()>1) mWorkTeamNames = distinctNames;
        }

        List<List<V>> listByWorkTeamName = new ArrayList<>();
        if (distinctNames != null) {
            for (String keyName : distinctNames) {
                List<V> list = new ArrayList<>();
                for (V entity : originList) {
                    if (entity instanceof ProcessWorkerBean) {
                        if (((ProcessWorkerBean) entity).SWORKTEAMNAME.equals(keyName)) {
                            list.add(entity);
                        }
                    } else if (entity instanceof ClassGroupBean) {
                        if (((ClassGroupBean) entity).SWORKTEAMNAME.equals(keyName)) {
                            list.add(entity);
                        }
                    }

                }
                listByWorkTeamName.add(list);
            }
        }
        return listByWorkTeamName;
    }

    private List<String> distinctData(List<String> source) {
        List<String> result = new ArrayList<>();
        for (String str : source) {
            if (!result.contains(str)) {
                result.add(str);
            }
        }

        return result;
    }

    private List<ClassGroupBean> getClassGroupBeenByWorkTeamName(String currentSelectedWorkTeamName, List<List<ClassGroupBean>> classGroupListByWorkTeamName) {
        for (List<ClassGroupBean> classGroupBeen : classGroupListByWorkTeamName) {
            for (ClassGroupBean classGroupBean : classGroupBeen) {
                if (classGroupBean.SWORKTEAMNAME.equals(currentSelectedWorkTeamName)) {
                    return classGroupBeen;
                }
            }
        }
        return null;
    }

    /**
     * 显示柱状图信息
     *
     * @param realTimeProcessBarBeanEntityList
     */
    private void showBarData(List<WsEntity> realTimeProcessBarBeanEntityList) {
        mBarChart.getAxisRight().setEnabled(false);
        xAxis = mBarChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        yAxis = mBarChart.getAxisLeft();
//                        xAxis.setSpaceBetweenLabels(2);
//                        yAxis.setLabelCount(8, false);
        yAxis.setDrawGridLines(false);
        mBarChart.setNoDataText("没有数据");
        mRealTimeProcessBarBeanList.clear();
        xData = null;//x轴描述
        barChart.clear();
        allPoints.clear();
        errorPoints.clear();
        for (int i = 0; i < realTimeProcessBarBeanEntityList.size(); i++) {
            RealTimeProcessBarBean realTimeProcessBarBean = (RealTimeProcessBarBean) realTimeProcessBarBeanEntityList.get(i);
            mRealTimeProcessBarBeanList.add(realTimeProcessBarBean);
        }
        xData = new String[mRealTimeProcessBarBeanList.size()];
        for (int i = 0; i < mRealTimeProcessBarBeanList.size(); i++) {
            allPoints.add(new BarEntry(Integer.valueOf(mRealTimeProcessBarBeanList.get(i).IQTY), i));
            errorPoints.add(new BarEntry(Integer.valueOf(mRealTimeProcessBarBeanList.get(i).IQTY), i));
            xData[i] = mRealTimeProcessBarBeanList.get(i).SPROCEDURENAME;
        }
        allPointsName = new BarDataSet(allPoints, "测试1");
        allPointsName.setColor(Color.rgb(104, 241, 175));
        errorPointsName = new BarDataSet(errorPoints, "测试2");
        errorPointsName.setColor(Color.rgb(255, 102, 0));
        barChart.add(allPointsName);
        barChart.add(errorPointsName);
        BarData barData = new BarData(xData, barChart);
        barData.notifyDataChanged();
        barData.setDrawValues(true);

        mBarChart.setData(barData);
        OthersUtil.dismissLoadDialog(mDialog);
        mBarChart.animateXY(0, 2000);
        mBarChart.invalidate();
        barData.notifyDataChanged();
    }


    /**
     * 显示头部信息
     */
    private void showTopData(TopDataBean topDataBean) {
        mActivityMainBinding.tvOrderNum.setText(topDataBean.SORDERNO);
        mActivityMainBinding.tvStyleNum.setText(topDataBean.SSTYLENO);
        mActivityMainBinding.tvBatchNum.setText(topDataBean.SLOTNO);
        mActivityMainBinding.tvDeliveryDate.setText(topDataBean.DDELIVERYDATE);
        mActivityMainBinding.tvOrderQTY.setText(topDataBean.IORDERQTY);
        mActivityMainBinding.tvCutQTY.setText(topDataBean.ICUTQTY);
        mActivityMainBinding.tvUpQTY.setText(topDataBean.IUPQTY);
        mActivityMainBinding.tvDownQTY.setText(topDataBean.IDOWNQTY);
    }

    /**
     * popWindow
     *
     * @param view
     */
    private void showPop(final View view) {
        View contentView = LayoutInflater.from(getApplicationContext()).inflate(R.layout.pop_list, null);
        final PopupWindow popupWindow = new PopupWindow(contentView, length[0] / 4 - 10, length[1] / 4, true);
        final ListView popListView = (ListView) contentView.findViewById(R.id.lv_pop_clothlist);
        switch (view.getId()) {
            case R.id.tvOrderNum:
                popListView.setAdapter(mOrderNumAdapter);
                break;
            case R.id.tvStyleNum:
                popListView.setAdapter(mStyleNumAdapter);
                break;
            case R.id.tv_BatchNum:
                popListView.setAdapter(mBatchNumAdapter);
                break;
            case R.id.tvClass:
                popListView.setAdapter(mGrouNumAdapter);
                break;

        }
        popupWindow.setTouchable(true);
        ColorDrawable dw = new ColorDrawable(0x000);
        popupWindow.setBackgroundDrawable(dw);
        popupWindow.showAsDropDown(view);
        //三级联动
        popListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view2, int position, long id) {

                switch (view.getId()) {
                    case R.id.tvOrderNum:
                        TopDataBean topDataBean= (TopDataBean) parent.getItemAtPosition(position);
                        mActivityMainBinding.tvOrderNum.setText(topDataBean.SORDERNO);
//                        TableHeadBean tableHeadBean = (TableHeadBean) parent.getItemAtPosition(position);
//                        mActivityMainBinding.tvOrderNum.setText(tableHeadBean.SORDERNO);
//                        listFliterStyleItem.clear();
//                        listFliterBatchItem.clear();
//                        //订单号对应的款号
//                        for (int i = 0; i < mStyleNumBeanList.size(); i++) {
//                            StyleNumBean styleNumBean = mStyleNumBeanList.get(i);
//                            if (tableHeadBean.SORDERNO.equals(styleNumBean.SORDERNO)) {
//                                listFliterStyleItem.add(styleNumBean);
//                            }
//                        }
//                        if (listFliterStyleItem.isEmpty()) {
//                            mActivityMainBinding.tvStyleNum.setText("款号");
//                            mActivityMainBinding.tvBatchNum.setText("批次号");
//                            mFliterStyleNumList.clear();
//                            mFliterBatchNumList.clear();
//                        } else {
//                            mFliterStyleNumList.clear();
//                            mFliterStyleNumList.addAll(listFliterStyleItem);
//                            mActivityMainBinding.tvStyleNum.setText(listFliterStyleItem.get(0).SSTYLENO);
//                            String styleNumId = listFliterStyleItem.get(0).SSTYLENO;
//                            //款号对相应的批次号
//                            for (int i = 0; i < mBatchNumBeanList.size(); i++) {
//                                TopDataBean batchNumBean = mBatchNumBeanList.get(i);
//                                if (styleNumId.equals(batchNumBean.SSTYLENO)) {
//                                    listFliterBatchItem.add(batchNumBean);
//                                }
//                            }
//                            if (listFliterBatchItem.isEmpty()) {
//                                mActivityMainBinding.tvStyleNum.setText("批次号");
//                                mFliterBatchNumList.clear();
//                            } else {
//                                mFliterBatchNumList.clear();
//                                mFliterBatchNumList.addAll(listFliterBatchItem);
//                                mActivityMainBinding.tvBatchNum.setText(listFliterBatchItem.get(0).SLOTNO);
//                            }
//
//                        }

                        break;
                    case R.id.tvStyleNum:
                        TopDataBean dataBean= (TopDataBean) parent.getItemAtPosition(position);
                        mActivityMainBinding.tvStyleNum.setText(dataBean.SSTYLENO);
//                        StyleNumBean styleNumBean = (StyleNumBean) parent.getItemAtPosition(position);
//                        mActivityMainBinding.tvStyleNum.setText(styleNumBean.SSTYLENO);
//                        listFliterBatchItem.clear();
//                        //款号对应的批次号
//                        for (int i = 0; i < mBatchNumBeanList.size(); i++) {
//                            TopDataBean batchNumBean = mBatchNumBeanList.get(i);
//                            if (styleNumBean.SSTYLENO.equals(batchNumBean.SSTYLENO)) {
//                                listFliterBatchItem.add(batchNumBean);
//                            }
//                        }
//                        if (listFliterBatchItem.isEmpty()) {
//                            mActivityMainBinding.tvBatchNum.setText("批次号");
//                            mFliterBatchNumList.clear();
//                        } else {
//                            mFliterBatchNumList.clear();
//                            mFliterBatchNumList.addAll(listFliterBatchItem);
//                            mActivityMainBinding.tvBatchNum.setText(listFliterBatchItem.get(0).SLOTNO);
//                        }

                        break;
                    case R.id.tv_BatchNum:
                        TopDataBean bean= (TopDataBean) parent.getItemAtPosition(position);
                        mActivityMainBinding.tvBatchNum.setText(bean.SLOTNO);
                        mActivityMainBinding.tvDeliveryDate.setText(bean.DDELIVERYDATE);
                        mActivityMainBinding.tvOrderQTY.setText(bean.IORDERQTY);
                        mActivityMainBinding.tvCutQTY.setText(bean.ICUTQTY);
                        mActivityMainBinding.tvUpQTY.setText(bean.IUPQTY);
                        mActivityMainBinding.tvDownQTY.setText(bean.IDOWNQTY);
//                        TopDataBean batchNumBean = (TopDataBean) parent.getItemAtPosition(position);
//                        mActivityMainBinding.tvBatchNum.setText(batchNumBean.SLOTNO);
//                        mActivityMainBinding.tvDeliveryDate.setText(batchNumBean.DDELIVERYDATE);
//                        mActivityMainBinding.tvOrderQTY.setText(batchNumBean.IORDERQTY);
//                        mActivityMainBinding.tvCutQTY.setText(batchNumBean.ICUTQTY);
//                        mActivityMainBinding.tvUpQTY.setText(batchNumBean.IUPQTY);
//                        mActivityMainBinding.tvDownQTY.setText(batchNumBean.IDOWNQTY);
//                        setProcessWorkersData();
//                        setWorkerInforsData();
                        break;
                    case R.id.tvClass:
                        mCurrentSelectedWorkTeam = (String) parent.getItemAtPosition(position);
                        List<ClassGroupBean> classGroupBeenByWorkTeamName = getClassGroupBeenByWorkTeamName(mCurrentSelectedWorkTeam, mClassGroupListByWorkTeamName);
                        mGroupAdapter.setList(classGroupBeenByWorkTeamName);
                        mGroupAdapter.notifyDataSetChanged();
                        mActivityMainBinding.tvClass.setText(mCurrentSelectedWorkTeam);
                        break;
                }
                popupWindow.dismiss();
            }
        });

    }
//    //工序人员分布
//    private void setProcessWorkersData() {
//
//        NewRxjavaWebUtils.getUIThread(NewRxjavaWebUtils.getObservable(this, "")
//                        .map(new Func1<String, HsWebInfo>() {
//                                 @Override
//                                 public HsWebInfo call(String s) {
//                                     return getJsonData(getApplicationContext(), CUS_SERVICE,
//                                             "spappAssignment", "iIndex =2",
//                                             ProcessWorkerBean.class.getName(), true, "");
//                                 }
//                             }
//                        )
//                , getApplicationContext(), mDialog, new SimpleHsWeb() {
//                    @Override
//                    public void success(HsWebInfo hsWebInfo) {
//                        List<WsEntity> entities = hsWebInfo.wsData.LISTWSDATA;
//                        mProcessWorkerBeanList.clear();
//                        for (int i = 0; i < entities.size(); i++) {
//                            ProcessWorkerBean processWorkerBean = (ProcessWorkerBean) entities.get(i);
//                            mProcessWorkerBeanList.add(processWorkerBean);
//                        }
//                    }
//
//                    @Override
//                    public void error(HsWebInfo hsWebInfo, Context context) {
//                        super.error(hsWebInfo, context);
//
//                    }
//                });
//    }
//    //待选人员清单
//    private void setWorkerInforsData() {
//
//        NewRxjavaWebUtils.getUIThread(NewRxjavaWebUtils.getObservable(this, "")
//                        .map(new Func1<String, HsWebInfo>() {
//                                 @Override
//                                 public HsWebInfo call(String s) {
//                                     return getJsonData(getApplicationContext(), CUS_SERVICE,
//                                             "spappAssignment", "iIndex =3",
//                                             ProcessWorkerBean.class.getName(), true, "");
//
//                                 }
//                             }
//                        )
//                , getApplicationContext(), mDialog, new SimpleHsWeb() {
//                    @Override
//                    public void success(HsWebInfo hsWebInfo) {
//                        List<WsEntity> entities = hsWebInfo.wsData.LISTWSDATA;
//                        mProcessWorkerBeanList.clear();
//                        for (int i = 0; i < entities.size(); i++) {
//                            ProcessWorkerBean processWorkerBean = (ProcessWorkerBean) entities.get(i);
//                            mProcessWorkerBeanList.add(processWorkerBean);
//                        }
//                    }
//
//                    @Override
//                    public void error(HsWebInfo hsWebInfo, Context context) {
//                        super.error(hsWebInfo, context);
//
//                    }
//                });
//    }


}
