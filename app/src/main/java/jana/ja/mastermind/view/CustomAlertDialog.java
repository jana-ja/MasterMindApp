package jana.ja.mastermind.view;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import jana.ja.mastermind.R;
import com.google.android.material.button.MaterialButton;

public class CustomAlertDialog extends DialogFragment{

    private String message;
    private View.OnClickListener listener;

    public CustomAlertDialog(String message, View.OnClickListener listener) {

        this.message = message;
        this.listener = listener;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        LayoutInflater inflater = requireActivity().getLayoutInflater();

        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        View view = inflater.inflate(R.layout.alert_dialog_layout, null);
        TextView textView = view.findViewById(R.id.dialog_message);
        textView.setText(message);
        MaterialButton buttonPos = view.findViewById(R.id.button_positive);
        buttonPos.setOnClickListener(v -> {
            listener.onClick(v);
            dismiss();
        });
        MaterialButton buttonNeg = view.findViewById(R.id.button_negative);
        buttonNeg.setOnClickListener(v -> dismiss());

        builder.setView(view);
        return builder.create();
    }

}
