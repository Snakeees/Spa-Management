import java.util.Date;

public class Appointment {
    private int id;
    private int clientId;
    private Date appointmentDate;
    private Date appointmentTime;
    private int therapistId;
    private int serviceId;
    private boolean isDone;
    private boolean isPaid;

    public Appointment(int id, int clientId, Date appointmentDate, Date appointmentTime, int therapistId, int serviceId, boolean isDone, boolean isPaid) {
        this.id = id;
        this.clientId = clientId;
        this.appointmentDate = appointmentDate;
        this.appointmentTime = appointmentTime;
        this.therapistId = therapistId;
        this.serviceId = serviceId;
        this.isDone = isDone;
        this.isPaid = isPaid;
    }

    public int getId() { return id; }
    public int getClientId() { return clientId; }
    public Date getAppointmentDate() { return appointmentDate; }
    public Date getAppointmentTime() { return appointmentTime; }
    public int getTherapistId() { return therapistId; }
    public int getServiceId() { return serviceId; }
    public boolean isDone() { return isDone; }
    public boolean isPaid() { return isPaid; }

    public void setId(int id) { this.id = id; }
    public void setClientId(int clientId) { this.clientId = clientId; }
    public void setAppointmentDate(Date appointmentDate) { this.appointmentDate = appointmentDate; }
    public void setAppointmentTime(Date appointmentTime) { this.appointmentTime = appointmentTime; }
    public void setTherapistId(int therapistId) { this.therapistId = therapistId; }
    public void setServiceId(int serviceId) { this.serviceId = serviceId; }
    public void setDone(boolean done) { isDone = done; }
    public void setPaid(boolean paid) { isPaid = paid; }
}