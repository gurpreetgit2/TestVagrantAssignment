package org.testVagrant;

import static org.junit.Assert.assertTrue;

import Utilities.Base;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.OrderWith;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import pageObject.DelhiPage;
import pageObject.HomePage;

import java.util.HashMap;

/**
 * Problem statement from TestVagrant PDF.
 */
public class AppTest extends Base {
    float tempFromApiLayer;
    float tempFromUiLayer;

    @Test()
    public void compareBothTemps() throws InterruptedException {
        //1. fetch the value from API
        RestAssured.baseURI = getPropertyValue("base.uri.of.application");
        String response = RestAssured.given().queryParams(getMapForQueryParams()).log().all().
                when().get(getPropertyValue("context.path.of.api")).
                then().log().all().statusCode(200).contentType(ContentType.JSON).extract().response().asString();
        JsonPath path = new JsonPath(response);
        tempFromApiLayer = path.getFloat("main.temp");

        //2. fetch the value from UI
        driver = getWebDriver("chrome");
        HomePage hm = new HomePage(driver);
        driver.get(getPropertyValue("url.of.application"));
        setImplicitWait(5);
        hm.getSearchBox().sendKeys(getPropertyValue("city.name.for.ui"));
        Thread.sleep(2000);
        hm.getSearchBox().sendKeys(Keys.ARROW_DOWN, Keys.ENTER);
        DelhiPage dl = new DelhiPage(driver);
        tempFromUiLayer = Float.parseFloat(dl.getTempDiv().getText().substring(0, 2));
        driver.close();
        driver.quit();

        //3. Compare two variance
        Assert.assertTrue(compareTwoTempValues(tempFromUiLayer, tempFromApiLayer, 5));

    }

}
