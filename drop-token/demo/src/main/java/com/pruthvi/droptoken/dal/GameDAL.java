package com.pruthvi.droptoken.dal;

import com.pruthvi.droptoken.exception.MalformedRequestException;
import com.pruthvi.droptoken.exception.RequestConflictException;
import com.pruthvi.droptoken.exception.RequestGoneException;
import com.pruthvi.droptoken.exception.ResourceNotFoundException;
import com.pruthvi.droptoken.model.*;
import org.springframework.stereotype.Repository;

@Repository
public interface GameDAL {

//    GetGamesResponse printGames();

    CreateGameResponse createGame(CreateGameRequest createGameRequest) throws MalformedRequestException;

    GetGamesResponse getGames();

    GameStatusResponse gameStatus(String gameId) throws ResourceNotFoundException;

    PostMoveResponse postMove(String gameId, String playerId, Integer column) throws ResourceNotFoundException, RequestConflictException;

    GetMovesResponse getMoves(String gameId, Integer startMove, Integer untilMove) throws ResourceNotFoundException;

    GetMoveResponse getMove(String gameId, Integer move_number) throws ResourceNotFoundException;

    String deleteGame(String gameId, String playerId) throws ResourceNotFoundException, RequestGoneException;
}
