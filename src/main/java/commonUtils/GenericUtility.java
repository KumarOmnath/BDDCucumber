package commonUtils;


	
	import static org.testng.Assert.fail;
	import java.awt.AWTException;
	import java.awt.Robot;
	import java.awt.event.KeyEvent;
	import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
	import java.net.HttpURLConnection;
	import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
	import java.util.List;
	import java.util.Random;
	import java.util.Set;
	import java.util.concurrent.TimeUnit;
	import org.apache.commons.io.FileUtils;
	import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.openqa.selenium.By;
	import org.openqa.selenium.Cookie;
	import org.openqa.selenium.JavascriptExecutor;
	import org.openqa.selenium.Keys;
	import org.openqa.selenium.NoSuchElementException;
	import org.openqa.selenium.NoSuchFrameException;
	import org.openqa.selenium.OutputType;
	import org.openqa.selenium.Point;
	import org.openqa.selenium.TakesScreenshot;
	import org.openqa.selenium.TimeoutException;
	import org.openqa.selenium.WebDriver;
	import org.openqa.selenium.WebDriverException;
	import org.openqa.selenium.WebElement;
	import org.openqa.selenium.interactions.Action;
	import org.openqa.selenium.interactions.Actions;
	import org.openqa.selenium.remote.RemoteWebElement;
	import org.openqa.selenium.support.ui.ExpectedConditions;
	import org.openqa.selenium.support.ui.Select;
	import org.openqa.selenium.support.ui.Wait;
	import org.openqa.selenium.support.ui.WebDriverWait;
	import org.testng.Assert;
	import org.testng.log4testng.Logger;

