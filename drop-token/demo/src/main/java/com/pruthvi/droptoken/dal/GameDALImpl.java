package com.pruthvi.droptoken.dal;

import com.pruthvi.droptoken.exception.RequestConflictException;
import com.pruthvi.droptoken.exception.RequestGoneException;
import com.pruthvi.droptoken.exception.ResourceNotFoundException;
import com.pruthvi.droptoken.model.*;
import com.pruthvi.droptoken.utilities.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;


@Service
public class GameDALImpl implements GameDAL {

    private static final String MOVE = "MOVE";
    private final String IN_PROGRESS = "IN PROGRESS";
    private final String DONE = "DONE";
    private final String DRAW = "DRAW";
    private final String QUIT = "QUIT";
    private final String WON = "WON";


    private final Logger LOG = LoggerFactory.getLogger(getClass());

    private Game game;

    private CreateGameResponse createGameResponse;
    @Autowired
    private PostMoveRequest postMoveRequest;
    @Autowired
    private PostMoveResponse postMoveResponse;
    @Autowired
    private GetGamesResponse getGamesResponse;
    @Autowired
    private GameStatusResponse gameStatusResponse;
    @Autowired
    private GetMovesResponse getMovesResponse;
    @Autowired
    private GetMoveResponse getMoveResponse;

    private Utils utils = new Utils();

    Hashtable<String, Game> dataBase = new Hashtable<>();


//    @Override
//    public GetGamesResponse printGames() {
//        System.out.println(dataBase);
//        System.out.println("Checking live reload");
//        Set st = dataBase.entrySet();
//        Iterator it = st.iterator();
//        while(it.hasNext()){
//            Map.Entry entry = (Map.Entry) it.next();
//            game = (Game)entry.getValue();
//            System.out.println("---> "+game.toString());
//            System.out.println("---> "+Arrays.deepToString(game.getBoard()));
//
//        }
//        return null;
//    }


/*
Code implementation for all the classes declared in GameDAL interface.
    All the implementations refer to the contracts defined in controller.
*/


    /**
     * @return GetGamesResponse containing list of gameIDs.
     *
     */
    @Override
    public GetGamesResponse getGames() {
        LOG.info("-$-$-$--Inside gameDALImpl getGames method--$-$-$--");
        getGamesResponse = new GetGamesResponse();
        Set<String> gameIdKeys = dataBase.keySet();
        List<String> gameIds = new ArrayList<>();
        for (String gameId : gameIdKeys) {
            gameIds.add(gameId);
        }
        getGamesResponse.setGameIds(gameIds);
        return getGamesResponse;
    }

    /**
     * @param createGameRequest - with list of players, column and row. Auto generated a UUID.
     * @return createGameResponse with the gameID.
     */
    @Override
    public CreateGameResponse createGame(CreateGameRequest createGameRequest) {
        LOG.info("-$-$-$--Inside gameDALImpl createGame method--$-$-$--");

        game = new Game();
        createGameResponse = new CreateGameResponse();
        String uuid = UUID.randomUUID().toString();
        game.setGameID(uuid);
        game.setPlayer1(createGameRequest.getPlayers().get(0));
        game.setPlayer2(createGameRequest.getPlayers().get(1));
        game.setColumns(createGameRequest.getColumns());
        game.setRows(createGameRequest.getRows());
        game.setCurrentPlayerTurn(game.getPlayer1());
        game.setMoves(new LinkedList<>());
        game.setBoard(new int[game.getColumns()][game.getRows()]);
        game.setWinner("");
        game.setStatus(IN_PROGRESS);
        dataBase.put(uuid, game);
        createGameResponse.setGameId(game.getGameID());
        LOG.info(game.toString());
        return createGameResponse;
    }

