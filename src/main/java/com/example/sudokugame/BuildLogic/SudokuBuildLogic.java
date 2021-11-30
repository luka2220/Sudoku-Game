package com.example.sudokugame.BuildLogic;

import com.example.sudokugame.computationalogic.GameLogic;
import com.example.sudokugame.persistence.LocalStorageImpl;
import com.example.sudokugame.problemdomain.IStorage;
import com.example.sudokugame.problemdomain.SudokuGame;
import com.example.sudokugame.userinterface.IUserInterfaceContract;
import com.example.sudokugame.userinterface.logic.ControlLogic;

import java.io.IOException;

public class SudokuBuildLogic {

    public static void build(IUserInterfaceContract.View userInterface) throws IOException {
        SudokuGame initialState;
        IStorage storage = new LocalStorageImpl();

        try {
            //will throw if no game data is found in local storage
            initialState = storage.getGameData();
        } catch (IOException e) {

            initialState = GameLogic.getNewGame();
            //this method below will also throw an IOException
            //if it cannot update the game data. At this point
            //the application is considered unrecoverable
            storage.updateGameData(initialState);
        }

        IUserInterfaceContract.EventListener uiLogic = new ControlLogic(storage, userInterface);
        userInterface.setListener(uiLogic);
        userInterface.updateBoard(initialState);
    }
}
