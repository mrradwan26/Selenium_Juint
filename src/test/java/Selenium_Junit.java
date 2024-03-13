import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import static java.lang.Thread.sleep;

public class Selenium_Junit {
    WebDriver driver;

    @Before
    public void setup() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

    }

    @Test
    public void getTitle() {
        driver.get("https://demoqa.com/");
        String title_actual = driver.getTitle();
        String expected = "DEMOQA";
        System.out.println(title_actual);
        Assert.assertEquals(expected, title_actual);
    }

    @Test
    public void checkIfImageExist() {
        driver.get("https://demoqa.com/");
        boolean status = driver.findElement(By.xpath("//header/a[1]/img[1]")).isDisplayed();
        System.out.println(status);
        Assert.assertTrue(status);

    }

    @Test
    public void submitForm() throws InterruptedException {
        driver.get("https://demoqa.com/text-box");
//        driver.findElement(By.id("userName")).sendKeys("Test User"); //UsingId
//        driver.findElement(By.cssSelector("[type=text]")).sendKeys("Test User"); //UsingType

        List<WebElement> formControls = driver.findElements(By.className("form-control"));
        formControls.get(0).sendKeys("Test User");

//        driver.findElements(By.className("form-control")).get(0).sendKeys("Test User");
//        driver.findElement(By.id("userEmail")).sendKeys("testuser@test.com");
        formControls.get(1).sendKeys("testuser@test.com");

//        driver.findElements(By.className("form-control")).get(1).sendKeys("testuser@test.com");
//        Thread.sleep(4000);

        List<WebElement> btnSubmit = driver.findElements(By.tagName("button"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", btnSubmit.get(1));
        btnSubmit.get(1).click();
//        WebElement submitButton = driver.findElement(By.id("submit"));

//        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", btnSubmit);
//        btnSubmit.get(1).click();
//        String name_actual= driver.findElement(By.id("name")).getText();
//        String name_expected="Test User";
//        Assert.assertTrue(name_actual.contains(name_expected));

    }

    @Test
    public void clickOnButtons() {
        driver.get("https://demoqa.com/buttons");
        List<WebElement> buttons = driver.findElements(By.cssSelector("[type=button]"));

        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollBy(0,500)");

        Actions actions = new Actions(driver);
        actions.doubleClick(buttons.get(1)).perform();
        actions.contextClick(buttons.get(2)).perform();
        actions.click(buttons.get(3)).perform();

        String actual_message1 = driver.findElement(By.id("doubleClickMessage")).getText();
        String expected_message1 = "You have done a double click";

        String actual_message2 = driver.findElement(By.id("rightClickMessage")).getText();
        String expected_message2 = "You have done a right click";

        String actual_message3 = driver.findElement(By.id("dynamicClickMessage")).getText();
        String expected_message3 = "You have done a dynamic click";

        Assert.assertTrue(actual_message1.contains(expected_message1));
        Assert.assertTrue(actual_message2.contains(expected_message2));
        Assert.assertTrue(actual_message3.contains(expected_message3));
    }

    @Test
    public void alert() throws InterruptedException {
        driver.get("https://demoqa.com/alerts");

        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollBy(0,500)");

//        driver.findElement(By.id("alertButton")).click();
//        Thread.sleep(2000);
//        driver.switchTo().alert().accept();

//        driver.findElement(By.id("confirmButton")).click();
//        Thread.sleep(2000);
//        driver.switchTo().alert().accept();

        driver.findElement(By.id("promtButton")).click();
        driver.switchTo().alert().sendKeys("Hello");
        sleep(2000);
        driver.switchTo().alert().accept();
    }

    @Test
    public void selectDate() {
        driver.get("https://demoqa.com/date-picker");

        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollBy(0,600)");

        WebElement calander = driver.findElement(By.id("datePickerMonthYearInput"));
        calander.sendKeys(Keys.CONTROL + "a", Keys.BACK_SPACE);
        calander.sendKeys("03/08/2023");
        calander.sendKeys(Keys.ENTER);
    }

    @Test
    public void selectDropDownClassic() {
        driver.get("https://demoqa.com/select-menu");
        Select select = new Select(driver.findElement(By.id("oldSelectMenu")));
        select.selectByValue("3");
        select.selectByIndex(3);
        select.selectByVisibleText("Yellow");
    }

    @Test
    public void selectDropDownClassic2() {
        driver.get("https://demoqa.com/select-menu");

        driver.findElement(By.className("css-tlfecz-indicatorContainer")).click();
        driver.findElement(By.className("css-tlfecz-indicatorContainer")).sendKeys(Keys.ARROW_DOWN, Keys.ENTER);

    }

    @Test
    public void selectDropDown3() {
        driver.get("https://demoqa.com/select-menu");
        Select color = new Select(driver.findElement(By.id("oldSelectMenu")));
        color.selectByValue("1");
        Select cars = new Select(driver.findElement(By.id("cars")));
        if (cars.isMultiple()) {
            cars.selectByValue("volvo");
            cars.selectByValue("audi");
            cars.selectByValue("opel");
        }
    }

    @Test
    public void handleMultipeTable() throws InterruptedException {
        driver.get("https://demoqa.com/browser-windows");
        driver.findElement(By.id("tabButton")).click();
        Thread.sleep(2000);
        ArrayList<String> w = new ArrayList<>(driver.getWindowHandles());
// switch to open tab
        driver.switchTo().window(w.get(1));
        System.out.println("New tab title; " + driver.getTitle());
        String text = driver.findElement(By.id("sampleHeading")).getText();
        Assert.assertEquals(text, "This is a sample page");
        driver.close();
        driver.switchTo().window(w.get(0));
    }



    @After
    public void closeBrowser() {
//     driver.close();
//     }
    }
}