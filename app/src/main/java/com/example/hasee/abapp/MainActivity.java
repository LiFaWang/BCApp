package com.example.hasee.abapp;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hasee.abapp.adapter.BatchNumAdapter;
import com.example.hasee.abapp.adapter.GroupAdapter;
import com.example.hasee.abapp.adapter.OrderNumAdapter;
import com.example.hasee.abapp.adapter.ProcessWorkerAdapter;
import com.example.hasee.abapp.adapter.StyleNumAdapter;
import com.example.hasee.abapp.bean.ClassGroupBean;
import com.example.hasee.abapp.bean.ProcessWorkerBean;
import com.example.hasee.abapp.bean.RealTimeProcessBarBean;
import com.example.hasee.abapp.bean.StyleNumBean;
import com.example.hasee.abapp.bean.TableHeadBean;
import com.example.hasee.abapp.bean.TopDataBean;
import com.example.hasee.abapp.databinding.ActivityMainBinding;
import com.example.hasee.abapp.event.CardNumEvent;
import com.example.hasee.abapp.utils.MyUtils;
import com.example.hasee.abapp.utils.WBUtils;
import com.github.florent37.viewanimator.ViewAnimator;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Timer;

import huansi.net.qianjingapp.base.NotWebBaseActivity;
import huansi.net.qianjingapp.entity.HsWebInfo;
import huansi.net.qianjingapp.entity.WsData;
import huansi.net.qianjingapp.entity.WsEntity;
import huansi.net.qianjingapp.imp.SimpleHsWeb;
import huansi.net.qianjingapp.utils.NewRxjavaWebUtils;
import huansi.net.qianjingapp.utils.OthersUtil;
import rx.functions.Func1;

import static com.example.hasee.abapp.R.id.tvStyleNum;
import static huansi.net.qianjingapp.utils.NewRxjavaWebUtils.getJsonData;
import static huansi.net.qianjingapp.utils.WebServices.WebServiceType.CUS_SERVICE;

public class MainActivity extends NotWebBaseActivity implements View.OnClickListener {
    private static Handler handler = new Handler();

    private ActivityMainBinding mActivityMainBinding;
    private int[] length;//屏幕长宽;
    private OrderNumAdapter mOrderNumAdapter;//订单号adapter
    private StyleNumAdapter mStyleNumAdapter;//款号adapter
    private BatchNumAdapter mBatchNumAdapter;//批次号adapter
    private List<StyleNumBean> mFliterStyleNumList;//筛选款号数据集合
    private List<TopDataBean> mFliterBatchNumList;//筛选的批次号集合
    private List<StyleNumBean> listFliterStyleItem;//筛选款号的数据
    private List<TopDataBean> listFliterBatchItem;//筛选批次号的数据
    private List<TopDataBean> mBatchNumBeanList;//批次号数据
    private List<TableHeadBean> mTableHeadBeanList;//订单号数据
    private List<StyleNumBean> mStyleNumBeanList;//款号数据
    private List<BarDataSet> barChart;//存储BarDataSet
    String order;
    String style;
    String product;
//    private List<ProcessWorkerBean> mProcessWorkerBeanList;

//    private List<WorkAllBean> mWorkAllBeanList;//工序组和班组整在一起
//    private List<ClassGroupBean> mClassGroupBeanList;

    private XAxis xAxis;//x轴
    private YAxis yAxis;//y轴
    private String xData[];//x轴描述
    //    private List<RealTimeProcessBarBean> data;//实时监控设备数据
    private BarDataSet allPointsName, errorPointsName;//所有点数，异常点数名字
    private ArrayList<BarEntry> allPoints, errorPoints;//所有点数，异常点数
//    private List<RealTimeProcessBarBean> mGroupBeen;

    private List<TopDataBean> mTopDataBeanList;
    private List<RealTimeProcessBarBean> mRealTimeProcessBarBeanList;

    //    private LinearLayout mLlBalance;
//    private List<ClassGroupBean> mClassGroupBeenList;
    //    private List<ProcessWorkerBean> mProcessWorkerBeanList;
//    private float mY;
//    private float mCurrentY;
//    private float mCurrentX;
//    private float mX;
//    private List<String> mWorkTeamNames;//pop用的组名集合
//    private String mCurrentSelectedWorkTeam;
//    private List<List<ClassGroupBean>> mClassGroupListByWorkTeamName;
//    private List<ClassGroupBean> mClassGroupBeen;//根据组名筛选后存入的组对象
    private Toast toast;


