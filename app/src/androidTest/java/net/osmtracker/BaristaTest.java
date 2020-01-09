package net.osmtracker;

import android.Manifest;
import android.support.test.rule.ActivityTestRule;

import net.osmtracker.activity.TrackManager;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import gr.aueb.android.barista.core.annotations.GeoFix;
import gr.aueb.android.barista.core.annotations.Permission;
import gr.aueb.android.barista.core.http_client.HTTPClientManager;
import gr.aueb.android.barista.core.model.CommandDTO;
import gr.aueb.android.barista.core.model.GeoFixDTO;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

public class BaristaTest {

    @Rule
    public ActivityTestRule<TrackManager> activityActivityTestRule = new ActivityTestRule<TrackManager>(TrackManager.class);

    @Test
    @GeoFix(lat = 37.975391, longt = 23.735524)
    //@Permission(Manifest.permission.ACCESS_FINE_LOCATION)
    public void testBarista() {
        onView(withText("Track list:")).check(matches(isDisplayed()));
    }

    @Before
    public void init(){
        activityActivityTestRule.getActivity().getContentScene();
    }


}
