
package com.common.utils;
import android.text.TextUtils;
import android.view.accessibility.AccessibilityNodeInfo;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.common.Print;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;

/**
 * 用于获取json字符串中指定key的value
 */
public class UtilJson {
    
    public interface IBingo {
        void onBingo(Object obj);
        void onFinished();
    }


    /**
     * 通过Path路径找到jsonObject对象，目前这个还不可以模糊匹配
     * @param obj
     * @param path
     * @return
     */
    public static void findJsonObjByAbsPath(JSONObject obj, String path, IBingo bingo) {
        path = path.trim();
        boolean isStartWith = path.startsWith("/");
        boolean isEndWith = path.endsWith("/");
        path = path.substring(isStartWith ? 1 : 0, isEndWith ? path.length()-1 : path.length());
        String[] keys = path.split("/");
        if (keys.length > 0) {
            findJsonObjByAbsPath__(obj, keys, 0, bingo);
        }
        bingo.onFinished();
    }
    
    private static void findJsonObjByAbsPath__(Object originObj, final String[] keys, int index, IBingo bingo) {
        String key = index < keys.length ? keys[index] : "";
        if (TextUtils.isEmpty(key)) {
            bingo.onBingo(originObj);
            return;
        }
        if (originObj == null) {
            return;
        }
        if (originObj instanceof JSONObject) {
            try {
                findJsonObjByAbsPath__(((JSONObject) originObj).get(key), keys, ++index, bingo);
            } catch (ClassCastException e) {
            }
        } else if (originObj instanceof JSONArray) {
            for (int j = 0; j < ((JSONArray) originObj).size(); j++) {
                try {
                    findJsonObjByAbsPath__(((JSONArray) originObj).getJSONObject(j), keys, ++index, bingo);
                } catch (ClassCastException e) { 
                }
            }
        } else { }
    }


    /**
     * 根据某一个key值，模糊搜索匹配，找到所在的JsonObject对象，找到了之后，通过IBingo返回数据
     * @param obj
     * @param key
     * @param bingo
     */
    public static void findJsonObjByKey(JSONObject obj, String key, IBingo bingo) {
        findJsonObjByKey__(obj, key, bingo);
        bingo.onFinished();
    }
    private static void findJsonObjByKey__(Object obj, String key, IBingo bingo) {
        if (obj instanceof JSONObject) {
            for (String k : ((JSONObject) obj).keySet()) {
                if (!TextUtils.isEmpty(k) && k.equals(key)) {
                    bingo.onBingo(((JSONObject) obj).get(k));
                } else {
                    findJsonObjByKey__(((JSONObject) obj).get(k), key, bingo);
                }
            }
        } else if (obj instanceof JSONArray) {
            for (int j = 0; j < ((JSONArray) obj).size(); j++) {
                JSONObject jsonObject = null;
                try {
                    jsonObject = ((JSONArray) obj).getJSONObject(j);
                } catch (ClassCastException e) {
                }
                if (jsonObject != null) {
                    for (String k : ((JSONObject) jsonObject).keySet()) {
                        if (!TextUtils.isEmpty(k) && k.equals(key)) {
                            bingo.onBingo(((JSONObject) obj).get(k));
                        } else {
                            findJsonObjByKey__(((JSONObject) obj).get(k), key, bingo);
                        }
                    }
                }
            }
        } else { }
    }


}
