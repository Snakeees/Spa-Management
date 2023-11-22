public class UserLogin {
    private int id;
    private String loginName;
    private String password;
    private boolean isAdmin;
    private boolean isActive;

    public UserLogin(int id, String loginName, String password, boolean isAdmin, boolean isActive) {
        this.id = id;
        this.loginName = loginName;
        this.password = password;
        this.isAdmin = isAdmin;
        this.isActive = isActive;
    }

    public UserLogin() {

    }

    public int getId() { return id; }
    public String getLoginName() { return loginName; }
    public String getPassword() { return password; }
    public boolean isAdmin() { return isAdmin; }
    public boolean isActive() { return isActive; }

    public void setId(int id) { this.id = id; }
    public void setLoginName(String loginName) { this.loginName = loginName; }
    public void setPassword(String password) { this.password = password; }
    public void setAdmin(boolean admin) { isAdmin = admin; }
    public void setActive(boolean active) { isActive = active; }
}

