package commonUtils;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.support.events.EventFiringWebDriver;

public class BaseTest {

	public static WebDriver driver;

	public static EventFiringWebDriver event_driver;
	public static WebEventListener eventListener;

	public static WebDriver getBrowser(String browserName) throws Exception {

		browserName.toLowerCase();
		if (browserName.equals("chrome"))
			return getChromeInstance();
		else if (browserName.equals("firefox"))
			return getFirefoxInstance();
		else
			return getIEInstance();
	}

	private static WebDriver getChromeInstance() throws Exception {

		String path = PropertyReader.getProperty("chromedriverpath");
		System.setProperty("webdriver.chrome.driver", path);
		driver = new ChromeDriver();
		return driver;

	}

	private static WebDriver getFirefoxInstance() throws Exception {

		String path = PropertyReader.getProperty("geckodriverpath");
		System.setProperty("webdriver.gecko.driver", path);
		driver = new FirefoxDriver();
		return driver;

	}

	private static WebDriver getIEInstance() throws Exception {

		String path = PropertyReader.getProperty("IEdriverpath");
		System.setProperty("webdriver.ie.driver", path);
		driver = new InternetExplorerDriver();
		return driver;

	}

	public static void getBrowserEventFire() throws Exception {

		event_driver = new EventFiringWebDriver(driver);
		// Now create object of EventListerHandler to register it with
		// EventFiringWebDriver
		eventListener = new WebEventListener();
		event_driver.register(eventListener);
		driver = event_driver;
		String implicitWait = PropertyReader.getProperty("Implicit_Wait");
		driver.manage().timeouts().implicitlyWait(Long.parseLong(implicitWait), TimeUnit.SECONDS);

		driver.get(PropertyReader.getProperty("url"));

	}

	public static void tearDown() throws IOException {
		driver.close();
	}
}