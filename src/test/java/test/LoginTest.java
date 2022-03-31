package test;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.AssertJUnit;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import utils.Constants;
import web.LoginForm;

public class LoginTest {
    private static final WebDriver driver = new FirefoxDriver();
    LoginForm webForm = new LoginForm(driver);
    WebDriverWait wait = new WebDriverWait(driver, 20);

    @BeforeSuite
    public void before() {
        System.setProperty("webdriver.firefox.driver", Constants.FIREFOX_DRIVER_LOCATION);
    }

    @Test(testName = "Go to login page, check the input area names is true and login & test if login success.")
    public void trueLoginTest() throws InterruptedException {
        driver.get(Constants.N11_URL);
        driver.manage().window().maximize();
        Assert.assertEquals(driver.getCurrentUrl(), "https://www.n11.com/giris-yap");
        webForm.checkInputAreaNames();
        webForm.login();
        driver.manage().deleteAllCookies();
    }

    @Test(testName = "Go to login page, check the input area names is true and enter wrong informations, check the error message.")
    public void falseLoginTest(){
        driver.get(Constants.N11_URL);
        driver.manage().window().maximize();
        Assert.assertEquals(driver.getCurrentUrl(), "https://www.n11.com/giris-yap");
        webForm.checkInputAreaNames();
        webForm.falseLogin();
        driver.manage().deleteAllCookies();

    }

    @Test(dataProvider = "wrongMails", testName = "Go to login page and enter wrong formatted mails to mail to input area, check the error message.")
    public void errorControlForMail(String mail){
        driver.get(Constants.N11_URL);
        driver.manage().window().maximize();
        Assert.assertEquals(driver.getCurrentUrl(), "https://www.n11.com/giris-yap");
        webForm.popUpControl();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("email"))).sendKeys(mail);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("password"))).click();
        AssertJUnit.assertEquals(wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#loginForm > div:nth-child(1) > div > div"))).getText(),"Lütfen geçerli bir e-posta adresi girin.");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("email"))).clear();
        driver.manage().deleteAllCookies();

    }

    @Test(dataProvider = "wrongPasswords", testName = "Go to login page and enter wrong formatted passwords to password input area, check the error message.")
    public void errorControlForPassword(String password){
        driver.get(Constants.N11_URL);
        driver.manage().window().maximize();
        Assert.assertEquals(driver.getCurrentUrl(), "https://www.n11.com/giris-yap");
        webForm.popUpControl();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".showPass"))).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("password"))).sendKeys(password);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("email"))).click();
        System.out.println(wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("password"))).getText());
        if(password.length() > 15) {
            AssertJUnit.assertEquals(wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div.errorMessage:nth-child(4) > div:nth-child(1)"))).getText(),"Girilen değer en fazla 15 karakter olmalıdır.");
        }
        else if (password.length() < 6 ){
            AssertJUnit.assertEquals(wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div.errorMessage:nth-child(4) > div:nth-child(1)"))).getText(),"Girilen değer en az 6 karakter olmalıdır.");
        }
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("password"))).clear();
        driver.manage().deleteAllCookies();

    }


    @Test(dataProvider = "wrongMails", testName = "Go to login page and test forgot password function with wrong mails")
    public void forgotPasswordWithWrongMailsTest(String mail){
        driver.get(Constants.N11_URL);
        driver.manage().window().maximize();
        Assert.assertEquals(driver.getCurrentUrl(), "https://www.n11.com/giris-yap");
        webForm.checkForgotPassword();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("forgottenUserEmail"))).sendKeys(mail);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("sendLinkForPasswordBtn"))).click();
        AssertJUnit.assertEquals(wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#forgotPasswordModalWrapper > div.popupContent > div > div > div > div > div"))).getText(),"Lütfen geçerli bir e-posta adresi girin.");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("forgottenUserEmail"))).clear();
        driver.manage().deleteAllCookies();

    }

    @Test( testName = "Go to login page and test forgot password function with empty input as mail address")
    public void forgotPasswordWithEmptyMailsTest(){
        driver.get(Constants.N11_URL);
        driver.manage().window().maximize();
        Assert.assertEquals(driver.getCurrentUrl(), "https://www.n11.com/giris-yap");
        webForm.checkForgotPassword();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("forgottenUserEmail"))).sendKeys(Constants.EMPTY_MAIL);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("sendLinkForPasswordBtn"))).click();
        AssertJUnit.assertEquals(wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div.errorText:nth-child(2)"))).getText(),"Lütfen e-posta adresinizi girin.");
        driver.manage().deleteAllCookies();

    }

    @Test(dataProvider = "trueMails", testName = "Go to login page and test forgot password function with true mails")
    public void forgotPasswordWithTrueMailsTest(String mail){
        driver.get(Constants.N11_URL);
        driver.manage().window().maximize();
        Assert.assertEquals(driver.getCurrentUrl(), "https://www.n11.com/giris-yap");
        webForm.checkForgotPassword();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("forgottenUserEmail"))).sendKeys(mail);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("sendLinkForPasswordBtn"))).click();
        AssertJUnit.assertEquals(wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".sendPassword > div:nth-child(1) > h3:nth-child(2)"))).getText(),"Şifre yenileme linki, e-posta adresinize gönderildi.");
        AssertJUnit.assertEquals(wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".sendPassword > div:nth-child(1) > div:nth-child(3)"))).getText(),"Lütfen e-posta kutunuzu kontrol edin.");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("a.closePopup"))).click();
        driver.manage().deleteAllCookies();

    }

    @DataProvider(name = "wrongMails")
    public Object[][] wrongMailData() {
        return new Object[][]{
                {"a@b."},
                {"abcdefghabcdefghabcdefghabcddefghabcdefghabc.com"},
                {"yunusemre"},
                {"@test.com"},
                {"şişş@mail.com"}
        };
    }

    @DataProvider(name = "trueMails")
    public Object[][] trueMailData() {
        return new Object[][]{
                {"test99999@abc.com"},
                {"abc@abc.com"},
                {"aa@asd.tr"},
                {"abcdefghabcdefghabcdefghabcddefghabcd@yunus.com"},
                {"yunusemre@emre.yunus"}
        };
    }

    @DataProvider(name = "wrongPasswords")
    public Object[][] wrongPasswordData() {
        return new Object[][]{
                {"123"},
                {"1"},
                {"abcde"},
                {"abcd1"},
                {"1a2b3"},
                {".q12+"},
                {"aşklsmdşalmsdşlamdşlamdşlaşldmaşlsmdaşlsmdşasmdşlamsdşlmaşld"},
                {"12312312312312312312312312313212312312313123123123123123123123"}
        };
    }


}
