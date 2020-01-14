package net.osmtracker.rest;

import android.widget.ActionMenuView;

import net.osmtracker.R;
import net.osmtracker.activity.TrackLogger;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.isAssignableFrom;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

public class GoToTrackLogger {

    public static TrackLogger recordNewTrack() {
        onView(withId(R.id.trackmgr_menu_newtrack)).perform(click());

        TrackLogger trackLogger = (TrackLogger) Utils.getActivityInstance();
        return trackLogger;
    }

    public static void saveAndStopTracking() {
        onView(withId(R.id.tracklogger_menu_stoptracking)).perform(click());
    }
}
