package BrowserTests;


import org.junit.Before;
import org.junit.Test;
import play.Logger;
import play.api.mvc.RequestHeader;
import play.mvc.Http;
import play.test.WithBrowser;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static junit.framework.TestCase.assertEquals;
import static org.mockito.Mockito.*;

public class IndexTest extends WithBrowser {

    @Before
    public void setUp() throws Exception {
//        Http.Request mockRequest = mock(Http.Request.class);
//        when(mockRequest.remoteAddress()).thenReturn("127.0.0.1");
//
//        Http.Context mockContext = mock(Http.Context.class);
//        when(mockContext.request()).thenReturn(mockRequest);
//
//        Http.Session mockSession;
        Logger.warn("@Before Generuje nowy Http.Context");

        Map<String, String> sessionMap = new HashMap<>();

        sessionMap.put("username", "ZalogowanyWczesniej");
        sessionMap.put("csrf", "b3cbabb0dbe767fdce51773a3e18ba45abeb5709-1515066120235-7022df3ee0fdd1105c24515a");

        Http.RequestBuilder requestBuilder = new Http.RequestBuilder();
        requestBuilder.session(sessionMap);
        Http.Context context = new Http.Context(requestBuilder, null);
        Http.Context.current.set(context);
    }

    @Test
    public void loginInBrowserTest() {
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
