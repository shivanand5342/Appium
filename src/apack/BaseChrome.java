package apack;

import java.net.MalformedURLException;
import java.net.URL;

import org.openqa.selenium.remote.DesiredCapabilities;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.remote.MobileCapabilityType;

public class BaseChrome {

	public static AndroidDriver<AndroidElement> cap() throws MalformedURLException {
		DesiredCapabilities capability = new DesiredCapabilities();
		AndroidDriver<AndroidElement> driver = new AndroidDriver<>(new URL("http://127.0.0.1:4723/wd/hub"), capability);
		
		capability.setCapability(MobileCapabilityType.DEVICE_NAME, "Android Device");
		capability.setCapability(MobileCapabilityType.BROWSER_NAME, "Chrome");
		return driver;
	}
}
