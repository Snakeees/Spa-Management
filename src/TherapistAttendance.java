import java.sql.Time;
import java.util.Date;

public class TherapistAttendance {
    private int id;
    private int therapistId;
    private Date date;
    private Time checkinTime;
    private Time checkoutTime;

    public TherapistAttendance() {
    }

    public TherapistAttendance(int id, int therapistId, Date date, Time checkinTime, Time checkoutTime) {
        this.id = id;
        this.therapistId = therapistId;
        this.date = date;
        this.checkinTime = checkinTime;
        this.checkoutTime = checkoutTime;
    }

    public int getId() { return id; }
    public int getTherapistId() { return therapistId; }
    public Date getDate() { return date; }
    public Time getCheckinTime() { return checkinTime; }
    public Time getCheckoutTime() { return checkoutTime; }

    public void setId(int id) { this.id = id; }
    public void setTherapistId(int therapistId) { this.therapistId = therapistId; }
    public void setDate(Date date) { this.date = date; }
    public void setCheckinTime(Time checkinTime) { this.checkinTime = checkinTime; }
    public void setCheckoutTime(Time checkoutTime) { this.checkoutTime = checkoutTime; }
}



