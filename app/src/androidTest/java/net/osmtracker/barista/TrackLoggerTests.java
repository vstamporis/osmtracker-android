package net.osmtracker.barista;

import android.Manifest;
import android.support.test.espresso.action.ViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.util.Pair;
import android.widget.ActionMenuView;
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

import gr.aueb.android.barista.core.annotations.BatteryOptions;
import gr.aueb.android.barista.core.annotations.Data;
import gr.aueb.android.barista.core.annotations.GeoFix;
import gr.aueb.android.barista.core.annotations.Gps;
import gr.aueb.android.barista.core.annotations.Permission;
import gr.aueb.android.barista.core.annotations.Wifi;
import gr.aueb.android.barista.core.annotations.enumarations.NetworkAdapterStateType;
import gr.aueb.android.barista.core.annotations.enumarations.OrientationOptions;
import gr.aueb.android.barista.core.inline.Barista;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.matcher.ViewMatchers.isRoot;
import static android.support.test.espresso.matcher.ViewMatchers.withClassName;
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

    @Data(NetworkAdapterStateType.DISABLED)
    @Test
    public void viewMapAndWalk() {
        Utils.waitFor(2);
        openActionBarOverflowOrOptionsMenu(activityActivityTestRule.getActivity().getApplicationContext());
        Utils.waitFor(1);
        onView(withText("Display track")).perform(click());
        Barista.setGeolocation(23.4, 38.4);
        Utils.waitFor(2);
        Barista.setOrientation(OrientationOptions.ORIENTATION_90);
        Utils.waitFor(2);
        Barista.setGeolocation(23.4, 38.5);
        Utils.waitFor(2);
        onView(isRoot()).perform(ViewActions.pressBack());
        Utils.waitFor(10);
    }

    @Data(NetworkAdapterStateType.DISABLED)
    //BatteryOptions()
    @Test
    public void changeOrientation() {
        Utils.waitFor(2);
        Barista.setOrientation(OrientationOptions.ORIENTATION_90);
        Barista.setGpsState(NetworkAdapterStateType.ENABLED);
        //Barista.setBatteryState(10, false);
        Utils.waitFor(3);
    }

    @Data(NetworkAdapterStateType.ENABLED)
    @Test
    public void lowBatteryTest() {
        //Barista.setBatteryState(10, false);
        Utils.waitFor(5);
        openActionBarOverflowOrOptionsMenu(activityActivityTestRule.getActivity().getApplicationContext());
        Utils.waitFor(1);
        //Barista.setBatteryState(8, false);
        onView(withText("Settings")).perform(click());
        onView(withText("Voice record duration")).perform(click());
        //Barista.setBatteryState(4, false);
        onView(withText("10")).perform(click());
        onView(isRoot()).perform(ViewActions.pressBack());
        onView(withText("VOICE RECORD")).perform(click());
        //Barista.setBatteryState(0, false);
    }

    @Test
    //@Gps(NetworkAdapterStateType.DISABLED)
    public void wrongExit() {
        Barista.setGpsState(NetworkAdapterStateType.ENABLED);
    }

    @Test
    @GeoFix(lat = 38.2, longt = 23.4)
    @Gps(NetworkAdapterStateType.ENABLED)
    @Permission(Manifest.permission.ACCESS_FINE_LOCATION)
    public void createNoteWhileMovingDroppedGps() {
        Utils.waitFor(3);
        onView(withText("Text note")).perform(click());
        Utils.waitFor(2);
        onView(withId(R.id.text_note_input)).perform(click(), replaceText("Barista Test Input"));
        Barista.setGpsState(NetworkAdapterStateType.DISABLED);
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
