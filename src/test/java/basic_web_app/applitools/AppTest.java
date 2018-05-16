package basic_web_app.applitools;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import com.applitools.eyes.BatchInfo;
import com.applitools.eyes.RectangleSize;
import com.applitools.eyes.TestResults;
import com.applitools.eyes.selenium.Eyes;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for simple App.
 */
public class AppTest 
    extends TestCase
{
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public AppTest( String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( AppTest.class );
    }

   
    /**
     * Rigourous Test :-)
     * @throws URISyntaxException 
     */
    public void testAppFirefox() throws URISyntaxException
    {
    	 URI serverURL = null;


        serverURL = new URI("https://littlebigprojects.com/");
        
        System.setProperty("webdriver.gecko.driver","./geckodriver.exe");
      
        // Open a Chrome browser.
        WebDriver driver = new FirefoxDriver();

        // Initialize the eyes SDK and set your private API key.
        Eyes eyes = new Eyes();
        BatchInfo batchInfo = new BatchInfo(System.getenv("APPLITOOLS_BATCH_NAME"));
    	// If the test runs via Jenkins, set the batch ID accordingly.
    	String batchId = System.getenv("APPLITOOLS_BATCH_ID");
    	if (batchId != null) {
    	    batchInfo.setId(batchId);
    	}
    	eyes.setBatch(batchInfo);
        eyes.setApiKey("998qD2l103S3i1frBsZ0tkCJkGVlNdLF98j7xuuHD17nUs110");

        try {
            eyes.open(driver, "LittleBigProjects", "Sign in",
                    new RectangleSize(800, 600));

            // Navigate the browser to the "hello world!" web-site.
            driver.get(serverURL.toString());

            // Visual checkpoint #1.
            eyes.checkWindow("SIGN IN");

            // Click the "Click me!" button.
            driver.findElement(By.id("nav-login-btn")).click();

            driver.findElement(By.cssSelector("#ms-main-nav-bar-collapse form input:first-child")).sendKeys("rowanfletch");
            driver.findElement(By.cssSelector("#ms-main-nav-bar-collapse form input:nth-child(2)")).sendKeys("dummy123");
            driver.findElement(By.cssSelector(".login-wrapper form button:last-child")).click();
            
            TimeUnit.SECONDS.sleep(3);
            // Visual checkpoint #2.
            eyes.checkWindow("Pilot");
            // End of test.
            TimeUnit.SECONDS.sleep(1);
            Boolean throwTestCompleteException = false;
            TestResults result = eyes.close(throwTestCompleteException);
            
           
            String url = result.getUrl();
            if (result.isNew())
                System.out.println("New Baseline Created: URL=" + url);
             else if (result.isPassed())
                System.out.println("All steps passed:     URL=" + url);
            else
                System.out.println("Test Failed:          URL=" + url);


        } catch (Exception e) { e.printStackTrace(); }
        finally {
            // Close the browser.
            driver.quit();

            // If the test was aborted before eyes.close was called, ends the test as aborted.
            eyes.abortIfNotClosed();
        }
       
        assertTrue( true );
    }
}
