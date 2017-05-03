package com.android.grubox.connectionutils;

/**
 * Created by root on 30/4/16.
 */
public class URL1 {

     static String BASE_URL="http://104.199.176.31/Project/api/Manager";
    static String BASE_URL_CHECKSUM="http://104.199.176.31/Project/api/Manager/GenCheckSum/";
   // String BASE_URL="http://192.168.1.3:8000/Project/api/Manager";

    public static String getProducturl()
    {
        return BASE_URL+"/MachineStockLatest/?MachineID=2&format=json";
    }
    public static String getMachineurl()
    {
        return BASE_URL+"/Vmachine/?format=json";
    }

    public static String getUnsuccessfulVendurl()
    {
        return BASE_URL+"/UnsuccessfulVends";
    }

    public static String getWhouseurl()
    {
        return BASE_URL+"/Whouse/?format=json";
    }

    public static String getWareHouseStock()
    {
        return BASE_URL+"/WhouseStock/?Wid=";
    }

    public static String getQRCodeStock()
    {
        return BASE_URL_CHECKSUM;
    }

}
