package net.osmtracker.barista;

import android.Manifest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.util.Pair;
import android.widget.TextView;

import net.osmtracker.R;
import net.osmtracker.activity.TrackLogger;
import net.osmtracker.activity.TrackManager;
import net.osmtracker.activity.WaypointList;
import net.osmtracker.rest.GoToTrackLogger;
import net.osmtracker.rest.Utils;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import gr.aueb.android.barista.core.annotations.GeoFix;
import gr.aueb.android.barista.core.annotations.Permission;
import gr.aueb.android.barista.core.inline.Barista;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.anything;

@RunWith(AndroidJUnit4.class)
public class TrackLoggerTests {

    private TrackLogger trackLogger = null;

    @Rule
    public ActivityTestRule<TrackManager> activityActivityTestRule = new ActivityTestRule<TrackManager>(TrackManager.class);

    @Test
    @GeoFix(lat = 38.2, longt = 23.4)
    @Permission(Manifest.permission.ACCESS_FINE_LOCATION)
    public void changeLocation() {
        Utils.waitFor(3);
        double lat = Utils.truncateDouble(trackLogger.getGpsLogger().getLatitude());
        GoToTrackLogger.saveAndStopTracking();
        Assert.assertEquals(Double.valueOf(38.2), Double.valueOf(lat));
    }

    @Test
    @GeoFix(lat = 38.2, longt = 23.4)
    @Permission(Manifest.permission.ACCESS_FINE_LOCATION)
    public void createNoteWhileMoving() {
        Utils.waitFor(3);
        onView(withText("Text note")).perform(click());
        Utils.waitFor(2);
        onView(withId(R.id.text_note_input)).perform(click(), replaceText("Barista Test Input"));
        Barista.setGeolocation(23.4, 38.4);
        Utils.waitFor(2);
        onView(withText("OK")).perform(click());
        GoToTrackLogger.saveAndStopTracking();
        Utils.waitFor(3);
        onData(anything()).atPosition(0).perform(click());
        Utils.waitFor(3);
        onView(withText("Waypoints:")).perform(click());
        WaypointList waypoints = ((WaypointList) Utils.getActivityInstance());
        TextView location = ((TextView) waypoints.findViewById(R.id.wplist_item_location));
        Pair<Double, Double> latlong = Utils.getLatLong(location.getText().toString());
        Assert.assertEquals(Double.valueOf(38.4), latlong.first);
        Assert.assertEquals(Double.valueOf(23.4), latlong.second);
    }

    @Before
    public void init() {
        //GoToTrackLogger.deleteAllTracks();
        activityActivityTestRule.getActivity().getContentScene();
        trackLogger = GoToTrackLogger.recordNewTrack();
    }
}
