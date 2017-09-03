//package com.example.hasee.abapp.utils;
//
//import android.content.Context;
//
//import com.trello.rxlifecycle.components.support.RxAppCompatActivity;
//
//import net.huansi.gwapp.bean.BQGetActionBean;
//import net.huansi.gwapp.bean.BQStartStateBean;
//import net.huansi.gwapp.bean.BQTJParaHistoryBean;
//import net.huansi.gwapp.bean.CardTypeBean;
//import net.huansi.gwapp.bean.ClothNoBean;
//import net.huansi.gwapp.bean.ConfirmCallState;
//import net.huansi.gwapp.bean.DCGCallListBean;
//import net.huansi.gwapp.bean.DCGetTaskInfoBean;
//import net.huansi.gwapp.bean.DCStateBean;
//import net.huansi.gwapp.bean.EmployeeInfoBean;
//import net.huansi.gwapp.bean.EndReasonListBean;
//import net.huansi.gwapp.bean.HomeNoticeBean;
//import net.huansi.gwapp.bean.HomeTaskBean;
//import net.huansi.gwapp.bean.PendingTaskBean;
//import net.huansi.gwapp.bean.ProcessBean;
//import net.huansi.gwapp.bean.StopReasonListBean;
//import net.huansi.gwapp.bean.VersionUpdateBean;
//import net.huansi.gwapp.listenter.NetCallBack;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import huansi.net.qianjingapp.entity.HsWebInfo;
//import huansi.net.qianjingapp.entity.WsEntity;
//import huansi.net.qianjingapp.imp.SimpleHsWeb;
//import huansi.net.qianjingapp.utils.RxjavaWebUtils;
//import huansi.net.qianjingapp.utils.SPUtils;
//import huansi.net.qianjingapp.view.LoadProgressDialog;
//
//import static huansi.net.qianjingapp.utils.WebServices.WebServiceType.HS_SERVICE;
//
///**
// * Created by wb on 2017/4/19 0019.
// */
//
//public class NetListUtils {
//
//    //版本升级
//    public static void versionUpdate(RxAppCompatActivity activity, final NetCallBack callBack,LoadProgressDialog dialog) {
//        RxjavaWebUtils.requestByGetJsonData(activity, HS_SERVICE,
//                "spappiotOnlineUpgrade",
//                "",
//                dialog,
//                VersionUpdateBean.class.getName(),
//                true,
//                "更新版本失败",
//                new SimpleHsWeb() {
//                    @Override
//                    public void success(HsWebInfo hsWebInfo) {
//                        List<WsEntity> entities = hsWebInfo.wsData.LISTWSDATA;
//                        for (int i = 0; i < entities.size(); i++) {
//                            VersionUpdateBean versionUpdateBean = (VersionUpdateBean) entities.get(i);
//                            callBack.success(versionUpdateBean);
//                        }
//                    }
//
//                    @Override
//                    public void error(HsWebInfo hsWebInfo, Context context) {
//                        super.error(hsWebInfo, context);
//                        callBack.error();
//                    }
//                });
//    }
//
//    //首页任务号初始化
//    public static void initPlanId(RxAppCompatActivity activity, final NetCallBack callBack, String sNextTaskNo, LoadProgressDialog dialog) {
//        String machineNo = SPUtils.readMacId(activity);
//        RxjavaWebUtils.requestByGetJsonData(activity,
//                HS_SERVICE,
//                "spappiotMacFirstInitTask",
//                "sMacNo=" + machineNo + ",sNextTaskNo=" + sNextTaskNo,
//                dialog,
//                DCStateBean.class.getName(), true, "", new SimpleHsWeb() {
//                    @Override
//                    public void success(HsWebInfo hsWebInfo) {
//                        List<WsEntity> entities = hsWebInfo.wsData.LISTWSDATA;
//                        for (int i = 0; i < entities.size(); i++) {
//                            DCStateBean dCStateBean = (DCStateBean) entities.get(i);
//                            callBack.success(dCStateBean.SMSG);
//                        }
//                    }
//
//                    @Override
//                    public void error(HsWebInfo hsWebInfo, Context context) {
//                        super.error(hsWebInfo, context);
//                        callBack.error();
//                    }
//                });
//    }
//
//    //刷卡类型
//    public static void getCardTypeByScanCard(RxAppCompatActivity activity, final NetCallBack callBack, String cardNo, LoadProgressDialog dialog) {
//        String machineNo = SPUtils.readMacId(activity);
//        RxjavaWebUtils.requestByGetJsonData(activity, HS_SERVICE,
//                "spappiotGetCardTypeByScanCard", "sMacNo=" + machineNo + ",sCardNo=" + cardNo,dialog,
//                CardTypeBean.class.getName(), true, "", new SimpleHsWeb() {
//                    @Override
//                    public void success(HsWebInfo hsWebInfo) {
//                        List<WsEntity> entities = hsWebInfo.wsData.LISTWSDATA;
//                        for (int i = 0; i < entities.size(); i++) {
//                            CardTypeBean cardId = (CardTypeBean) entities.get(i);
////                            icardtype = cardId.ICARDTYPE;
//                            callBack.success(cardId.ICARDTYPE);
//                        }
//                    }
//
//                    @Override
//                    public void error(HsWebInfo hsWebInfo, Context context) {
//                        super.error(hsWebInfo, context);
//                        callBack.error();
//                    }
//                });
//    }
//
//    //首页信息
//    public static void HomeTaskInfo(RxAppCompatActivity activity, final NetCallBack callBack,LoadProgressDialog dialog) {
//        String machineNo = SPUtils.readMacId(activity);
//        RxjavaWebUtils.requestByGetJsonData(activity, HS_SERVICE,
//                "spappiotHomeTaskInfo", "sMacNo=" + machineNo,dialog,
//                HomeTaskBean.class.getName(), true, "", new SimpleHsWeb() {
//                    @Override
//                    public void success(HsWebInfo hsWebInfo) {
//                        List<WsEntity> entities = hsWebInfo.wsData.LISTWSDATA;
//                        HomeTaskBean homeTaskBean = new HomeTaskBean();
//                        for (int i = 0; i < entities.size(); i++) {
//                            homeTaskBean = (HomeTaskBean) entities.get(i);
//                            callBack.success(homeTaskBean);
//                        }
//                    }
//
//                    @Override
//                    public void error(HsWebInfo hsWebInfo, Context context) {
//                        super.error(hsWebInfo, context);
//                        callBack.error();
//                    }
//                });
//    }
//
////    //首页信息
////    public static void HomeTaskInfo2(RxAppCompatActivity activity, LoadProgressDialog dialog, final NetCallBack callBack) {
////        String machineNo = SPUtils.readMacId(activity);
////        RxjavaWebUtils.requestByGetJsonData2(activity, HS_SERVICE,
////                "spappiotHomeTaskInfo", "sMacNo=" + machineNo, dialog,
////                HomeTaskBean.class.getName(), true, "", new SimpleHsWeb() {
////                    @Override
////                    public void success(HsWebInfo hsWebInfo) {
////                        List<WsEntity> entities = hsWebInfo.wsData.LISTWSDATA;
////                        HomeTaskBean homeTaskBean = new HomeTaskBean();
////                        for (int i = 0; i < entities.size(); i++) {
////                            homeTaskBean = (HomeTaskBean) entities.get(i);
////                            callBack.success(homeTaskBean);
////                        }
////                    }
////
////                    @Override
////                    public void error(HsWebInfo hsWebInfo, Context context) {
////                        super.error(hsWebInfo, context);
////                        callBack.error();
////                    }
////                });
////    }
//
//    //机台提示信息
//    public static void homeNoticeInfo(RxAppCompatActivity activity, final NetCallBack callBack,LoadProgressDialog dialog) {
//        String machineNo = SPUtils.readMacId(activity);
//        RxjavaWebUtils.requestByGetJsonData(activity, HS_SERVICE,
//                "spappiotHomeNoticeInfo", "sMacNo=" + machineNo,dialog,
//                HomeNoticeBean.class.getName(), true, "", new SimpleHsWeb() {
//                    @Override
//                    public void success(HsWebInfo hsWebInfo) {
//                        List<WsEntity> entities = hsWebInfo.wsData.LISTWSDATA;
//                        HomeNoticeBean homeNoticeBean = new HomeNoticeBean();
//                        for (int i = 0; i < entities.size(); i++) {
//                            homeNoticeBean = (HomeNoticeBean) entities.get(i);
//                        }
//                        callBack.success(homeNoticeBean);
//                    }
//
//                    @Override
//                    public void error(HsWebInfo hsWebInfo, Context context) {
//                        super.error(hsWebInfo, context);
//                        callBack.error();
//                    }
//                });
//    }
//
//    //机台列表任务
//    public static void planList(RxAppCompatActivity activity, final NetCallBack callBack,LoadProgressDialog dialog) {
//        String machineNo = SPUtils.readMacId(activity);
//        RxjavaWebUtils.requestByGetJsonData(activity, HS_SERVICE,
//                "spappiotMacPendingTask", "sMacNo=" + machineNo + ",sUserNo="+"",dialog,
//                PendingTaskBean.class.getName(), true, "", new SimpleHsWeb() {
//                    @Override
//                    public void success(HsWebInfo hsWebInfo) {
//                        List<PendingTaskBean> data = new ArrayList<>();
//                        List<WsEntity> entities = hsWebInfo.wsData.LISTWSDATA;
//                        for (int i = 0; i < entities.size(); i++) {
//                            PendingTaskBean pendingTaskBean = (PendingTaskBean) entities.get(i);
//                            data.add(pendingTaskBean);
//                        }
//                        callBack.success(data);
//                    }
//
//                    @Override
//                    public void error(HsWebInfo hsWebInfo, Context context) {
////                        super.error(hsWebInfo, context);
//                        callBack.error(hsWebInfo);
//                    }
//                });
//    }
//
//    //工艺信息
//    public static void getProcess(RxAppCompatActivity activity,
//                                  final NetCallBack callBack, String planId, LoadProgressDialog dialog) {
//        String machineNo = SPUtils.readMacId(activity);
//        RxjavaWebUtils.requestByGetJsonData(activity, HS_SERVICE,
//                "spappiotMacTaskArtInfo", "sMacNo=" + machineNo + ",sTaskNo=" + planId + ",sUserNo=null",
//                dialog,
//                ProcessBean.class.getName(), true, "", new SimpleHsWeb() {
//                    @Override
//                    public void success(HsWebInfo hsWebInfo) {
//                        List<WsEntity> entities = hsWebInfo.wsData.LISTWSDATA;
//                        for (int i = 0; i < entities.size(); i++) {
//                            ProcessBean processBean = (ProcessBean) entities.get(i);
//                            callBack.success(processBean);
//                        }
//
//                    }
//
//                    @Override
//                    public void error(HsWebInfo hsWebInfo, Context context) {
//                        super.error(hsWebInfo, context);
//                        callBack.error();
//                    }
//                });
//
//    }
//
//    //保全工读取 调机和试验状态
//    public static void getbqstate(RxAppCompatActivity activity,
//                                  final NetCallBack callBack, String planid, String cardNo, LoadProgressDialog dialog) {
//        String machineNo = SPUtils.readMacId(activity);
//        RxjavaWebUtils.requestByGetJsonData(activity, HS_SERVICE,
//                "spappiotBQGetActionInfo", "sMacNo=" + machineNo + ",sTaskNo=" + planid + ",sCardNo=" + cardNo,dialog,
//                BQGetActionBean.class.getName(), true, "", new SimpleHsWeb() {
//                    @Override
//                    public void success(HsWebInfo hsWebInfo) {
//                        List<WsEntity> entities = hsWebInfo.wsData.LISTWSDATA;
//                        BQGetActionBean bqGetActionBean = new BQGetActionBean();
//                        for (int i = 0; i < entities.size(); i++) {
//                            bqGetActionBean = (BQGetActionBean) entities.get(i);
//                            callBack.success(bqGetActionBean);
//                        }
//                    }
//
//                    @Override
//                    public void error(HsWebInfo hsWebInfo, Context context) {
//                        super.error(hsWebInfo, context);
//                        callBack.error();
//                    }
//                });
//    }
//
//    //开始调机
//    public static void bqStartTJ(RxAppCompatActivity activity, final NetCallBack callBack, String planid, String cardNo, LoadProgressDialog dialog) {
//        String machineNo = SPUtils.readMacId(activity);
//
//        RxjavaWebUtils.requestByGetJsonData(activity, HS_SERVICE,
//                "spappiotBQStartTJ", "sMacNo=" + machineNo + ",sTaskNo=" + planid + ",sCardNo=" + cardNo,dialog,
//                BQStartStateBean.class.getName(), true, "", new SimpleHsWeb() {
//                    @Override
//                    public void success(HsWebInfo hsWebInfo) {
//                        List<WsEntity> entities = hsWebInfo.wsData.LISTWSDATA;
//                        for (int i = 0; i < entities.size(); i++) {
//                            BQStartStateBean bqStartStateBean = (BQStartStateBean) entities.get(i);
//                            callBack.success(bqStartStateBean);
//                        }
//                    }
//
//                    @Override
//                    public void error(HsWebInfo hsWebInfo, Context context) {
//                        super.error(hsWebInfo, context);
//                        callBack.error();
//                    }
//                });
//    }
//
//    //开始试验
//    public static void bqStartSY(RxAppCompatActivity activity, final NetCallBack callBack, String planid, String cardNo, LoadProgressDialog dialog) {
//        String machineNo = SPUtils.readMacId(activity);
//        RxjavaWebUtils.requestByGetJsonData(activity, HS_SERVICE,
//                "spappiotBQStartSY", "sMacNo=" + machineNo + ",sTaskNo=" + planid + ",sCardNo=" + cardNo,dialog,
//                BQStartStateBean.class.getName(), true, "", new SimpleHsWeb() {
//                    @Override
//                    public void success(HsWebInfo hsWebInfo) {
//                        List<WsEntity> entities = hsWebInfo.wsData.LISTWSDATA;
//                        for (int i = 0; i < entities.size(); i++) {
//                            BQStartStateBean bqStartStateBean = (BQStartStateBean) entities.get(i);
//                            callBack.success(bqStartStateBean);
//                        }
//                    }
//
//                    @Override
//                    public void error(HsWebInfo hsWebInfo, Context context) {
//                        super.error(hsWebInfo, context);
//                        callBack.error();
//                    }
//                });
//    }
//
//    //挡车工对当前任务的各工序数据
//    public static void dcGetTaskInfo(RxAppCompatActivity activity, final NetCallBack callBack, String planid, String cardNo, LoadProgressDialog dialog) {
//        String machineNo = SPUtils.readMacId(activity);
//        RxjavaWebUtils.requestByGetJsonData(activity, HS_SERVICE,
//                "spappiotDCGetTaskWPInfo", "sMacNo=" + machineNo + ",sTaskNo=" + planid + ",sCardNo=" + cardNo,dialog,
//                DCGetTaskInfoBean.class.getName(), true, "", new SimpleHsWeb() {
//                    @Override
//                    public void success(HsWebInfo hsWebInfo) {
//                        List<WsEntity> entities = hsWebInfo.wsData.LISTWSDATA;
//                        DCGetTaskInfoBean dcGetTaskInfo = new DCGetTaskInfoBean();
//                        for (int i = 0; i < entities.size(); i++) {
//                            dcGetTaskInfo = (DCGetTaskInfoBean) entities.get(i);
//                            callBack.success(dcGetTaskInfo);
//                        }
//                    }
//
//                    @Override
//                    public void error(HsWebInfo hsWebInfo, Context context) {
//                        super.error(hsWebInfo, context);
//                        callBack.error();
//                    }
//                });
//    }
//
//    //员工信息
//    public static void getEmployeeInfo(RxAppCompatActivity activity, final NetCallBack callBack, String cardNum, LoadProgressDialog dialog) {
//        String machineNo = SPUtils.readMacId(activity);
//        String str = "spappiotGetEmployeeInfoByCardNo";
//        String sParaStr = "sMacNo=" + machineNo + ",sCardNo=" + cardNum;
//        RxjavaWebUtils.requestByGetJsonData(activity, HS_SERVICE,
//                str, sParaStr,dialog,
//                EmployeeInfoBean.class.getName(), true, "", new SimpleHsWeb() {
//                    @Override
//                    public void success(HsWebInfo hsWebInfo) {
//                        List<WsEntity> entities = hsWebInfo.wsData.LISTWSDATA;
//                        EmployeeInfoBean employeeinforBean;
//                        for (int i = 0; i < entities.size(); i++) {
//                            employeeinforBean = (EmployeeInfoBean) entities.get(i);
//                            callBack.success(employeeinforBean);
//                        }
//
//                    }
//
//                    @Override
//                    public void error(HsWebInfo hsWebInfo, Context context) {
//                        super.error(hsWebInfo, context);
//                        callBack.error();
//                    }
//                });
//
//    }
//
//    //上纱开始结束保存
//    public static void upYarnSave(RxAppCompatActivity activity, final NetCallBack callBack,
//                                  String sTaskNo, String cardNo, String action, LoadProgressDialog dialog) {
//        String machineNo = SPUtils.readMacId(activity);
//        RxjavaWebUtils.requestByGetJsonData(activity, HS_SERVICE,
//                "spappiotDCGYarnUpSave", "sMacNo=" + machineNo +
//                                        ",sTaskNo=" + sTaskNo +
//                                        ",sCardNo=" + cardNo +
//                                        ",sAction=" + action,
//                dialog,
//                DCStateBean.class.getName(), true, "此操作失败了", new SimpleHsWeb() {
//                    @Override
//                    public void success(HsWebInfo hsWebInfo) {
//                        callBack.success("ok");
//                    }
//
//                    @Override
//                    public void error(HsWebInfo hsWebInfo, Context context) {
//                        super.error(hsWebInfo, context);
//                    }
//                });
//    }
//
//    //CREATE PROCEDURE dbo.spappiotDCGCutFabricSave
////(
////    @sMacNo NVARCHAR(50)		--机台编号
////	,@sTaskNo NVARCHAR(50)		--任务号
////	,@sCardNo NVARCHAR(50)		--员工卡号
////	,@iTurnsNum INT				--转数圈数
////	,@iManualTurnsNum INT		--手工输入的卷数
////	,@nFabircWeight DECIMAL(9,2)	--布重
////	,@sFabricNo NVARCHAR(50)	--布号
////)
//    //落布保存
//    public static void cutFabricSave(RxAppCompatActivity activity,
//                                     final NetCallBack callBack,
//                                     String sTaskNo,
//                                     String cardNo,
//                                     String iTurnsNum,
//                                     String iManualTurnsNum,
//                                     String nFabircWeight,
//                                     String sFabricNo,
//                                     LoadProgressDialog dialog) {
//        String machineNo = SPUtils.readMacId(activity);
//        RxjavaWebUtils.requestByGetJsonData(activity, HS_SERVICE,
//                "spappiotDCGCutFabricSave",
//                        "sMacNo=" + machineNo +
//                        ",sTaskNo=" + sTaskNo +
//                                ",sCardNo=" + cardNo +
//                        ",iTurnsNum=" + iTurnsNum + ",iManualTurnsNum=" + iManualTurnsNum +
//                        ",nFabircWeight=" + nFabircWeight + ",sFabricNo=" + sFabricNo,dialog,
//                ConfirmCallState.class.getName(), true, "", new SimpleHsWeb() {
//                    @Override
//                    public void success(HsWebInfo hsWebInfo) {
//                        List<WsEntity> entities = hsWebInfo.wsData.LISTWSDATA;
//                        ConfirmCallState ConfirmCallState = new ConfirmCallState();
//                        for (int i = 0; i < entities.size(); i++) {
//                            ConfirmCallState = (ConfirmCallState) entities.get(i);
//                        }
//                        callBack.success(ConfirmCallState);
//                    }
//
//                    @Override
//                    public void error(HsWebInfo hsWebInfo, Context context) {
//                        super.error(hsWebInfo, context);
//                        callBack.error();
//                    }
//                });
//    }
//
//    //获取布号列表
//    public static void getcolthList(RxAppCompatActivity activity, final NetCallBack callBack, String sTaskNo, String cardNo, LoadProgressDialog dialog) {
//        String machineNo = SPUtils.readMacId(activity);
//        RxjavaWebUtils.requestByGetJsonData(activity, HS_SERVICE,
//                "spappiotDCGGetFabricNoList", "sMacNo=" + machineNo + ",sTaskNo=" + sTaskNo + ",sCardNo=" + cardNo,dialog,
//                ClothNoBean.class.getName(), true, "", new SimpleHsWeb() {
//                    @Override
//                    public void success(HsWebInfo hsWebInfo) {
//                        List<WsEntity> entities = hsWebInfo.wsData.LISTWSDATA;
//                        List<ClothNoBean> data = new ArrayList<>();
//                        for (int i = 0; i < entities.size(); i++) {
//                            ClothNoBean clothNoBean = (ClothNoBean) entities.get(i);
//                            data.add(clothNoBean);
//                        }
//                        callBack.success(data);
//                    }
//
//                    @Override
//                    public void error(HsWebInfo hsWebInfo, Context context) {
//                        super.error(hsWebInfo, context);
//                        callBack.error();
//                    }
//                });
//    }
//
//    //确认开机
//    public static void confirmBoot(RxAppCompatActivity activity, final NetCallBack callBack, String sTaskNo, String cardNo, LoadProgressDialog dialog) {
//        String machineNo = SPUtils.readMacId(activity);
//        RxjavaWebUtils.requestByGetJsonData(activity, HS_SERVICE,
//                "spappiotDCGToBulkSave", "sMacNo=" + machineNo + ",sTaskNo=" + sTaskNo + ",sCardNo=" + cardNo,dialog,
//                DCStateBean.class.getName(), true, "", new SimpleHsWeb() {
//                    @Override
//                    public void success(HsWebInfo hsWebInfo) {
//                        List<WsEntity> entities = hsWebInfo.wsData.LISTWSDATA;
//                        callBack.success(entities);
//                    }
//
//                    @Override
//                    public void error(HsWebInfo hsWebInfo, Context context) {
//                        super.error(hsWebInfo, context);
//                        callBack.error();
//                    }
//                });
//    }
//
//    //确认了机
//    public static void confirmEnd(RxAppCompatActivity activity, final NetCallBack callBack, String sTaskNo, String cardNo, LoadProgressDialog dialog) {
//        String machineNo = SPUtils.readMacId(activity);
//        RxjavaWebUtils.requestByGetJsonData(activity, HS_SERVICE,
//                "spappiotDCGTaskFinishSave", "sMacNo=" + machineNo + ",sTaskNo=" + sTaskNo + ",sCardNo=" + cardNo,dialog,
//                DCStateBean.class.getName(), true, "", new SimpleHsWeb() {
//                    @Override
//                    public void success(HsWebInfo hsWebInfo) {
//                        List<WsEntity> entities = hsWebInfo.wsData.LISTWSDATA;
//                        callBack.success(entities);
//                    }
//
//                    @Override
//                    public void error(HsWebInfo hsWebInfo, Context context) {
//                        super.error(hsWebInfo, context);
//                        callBack.error();
//                    }
//                });
//    }
//
//    //确认换品种
//    public static void confirmChange(RxAppCompatActivity activity, final NetCallBack callBack, String sTaskNo, String cardNo, LoadProgressDialog dialog) {
//        String machineNo = SPUtils.readMacId(activity);
//        RxjavaWebUtils.requestByGetJsonData(activity, HS_SERVICE,
//                "spappiotDCGChangeVarietySave", "sMacNo=" + machineNo + ",sTaskNo=" + sTaskNo + ",sCardNo=" + cardNo,dialog,
//                DCStateBean.class.getName(), true, "", new SimpleHsWeb() {
//                    @Override
//                    public void success(HsWebInfo hsWebInfo) {
//                        callBack.success("");
//                    }
//
//                    @Override
//                    public void error(HsWebInfo hsWebInfo, Context context) {
//                        super.error(hsWebInfo, context);
//                        callBack.error();
//                    }
//                });
//    }
//
//    //停机原因列表
//    public static void getEndReasonList(RxAppCompatActivity activity, final NetCallBack callBack, String sTaskNo, String cardNo, LoadProgressDialog dialog) {
//        String machineNo = SPUtils.readMacId(activity);
//        RxjavaWebUtils.requestByGetJsonData(activity, HS_SERVICE,
//                "spappiotDCGGetStopReasonList", "sMacNo=" + machineNo + ",sTaskNo=" + sTaskNo + ",sCardNo=" + cardNo,dialog,
//                StopReasonListBean.class.getName(), true, "请刷新", new SimpleHsWeb() {
//                    @Override
//                    public void success(HsWebInfo hsWebInfo) {
//                        List<WsEntity> entities = hsWebInfo.wsData.LISTWSDATA;
//                        List<StopReasonListBean> data = new ArrayList<>();
//                        for (int i = 0; i < entities.size(); i++) {
//                            StopReasonListBean stopReasonListBean = (StopReasonListBean) entities.get(i);
//                            data.add(stopReasonListBean);
//                        }
//                        callBack.success(data);
//                    }
//
//                    @Override
//                    public void error(HsWebInfo hsWebInfo, Context context) {
//                        super.error(hsWebInfo, context);
//                        callBack.error();
//                    }
//                });
//    }
//
//    //停机原因保存
//    public static void StopReasonSave(RxAppCompatActivity activity, final NetCallBack callBack, String sTaskNo,
//                                      String cardNo, String reason, String nCurrTurnsNum, LoadProgressDialog dialog) {
//        String machineNo = SPUtils.readMacId(activity);
//        RxjavaWebUtils.requestByGetJsonData(activity, HS_SERVICE,
//                "spappiotDCGStopReasonSave", "sMacNo=" + machineNo + ",sTaskNo=" + sTaskNo + ",sCardNo=" + cardNo + ",sReasonCode=" + reason + ",nCurrTurnsNum=" + nCurrTurnsNum,
//                dialog,
//                DCStateBean.class.getName(), true, "", new SimpleHsWeb() {
//                    @Override
//                    public void success(HsWebInfo hsWebInfo) {
//                        List<WsEntity> entities = hsWebInfo.wsData.LISTWSDATA;
//                        for (int i = 0; i < entities.size(); i++) {
//                            DCStateBean d = (DCStateBean) entities.get(i);
//                            callBack.success(d);
//                        }
//                    }
//
//                    @Override
//                    public void error(HsWebInfo hsWebInfo, Context context) {
//                        super.error(hsWebInfo, context);
//                        callBack.error();
//                    }
//                });
//    }
//
//    //停机原因历史
//    public static void getEndReasonHisList(RxAppCompatActivity activity, final NetCallBack callBack,
//                                           String sTaskNo, String cardNo, LoadProgressDialog dialog) {
//        String machineNo = SPUtils.readMacId(activity);
//
//        RxjavaWebUtils.requestByGetJsonData(activity, HS_SERVICE,
//                "spappiotDCGGetTaskStopedReasonList",
//                "sMacNo=" + machineNo + ",sTaskNo=" + sTaskNo + ",sCardNo=" + cardNo,
//                dialog,
//                EndReasonListBean.class.getName(), true, "", new SimpleHsWeb() {
//                    @Override
//                    public void success(HsWebInfo hsWebInfo) {
//                        List<WsEntity> entities = hsWebInfo.wsData.LISTWSDATA;
//                        List<EndReasonListBean> data = new ArrayList<EndReasonListBean>();
//                        for (int i = 0; i < entities.size(); i++) {
//                            EndReasonListBean endReasonListBean = (EndReasonListBean) entities.get(i);
//                            data.add(endReasonListBean);
//                        }
//                        callBack.success(data);
//                    }
//
//                    @Override
//                    public void error(HsWebInfo hsWebInfo, Context context) {
//                        super.error(hsWebInfo, context);
//                        callBack.error();
//                    }
//                });
//    }
//
//    //保存停机子原因
//    public static void saveChildReason(RxAppCompatActivity activity, final NetCallBack callBack,
//                                       String sTaskNo, String sCardNo, int iIden,
//                                       String sSubStopReason, LoadProgressDialog dialog) {
//        String machineNo = SPUtils.readMacId(activity);
//        RxjavaWebUtils.requestByGetJsonData(activity, HS_SERVICE,
//                "spappiotDCGGetTaskSaveSubStopReason", "sMacNo=" + machineNo +
//                        ",sTaskNo=" + sTaskNo + ",sCardNo=" + sCardNo +
//                        ",iIden=" + iIden + ",sSubStopReason=" + sSubStopReason,
//                dialog,
//                DCStateBean.class.getName(), true, "", new SimpleHsWeb() {
//                    @Override
//                    public void success(HsWebInfo hsWebInfo) {
//                        List<WsEntity> entities = hsWebInfo.wsData.LISTWSDATA;
//                        callBack.success(entities);
//                    }
//
//                    @Override
//                    public void error(HsWebInfo hsWebInfo, Context context) {
//                        super.error(hsWebInfo, context);
//                        callBack.error();
//                    }
//                });
//    }
//
//    //换班保存
//    public static void ChangeShiftSave(RxAppCompatActivity activity, final NetCallBack callBack,
//                                       String sTaskNo, String newcard,
//                                       String currOutput, String currTurnsNum, LoadProgressDialog dialog) {
//        String machineNo = SPUtils.readMacId(activity);
//        RxjavaWebUtils.requestByGetJsonData(activity, HS_SERVICE,
//                "spappiotDCGChangeShiftSave", "sMacNo=" + machineNo +
//                        ",sTaskNo=" + sTaskNo + ",sNewCardNo=" + newcard +
//                        ",nCurrOutput=" + currOutput + ",nCurrTurnsNum=" + currTurnsNum,
//                dialog,
//                DCStateBean.class.getName(), true, "", new SimpleHsWeb() {
//                    @Override
//                    public void success(HsWebInfo hsWebInfo) {
//                        List<WsEntity> entities = hsWebInfo.wsData.LISTWSDATA;
//                        callBack.success(entities);
//                    }
//
//                    @Override
//                    public void error(HsWebInfo hsWebInfo, Context context) {
//                        super.error(hsWebInfo, context);
//                        callBack.error();
//                    }
//                });
//    }
//
//    //呼叫列表
//    public static void getDCGCallList(RxAppCompatActivity activity, final NetCallBack callBack, String sTaskNo, String cardNo, LoadProgressDialog dialog) {
//        String machineNo = SPUtils.readMacId(activity);
//        RxjavaWebUtils.requestByGetJsonData(activity, HS_SERVICE,
//                "spappiotDCGCallList", "sMacNo=" + machineNo + ",sTaskNo=" + sTaskNo + ",sCardNo=" + cardNo,dialog,
//                DCGCallListBean.class.getName(), true, "", new SimpleHsWeb() {
//                    @Override
//                    public void success(HsWebInfo hsWebInfo) {
//                        List<WsEntity> entities = hsWebInfo.wsData.LISTWSDATA;
//                        List<DCGCallListBean> data = new ArrayList<>();
//                        for (int i = 0; i < entities.size(); i++) {
//                            DCGCallListBean dCGCallListBean = (net.huansi.gwapp.bean.DCGCallListBean) entities.get(i);
//                            data.add(dCGCallListBean);
//                        }
//                        callBack.success(data);
//                    }
//
//                    @Override
//                    public void error(HsWebInfo hsWebInfo, Context context) {
//                        super.error(hsWebInfo, context);
//                        callBack.error();
//                    }
//                });
//    }
//
//    //新呼叫
//    public static void newCall(RxAppCompatActivity activity, final NetCallBack callBack, String sTaskNo, String sCardNo, LoadProgressDialog dialog) {
//        String machineNo = SPUtils.readMacId(activity);
//        RxjavaWebUtils.requestByGetJsonData(activity, HS_SERVICE,
//                "spappiotDCGNewCall", "sMacNo=" + machineNo + ",sTaskNo=" + sTaskNo + ",sCardNo=" + sCardNo,dialog,
//                DCStateBean.class.getName(), true, "", new SimpleHsWeb() {
//                    @Override
//                    public void success(HsWebInfo hsWebInfo) {
//                        List<WsEntity> entities = hsWebInfo.wsData.LISTWSDATA;
//                        callBack.success(entities);
//                    }
//
//                    @Override
//                    public void error(HsWebInfo hsWebInfo, Context context) {
//                        super.error(hsWebInfo, context);
//                        callBack.error();
//                    }
//                });
//    }
//
//    //确认呼叫
//    public static void confirmCall(RxAppCompatActivity activity, final NetCallBack callBack, String sCardNo, String iCallIden, LoadProgressDialog dialog) {
//        String machineNo = SPUtils.readMacId(activity);
//        RxjavaWebUtils.requestByGetJsonData(activity, HS_SERVICE,
//                "spappiotDCGConfirmCall", "sMacNo=" + machineNo + ",sCardNo=" + sCardNo + ",iCallIden=" + iCallIden,dialog,
//                ConfirmCallState.class.getName(), true, "", new SimpleHsWeb() {
//                    @Override
//                    public void success(HsWebInfo hsWebInfo) {
//                        List<WsEntity> entities = hsWebInfo.wsData.LISTWSDATA;
//                        for (int i = 0; i < entities.size(); i++) {
//                            ConfirmCallState confirmCallState = (ConfirmCallState) entities.get(i);
//                            callBack.success(confirmCallState);
//                        }
//                    }
//
//                    @Override
//                    public void error(HsWebInfo hsWebInfo, Context context) {
//                        super.error(hsWebInfo, context);
//                        callBack.error();
//                    }
//                });
//    }
//
//    //调机信息保存
//    public static void bqTJParaSave(RxAppCompatActivity activity, final NetCallBack callBack, String sTaskNo
//            , String sCardNo, String sPara1, String sPara2, String sPara3, String sPara4, String sPara5, LoadProgressDialog dialog) {
//        String machineNo = SPUtils.readMacId(activity);
//        RxjavaWebUtils.requestByGetJsonData(activity, HS_SERVICE,
//                "spappiotBQTJParaSave", "sMacNo=" + machineNo + ",sTaskNo=" + sTaskNo + ",sCardNo=" + sCardNo
//                        + ",sPara1=" + sPara1 + ",sPara2=" + sPara2 + ",sPara3=" + sPara3 + ",sPara4=" + sPara4 + ",sPara5=" + sPara5,
//                dialog,
//                BQStartStateBean.class.getName(), true, "", new SimpleHsWeb() {
//                    @Override
//                    public void success(HsWebInfo hsWebInfo) {
//                        List<WsEntity> entities = hsWebInfo.wsData.LISTWSDATA;
//                        for (int i = 0; i < entities.size(); i++) {
//                            BQStartStateBean bqStartStateBean = (BQStartStateBean) entities.get(i);
//                            callBack.success(bqStartStateBean);
//                        }
//                    }
//
//                    @Override
//                    public void error(HsWebInfo hsWebInfo, Context context) {
//                        super.error(hsWebInfo, context);
//                        callBack.error();
//                    }
//                });
//    }
//
//    //调机信息列表
//    public static void getBQTJParaHistory(RxAppCompatActivity activity, final NetCallBack callBack, String sTaskNo, String sCardNo, LoadProgressDialog dialog) {
//        String machineNo = SPUtils.readMacId(activity);
//        RxjavaWebUtils.requestByGetJsonData(activity, HS_SERVICE,
//                "spappiotBQTJParaHistory", "sMacNo=" + machineNo + ",sTaskNo=" + sTaskNo + ",sCardNo=" + sCardNo,dialog,
//                BQTJParaHistoryBean.class.getName(), true, "网络状态差", new SimpleHsWeb() {
//                    @Override
//                    public void success(HsWebInfo hsWebInfo) {
//                        List<WsEntity> entities = hsWebInfo.wsData.LISTWSDATA;
//                        List<BQTJParaHistoryBean> data = new ArrayList<>();
//                        for (int i = 0; i < entities.size(); i++) {
//                            BQTJParaHistoryBean bqTJParaHistoryBean = (BQTJParaHistoryBean) entities.get(i);
//                            data.add(bqTJParaHistoryBean);
//                        }
//                        callBack.success(data);
//                    }
//
//                    @Override
//                    public void error(HsWebInfo hsWebInfo, Context context) {
//                        super.error(hsWebInfo, context);
//                        callBack.error();
//                    }
//    });
//
//    }
//    //机器状态保存
//    public static void setMacState(RxAppCompatActivity activity, final NetCallBack callBack, String sTaskNo, String sStatus, int nCurrTurnsNum, LoadProgressDialog dialog) {
//        String machineNo = SPUtils.readMacId(activity);
//        RxjavaWebUtils.requestByGetJsonData(activity, HS_SERVICE,
//                "spappiotTaskDtlStatusSave", "sMacNo=" + machineNo + ",sTaskNo=" + sTaskNo + ",sCardNo="
//                +",sStatus="+sStatus+",nCurrTurnsNum="+nCurrTurnsNum,dialog,
//                DCStateBean.class.getName(), true, "网络状态差", new SimpleHsWeb() {
//                    @Override
//                    public void success(HsWebInfo hsWebInfo) {
//                        List<WsEntity> entities = hsWebInfo.wsData.LISTWSDATA;
//                        for (int i = 0; i < entities.size(); i++) {
//                            DCStateBean dcStateBean = (DCStateBean) entities.get(i);
//                            callBack.success(dcStateBean);
//                        }
//
//                    }
//
//                    @Override
//                    public void error(HsWebInfo hsWebInfo, Context context) {
//                        super.error(hsWebInfo, context);
//                        callBack.error();
//                    }
//                });
//
//    }
//
//}
//
//
//
