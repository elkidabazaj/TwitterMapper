package twitter;

import twitter4j.conf.Configuration;
import twitter4j.conf.ConfigurationBuilder;

public class ConfigurationManager {
    public static Configuration getDefaultConfiguration() {
        ConfigurationBuilder cb = new ConfigurationBuilder();
        cb.setOAuthConsumerKey("dB2HRCrZK9oKs88tm6FnOpcDc")
                .setOAuthConsumerSecret("b8LKCZpr7fSyK62uUxvBQ6syASmtlHoNtVl3EhlS6O31JTFPeV")
                .setOAuthAccessToken("3204939767-G0BfsVogqd9Tg3CuA9CEKKk0wHVOpRaieEt35cu")
                .setOAuthAccessTokenSecret("9gor9Xr1dIcafuyu6btKBqwWkCFL0ybPHQRhcFTCGANnH");

        return cb.build();
    }
}
