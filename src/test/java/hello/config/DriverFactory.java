package hello.config;

import static hello.config.DriverType.FIREFOX_HEADLESS;
import static hello.config.DriverType.valueOf;
import static org.openqa.selenium.Proxy.ProxyType.MANUAL;
import static org.openqa.selenium.remote.CapabilityType.PROXY;

import java.net.MalformedURLException;
import java.net.URL;
import org.openqa.selenium.Platform;
import org.openqa.selenium.Proxy;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

public class DriverFactory {

  private RemoteWebDriver driver;
  private DriverType selectedDriverType;

  private final String operatingSystem = System.getProperty("os.name").toUpperCase();
  private final String systemArchitecture = System.getProperty("os.arch");
  private final boolean useRemoteWebDriver = Boolean.getBoolean("remoteDriver");
  private final boolean proxyEnabled = Boolean.getBoolean("proxyEnabled");
  private final String proxyHostname = System.getProperty("proxyHost");
  private final Integer proxyPort = Integer.getInteger("proxyPort");
  private final String proxyDetails = String.format("%s:%d", proxyHostname, proxyPort);

  public DriverFactory() {
    DriverType driverType = FIREFOX_HEADLESS;
    String browser = System.getProperty("browser", driverType.toString()).toUpperCase();
    try {
      driverType = valueOf(browser);
    } catch (IllegalArgumentException ignored) {
      System.err.println("Unknown driver specified, defaulting to '" + driverType + "'...");
    } catch (NullPointerException ignored) {
      System.err.println("No driver specified, defaulting to '" + driverType + "'...");
    }
    selectedDriverType = driverType;
  }

  public RemoteWebDriver getDriver() throws Exception {
    if (null == driver) {
      instantiateWebDriver(selectedDriverType);
    }

    return driver;
  }

  public RemoteWebDriver getStoredDriver() {
    return driver;
  }

  public void quitDriver() {
    if (null != driver) {
      driver.quit();
      driver = null;
    }
  }

  private void instantiateWebDriver(DriverType driverType) throws MalformedURLException {
    System.out.println(" ");
    System.out.println("Current Operating System: " + operatingSystem);
    System.out.println("Current Architecture: " + systemArchitecture);
    System.out.println("Current Browser Selection: " + selectedDriverType);
    System.out.println(" ");

    DesiredCapabilities desiredCapabilities = new DesiredCapabilities();

    if (proxyEnabled) {
      Proxy proxy = new Proxy();
      proxy.setProxyType(MANUAL);
      proxy.setHttpProxy(proxyDetails);
      proxy.setSslProxy(proxyDetails);
      desiredCapabilities.setCapability(PROXY, proxy);
    }

    if (useRemoteWebDriver) {
      URL seleniumGridURL = new URL(System.getProperty("gridURL"));
      String desiredBrowserVersion = System.getProperty("desiredBrowserVersion");
      String desiredPlatform = System.getProperty("desiredPlatform");

      if (null != desiredPlatform && !desiredPlatform.isEmpty()) {
        desiredCapabilities.setPlatform(Platform.valueOf(desiredPlatform.toUpperCase()));
      }

      if (null != desiredBrowserVersion && !desiredBrowserVersion.isEmpty()) {
        desiredCapabilities.setVersion(desiredBrowserVersion);
      }

      driver = new RemoteWebDriver(seleniumGridURL, desiredCapabilities);
    } else {
      driver = driverType.getWebDriverObject(desiredCapabilities);
    }
  }
}

