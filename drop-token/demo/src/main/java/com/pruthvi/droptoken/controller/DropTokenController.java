package com.pruthvi.droptoken.controller;

import com.pruthvi.droptoken.dal.GameDAL;
import com.pruthvi.droptoken.exception.MalformedRequestException;
import com.pruthvi.droptoken.exception.RequestConflictException;
import com.pruthvi.droptoken.exception.RequestGoneException;
import com.pruthvi.droptoken.exception.ResourceNotFoundException;
import com.pruthvi.droptoken.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;


@RestController
@RequestMapping(value = "/drop_token")
public class DropTokenController {

    private final Logger LOG = LoggerFactory.getLogger(getClass());

    @Autowired
    private GameStatusResponse gameStatusResponse;
    @Autowired
    private GameDAL gameDAL;

//    @Autowired
//    private CreateGameResponse createGameResponse;
//    @Autowired
//    private PostMoveRequest postMoveRequest;
//    @Autowired
//    private PostMoveResponse postMoveResponse;
//    @Autowired
//    private GetGamesResponse getGamesResponse;
//    @Autowired
//    private GetMovesResponse getMovesResponse;


//    Crete Game Request and Create Game Response
//    Puts players details and board size to database and returns game id.

//    @RequestMapping(value = "/printAll", method = RequestMethod.GET)
//    public GetGamesResponse printGamesRequest() {
//        LOG.info("->->->--Inside controller printGamesRequest call--<-<-<-");
//        return gameDAL.printGames();
//    }


/*
GetGamesRequest
    base URI request returns with a list of gameIDs in the database.
*/

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity getGamesResponse() {
        LOG.info("->->->--Inside controller getGamesRequest call--<-<-<-");
        return ResponseEntity.ok(gameDAL.getGames());
    }


    /*
    CreateGameRequest
        method takes players list, columns and rows as body from request and return with a CreateGameResponse(gameID) if successful response.
    */
    @RequestMapping(method = RequestMethod.POST)
    public CreateGameResponse createGameRequest(@RequestBody CreateGameRequest createGameRequest) throws MalformedRequestException {
        if (createGameRequest.getPlayers().size() != 2 || createGameRequest.getColumns() != 4 || createGameRequest.getRows() != 4) {
            throw new MalformedRequestException("One/More of the input fields are not acceptable.");
        }
        LOG.info("->->->--Inside controller createGameRequest call--<-<-<-");
        return gameDAL.createGame(createGameRequest);
    }

    /*
    GameStatusRequest
        method takes in gameID and returns with a GameStatusResponse(players list, state, winner(optional)) if successful response.
    */
    @RequestMapping(value = "/{gameId}", method = RequestMethod.GET)
    public GameStatusResponse gameStatusRequest(@PathVariable String gameId) throws ResourceNotFoundException {
        gameStatusResponse = gameDAL.gameStatus(gameId);
        return gameStatusResponse;
    }

    /*
    GetMovesRequest
        method takes in gameID(optional start & until parameters) and returns GetMovesResponse(list of moves) if successful response.
    */
    @RequestMapping(value = "/{gameId}/moves", method = RequestMethod.GET)
    public GetMovesResponse getMovesRequest(@PathVariable String gameId, @RequestParam(value = "start", required = false) Integer startMove, @RequestParam(value = "until", required = false) Integer untilMove) throws ResourceNotFoundException {
        LOG.info("->->->--Inside controller getMovesResponse call--<-<-<-");
        LOG.info("->->->-- " + gameId + " " + startMove + " " + untilMove + " --<-<-<-");
        return gameDAL.getMoves(gameId, startMove, untilMove);
    }

    /*

    PostMoveRequest
        method takes in gameID & playerID and returns PostMoveResponse(gameID and move number) if successful response.
    */
    @RequestMapping(value = "/{gameId}/{playerId}", method = RequestMethod.POST)
    public PostMoveResponse postMoveRequest(@PathVariable Map<String, String> pathVars, @RequestBody PostMoveRequest postMoveRequest) throws ResourceNotFoundException, RequestConflictException {
        LOG.info("->->->--Inside controller postMoveRequest call--<-<-<-");
        String gameId = pathVars.get("gameId");
        String playerId = pathVars.get("playerId");
        Integer column = postMoveRequest.getColumn();
        LOG.info("->->->--" + gameId + " " + playerId + " " + column + "--<-<-<-");
        return gameDAL.postMove(gameId, playerId, column);
    }

    /*
    GetMoveRequest
        method takes in gameID &  move number as input and returns with GetMoveResponse(with type, player, column) if successful response.
    */
    @RequestMapping(value = "/{gameId}/moves/{move_number}")
    public GetMoveResponse getMoveRequest(@PathVariable String gameId, @PathVariable Integer move_number) throws ResourceNotFoundException {
        return gameDAL.getMove(gameId, move_number);
    }

    /*
    DeleteGameRequest
        Current player to drop from the game. Takes in gameID & playerID as input and returns with respective status code.
    */
    @RequestMapping(value = "/{gameId}/{playerId}", method = RequestMethod.DELETE)
    public String deleteGameRequest(@PathVariable Map<String, String> pathVars) throws ResourceNotFoundException, RequestGoneException {
        String gameId = pathVars.get("gameId");
        String playerId = pathVars.get("playerId");
        return gameDAL.deleteGame(gameId, playerId);
    }


}
