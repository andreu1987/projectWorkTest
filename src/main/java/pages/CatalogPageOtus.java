package pages;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import javax.lang.model.util.Elements;
import javax.swing.text.Element;
import java.io.IOException;
import java.time.Duration;
import java.time.temporal.TemporalUnit;
import java.util.List;

import static org.apache.commons.lang3.RegExUtils.replaceAll;
import static org.bouncycastle.asn1.crmf.SinglePubInfo.web;

public class CatalogPageOtus extends BasePage {

    public CatalogPageOtus(WebDriver driver){
        super(driver,"/catalog/courses");
    }
    // элемент карточка
    @FindBy(xpath = "//section//div[not(@style)]/a[contains(@href, '/lessons/')][.//h6]")
    private List<WebElement> lessonsNumber;
    //элемент дата карточки
    @FindBy(xpath = "//section//div[not(@style)]/a[contains(@href, '/lessons/')]/h6/following-sibling::div")
    private List<WebElement> lessonDuration;


    // Метод подсчета карточек на страницы
    public void lessonsNumberShou(int number){
        Assertions.assertEquals(number,lessonsNumber.size(),
                String.format("Numbers of lessons tiles should be %d",number));
        System.out.println("На страницы [Католог] нашли  " + number + " карточек курса");
    }

    // метод проверки карточки
    public void clickRandomLessonsCards(){
      faker.options().nextElement(lessonsNumber).click();
        System.out.println(lessonsNumber);

    }

    // метод возвращает колличество
    public int getTilesNumbers(){
        return lessonsNumber.size();
    }

    // метод проверки названия карточки по индексу
    public String getLessonNameByIndex(int index){
      return lessonsNumber.get(--index).findElement(By.xpath(".//h6")).getText();
    }

    // метод проверка длительности (Даты у карточки)
    public String getLessonDuration(int index){
        return lessonDuration.get(--index).getText();
    }

    private Document getDomPage(int index) throws IOException {
        String url = lessonsNumber.get(--index).getAttribute("href");
        return Jsoup.connect(url).get();
    }

    //  метод проверки карточки
    public void checkHeaderLessonByIndex(int index, String expectedHeader) throws IOException {
            Document dom = getDomPage(index);
            //WebElement headerPageElement = (WebElement) dom.selectFirst("h1");
            //Assertions.assertEquals(expectedHeader, headerPageElement.getText(),"Error");
        Element headerPageElement = dom.selectFirst("h1");
        Assertions.assertEquals(expectedHeader, headerPageElement.Text(),"Error");

        }

    //метод проверки
    public void checkDescriptionLessonByIndex(int index) throws IOException{
        WebElement webElement = (WebElement) getDomPage(index).selectXpath("//h1/following-sibling::div[text()]");
        if (webElement.isDisplayed()){
            webElement = (WebElement) getDomPage(index).selectXpath("//h1/following-sibling::div/p[text()]");
        }

        String headerPageElement = webElement.getText();

    }

    //метод проверки длительности
    public void checkLessonDuration(int index,String expectedDuration) throws IOException{
        WebElement headerPageElement = (WebElement)
                getDomPage(index).selectXpath("//div/following-sibling::p[contains(text(),'месяц')]").get(0);
        Assertions.assertEquals(expectedDuration.replaceAll("^.*?\\s*",""),headerPageElement.getText());
    }

    // метод проверки формат обучения
    public void checkLessonFormat(int index,String format) throws IOException{
        WebElement formatLessonElement = (WebElement)
                getDomPage(index).selectXpath(String.format("//p[contains(text(),'%s')]",format)).get(0);
        Assertions.assertFalse(formatLessonElement.getText().isEmpty(),"Error");
    }


    }

