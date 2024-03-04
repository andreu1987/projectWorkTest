package pages;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class LessonPage extends BasePage {
    public LessonPage(WebDriver driver, String lessonPath){
        super(driver,String.format("/lessons/%s",lessonPath));
    }

    // элемент карточка
    @FindBy(xpath = "//section//div[not(@style)]/a[contains(@href, '/lessons/')][.//h6]")
    private List<WebElement> lessonsNumber;


    // метод проверки карточки
    public void clickRandomLessonsCards(){
        faker.options().nextElement(lessonsNumber).click();
        System.out.println(lessonsNumber);

    }


    // метод проверки названия карточки
    public void getLessonName(){
        WebElement lessonsNumber = driver.findElement(By.xpath(".//h1"));
        lessonsNumber.getText();
        System.out.println("Названия карточки " + lessonsNumber.getText());
    }


    //метод проверки описания карточки
    public void checkDescriptionLesson() {
        WebElement headerPageElement = driver.findElement(By.xpath("//h1/following-sibling::div[text()]"));
        if (headerPageElement.isDisplayed()) {
            driver.findElement(By.xpath("//h1/following-sibling::div/p[text()]"));
        }
        headerPageElement.getText();
        System.out.println("Описания карточки " + headerPageElement.getText());
    }



    //метод проверки длительности
    public void checkLessonDuration() {
        WebElement pageDurationElement = driver.findElement
                (By.xpath("//div/following-sibling::p[contains(text(), 'месяц')]"));
        pageDurationElement.getText();
        System.out.println("Длительность обучения "+ pageDurationElement.getText());
    }


    // метод проверки формат обучения
    public void checkLessonFormat(String format){
        WebElement formatLessonElement = driver.findElement
                (By.xpath(String.format("//p[contains(text(), '%s')]",format)));
        Assertions.assertFalse(Boolean.parseBoolean(formatLessonElement.getText()),"Error");
        System.out.println("Формат обучения курса " + formatLessonElement.getText());
    }




}
