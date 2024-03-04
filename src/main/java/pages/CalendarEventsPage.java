package pages;

import data.catalog.sorted.EventTypeData;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CalendarEventsPage extends BasePage{

    public CalendarEventsPage(WebDriver driver){
        super(driver,"/events/near/");
    }

    @FindBy(css = ".dod_new-event")
    private List<WebElement> eventTiles;

    @FindBy(css = ".dod_new-event__calendar-icon ~.dod_new-event__date-text")
    private List<WebElement> dataEvents;

    @FindBy(css = ".dod_new-event .dod_new-type__text")
    private List<WebElement> eventsTypes;

    private String dropdownSortingEventsListSelector = ".dod_new-events-dropdown";
    private String dropdownEventsListSelector = dropdownSortingEventsListSelector + " .dod_new-events-dropdown__list";
    private String dropdownSortingEventsItemTemplate = dropdownSortingEventsListSelector + " [title='%s']";

    public CalendarEventsPage checkEventTilesShouldBeVisible(){
        Assertions.assertTrue(waiters.WaitForCondition(ExpectedConditions.visibilityOfAllElements(eventTiles)));
        return this;
    }

    public void checkStartEventDate(){
      for (WebElement dataEvent: dataEvents){
          LocalDate currentDate = LocalDate.now();

          Pattern pattern = Pattern.compile("\\d+\\s+[а-яА-Я]+\\s+\\d{4}");
          String dateEventStr = dataEvent.getText();
          Matcher matcher = pattern.matcher(dateEventStr);
          if(!matcher.find()) {
              dateEventStr += String.format(" %d", currentDate.getYear());

          }
          LocalDate eventDate = LocalDate.parse(dateEventStr, DateTimeFormatter.ofPattern("dd MMMM yyyy", Locale.forLanguageTag("ru")));

          Assertions.assertTrue(eventDate.isAfter(currentDate) || eventDate.isEqual(currentDate),"Error");
      }
    }

    private CalendarEventsPage dropdownSortingEventsShouldNotBeOpened(){
        Assertions.assertTrue(waiters.WaitForCondition(
                ExpectedConditions.not(
                        ExpectedConditions.attributeContains(
                                $(dropdownSortingEventsListSelector),"class","dod_new-events-dropdown_opened")
                )
        )

        );
        return this;
    }
    private CalendarEventsPage dropdownSortingEventsShouldBeOpened(){
        Assertions.assertTrue(waiters.WaitForCondition(
                                ExpectedConditions.attributeContains(
                                        $(dropdownSortingEventsListSelector),"class","dod_new-events-dropdown_opened")
                        )

        );
        return this;
    }
    // кликаем по разделу [Все мероприятия]
    private CalendarEventsPage openSortingEventsDropdown(){
      $(dropdownSortingEventsListSelector).click();

      return this;
    }

    private CalendarEventsPage sortingItemsShouldBeVisible(){
      Assertions.assertTrue(waiters.waitElementVisible($(dropdownEventsListSelector)));

      return this;
    }

    //кликаем по элементу
    private CalendarEventsPage clickSortingItem(EventTypeData eventSortedData){
        $(String.format(dropdownSortingEventsItemTemplate, eventSortedData.getName())).click();

        return this;
    }

    public CalendarEventsPage selectSortedEventsType(EventTypeData eventSortedData){
            this.dropdownSortingEventsShouldNotBeOpened()
                .openSortingEventsDropdown()
                .dropdownSortingEventsShouldBeOpened()
                .sortingItemsShouldBeVisible()
                .clickSortingItem(eventSortedData);
       return this;
    }

    public CalendarEventsPage checkEventsType(EventTypeData eventTypeData){
        for (WebElement element: eventsTypes) {
            Assertions.assertEquals(eventTypeData.getName(), element.getText(),"Error");
        }
        return this;
    }


}
