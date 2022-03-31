package web;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.AssertJUnit;
import utils.Constants;

public class LoginForm extends PageObject {
    public LoginForm(WebDriver driver) {
        super(driver);
    }

    public void clickOnLoginLink(){
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#header > div > div > div.hMedMenu > div.customMenu > div.myAccountHolder.customMenuItem.withLocalization > div > div > a.btnSignIn"))).click();
    }

    public void login() throws InterruptedException {
        WebDriverWait wait = new WebDriverWait(driver, 10);
        popUpControl();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("email"))).sendKeys(Constants.MAIL);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("password"))).sendKeys(Constants.PASSWORD);
        wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("loginButton"))).click();
        // Check if login is true
        Thread.sleep(3000);
        AssertJUnit.assertEquals(wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#header > div > div > div.hMedMenu > div.customMenu > div.myAccountHolder.customMenuItem.hasMenu.withLocalization > div.myAccount > a.menuLink.user"))).getText(),"Yunus Yıldız");
    }

    public void falseLogin() {
        WebDriverWait wait = new WebDriverWait(driver, 10);
        popUpControl();
        //True mail, wrong password
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("email"))).sendKeys(Constants.MAIL);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("password"))).sendKeys(Constants.WRONG_PASSWORD);
        wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("loginButton"))).click();
        // Check if login is true
        AssertJUnit.assertEquals(wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#loginForm > div.inputField.error > div.errorMessage > div"))).getText(), "E-posta adresiniz veya şifreniz hatalı");
        //Clear input areas
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("email"))).clear();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("password"))).clear();
        //Wrong mail, true password
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("email"))).sendKeys(Constants.WRONG_MAIL);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("password"))).sendKeys(Constants.PASSWORD);
        // Check if login is true
        AssertJUnit.assertEquals(wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#loginForm > div.inputField.error > div.errorMessage > div"))).getText(),"E-posta adresiniz veya şifreniz hatalı");
    }

    public void checkInputAreaNames(){
        WebDriverWait wait = new WebDriverWait(driver, 10);
        AssertJUnit.assertEquals(wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div.inputField:nth-child(1) > label:nth-child(2)"))).getText(),"E-Posta Adresi");
        AssertJUnit.assertEquals(wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div.inputField:nth-child(2) > label:nth-child(2)"))).getText(),"Şifre");
        AssertJUnit.assertTrue(wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".showPass"))).isDisplayed());
        AssertJUnit.assertTrue(wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("loginButton"))).isDisplayed());
    }

    public void checkForgotPassword(){
        WebDriverWait wait = new WebDriverWait(driver, 10);
        AssertJUnit.assertEquals(wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("forgotPassword"))).getText(),"Şifremi Unuttum");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("forgotPassword"))).click();
        AssertJUnit.assertEquals(wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#forgotPasswordModalWrapper > div.popupContent > div > p"))).getText(),"Şifre yenileme linki, e-posta adresinize gönderilecektir.");
        AssertJUnit.assertEquals(wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#forgotPasswordModalWrapper > div.popupContent > div > div > div > label"))).getText(),"E-Posta Adresi");
    }

    public void closePopUp(){
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#cookieUsagePopIn > span"))).click();
    }

    public void popUpControl(){
        try {
            if (driver.findElement(By.cssSelector("#cookieUsagePopIn > span")).isDisplayed()) {
                closePopUp();
            }
        }
        catch (NoSuchElementException ignored){
        }
    }

    public void captchaControl(){
        try {
            if (driver.findElement(By.id("captchaImage")).isDisplayed()) {
                driver.findElement(By.cssSelector("#forgotPasswordModalWrapper > div:nth-child(1) > span:nth-child(2)")).isDisplayed();
            }
        }
        catch (Exception e){
            System.out.println("CAPTCHA SEBEBIYLE TEST ŞU AN YAPILAMADI.");
        }
    }



}
