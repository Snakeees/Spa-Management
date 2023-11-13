import java.util.Date;

public class TherapistAttendance {
    private int id;
    private int therapistId;
    private Date date;
    private Date checkinTime;
    private Date checkoutTime;

    public TherapistAttendance(int id, int therapistId, Date date, Date checkinTime, Date checkoutTime) {
        this.id = id;
        this.therapistId = therapistId;
        this.date = date;
        this.checkinTime = checkinTime;
        this.checkoutTime = checkoutTime;
    }

    public int getId() { return id; }
    public int getTherapistId() { return therapistId; }
    public Date getDate() { return date; }
    public Date getCheckinTime() { return checkinTime; }
    public Date getCheckoutTime() { return checkoutTime; }

    public void setId(int id) { this.id = id; }
    public void setTherapistId(int therapistId) { this.therapistId = therapistId; }
    public void setDate(Date date) { this.date = date; }
    public void setCheckinTime(Date checkinTime) { this.checkinTime = checkinTime; }
    public void setCheckoutTime(Date checkoutTime) { this.checkoutTime = checkoutTime; }
}



