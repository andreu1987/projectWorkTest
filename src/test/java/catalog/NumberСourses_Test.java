package catalog;

import data.catalog.LessonsCategoryDate;
import factory.DriverFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import pages.CatalogPageOtus;
import pages.LessonPage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class NumberСourses_Test {
    private WebDriver driver;
    private CatalogPageOtus catalogPageOtus = null;


    @BeforeEach
    public void beforeEach() {
        this.driver = new DriverFactory().create();

        List<String> queryParams = new ArrayList<>();
        queryParams.add(String.format("categories=%s", LessonsCategoryDate.TESTING.name().toLowerCase(Locale.ROOT)));

        this.catalogPageOtus = new CatalogPageOtus(driver);
        catalogPageOtus.open(queryParams);

    }

    @Test
    public void TestTileNumbers(){
        catalogPageOtus.lessonsNumberShou(10); // проверка колличесво карточек на страницы

    }



    @Test
    public void TestCheckDataOnLessonPage() throws IOException {
        for (int i = 1; i < catalogPageOtus.getTilesNumbers(); i++){
            String expectedHeader = catalogPageOtus.getLessonNameByIndex(i);
            String expectedLessonDuration = catalogPageOtus.getLessonDuration(i);

            catalogPageOtus.checkHeaderLessonByIndex(i,expectedHeader);
            catalogPageOtus.checkDescriptionLessonByIndex(i);
            catalogPageOtus.checkLessonDuration(i,expectedLessonDuration); // метод проверки длительности
            catalogPageOtus.checkLessonFormat(i,"Онлайн");// метод проверки формата обучения

        }


        catalogPageOtus.clickRandomLessonsCards();
        LessonPage lessonPage = new LessonPage(driver,"");
        lessonPage.getLessonName();
        lessonPage.checkDescriptionLesson();
        lessonPage.checkLessonDuration();
        lessonPage.checkLessonFormat("Онлайн");

    }


    @AfterEach
    public void finish() {
        if (driver != null){
            driver.quit();
        }
    }
}
