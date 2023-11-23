import java.util.Date;

public class Therapist {
    private int id;
    private String firstName;
    private String phoneNumber;
    private String address;
    private boolean isActive;
    private Date resignationDate;

    public Therapist() {
    }

    public Therapist(int id, String firstName, String phoneNumber, String address, boolean isActive, Date resignationDate) {
        this.id = id;
        this.firstName = firstName;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.isActive = isActive;
        this.resignationDate = resignationDate;
    }

    // Getters
    public int getId() { return id; }
    public String getFirstName() { return firstName; }
    public String getPhoneNumber() { return phoneNumber; }
    public String getAddress() { return address; }
    public boolean isActive() { return isActive; }
    public Date getResignationDate() { return resignationDate; }

    // Setters
    public void setId(int id) { this.id = id; }
    public void setFirstName(String firstName) { this.firstName = firstName; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }
    public void setAddress(String address) { this.address = address; }
    public void setActive(boolean active) { isActive = active; }
    public void setResignationDate(Date resignationDate) { this.resignationDate = resignationDate; }
}
