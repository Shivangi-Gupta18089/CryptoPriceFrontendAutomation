package TestSuites;

import dataProvider.CryptoDataProvider;
import logger.log;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import pages.coinMarketCapPages;
import util.baseTest;

public class coinMarketCap extends baseTest {

    coinMarketCapPages cp;

    @Test(dataProvider = "cryptoSymbols", dataProviderClass = CryptoDataProvider.class)
    public void testPrice(String symbol) {
        softAssert = new SoftAssert();
        cp = new coinMarketCapPages();
        try {
            String price = cp.getCryptoPrice(symbol);
            log.info(symbol + " price is: " + price);
            System.out.println(symbol + " price: " + price);
            softAssert.assertTrue(price != null && !price.contains("not found"),
                    "Price should be valid for symbol: " + symbol);
        } catch (Exception e) {
            log.error("Exception occurred while fetching price for: " + symbol, e);
            softAssert.fail("Exception in testPrice for symbol: " + symbol + " - " + e.getMessage());
        } finally {
            softAssert.assertAll();
        }
    }

}
