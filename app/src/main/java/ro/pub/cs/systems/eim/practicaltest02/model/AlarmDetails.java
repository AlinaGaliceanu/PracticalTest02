package ro.pub.cs.systems.eim.practicaltest02.model;

public class AlarmDetails {

    private String Hours;
    private String Minutes;

    public AlarmDetails(String hour, String minutes) {
        this.Hours = hour;
        this.Minutes = minutes;
    }

    public String getHours() {
        return Hours;
    }

    public void setHours(String hours) {
        Hours = hours;
    }

    public String getMinutes() {
        return Minutes;
    }

    public void setMinutes(String minutes) {
        Minutes = minutes;
    }
}