    private List<List<ProcessWorkerBean>> workerBeanList;//工序的数组
    private Map<String, List<ClassGroupBean>> classGroupBeanSourceMap;//班组的源数据
    private List<ClassGroupBean> classGroupBeanShowList;//班组的显示数据
    private List<String> classGroupNameList;//班组名称的数组

    private ProcessWorkerAdapter mProcessWorkerAdapter;//工序适配器
    private GroupAdapter mGroupAdapter;//班组的适配器
    private ArrayAdapter<String> classGroupNameAdapter;//班组名称的adapter

    private boolean isShrink = false;//false=>释放 true=>收缩
    private boolean isChecked;//班组栏的选择
    private String mSemployeeno;//员工编码
    private List<WsEntity> mClassGroupBeanEntityList;//班组的源数据


    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void init() {
        mActivityMainBinding = (ActivityMainBinding) viewDataBinding;
//        mProcessWorkerBeanList=new ArrayList<>();
//        mWorkAllBeanList = new ArrayList<>();
        mTopDataBeanList = new ArrayList<>();
        mRealTimeProcessBarBeanList = new ArrayList<>();
        workerBeanList = new ArrayList<>();
        classGroupBeanSourceMap = new HashMap<>();
        classGroupBeanShowList = new ArrayList<>();
        classGroupNameList = new ArrayList<>();
//        mClassGroupBeanList=new ArrayList<>();
        mFliterStyleNumList = new ArrayList<>();
        listFliterStyleItem = new ArrayList<>();
        listFliterBatchItem = new ArrayList<>();
        mFliterBatchNumList = new ArrayList<>();
        mBatchNumBeanList = new ArrayList<>();
        mTableHeadBeanList = new ArrayList<>();
        mStyleNumBeanList = new ArrayList<>();
//        mProcessWorkerBeanList=new ArrayList<>();
        allPoints = new ArrayList<>();
        errorPoints = new ArrayList<>();
        barChart = new ArrayList<>();

//        mActivityMainBinding.tvOrderNum.setOnClickListener(this);
//        mActivityMainBinding.tvStyleNum.setOnClickListener(this);
//        mActivityMainBinding.tvBatchNum.setOnClickListener(this);
        length = MyUtils.getScreenSize(this);
//        initHead();
//        mActivityMainBinding.hdvgMain.setOnDragReleaseListener(new HSDragViewGroup.OnDragReleaseListener() {
//            @Override
//            public void dragRelease(View from, View to) {
//                Log.i("dragRelease",from+","+to);
//            }
//        });
        initHeadChard();
//
        //点击动态添加布局
//        layoutChange();
        mActivityMainBinding.tvOrderNum.setOnClickListener(this);
        mActivityMainBinding.tvStyleNum.setOnClickListener(this);
        mActivityMainBinding.tvBatchNum.setOnClickListener(this);
        mProcessWorkerAdapter = new ProcessWorkerAdapter(workerBeanList, getApplicationContext());
        mActivityMainBinding.lvWork.setAdapter(mProcessWorkerAdapter);
        mGroupAdapter = new GroupAdapter(classGroupBeanShowList, getApplicationContext());
        mActivityMainBinding.gvGroup.setAdapter(mGroupAdapter);

        classGroupNameAdapter = new ArrayAdapter<>(getApplicationContext(), R.layout.string_item,
                R.id.tvString, classGroupNameList);
        mActivityMainBinding.spGroupName.setAdapter(classGroupNameAdapter);
//        mActivityMainBinding.gvGroup.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//
//            int currentPosition;
//
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//
//                    if (!isChecked) {
//                        currentPosition = position;
//                        mActivityMainBinding.gvGroup.setClickable(true);
//                        view.setBackgroundColor(Color.DKGRAY);
//                        String workersName = classGroupBeanShowList.get(position).SEMPLOYEENAMECN;
//                        String GroupName = classGroupBeanShowList.get(position).SWORKTEAMNAME;
//                        mSemployeeno = classGroupBeanShowList.get(position).SEMPLOYEENO;
//                        OthersUtil.ToastMsg(getApplicationContext(), "已经选到" + workersName + "(" + GroupName + ")");
//                        isChecked = true;
//                    } else {
//                        view.setBackgroundColor(Color.TRANSPARENT);
//                        isChecked = false;
//                        mActivityMainBinding.gvGroup.setClickable(false);
//
//                    }
//
////                isChecked=!isChecked;
//                mGroupAdapter.notifyDataSetChanged();
//            }
//
//        });

        mActivityMainBinding.gvGroup.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                for (int i = 0; i < classGroupBeanShowList.size(); i++) {
                    if (position == i) {
                        classGroupBeanShowList.get(i).isSelected = !classGroupBeanShowList.get(i).isSelected;
                        isChecked = classGroupBeanShowList.get(i).isSelected;
                        mSemployeeno = classGroupBeanShowList.get(i).SEMPLOYEENO;
                        mGroupAdapter.notifyDataSetChanged();
                        continue;
                    }
                    classGroupBeanShowList.get(i).isSelected = false;
                    mGroupAdapter.notifyDataSetChanged();
                }
            }
        });
        mProcessWorkerAdapter.setOnSubItemClickListener(new ProcessWorkerAdapter.OnSubItemClickListener() {
            //删除员工
            @Override
            public void onSubItemClick(final View v) {
                final ProcessWorkerBean bean = (ProcessWorkerBean) v.getTag();
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setMessage("确定要把 " + bean.SEMPLOYEENAMECN + " 从 " + bean.SPARTNAME + " 组中移除吗？");
                builder.setTitle("提示");
                builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        deleteWorkers(bean.IIDEN);
                        OthersUtil.ToastMsg(MainActivity.this, ((TextView) v).getText().toString());
                        setCurrentDate(order, style, product);//根据记录后的三级条件查询
                    }
                });
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                builder.create().show();


            }

            //新增员工
            @Override
            public void onItemClick(final View v) {
                if (isChecked) {
                    final ProcessWorkerBean processWorkerBean = (ProcessWorkerBean) v.getTag();
                    v.setBackgroundColor(Color.GREEN);
                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                    builder.setMessage("确定要添加到 " + processWorkerBean.SPARTNAME + " 工序吗？");
                    builder.setTitle("提示");
                    builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            upDateWorkers(processWorkerBean.SPARTNAME, processWorkerBean.IHDRID, processWorkerBean.ICUPROCEDUREID, mSemployeeno);
                            setCurrentDate(order, style, product);//根据记录后的三级条件查询
                            isChecked = false;
                            v.setBackgroundDrawable(getResources().getDrawable(R.drawable.setbar_bg));
                            dialog.dismiss();
                        }
                    });

                    builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            v.setBackgroundDrawable(getResources().getDrawable(R.drawable.setbar_bg));
                            dialog.dismiss();
                        }
                    });
                    builder.create().show();

                }

            }
        });
        //查询工作人员
        mActivityMainBinding.tvSearch.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {
                List<ClassGroupBean> searchGroupBeen=new ArrayList<>();
                String name = mActivityMainBinding.etName.getText().toString().trim();
                for (int i = 0; i < mClassGroupBeanEntityList.size(); i++) {
                    ClassGroupBean bean = (ClassGroupBean) mClassGroupBeanEntityList.get(i);
                    if (bean.SEMPLOYEENAMECN.contains(name)){
                        OthersUtil.ToastMsg(MainActivity.this, bean.SEMPLOYEENAMECN);
                        searchGroupBeen.add(bean);
                    }
                }
                classGroupBeanShowList.clear();
                classGroupBeanShowList.addAll(searchGroupBeen);
                mGroupAdapter.notifyDataSetChanged();
                mActivityMainBinding.etName.setText("");
            }
        });


        mActivityMainBinding.spGroupName.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                for (ClassGroupBean classGroupBean : classGroupBeanShowList) {
                    classGroupBean.isSelected = false;
                }
                isChecked = false;
                showClassGroupDataByName(classGroupNameList.get(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        //收缩,释放

        mActivityMainBinding.btnShrink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                synchronized ("Shrink") {
                    mActivityMainBinding.shrinkLayout.measure(0, 0);
                    Timer timer = new Timer();
                    //释放
                    if (isShrink) {
                        ViewAnimator
                                .animate(mActivityMainBinding.shrinkLayout)
                                .alpha(0, 1)
                                .translationY(-mActivityMainBinding.shrinkLayout.getMeasuredHeight(), 0)
                                .duration(1000)
                                .start();
                        mActivityMainBinding.shrinkLayout.setVisibility(View.VISIBLE);

                        //收缩
                    } else {
                        ViewAnimator
                                .animate(mActivityMainBinding.shrinkLayout)
                                .alpha(1, 0)
                                .translationY(0, -mActivityMainBinding.shrinkLayout.getMeasuredHeight())
                                .duration(1000)
                                .start();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                mActivityMainBinding.shrinkLayout.setVisibility(View.GONE);
                            }
                        }, 1000);
                    }
                    isShrink = !isShrink;

                    if (isShrink) {
                        mActivityMainBinding.btnShrink.setText("释放");
                    } else {
                        mActivityMainBinding.btnShrink.setText("收缩");
                    }
                }
            }
        });

