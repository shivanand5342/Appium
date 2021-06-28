package test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.logging.LogEntry;
import org.openqa.selenium.remote.DesiredCapabilities;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.android.GsmCallActions;
import io.appium.java_client.remote.MobileCapabilityType;
import io.appium.java_client.service.local.AppiumDriverLocalService;


public class CallTest {
	public static AppiumDriverLocalService service;
	static String fileName;
public static void main(String[] args) throws Exception {
	//Code to launch Appium Server automatically
	service = AppiumDriverLocalService.buildDefaultService();
	service.start();
	String mob="9686999571";
	int callDuration=60;
	
    DesiredCapabilities capabilities = new DesiredCapabilities();
//Here based on Device Name scripts will decide where to execute(in emulator or in Real device)
    // capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "Pixel_2xl");
    capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "Android Device");
    capabilities.setCapability("appPackage", "com.android.dialer");
    //capabilities.setCapability("appActivity", "com.android.contacts.activities.DialtactsActivity");
    capabilities.setCapability("appActivity", "com.android.dialer.app.DialtactsActivity");
    capabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME,"uiautomator2");//new step 
    AndroidDriver<AndroidElement> driver = new AndroidDriver<AndroidElement>(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);
    

    captureLog(driver,"Logcat");
    driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    Thread.sleep(3000);
    //WebElement TurnON = driver.findElementByXPath("//*[@text='TURN ON']");
    
    boolean res = false;
    try{
    	driver.findElementByXPath("//*[@text='TURN ON']").isDisplayed();
    }catch(Exception e){
    	res=false;;
    }
    
    if(res) {
    	driver.findElementByXPath("//*[@text='Recents']").click();
        driver.findElementByXPath("//*[@text='TURN ON']").click();
        driver.findElementByXPath("//android.widget.Button[@text='ALLOW']").click();
        driver.findElementByXPath("//android.widget.Button[@text='ALLOW']").click();
        driver.findElementByXPath("//android.widget.Button[@text='ALLOW']").click();	
    }
    driver.findElementById("com.android.dialer:id/fab").click();
    
    for(int i =0;i<mob.length();i++) {
		driver.findElementByXPath("//android.widget.TextView[@text="+mob.charAt(i)+"]").click();
	}
    
    /*
    driver.findElementByXPath("//android.widget.TextView[@text='4']").click();
    driver.findElementById("com.android.dialer:id/nine").click();
  */
    Thread.sleep(3000);
    driver.findElementById("com.android.dialer:id/dialpad_floating_action_button").click();
    Thread.sleep(3000);
    waitTill_CallDuration(callDuration);
    Thread.sleep(2000);
//Check the call status through adb command and update in result sheet
	if(callState().equals("Idle_State")) {
	   System.out.println("call in idle state");
	   }else {
		       System.out.println("in call ");
	         }
	
    //driver.findElementById("com.android.dialer:id/incall_end_call").click();
	try {
		Runtime.getRuntime().exec("adb shell input keyevent \"KEYCODE_ENDCALL\"");
	} catch (IOException e) {e.printStackTrace();}
	
    Thread.sleep(3000);
    new CallTest().testCaseReuslt(fileName);
    
    System.out.println("Completed");
		
    service.stop();
}

public static void captureLog(AppiumDriver driver, String testName)
	    throws Exception {
	    DateFormat df = new SimpleDateFormat("dd_MM_yyyy_HH-mm-ss");
	    Date today = Calendar.getInstance().getTime();
	    String reportDate = df.format(today);
	    String logPath = ".//";
	    System.out.println(driver.getSessionId() + ": Saving device log...");
	    List<LogEntry> logEntries = driver.manage().logs().get("logcat").filter(Level.ALL);
	    fileName = logPath+reportDate+"-"+testName+".txt";
	    File logFile = new File(fileName);
	    PrintWriter log_file_writer = new PrintWriter(logFile);
	    log_file_writer.println(logEntries );
	    log_file_writer.flush();
	    System.out.println(driver.getSessionId() + ": Saving device log - Done.");
	    }

public void testCaseReuslt(String fileName) {
	File file = new File(fileName);

	try {
	    Scanner scanner = new Scanner(file);
	    //now read the file line by line...
	    int lineNum = 0;
	    while (scanner.hasNextLine()) {
	        String line = scanner.nextLine();
	        lineNum++;
	        if(line.contains("setCallState DIALING -> ACTIVE")) { 
	            System.out.println("Call Pass, string found on line " +lineNum);
	        }else {System.out.println("Call Failed, string not found in file");}
	    }
	} catch(FileNotFoundException e) { }
}

//Check Class state
	public static String callState() {
		//PropertyConfigurator.configure(dir+"\\AutoDocs\\log4j.properties");
		String line;
		String state=null;
		int sum=0;
		try {
			Process p = Runtime.getRuntime().exec("adb shell \"dumpsys telephony.registry | grep mCallState\"");
			BufferedReader bf = new BufferedReader(new InputStreamReader(p.getInputStream()));
		
			while ((line = bf.readLine()) != null) {
				//System.out.print(line);
				if (line.contains("mCallState")) {
					line = line.trim();
					sum = sum+Integer.parseInt(String.valueOf(line.charAt(line.length()-1)));
					}
			   }
			state = (sum == 0)? "Idle_State":"Ringing_Inprogress";
		}catch(Exception e) {//log.error("To check Call Status Adb command not executed");			               
		}
		
		return state;
	}

//Waits the call till given duration	
	public static void waitTill_CallDuration(int callDurn) {
		int time=callDurn*1000;
		int timeDurn=0;
		int itr=time/5000;
		do {
			if(timeDurn !=0 & new CallTest().callState().equals("Idle_State")) {
				System.out.println("Call went to Idle State between "+(timeDurn-5)+" to "+timeDurn+" seconds");
				break;
			}else if(timeDurn == 0 & new CallTest().callState().equals("Idle_State")) {
				System.out.println("First Call not Established");
				break;
			}
			try {
				Thread.sleep(5000);
				} catch (Exception e) {e.printStackTrace();}
			timeDurn+=5;
		}while((timeDurn*1000)<=time);
		System.out.println("Call established around "+timeDurn+" seconds");
	}


}

