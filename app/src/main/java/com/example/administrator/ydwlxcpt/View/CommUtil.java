package com.example.administrator.ydwlxcpt.View;

/**
 * Created by Administrator on 2018/5/10.
 */
import com.example.administrator.ydwlxcpt.Bean.Pingpai;

import java.util.ArrayList;
import java.util.List;
public class CommUtil {
    /**
     * 根据当前选中的项获取其第一次出现该项首字母的位置
     *
     * @param position 当前选中的位置
     * @param datas  数据源
     * @return
     */
    public static int getPositionForSection(int position, List<? extends Pingpai> datas) {
        // 当前选中的项
        Pingpai sideBase = datas.get(position);
        for (int i = 0; i < datas.size(); i++) {
            String firstStr = datas.get(i).getCB_FirstLetter().toUpperCase();
            // 返回第一次出现该项首字母的位置
            if (firstStr.equals(sideBase.getCB_FirstLetter())) {
                return i;
            }
        }
        return -1;
    }

    /**
     * 获取所选中的索引在列表中的位置
     *
     * @param list
     * @param letter
     * @return
     */
    public static int getLetterPosition(List<? extends Pingpai> list, String letter) {
        int position = -1;

        if (list != null && !list.isEmpty() && !"".equals(letter)) {
            for (int i = 0; i < list.size(); i++) {
                Pingpai bean = list.get(i);
                if (bean.getCB_FirstLetter().equals(letter)) {
                    position = i;
                    break;
                }
            }
        }
        return position;
    }

    /**
     * 筛选出数据源中所包含的全部索引值
     *
     * @param list
     * @return
     */
    public static String[] getLetters(List<? extends Pingpai> list) {
        List<String> letters = new ArrayList<>();
        if (list != null && !list.isEmpty()) {
            for (int i = 0; i < list.size(); i++) {
                if (!letters.contains(list.get(i).getCB_FirstLetter())) {
                    letters.add(list.get(i).getCB_FirstLetter());
                }
            }
        }
        return (String[]) letters.toArray(new String[letters.size()]);
    }

}