//        mWorkAdapter = new WorkAdapter(mWorkAllBeanList, this);
//        mGroupAdapter = new GroupAdapter(mClassGroupBeenList, this);
//        mGroupAdapter = new GroupAdapter(mWorkAllBeanList, this);
//        mActivityMainBinding.lvWork.setAdapter(mWorkAdapter);
//        mActivityMainBinding.gvGroup.setAdapter(mGroupAdapter);
//        mActivityMainBinding.tvClass.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                mGrouNumAdapter = new GroupNumAdapter(mWorkTeamNames, MainActivity.this);
//                showPop(v);
//
//            }
//        });


    }

    /**
     * 接收卡号
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getCardNum(CardNumEvent event) {
        String cardNum = event.getCardNum();
//        activityMainBinding.etMain.setText(cardNum+"");
//        setCard(cardNum);
        toast = WBUtils.toastUtils(this, "卡号：" + cardNum, toast);
        System.out.println("卡号：" + cardNum);
    }

    /**
     * 根据卡号类型判断是挡车工还是保全工
     *
     * @param
     */
//    public void setCard(final String cardNum) {
//        NetListUtils.getCardTypeByScanCard(this, new NetCallBack() {
//            @Override
//            public void success(Object o) {
//                String id = (String) o;
//                if(id.equals("0")){
////                    Intent intent = new Intent(MainActivity.this, HomeMachinistActivity.class);
////                    intent.putExtra(CARD_NUM, cardNum);
////                    startActivity(intent);
//                }else if(id.equals("1")){
////                    Intent intent2 = new Intent(MainActivity.this, HomeBQActivity.class);
////                    intent2.putExtra(CARD_NUM, cardNum);
////                    startActivity(intent2);
//                }else{
//                    toast = WBUtils.toastUtils(MainActivity.this,"卡号不正确",toast);
//                }
//            }
//            @Override
//            public void error() {
//                super.error();
//                toast = WBUtils.toastUtils(MainActivity.this,"请再试一次",toast);
//
//            }
//        }, cardNum,mDialog);
//
//    }
//初始化表头
    private void initHeadChard() {
        OthersUtil.showLoadDialog(mDialog);
        NewRxjavaWebUtils.getUIThread(NewRxjavaWebUtils.getObservable(this, "")

                //表头数据
                .map(new Func1<String, HsWebInfo>() {
                    @Override
                    public HsWebInfo call(String s) {
                        return NewRxjavaWebUtils.getJsonData(getApplicationContext(), CUS_SERVICE,
                                "spappAssignment", "iIndex =-1",
                                TopDataBean.class.getName(), true, "查询无结果！");


                    }
                }), getApplicationContext(), mDialog, new SimpleHsWeb() {
            @Override
            public void success(HsWebInfo hsWebInfo) {
                List<WsEntity> entities = hsWebInfo.wsData.LISTWSDATA;
                for (int i = 0; i < entities.size(); i++) {
                    TopDataBean topDataBean = (TopDataBean) entities.get(i);
                    mTopDataBeanList.add(topDataBean);
                }
                TopDataBean topDataBean = (TopDataBean) entities.get(0);
                mActivityMainBinding.tvOrderNum.setText(topDataBean.SORDERNO);
                mActivityMainBinding.tvStyleNum.setText(topDataBean.SSTYLENO);
                mActivityMainBinding.tvBatchNum.setText(topDataBean.SPRODUCTNO);
            }
        });
    }

    //    private void layoutChange() {
