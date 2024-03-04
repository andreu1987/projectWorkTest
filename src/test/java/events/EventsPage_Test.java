package events;

import data.catalog.sorted.EventTypeData;
import factory.DriverFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import pages.CalendarEventsPage;

public class EventsPage_Test {
    private WebDriver driver;
    private CalendarEventsPage calendarEventsPage;

    @BeforeEach
    public void beforeEach() {
        this.driver = new DriverFactory().create();

        this.calendarEventsPage = new CalendarEventsPage(driver);
        calendarEventsPage.open();

    }

    @Test
    public void eventsTiles(){
        calendarEventsPage.checkEventTilesShouldBeVisible()
                .checkStartEventDate();

    }


    @Test
    public void selectSortedType(){
      calendarEventsPage.selectSortedEventsType(EventTypeData.OPEN)
                        .checkEventTilesShouldBeVisible()
                        .checkEventsType(EventTypeData.OPEN);
    }

    @AfterEach
    public void finish() {
        if (driver != null){
            driver.quit();
        }
    }
}
