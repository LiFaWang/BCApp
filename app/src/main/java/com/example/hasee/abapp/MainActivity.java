package com.example.hasee.abapp;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.PopupWindow;

import com.example.hasee.abapp.adapter.BatchNumAdapter;
import com.example.hasee.abapp.adapter.OrderNumAdapter;
import com.example.hasee.abapp.adapter.StyleNumAdapter;
import com.example.hasee.abapp.adapter.WorkAdapter;
import com.example.hasee.abapp.bean.ClassGroupBean;
import com.example.hasee.abapp.bean.ProcessWorkerBean;
import com.example.hasee.abapp.bean.RealTimeProcessBarBean;
import com.example.hasee.abapp.bean.TopDataBean;
import com.example.hasee.abapp.bean.WorkAllBean;
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

public class MainActivity extends NotWebBaseActivity implements View.OnClickListener {

    private ActivityMainBinding mActivityMainBinding;
    private int[] length;//屏幕长宽;
    private OrderNumAdapter mOrderNumAdapter;//订单号adapter
    private StyleNumAdapter mStyleNumAdapter;//款号adapter
    private BatchNumAdapter mBatchNumAdapter;//批次号adapter
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
//    private List<BarEntry> xData;//x轴描述
//    private List<RealTimeProcessBarBean> data;//实时监控设备数据
    private BarDataSet allPointsName, errorPointsName;//所有点数，异常点数名字
    private ArrayList<BarEntry> allPoints, errorPoints;//所有点数，异常点数
//    private RealTimeProcessAdapter mRealTimeProcessAdapter;
//    private String iCountryId = "";//国家id
//    private String iFactoryId = "";//工厂id
//    private List<RealTimeProcessBarBean> mGroupBeen;
    private WorkAdapter mWorkAdapter;
//    private View mHead;
    private BarChart mBarChart;
    private List<TopDataBean> mTopDataBeanList;
    private List<RealTimeProcessBarBean> mRealTimeProcessBarBeanList;
    //    private List<ProcessWorkerBean> mProcessWorkerBeanList;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void init() {
        mActivityMainBinding = (ActivityMainBinding) viewDataBinding;
//        mProcessWorkerBeanList=new ArrayList<>();
        mWorkAllBeanList=new ArrayList<>();
        mTopDataBeanList=new ArrayList<>();
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

        mActivityMainBinding.tvOrderNum.setOnClickListener(this);
        mActivityMainBinding.tvStyleNum.setOnClickListener(this);
        mActivityMainBinding.tvBatchNum.setOnClickListener(this);
        length = MyUtils.getScreenSize(this);
        initHead();

        initData();
//        mWorkAdapter=new WorkAdapter(mWorkAllBeanList,this);
//        mActivityMainBinding.lvWork.setAdapter(mWorkAdapter);



    }

    private void initHead(){
        View head = View.inflate(this,R.layout.head_barchart_list,null);
        mBarChart =  head.findViewById(R.id.barChart);
        mActivityMainBinding.lvWork.addHeaderView(head);
    }

