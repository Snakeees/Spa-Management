import java.util.Date;

public class Therapist {
    private int id;
    private String firstName;
    private String phoneNumber;
    private String address;
    private boolean isActive;
    private Date resignationDate;

    public Therapist(int id, String firstName) {
        this.id = id;
        this.firstName = firstName;
    }

    public Therapist() {
    }

    @Override
    public String toString() {
        return firstName;
    }

    // Getters
    public int getId() {
        return id;
    }

    // Setters
    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public Date getResignationDate() {
        return resignationDate;
    }

    public void setResignationDate(Date resignationDate) {
        this.resignationDate = resignationDate;
    }
}
