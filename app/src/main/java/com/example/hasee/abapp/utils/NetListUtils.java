package com.example.hasee.abapp.utils;

import com.trello.rxlifecycle.components.support.RxAppCompatActivity;

import huansi.net.qianjingapp.utils.SPUtils;
import huansi.net.qianjingapp.view.LoadProgressDialog;

/**
 * Created by wb on 2017/4/19 0019.
 */

public class NetListUtils {


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
//
//
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




    //员工信息
    public static void getEmployeeInfo(RxAppCompatActivity activity, final NetCallBack callBack, String cardNum, LoadProgressDialog dialog) {
        String machineNo = SPUtils.readMacId(activity);
        String str = "spappiotGetEmployeeInfoByCardNo";
        String sParaStr = "sMacNo=" + machineNo + ",sCardNo=" + cardNum;
//        RxjavaWebUtils.requestByGetJsonData(activity, CUS_SERVICE,
//                str, sParaStr,dialog,
//                TopDataBean.class.getName(), true, "", new SimpleHsWeb() {
//                    @Override
//                    public void success(HsWebInfo hsWebInfo) {
//                        List<WsEntity> entities = hsWebInfo.wsData.LISTWSDATA;
//                        TopDataBean employeeinforBean;
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

    }


}



