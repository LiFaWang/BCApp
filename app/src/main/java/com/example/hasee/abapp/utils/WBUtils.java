package com.example.hasee.abapp.utils;

import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import com.example.hasee.abapp.port.SerialPort;

import java.io.File;
import java.lang.reflect.Method;
import java.text.DecimalFormat;


public class WBUtils {

    //获取当前版本号
    public static int getVersion(Context context) {
        PackageManager packageManager = context.getPackageManager();
        try {
            PackageInfo packageInfo = packageManager.getPackageInfo(
                    context.getPackageName(), PackageManager.GET_PERMISSIONS);
            return packageInfo.versionCode;
        } catch (Exception e) {
            e.printStackTrace();

        }
        return 10000;
    }
    public static String getVersionName(Context context) {
        try {
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(
                    context.getPackageName(), 0);
            return packageInfo.versionName;
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }


    public static boolean isFastDoubleClick(long lastClickTime) {
        long time = System.currentTimeMillis();
        long timeD = time - lastClickTime;
        //设置毫秒内按钮无效，这样可以控制快速点击，自己调整频率
        if (0 < timeD && timeD < 2000) return true;
        return false;
    }

    //    private static Toast toast = null;
    public static Toast toastUtils(Context context, String str, Toast toast) {
        if (toast == null) {
            toast = Toast.makeText(context, str, Toast.LENGTH_SHORT);
        } else {
            toast.setText(str);
        }
        toast.show();
        return toast;
    }

    /**
     * 获取卡号
     *
     * @param b
     * @param size
     * @return
     */
    public static String getCardNo(byte[] b, int size) {
        StringBuffer hs = new StringBuffer(size);
        String stmp = "";
        for (int i = 0; i < size; i++) {
            stmp = Integer.toHexString(b[i] & 0xFF);
            if (i > 6 && i < 11) {
                if (stmp.length() == 1) {
                    hs.append("0").append(stmp);
                } else {
                    hs.append(stmp);
                }
            }
        }
        return to10(hs.toString());
    }

    /**
     * 16转10
     *
     * @param number
     * @return
     */
    public static String to10(String number) {
        String str = "";
        try {
            str = Long.parseLong(number, 16) + "";
        } catch (Exception e) {

        }
        return str; //转10进制
    }

    /**
     * 保留两位小数
     */
    public static double saveDot(int digit, double number) {
        StringBuffer sbZeros = new StringBuffer();
        sbZeros.append("#.");
        for (int i = 0; i < digit; i++) {
            sbZeros.append("0");
        }
        DecimalFormat df = new DecimalFormat(sbZeros.toString());
        return Double.parseDouble(df.format(number));
    }

    /**
     * 获取串口对象
     *
     * @param path
     * @param baudrate
     * @return
     */
    public static SerialPort getSerialPort(String path, int baudrate) {
        SerialPort mSerialPort = null;
        try {
            /* Read serial port parameters */
            mSerialPort = new SerialPort(new File(path), baudrate, 0);
            return mSerialPort;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mSerialPort;
    }

//    //软件盘
//    //数字键盘
//    public void showPop(View view, AppCompatActivity activity) {
//        View contentView = LayoutInflater.from(activity).inflate(
//                R.layout.keyboard, null);
//        PopupWindow popupWindow = new PopupWindow(contentView,
//                500,
//                400, true);
//        popupWindow.setTouchable(true);
//        ColorDrawable dw = new ColorDrawable(0x000);
//        popupWindow.setBackgroundDrawable(dw);
//        popupWindow.showAsDropDown(view);
//
//    }

    /**
     * 将秒转换为xx小时xx分钟xx秒
     */
    public static String getHourMinSecBySeconds(long seconds) {
        if (seconds < 60) return seconds + "秒";
        if (seconds < 60 * 60) return (seconds / 60) + "分钟" + (seconds % 60) + "秒";
        long hour = seconds / (60 * 60);
        long secExceptHour = seconds % (60 * 60);//除去小时的秒数
        long min = secExceptHour / 60;
        long sec = secExceptHour % 60;
        return hour + "小时" + min + "分钟" + sec + "秒";

    }

    //获取editText 光标
    public static void hideSoftInputMethod(EditText ed, AppCompatActivity a) {
        a.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        int currentVersion = android.os.Build.VERSION.SDK_INT;
        String methodName = null;
        if (currentVersion >= 16) {
            // 4.2
            methodName = "setShowSoftInputOnFocus";

        } else if (currentVersion >= 14) {
            // 4.0
            methodName = "setSoftInputShownOnFocus";

        }
        if (methodName == null) {
            ed.setInputType(InputType.TYPE_NULL);
        } else {
            Class<EditText> cls = EditText.class;

            Method setShowSoftInputOnFocus;

            try {

                setShowSoftInputOnFocus = cls.getMethod(methodName, boolean.class);

                setShowSoftInputOnFocus.setAccessible(true);

                setShowSoftInputOnFocus.invoke(ed, false);

            } catch (NoSuchMethodException e) {

                ed.setInputType(InputType.TYPE_NULL);

                e.printStackTrace();

            } catch (Exception e) {

                e.printStackTrace();

            }

        }

    }


    //显示基本注册AlertDialog
    public static void showDialog(Context context, String s) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("注册");
        builder.setMessage(s);
        builder.setNegativeButton("确定",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {

                    }
                });
        builder.show();
    }

//    //显示基本机台号AlertDialog
//    public static void showNoDialog(final Context context) {
//        final EditText et = new EditText(context);
//        final AlertDialog.Builder builder = new AlertDialog.Builder(context);
//        builder.setTitle("请输入任务号初始化系统：");
//        builder.setView(et);
//        builder.setNeutralButton("取消",new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//
//            }
//        });
//        builder.setPositiveButton("确定",null);
//        final AlertDialog alertDialog = builder.create();
//        alertDialog.setCancelable(false);
//        alertDialog.show();
//        alertDialog.getButton(DialogInterface.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String planId = et.getText().toString();
//                NetListUtils.initPlanId((RxAppCompatActivity) context, new NetCallBack() {
//                    @Override
//                    public void success(Object o) {
//                      alertDialog.cancel();
//                    }
//                    @Override
//                    public void error() {
//                        super.error();
//                    }
//                },planId);
//            }
//        });



//    }
    private static final String HEX_STRING = "0123456789ABCDEF";

