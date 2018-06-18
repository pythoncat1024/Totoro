package com.pythoncat.totoro.utils;

import java.util.HashMap;
import java.util.Random;
import java.util.Set;

/**
 * Created by pythonCat on 2016/5/7.
 */
public class UriUtil {

    private static final UriUtil impl = new UriUtil();

    public static final  String cat = "http://img0.imgtn.bdimg.com/it/u=2538660857,904390849&fm=21&gp=0.jpg";

    private String adImg01 = "http://www.ultraimg.com/images/141ee6.jpg";
    private String adBt01 = "http://dc.addcl.xyz/htm_data/17/1605/1925387.html";

    private String adImg02 = "http://www.ultraimg.com/images/111648.jpg";
    private String adBt02 = "http://dc.addcl.xyz/htm_data/17/1605/1925388.html";

    private String adImg03 = "http://www.ultraimg.com/images/1ddb80.jpg";
    private String adBt03 = "http://dc.addcl.xyz/htm_data/17/1604/1907724.html";

    private String adImg04 = "http://www.ultraimg.com/images/119523d.jpg";
    private String adBt04 = "http://dc.addcl.xyz/htm_data/17/1604/1907727.html";


    private String adImg05 = "http://www.ultraimg.com/images/122b276.jpg";
    private String adBt05 = "http://dc.addcl.xyz/htm_data/17/1604/1902883.html";


    private UriUtil() {
        init();
    }

    public static UriUtil impl() {
        return impl;
    }

    private void init() {
        adMaps.put(adImg01, adBt01);
        adMaps.put(adImg02, adBt02);
        adMaps.put(adImg03, adBt03);
        adMaps.put(adImg04, adBt04);
        adMaps.put(adImg05, adBt05);

    }

    private HashMap<String, String> adMaps = new HashMap<>();


    public String img() {
        Set<String> strings = adMaps.keySet();
        int size = strings.size();
        int random = new Random().nextInt(size);
        int i = 0;
        for (String key : strings) {
            if (i == random) {
                return key;
            }
            i++;
        }
        return adImg01;
    }


    public String bt(String img) {
        Set<String> strings = adMaps.keySet();
        if (!strings.contains(img))
            return adBt01;
        return adMaps.get(img);
    }
}
