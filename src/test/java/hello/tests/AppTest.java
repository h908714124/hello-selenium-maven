package hello.tests;

import static hello.config.DriverFactory.createFirefoxDriver;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import hello.page.App;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;

public class AppTest {

  private RemoteWebDriver driver;

  private App app;

  @Before
  public void setUp() {
    driver = createFirefoxDriver();
    app = new App(driver);
    app.init();
  }

  @After
  public void quit() {
    driver.quit();
  }

  @Test
  public void test() throws IOException {
    assertTrue("Connection failed", app.clickConnectButton());
    screenshot("00_initial.png");
    app.enterMessage("world");
    screenshot("11_text_entered.png");
    app.sendMessage();
    List<WebElement> messages = app.waitForMessageReceived();
    screenshot("22_message_received.png");
    assertEquals(1, messages.size());
    WebElement firstMessage = messages.get(0);
    WebElement td = firstMessage.findElement(By.tagName("td"));
    assertEquals("Hello, world!", td.getText());
  }

  private void screenshot(String filename) throws IOException {
    Path screenshots = Paths.get("screenshots");
    assert screenshots.toFile().exists() || screenshots.toFile().mkdirs();
    try (FileOutputStream out = new FileOutputStream(
        screenshots.resolve(filename).toFile())) {
      out.write(driver.getScreenshotAs(OutputType.BYTES));
    }
  }
}
