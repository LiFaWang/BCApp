package com.example.hasee.abapp.bean;

import huansi.net.qianjingapp.entity.WsData;

/**
 * Created by Administrator on 2017/7/7 0007.
 */

public class TopDataBean extends WsData {

    /**
     *订单号
     * [
     {
     "SORDERNO": "SO17040001",
     "SSTYLENO": "test",
     "SLOTNO": "test",
     "DDELIVERYDATE": "2017/4/16 0:00:00",
     "IORDERQTY": "600",
     "ICUTQTY": "60",
     "IUPQTY": "",
     "IDOWNQTY": "5"
     }
     ]
     */
    /**
     * {
     "STATUS": "0",
     "DATA": [
     {
     "SORDERNO": "SO17040001",
     "SSTYLENO": "test",
     "SPRODUCTNO": "49481"
     }
     ]
     }
     */

    public String SORDERNO="";
    public String SSTYLENO="";
//    public String SLOTNO="";
//    public String DDELIVERYDATE="";
//    public String IORDERQTY="";
//    public String ICUTQTY="";
//    public String IUPQTY="";
//    public String IDOWNQTY="";
    public String SPRODUCTNO="";



}
