package com.sabareesh.hotSpot;


        import java.lang.reflect.Method;
        import com.google.common.base.Preconditions;
        import android.content.Context;
        import android.net.wifi.WifiConfiguration;
        import android.net.wifi.WifiManager;
        import android.text.format.Formatter;
        import android.util.Log;

public final class HotSpotManager {
    private static final int WIFI_AP_STATE_FAILED = 4;
    private final WifiManager mWifiManager;
    private final String TAG = "Wifi Access Manager";
    private Method wifiControlMethod;
    private Method wifiApConfigurationMethod;
    private Method wifiApState;

    public HotSpotManager(Context context) throws SecurityException, NoSuchMethodException {
        context = Preconditions.checkNotNull(context);
        mWifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        wifiControlMethod = mWifiManager.getClass().getMethod("setWifiApEnabled", WifiConfiguration.class,boolean.class);
        wifiApConfigurationMethod = mWifiManager.getClass().getMethod("getWifiApConfiguration",null);

        wifiApState = mWifiManager.getClass().getMethod("getWifiApState");
    }
    public boolean setWifiApState(WifiConfiguration config, boolean enabled) {
        config = Preconditions.checkNotNull(config);
        try {
            if (enabled) {

                mWifiManager.setWifiEnabled(!enabled);

            }
            return (Boolean) wifiControlMethod.invoke(mWifiManager, config, enabled);
        } catch (Exception e) {
            Log.e(TAG, "", e);
            return false;
        }
    }
    public WifiConfiguration getWifiApConfiguration()
    {
        try{
            return (WifiConfiguration)wifiApConfigurationMethod.invoke(mWifiManager, null);
        }
        catch(Exception e)
        {
            return null;
        }
    }
    public String getIp(){
        return Formatter.formatIpAddress(mWifiManager.getConnectionInfo().getIpAddress());
    }
    public int getWifiApState() {
        try {
            return (Integer)wifiApState.invoke(mWifiManager);
        } catch (Exception e) {
            Log.e(TAG, "", e);
            return WIFI_AP_STATE_FAILED;
        }
    }
}