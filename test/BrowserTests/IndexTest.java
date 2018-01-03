package BrowserTests;


import org.junit.Before;
import org.junit.Test;
import play.api.mvc.RequestHeader;
import play.mvc.Http;
import play.test.WithBrowser;

import java.util.Collections;
import java.util.Map;

import static junit.framework.TestCase.assertEquals;
import static org.mockito.Mockito.*;

public class IndexTest extends WithBrowser {

    @Before
    public void setUp() throws Exception {
        Http.Request mockRequest = mock(Http.Request.class);
        when(mockRequest.remoteAddress()).thenReturn("127.0.0.1");
        when(mockRequest.header("User-Agent")).thenReturn(java.util.Optional.of("mocked user-agent"));

        // ... and so on. Mock precisely what you need, then add it to your mocked Context

        Http.Context mockContext = mock(Http.Context.class);
        when(mockContext.request()).thenReturn(mockRequest);
    }

    @Test
    public void loginInBrowserTest() {
        browser.goTo("/");
        assertEquals("Trylma Tremu", browser.$("#pagetitle").text());
        browser.$("#username").click().write("BotTestujeLogowanie");
        browser.$("#submit").submit();
        assertEquals("Logged in as BotTestujeLogowanie", browser.$("#loggedInAs").text());
    }

}
