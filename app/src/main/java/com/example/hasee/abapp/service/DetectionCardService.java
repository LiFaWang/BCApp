package com.example.hasee.abapp.service;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;

import com.example.hasee.abapp.event.CardNumEvent;
import com.example.hasee.abapp.port.SerialPort;
import com.example.hasee.abapp.utils.WBUtils;

import org.greenrobot.eventbus.EventBus;

import java.io.InputStream;

/**
 * 刷卡的services
 */
public class DetectionCardService extends Service {

    @Override
    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        initSerialPort();

    }
    private static Handler handler  = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            String card  = (String) msg.obj;
            CardNumEvent event = new CardNumEvent();
            event.setCardNum(card);
            EventBus.getDefault().post(event);

        }
    };
    private Boolean flag=true;
    private void initSerialPort(){
        //ttyS0
        //
        try {
            SerialPort serialPort= WBUtils.getSerialPort("/dev/ttyUSB10",9600);
//            SerialPort serialPort= WBUtils.getSerialPort("/dev/ttyS0",9600);
            final InputStream mInputStream=serialPort.getInputStream();
            new Thread(new Runnable() {
                @Override
                public void run() {
                    while (flag){
                        if (mInputStream == null) {
                            return;
                        }
                        try {
                            int sizeline = -1;
                            byte[] bufferline = new byte[64];
                            if (mInputStream == null) {
                                return;
                            }
                            sizeline = mInputStream.read(bufferline);
                            if (sizeline > 0) {
                                String tmp = WBUtils.getCardNo(bufferline, sizeline);
                                if (tmp != null) {
                                    Message msg = new Message();
                                    msg.obj=tmp;
                                    handler.sendMessage(msg);
                                }
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }).start();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        flag=false;
    }
}
