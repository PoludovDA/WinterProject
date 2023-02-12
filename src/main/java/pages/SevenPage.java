package pages;

import com.browserup.bup.BrowserUpProxy;
import com.browserup.bup.proxy.CaptureType;
import com.browserup.harreader.model.HarEntry;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.WebDriverRunner;

import java.util.List;

import static com.codeborne.selenide.Selenide.$x;
import static com.codeborne.selenide.Selenide.open;

public class SevenPage {
    private final static SelenideElement playList = $x("//a[text()='Плейлист']");

    public SevenPage() {
        open("https://www.radio-megapolis.ru/");
    }

    public List<HarEntry> clickPlaylist() {
        BrowserUpProxy bmp = WebDriverRunner.getSelenideProxy().getProxy();
        bmp.setHarCaptureTypes(CaptureType.getAllContentCaptureTypes());
        //Мониторим запросы и ответы
        bmp.enableHarCaptureTypes(CaptureType.REQUEST_CONTENT, CaptureType.RESPONSE_CONTENT);
        bmp.newHar("pofig");

        playList.click();
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        List<HarEntry> requests = bmp.getHar().getLog().getEntries();
        return requests;
    }
}
