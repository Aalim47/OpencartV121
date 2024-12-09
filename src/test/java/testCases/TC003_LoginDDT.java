package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;

import pageObjects.HomePage;
import pageObjects.LoginPage;
import pageObjects.MyAccountPage;
import testBase.BaseClass;
//import utilities.DataProviders;
import utilities.DataProviders;

public class TC003_LoginDDT extends BaseClass { 
	
	@Test(dataProvider = "LoginData",dataProviderClass=DataProviders.class,groups="datadrivern")
    public void verify_LoginDDT(String email,String pwd,String exp)
    {
		logger.info("***** Starting TC003_LoginDDT ******");
		
	try {
    //home page
	HomePage hp = new HomePage(driver);
	hp.clickMyAccount();
	hp.ClickonLogin();
	
	
	//loging page
	LoginPage lp = new LoginPage(driver);
	lp.setEmailAddress(email);
	lp.setPassword(pwd);
	Thread.sleep(1000);
	lp.ClickLogin();
	
	
	
	//myaccound page
	MyAccountPage macc = new MyAccountPage(driver);
    boolean targetpage=macc.isMyAccountPageExists();
   
    /*Data is valid - login sucess - test pass -logout
                    -   login failed -test fail
                    
     Data is invalid - login sucess - test fail - logout
                       login failed - test pass               
     */
    
    
    if(exp.equalsIgnoreCase("Valid"))
    {
    	if(targetpage=true)
    	{
    		macc.ClickLogout();
    		Assert.assertTrue(true);
    	}
    	
    	else
    	{
    		Assert.assertTrue(false);
    	}
    }
    if(exp.equalsIgnoreCase("Invalid"))
    {
    	if(targetpage=true)
    	{
    		macc.ClickLogout();
        	Assert.assertTrue(false);	
    	}
    	else
    	{
    		Assert.assertTrue(true); 
    	}
    	
       }
	}
	catch(Exception e)
	{
		Assert.fail();
	}
    logger.info("***** Finished TC003_LoginDDT ******");
    }
  }