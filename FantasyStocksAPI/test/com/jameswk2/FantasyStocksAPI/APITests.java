package com.jameswk2.FantasyStocksAPI;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by ddsnowboard on 4/17/17.
 */
public class APITests {
    public static final String USERNAME = "testerMan";
    public static final String PASSWORD = "test123";

    private static FantasyStocksAPI api;

    @BeforeAll
    public static void setUp() {
        api = FantasyStocksAPI.getInstance();
    }

    @Test
    public void getUser() {
        api.login(USERNAME, PASSWORD);
        FullUser user = api.getUser();
        assertEquals(user.getUsername(), USERNAME);
    }

    @Test
    public void readFloors() {
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
        assertTrue(Arrays.stream(users).filter(u -> u.equals(myUser)).findFirst().isPresent());
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
        Arrays.stream(randomPlayer.getStocks()).forEach(s -> System.out.println(s));
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
                    .filter(p -> p.getUser().equals(myUser))
                    .findFirst()
                    .isPresent());
            assertEquals(newPlayer.getUser(), myUser);
            assertEquals(newPlayer.getFloor(), randomFloor);
        }
        catch (RuntimeException e) {
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
        }
        catch (RuntimeException e) {
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
        assertTrue(Arrays.stream(User.getUsers()).filter(u -> u.getUsername().equals(USERNAME)).findFirst().isPresent());
    }
}
