package newAccounts;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class AccountsRegister {
    public static void main(String[] args) {
        ChromeOptions options = new ChromeOptions();

// Disabling autocomplete, password saving, and address storage
        Map<String, Object> prefs = new HashMap<>();
        prefs.put("credentials_enable_service", false);          // Disable login remembering
        prefs.put("profile.password_manager_enabled", false);    // Disable password manager
        prefs.put("autofill.profile_enabled", false);            // Disable address autocomplete
        prefs.put("autofill.address_enabled", false);            // Disable address saving

        options.setExperimentalOption("prefs", prefs);


        WebDriver driver = new ChromeDriver(options);
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));

        int numberofAccounts = 3;

        for (int i = 0; i < numberofAccounts; i++) {

            driver.get("https://mystore-testlab.coderslab.pl/index.php");
            //Sign in
            WebElement signIn = driver.findElement(By.xpath("//div[@class='user-info']"));
            signIn.click();

            //Create account
            WebElement accountCreate = driver.findElement(By.xpath("//div[@class='no-account']"));
            accountCreate.click();

            //Social title randomize
            Random gender = new Random();
            boolean isMale = gender.nextBoolean();

            if (isMale) {
                WebElement socialMr = driver.findElement(By.id("field-id_gender-1"));
                socialMr.click();
            } else {
                WebElement socialMrs = driver.findElement(By.id("field-id_gender-2"));
                socialMrs.click();
            }
            //First name randomize
            String firstName = getRandomName(isMale);
            WebElement firstNameInput = driver.findElement(By.id("field-firstname"));
            firstNameInput.sendKeys(firstName);

            //Last name randomize
            String lastName = getRandomLastName(isMale);
            WebElement lastNameInput = driver.findElement(By.id("field-lastname"));
            lastNameInput.sendKeys(lastName);

            //Random email
            long milisecs = System.currentTimeMillis();
            WebElement emailInput = driver.findElement(By.id("field-email"));
            emailInput.sendKeys("test" + milisecs + "@test.com");

            //Random Password
            WebElement passwordInput = driver.findElement(By.id("field-password"));
            passwordInput.sendKeys(generateRandomPassword());

            //Random birthday
            WebElement birthDayInput = driver.findElement(By.id("field-birthday"));
            birthDayInput.sendKeys(generateRandomBirthday());

            //Checkbox click
            WebElement checkbox1Select = driver.findElement(By.xpath("//input[@name='optin']"));
            checkbox1Select.click();
            WebElement checkbox2Select = driver.findElement(By.xpath("//input[@name='customer_privacy']"));
            checkbox2Select.click();
            WebElement checkbox3Select = driver.findElement(By.xpath("//input[@name='newsletter']"));
            checkbox3Select.click();
            WebElement checkbox4Select = driver.findElement(By.xpath("//input[@name='psgdpr']"));
            checkbox4Select.click();

            //register
            WebElement saveClick = driver.findElement(By.xpath("//button[@data-link-action='save-customer']"));

            saveClick.click();
            //if email is already used
            List<WebElement> alertElements = driver.findElements(By.xpath("//li[contains(@class, 'alert')]"));
            if (!alertElements.isEmpty() && alertElements.get(0).isDisplayed()) {
                emailInput = driver.findElement(By.xpath("//input[@name='email']"));
                emailInput.clear();
                emailInput.sendKeys("test" + milisecs + "@test.com");
                passwordInput = driver.findElement(By.xpath("//input[@name='password']"));
                passwordInput.clear();
                passwordInput.sendKeys("test" + milisecs + "@test.com");
                saveClick = driver.findElement(By.xpath("//button[@data-link-action='save-customer']"));
                saveClick.click();
            }
            //Wait for elements on the page to load
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));

            //My account
            WebElement myAccountSelect = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[@title='View my customer account']")));
            myAccountSelect.click();

            //Addresses
            WebElement myAddressSelect = driver.findElement(By.id("address-link"));
            myAddressSelect.click();

            //Street input
            WebElement streetInput = driver.findElement(By.id("field-address1"));
            streetInput.sendKeys(generateRandomStreet());

            //City input
            WebElement cityInput = driver.findElement(By.id("field-city"));
            cityInput.sendKeys(generateRandomCity());

            //ZipCode input
            WebElement zipCodeInput = driver.findElement(By.id("field-postcode"));
            zipCodeInput.sendKeys(generateRandomZipCode());

            //Save
            WebElement saveAddressClick = driver.findElement(By.xpath("//button[contains(@class, 'btn-primary')]"));
            saveAddressClick.click();

            //Log out
            WebElement logoutSelect = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[contains(@class, 'logout hidden-sm-down')]")));
            logoutSelect.click();
        }

    }

    public static String getRandomName(boolean isMale) {
        String[] maleNames = {"Bartosz", "Piotr", "Kamil", "Szymon", "Krzysztof", "Zdzisław", "Andrzej", "Stanisław"};
        String[] femaleNames = {"Anna", "Katarzyna", "Paulina", "Magda", "Jagoda", "Patrycja", "Agnieszka", "Aleksandra"};

        Random firstNameRandom = new Random();
        if (isMale) {
            return maleNames[firstNameRandom.nextInt(maleNames.length)];
        } else {
            return femaleNames[firstNameRandom.nextInt(femaleNames.length)];
        }
    }

    public static String getRandomLastName(boolean isMale) {
        String[] maleLastNames = {"Nowak", "Kowalski", "Wiśniewski", "Wójcik", "Kamiński", "Lewandowski", "Zieliński", "Woźniak"};
        String[] femaleLastNames = {"Nowak", "Kowalska", "Wiśniewska", "Wójcik", "Kamińska", "Lewandowska", "Zielińska", "Woźniak"};
        Random lastNameRandom = new Random();
        if (isMale) {
            return maleLastNames[lastNameRandom.nextInt(maleLastNames.length)];
        } else {
            return femaleLastNames[lastNameRandom.nextInt(femaleLastNames.length)];
        }
    }

    public static String generateRandomPassword() {
        return "Test" + new Random().nextInt(10000, 99999) + "!";
    }

    public static String generateRandomBirthday() {
        Random randBirthday = new Random();
        int randomDay = randBirthday.nextInt(1, 32);
        int randomMonth = randBirthday.nextInt(1, 13);
        int randomYear = randBirthday.nextInt(1960, 2026);
        if (randomMonth == 2) randomDay = randBirthday.nextInt(1, 29);
        return String.format("%02d/%02d/%04d", randomMonth, randomDay, randomYear);
    }

    public static String generateRandomStreet() {
        String[] street = {"Aleja Bułgarska", "Aleja Łużycka", "Barska", "Biała", "Bonin", "Bzowa", "Ciasna", "Czwartaków", "Dworkowa"};
        Random randStreet = new Random();
        int randomStreetNumber = randStreet.nextInt(1, 21);
        return street[randStreet.nextInt(street.length)] + " " + randomStreetNumber;
    }

    public static String generateRandomCity() {
        String[] city = {"Poznań", "Piła", "Bydgoszcz", "Zielona Góra", "Warszawa", "Kraków", "Wrocław", "Gdańsk"};
        Random randCity = new Random();
        return city[randCity.nextInt(city.length)];
    }
    public static String generateRandomZipCode() {
        Random randZipCode = new Random();
        int randomZipCodeNumber1 = randZipCode.nextInt(0, 9);
        int randomZipCodeNumber2 = randZipCode.nextInt(100, 999);
        return String.format("%02d-%03d", randomZipCodeNumber1, randomZipCodeNumber2);


    }
}

