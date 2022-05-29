package pageObject;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class DelhiPage {

    protected WebDriver driver;

    public DelhiPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    @FindBy(xpath = "//div[@class='cur-con-weather-card__panel']//div[@class='temp']")
    private WebElement tempDiv;

    public WebElement getTempDiv() {
        return tempDiv;
    }
}
