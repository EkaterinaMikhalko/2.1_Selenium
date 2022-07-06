package ru.netology.web;

import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CardOrderTest {

    private WebDriver driver;
    @BeforeAll
    static void setUp (){
        System.setProperty("webdriver.chrome.driver", "driver/win/chromedriver.exe");
    }

    @BeforeEach
    void setUp2 () {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--no-sandbox");
        options.addArguments("--headless");
        driver = new ChromeDriver(options);
    }

    @AfterEach
    void close() {
        driver.quit ();
        driver = null;

    }

    @Test
    public void shouldSubmitRequest (){
        driver.get ("http://localhost:9999");
        List<WebElement> elements = driver.findElements (By.className("input__control"));
        elements.get(0).sendKeys("Иван Иванов");
        elements.get(1).sendKeys("+79066325665");
        driver.findElement (By.className("checkbox__box")).click();
        driver.findElement (By.className("button__content")).click();
        String text = driver.findElement (By.cssSelector("[data-test-id=\"order-success\"]")).getText();
        assertEquals ("Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.", text.trim());
    }



    @Test
    public void shouldNotSubmitRequestEng (){
        driver.get ("http://localhost:9999");
        List<WebElement> elements = driver.findElements (By.className("input__control"));
        elements.get(0).sendKeys("Ivanov Ivan");
        elements.get(1).sendKeys("+79066325665");
        driver.findElement (By.className("checkbox__box")).click();
        driver.findElement (By.className("button__content")).click();
        String text = driver.findElement (By.className("input__sub")).getText();
        assertEquals ("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.", text.trim());
    }

    @Test
    public void shouldNotSubmitRequestWrongNumber (){
        driver.get ("http://localhost:9999");
        List<WebElement> elements = driver.findElements (By.className("input__control"));
        elements.get(0).sendKeys("Иванов Иван");
        elements.get(1).sendKeys("9066325665");
        driver.findElement (By.className("checkbox__box")).click();
        driver.findElement (By.className("button__content")).click();
        WebElement telId = driver.findElement(By.className("input_type_tel"));
        WebElement wrongNumber = telId.findElement(By.className("input__sub"));
        String text = wrongNumber.getText();
        assertEquals ("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.", text.trim());
    }

    @Test
    public void shouldNotSubmitRequestNoNumber (){
        driver.get ("http://localhost:9999");
        List<WebElement> elements = driver.findElements (By.className("input__control"));
        elements.get(0).sendKeys("Иванов Иван");
        elements.get(1).sendKeys("");
        driver.findElement (By.className("checkbox__box")).click();
        driver.findElement (By.className("button__content")).click();
        WebElement telId = driver.findElement(By.className("input_type_tel"));
        WebElement wrongNumber = telId.findElement(By.className("input__sub"));
        String text = wrongNumber.getText();
        assertEquals ("Поле обязательно для заполнения", text.trim());
    }

    @Test
    public void shouldNotSubmitRequestNoName (){
        driver.get ("http://localhost:9999");
        List<WebElement> elements = driver.findElements (By.className("input__control"));
        elements.get(0).sendKeys("");
        elements.get(1).sendKeys("+79066325665");
        driver.findElement (By.className("checkbox__box")).click();
        driver.findElement (By.className("button__content")).click();
        String text = driver.findElement (By.className("input__sub")).getText();
        assertEquals ("Поле обязательно для заполнения", text.trim());
    }


}
