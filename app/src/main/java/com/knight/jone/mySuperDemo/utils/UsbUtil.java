package com.knight.jone.mySuperDemo.utils;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.hardware.input.InputManager;
import android.os.BatteryManager;
import android.view.InputDevice;

import static android.content.Context.INPUT_SERVICE;

public class UsbUtil {

    /**
     * 判断当前USB是否连接
     *
     * @return 是否连接
     */
    public static boolean isUsbConnected(Context context) {
        IntentFilter filter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        Intent batteryStatusIntent = context.registerReceiver(null, filter);

        int status = batteryStatusIntent.getIntExtra(BatteryManager.EXTRA_STATUS, -1);
        boolean isCharging = (status == BatteryManager.BATTERY_STATUS_CHARGING) ||
                (status == BatteryManager.BATTERY_STATUS_FULL);

        int chargePlug = batteryStatusIntent.getIntExtra(BatteryManager.EXTRA_PLUGGED, -1);
        boolean usbCharge = chargePlug == BatteryManager.BATTERY_PLUGGED_USB;
        boolean acCharge = chargePlug == BatteryManager.BATTERY_PLUGGED_AC;

        Lg.d("UsbState", "Is Usb Connect:" + isCharging + "   usbCharge:" + usbCharge);
        return isCharging && usbCharge;
    }

    /**
     * 当前USB连接设备
     *
     * @param context
     */
    public static void detectUsbDeviceWithInputManager(Context context) {
        InputManager im = (InputManager) context.getSystemService(INPUT_SERVICE);
        int[] devices = im.getInputDeviceIds();
        for (int id : devices) {
            InputDevice device = im.getInputDevice(id);
            Lg.e("UsbState", "detectUsbDeviceWithInputManager: " + device.getName());
        }
    }
}
