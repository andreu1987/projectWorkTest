package pages;

import common.AbsCommon;
import org.openqa.selenium.WebDriver;

public class LessonPage extends BasePage {
    public LessonPage(WebDriver driver, String lessonPath){
        super(driver,String.format("/lessons/%s",lessonPath));
    }
}
