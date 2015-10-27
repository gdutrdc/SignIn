package com.rdc.signin.utils;

import android.content.Context;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.util.Log;

import com.rdc.signin.app.SignInApp;

import java.lang.reflect.Method;
import java.util.List;

/**
 * Created by seasonyuu on 15/9/20.
 */
public class WifiController {
	private WifiManager manager;
	private boolean isWifiEnabled;

	public WifiController(Context context) {

		manager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
		isWifiEnabled = !isWifiApEnabled() && isWifiEnabled();
	}

	/**
	 * 获取wifi的打开状态
	 *
	 * @return true 为打开，false 为未打开
	 */
	public boolean isWifiEnabled() {
		return manager.isWifiEnabled();
	}

	/**
	 * 获得本设备的Mac地址，该字符串是唯一的
	 *
	 * @return Mac地址 形式为 **:**:**:**:**:**
	 */
	public String getLocalMacAddress() {
		WifiInfo wifiInfo = manager.getConnectionInfo();
		return wifiInfo.getMacAddress();
	}

	/**
	 * 设置wifi热点的开启关闭
	 *
	 * @param enabled true to enabled , false to disabled
	 */
	public void setWifiApEnabled(boolean enabled) {
		if (enabled && manager.isWifiEnabled()) {
			isWifiEnabled = true;
			setWifiEnabled(false);
		}
		WifiConfiguration apConfig = new WifiConfiguration();
		// 配置热点的名称(可以在名字后面加点随机数什么的)
		apConfig.SSID = "Teacher's Wifi AP";
		// 配置热点的密码
		apConfig.preSharedKey = getLocalMacAddress();
		// 通过反射调用设置热点
		try {
			Method method = manager.getClass().getMethod("setWifiApEnabled",
					WifiConfiguration.class, Boolean.TYPE);
			method.invoke(manager, apConfig, enabled);
		} catch (Exception e) {
			e.printStackTrace();
		}

		//如果要关闭热点，判断开启热点前wifi是否开启，若wifi是
		//开启的，则替用户打开wifi
		if (!enabled && isWifiEnabled)
			setWifiEnabled(isWifiEnabled);
	}

	public boolean isWifiApEnabled() {
		try {
			Method method = manager.getClass().getMethod("isWifiApEnabled");
			return (Boolean) method.invoke(manager);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * 判断目标Mac地址是否存在于当前扫描到的wifi列表中
	 *
	 * @param macAddress 要搜索的Mac地址
	 * @return true 表示目标存在于wifi列表中，否则返回 false
	 */
	public boolean searchMacInScanResult(String macAddress) {
		boolean flag = false;
		for (ScanResult result : getScanResults()) {
			if (macAddress.equals(result.BSSID)) {
				flag = true;
				break;
			}
		}
		return flag;
	}

	/**
	 * 设置wifi开关
	 *
	 * @param enabled true to enable , false to disable
	 */
	public void setWifiEnabled(boolean enabled) {
		if (manager.isWifiEnabled() != enabled)
			manager.setWifiEnabled(enabled);
	}

	/**
	 * 返回包含当前wifi列表的数据
	 *
	 * @return 一个包含了所有扫描到的wifi结果的list
	 */
	private List<ScanResult> getScanResults() {
		List<ScanResult> resultList;
		manager.startScan();
		resultList = manager.getScanResults();
		if (SignInApp.DEBUG)
			Log.d(WifiController.class.getSimpleName(), "resultList.size() = " + resultList.size());
		return resultList;
	}

	public boolean getWifiBack() {
		return isWifiEnabled;
	}
}
