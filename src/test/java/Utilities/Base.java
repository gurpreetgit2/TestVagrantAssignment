package Utilities;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class Base {

    private static final String PROPERTY_PATH = "src/test/java/properties/application.properties";
    protected WebDriver driver;

    //1. method to get the property value from property name
    public String getPropertyValue(String propertyName) {
        Properties prop = new Properties();
        try {
            prop.load(new FileInputStream(PROPERTY_PATH));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return prop.getProperty(propertyName);
    }

    //2. method to initialize the webDriver using WebDriverManager
    public WebDriver getWebDriver(String browserName) {
        if (browserName.equalsIgnoreCase("chrome")) {
            WebDriverManager.chromedriver().setup();
            return new ChromeDriver();
        } else if (browserName.equalsIgnoreCase("edge")) {
            WebDriverManager.edgedriver().setup();
            return new EdgeDriver();
        } else {
            System.out.println("Browser name not valid");
            return null;
        }
    }

    //3. method for the implicit wait
    public void setImplicitWait(long durationInSeconds) {
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(durationInSeconds));
    }

    //4. method to compare two values - UI Layer and API Layer
    public boolean compareTwoTempValues(float tempFromUiLayer, float tempFromApiLayer, float allowedVariance) {

        float diff = Math.abs(tempFromApiLayer - tempFromUiLayer);

        if (diff > allowedVariance) {
            System.out.println("The variance in temperature is more than the allowed variance");
            throw new RuntimeException("The variance in temperature is: " + diff + " which is more than the allowed variance value of: " + allowedVariance);
        } else
            return true;
    }

    //5. Return Map for query parameters
    public Map<String, String> getMapForQueryParams() {
        HashMap<String, String> map = new HashMap();
        map.put("lat", getPropertyValue("lat.value.for.api"));
        map.put("lon", getPropertyValue("long.value.for.api"));
        map.put("appid", getPropertyValue("value.for.app.id"));
        map.put("units", getPropertyValue("value.for.units"));
        return map;
    }

}
