package com.jameswk2.FantasyStocksAPI;

import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashMap;

import static com.jameswk2.FantasyStocksAPI.FantasyStocksAPI.gson;
import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertFalse;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class APITests {
    private static final int USER_ID = 32;
    private static final String USERNAME = "testerMan";
    private static final String PASSWORD = "test123";

    private FantasyStocksAPI api;
    private MockNetworkBackend backend;

    private Floor[] floors;

    @Before
    public void setUp() {
        backend = new MockNetworkBackend();
        api = new FantasyStocksAPI(backend);
        final int NUM_OF_FLOORS = 3;
        floors = new Floor[NUM_OF_FLOORS];
        for(int i = 0; i < NUM_OF_FLOORS; i++) {
            FullFloor next = new FullFloor();
            floors[i] = next;
            next.setId(i);
            next.setName(String.format("%dth floor", i));
            // Finish making some fake floors to make readFloors() work
        }
    }

    @Test
    public void loginTest() {
        final String SESSION_ID = "aslkdfjiooasidfj4365936";
        JsonObject postData = new JsonObject();
        postData.add("username", new JsonPrimitive(USERNAME));
        postData.add("password", new JsonPrimitive(PASSWORD));
        JsonObject responseObject = new JsonObject();
        responseObject.add("sessionId", new JsonPrimitive(SESSION_ID));
        backend.expectPost("auth/getKey/", new HashMap<>(), postData, responseObject);

        api.login(USERNAME, PASSWORD);

        backend.validateExpectations();
    }

    @Test
    public void getUser() {
        login();
        User user = api.getUser();
        // I don't understand where this is getting its username. So that's good.
        assertEquals(user.getUsername(), USERNAME);
    }

    @Test
    public void readFloors() {
        // I'm going to need to do some serious upgrades to my login method to make this work. *sigh*
        Assert.fail();
        api.login(USERNAME, PASSWORD);

        Floor[] floors = Arrays.stream(api.getUser().getPlayers())
                .map(x -> x.getFloor()).toArray(i -> new Floor[i]);
        for (FullPlayer p : Arrays.stream(FullPlayer.getPlayers()).filter(p -> p.getUser().equals(api.getUser())).toArray(i -> new FullPlayer[i])) {
            assertTrue(Arrays.stream(floors).filter(f -> p.getFloor().equals(f)).findFirst().isPresent());
        }
    }

    @Test
    public void readUsers() {
        api.login(USERNAME, PASSWORD);

        User[] users = User.getUsers();
        User myUser = api.getUser();
        assertTrue(Arrays.stream(users).anyMatch(u -> u.equals(myUser)));
        assertFalse(Arrays.stream(users).filter(u -> !u.equals(myUser)).findFirst().get().getUsername().equals(myUser.getUsername()));
    }

    @Test
    public void readStocksFromPlayer() {
        api.login(USERNAME, PASSWORD);

        User myUser = api.getUser();
        Floor randomFloor = Arrays.stream(Floor.getFloors()).findAny().get();
        Player randomPlayer = Arrays.stream(Player.getPlayers())
                .filter(p -> p.getFloor().equals(randomFloor))
                .findAny()
                .get();
        Arrays.stream(randomPlayer.getStocks()).forEach(System.out::println);
    }

    @Test
    public void joinFloor() {
        api.login(USERNAME, PASSWORD);
        User myUser = api.getUser();
        Floor randomFloor = Arrays.stream(Floor.getFloors()).findAny().get();
        try {
            Player newPlayer = Player.create(myUser, randomFloor);
            System.out.println(newPlayer);
            assertTrue(Arrays.stream(Player.getPlayers())
                    .filter(p -> p.getFloor().equals(randomFloor))
                    .anyMatch(p -> p.getUser().equals(myUser)));
            assertEquals(newPlayer.getUser(), myUser);
            assertEquals(newPlayer.getFloor(), randomFloor);
        } catch (RuntimeException e) {
            System.out.println("You probably need to restart the server, if the error is that the model already exists");
            throw e;
        }
    }

    @Test
    public void createTrade() {
        api.login(USERNAME, PASSWORD);
        Floor randomFloor = Floor.getFloors()[0];

        Player recipient = Arrays.stream(Player.getPlayers())
                .filter(p -> p.getFloor().equals(randomFloor))
                .findFirst()
                .get();

        Player sender;
        try {
            sender = Player.create(api.getUser(), randomFloor);
        } catch (RuntimeException e) {
            sender = Arrays.stream(Player.getPlayers())
                    .filter(p -> p.getUser().equals(api.getUser()))
                    .findFirst()
                    .get();
        }

        Stock[] senderStocks = sender.getStocks();
        Stock[] recipientStocks = recipient.getStocks();

        Trade trade = Trade.create(sender, recipient, senderStocks, recipientStocks, randomFloor);
        assertEquals(trade.getFloor(), randomFloor);

        assertArrayEquals(trade.getRecipientStocks(), recipientStocks);
        assertArrayEquals(trade.getSenderStocks(), senderStocks);
        assertFalse(Arrays.equals(trade.getSenderStocks(), recipientStocks));
        assertFalse(Arrays.equals(trade.getRecipientStocks(), senderStocks));
    }

    @Test
    public void createUser() {
        final String USERNAME = "testerMcTest";
        final String PASSWORD = "rosebud";

        User ret = api.register(USERNAME, PASSWORD);
        assertEquals(ret.getUsername(), USERNAME);
        assertNotNull(api.getSessionId());
        assertTrue(Arrays.stream(User.getUsers()).anyMatch(u -> u.getUsername().equals(USERNAME)));
    }

    @Test
    public void registerToken() {
        final String THIS_IS_A_FAKE_ID = "salkdfjldksajfsaldkjfoiuoiuoiuwoqeiruqwoeirua,smdnc";
        FantasyStocksAPI.getInstance().registerFirebaseId(THIS_IS_A_FAKE_ID);
        System.out.println("GO CHECK THAT THIS RANDOM ID GOT ADDED");
    }

    private void login() {
        final String SESSION_ID = "aslkdfjiooasidfj4365936";
        JsonObject postData = new JsonObject();
        postData.add("username", new JsonPrimitive(USERNAME));
        postData.add("password", new JsonPrimitive(PASSWORD));
        JsonObject responseObject = new JsonObject();
        responseObject.add("sessionId", new JsonPrimitive(SESSION_ID));
        FullUser user = new FullUser();
        user.setId(USER_ID);
        user.setUsername(USERNAME);
        responseObject.add("user", toJsonObject(user));
        backend.expectPost("auth/getKey/", new HashMap<>(), postData, responseObject).setRepeat(true);



        api.login(USERNAME, PASSWORD);
    }

    private JsonObject toJsonObject(Object o) {
        return gson.fromJson(gson.toJson(o), JsonObject.class);
    }

}

