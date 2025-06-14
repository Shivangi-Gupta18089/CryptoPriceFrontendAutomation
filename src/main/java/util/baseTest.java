package util;

import io.github.bonigarcia.wdm.WebDriverManager;
import io.qameta.allure.Allure;
import logger.*;
import org.openqa.selenium.*;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.*;
import org.testng.asserts.SoftAssert;

import java.io.*;
import java.time.Duration;
import java.util.List;
import java.util.*;

public class baseTest {
    public static WebDriver driver;
    public static String currentDirectory;
    public yamlReader reader;
    public static String loginURl;
    public static SoftAssert softAssert;
    public static SoftAssert softAssertRunTest;
    public static String headless;
    public static String staging;
    public static String webDriverManager;
    public static int tries = 0;


    @BeforeTest
    @Parameters({"headless","staging","webDriverManager"})
    public void initializeVariables(String headless, String staging, String webDriverManager) {
        currentDirectory = System.getProperty("user.dir");
        log.info("Current directory " + currentDirectory);
        log.info("Initializing variables");
        baseTest.headless = headless;
        baseTest.staging = staging;
        baseTest.webDriverManager = webDriverManager;
        log.info("headless:"+headless);
        log.info("staging:"+staging);
        log.info("webDriverManager:"+webDriverManager);
        reader = new yamlReader(baseTest.class.getClassLoader().getResourceAsStream("data_staging.yml"));
        loginURl = reader.get("Website.url.loginURl");
            log.info("Using credentials");
           log.info("loginUrl ---> " +loginURl);

        softAssert = new SoftAssert();
        softAssertRunTest = new SoftAssert();

       createDriver();
        driver.get(loginURl);
    }

    public void createDriver() {
        log.info("Creating driver");

        ChromeOptions opt = new ChromeOptions();

        if(headless.contains("True")) {
            opt.addArguments("--headless");
        }
        opt.addArguments("--incognito");
        opt.addArguments("--no-sandbox");
        opt.addArguments("--disable-logging");
        opt.addArguments("--disable-gpu");
        opt.addArguments("--disable-dev-shm-usage");

        if(webDriverManager.contains("True")) {
            log.info("Setting up webdriver manager");
            WebDriverManager.chromedriver().setup();
        }

        String OS = System.getProperty("os.name").toLowerCase();
        log.info("OS Type -> "+OS);
        boolean IS_WINDOWS = (OS.indexOf("win") >= 0);

        if(IS_WINDOWS) {
            if(!webDriverManager.contains("True")) {
                System.setProperty("webdriver.chrome.driver", "drivers/chromedriver.exe");
            }
            log.info("Driver location -> drivers/chromedriver.exe");
        }else{
            if(!webDriverManager.contains("True")) {
                System.setProperty("webdriver.chrome.driver", "drivers/chromedriver");
            }
            log.info("Driver location -> drivers/chromedriver");
        }

        driver = new ChromeDriver(opt);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(30));
        driver.manage().window().setSize(new Dimension(1280, 720));
        driver.manage().window().maximize();

    }

//    @AfterSuite
//    public void terminate() {
//        log.info("Terminating");
//        driver.quit();
//        softAssertRunTest.assertAll();
//    }


    public List<List<String>> readProperties() {
        Properties p = new Properties();
        InputStream file = null ;
        try {
            file = baseTest.class.getClassLoader().getResourceAsStream("application.properties");
        }catch (Exception ex) {
            throw new RuntimeException(ex);
        }
        try {
            p.load(file);
            file.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        List<List<String>> ret = new ArrayList<>();
        ret.add(Arrays.asList(p.getProperty("names").split(",")));
        ret.add(Arrays.asList(p.getProperty("numbers").split(",")));
        ret.add(Arrays.asList(p.getProperty("keySkills").split(",")));
        ret.add(Arrays.asList(p.getProperty("designation").split(",")));
        ret.add(Arrays.asList(p.getProperty("companies").split(",")));
        ret.add(Arrays.asList(p.getProperty("locationIndia").split(",")));
        ret.add(Arrays.asList(p.getProperty("locationOutside").split(",")));
        ret.add(Arrays.asList(p.getProperty("institute").split(",")));
        return ret;
    }

    public String getRandomNumbers() {
        return readProperties().get(1).get(getRandom(readProperties().get(0).size()));
    }


    public int getRandom(int size) {
        Random r = new Random();
        return r.nextInt(size);
    }

    public void expWait(WebDriver driver, String xp) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        WebElement temp = driver.findElement(By.xpath(xp));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xp)));
    }

    public void impWait(WebDriver driver) {
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
    }


    public void sleep(int time) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }


    public void saveScreenShot(WebDriver driver, String caption) {
        Allure.addAttachment(caption,new ByteArrayInputStream(((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES)));
    }

    public void jsClick(WebDriver driver, WebElement element) {
        ((JavascriptExecutor)driver).executeScript("arguments[0].click();",element);
    }

    public void actionsClick(WebDriver driver, WebElement element) {
        (new Actions(driver)).moveToElement(element).click().build().perform();
    }
}
