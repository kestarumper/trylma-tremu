import org.junit.Before;
import org.junit.Test;
import play.Application;
import play.api.test.FakeRequest;
import play.mvc.Http;
import play.test.WithApplication;
import play.twirl.api.Content;

import java.util.Collections;
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
        Http.Request mockRequest = mock(Http.Request.class);
        when(mockRequest.remoteAddress()).thenReturn("127.0.0.1");
        when(mockRequest.header("User-Agent")).thenReturn(java.util.Optional.of("mocked user-agent"));

        // ... and so on. Mock precisely what you need, then add it to your mocked Context

        Http.Context mockContext = mock(Http.Context.class);
        when(mockContext.request()).thenReturn(mockRequest);
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
