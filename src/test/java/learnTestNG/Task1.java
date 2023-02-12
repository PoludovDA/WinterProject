package learnTestNG;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class Task1 {

    @Test(description = "Дата провайдер", dataProvider = "wordsData")
    public void checkMoreTwoWords(String input, boolean expected) {
        Assert.assertEquals(input.split(" ").length > 2, expected);
    }

    @Test(description = "Отлов исключений",
            expectedExceptions = ArithmeticException.class,
            expectedExceptionsMessageRegExp = "/ by zero")
    public void delZero() {
        int a = 0;
        Assert.assertEquals(20 / a, 0);
    }

    @Test(description = "Неверный тип входящих данных", dataProvider = "symbolsData")
    public void wrongArgs(int input) {
        Assert.assertTrue(input > 0);
    }

    @DataProvider
    public Object[][] wordsData() {
        return new Object[][] {
                {"Я гей", false},
                {"Я больше никогда не потревожу", true},
                {"Люблю твой сосок", true}
        };
    }

    @DataProvider
    public Object[][] symbolsData() {
        return new Object[][] {
                {'a'},
                {'b'},
                {"69"}
        };
    }
}
