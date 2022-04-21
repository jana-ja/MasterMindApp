package jana.ja.mastermind.view;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import jana.ja.mastermind.R;
import com.google.android.material.button.MaterialButton;

public class NormalGameEndDialog extends DialogFragment {

    private boolean won;

    public NormalGameEndDialog(boolean won) {

        this.won = won;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());
        // Get the layout inflater
        LayoutInflater inflater = requireActivity().getLayoutInflater();

        String text = won? getString(R.string.game_won_text) : getString(R.string.game_lost_text);
        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        View view = inflater.inflate(R.layout.game_end_dialog, null);
        TextView textView = view.findViewById(R.id.dialog_message);
        textView.setText(text);
        MaterialButton buttonPos = view.findViewById(R.id.button_okay);
        buttonPos.setOnClickListener(v -> dismiss());

        builder.setView(view);
        return builder.create();
    }

}