//        final String[] mWorkerName = {""};
//        mActivityMainBinding.gvGroup.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//
//
//
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
////                Adapter adapter = parent.getAdapter();
////                Map<String,String> map= (Map<String, String>) adapter.getItem(position);
//
//                ClassGroupBean classGroupBean = mClassGroupBeen.get(position);
//                mWorkerName[0] = classGroupBean.SEMPLOYEENAMECN;
//                OthersUtil.ToastMsg(MainActivity.this, "获取员工姓名:"+mWorkerName[0]);
//
//            }
//        });
//        mActivityMainBinding.lvWork.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                if( mWorkerName[0]!=null){
//                    OthersUtil.ToastMsg(MainActivity.this, "添加:"+mWorkerName[0]+"进该工序");
//                }
//                parent.removeAllViews();
//                TextView textView=new TextView(getApplicationContext());
//                textView.setText(mWorkerName[0]);
//                parent.addView(textView);
//
//                mWorkerName[0]=null;
//            }
//        });
//    }
//
//三级联动点击
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tvOrderNum:
                mOrderNumAdapter = new OrderNumAdapter(mTopDataBeanList, this);
                mOrderNumAdapter.notifyDataSetChanged();
                showPop(v);
                break;
            case tvStyleNum:
                mStyleNumAdapter = new StyleNumAdapter(mTopDataBeanList, this);
                mStyleNumAdapter.notifyDataSetChanged();
                showPop(v);
                break;
            case R.id.tv_BatchNum:
                mBatchNumAdapter = new BatchNumAdapter(mTopDataBeanList, this);
                mBatchNumAdapter.notifyDataSetChanged();
                showPop(v);
                break;

            default:
                break;
        }

    }

    /**
     * popWindow
     *
     * @param view
     */
    private void showPop(final View view) {
        View contentView = LayoutInflater.from(getApplicationContext()).inflate(R.layout.pop_list, null);
        final PopupWindow popupWindow = new PopupWindow(contentView, length[0] / 3 - 10, length[1] / 4, true);
        final ListView popListView = (ListView) contentView.findViewById(R.id.lv_pop_clothlist);
        switch (view.getId()) {
            case R.id.tvOrderNum:
                popListView.setAdapter(mOrderNumAdapter);
                break;
            case tvStyleNum:
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
                        TopDataBean topDataBean = (TopDataBean) parent.getItemAtPosition(position);
                        mActivityMainBinding.tvOrderNum.setText(topDataBean.SORDERNO);
                        listFliterStyleItem.clear();
                        listFliterBatchItem.clear();
                        //订单号对应的款号
                        for (int i = 0; i < mStyleNumBeanList.size(); i++) {
                            StyleNumBean styleNumBean = mStyleNumBeanList.get(i);
                            if (topDataBean.SORDERNO.equals(styleNumBean.SORDERNO)) {
                                listFliterStyleItem.add(styleNumBean);
                            }
                        }
                        if (listFliterStyleItem.isEmpty()) {
//                            mActivityMainBinding.tvStyleNum.setText("款号");
//                            mActivityMainBinding.tvBatchNum.setText("批次号");
                            mFliterStyleNumList.clear();
                            mFliterBatchNumList.clear();
                        } else {
                            mFliterStyleNumList.clear();
                            mFliterStyleNumList.addAll(listFliterStyleItem);
                            mActivityMainBinding.tvStyleNum.setText(listFliterStyleItem.get(0).SSTYLENO);
                            String styleNumId = listFliterStyleItem.get(0).SSTYLENO;
                            //款号对相应的批次号
                            for (int i = 0; i < mBatchNumBeanList.size(); i++) {
                                TopDataBean batchNumBean = mBatchNumBeanList.get(i);
                                if (styleNumId.equals(batchNumBean.SSTYLENO)) {
                                    listFliterBatchItem.add(batchNumBean);
                                }
                            }
                            if (listFliterBatchItem.isEmpty()) {
//                                mActivityMainBinding.tvStyleNum.setText("批次号");
                                mFliterBatchNumList.clear();
                            } else {
                                mFliterBatchNumList.clear();
                                mFliterBatchNumList.addAll(listFliterBatchItem);
                                mActivityMainBinding.tvBatchNum.setText(listFliterBatchItem.get(0).SPRODUCTNO);
                            }

                        }

                        break;
                    case tvStyleNum:
                        System.out.println("tvStyleNum");
                        TopDataBean dataBean = (TopDataBean) parent.getItemAtPosition(position);
                        //款号对相应的批次号
                        for (int i = 0; i < mBatchNumBeanList.size(); i++) {
                            TopDataBean batchNumBean = mBatchNumBeanList.get(i);

                            listFliterBatchItem.add(batchNumBean);

                        }
                        mActivityMainBinding.tvStyleNum.setText(dataBean.SSTYLENO);

                        break;
                    case R.id.tv_BatchNum:
                        System.out.println("tv_BatchNum");
                        TopDataBean bean = (TopDataBean) parent.getItemAtPosition(position);
                        mActivityMainBinding.tvBatchNum.setText(bean.SPRODUCTNO);
                        String orderNo = mActivityMainBinding.tvOrderNum.getText().toString();
                        String styleNo = mActivityMainBinding.tvStyleNum.getText().toString();
                        String productNo = mActivityMainBinding.tvBatchNum.getText().toString();
                        setCurrentDate(orderNo, styleNo, productNo);//根据三级条件的查询
                        order = orderNo;
                        style = styleNo;
                        product = productNo;
                        break;
                }
                popupWindow.dismiss();
            }
        });

    }

    /**
     * 根据三级条件查询
     *
     * @param orderNo
     * @param styleNo
     * @param productNo
     */

    @SuppressWarnings("unchecked")
    private void setCurrentDate(final String orderNo, final String styleNo, final String productNo) {
        workerBeanList.clear();
        classGroupBeanShowList.clear();
        classGroupBeanSourceMap.clear();
        OthersUtil.showLoadDialog(mDialog);
        NewRxjavaWebUtils.getUIThread(NewRxjavaWebUtils.getObservable(this, "")

                        //表头数据
                        .map(new Func1<String, HsWebInfo>() {
                            @Override
                            public HsWebInfo call(String s) {
                                HsWebInfo hsWebInfo = NewRxjavaWebUtils.getJsonData(getApplicationContext(), CUS_SERVICE,
                                        "spappAssignment", "iIndex =0" + ",sOrderNo=" + orderNo + ",sStyleNo=" +
                                                styleNo + ",sProductNo=" + productNo,
                                        TableHeadBean.class.getName(), true, "查询无结果！");
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
                                hsWebInfo = NewRxjavaWebUtils.getJsonData(getApplicationContext(), CUS_SERVICE,
                                        "spappAssignment", "iIndex =1" + ",sOrderNo=" + orderNo + ",sStyleNo=" +
                                                styleNo + ",sProductNo=" + productNo,
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
                                        "spappAssignment", "iIndex =2" + ",sOrderNo=" + orderNo + ",sStyleNo=" +
                                                styleNo + ",sProductNo=" + productNo,
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
                                        "spappAssignment", "iIndex =3" + ",sOrderNo=" + orderNo + ",sStyleNo=" +
                                                styleNo + ",sProductNo=" + productNo,
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
                        mClassGroupBeanEntityList = (List<WsEntity>) map.get("ClassGroupBean");
                        //表头信息
                        TableHeadBean tableHeadBean = (TableHeadBean) topDataBeanEntityList.get(0);
                        showTopData(tableHeadBean);
                        //柱状图信息
                        showBarData(realTimeProcessBarBeanEntityList);
                        //显示工序信息
                        showPartData(processWorkerBeanEntityList);
                        //显示班组信息
                        initClassGroupData(mClassGroupBeanEntityList);
                        if (!classGroupNameList.isEmpty())
                            showClassGroupDataByName(classGroupNameList.get(0));

                    }

                    @Override
                    public void error(HsWebInfo hsWebInfo, Context context) {
                        super.error(hsWebInfo, context);
                        mActivityMainBinding.barChart.clear();
                        mActivityMainBinding.barChart.invalidate();
                        mProcessWorkerAdapter.notifyDataSetChanged();
                        mGroupAdapter.notifyDataSetChanged();
                    }
                });
    }

    //新增员工数据
    private void upDateWorkers(final String spartname, final String ihdrid, final String icuprocedureid, final String semployeeno) {
        OthersUtil.showLoadDialog(mDialog);
        NewRxjavaWebUtils.getUIThread(NewRxjavaWebUtils.getObservable(this, "")
                .map(new Func1<String, HsWebInfo>() {
                    @Override
                    public HsWebInfo call(String s) {
                        return NewRxjavaWebUtils.getJsonData(getApplicationContext(), CUS_SERVICE,
                                "spappAssignStaffAction", "iIndex=0" + ",iHdrId=" + ihdrid + ",sPartName="
                                        + spartname + ",icuProcedureId=" + icuprocedureid
                                        + ",sEmployeeNo=" + semployeeno, WsData.class.getName(),
                                true, "");

                    }
                }), getApplicationContext(), mDialog, new SimpleHsWeb() {
            @Override
            public void success(HsWebInfo hsWebInfo) {
                String json = hsWebInfo.json;
                System.out.println("新增员工" + json);
                OthersUtil.ToastMsg(MainActivity.this, "添加成功");
                mProcessWorkerAdapter.notifyDataSetChanged();
            }

            @Override
            public void error(HsWebInfo hsWebInfo, Context context) {
                super.error(hsWebInfo, context);

            }
        });
    }

    //删除员工数据
    private void deleteWorkers(final String iIden) {
        OthersUtil.showLoadDialog(mDialog);
        NewRxjavaWebUtils.getUIThread(NewRxjavaWebUtils.getObservable(this, "")
                .map(new Func1<String, HsWebInfo>() {
                    @Override
                    public HsWebInfo call(String s) {
                        return NewRxjavaWebUtils.getJsonData(getApplicationContext(), CUS_SERVICE,
                                "spappAssignStaffAction", "iIndex=1" + ",iIden=" + iIden, WsData.class.getName(),
                                true, "");

                    }
                }), getApplicationContext(), mDialog, new SimpleHsWeb() {
            @Override
            public void success(HsWebInfo hsWebInfo) {
                System.out.println("删除员工:" + hsWebInfo);
                String smessage = hsWebInfo.wsData.SMESSAGE;
                OthersUtil.ToastMsg(MainActivity.this, "删除成功");
            }

            @Override
            public void error(HsWebInfo hsWebInfo, Context context) {
                super.error(hsWebInfo, context);

            }
        });
    }

    /**
     * 显示工序数据
     */
    private void showPartData(List<WsEntity> processWorkerBeanEntityList) {
        if (processWorkerBeanEntityList == null || processWorkerBeanEntityList.isEmpty()) return;
//        List<ProcessWorkerBean> processWorkerBeanList = new ArrayList<>();//工序组人员集合
        Map<String, List<ProcessWorkerBean>> map = new HashMap<>();

        for (WsEntity entity : processWorkerBeanEntityList) {
            ProcessWorkerBean bean = (ProcessWorkerBean) entity;
            List<ProcessWorkerBean> subList = map.get(bean.SPARTNAME);
            if (subList == null) subList = new ArrayList<>();
            subList.add(bean);
            map.put(bean.SPARTNAME, subList);
        }
        Iterator<Map.Entry<String, List<ProcessWorkerBean>>> it = map.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<String, List<ProcessWorkerBean>> entry = it.next();
            List<ProcessWorkerBean> subList = entry.getValue();
            workerBeanList.add(subList);
        }
        mProcessWorkerAdapter.notifyDataSetChanged();


//
//        List<List<ProcessWorkerBean>> listByWorkTeamName = getListByWorkTeamName(processWorkerBeanList);
//        //通过组名过滤
//
//        List<String> partNames = new ArrayList<>();//工序名称集合
//        List<String> distinctPartNames;
//        WorkGroupBean workGroupBean = new WorkGroupBean();
//        for (List<ProcessWorkerBean> list : listByWorkTeamName) {//组名集合
//            for (ProcessWorkerBean bean : list) {
//                partNames.add(bean.SPARTNAME);//工序名字
//            }
//            distinctPartNames = distinctData(partNames);
//            for (String keyName : distinctPartNames) {
//                WorkGroupBean.ProgressBean progressBean = new WorkGroupBean.ProgressBean();
//                progressBean.progressName = keyName;
//                for (ProcessWorkerBean bean : list) {
//                    if (keyName.equals(bean.SPARTNAME)) {
//                        progressBean.workerNames.add(bean.SEMPLOYEENAMECN);
//                    }
//                }
//                workGroupBean.progressBeen.add(progressBean);
//            }
//        }
////                        System.out.println(workGroupBean.progressBeen);
//        mProcessWorkerAdapter = new ProcessWorkerAdapter(workGroupBean.progressBeen, getApplicationContext());
//        mActivityMainBinding.lvWork.setAdapter(mProcessWorkerAdapter);
    }

    /**
     * 整理班组信息
     */
    private void initClassGroupData(List<WsEntity> classGroupBeanEntityList) {
        //工序组成员通过班组名称分组
//        List<ClassGroupBean> classGroupBeanList = new ArrayList<>();
        classGroupNameList.clear();
        for (WsEntity entity : classGroupBeanEntityList) {
            ClassGroupBean bean = (ClassGroupBean) entity;
            List<ClassGroupBean> subList = classGroupBeanSourceMap.get(bean.SWORKTEAMNAME);
            if (subList == null) subList = new ArrayList<>();
            subList.add(bean);
            classGroupBeanSourceMap.put(bean.SWORKTEAMNAME, subList);
        }
        Set<String> keySet = classGroupBeanSourceMap.keySet();
        Iterator<String> it = keySet.iterator();
        while (it.hasNext()) {
            classGroupNameList.add(it.next());
        }
        classGroupNameAdapter.notifyDataSetChanged();

//        mClassGroupListByWorkTeamName = getListByWorkTeamName(classGroupBeanList);
//
//        mCurrentSelectedWorkTeam = processWorkerBeanList.get(0).SWORKTEAMNAME;
//        mClassGroupBeen = getClassGroupBeenByWorkTeamName(mCurrentSelectedWorkTeam, mClassGroupListByWorkTeamName);
//        if (mClassGroupBeen != null) {
//            mGroupAdapter=new GroupAdapter(mClassGroupBeen,getApplicationContext());
//            mActivityMainBinding.gvGroup.setAdapter(mGroupAdapter);
//            mActivityMainBinding.tvClass.setText(mClassGroupBeen.get(0).SWORKTEAMNAME);
//        }
    }

    /**
     * 根据班组名显示班组信息
     */
    private void showClassGroupDataByName(String name) {
        if (name == null || name.isEmpty()) return;
        classGroupBeanShowList.clear();
        List<ClassGroupBean> list = classGroupBeanSourceMap.get(name);
        if (list == null) return;
        classGroupBeanShowList.addAll(list);
        mGroupAdapter.notifyDataSetChanged();
    }

//    private <V extends WsEntity> List<List<V>> getListByWorkTeamName(List<V> originList) {
//        List<String> workTeamNames = new ArrayList<>();
//        List<String> distinctNames = null;
//        for (V entity : originList) {
//            if (entity instanceof ProcessWorkerBean) {
//                workTeamNames.add(((ProcessWorkerBean) entity).SWORKTEAMNAME);
//            } else if (entity instanceof ClassGroupBean) {
//                workTeamNames.add(((ClassGroupBean) entity).SWORKTEAMNAME);
//            }
//            distinctNames = distinctData(workTeamNames);
//            if (distinctNames.size()>1) mWorkTeamNames = distinctNames;
//        }
//
//        List<List<V>> listByWorkTeamName = new ArrayList<>();
//        if (distinctNames != null) {
//            for (String keyName : distinctNames) {
//                List<V> list = new ArrayList<>();
//                for (V entity : originList) {
//                    if (entity instanceof ProcessWorkerBean) {
//                        if (((ProcessWorkerBean) entity).SWORKTEAMNAME.equals(keyName)) {
//                            list.add(entity);
//                        }
//                    } else if (entity instanceof ClassGroupBean) {
//                        if (((ClassGroupBean) entity).SWORKTEAMNAME.equals(keyName)) {
//                            list.add(entity);
//                        }
//                    }
//
//                }
//                listByWorkTeamName.add(list);
//            }
//        }
//        return listByWorkTeamName;
//    }

//    private List<String> distinctData(List<String> source) {
//        List<String> result = new ArrayList<>();
//        for (String str : source) {
//            if (!result.contains(str)) {
//                result.add(str);
//            }
//        }
//
//        return result;
//    }

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
        mActivityMainBinding.barChart.getAxisRight().setEnabled(false);
        xAxis = mActivityMainBinding.barChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        yAxis = mActivityMainBinding.barChart.getAxisLeft();
//      xAxis.setSpaceBetweenLabels(2);
//      yAxis.setLabelCount(8, false);
        yAxis.setDrawGridLines(false);
        mActivityMainBinding.barChart.setNoDataText("没有数据");
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

        mActivityMainBinding.barChart.setData(barData);
        OthersUtil.dismissLoadDialog(mDialog);
        mActivityMainBinding.barChart.animateXY(0, 2000);
        mActivityMainBinding.barChart.invalidate();
        barData.notifyDataChanged();
    }


    /**
     * 显示头部信息
     */
    private void showTopData(TableHeadBean tableHeadBean) {
//        mActivityMainBinding.tvOrderNum.setText(tableHeadBean.SORDERNO);
//        mActivityMainBinding.tvStyleNum.setText(tableHeadBean.SSTYLENO);
//        mActivityMainBinding.tvBatchNum.setText(tableHeadBean.SLOTNO);
        mActivityMainBinding.tvDeliveryDate.setText(tableHeadBean.DDELIVERYDATE);
        mActivityMainBinding.tvOrderQTY.setText(tableHeadBean.IORDERQTY);
        mActivityMainBinding.tvCutQTY.setText(tableHeadBean.ICUTQTY);
        mActivityMainBinding.tvUpQTY.setText(tableHeadBean.IUPQTY);
        mActivityMainBinding.tvDownQTY.setText(tableHeadBean.IDOWNQTY);
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
