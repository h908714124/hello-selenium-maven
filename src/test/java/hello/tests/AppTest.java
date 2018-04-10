package hello.tests;

import static hello.config.DriverFactory.createFirefoxDriver;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import hello.page.App;
import java.util.List;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;

public class AppTest {

  private RemoteWebDriver driver;

  @Before
  public void setUp() {
    driver = createFirefoxDriver();
  }

  @After
  public void quit() {
    driver.quit();
  }

  @Test
  public void test() {
    driver.get("http://localhost:8080");
    App app = new App(driver);
    assertTrue("Connection failed", app.connect());
    List<WebElement> messages = app.sendMessage("world");
    assertEquals(1, messages.size());
    WebElement firstMessage = messages.get(0);
    assertEquals("Hello, world!", firstMessage.findElement(By.tagName("td")).getText());
  }
}