//    private void initTestWorkData() {
//        List<WorkGroupBean.WorkerInfo> workerInfos = new ArrayList<>();
//        for (int i = 0; i < 6; i++) {
//            WorkGroupBean.WorkerInfo workerInfo = new WorkGroupBean.WorkerInfo();
//            workerInfo.workerID = "1000" + i;
//            workerInfo.workerName = "张三" + i;
//            workerInfo.workerPictureUri = "";
//            workerInfo.workerType = "烫台" + i;
//            workerInfos.add(workerInfo);
//        }
//        WorkGroupBean.ProgressBean progressBean1 = new WorkGroupBean.ProgressBean();
//        progressBean1.progressName = "后片包缝";
//        progressBean1.workerNames = new String[]{"支燕云","代广菊","郭玲林","刘涛"};
//        WorkGroupBean.ProgressBean progressBean2 = new WorkGroupBean.ProgressBean();
//        progressBean2.progressName = "后片包缝";
//        progressBean2.workerNames = new String[]{"支燕云","代广菊","刘涛"};
//        WorkGroupBean.ProgressBean progressBean3 = new WorkGroupBean.ProgressBean();
//        progressBean3.progressName = "后片包缝";
//        progressBean3.workerNames = new String[]{"支燕云","代广菊","郭玲林","刘涛"};
//        WorkGroupBean.ProgressBean progressBean4 = new WorkGroupBean.ProgressBean();
//        progressBean4.progressName = "后片包缝";
//        progressBean4.workerNames = new String[]{"支燕云","刘涛"};
//        WorkGroupBean.ProgressBean progressBean5 = new WorkGroupBean.ProgressBean();
//        progressBean5.progressName = "后片包缝";
//        progressBean5.workerNames = new String[]{"支燕云","代广菊","郭玲林","刘涛"};
//        WorkGroupBean.ProgressBean progressBean6 = new WorkGroupBean.ProgressBean();
//        progressBean6.progressName = "后片包缝";
//        progressBean6.workerNames = new String[]{};
//        WorkGroupBean.ProgressBean progressBean7 = new WorkGroupBean.ProgressBean();
//        progressBean7.progressName = "后片包缝";
//        progressBean7.workerNames = new String[]{"支燕云","代广菊","郭玲林","刘涛"};
//
//        WorkGroupBean workGroupBean1=new WorkGroupBean();
//        workGroupBean1.workerInfos = workerInfos;
//        workGroupBean1.progressBeen.add(progressBean1);
//        workGroupBean1.progressBeen.add(progressBean2);
//        workGroupBean1.progressBeen.add(progressBean3);
//        workGroupBean1.progressBeen.add(progressBean4);
//        workGroupBean1.progressBeen.add(progressBean5);
//        workGroupBean1.progressBeen.add(progressBean6);
//        workGroupBean1.progressBeen.add(progressBean7);
//        WorkGroupBean workGroupBean2=new WorkGroupBean();
//        workGroupBean2.workerInfos = workerInfos;
//        workGroupBean2.progressBeen.add(progressBean1);
//        workGroupBean2.progressBeen.add(progressBean2);
//        workGroupBean2.progressBeen.add(progressBean3);
//        workGroupBean2.progressBeen.add(progressBean4);
//        workGroupBean2.progressBeen.add(progressBean5);
//        workGroupBean2.progressBeen.add(progressBean6);
//        workGroupBean2.progressBeen.add(progressBean7);
//
//        mGroupBeen = new ArrayList<>();
//        mGroupBeen.add(workGroupBean1);
//        mGroupBeen.add(workGroupBean2);
//
//
//    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tvOrderNum:
                mOrderNumAdapter = new OrderNumAdapter(mTopDataBeanList, this);
                mOrderNumAdapter.notifyDataSetChanged();
                showPop(view);
                break;
            case R.id.tvStyleNum:
                mStyleNumAdapter = new StyleNumAdapter(mTopDataBeanList, this);
                mStyleNumAdapter.notifyDataSetChanged();
                showPop(view);
                break;
            case R.id.tv_BatchNum:
                mBatchNumAdapter = new BatchNumAdapter(mTopDataBeanList, this);
                mBatchNumAdapter.notifyDataSetChanged();
                showPop(view);
                break;

            default:
                break;
        }

    }

    @SuppressWarnings("unchecked")
    private void initData() {
        OthersUtil.showLoadDialog(mDialog);

        NewRxjavaWebUtils.getUIThread(NewRxjavaWebUtils.getObservable(this, "")
                        //订单号
                        .map(new Func1<String, HsWebInfo>() {
                                 @Override
                                 public HsWebInfo call(String s) {
                                     HsWebInfo hsWebInfo=NewRxjavaWebUtils.getJsonData(getApplicationContext(), CUS_SERVICE,
                                             "spappAssignment", "iIndex =0",
                                             TopDataBean.class.getName(), true, "查询无结果！");
                                     Map<String,Object> map=new HashMap<>();
                                     if(!hsWebInfo.success) return hsWebInfo;
                                     map.put("TopDataBean",hsWebInfo.wsData.LISTWSDATA);
                                     hsWebInfo.object=map;
                                     return hsWebInfo;
                                 }
                             })
                        //柱状图
                        .map(new Func1<HsWebInfo, HsWebInfo>() {
                            @Override
                            public HsWebInfo call(HsWebInfo hsWebInfo) {
                                if(!hsWebInfo.success) return hsWebInfo;
                                Map<String,Object> map= (Map<String, Object>) hsWebInfo.object;
                                hsWebInfo= getJsonData(getApplicationContext(), CUS_SERVICE,
                                        "spappAssignment", "iIndex =1",
                                        RealTimeProcessBarBean.class.getName(), true, "");
                                if(!hsWebInfo.success) return hsWebInfo;
                                map.put("RealTimeProcessBarBean",hsWebInfo.wsData.LISTWSDATA);
                                hsWebInfo.object=map;
                                return hsWebInfo;
                            }
                        })
                        //工序人员-左侧
                        .map(new Func1<HsWebInfo, HsWebInfo>() {
                            @Override
                            public HsWebInfo call(HsWebInfo hsWebInfo) {
                                if(!hsWebInfo.success) return hsWebInfo;
                                Map<String,Object> map= (Map<String, Object>) hsWebInfo.object;
                                hsWebInfo= getJsonData(getApplicationContext(), CUS_SERVICE,
                                        "spappAssignment", "iIndex =2",
                                        ProcessWorkerBean.class.getName(), true, "");
                                if(!hsWebInfo.success) return hsWebInfo;
                                map.put("ProcessWorkerBean",hsWebInfo.wsData.LISTWSDATA);
                                hsWebInfo.object=map;
                                return hsWebInfo;
                            }
                        })
                        //班组人员-右侧
                        .map(new Func1<HsWebInfo, HsWebInfo>() {
                                 @Override
                                 public HsWebInfo call(HsWebInfo hsWebInfo) {
                                     if(!hsWebInfo.success) return hsWebInfo;
                                     Map<String,Object> map= (Map<String, Object>) hsWebInfo.object;
                                     hsWebInfo= getJsonData(getApplicationContext(), CUS_SERVICE,
                                             "spappAssignment", "iIndex =3",
                                             ClassGroupBean.class.getName(), true, "");
                                     if(!hsWebInfo.success) return hsWebInfo;
                                     map.put("ClassGroupBean",hsWebInfo.wsData.LISTWSDATA);
                                     hsWebInfo.object=map;
                                     return hsWebInfo;
                                 }
                             })
                , getApplicationContext(), mDialog, new SimpleHsWeb() {
                    @Override
                    public void success(HsWebInfo hsWebInfo) {
                        Map<String,Object> map=(Map<String, Object>) hsWebInfo.object;
                        List<WsEntity> topDataBeanEntityList= (List<WsEntity>) map.get("TopDataBean");
                        List<WsEntity> realTimeProcessBarBeanEntityList= (List<WsEntity>) map.get("RealTimeProcessBarBean");
                        List<WsEntity> processWorkerBeanEntityList= (List<WsEntity>) map.get("ProcessWorkerBean");
                        List<WsEntity> classGroupBeanEntityList= (List<WsEntity>) map.get("ClassGroupBean");
                        //表头信息
                        TopDataBean topDataBean= (TopDataBean) topDataBeanEntityList.get(0);
                        mTopDataBeanList.add(topDataBean);
                        //柱状图信息

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
                        for (int i = 0; i <realTimeProcessBarBeanEntityList.size() ; i++) {
                            RealTimeProcessBarBean realTimeProcessBarBean= (RealTimeProcessBarBean) realTimeProcessBarBeanEntityList.get(i);
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
                        Map<String,WorkAllBean> workMap=new HashMap<>();
                        //工序组成员通过班组名称分组
                        for(WsEntity entity:processWorkerBeanEntityList){
                            ProcessWorkerBean bean= (ProcessWorkerBean) entity;
//                            mProcessWorkerBeanList.add(bean);
                            WorkAllBean workAllBean=workMap.get(bean.SWORKTEAMNAME);
                            if(workAllBean==null) workAllBean=new WorkAllBean();
                            workAllBean.processWorkerBeanList.add(bean);
//                            workMap.put(bean.SWORKTEAMNAME,workAllBean);
                            mWorkAllBeanList.add(workAllBean);
//                            mWorkAllBeanList.add(workMap.get(bean.SWORKTEAMNAME));
                        }
                        if (mWorkAdapter==null)
                        mWorkAdapter=new WorkAdapter(mWorkAllBeanList,getApplicationContext());
                        mActivityMainBinding.lvWork.setAdapter(mWorkAdapter);
                        mWorkAdapter.notifyDataSetChanged();
                        //工作组成员通过班组名称分组
//                        for (WsEntity entity:classGroupBeanEntityList ) {
//                            ClassGroupBean classGroupBean= (ClassGroupBean) entity;
//                            WorkAllBean workAllBean=workMap.get(classGroupBean.SWORKTEAMNAME);
////                            if(workAllBean==null) workAllBean=new WorkAllBean();
//                            workAllBean.classGroupBeanList.add(classGroupBean);
////                            mWorkAllBeanList.add(workAllBean);
//                        }
//                        if (mWorkAdapter==null)
//                        mWorkAdapter=new WorkAdapter(mWorkAllBeanList,getApplicationContext());
//                        mActivityMainBinding.lvWork.setAdapter(mWorkAdapter);
//                        mWorkAdapter.notifyDataSetChanged();
//                        mTableHeadBeanList.clear();//订单号集合
//                        for (int i = 0; i < entities.size(); i++) {
//                            TableHeadBean tableHeadBean = (TableHeadBean) entities.get(i);
//                            mTableHeadBeanList.add(tableHeadBean);
//                        }
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

    /**
     * popWindow
     *
     * @param view
     */
    private void showPop(final View view) {
        View contentView = LayoutInflater.from(getApplicationContext()).inflate(R.layout.pop_list, null);
        final PopupWindow popupWindow = new PopupWindow(contentView, length[0] / 4 - 10, length[1] / 4, true);
        final ListView popListView = contentView.findViewById(R.id.lv_pop_clothlist);
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
