package radio;

import com.browserup.bup.BrowserUpProxy;
import com.browserup.bup.proxy.CaptureType;
import com.browserup.harreader.model.HarEntry;
import com.codeborne.selenide.WebDriverRunner;
import core.BaseSelenideTest;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.SevenPage;

import java.util.List;
import java.util.stream.Collectors;

import static io.restassured.RestAssured.given;

public class NowSongTest extends BaseSelenideTest {
    @Test(description = "Получение звучащей сейчас песни на Радио 7")
    public void checkRadio7() {
        Response response = given()
                .when()
                .contentType(ContentType.JSON)
                .get("https://radio7.ru/on_air/onair.json.js?_=1676196151530")
                .then().log().all()
                .extract().response();
        JsonPath jsonPath = response.jsonPath();
        List<String> artist = jsonPath.getList("playlist.artist.name");
        List<String> songNames = jsonPath.getList("playlist.song.name");
        Assert.assertTrue(artist.size() > 0 && songNames.size() > 0 && artist.size() == songNames.size(),
                "Песни не получены, или какие то данные утеряны");
    }

    @Test(description = "Получение звучащей сейчас песни на Радио Мегаполис")
    public void checkNetwork() {
        SevenPage megaPage = new SevenPage();
        List<HarEntry> responses = megaPage.clickPlaylist();
        HarEntry trackResponse = responses.stream().filter(x-> x.getRequest().getUrl()
                .contains("cur_playing")).collect(Collectors.toList()).get(0);

        String text = trackResponse.getResponse().getContent().getText();
        Assert.assertTrue(text.split(" ").length > 1);
    }
}
