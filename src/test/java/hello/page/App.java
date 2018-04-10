package hello.page;

import static org.openqa.selenium.By.cssSelector;
import static org.openqa.selenium.support.ui.ExpectedConditions.attributeToBeNotEmpty;
import static org.openqa.selenium.support.ui.ExpectedConditions.not;
import static org.openqa.selenium.support.ui.ExpectedConditions.numberOfElementsToBeMoreThan;

import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

public class App {

  private final RemoteWebDriver driver;
  private WebElement input;

  public App(RemoteWebDriver driver) {
    this.driver = driver;
  }

  public void init() {
    driver.get("http://localhost:8080");
    input = driver.findElement(By.id("text"));
  }

  public void enterMessage(String message) {
    input.clear();
    input.sendKeys(message);
  }

  public void sendMessage() {
    driver.findElement(By.id("send")).click();
  }

  public List<WebElement> waitForMessageReceived() {
    return new WebDriverWait(driver, 10).until(
        numberOfElementsToBeMoreThan(cssSelector("#greetings tr"), 0));
  }

  public boolean clickConnectButton() {
    driver.findElement(By.id("connect")).click();
    return new WebDriverWait(driver, 10).until(
        not(attributeToBeNotEmpty(input, "disabled")));
  }
}
