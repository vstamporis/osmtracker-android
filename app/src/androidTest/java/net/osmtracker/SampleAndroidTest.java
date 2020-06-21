package net.osmtracker;

import android.support.test.rule.ActivityTestRule;

import net.osmtracker.activity.TrackManager;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import gr.aueb.android.barista.core.annotations.enumarations.NetworkAdapterStateType;
import gr.aueb.android.barista.core.inline.Barista;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

public class SampleAndroidTest {

    @Rule
    public ActivityTestRule<TrackManager> trackManagerActivityTestRule = new ActivityTestRule<TrackManager>(TrackManager.class);

    @Test
    public void test() {
        onView(withId(R.id.trackmgr_menu_newtrack)).perform(click());
        Barista.setGpsState(NetworkAdapterStateType.DISABLED);
    }

    @Before
    public void init(){
        trackManagerActivityTestRule.getActivity();
    }

}
