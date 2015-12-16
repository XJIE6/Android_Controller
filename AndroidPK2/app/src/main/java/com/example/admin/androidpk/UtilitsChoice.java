package com.example.admin.androidpk;

import java.io.DataInput;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Admin on 15.12.2015.
 */
public class UtilitsChoice {
    DataInput choiceSettingsFile;
    int idLayout;

    public UtilitsChoice(int choiceLayout, int choiceSettings, int idLayout) throws IOException {
        String fileSettins = "choice" + choiceLayout + "." + choiceSettings;
        this.choiceSettingsFile = new DataInputStream(new FileInputStream(fileSettins));
        this.idLayout = idLayout;
    }

    private void sendToServer(ArrayList<Integer> forSend) {
        synchronized (MainActivity.mail) {
            for (int j: forSend) {
                MainActivity.mail.add(j);
            }
            notify();
        }
    }

    private static ArrayList<Integer> intToArray(int a) {
        ArrayList<Integer> single = new ArrayList<>();
        single.add(a);
        return single;
    }

    public ArrayList<ArrayList<Integer>> readCommandsFromFileAndSendServer() throws IOException {
        ArrayList<ArrayList<Integer>> setsForViews = new ArrayList<>();
        int countOfViews;
        int countAllCommandSet;
        countOfViews = choiceSettingsFile.readInt();
        countAllCommandSet = choiceSettingsFile.readInt();
        sendToServer(intToArray(countAllCommandSet));
        int numberCurCommand = 0;
        for (int i = 0; i < countOfViews; i++) {
            int countOfVarietySets = choiceSettingsFile.readInt();
            ArrayList<Integer> forSend = new ArrayList<>();
            ArrayList<Integer> setsCurView = new ArrayList<>();
            for (int j = 0; j < countOfVarietySets; j++) {
                setsCurView.add(numberCurCommand++);
                int countOfCommandsSet = choiceSettingsFile.readInt();
                forSend.add(countOfCommandsSet);
                for (int k = 0; k < countOfCommandsSet; k++) {
                    int idCommand = choiceSettingsFile.readInt();
                    forSend.add(idCommand);
                }
            }
            sendToServer(forSend);
            setsForViews.add(setsCurView);
        }
        return setsForViews;
    }
}
