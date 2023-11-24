import java.util.Date;

public class Appointment {
    private int id;
//    private int clientId;
    private String clientName;
    private  String clientPhoneNumber;
    private Date appointmentDate;
    private Date appointmentTime;
    private int therapistId;
    private int serviceId;
    private boolean isDone;
    private boolean isPaid;
    private boolean isActive;

    public Appointment() {
    }

    public Appointment(int id, String clientName, String clientPhoneNumber, Date appointmentDate, Date appointmentTime, int therapistId, int serviceId, boolean isDone, boolean isPaid,boolean isActive) {
        this.id = id;
        this.clientName=clientName;
        this.clientPhoneNumber=clientPhoneNumber;
        this.appointmentDate = appointmentDate;
        this.appointmentTime = appointmentTime;
        this.therapistId = therapistId;
        this.serviceId = serviceId;
        this.isDone = isDone;
        this.isPaid = isPaid;
        this.isActive=isActive;
    }

    public int getId() { return id; }

    public Date getAppointmentDate() { return appointmentDate; }
    public Date getAppointmentTime() { return appointmentTime; }
    public int getTherapistId() { return therapistId; }
    public int getServiceId() { return serviceId; }
    public boolean isDone() { return isDone; }
    public boolean isPaid() { return isPaid; }

    public void setId(int id) { this.id = id; }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getClientPhoneNumber() {
        return clientPhoneNumber;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public void setClientPhoneNumber(String clientPhoneNumber) {
        this.clientPhoneNumber = clientPhoneNumber;
    }

    public void setAppointmentDate(Date appointmentDate) { this.appointmentDate = appointmentDate; }
    public void setAppointmentTime(Date appointmentTime) { this.appointmentTime = appointmentTime; }
    public void setTherapistId(int therapistId) { this.therapistId = therapistId; }
    public void setServiceId(int serviceId) { this.serviceId = serviceId; }
    public void setDone(boolean done) { isDone = done; }
    public void setPaid(boolean paid) { isPaid = paid; }
}