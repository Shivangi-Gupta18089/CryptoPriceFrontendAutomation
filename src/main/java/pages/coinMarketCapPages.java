package pages;

import logger.log;
import org.openqa.selenium.By;
import util.baseTest;

public class coinMarketCapPages extends baseTest {


    public String getCryptoPrice(String symbol) {
        try {
            String xpath = "//tbody//tr[.//p[text()='" + symbol + "']]//td[4]//span";
            expWait(driver, xpath);
            return driver.findElement(By.xpath(xpath)).getText();
        } catch (Exception e) {
            log.error("Failed to find price for " + symbol, e);
            return "Symbol not found: " + symbol;
        }
    }
}