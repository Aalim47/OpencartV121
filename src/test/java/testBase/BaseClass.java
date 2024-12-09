package testBase;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.Properties;

import org.apache.commons.lang3.RandomStringUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Platform;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;

import freemarker.template.SimpleDate;

import org.apache.logging.log4j.LogManager; //log4j
import org.apache.logging.log4j.Logger;   //log4j
import org.apache.poi.ss.formula.atp.Switch;

public class BaseClass {
	
	public static WebDriver driver;
	public Logger logger; //Log4j
	public Properties p;
	
	@BeforeClass(groups = {"Sanity","Regression","Master",})
	@Parameters({"os","browser"})
	public void setup(String os,String br) throws IOException
	{
		//Loading properties file
		FileReader file = new FileReader(".\\src\\test\\resources\\config.properties");
		p=new Properties();
		p.load(file);
		
		
		logger=LogManager.getLogger(this.getClass());
		
		if(p.getProperty("execution_env").equalsIgnoreCase("remote"))
		{
			DesiredCapabilities capabilities = new DesiredCapabilities();
		
			//os
			if(os.equalsIgnoreCase("Windows"))
			{
				capabilities.setPlatform(Platform.WIN11);
			}
			else if(os.equalsIgnoreCase("linux"))
			{
				capabilities.setPlatform(Platform.LINUX);
			}
			else
			{
				System.out.println("No matiching os");
				return;
			}
			
			//browser
					
			switch(br.toLowerCase())
			{
			case "chrome": capabilities.setBrowserName("chrome"); break;
			case "firefox": capabilities.setBrowserName("firefoxe"); break;
			case "edge": capabilities.setBrowserName("MicrosoftEdge"); break;
			default: System.out.println("No matching browser"); return;
			}
			
			driver = new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"),capabilities);
		}
		
		if(p.getProperty("execution_env").equalsIgnoreCase("local"))
		{
			switch(br.toLowerCase())
			{
			case "chrome" : driver=new ChromeDriver(); break;
			case "firefox" : driver=new FirefoxDriver();break;
			case "edge" : driver=new EdgeDriver();break;
			default : System.out.println("Invalid browser name.."); 
	 		}
		}
		
		driver.manage().deleteAllCookies();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		driver.get(p.getProperty("appURL")); //reading url from property file.
		driver.manage().window().maximize();
	}
	
	@AfterClass(groups = {"Sanity","Regression","Master"})
	public void teardown()
	{
		driver.quit();
	}
	
	public String randomeString()
	{
		String generatedstring=RandomStringUtils.randomAlphabetic(5);
		return generatedstring;
	}
	
	public String randomeNumber()
	{
		String generatednumber=RandomStringUtils.randomNumeric(5);
		return generatednumber;
	}
	
	public String randomeAlphanumeric()
	{
		String generatedstring=RandomStringUtils.randomAlphabetic(5);
		String generatednumber=RandomStringUtils.randomNumeric(5);
		return (generatedstring+"*"+generatednumber);
	}
	
	public String captureScreen(String tname) {
		String timespam = new SimpleDateFormat("yyyyMMddhhmm").format(new Date());
		
		TakesScreenshot takesScreenshot = (TakesScreenshot) driver;
		File sourceFile = takesScreenshot.getScreenshotAs(OutputType.FILE);
		
		String targatFilePath=System.getProperty("user.dir")+"\\screenshots\\"+ tname + "_" + timespam + ".png"; 
	    File targatFile=new File(targatFilePath);
	    
	    sourceFile.renameTo(targatFile);
	    
	    return targatFilePath;
	}
	
	
}
