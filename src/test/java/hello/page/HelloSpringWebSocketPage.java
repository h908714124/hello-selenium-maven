package hello.page;

import org.openqa.selenium.By;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class HelloSpringWebSocketPage {

  private final RemoteWebDriver driver;

  public HelloSpringWebSocketPage(RemoteWebDriver driver) {
    this.driver = driver;
  }

  public void enterSearchTerm(String searchTerm) {
    driver.findElement(By.id("text")).clear();
    driver.findElement(By.id("text")).sendKeys(searchTerm);
    driver.findElement(By.id("send")).click();
    WebDriverWait wait = new WebDriverWait(driver, 10);
    wait.until(driver -> !driver.findElements(By.cssSelector("#greetings tr")).isEmpty());
  }

  public void connect() {
    driver.findElement(By.id("connect")).click();
    WebDriverWait wait = new WebDriverWait(driver, 10);
    wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.id("conversation"))));
  }
}
