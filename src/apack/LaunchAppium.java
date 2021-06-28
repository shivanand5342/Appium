package apack;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.remote.MobileCapabilityType;
import io.appium.java_client.service.local.AppiumDriverLocalService;

public class LaunchAppium{
	public static AppiumDriverLocalService service;
	public static void main(String[] args) throws MalformedURLException {
		
		//Code to launch Appium Server automatically
		service = AppiumDriverLocalService.buildDefaultService();
		service.start();
		
		//Code to just launch the ApiDemos-debug.apk
		 File appDir = new File("src");
	     File app = new File(appDir, "ApiDemos-debug.apk");
	     DesiredCapabilities capabilities = new DesiredCapabilities();
	     //Here based on Device Name scripts will decide where to execute(in emulator or in Real device)
	     capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "Pixel_2xl");
	     //capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "Android Device");
	     
	     capabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME,"uiautomator2");//new step 
	     capabilities.setCapability(MobileCapabilityType.APP, app.getAbsolutePath());
	     AndroidDriver<AndroidElement> driver = new AndroidDriver<AndroidElement>(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);
	     driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	     
		 driver.findElementByXPath("//android.widget.TextView[@text='Preference']").click();
		 driver.findElementByXPath("//android.widget.TextView[@text='3. Preference dependencies']").click();
		 driver.findElementById("android:id/checkbox").click();
		 //driver.findElementByXPath("//*[@text='WiFi settings']").click();
		 driver.findElementByXPath("(//android.widget.RelativeLayout)[2]").click();
		 driver.findElementByClassName("android.widget.EditText").sendKeys("Hello");
		 driver.findElementsByClassName("android.widget.Button").get(1).click();
		 
		 service.stop();
	}
	
	
}
