package com.jameswk2.FantasyStocksAPI;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.Date;
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

    private final int NUM_OF_FLOORS = 3;
    private final int NUM_OF_STOCKS = 3;

    @Before
    public void setUp() {
        backend = new MockNetworkBackend();
        api = new FantasyStocksAPI(backend);
    }

    @After
    public void tearDown() {
        backend.validateExpectations();
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

        Stock[] stocks = createFakeStocks(NUM_OF_STOCKS);
        floors = new Floor[NUM_OF_FLOORS];
        Player[] players = new Player[NUM_OF_FLOORS];
        ((FullUser) api.getUser()).setPlayers(players);
        for (int i = 0; i < floors.length; i++) {
            FullFloor next = new FullFloor();
            floors[i] = next;
            next.setId(i);
            next.setName(String.format("%dth floor", i));
            FullPlayer usersPlayer = new FullPlayer();
            usersPlayer.setFloor(next);
            usersPlayer.setStocks(stocks.clone());
            players[i] = usersPlayer;
            next.setOwner(api.getUser());
            usersPlayer.setUser(api.getUser());
        }
    }

    private JsonObject toJsonObject(Object o) {
        return gson.fromJson(gson.toJson(o), JsonObject.class);
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
    }

    @Test
    public void getUser() {
        login();
        User user = api.getUser();
        assertEquals(user.getUsername(), USERNAME);
    }

    @Test
    public void readFloors() {
        // Does this work? Hell if I know
        login();

        Floor[] floors = Arrays.stream(api.getUser().getPlayers())
                .map(Player::getFloor).toArray(i -> new Floor[i]);
        Arrays.stream(FullPlayer.getPlayers()).filter(p -> p.getUser().equals(api.getUser())).forEach(p -> {
            assertTrue(Arrays.stream(floors).anyMatch(f -> p.getFloor().equals(f)));
        });

        assertArrayEquals(floors, this.floors);
    }

    @Test
    public void readUsers() {
        // Make a list of users with random ids, give it to the API under the /users/ endpoint, make sure it comes back
        final int NUM_USERS = 3;
        User[] users = new User[NUM_USERS];
        for (int i = 0; i < NUM_USERS; i++) {
            FullUser u = new FullUser();
            u.setId(i);
            users[i] = u;
        }

        JsonArray jsonObj = gson.fromJson(gson.toJson(users), JsonArray.class);
        backend.expectGet("user/view/", new HashMap<>(), jsonObj);
        User[] retUsers = User.getUsers();
        assertArrayEquals(retUsers, users);
    }

    @Test
    public void readStocksFromPlayer() {
        login();
        Stock[] stocks = api.getUser().getPlayers()[0].getStocks();
        assertArrayEquals(stocks, createFakeStocks(NUM_OF_STOCKS));
    }

    @Test
    public void joinFloor() {
        final int ID = 311;
        login();
        FullFloor fakeFloor = new FullFloor();
        fakeFloor.setId(floors[floors.length - 1].getId() + 1);
        fakeFloor.setName("I'm a fake floor!");
        JsonObject jsonObj = new JsonObject();
        jsonObj.addProperty("user", api.getUser().getId());
        jsonObj.addProperty("floor", fakeFloor.getId());
        FullPlayer expectedPlayer = new FullPlayer();
        expectedPlayer.setId(ID);
        expectedPlayer.setStocks(new Stock[0]);
        expectedPlayer.setFloor(fakeFloor);
        expectedPlayer.setUser(api.getUser());
        JsonObject jsonRet = gson.fromJson(gson.toJson(expectedPlayer), JsonObject.class);
        backend.expectPost("player/create/", new HashMap<>(), jsonObj, jsonRet);
        Player newPlayer = Player.create(api.getUser(), fakeFloor);
        assertEquals(expectedPlayer, newPlayer);
    }

    @Test
    public void createTrade() {
        Assert.fail();
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
        Assert.fail();
        final String USERNAME = "testerMcTest";
        final String PASSWORD = "rosebud";

        User ret = api.register(USERNAME, PASSWORD);
        assertEquals(ret.getUsername(), USERNAME);
        assertNotNull(api.getSessionId());
        assertTrue(Arrays.stream(User.getUsers()).anyMatch(u -> u.getUsername().equals(USERNAME)));
    }

    @Test
    public void registerToken() {
        Assert.fail();
        final String THIS_IS_A_FAKE_ID = "salkdfjldksajfsaldkjfoiuoiuoiuwoqeiruqwoeirua,smdnc";
        FantasyStocksAPI.getInstance().registerFirebaseId(THIS_IS_A_FAKE_ID);
        System.out.println("GO CHECK THAT THIS RANDOM ID GOT ADDED");
    }

    private Stock[] createFakeStocks(int numStocks) {
        Stock[] stocks = new Stock[numStocks];
        for (int i = 0; i < numStocks; i++) {
            final int currentIdx = i;
            stocks[i] = new Stock() {
                @Override
                public int getId() {
                    return currentIdx;
                }

                @Override
                public String getCompanyName() {
                    return String.format("Company Number %d", currentIdx);
                }

                @Override
                public String getSymbol() {
                    return String.format("C%d", currentIdx);
                }

                @Override
                public Date getLastUpdated() {
                    return null;
                }

                @Override
                public double getPrice() {
                    return 0;
                }

                @Override
                public double getChange() {
                    return 0;
                }

                @Override
                public StockSuggestion[] getStockSuggestions() {
                    return new StockSuggestion[0];
                }

                public boolean equals(Object other) {
                    return other instanceof Stock && this.getId() == ((Stock) other).getId();
                }
            };
        }
        return stocks;
    }
}

