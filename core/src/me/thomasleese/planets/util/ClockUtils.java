package me.thomasleese.planets.util;

import com.badlogic.gdx.math.MathUtils;
import com.luckycatlabs.sunrisesunset.SunriseSunsetCalculator;
import com.luckycatlabs.sunrisesunset.dto.Location;

import java.util.Calendar;

public abstract class ClockUtils {

    private static Calendar sSunrise, sSunset;
    private static float sSunriseAngle, sSunsetAngle;
    private static Calendar sLastSunriseSunsetUpdate;

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

    private static void calculateSunriseSunset() {
        Calendar now = Calendar.getInstance();

        Calendar twoHoursAgo = Calendar.getInstance();
        twoHoursAgo.add(Calendar.HOUR, -2);

        if (sLastSunriseSunsetUpdate == null || twoHoursAgo.after(sLastSunriseSunsetUpdate)) {
            Location location = new Location("50.923802050", "-1.3840690");
            SunriseSunsetCalculator calculator = new SunriseSunsetCalculator(location, "Europe/London");

            sSunrise = calculator.getOfficialSunriseCalendarForDate(now);
            sSunset = calculator.getOfficialSunsetCalendarForDate(now);

            sSunriseAngle = ClockUtils.calculateRotation(Calendar.HOUR, sSunrise);
            sSunsetAngle = ClockUtils.calculateRotation(Calendar.HOUR, sSunset);

            sLastSunriseSunsetUpdate = now;
        }
    }

    public static Calendar getSunrise() {
        calculateSunriseSunset();
        return sSunrise;
    }

    public static Calendar getSunset() {
        calculateSunriseSunset();
        return sSunset;
    }

    public static float getSunriseAngle() {
        calculateSunriseSunset();
        return sSunriseAngle;
    }

    public static float getSunsetAngle() {
        calculateSunriseSunset();
        return sSunsetAngle;
    }

}
