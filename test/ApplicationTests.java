import models.GameSession;
import models.TrylmaApp;
import models.User;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.omg.CosNaming.NamingContextPackage.NotFound;
import play.Logger;
import play.mvc.Http;
import play.mvc.Result;
import play.test.Helpers;
import play.test.WithApplication;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static play.mvc.Http.Status.FORBIDDEN;
import static play.mvc.Http.Status.NOT_FOUND;
import static play.mvc.Http.Status.OK;
import static play.test.Helpers.route;

public class ApplicationTests extends WithApplication {
    private TrylmaApp fakeTrylmaApp = TrylmaApp.getInstance();
    private Map fakeGameSessionMap = mock(Map.class);
    private Map fakeUsers = mock(Map.class);
    private User fakeUser = prepareFakeUser();
    private GameSession fakeGameSession = mock(GameSession.class);

    {
        when(fakeUsers.get("adrian")).thenReturn(fakeUser);
    }

    private User prepareFakeUser() {
        User result = mock(User.class);
        when(result.getName()).thenReturn("adrian");
        when(result.getCsrf()).thenReturn("test");
        return result;
    }

    @Before
    public void setUp() throws Exception {
        Logger.info("SetUp");
        fakeTrylmaApp.getGameSessions().clear();
        fakeTrylmaApp.getUsers().clear();
        fakeTrylmaApp.getUsers().put("adrian", fakeUser);
        fakeTrylmaApp.getGameSessions().put("adrian", fakeGameSession);
    }

    @Test
    public void forbideAccessRoomWhenNotLogged() {

        Http.RequestBuilder requestBuilder = Helpers.fakeRequest()
                .method("GET")
                .uri("/room/adrian");
        Result result = route(app, requestBuilder);
        assertEquals(FORBIDDEN, result.status());
    }

    @Test
    public void allowAccessRoomWhenLogged() {

        Http.RequestBuilder requestBuilder = Helpers.fakeRequest()
                .session("username", "adrian")
                .method("GET")
                .uri("/room/adrian");
        Result result = route(app, requestBuilder);
        assertEquals(OK, result.status());
    }

    @Test
    public void roomNotExist() {
        Http.RequestBuilder requestBuilder = Helpers.fakeRequest()
                .session("username", "adrian")
                .session("csrf", "test")
                .method("GET")
                .uri("/room/czupakabra");
        Result result = route(app, requestBuilder);
        assertEquals(NOT_FOUND, result.status());
    }

    @After
    public void tearDown() throws Exception {
        Logger.info("Tear Down");
        fakeTrylmaApp.getUsers().clear();
        fakeTrylmaApp.getGameSessions().clear();
    }
}
