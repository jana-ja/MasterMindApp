package jana.ja.mastermind.view;

import android.content.Context;

import jana.ja.mastermind.R;

public class Indicator extends Cell {

    public Indicator(Context context, int xPos, int yPos) {
        super(context, xPos, yPos);
    }

    public void setNumber(int number, boolean black){
        this.setImageResource(getNumberImageResource(number,black));
    }

    public void highlight(){
        // show rocket
        this.setImageResource(R.drawable.rakete);
    }

    private int getNumberImageResource(int number, boolean black){
        if(black){
            switch(number){
                case 0: return R.drawable.black_0;
                case 1: return R.drawable.black_1;
                case 2: return R.drawable.black_2;
                case 3: return R.drawable.black_3;
                case 4: return R.drawable.black_4;
                default: return android.R.color.transparent;
            }
        } else {
            switch(number){
                case 0: return R.drawable.red_0;
                case 1: return R.drawable.red_1;
                case 2: return R.drawable.red_2;
                case 3: return R.drawable.red_3;
                case 4: return R.drawable.red_4;
                default: return android.R.color.transparent;
            }
        }
    }

}
