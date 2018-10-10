package com.systemlibrary.selenium;

import java.util.concurrent.TimeUnit;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class AppTest {
	private static ChromeDriver driver;
	WebElement element;

	@BeforeClass
	public static void openBrowser() {
		System.setProperty("webdriver.chrome.driver", "F:\\nitun-dev\\sts-workspace\\SeleniumCase\\chromedriver_win32\\chromedriver.exe");
		driver = new ChromeDriver();
		driver.manage().deleteAllCookies();
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	}

	@Test
	public void testWebsite() {
		System.out.println("Starting test " + new Object() {
		}.getClass().getEnclosingMethod().getName());
		driver.get("http://www.5balloons.info");

		Assert.assertEquals("5 Balloons � Laravel, PHP, Spring, Technical Solutions and Tutorials", driver.getTitle());
		System.out.println("Ending test " + new Object() {
		}.getClass().getEnclosingMethod().getName());
	}

	@AfterClass
	public static void closeBrowser() {
		//driver.quit();
	}
}
