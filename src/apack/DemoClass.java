package apack;

import java.net.MalformedURLException;
    import java.net.URL;
    import java.util.concurrent.TimeUnit;
     
    import org.openqa.selenium.By;
    import org.openqa.selenium.remote.DesiredCapabilities;
    
    import io.appium.java_client.AppiumDriver;
    import io.appium.java_client.MobileElement;
    import io.appium.java_client.android.AndroidDriver;
    import io.appium.java_client.remote.MobileCapabilityType;
     
    public class DemoClass {
     
    	final static String dir = System.getProperty("user.dir");
    	static AppiumDriver driver;
    	public static void main(String[] args) throws MalformedURLException {
    		
    		DesiredCapabilities cap = new DesiredCapabilities();
    		cap.setCapability(MobileCapabilityType.DEVICE_NAME, "Pixel_2xl");
    		cap.setCapability(MobileCapabilityType.VERSION, "9.0");
    		cap.setCapability(MobileCapabilityType.PLATFORM_NAME, "Android");
    		cap.setCapability(MobileCapabilityType.BROWSER_NAME, "Chrome");
    		cap.setCapability("chromedriverExecutable", dir+"//"+"chromedriver.exe");
    		driver = new AndroidDriver<MobileElement>(new URL("http://127.0.0.1:4723/wd/hub"), cap);
    		driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
    		
    		driver.get("http://facebook.com");
     
    		
    	}
    		

	}