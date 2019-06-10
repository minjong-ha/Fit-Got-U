package com.example.Fragment;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import com.example.R;

public class HTDialogFragment extends DialogFragment {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        final LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_ht, null);
        builder.setView(view);

        Bundle arg = getArguments();
        String title = arg.getString("title");
        int imageid = arg.getInt("imageid");
        String desc = arg.getString("desc");
        final int nameid = arg.getInt("nameid");

        ImageView iv = (ImageView) view.findViewById(R.id.dialog_ht_image);
        if (imageid != 0) {
            iv.setImageResource(imageid);
        }
        TextView tv2 = (TextView)view.findViewById(R.id.dialog_ht_desc);
        tv2.setText(desc);

        builder.setPositiveButton("시작",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent();
                        intent.putExtra("exercise", nameid);
                        getTargetFragment().onActivityResult(getTargetRequestCode(), Activity.RESULT_OK, intent);
                        dismiss();
                    }
                });
        builder.setNegativeButton("취소",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dismiss();
                    }
                });

        builder.setTitle(title);

        return builder.create();
    }
}