import com.cybersecurity.pages.CrmOrgPage;

	/**
	 * @author kumar
	 *
	 */
	/**
	 * This CommonUtilities class has all generic methods. We can use these methods across the
	 * projects. We consider Cucumber-Selenium project as base and converted the common generic libraries of that project
	 * into  framework 
	 */

	public class GenericUtility extends BaseTest implements IConst  {
		public static Workbook wb;
		public static String beforeWindowHandle = "";
		public static String currentWindowHandle = "";

		public static WebDriver webDriver;
		Logger LOG = Logger.getLogger(GenericUtility.class);
		public boolean throwActionException;

		/**
		 * User validate current URL of page
		 * 
		 * @return true
		 */
		public boolean urlContainsSubstring(String substring) {
			LOG.info("Current URL: " + webDriver.getCurrentUrl());
			return webDriver.getCurrentUrl().contains(substring);
		}

		public void click(WebElement object) {
			LOG.info("Click " + object);
			try {
				object.click();
			} catch (final Exception e) {
				e.getMessage();
			}
		}

		public boolean validateElementSelected(final WebElement elementName) throws Exception, Exception {
			LOG.info("Verify expected element is displayed");
			boolean actualValue = elementName.isSelected();

			if (actualValue) {
				LOG.debug("expected element" + elementName.getText() + " is Selected");
			} else {
				throw new Exception("Object should be Selected, Object is is not Selected");
			}
			return actualValue;
		}

		public boolean validateElementNotSelected(final WebElement elementName) throws Exception, Exception {
			LOG.info("Verify expected element is displayed");
			boolean actualValue = elementName.isSelected();

			if (actualValue == false) {
				LOG.debug("expected element" + elementName.getText() + " is not Selected");
			} else {
				throw new Exception("Object should not be Selected, Object is Selected");
			}
			return actualValue;
		}

		public String getText(WebElement object) throws Exception {
			String value = object.getText();
			return value;
		}

		public boolean validateElementisEnabled(final WebElement elementName) throws Exception, Exception {
			LOG.debug("Verify expected element is enabled");

			boolean actualValue = elementName.isEnabled();
			if (actualValue) {
				LOG.debug("Object is enabled");

			} else {

				throw new Exception("Object should be enabled,Objedct is not enabled");
			}
			return actualValue;
		}

		/**
		 * User validate current Title of page
		 * 
		 * @return true
		 */
		public boolean pageContainsTitleSubstring(String subString) {
			LOG.info("Current Title: " + webDriver.getTitle());
			return webDriver.getTitle().contains(subString);
		}

		/**
		 * User enter text in to textField
		 * 
		 */
		public void typeTextToTextBox(WebElement element, String text) throws Exception {
			LOG.info("Enter text in to textField: " + text);
			clearText(element);
			element.sendKeys(text);
		}

		/**
		 * User clear text in text field
		 * 
		 */
		public void clearText(WebElement element) throws Exception {
			LOG.info("Clear text in text field");
			element.clear();
		}

		/**
		 * User switch to the window
		 * 
		 */
		public void switchToNewWindow() {
			LOG.info("Switch to the window");
			try {
				System.out.println("Current window: " + webDriver.getCurrentUrl());
				for (String winHandle : webDriver.getWindowHandles()) {
					LOG.info(String.format("Window Handle - [%s].", winHandle));
					if (winHandle != webDriver.getWindowHandle()) {
						webDriver.switchTo().window(winHandle);
						webDriver.manage().window().maximize();
						LOG.info("Getting new window: " + webDriver.getCurrentUrl());
						System.out.println("Getting new window: " + webDriver.getCurrentUrl());
					}
				}
			} catch (final NoSuchElementException e) {
				LOG.error("Expected window handle is not present");
				return;
			}

		}

		/**
		 * User moving focus to parent window
		 * 
		 */
		public void moveFocusToParentWindow() throws Exception {
			final Set<String> handles = webDriver.getWindowHandles();
			final String firstWinHandle = webDriver.getWindowHandle();

			handles.remove(firstWinHandle);
			try {

				LOG.info("Moving focus to parent window");
				final String secondWinHandle = (String) handles.iterator().next();

				if (secondWinHandle != firstWinHandle) {
					Thread.sleep(2000);
					webDriver.switchTo().window(secondWinHandle);
					Thread.sleep(2000);
					webDriver.switchTo().window(firstWinHandle);
					Thread.sleep(2000);
				}
			} catch (final NoSuchElementException e) {
				LOG.error("Not focused to parent window");
				return;
			}
		}

		/**
		 * Switching to default content
		 * 
		 */
		public void switchToDefaultContent() {
			LOG.info("Switching to default content");
			webDriver.switchTo().defaultContent();
		}

		/**
		 * Switching back to previous window
		 * 
		 */
		public void switchBackToPreviousWindow() {
			LOG.info("Switching back to previous window");
			webDriver.navigate().back();
		}

		/**
		 * User Get screenshot on page
		 * 
		 * @param filePath
		 *            Describes path to save the screenshots
		 */
		public static void takeScreenshotAtEndOfTest() throws IOException {
			
			File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
			String currentDir = System.getProperty("user.dir");
			FileUtils.copyFile(scrFile, new File(currentDir + "/screenshots/" + System.currentTimeMillis() + ".png"));
		}
		
		
		

		/**
		 * Select expected value from drop down
		 * 
		 * @param webElement
		 *            Describe web element of a select drop down
		 * @param actualSelectText
		 *            Actual text to select in drop down
		 */
		public void selectDropdown(WebElement webElement, String actualSelectText) {
			LOG.info(String.format("Select expected value - [%s] from dropdown", actualSelectText));
			Select dropDown = new Select(webElement);
			int flag = 0;
			try {
				if (!dropDown.isMultiple()) {
					List<WebElement> dropDownCategories = dropDown.getOptions();
					for (WebElement dropDownCategory : dropDownCategories) {
						if (dropDownCategory.getText().equals(actualSelectText)) {
							flag = 1;
							break;
						}
					}
					if (flag == 1) {
						dropDown.selectByVisibleText(actualSelectText);
					}
				}
			} catch (Exception e) {
				LOG.error(String.format("Expected value - [%s] is not present in dropdown", actualSelectText));
			}

		}

		/**
		 * User Click on expected link text
		 * 
		 */
		public void ClickText(final String text) throws Exception {
			LOG.info(String.format("Click on expected link text - [%s] .", text));
			webDriver.findElement(By.linkText(text)).click();
		}

		/**
		 * User Wait for some time to visible element
		 * 
		 */
		public void wait(int object) throws InterruptedException {
			LOG.info("Wait for some time");
			TimeUnit.SECONDS.sleep(object);
		}

		/**
		 * Refresh current window
		 * 
		 */
		public void refresh_success() throws Exception {
			LOG.info("Refresh current window");
			webDriver.navigate().refresh();
		}

		/**
		 * Close current window browser
		 * 
		 */
		public void close_success() throws Exception {
			LOG.info("Close current window browser");
			webDriver.close();
		}

		/**
		 * Quit all browsers opened through web driver
		 * 
		 */
		public void quit_success() throws Exception {
			LOG.info("Quit all browsers opened through webdriver");
			webDriver.quit();
		}

		/**
		 * Validate title of the web page
		 * 
		 */
		public String getPageTitle_success() throws Exception {
			String title = null;
			try {
				title = webDriver.getTitle();
				LOG.info(String.format("User get the title as - [%s].", title));
				fail();
			} catch (Exception e) {
				return null;
			}
			return title;
		}

		/**
		 * User getting the current URL
		 * 
		 * @return current url
		 */
		public String getCurrentUrl() {
			LOG.info("User getting the current URL");
			return webDriver.getCurrentUrl();
		}

		/**
		 * Validate user maximize the window
		 * 
		 */
		public void maximizeWindow_exception() throws Exception {
			LOG.info("Validate user maximize the window");
			try {
				webDriver.manage().window().maximize();
				fail();
			} catch (Exception e) {
				return;
			}
		}

		/**
		 * Switching to expected frame Id name
		 * 
		 * @param frameName
		 *            Describes frame tag name
		 * @param expectedFrameID
		 *            Described frame id name to switch
		 */

		/**
		 * Switching to expected frame Id index
		 * 
		 * @param frameName
		 *            Describes frame tag name
		 * @param frameIndex
		 *            Described index number of a frame to switch
		 */
		public void switchToFrameByIndex(final String frameName, final int frameIndex) {
			LOG.info(String.format("Switching to expected frame Id index - [%s]", frameIndex));
			List<WebElement> frameToSelect = webDriver.findElements(By.tagName(frameName));
			try {
				webDriver.switchTo().frame(frameToSelect.get(frameIndex));
			} catch (Exception e) {
				e.getMessage();
			}
		}

		/**
		 * Execute user action
		 * 
		 */
		public void executeAction(final Actions action) {
			LOG.info("Exceute user action");
			if (throwActionException) {
				throw new TimeoutException("Throwing test Exception as directed");
			}
		}

		/**
		 * User moving action to expected element
		 * 
		 */
		public void moveToObject(final WebElement element) throws Exception {
			LOG.info("User moving action to expected element");
			final Actions action = new Actions(webDriver);
			final Action performAction;
			performAction = action.moveToElement(element).build();
			performAction.perform();
			executeAction(action);
		}

		/**
		 * Validate User moving action to page down
		 * 
		 */
		public void pageDown_exception() throws Exception {
			try {
				pageDown();
				fail();
			} catch (Exception e) {
				LOG.error("unable to move action to down");
				return;
			}

		}

		/**
		 * Validate User moving action to page up
		 * 
		 */
		public void pageUp_exception() throws Exception {
			try {
				pageUp();
				fail();
			} catch (Exception e) {
				LOG.error("unable to move action to up");
				return;
			}

		}

		/**
		 * User moving action to page down
		 * 
		 */
		public void pageDown() throws Exception {
			LOG.info("User moving action to page down");
			final Actions action = new Actions(webDriver);
			action.sendKeys(Keys.PAGE_DOWN);
			executeAction(action);
		}

		/**
		 * User moving action to page up
		 * 
		 */
		public void pageUp() throws Exception {
			LOG.info("User moving action to page up");
			final Actions action = new Actions(webDriver);
			action.sendKeys(Keys.PAGE_UP);
			executeAction(action);
		}

		/**
		 * Open link in a new window
		 * 
		 * @param element
		 *            describes WebElement of a link/button to click
		 * 
		 */
		public void openLinkInNewWindow(final WebElement element) throws Exception {
			LOG.info("User clicks and open link in new window");
			final Actions action = new Actions(webDriver);
			action.keyDown(Keys.SHIFT).click(element).keyUp(Keys.SHIFT).build().perform();
		}

		/**
		 * Get all page source objects
		 */
		public String getPageSource() throws Exception {
			LOG.info("Getting all page source object in current window");
			return webDriver.getPageSource();
		}

		/**
		 * Delete all cookies
		 */
		public void deleteAllCookies() throws Exception {
			LOG.info("Delete all cookies");
			webDriver.manage().deleteAllCookies();
		}

		/**
		 * Convert the passed String to a long. If the conversion is not successful,
		 * return the default value.
		 * 
		 * @param value
		 *            String value to be converted to a long. Null-safe
		 * @return long representation of String value if String can successfully be
		 *         converted. Otherwise return defaultValue
		 */
		public static long toLong(final String value) {
			
			try {
				return new Long(value).longValue();
			} catch (final Exception e) {
				return 0;
			}
		}

		/**
		 * Convert the passed String to an integer value. If the conversion is not
		 * successful, return the default value.
		 * 
		 * @param value
		 *            String value to be converted to an int. Null-safe
		 * @return Integer representation of String value if String can successfully
		 *         be converted. Otherwise return defaultValue
		 */
		public static int toInt(final String value) {
			
			try {
				return new Integer(value).intValue();
			} catch (final Exception e) {
				return 0;
			}
		}

		public void validateAllLinks(String domainname) {
			LOG.info("Inside broken link method1");
			HttpURLConnection huc = null;
			int responseCode;
			String url = null;
			// switchToNewWindow();
			List<WebElement> links = webDriver.findElements(By.tagName("a"));
			Iterator<WebElement> iterator = links.iterator();
			while (iterator.hasNext()) {
				LOG.info("Inside broken link method2");
				url = ((RemoteWebElement) iterator.next()).getAttribute("href");
				if (!url.startsWith("javascrript")) {
					LOG.info("URL : " + url);
					if (url == null || url.isEmpty()) {
						LOG.info(url + "- is either not configured for anchor tag or it is empty");
						continue;
					}
					if (!url.startsWith(domainname)) {
						LOG.info(url + "-URL belongs to another domain, skipping it.");
						continue;
					}
					try {
						huc = (HttpURLConnection) (new URL(url).openConnection());
						huc.setRequestMethod("GET");
						huc.connect();
						responseCode = huc.getResponseCode();
						LOG.info("Response code : " + responseCode);
						if (responseCode >= 400) {
							LOG.info("url is a broken link : " + url);
						} else {
							LOG.info("url is a valid link : " + url);
						}

					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}

		public void checkCookie(final String object) throws Exception {

			try {
				Set<Cookie> cookiesForCurrentURL = webDriver.manage().getCookies();
				for (Cookie cookie : cookiesForCurrentURL) {
					System.out.println(" Cookie Name - " + cookie.getName() + " Cookie Value - " + cookie.getValue());
					String output = " " + cookie.getValue();

					if (object.equalsIgnoreCase(output)) {
						System.out.println(
								" Cookie Name - " + cookie.getName() + " Equals Cookie Value - " + cookie.getValue());
						Assert.assertEquals(object, output);
						break;
					}

				}
			} catch (final Exception e) {
				e.getMessage();
			}
		}

		public void moveSlider(final String object) throws Exception {

			LOG.info("Moving the slider to some position --");
			try {
				WebElement element = getElementWithoutWait(object);
				Actions action = new Actions(webDriver);
				action.click(element).build().perform();
				Thread.sleep(1000);
				action.sendKeys(Keys.ARROW_LEFT).build().perform();
				Thread.sleep(100);
				action.sendKeys(Keys.ARROW_RIGHT).build().perform();

			} catch (final Exception e) {
				e.printStackTrace();
			}

		}

		/**
		 * Get the specified web element. If not found, returns
		 * 
		 * @param object
		 *            Web element to be retrieved
		 * 
		 * @return WebElement for the specified object if it exists. Null if an
		 *         error occurs or element can't be found.
		 */
		public WebElement getElementWithoutWait(final String object) {

			try {
				LOG.debug("Getting element " + object);
				final WebElement element = (WebElement) webDriver.findElement(By.xpath(object));
				LOG.debug("Got element " + object);
				return element;
			} catch (final Exception e) {
				LOG.debug("Could not get element " + object + ", returning  " + e.getMessage());
				return null;
			}
		}

		/**
		 * Verify that the passed object is of a specified size.
		 * 
		 * @param object
		 *            Web element to be validated
		 * @param expectedSize
		 *            Expected size in format widthXheight (e.g. "10X100" for
		 *            width=10 and height=100)
		 * @throws Exception
		 */
		public void validateElementSize(final WebElement object, final String expectedSize) throws Exception {

			String actualSize = null;
			try {

				actualSize = object.getSize().toString();
				LOG.debug("Size='" + actualSize + "'");
			} catch (final Exception e) {

				return;
			}

			if (StringUtils.equals(actualSize, expectedSize)) {
				LOG.debug("Image size for " + object + " is correct");
			} else {
				throw new Exception("Image size is not eqaul");
			}
		}

		/**
		 * Verify that the passed object Text is present.
		 * 
		 * @param object
		 *            Web element to be validated
		 * @throws Exception
		 */
		public void verifyTextPresent(final String object) throws Exception {
			try {
				LOG.info("Verifiy text is present in a webPage");
				String Verifytext = webDriver.findElement(By.tagName("body")).getText().trim();
				Assert.assertTrue(Verifytext.contains(object));
			} catch (final Exception e) {
				e.printStackTrace();
			}
		}

		/**
		 * Verify expected element is not displayed.
		 * 
		 * @param object
		 *            Web element to be validated
		 * @throws Exception
		 */
		public boolean validateElementNotDisplayed(final WebElement elementName) throws Exception {

			LOG.info("Verify expected element is not displayed");
			boolean actualValue = elementName.isDisplayed();
			if (!actualValue) {
				LOG.debug("expected element" + elementName.getText() + " is not present");
			} else {
				throw new Exception("Object should not present ,but Object is present");
			}

			return actualValue;
		}

		/**
		 * Verify expected element is displayed.
		 * 
		 * @param object
		 *            Web element to be validated
		 * @throws Exception
		 */
		public boolean validateElementDisplayed(final WebElement elementName) throws Exception, Exception {
			LOG.info("Verify expected element is displayed");
			boolean actualValue = elementName.isDisplayed();

			if (actualValue) {
				LOG.debug("expected element" + elementName.getText() + " is present");
			} else {
				throw new Exception("Object should be displayeddd, Object is is not displaying");
			}
			return actualValue;
		}

		/**
		 * Verify expected element is disable.
		 * 
		 * @param object
		 *            Web element to be validated
		 * @throws Exception
		 */
		public boolean validateElementisDisable(final WebElement elementName) throws Exception, Exception {
			LOG.debug("Verify expected element is disable");

			boolean actualValue = elementName.isEnabled();
			if (!actualValue) {
				LOG.debug("expected element" + elementName.getText() + " is present");

			} else {
				throw new Exception("Object should be in disable,Object is Enable");

			}
			return actualValue;
		}

		/**
		 * Verify if actual value in same as entered value
		 * 
		 * @param object
		 *            Web element to be validated
		 * @throws Exception
		 */
		public void validateText(WebElement object, String value_Entered) throws Exception {
			LOG.info("Checking if value in  " + object + " is same as value entered");
			String value = object.getAttribute("value");
			if (value.equalsIgnoreCase(value_Entered)) {
				LOG.info("Enterd value is appearing in the page");
			} else {
				throw new Exception("Text should be same ,Text values are not matching");
			}

		}

		/**
		 * Verify if actual value in and are same
		 * 
		 * @param object1
		 *            First Web element to be validated
		 * @param object2
		 *            Second Web element to be validated
		 * @throws Exception
		 */
		public void ValidateText_object1_Object2(final WebElement object1, final WebElement object2) throws Throwable {

			LOG.info("Checking if value in  " + object1.getText() + " and  " + object2.getText() + " are same ");

			String actualtext = object1.getText();
			String expectedtext = object2.getText();

			if (actualtext.equalsIgnoreCase(expectedtext)) {

				LOG.info("value in  " + object1.getText() + " and " + object2.getText() + " are same");
			} else {
				throw new Exception(
						object1.getText() + "and" + object2.getText() + "Text should be same, Text are not matching");
			}

		}

		// method to validate the broken links response code
		public int VerifyBrokenLink(WebElement WebElement) {

			HttpURLConnection huc = null;
			int respCode = 200;
			String url;

			// WebElement links = new
			// WebDriverTestBase().getDriver().findElement(By.linkText(xpath));

			url = WebElement.getAttribute("href");
			if (url == null || url.isEmpty()) {
				System.out.println("URL is either not configured for anchor tag or it is empty");

			}

			try {
				huc = (HttpURLConnection) (new URL(url).openConnection());
				huc.connect();
				respCode = huc.getResponseCode();
			} catch (Exception e) {

				e.printStackTrace();
			}

			return respCode;

		}

		// Method to close the newly opened tab while verify all the link clicks and
		// its
		// navigation to new screen

		public void Highlighting_Element(WebElement header) throws Exception {
			JavascriptExecutor js = (JavascriptExecutor) driver;
			for (int iCnt = 0; iCnt < 2; iCnt++) {
				js.executeScript("arguments[0].style.border='5px dotted red';", header);

				js.executeScript("arguments[0].style.border=''", header);
			}

		}

		public void ValidatePresenceOfElement(List<WebElement> all) throws Exception

		{
			int a = all.size();
			if (a > 0) {
				throw new Exception("Element is Displayed");
			}
		}

		public String getRandomText(int length) {
			LOG.info("Generating Random Text");
			String alphabet = new String("0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ");

			int n = alphabet.length();
			String result = new String();
			Random r = new Random();

			for (int i = 0; i < length; i++) {
				result = result + alphabet.charAt(r.nextInt(n));
			}
			LOG.info("Generating Random Text" + result);
			return result;

		}

		public String getRandomNumber(int length) {
			LOG.info("Generating Random Text");
			String alphabet = new String("0123456789");

			int n = alphabet.length();
			String result = new String();
			Random r = new Random();

			for (int i = 0; i < length; i++) {
				result = result + alphabet.charAt(r.nextInt(n));
			}
			LOG.info("Generating Random number");
			return result;

		}

		public String getRandomEmailId() {
			LOG.info("Generating Random emailid");
			String emailid = getRandomText(10) + "@" + getRandomText(3) + ".com";
			LOG.info("Generating Random emailid" + emailid);
			return emailid;
		}

		public void scrollToview(WebElement element) {
			LOG.info("Scrolling to the Eelement" + element);
			JavascriptExecutor executor = (JavascriptExecutor) webDriver;
			executor.executeScript("arguments[0].scrollIntoView(true);", element);
		}

		public void JseHighlightTheElement(WebElement header) throws Exception {
			LOG.info("Highlightging the Element " + header);
			JavascriptExecutor js = (JavascriptExecutor) webDriver;
			for (int iCnt = 0; iCnt < 2; iCnt++) {
				js.executeScript("arguments[0].style.border='5px dotted red';", header);

			}

		}

		public void JseClick(WebElement element) {
			try {
				LOG.info("Mouse overing to the Element " + element);
				JavascriptExecutor executor = (JavascriptExecutor) driver;
				executor.executeScript("arguments[0].click();", element);
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}

		}

		public void JseMouseOver(WebElement element) {
			LOG.info("Mouse overing to the Element " + element);
			JavascriptExecutor executor = (JavascriptExecutor) webDriver;
			executor.executeScript("arguments[0].onmouseover()", element);
		}

		public void WaitUntilelementToBeClickable(WebElement Ele, int timeOut) {
			LOG.info("ExplicitWait for the Element " + Ele + "With the timeout " + timeOut);
			Wait<WebDriver> wait = new WebDriverWait(webDriver, timeOut);
			wait.until(ExpectedConditions.elementToBeClickable(Ele));

		}

		public void WaitForTheVisibilityOfElement(WebElement Ele, int timeOut) {
			LOG.info("WaitForTheVisibilityOfElement " + Ele + "With the timeout " + timeOut);
			Wait<WebDriver> wait = new WebDriverWait(webDriver, timeOut);
			wait.until(ExpectedConditions.elementToBeClickable(Ele));

		}

		public void RobotMouseOver(WebElement element) throws AWTException {
			LOG.info("Mouse overing to the Element " + element);
			Point point = element.getLocation();
			Robot Robot = new Robot();
			Robot.mouseMove(point.getX(), point.getY());

		}

		public void selectDropDown(WebElement webElement, int index) {
			Select dropDown = new Select(webElement);
			try {
				dropDown.selectByIndex(index);
			} catch (Exception e) {
				e.printStackTrace();
			}

		}

		public void validatemessagepresent(WebElement object, String value_Entered) throws Exception {
			LOG.info("Checking if value in  " + object + " is same as value entered");
			String value = object.getText();
			if (value.equalsIgnoreCase(value_Entered)) {
				LOG.info("Enterd value is appearing in the page");
			} else {
				throw new Exception("Text should be same ,Text values are not matching");
			}
		}

		public void ClickonPrinticonAndEsc() throws AWTException {
			LOG.info("esc on click print ");
			Robot robot = new Robot();
			robot.keyPress(KeyEvent.VK_ESCAPE);
			robot.keyRelease(KeyEvent.VK_ESCAPE);
		}

		public void moveFocusToParentWindow1() throws Exception {

			final Set<String> handles = webDriver.getWindowHandles();
			LOG.info("Switch to recent window");
			for (String obj : handles) {

				webDriver.switchTo().window(obj);

				System.out.println(obj);

			}

		}

		public void CaptureC() {

			beforeWindowHandle = webDriver.getWindowHandle();
			LOG.info("Capturing the window of current control");

		}

		public void Moveback() {

			webDriver.switchTo().window(beforeWindowHandle);
			LOG.info("move again to parent window");

		}
		
		// frame by index
		public void switchToFramebyIndex(int frame) {
			try {
				webDriver.switchTo().frame(frame);
				System.out.println("Navigated to frame with id " + frame);
			} catch (NoSuchFrameException e) {
				System.out.println("Unable to locate frame with id " + frame
						+ e.getStackTrace());
			} catch (Exception e) {
				System.out.println("Unable to navigate to frame with id " + frame
						+ e.getStackTrace());
			}
		}
		
		// switch to frame by id /name
		public void switchToFramebyID(String frame) {
			try {
				webDriver.switchTo().frame(frame);
				System.out.println("Navigated to frame with name " + frame);
			} catch (NoSuchFrameException e) {
				System.out.println("Unable to locate frame with id " + frame
						+ e.getStackTrace());
			} catch (Exception e) {
				System.out.println("Unable to navigate to frame with id " + frame
						+ e.getStackTrace());
			}
		}
		
		// switch to frame by Webelement
		public void switchToFrame(WebElement frameElement) throws Exception {
			try {
				if (validateElementDisplayed(frameElement)) {
					webDriver.switchTo().frame(frameElement);
					System.out.println("Navigated to frame with element "+ frameElement);
				} else {
					System.out.println("Unable to navigate to frame with element "+ frameElement);
				}
			} catch (NoSuchFrameException e) {
				System.out.println("Unable to locate frame with element " + frameElement + e.getStackTrace());
			
		}

	}
		//frame inside frame
		public void switchToFrame(String ParentFrame, String ChildFrame) {
			try {
				webDriver.switchTo().frame(ParentFrame).switchTo().frame(ChildFrame);
				System.out.println("Navigated to innerframe with id " + ChildFrame
						+ "which is present on frame with id" + ParentFrame);
			} catch (NoSuchFrameException e) {
				System.out.println("Unable to locate frame with id " + ParentFrame
						+ " or " + ChildFrame + e.getStackTrace());
			} catch (Exception e) {
				System.out.println("Unable to navigate to innerframe with id "
						+ ChildFrame + "which is present on frame with id"
						+ ParentFrame + e.getStackTrace());
			}
		}
		
		
		// switch to default context
		public void switchtoDefaultFrame() {
			try {
				webDriver.switchTo().defaultContent();
				System.out.println("Navigated back to webpage from frame");
			} catch (Exception e) {
				System.out
						.println("unable to navigate back to main webpage from frame"
								+ e.getStackTrace());
			}
		}
		
		
//		public static String getCellValue(String sheetName, int rowNum, int columnNum){
//			String cellValue ="";
//			try {
//		 wb = WorkbookFactory.create(new FileInputStream(XLPATH));
//		 cellValue = wb.getSheet(sheetName).getRow(rowNum).getCell(columnNum).toString();
//			} catch (Exception e) {
//			} 
//			return cellValue;
//		}
//		public static int getRowCount(String sheetName){
//			int rowCount = 0;
//			try {
//				 wb = WorkbookFactory.create(new FileInputStream(XLPATH));
//				 rowCount = wb.getSheet(sheetName).getLastRowNum();
//			} catch (Exception e) {
//			} 
//			return rowCount;
//		}
		
		
		
	}



