package pages;

import common.AbsCommon;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;

public abstract class BasePage extends AbsCommon {
    public BasePage (WebDriver driver,String path){
        super(driver);
        this.path = path.endsWith("/") ? path.substring(0,path.length() -1): path;
    }
    private String OTUS_URL = System.getProperty("otus.Url");
    private String path = "";

    public void open(){
        driver.get(OTUS_URL + this.path);
    }

    public void open(List<String> queryParams){
        String url = OTUS_URL + this.path + "?" + String.join("&",queryParams);
        driver.get(url);
    }

    public WebElement $(By by){
        return driver.findElement(by);
    }

    public WebElement $(String locator){
        By by = locator.startsWith("/") ? By.xpath(locator): By.cssSelector(locator);
        return this.$(by);
    }

}
