package net.osmtracker;

import android.Manifest;

import androidx.test.rule.ActivityTestRule;
import androidx.test.rule.GrantPermissionRule;

import net.osmtracker.activity.TrackManager;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import gr.aueb.android.barista.core.annotations.Gps;
import gr.aueb.android.barista.core.annotations.enumarations.NetworkAdapterStateType;
import gr.aueb.android.barista.core.inline.Barista;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

public class SampleAndroidTest {

    @Rule
    public ActivityTestRule<TrackManager> trackManagerActivityTestRule = new ActivityTestRule<TrackManager>(TrackManager.class);

    @Rule
    public GrantPermissionRule mRuntimePermissionRule = GrantPermissionRule.grant(Manifest.permission.WRITE_EXTERNAL_STORAGE);

    @Rule
    public GrantPermissionRule mRuntimePermissionRule2 = GrantPermissionRule.grant(Manifest.permission.ACCESS_FINE_LOCATION);

    @Gps(NetworkAdapterStateType.ENABLED)
    @Test
    public void displayMessageIfGpsDisabled() {
        Barista.setGpsState(NetworkAdapterStateType.DISABLED);
        onView(withId(R.id.trackmgr_menu_newtrack)).perform(click());
        onView(withText("GPS disabled")).check(matches(isDisplayed()));
    }

    @Before
    public void init(){
        trackManagerActivityTestRule.getActivity();
    }

}
