package com.android.grubox.databaseutils;

import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by root on 28/1/17.
 */
public class Utility {



    public static List<Integer> getCategory(String catTag, String percepTag)
    {
        List<Integer> categories=new ArrayList<>();

        if(catTag.contentEquals("AB")||catTag.contentEquals("NAB")||catTag.contentEquals("D"))
        {
            categories.add(1);
        }
        if(catTag.contentEquals("CH")||catTag.contentEquals("OTH")||catTag.contentEquals("NOOD"))
        {
            categories.add(0);
        }
        if(catTag.contentEquals("CAK")||catTag.contentEquals("CHOC"))
        {
            categories.add(2);
        }
        if(percepTag.contentEquals("H"))
        {
            categories.add(3);
        }
        return categories;


    }

    public static byte[] getBytesOfProduct(int rowcol)
    {
        HashMap<Integer, byte[]> map = new HashMap<Integer, byte[]>();

        map.put(11,new byte[] { 0x01, 0x09, 0x03, 0x01, 0x01, 0x00, 0x00, 0x00, 0x00, 0x00, 0x01, 0x0B });
        map.put(12,new byte[] { 0x01, 0x09, 0x03, 0x01, 0x01, 0x00, 0x00, 0x00, 0x00, 0x00, 0x01, 0x0B });
        map.put(13,new byte[] { 0x01, 0x09, 0x03, 0x01, 0x03, 0x00, 0x00, 0x00, 0x00, 0x00, 0x01, 0x09 });
        map.put(14,new byte[] { 0x01, 0x09, 0x03, 0x01, 0x03, 0x00, 0x00, 0x00, 0x00, 0x00, 0x01, 0x09 });
        map.put(15,new byte[] { 0x01, 0x09, 0x03, 0x01, 0x05, 0x00, 0x00, 0x00, 0x00, 0x00, 0x01, 0x0F });
        map.put(16,new byte[] { 0x01, 0x09, 0x03, 0x01, 0x05, 0x00, 0x00, 0x00, 0x00, 0x00, 0x01, 0x0F });
        map.put(17,new byte[] { 0x01, 0x09, 0x03, 0x01, 0x07, 0x00, 0x00, 0x00, 0x00, 0x00, 0x01, 0x0D });
        map.put(18,new byte[] { 0x01, 0x09, 0x03, 0x01, 0x07, 0x00, 0x00, 0x00, 0x00, 0x00, 0x01, 0x0D });
        map.put(19,new byte[] { 0x01, 0x09, 0x03, 0x01, 0x09, 0x00, 0x00, 0x00, 0x00, 0x00, 0x01, 0x03 });


        map.put(21,new byte[] { 0x01, 0x09, 0x03, 0x01, 0x11, 0x00, 0x00, 0x00, 0x00, 0x00, 0x01, 0x1B });
        map.put(22,new byte[] { 0x01, 0x09, 0x03, 0x01, 0x11, 0x00, 0x00, 0x00, 0x00, 0x00, 0x01, 0x1B });
        map.put(23,new byte[] { 0x01, 0x09, 0x03, 0x01, 0x13, 0x00, 0x00, 0x00, 0x00, 0x00, 0x01, 0x19 });
        map.put(24,new byte[] { 0x01, 0x09, 0x03, 0x01, 0x13, 0x00, 0x00, 0x00, 0x00, 0x00, 0x01, 0x19 });
        map.put(25,new byte[] { 0x01, 0x09, 0x03, 0x01, 0x15, 0x00, 0x00, 0x00, 0x00, 0x00, 0x01, 0x1F });
        map.put(26,new byte[] { 0x01, 0x09, 0x03, 0x01, 0x15, 0x00, 0x00, 0x00, 0x00, 0x00, 0x01, 0x1F });
        map.put(27,new byte[] { 0x01, 0x09, 0x03, 0x01, 0x17, 0x00, 0x00, 0x00, 0x00, 0x00, 0x01, 0x1D });
        map.put(28,new byte[] { 0x01, 0x09, 0x03, 0x01, 0x17, 0x00, 0x00, 0x00, 0x00, 0x00, 0x01, 0x1D });
        map.put(29,new byte[] { 0x01, 0x09, 0x03, 0x01, 0x19, 0x00, 0x00, 0x00, 0x00, 0x00, 0x01, 0x13 });

        map.put(31,new byte[] { 0x01, 0x09, 0x03, 0x01, 0x21, 0x00, 0x00, 0x00, 0x00, 0x00, 0x01, 0x2B });
        map.put(32,new byte[] { 0x01, 0x09, 0x03, 0x01, 0x21, 0x00, 0x00, 0x00, 0x00, 0x00, 0x01, 0x2B });
        map.put(33,new byte[] { 0x01, 0x09, 0x03, 0x01, 0x23, 0x00, 0x00, 0x00, 0x00, 0x00, 0x01, 0x29 });
        map.put(34,new byte[] { 0x01, 0x09, 0x03, 0x01, 0x24, 0x00, 0x00, 0x00, 0x00, 0x00, 0x01, 0x2E });
        map.put(35,new byte[] { 0x01, 0x09, 0x03, 0x01, 0x25, 0x00, 0x00, 0x00, 0x00, 0x00, 0x01, 0x2F });
        map.put(36,new byte[] { 0x01, 0x09, 0x03, 0x01, 0x25, 0x00, 0x00, 0x00, 0x00, 0x00, 0x01, 0x2F });
        map.put(37,new byte[] { 0x01, 0x09, 0x03, 0x01, 0x27, 0x00, 0x00, 0x00, 0x00, 0x00, 0x01, 0x2D });
        map.put(38,new byte[] { 0x01, 0x09, 0x03, 0x01, 0x27, 0x00, 0x00, 0x00, 0x00, 0x00, 0x01, 0x2D });
        map.put(39,new byte[] { 0x01, 0x09, 0x03, 0x01, 0x29, 0x00, 0x00, 0x00, 0x00, 0x00, 0x01, 0x23 });

        map.put(41,new byte[] { 0x01, 0x09, 0x03, 0x01, 0x31, 0x00, 0x00, 0x00, 0x00, 0x00, 0x01, 0x3B });
        map.put(42,new byte[] { 0x01, 0x09, 0x03, 0x01, 0x32, 0x00, 0x00, 0x00, 0x00, 0x00, 0x01, 0x38 });
        map.put(43,new byte[] { 0x01, 0x09, 0x03, 0x01, 0x33, 0x00, 0x00, 0x00, 0x00, 0x00, 0x01, 0x39 });
        map.put(44,new byte[] { 0x01, 0x09, 0x03, 0x01, 0x34, 0x00, 0x00, 0x00, 0x00, 0x00, 0x01, 0x2E });
        map.put(45,new byte[] { 0x01, 0x09, 0x03, 0x01, 0x35, 0x00, 0x00, 0x00, 0x00, 0x00, 0x01, 0x3F });
        map.put(46,new byte[] { 0x01, 0x09, 0x03, 0x01, 0x36, 0x00, 0x00, 0x00, 0x00, 0x00, 0x01, 0x3C });
        map.put(47,new byte[] { 0x01, 0x09, 0x03, 0x01, 0x37, 0x00, 0x00, 0x00, 0x00, 0x00, 0x01, 0x3D });
        map.put(48,new byte[] { 0x01, 0x09, 0x03, 0x01, 0x38, 0x00, 0x00, 0x00, 0x00, 0x00, 0x01, 0x32 });
        map.put(49,new byte[] { 0x01, 0x09, 0x03, 0x01, 0x39, 0x00, 0x00, 0x00, 0x00, 0x00, 0x01, 0x33 });

        map.put(51,new byte[] { 0x01, 0x09, 0x03, 0x01, 0x41, 0x00, 0x00, 0x00, 0x00, 0x00, 0x01, 0x4B });
        map.put(52,new byte[] { 0x01, 0x09, 0x03, 0x01, 0x42, 0x00, 0x00, 0x00, 0x00, 0x00, 0x01, 0x48 });
        map.put(53,new byte[] { 0x01, 0x09, 0x03, 0x01, 0x43, 0x00, 0x00, 0x00, 0x00, 0x00, 0x01, 0x49 });
        map.put(54,new byte[] { 0x01, 0x09, 0x03, 0x01, 0x44, 0x00, 0x00, 0x00, 0x00, 0x00, 0x01, 0x4E });
        map.put(55,new byte[] { 0x01, 0x09, 0x03, 0x01, 0x45, 0x00, 0x00, 0x00, 0x00, 0x00, 0x01, 0x4F });
        map.put(56,new byte[] { 0x01, 0x09, 0x03, 0x01, 0x46, 0x00, 0x00, 0x00, 0x00, 0x00, 0x01, 0x4C });
        map.put(57,new byte[] { 0x01, 0x09, 0x03, 0x01, 0x47, 0x00, 0x00, 0x00, 0x00, 0x00, 0x01, 0x4D });
        map.put(58,new byte[] { 0x01, 0x09, 0x03, 0x01, 0x48, 0x00, 0x00, 0x00, 0x00, 0x00, 0x01, 0x42 });
        map.put(59,new byte[] { 0x01, 0x09, 0x03, 0x01, 0x49, 0x00, 0x00, 0x00, 0x00, 0x00, 0x01, 0x43 });

        map.put(61,new byte[] { 0x01, 0x09, 0x03, 0x01, 0x51, 0x00, 0x00, 0x00, 0x00, 0x00, 0x01, 0x5B });
        map.put(62,new byte[] { 0x01, 0x09, 0x03, 0x01, 0x52, 0x00, 0x00, 0x00, 0x00, 0x00, 0x01, 0x58 });
        map.put(63,new byte[] { 0x01, 0x09, 0x03, 0x01, 0x53, 0x00, 0x00, 0x00, 0x00, 0x00, 0x01, 0x59 });
        map.put(64,new byte[] { 0x01, 0x09, 0x03, 0x01, 0x54, 0x00, 0x00, 0x00, 0x00, 0x00, 0x01, 0x5E });
        map.put(65,new byte[] { 0x01, 0x09, 0x03, 0x01, 0x55, 0x00, 0x00, 0x00, 0x00, 0x00, 0x01, 0x5F });
        map.put(66,new byte[] { 0x01, 0x09, 0x03, 0x01, 0x56, 0x00, 0x00, 0x00, 0x00, 0x00, 0x01, 0x5C });
        map.put(67,new byte[] { 0x01, 0x09, 0x03, 0x01, 0x57, 0x00, 0x00, 0x00, 0x00, 0x00, 0x01, 0x5D });
        map.put(68,new byte[] { 0x01, 0x09, 0x03, 0x01, 0x58, 0x00, 0x00, 0x00, 0x00, 0x00, 0x01, 0x52 });
        map.put(69,new byte[] { 0x01, 0x09, 0x03, 0x01, 0x59, 0x00, 0x00, 0x00, 0x00, 0x00, 0x01, 0x53 });
        Log.v("Request",""+rowcol);
        return map.get(rowcol);
    }
}
