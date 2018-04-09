package hello.tests;

import static org.junit.Assert.assertEquals;

import hello.config.DriverFactory;
import hello.page.HelloSpringWebSocketPage;
import java.util.List;
import org.junit.After;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;

public class HelloSpringWebSocketIT {

  DriverFactory driverFactory = new DriverFactory();

  @After
  public void quit() {
    try {
      driverFactory.getStoredDriver().manage().deleteAllCookies();
      driverFactory.quitDriver();
    } catch (Exception ignored) {
      System.out.println("Unable to clear cookies, driver object is not viable...");
    }
  }


  @Test
  public void test() throws Exception {
    // Create a new WebDriver instance
    // Notice that the remainder of the code relies on the interface,
    // not the implementation.
    RemoteWebDriver driver = driverFactory.getDriver();

    // And now use this to visit Google
    driver.get("http://localhost:8080");
    // Alternatively the same thing can be done like this
    // driver.navigate().to("http://www.google.com");

    HelloSpringWebSocketPage app = new HelloSpringWebSocketPage(driverFactory.getDriver());

    app.connect();
    app.enterSearchTerm("world");

    List<WebElement> messages = driver.findElementsByCssSelector("#greetings tr");
    assertEquals(1, messages.size());
    WebElement firstMessage = messages.get(0);
    assertEquals("Hello, world!", firstMessage.findElement(By.tagName("td")).getText());
  }
}
