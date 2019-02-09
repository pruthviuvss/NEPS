package com.pruthvi.droptoken;

import com.pruthvi.droptoken.model.*;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class DropTokenApplicationTests {

    @LocalServerPort
    int randomServerPort;
    List<Integer> moves = new ArrayList<>();


    //Perfect game with player1 as winner.
    Game game1 = new Game("gameID1","player1","player2",4,4,"player1",new ArrayList<Integer>(Arrays.asList(0,1,0,1,0,
            1)),new int[][]{{1,2,0,0},{1,2,0,0},{1,2,0,0},{1,2,0,0}},"WIN","player1");
    Game game2 = new Game("gameID2","player1","player2",4,4,"player1",new ArrayList<Integer>(Arrays.asList(0,1,0,1,0,
            1)),new int[][]{{0,0,0,0},{1,2,0,0},{1,2,0,0},{1,2,0,0}},"IN PROGRESS",null);


    @Test
    public void contextLoads() {
    }

    @Test
    public void testGetGamesResponse() throws URISyntaxException {

        RestTemplate restTemplate = new RestTemplate();

        final String baseUrl = "http://localhost:" + randomServerPort + "/drop_token";
        URI uri = new URI(baseUrl);

        ResponseEntity<String> result = restTemplate.getForEntity(uri, String.class);

        Assert.assertEquals(200, result.getStatusCodeValue());
        Assert.assertEquals(true, result.getBody().contains("gameIds"));

    }

    @Test
    public void testCreateGameRequest() throws URISyntaxException {

        RestTemplate restTemplate = new RestTemplate();

        final String baseUrl = "http://localhost:" + randomServerPort + "/drop_token";
        URI uri = new URI(baseUrl);

        CreateGameRequest gameRequest1 = new CreateGameRequest();
        List<String> players1 = new ArrayList<>();
        players1.add("player1");
        players1.add("player2");
        gameRequest1.setPlayers(players1);
        gameRequest1.setColumns(4);
        gameRequest1.setRows(4);

        CreateGameRequest gameRequest2 = new CreateGameRequest();
        List<String> players2 = new ArrayList<>();
        players2.add("player1");
        gameRequest2.setPlayers(players2);
        gameRequest2.setColumns(4);
        gameRequest2.setRows(4);

        CreateGameRequest gameRequest3 = new CreateGameRequest();
        List<String> players3 = new ArrayList<>();
        players3.add("player1");
        players3.add("player2");
        gameRequest3.setPlayers(players3);
        gameRequest3.setColumns(4);
        gameRequest3.setRows(3);

        CreateGameRequest gameRequest4 = new CreateGameRequest();
        List<String> players4 = new ArrayList<>();
        players4.add("player1");
        players4.add("player2");
        gameRequest4.setPlayers(players4);
        gameRequest4.setColumns(null);
        gameRequest4.setRows(4);


        HttpEntity<CreateGameRequest> request1 = new HttpEntity<>(gameRequest1);
        ResponseEntity<CreateGameResponse> response = restTemplate.postForEntity(uri, request1, CreateGameResponse.class);
        Assert.assertEquals(200, response.getStatusCodeValue());
        Assert.assertEquals(true, response.getBody().getClass().equals(CreateGameResponse.class));


        HttpEntity<CreateGameRequest> request2 = new HttpEntity<>(gameRequest2);
        try {
            restTemplate.postForEntity(uri, request2, CreateGameResponse.class);
            Assert.fail();
        } catch (HttpClientErrorException ex) {
            Assert.assertEquals(400, ex.getRawStatusCode());
            Assert.assertEquals(true,ex.getResponseBodyAsString().contains("One/More of the input fields are not acceptable."));
        }


        HttpEntity<CreateGameRequest> request3 = new HttpEntity<>(gameRequest3);
        try {
            restTemplate.postForEntity(uri, request3, CreateGameResponse.class);
            Assert.fail();
        } catch (HttpClientErrorException ex) {
            Assert.assertEquals(400, ex.getRawStatusCode());
            Assert.assertEquals(true,ex.getResponseBodyAsString().contains("One/More of the input fields are not acceptable."));
        }

        HttpEntity<CreateGameRequest> request4 = new HttpEntity<>(gameRequest4);
        try {
            restTemplate.postForEntity(uri, request4, CreateGameResponse.class);
            Assert.fail();
        } catch (HttpClientErrorException ex) {
            Assert.assertEquals(400, ex.getRawStatusCode());
            Assert.assertEquals(true,ex.getResponseBodyAsString().contains("Please check the request for all the possible errors."));
        }

    }

    @Test
    public void testPostMoveRequest() throws URISyntaxException {

        RestTemplate restTemplate = new RestTemplate();

        final String baseUrl = "http://localhost:" + randomServerPort + "/drop_token/gameID2/player2";
        URI uri = new URI(baseUrl);

        PostMoveRequest postMoveRequest1 = new PostMoveRequest();
        postMoveRequest1.setColumn(0);


        HttpEntity<PostMoveRequest> request1 = new HttpEntity<>(postMoveRequest1);
        try {
            restTemplate.postForEntity(uri, postMoveRequest1, PostMoveResponse.class);
            Assert.fail();
        } catch (HttpClientErrorException ex) {
            Assert.assertEquals(404, ex.getRawStatusCode());
            Assert.assertEquals(true,ex.getResponseBodyAsString().contains("Player posting in wrong turn."));
        }


    }



}

