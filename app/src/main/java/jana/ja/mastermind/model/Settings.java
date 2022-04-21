package jana.ja.mastermind.model;

public class Settings {
    private boolean duplicatePins;
    private boolean emptyPins;
    private int numberRounds;

    public Settings() {
    }

    public boolean isDuplicatePins() {
        return duplicatePins;
    }

    public void setDuplicatePins(boolean duplicatePins) {
        this.duplicatePins = duplicatePins;
    }

    public boolean isEmptyPins() {
        return emptyPins;
    }

    public void setEmptyPins(boolean emptyPins) {
        this.emptyPins = emptyPins;
    }

    public int getNumberRounds() {
        return numberRounds;
    }

    public void setNumberRounds(int numberRounds) {
        this.numberRounds = numberRounds;
    }
}
