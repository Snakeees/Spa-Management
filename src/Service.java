import java.util.Date;

public class Service {
    private int id;
    private String serviceName;
    private int duration;
    private int cost;
    private boolean isActive;
    private Date serviceLastDate;

    public Service(int id, String serviceName, int duration, int cost, boolean isActive, Date serviceLastDate) {
        this.id = id;
        this.serviceName = serviceName;
        this.duration = duration;
        this.cost = cost;
        this.isActive = isActive;
        this.serviceLastDate = serviceLastDate;
    }

    // Getters
    public int getId() { return id; }
    public String getServiceName() { return serviceName; }
    public int getDuration() { return duration; }
    public int getCost() { return cost; }
    public boolean isActive() { return isActive; }
    public Date getServiceLastDate() { return serviceLastDate; }

    // Setters
    public void setId(int id) { this.id = id; }
    public void setServiceName(String serviceName) { this.serviceName = serviceName; }
    public void setDuration(int duration) { this.duration = duration; }
    public void setCost(int cost) { this.cost = cost; }
    public void setActive(boolean active) { isActive = active; }
    public void setServiceLastDate(Date serviceLastDate) { this.serviceLastDate = serviceLastDate; }
}
