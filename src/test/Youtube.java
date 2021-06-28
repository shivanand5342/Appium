package test;

import java.net.MalformedURLException;
import java.net.URL;

import org.openqa.selenium.remote.DesiredCapabilities;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.remote.MobileCapabilityType;
import io.appium.java_client.service.local.AppiumDriverLocalService;

public class Youtube {
	public static AppiumDriverLocalService service;
	public static void main(String[] args) throws MalformedURLException {
		
		//Code to launch Appium Server automatically
			service = AppiumDriverLocalService.buildDefaultService();
			service.start();
		  DesiredCapabilities capabilities = new DesiredCapabilities();
		    
		//Here based on Device Name scripts will decide where to execute(in emulator or in Real device)
		   // capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "Pixel_2xl");
		    capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "Android Device");
		    capabilities.setCapability("appPackage", "com.google.android.dialer");
		    capabilities.setCapability("appActivity", "com.android.dialer/.main.impl.MainActivity");
		
		    capabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME,"uiautomator2");//new step 
		    AndroidDriver<AndroidElement> driver = new AndroidDriver<AndroidElement>(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);
		
		    
	}	

}