    /**
     * @param gameId
     * @return gameStatusResponse with player infomation, state of the game and winner of the game.
     * @throws ResourceNotFoundException when requested gameID isn't found in the database.
     */
    @Override
    public GameStatusResponse gameStatus(String gameId) throws ResourceNotFoundException {
        if (!dataBase.containsKey(gameId)) {
            throw new ResourceNotFoundException(gameId, "Game with respective ID is not found.");
        }
        gameStatusResponse = new GameStatusResponse();
        if (dataBase.containsKey(gameId)) {
            game = dataBase.get(gameId);
            LOG.info("GAME STATUS RESPONSE : "+game.toString());
            List<String> players = new ArrayList<>();
            players.add(game.getPlayer1());
            players.add(game.getPlayer2());
            if (game.getStatus().equals(IN_PROGRESS)||game.getStatus().equals(DRAW)) {
                System.out.println("Setting two parameters only");
                gameStatusResponse.setPlayers(players);
                gameStatusResponse.setState(game.getStatus());
            } else {
                gameStatusResponse.setPlayers(players);
                gameStatusResponse.setState(game.getStatus());
                gameStatusResponse.setWinner(game.getWinner());
            }
        }
        return gameStatusResponse;
    }

    /**
     * @param gameId
     * @param startMove
     * @param untilMove
     * @return getMovesResponse object which contains a list of GetMoveResponse.
     * @throws ResourceNotFoundException when requested gameID is not present.
     */
    @Override
    public GetMovesResponse getMoves(String gameId, Integer startMove, Integer untilMove) throws ResourceNotFoundException {
        game = dataBase.get(gameId);

        if (!dataBase.containsKey(gameId)) {
            throw new ResourceNotFoundException(gameId, "Game with respective ID is not found.");
        }

        LOG.info("-$-$-$--Inside gameDALImpl getMoves method--$-$-$--");
        LOG.info("-$-$-$--" + gameId + " " + startMove + " " + untilMove + "--$-$-$--");

        boolean quitGame = false;
        List<Integer> movesList = game.getMoves();
        List<GetMoveResponse> opMove = new ArrayList<>();
        String player = game.getPlayer1();

        if (!dataBase.containsKey(gameId)) {
            throw new ResourceNotFoundException(gameId, "Game with respective ID is not found.");
        }

        if (startMove != null && untilMove == null) {
            if(startMove < 0) {
                throw new ResourceNotFoundException(gameId, "Start position isn't correct");
            }
        }
        if (startMove == null && untilMove != null) {
            if(untilMove > movesList.size()) {
                throw new ResourceNotFoundException(gameId, "End position isn't correct");
            }
        }
        if(startMove != null && untilMove != null){
            if(untilMove <= startMove){
                throw new ResourceNotFoundException(gameId, "Start & End positions aren't correct");
            }
        }


        int startSweep = 0, untilSweep = movesList.size();

        if (startMove != null) startSweep = startMove - 1;
        if (untilMove != null) untilSweep = untilMove - 1;



        //LOG.info("-$-$-$-- Start and until indices : " + startSweep + " " + untilSweep + "--$-$-$--");

        for (int i = startSweep; i < untilSweep; i++) {
            getMoveResponse = new GetMoveResponse();
            if (i == movesList.size() - 1) {
                 if (game.getStatus().equals(DONE) || game.getStatus().equals(DRAW) || game.getStatus().equals(IN_PROGRESS)) {
                    getMoveResponse.setType(game.getStatus());
                    getMoveResponse.setPlayer(player);
                    getMoveResponse.setColumn(movesList.get(i));
                    opMove.add(getMoveResponse);
                    player = i % 2 == 0 ? game.getPlayer2() : game.getPlayer1();
                }
                if(game.getStatus().equals(QUIT)){
                       quitGame = true;
                       getMoveResponse.setType(MOVE);
                       getMoveResponse.setPlayer(player);
                       getMoveResponse.setColumn(movesList.get(i));
                       opMove.add(getMoveResponse);
                       player = i % 2 == 0 ? game.getPlayer2() : game.getPlayer1();
                }
                break;
            }

                getMoveResponse.setType(MOVE);
                getMoveResponse.setPlayer(player);
                getMoveResponse.setColumn(movesList.get(i));
                opMove.add(getMoveResponse);
                player = i % 2 == 0 ? game.getPlayer2() : game.getPlayer1();

        }
        if (quitGame) {
            getMoveResponse = new GetMoveResponse();
            getMoveResponse.setPlayer(player);
            getMoveResponse.setType(QUIT);
            getMoveResponse.setColumn(null);
            opMove.add(getMoveResponse);
        }
        getMovesResponse.setMoves(opMove);
        return getMovesResponse;
    }

