package dataProvider;

import org.testng.annotations.DataProvider;

public class CryptoDataProvider {

    @DataProvider(name = "cryptoSymbols")
    public Object[][] getCryptoSymbols() {
        return new Object[][] {
                {"BTC"},
                {"ETH"},
                {"USDT"},
                {"XRP"},
                {"BNB"},
        };
    }


}
