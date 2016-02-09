package me.thomasleese.planets.util;

import com.badlogic.gdx.math.MathUtils;

import java.util.Calendar;

public abstract class ClockUtils {

    private static float calculateProportion(int timeUnit, Calendar now) {
        if (timeUnit == Calendar.HOUR) {
            return (now.get(Calendar.HOUR) * 60f + now.get(Calendar.MINUTE)) / (12f * 60f);
        } else if (timeUnit == Calendar.MINUTE) {
            return (now.get(Calendar.MINUTE) * 60f + now.get(Calendar.SECOND)) / 3600f;
        } else if (timeUnit == Calendar.SECOND) {
            return (now.get(Calendar.SECOND) * 1000f + now.get(Calendar.MILLISECOND)) / 60000f;
        } else {
            throw new IllegalArgumentException("Invalid time unit.");
        }
    }

    public static float calculateRotation(int timeUnit, Calendar now) {
        float proportion = calculateProportion(timeUnit, now);
        return (proportion * -MathUtils.PI2) * MathUtils.radiansToDegrees;
    }

}
