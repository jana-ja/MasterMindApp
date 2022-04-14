package com.example.mastermind.model;

import com.example.mastermind.R;

public enum PinColor {
    MERCURY, VENUS, EARTH, MARS, JUPITER, SATURN, URANUS, NEPTUN, PLUTO, EMPTY, SOLUTION;


    public int getImageName(){
        switch(this){
            case MERCURY:
                return R.drawable.merkur;
            case VENUS:
                return R.drawable.venus;
            case EARTH:
                return R.drawable.erde;
            case MARS:
                return R.drawable.mars;
            case JUPITER:
                return R.drawable.jupiter;
            case SATURN:
                return R.drawable.saturn;
            case URANUS:
                return R.drawable.uranus;
            case NEPTUN:
                return R.drawable.neptun;
            case PLUTO:
                return R.drawable.pluto;
            case EMPTY:
                return R.drawable.empty;
            case SOLUTION:
                return R.drawable.black_hole;
            default:
                return R.drawable.empty;
        }
    }

    public int getStringId(){
        switch(this) {
            case MERCURY:
                return R.string.merkur;
            case VENUS:
                return R.string.venus;
            case EARTH:
                return R.string.erde;
            case MARS:
                return R.string.mars;
            case JUPITER:
                return R.string.jupiter;
            case SATURN:
                return R.string.saturn;
            case URANUS:
                return R.string.uranus;
            case NEPTUN:
                return R.string.neptun;
            case PLUTO:
                return R.string.pluto;
            default:
                return 0;
        }
    }
}
