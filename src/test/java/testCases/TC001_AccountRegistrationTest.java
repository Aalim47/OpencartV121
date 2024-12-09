package testCases;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

import pageObjects.AccountRegistrationPage;
import pageObjects.HomePage;
import testBase.BaseClass;

public class TC001_AccountRegistrationTest extends BaseClass {
	@Test(groups = {"Regression","Master"})
	public void verify_account_registration()
	{
		logger.info("****** Starting TC001_AccountRegistrationTest ******");
		
		HomePage hp = new HomePage(driver);
		hp.clickMyAccount();
		logger.info(" Clicked on MyAccount Link ");
		hp.ClickRegister();
		logger.info(" Clicked on Register Link ");
		
		AccountRegistrationPage repage=new AccountRegistrationPage(driver);
		
		logger.info("Providing Customer details...");
		repage.setFirstName(randomeString());
		
		repage.setLastName(randomeString());
		
		repage.setEmail(randomeString()+"@gmail.com"); //Randomly generated the email 
		
		repage.setTelPhone(randomeNumber());
		
		String password=randomeAlphanumeric();
		repage.setPassword(password);
		
		repage.setConfirmPassword(password);
		
		repage.setPrivacyPolicy();
		
		repage.clickContinue();
		
		logger.info("Validating expected message..");
	    String confmsg=repage.getConfirmationMsg();
		Assert.assertEquals(confmsg, "Your Account Has Been Created!");
		
		logger.info("****** Finished TC001_AccountRegistrationTest ******");
		}
	
	
}
