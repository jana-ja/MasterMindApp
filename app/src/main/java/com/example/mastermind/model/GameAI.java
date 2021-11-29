package com.example.mastermind.model;

import org.sat4j.core.Vec;
import org.sat4j.core.VecInt;
import org.sat4j.minisat.SolverFactory;
import org.sat4j.specs.ContradictionException;
import org.sat4j.specs.IProblem;
import org.sat4j.specs.ISolver;
import org.sat4j.specs.IVecInt;
import org.sat4j.specs.TimeoutException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class GameAI {

    Settings settings;
    final int MAXVAR = 1000000;
    final int NBCLAUSES = 500000;

    ISolver solver;
    ArrayList<PinColor> colors;
    Boolean[] colorInSolution;
    Vec<IVecInt> allClauses;
    PinColor[] lastGuess;

    public GameAI(Settings settings) {
        this.settings = settings;
        colors = new ArrayList<>(Arrays.asList(PinColor.values()));
        colorInSolution = new Boolean[colors.size()];
        allClauses = new Vec<>();

        for (int i = 0; i < colors.size(); i++) {
            colorInSolution[i] = null;
        }

        initWithRules();
        //TODO adapt to settings

    }

    private void initWithRules() {
        //TODO nochmal genau angucken und denken
        //empty pins not possible
//        if(!settings.isEmptyPins()) {
            colorInSolution[colorInSolution.length - 1] = false;
//            colorPossible[colorPossible.length - 1] = false;
            colors.remove(colors.size() - 1);
//        }

        //init clauses with basic game knowledge (have to be 4 distinct colors)
        if(!settings.isEmptyPins() && !settings.isDuplicatePins()){
            int[] satVars = getAllSatVars4Init();
            //das sind so viele die muss ich programmatisch erzeugen
            //binär zählen und wenn 4 einsen -> int array
            allClauses = countBinary(satVars, 4);

        }
    }

    /**
     * count binary and check if count of 1 equals @param ones
     * build knf -> where
     * @param satVars die variablen die zu klauseln verarbeitet werden sollen
     * @param ones anzahl der 1en zur erfüllung in einer var belegung sein sollen
     * @return alle klauseln in einem Vec, erzeugt mit den satVars für die anzahl ones an 1en (beachten vorgehen wegen knf)
     */
    private Vec<IVecInt> countBinary(int[] satVars, int ones) {
        Vec<IVecInt> sat;
        List<boolean[]> booleans = new ArrayList<>();
        // binär hochzählen mit size von satvars stellen, dabei immer schauen ob anzahl einsen = ones
        int digits = satVars.length;
        String binary = "";
        for (int i = 0; i < digits; i++) {
            binary = binary.concat("0");
        }
        //hochzählen
        String binaryEnd;
        for(int i = 0; i < Math.pow(2, digits); i++){
            binaryEnd = Integer.toBinaryString(i);

            if( binaryEnd.chars().filter(digit -> Character.valueOf((char) digit).equals('1')).count() == ones){
                // weil knf bauen: da wo richtige anzahl 1 ist nichts machen
            } else {
                // weil knf bauen: da wo nicht richtige anzhal 1en ist:
                // vars negiert als klasuel hinzufügen -> sorgt dafür dass falsche sachen nicht erfüllt sein dürfen
                // binary länge zu ganzen digits bringen damit man alles invertieren kann
                String newBinary = replaceEnd(binary, binaryEnd);

                boolean[] booleanArray = new boolean[digits]; //TODO wird angeblich alles = false initialisiert
                int arrayIndex = booleanArray.length - 1;
                for (int j = 0; j < newBinary.length(); j++) {
                    //vars negiert
                    booleanArray[j] = (!Character.valueOf(newBinary.charAt(j)).equals('1'));
                }
                booleans.add(booleanArray);
            }
        }
        sat = new Vec<>(booleans.size());
        // liste fertig: VecInt erzeugen mit size von liste und mit satVars und liste die richtigen dinge auf sat pushen
        booleans.forEach(booleanArray -> sat.push(new VecInt(boolean2intArray(satVars, booleanArray))));

            return sat;
    }

    private String replaceEnd(String string, String end) {
        if(end.length() > string.length())
            return string;
        String newString = "";
        //ending bounds is exclusive
        newString = newString.concat(string.substring(0, string.length() - end.length())).concat(end);

        return newString;
    }

    public PinColor[] firstStep() {

        lastGuess = guessNext();
        return lastGuess;
    }

    /**
     * update das wissen darüber welche colors enthalten sein müssen, nicht enthalten sein können oder noch unbestimmt sind mit hilfe der sat klauseln
     * (mit checkIfPossible)
     * dann nächsten guess bestimmen lassen
     * (mit nextGuess)
     * @param ergebnis ergebnis feedback vom guess aus der runde vorher
     * @return nächster guess
     */
    public PinColor[] nextStep(Ergebnis ergebnis) {
        //TODO wenn 0 korrekte sind direkt die possible colors setzen und die formal hinzufügen? .. ne kA müsste ja eig gehen.

        // get current SAT formula(s)
        Vec<IVecInt> sat4Colors = getSat4Colors(ergebnis.getCorrectColors());

        // update sat formula
        Vec<IVecInt> newAllClauses = new Vec<>(allClauses.size() + sat4Colors.size());
        allClauses.copyTo(newAllClauses);
        sat4Colors.copyTo(newAllClauses);

        // idea: take current SAT formula and test for every pincolor var if it causes a contradiction
        // if contradiction: no longer consider this var, if not this var is still possible
        // sat vars are represented with integers, negative integer -> negated var
        // 0 not allowed so user index of colors array +1
        for(int i = 0; i < colorInSolution.length; i++){
            if(colorInSolution[i] == null) {
                if (!checkIfPossible(newAllClauses, i + 1)) {
                    colorInSolution[i] = false;
                    //TODO dann kann ich klasuel mit (i+1) hinzufügen und nicht passende klasuseln löschen

                } else
                //TODO auch noch andersrum testen -> wenn -a nicht geht, muss a wahr sein
                if (!checkIfPossible(newAllClauses, -(i + 1))) {
                    colorInSolution[i] = true;
                    //TODO dann kann ich klasuel mit -(i+1) hinzufügen und nicht passende klasuseln löschen
                }
            }
        }

        allClauses = newAllClauses;
        lastGuess = guessNext();

        return lastGuess;
    }

    /**
     * nächsten guess bestimmen basierend auf dem bisherigen wissen darüber welche farben noch möglich sind (colorInSolution)
     * (dieses wissen wird in nextstep vorher über die sat clauseln (allClauses) gewonnen.)
     * @return nächster guess
     */
    private PinColor[] guessNext(){
        PinColor[] guess = new PinColor[4];

        // die die ich weiß auf jeden fall nehmen:
        List<Integer> indices = new ArrayList<>();
        for (int i = 0; i < colorInSolution.length; i++) {
            if(colorInSolution[i] != null)
                if(colorInSolution[i])
                    indices.add(i);
        }
        if(indices.size() > guess.length) {
            //TODO error
            System.err.println("more correct colors found than should be possible");
        }
        for (int i = 0; i < indices.size(); i++) {
            guess[i] = colors.get(indices.get(i));
        }

        //aus unbestimmten random wählen
        //sonst hab ich schon lösung
        if(indices.size() < guess.length) {
            // alle möglichen unbestimmten in liste packen, shufflen
            ArrayList<Integer> remainingUnknown = new ArrayList<>();
            //TODO check if there are at least 4 possible colors in colorsPossible
            for (int i = 0; i < colorInSolution.length; i++) {
                // unknown, might be in solution
                if (colorInSolution[i] == null)
                    remainingUnknown.add(i);
            }
            Collections.shuffle(remainingUnknown);
            // wenn ich nich mehr genug unknown vars übrig hab ist was schief gelaufen
            if(remainingUnknown.size() < guess.length - indices.size()){
                //TODO error
                System.err.println("not enough possible colors left to make a guess, fatal");
            }
            // verbleibende auffüllen
            for (int i = indices.size(); i < guess.length; i++) {
                guess[i] = colors.get(remainingUnknown.get(i));
            }
        }

        //TODO guess gegen die klauseln vertoßen
        //zB wenn ich weiß rot, gelb ... 1 richtig, aber alle sind noch unknown
        //dann könnte AI die nochmal zsm wählenobwohl es keinen sinn macht
        // vllt sowas heuristisches wie: 5 mal versuchen was ohne widerspruchzu finden, sonst einfach nehmen
        // oder alle kombis der remaining unknown durchgehen bis man was ohne widerspruch findet

//        return new PinColor[]{PinColor.BLUE, PinColor.PINK, PinColor.GREY, PinColor.WHITE};
//        return new PinColor[]{PinColor.RED, PinColor.ORANGE, PinColor.YELLOW, PinColor.GREEN};
        return guess;
    }

    /**
     * bekommt die klasuseln und eine var belegeung und überprüft ob die var mit den klauselnim widerspruch steht
     * @param satClauses die klasueln mit denen getestet werden soll
     * @param i die var mit der getestet werden soll
     * @return ob es halt möglich ist ne
     */
    private boolean checkIfPossible(Vec<IVecInt> satClauses, int i) {

        // add i clause to satClauses
        Vec<IVecInt> testClauses = new Vec<>(satClauses.size() + 1);
        satClauses.copyTo(testClauses);
        testClauses.push(new VecInt(new int[]{i}));
        solver = SolverFactory.newDefault();

        // now test for contradiction
        // bei newVar muss größter verwendeter wert für sat var rein
        // solver geht davon aus dass alle vars bis zu diesem wert auch vorkommen
        solver.newVar(colorInSolution.length - 1);//);
        solver.setExpectedNumberOfClauses(testClauses.size());

        // Feed the solver using Dimacs format
        try {
            solver.addAllClauses(testClauses);
        } catch (ContradictionException e) {
            return false;
        }

        // we are done. Working now on the IProblem interface
        IProblem problem = solver;
        try {
            if (problem.isSatisfiable()) {
                return true;
            } else {
                return false;
            }
        } catch (TimeoutException e) {
            return false;
        }

    }

    /**
     * erzeugt passend zum feedback neue klauseln für das system
     * @param correctColors feedback zu letzem guess
     * @return klauseln erstellt für letzten guess mit feedback dazu
     */
    private Vec<IVecInt> getSat4Colors(int correctColors) {
        // in KNF!
        //get sat vars for last guess colors
        int[] satVars = getSatVars4Guess();
        Vec<IVecInt> sat4Colors;
        switch (correctColors){
            case 0:
                //all false
                sat4Colors = new Vec<>(4);
                sat4Colors.push(new VecInt(new int[]{- satVars[0]}));
                sat4Colors.push(new VecInt(new int[]{- satVars[1]}));
                sat4Colors.push(new VecInt(new int[]{- satVars[2]}));
                sat4Colors.push(new VecInt(new int[]{- satVars[3]}));
                //TODO könnte die direkt alle false setzen?
                break;
            case 4:
                //alle richtig, lösung gefunden!
                sat4Colors = new Vec<>(4);
                sat4Colors.push(new VecInt(new int[]{satVars[0]}));
                sat4Colors.push(new VecInt(new int[]{satVars[1]}));
                sat4Colors.push(new VecInt(new int[]{satVars[2]}));
                sat4Colors.push(new VecInt(new int[]{satVars[3]}));
                break;
            default: //1,2,3
                sat4Colors = countBinary(satVars, correctColors);
                break;
        }
        return sat4Colors;
    }

    /**
     * bekommmt satvars und booleans und ordnet den satvars true/false werte zu
     * @param vars sat vars mit denen klausel gebildet werden sol
     * @param booleans var belegung ob true oder false
     * @return klausel mit den vars in richtiger true/false belegung
     */
    private int[] boolean2intArray(int[] vars, boolean[] booleans) {
        int[] newVars = new int[vars.length];
        for (int i = 0; i < vars.length; i++) {
            newVars[i] = booleans[i] ? vars[i] : - vars[i];
        }
        return newVars;
    }

    /**
     * sat vars für guess aus vorheriger runde (indizes der farben aus dem guess jeweils +1)
     * AI hat dafür feedback bekommen und braucht deswegen die sat vars um neue klasueln ins system einzufügen
     * @return sat vars die den farben des letztens guesses entsprechen
     */
    private int[] getSatVars4Guess() {
        //from last guess
        int[] satVars = new int[4];
        for (int i = 0; i < satVars.length; i++) {
            //0 not allowed
            satVars[i] = colors.indexOf(lastGuess[i]) + 1;
        }
        return satVars;
    }

    /**
     * je nach spiel modus 8 oder 9 sat vars für die sat bib
     * um die spiel logik am anfang in formeln zu übersetzen
     * @return sat vars die den im aktuellen spiel verwendeten farben entsprechen
     */
    private int[] getAllSatVars4Init(){
        int[] satVars = new int[colors.size()];
        for (int i = 0; i < satVars.length; i++) {
            //0 not allowed
            satVars[i] = i + 1;
        }
        return satVars;
    }

}
