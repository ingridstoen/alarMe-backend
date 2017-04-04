package alarMe;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;

public class LoginProcess extends ConnectToDatabase {
	
	protected static WebDriver driver = new ChromeDriver();
			
	//super constructor
	public LoginProcess(String username, String user_password, WebDriver driver){
		username = getUserName();
		user_password = getUserPassword();
		LoginProcess.driver = driver;
	}
	
	//method to set the chromedriver.
	public void init() {
		//System.setProperty("webdriver.chrome.driver", "chromedriver");
		System.setProperty("webdriver.chrome.driver", "C:\\Users\\Heidi\\"
							+ "worspace1\\AlarMe\\chromedriver_win32\\chromedriver.exe");
	
	}
	
	//method to choose NTNU as university.
	public void chooseNTNU() throws InterruptedException {
        Select objectSelect = new Select(driver.findElement(By.id("org")));
        objectSelect.selectByValue("ntnu.no");
        driver.findElement(By.className("submit")).click();
        Thread.sleep(5000);
    }
	
	//method to choose NTNU as university one more time.
    public void chooseNTNUAgain() throws InterruptedException {
        Select schoolSelect = new Select(driver.findElement(By.id("institusjonsvalg:institusjonsMenu")));
        schoolSelect.selectByValue("FSNTNU");
        try{
        	driver.findElement(By.name("institusjonsvalg:j_idt121")).click();
        }catch(Exception e){
        	driver.findElement(By.name("institusjonsvalg:j_idt126")).click();
        }
        Thread.sleep(5000);
        driver.findElement(By.linkText("Pålogging via Feide")).click();
        Thread.sleep(5000);
    }
    
    //method to login into feide
    public void login() throws InterruptedException {
        WebElement usernameField = driver.findElement(By.name("feidename"));
        WebElement passwordField = driver.findElement(By.name("password"));
        usernameField.sendKeys(username);
        passwordField.sendKeys(user_password);
        driver.findElement(By.className("submit")).click();
        Thread.sleep(5000);
    }

}
