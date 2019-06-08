package com.example.Fragment;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import com.example.R;

public class SimpleDialogFragment extends DialogFragment {
    private int nameid;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        final LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_simple, null);
        builder.setView(view);

        Bundle arg = getArguments();
        String title = arg.getString("title");
        String text = arg.getString("text");
        String postext = arg.getString("postext");
        String negtext = null;
        if (arg.getString("negtext") != null) {
            negtext = arg.getString("negtext");
        }

        TextView tv = (TextView)view.findViewById(R.id.dialog_simple_text);
        tv.setText(text);

        builder.setPositiveButton(postext,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent();
                        getTargetFragment().onActivityResult(getTargetRequestCode(), Activity.RESULT_OK, intent);
                        dismiss();
                    }
                });
        if (negtext != null) {
            builder.setNegativeButton(negtext,
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dismiss();
                        }
                    });
        }

        builder.setTitle(title);

        return builder.create();
    }
}
