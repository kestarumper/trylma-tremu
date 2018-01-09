package models;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class RoomTest {
    private Room room;
    private User mockUser = mock(User.class);
    private String roomName = "test";

    @Before
    public void setUp() throws Exception {
        room = new Room(roomName, mockUser, Room.Mode.PLAYERS2);
        when(mockUser.getName()).thenReturn("Stefan");
        when(mockUser.getCsrf()).thenReturn("jagsdghafdghagssdghasfdghafdhgaf165");
    }

    @After
    public void tearDown() throws Exception {
        room = null;
    }

    @Test
    public void joinRoom() {
        room.joinRoom(mockUser);
        assertEquals(1, room.getUsers().size());
        assertTrue(room.getUsers().containsValue(mockUser));
    }

    @Test
    public void getMaxUsers() {
        Room room3 = new Room(roomName, mockUser, Room.Mode.PLAYERS3);
        Room room4 = new Room(roomName, mockUser, Room.Mode.PLAYERS4);
        Room room6 = new Room(roomName, mockUser, Room.Mode.PLAYERS6);
        assertEquals(2, room.getMaxUsers());
        assertEquals(3, room3.getMaxUsers());
        assertEquals(4, room4.getMaxUsers());
        assertEquals(6, room6.getMaxUsers());
    }

    @Test
    public void leaveRoom() {
        room.joinRoom(mockUser);
        assertEquals(1, room.getUsers().size());
        room.leaveRoom(mockUser);
        assertFalse(room.getUsers().containsValue(mockUser));
    }
}