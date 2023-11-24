package assignment;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.time.Duration;
import java.util.List;

public class AssignmentDynamicTable {
    public static void main(String[] args) throws InterruptedException, FileNotFoundException {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
        System.setProperty("webdriver.chrome.driver","C:/Users/ASUS/Documents/chromedriver-win64/chromedriver.exe");
        WebDriver driver = new ChromeDriver(options);

        //  Step 1 : getting url
        driver.get("https://testpages.herokuapp.com/styled/tag/dynamic-table.html");
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));


        // Step 2: Click on Table Data button

        WebElement tabledataButton= driver.findElement(By.xpath("//summary[text()='Table Data']"));
        tabledataButton.click();

        // Step 3: Insert the provided JSON data into the input text box

        List<WebElement> inputTextBox=driver.findElements(By.xpath("//textarea[@id='jsondata']"));
        inputTextBox.get(0).clear();
        inputTextBox.get(0).sendKeys("[{\"name\" : \"Bob\", \"age\" : 20, \"gender\": \"male\"}, {\"name\": \"George\", \"age\" : 42, \"gender\": \"male\"}, {\"name\":\n" +
                "\"Sara\", \"age\" : 42, \"gender\": \"female\"}, {\"name\": \"Conor\", \"age\" : 40, \"gender\": \"male\"}, {\"name\":\n" +
                "\"Jennifer\", \"age\" : 42, \"gender\": \"female\"}]");
        WebElement refreshButton = driver.findElement(By.id("refreshtable"));
        refreshButton.click();

        // Step 4: Wait for the table to be populated
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

        // Step 5: Assert the data in the table matches the provided JSON data
        List<WebElement> tableRows = driver.findElements(By.xpath("//table[@id='table-data']//tbody/tr"));

        for (int index = 0; index < tableRows.size(); index++) {
            List<WebElement> cells = tableRows.get(index).findElements(By.tagName("td"));
            assert cells.get(0).getText().equals("Bob");
            assert cells.get(1).getText().equals("20");
            assert cells.get(2).getText().equals("male");
        }

        // Close the browser
        driver.close();
    }
}

