package jana.ja.mastermind.model;

public class PinRow {

    private boolean okay;
    private int correctColors;
    private int correctPlaces;

    public PinRow() {
    }

    public boolean isOkay() {
        return okay;
    }

    public int getCorrectColors() {
        return correctColors;
    }

    public int getCorrectPlaces() {
        return correctPlaces;
    }

    public void setOkay(boolean okay) {
        this.okay = okay;
    }

    public void setCorrectColors(int correctColors) {
        this.correctColors = correctColors;
    }

    public void setCorrectPlaces(int correctPlaces) {
        this.correctPlaces = correctPlaces;
    }
}
