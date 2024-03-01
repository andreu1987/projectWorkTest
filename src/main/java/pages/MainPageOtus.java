package pages;

import org.openqa.selenium.WebDriver;

public class MainPageOtus extends BasePage{
    public MainPageOtus(WebDriver driver) {
        super(driver,"/");
    }
    private String otusUrl = System.getProperty("otus.Url");

    //Вход на сайт
    public MainPageOtus goTo() {
        driver.get(otusUrl);
        return this;
    }
}
