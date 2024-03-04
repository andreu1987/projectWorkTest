package tools;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class Waiters {
    //private int waiterTimeout = Integer.parseInt(System.getProperty("wait.timeout","10"));
    private WebDriver driver;

    public Waiters(WebDriver driver){
        this.driver = driver;
    }

    public boolean WaitForCondition(ExpectedCondition condition){
        try {
            new WebDriverWait(driver, Duration.ofSeconds(15)).until(condition);
            return true;
        } catch (TimeoutException ignored){
            return false;
        }

    }

    public boolean waitElementVisible(WebElement element){
        return WaitForCondition(ExpectedConditions.visibilityOf(element));
    }

    public boolean waitNotElementPresent(By locator){
        return this.WaitForCondition(ExpectedConditions.not(ExpectedConditions.presenceOfElementLocated(locator)));
    }

    public boolean waitElementPresent(By locator){
        return this.WaitForCondition(ExpectedConditions.presenceOfElementLocated(locator));
    }

}