    /**
     * @param gameId
     * @param playerId
     * @param column
     * @return PostMoveResponse with move number.
     * @throws ResourceNotFoundException when gameID, currentPlayer aren't correct
     * @throws RequestConflictException
     * checks game status and declares win/draw/quit.
     */
    @Override
    public PostMoveResponse postMove(String gameId, String playerId, Integer column) throws ResourceNotFoundException, RequestConflictException {

        if (gameId.isEmpty() || playerId.isEmpty() || column.equals(null)) {
            throw new ResourceNotFoundException(gameId, "One/More of the input resources is not present.");
        }

        if (!dataBase.containsKey(gameId)) {
            throw new ResourceNotFoundException(gameId, "Game Id not found in the database.");
        }

        game = dataBase.get(gameId);

//        LOG.info("-$-$-$--Inside gameDALImpl postMove method--$-$-$--");
//        LOG.info("-$-$-$--"+gameId+" "+playerId+" "+column+"--$-$-$--");

        if (!game.getStatus().equals(IN_PROGRESS)) {
            throw new RequestConflictException(gameId, "Player is trying to post in a terminated game");
        }
        if(!(game.getPlayer1().equals(playerId) || game.getPlayer2().equals(playerId))){
            //LOG.info(game.getPlayer1() + " -- "+game.getPlayer2()+" -- "+playerId);
            throw new ResourceNotFoundException(gameId, "Player Id is not associated with this game.");
        }
        if (dataBase.containsKey(gameId)) {
            if (!game.getCurrentPlayerTurn().equals(playerId)) {
                throw new RequestConflictException("Player posting in wrong turn.");
            }
        }


        int tokenToColumn = column;
        int rows = game.getRows() - 1;

        if (column > rows || column < 0) {
            throw new RequestConflictException(gameId, "Player is trying to drop token in a column that is not in board range.");
        }


        for (int i = rows; i >= 0; i--) {

            //LOG.info("-$-$-$--Inside for loop  : " + game.getBoard()[i][tokenToColumn] + " " + game
            // .getCurrentPlayerTurn() + "--$-$-$--");

            if (game.getBoard()[i][tokenToColumn] == 0 && game.getCurrentPlayerTurn().equals(game.getPlayer1())) {
              //  LOG.info("-$-$-$--Inside for loop first IF : " + playerId + " " + game.getPlayer1() + "--$-$-$--");
                game.getBoard()[i][tokenToColumn] = 1;
                game.setCurrentPlayerTurn(game.getPlayer2());
                break;
            }
            if (game.getBoard()[i][tokenToColumn] == 0 && game.getCurrentPlayerTurn().equals(game.getPlayer2())) {
                //LOG.info("-$-$-$--Inside for loop second IF : " + playerId + " " + game.getPlayer2() + "--$-$-$--");
                game.getBoard()[i][tokenToColumn] = 2;
                game.setCurrentPlayerTurn(game.getPlayer1());
                break;
            }
            if (game.getBoard()[i][tokenToColumn] > 0 && i == 0) {
                throw new RequestConflictException(gameId, "Player trying to access a filled position.");
            }
        }

        List<Integer> movesList = game.getMoves();
        movesList.add(column);
        int moveNumber = movesList.size();
        postMoveResponse = new PostMoveResponse();
        String gameStatus = utils.checkGameStatus(game.getBoard());
        if (gameStatus.equals(DRAW)){
            postMoveResponse.setMoveLink(game.getGameID() + "/moves/" + moveNumber);
            game.setStatus(DRAW);
            return postMoveResponse;
        }
        if (gameStatus.equals(WON)) {
            postMoveResponse.setMoveLink(game.getGameID() + "/moves/" + moveNumber);
            game.setStatus(DONE);
            String player = game.getCurrentPlayerTurn().equals(game.getPlayer1()) ? game.getPlayer2() : game.getPlayer1();
            game.setWinner(player);
            return postMoveResponse;
        }
        postMoveResponse.setMoveLink(game.getGameID() + "/moves/" + moveNumber);
        return postMoveResponse;
    }

