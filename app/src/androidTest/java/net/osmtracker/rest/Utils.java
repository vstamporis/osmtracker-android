package net.osmtracker.rest;

import android.app.Activity;
import android.support.test.runner.lifecycle.ActivityLifecycleMonitorRegistry;
import android.support.test.runner.lifecycle.Stage;
import android.util.Pair;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Iterator;

import static android.support.test.InstrumentationRegistry.getInstrumentation;

public class Utils {

    public static void waitFor(int seconds) {
        try {
            Thread.sleep(seconds * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static double truncateDouble(double number) {
        Double truncatedDouble = new BigDecimal(number).setScale(1, BigDecimal.ROUND_HALF_UP).doubleValue();
        return truncatedDouble;
    }

    public static Activity getActivityInstance() {
        final Activity[] currentActivity = {null};

        getInstrumentation().runOnMainSync(new Runnable(){
            public void run(){
                Collection<Activity> resumedActivity = ActivityLifecycleMonitorRegistry.getInstance().getActivitiesInStage(Stage.RESUMED);
                Iterator<Activity> it = resumedActivity.iterator();
                currentActivity[0] = it.next();
            }
        });

        return currentActivity[0];
    }

    public static Pair<Double, Double> getLatLong(String location) {
        String[] broken = location.split(", ");
        double lat = Double.parseDouble(broken[0].substring(4));
        double longi = Double.parseDouble(broken[1].substring(5));
        return new Pair<>(lat, longi);
    }
}
