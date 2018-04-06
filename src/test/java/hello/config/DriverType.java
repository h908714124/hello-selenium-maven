package hello.config;

import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

public enum DriverType {

  FIREFOX_HEADLESS {
    public RemoteWebDriver getWebDriverObject(DesiredCapabilities capabilities) {
      FirefoxOptions options = new FirefoxOptions();
      options.merge(capabilities);
      options.setHeadless(true);

      return new FirefoxDriver(options);
    }
  };

  public abstract RemoteWebDriver getWebDriverObject(DesiredCapabilities capabilities);
}
