package jana.ja.mastermind.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

abstract public class Game {

    protected int currenRound;
    protected PinColor[] solution;

    protected Settings settings;

    public Game(Settings settings){
        this.settings = settings;
        this.currenRound = 0;
    }


    public boolean checkSettingConformity(PinColor[] pinColors) {
        if(!settings.isEmptyPins()){
            //check if there are empty pins
            if(Arrays.asList(pinColors).contains(PinColor.EMPTY))
                return false;
        }
        //check if there are duplicates
        if(!settings.isDuplicatePins()){
            Set<PinColor> lump = new HashSet<>();
            for (PinColor c : pinColors)
            {
                if (lump.contains(c))
                    return false;
                lump.add(c);
            }
        }
        return true;
    }

    public int getCurrenRound() {
        return currenRound;
    }

    public PinColor[] getSolution() {
        return solution;
    }


    abstract public PinRow nextRound(PinColor[] pinColors);

    int checkColors(PinColor[] pinColors) {
        int dingsi = 0;
        //boardcellrow kopieren
        List<PinColor> editRow = new ArrayList<>(Arrays.asList(pinColors));

        //alle solution colors abgleichen mit der aktuellen row
        for (PinColor pinColor : solution) {
            for (PinColor color : editRow) {
                //wenn die farbe gleich ist lösch ich aus der aktuellen row damit die nciht doppelt gezählt werden falls in der solution was doppelt ist
                if(pinColor == color){
                    editRow.remove(color);
                    dingsi++;
                    break;
                }
            }
        }
        return dingsi;
    }

    int checkPlaces(PinColor[] pinColors) {
        int dingsi = 0;
        for (int i = 0; i < pinColors.length; i++) {
            if(pinColors[i] == solution[i])
                dingsi++;
        }
        return dingsi;
    }



}
