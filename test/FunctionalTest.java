import org.junit.Before;
import org.junit.Test;
import play.Application;
import play.Logger;
import play.api.test.FakeRequest;
import play.mvc.Http;
import play.test.WithApplication;
import play.twirl.api.Content;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * A functional test starts a Play application for every test.
 *
 * https://www.playframework.com/documentation/latest/JavaFunctionalTest
 */
public class FunctionalTest extends WithApplication {

    @Before
    public void setUp() throws Exception {
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
    public void renderTemplate() {
        // If you are calling out to Assets, then you must instantiate an application
        // because it makes use of assets metadata that is configured from
        // the application.

        Content html = views.html.index.render(null);
        assertThat("text/html").isEqualTo(html.contentType());
        assertThat(html.body()).contains("Trylma Tremu");
    }
}
