package org.tasks.activities;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;

import com.todoroo.astrid.adapter.FilterAdapter;

import org.tasks.data.GoogleTaskList;
import org.tasks.dialogs.DialogBuilder;
import org.tasks.gtasks.GoogleTaskListSelectionHandler;
import org.tasks.injection.InjectingNativeDialogFragment;
import org.tasks.injection.NativeDialogFragmentComponent;

import javax.inject.Inject;

import static org.tasks.activities.SupportGoogleTaskListPicker.createDialog;

public class NativeGoogleTaskListPicker extends InjectingNativeDialogFragment {

    public static NativeGoogleTaskListPicker newNativeGoogleTaskListPicker(GoogleTaskList defaultList) {
        NativeGoogleTaskListPicker dialog = new NativeGoogleTaskListPicker();
        Bundle arguments = new Bundle();
        if (defaultList != null) {
            arguments.putParcelable(EXTRA_SELECTED, defaultList);
        }
        dialog.setArguments(arguments);
        return dialog;
    }

    public static final String EXTRA_SELECTED = "extra_selected";

    @Inject DialogBuilder dialogBuilder;
    @Inject FilterAdapter filterAdapter;

    private GoogleTaskListSelectionHandler handler;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Bundle arguments = getArguments();
        GoogleTaskList selected = arguments.getParcelable(EXTRA_SELECTED);
        return createDialog(filterAdapter, dialogBuilder, selected, list -> handler.selectedList(list));
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        handler = (GoogleTaskListSelectionHandler) activity;
    }

    @Override
    protected void inject(NativeDialogFragmentComponent component) {
        component.inject(this);
    }
}