    public static String toHexString(byte[] b) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < b.length; i++) {
            if (b[i] == ' ') continue;
            sb.append(HEX_STRING.charAt(b[i] >>> 4 & 0x0F));
            sb.append(HEX_STRING.charAt(b[i] & 0x0F));
        }
        return sb.toString().trim();
    }
    /**
     * 显示转速
     */
//    private  void showTurnSpeed(){
//        synchronized("speed") {
//            if (isClose || mTurnList.size() < 2) {
//                tvSpeed.setText("0转/min");
//                return;
//            }
//            Item lastCloseItem = null;//上次关闭
//            Item lastItem = mTurnList.get(0);//最近一转
//            Item beforeLastItem=null;//计算速率的最远的一转
//            for (int i = 0; i < mStatusList.size(); i++) {
//                Item statusItem = mStatusList.get(i);
//                if (statusItem.s.contains("停机")) {
//                    lastCloseItem = statusItem;
//                }
//            }
//            int size=Math.min(mTurnList.size(),10);
//            if(lastCloseItem==null){
//                beforeLastItem = mTurnList.get(size-1);
//            }else {
//                for(int i=0;i<size;i++){
//                    Item item=mTurnList.get(i);
//                    try {
//                        long lastCloseTime=TimeUtils.getMillTime(lastCloseItem.time);
//                        long itemTime=TimeUtils.getMillTime(item.time);
//                        if(lastCloseTime>itemTime){
//                            beforeLastItem=item;
//                            break;
//                        }else {
//                            if(i==size-1){
//                                beforeLastItem=item;
//                                break;
//                            }
//                        }
//                    } catch (ParseException e) {
//                        if(i==size-1){
//                            beforeLastItem=item;
//                            break;
//                        }
//                    }
//                }
//            }
//
//
//            long lastCloseTime = 0;
//            try {
//                if (lastCloseItem != null) {
//                    lastCloseTime = TimeUtils.getMillTime(lastCloseItem.time);
//                }
//                long lastTime = TimeUtils.getMillTime(lastItem.time);
//                long beforeLastTime = TimeUtils.getMillTime(beforeLastItem.time);
//                if (lastCloseTime > beforeLastTime) {
//                    tvSpeed.setText(0 + "转/min");
//                    return;
//                }
//                long difference = Math.abs(lastTime - beforeLastTime);
//                double speed = (1.0d / difference) * (60.0d * 1000) * (mTurnList.size() < 10 ? (mTurnList.size() - 1) : 9);
//                speed = saveDot(1, speed);
//                tvSpeed.setText(speed + "转/min");
//            } catch (ParseException e) {
//                tvSpeed.setText("0转/min");
//            }
//        }
//    }
}