    /**
     * @param gameId
     * @param move_number
     * @return a list of GetMoveResponse if successful response.
     * @throws ResourceNotFoundException when gameID requested is not present in database.
     */
    @Override
    public GetMoveResponse getMove(String gameId, Integer move_number) throws ResourceNotFoundException {

        game = dataBase.get(gameId);
        List<Integer> movesList = game.getMoves();
        int movesListLength = movesList.size();
        LOG.info("-$-$-$--Inside gameDALImpl getMove method--$-$-$--");
        LOG.info("-$-$-$--" + gameId + " " + move_number + " " + movesListLength + "--$-$-$--");
        //LOG.info("-$-$-$--" + Arrays.toString(movesList.toArray()) + "--$-$-$--");
        LOG.info("-$-$-$--" + movesList + "--$-$-$--");

        if (!dataBase.containsKey(gameId)) {
            throw new ResourceNotFoundException(gameId, "Game not found.");
        }

        if (move_number < movesList.size() || move_number > movesList.size()) {
            throw new ResourceNotFoundException(gameId, "Move not found/Move requested is out of boundary.");
        }

        String op = null;
        String player = !(move_number % 2 == 0) ? game.getPlayer1() : game.getPlayer2();

        getMoveResponse = new GetMoveResponse();

        if (move_number > movesListLength || move_number <= 0) {
            getMoveResponse.setColumn(movesList.get(move_number - 1));
            getMoveResponse.setPlayer("");
            getMoveResponse.setType("NO MOVE FOUND");
        } else {
            getMoveResponse.setColumn(movesList.get(move_number - 1));
            getMoveResponse.setPlayer(player);
            getMoveResponse.setType("MOVE");
        }

        return getMoveResponse;
    }

    /**
     * @param gameId
     * @param playerId
     * @return a response code for the request and gameStatus will be set to QUIT.
     * @throws ResourceNotFoundException when particular gameID or playerID is not in database.
     * @throws RequestGoneException when player is trying to QUIT for a terminated game.
     */
    @Override
    public String deleteGame(String gameId, String playerId) throws ResourceNotFoundException, RequestGoneException {
        game = dataBase.get(gameId);

        LOG.info("-$-$-$--Inside gameDALImpl deleteGame method--$-$-$--");
        LOG.info("-$-$-$--" + gameId + " " + playerId + "--$-$-$--");
        if (!dataBase.containsKey(gameId) || !game.getCurrentPlayerTurn().equals(playerId)) {
            throw new ResourceNotFoundException(gameId, "Game not found or player is not part of this game.");
        }
        if (game.getStatus().equals(DONE)) {
            throw new RequestGoneException("Game is in DONE state.");
        }
        String droppedPlayer = game.getCurrentPlayerTurn();
        game.setStatus(QUIT);
        String winner = droppedPlayer.equals(game.getPlayer1()) ? game.getPlayer2() : game.getPlayer1();
        game.setWinner(winner);

        LOG.info(game.toString());

        return "Successfully aborted and winner is : " + game.getWinner();

    }

}


