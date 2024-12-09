package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;

import pageObjects.HomePage;
import pageObjects.LoginPage;
import pageObjects.MyAccountPage;
import testBase.BaseClass;

public class TC002_LoginTest extends BaseClass {
	
	@Test(groups = {"Sanity","Master"})
	public void verify_login()
	{
		logger.info("*****Starting TC_002_LoginTest ******");
		
		try {
		//home page	
		HomePage hp = new HomePage(driver);
		hp.clickMyAccount();
		hp.ClickonLogin();
		
		//loging page
		LoginPage lp = new LoginPage(driver);
		lp.setEmailAddress(p.getProperty("email"));
		lp.setPassword(p.getProperty("password"));
		lp.ClickLogin();
		
		//myaccound page
		MyAccountPage macc = new MyAccountPage(driver);
		boolean targetpage=macc.isMyAccountPageExists();
	    Assert.assertTrue(targetpage); //Assert.assertEquals(targetpage, true ,"Page is not found");
	    
	    macc.ClickLogout();
	    
		}
		catch(Exception e)
		{
			Assert.fail();
		}
	}
}
