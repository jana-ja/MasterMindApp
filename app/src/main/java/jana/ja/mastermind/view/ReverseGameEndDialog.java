package jana.ja.mastermind.view;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import jana.ja.mastermind.R;
import com.google.android.material.button.MaterialButton;

public class ReverseGameEndDialog  extends DialogFragment {

    private boolean won;

    public ReverseGameEndDialog(boolean won){

        this.won = won;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());
        // Get the layout inflater
        LayoutInflater inflater = requireActivity().getLayoutInflater();

        String text = won? getString(R.string.reverse_game_won_text) : getString(R.string.reverse_game_lost_text);
        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        View view = inflater.inflate(R.layout.game_end_dialog, null);
        TextView textView = view.findViewById(R.id.dialog_message);
        textView.setText(text);
        textView.setOnClickListener(v -> dismiss());
        // TODO image einfügen oder so

        builder.setView(view);
        return builder.create();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Window window = getDialog().getWindow();
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        window.addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        window.setDimAmount(NormalGameEndDialog.DIM_AMOUNT);
        return super.onCreateView(inflater, container, savedInstanceState);
    }

}