package BrowserTests;


import controllers.routes;
import org.junit.Before;
import org.junit.Test;
import play.Application;
import play.Logger;
import play.api.mvc.RequestHeader;
import play.mvc.Http;
import play.mvc.Result;
import play.test.Helpers;
import play.test.WithBrowser;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static junit.framework.TestCase.assertEquals;
import static org.mockito.Mockito.*;

public class IndexTest extends WithBrowser {

    @Before
    public void setUp() throws Exception {
        Logger.warn("@Before Generuje nowy Http.Context");

        Map<String, String> sessionMap = new HashMap<>();

        sessionMap.put("username", "ZalogowanyWczesniej");
        sessionMap.put("csrf", "b3cbabb0dbe767fdce51773a3e18ba45abeb5709-1515066120235-7022df3ee0fdd1105c24515a");

        Http.RequestBuilder requestBuilder = new Http.RequestBuilder();
        requestBuilder = requestBuilder.session(sessionMap);
        Http.Context context = new Http.Context(requestBuilder, null);
        Http.Context.current.set(context);
    }

    @Test
    public void loginInBrowserTest() {
        Logger.info("Sesja: {}", Http.Context.current().session());
        browser.goTo("/");
        assertEquals("Trylma Tremu", browser.$("#jumbotron").text());
        browser.$("#username").fill().with("BotTestujeLogowanie");
        browser.$("#submit").submit();
        assertEquals("Logged in as BotTestujeLogowanie", browser.$("#loggedInAs").text());
    }

    @Test
    public void loginInBrowserTest2() {
        browser.goTo("/");
        assertEquals("Trylma Tremu", browser.$("#jumbotron").text());
        browser.$("#username").fill().with("Bot2TestujeLogowanie");
        browser.$("#submit").submit();
        assertEquals("Logged in as Bot2TestujeLogowanie", browser.$("#loggedInAs").text());
    }

}
