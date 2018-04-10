package hello.config;

import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

public class DriverFactory {

  public static RemoteWebDriver createFirefoxDriver() {
    return new FirefoxDriver(getOptions());
  }

  private static FirefoxOptions getOptions() {
    FirefoxOptions options = new FirefoxOptions();
    options.merge(new DesiredCapabilities());
    options.setHeadless(true);
    return options;
  }
}
