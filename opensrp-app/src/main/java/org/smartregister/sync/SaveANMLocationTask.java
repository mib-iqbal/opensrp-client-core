package org.smartregister.sync;

import org.smartregister.repository.AllSettings;
import org.smartregister.util.Log;
import org.smartregister.view.BackgroundAction;
import org.smartregister.view.LockingBackgroundTask;
import org.smartregister.view.ProgressIndicator;

public class SaveANMLocationTask {
    private final LockingBackgroundTask task;
    private AllSettings allSettings;

    public SaveANMLocationTask(AllSettings allSettings) {
        this.allSettings = allSettings;
        task = new LockingBackgroundTask(new ProgressIndicator() {
            @Override
            public void setVisible() {
            }

            @Override
            public void setInvisible() {
                Log.logInfo("Successfully saved ANM Location information");
            }
        });
    }

    public void save(final String anmLocation) {
        task.doActionInBackground(new BackgroundAction<String>() {
            @Override
            public String actionToDoInBackgroundThread() {
                allSettings.saveANMLocation(anmLocation);
                return anmLocation;
            }

            @Override
            public void postExecuteInUIThread(String result) {
            }
        });
    }
}
