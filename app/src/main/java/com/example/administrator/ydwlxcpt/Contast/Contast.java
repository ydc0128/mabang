package com.example.administrator.ydwlxcpt.Contast;

import com.example.administrator.ydwlxcpt.Bean.Dingdan;
import com.example.administrator.ydwlxcpt.Bean.Massage;
import com.example.administrator.ydwlxcpt.Bean.Pingpai;
import com.example.administrator.ydwlxcpt.Bean.TitleInfo;
import com.example.administrator.ydwlxcpt.Bean.UpdateBean;
import com.example.administrator.ydwlxcpt.Bean.User;
import com.example.administrator.ydwlxcpt.Bean.Worker;
import com.example.administrator.ydwlxcpt.Bean.YHJMingxi;
import com.example.administrator.ydwlxcpt.Bean.Youhuiquan;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/8/5.
 */

public class Contast {
    public static User user = new User();
    public static TitleInfo titleInfo = new TitleInfo();
    public static String Domain = "https://api.mabangxiche.com/";//正式环境
//   public static String Domain="192.168.0.166:12124/";//测试环境
    public static String APP_ID = "wxb422cb98e22ae6d3";//wxb422cb98e22ae6d3
    public static String APP_KEY = "SHANXImabangwangluokejigongsi029";//商户秘钥
    public static String Pingpai;
    public static String Xinghao;
    public static boolean flag = false;
    public static boolean show = false;
    public static final String KEYS = "ald25e4fe86d4gv4bthj419t6yu4j6w56wty9";
    public static YHJMingxi yhjMingxi=new YHJMingxi();
    public static Massage massage=new Massage();
    public static Worker worker = new Worker();
    public static Dingdan dingdan=new Dingdan();
    public static Youhuiquan youhuiquan=new Youhuiquan();
    public static UpdateBean updateBean=new UpdateBean();
    public static Pingpai pingpai=new Pingpai();
}
