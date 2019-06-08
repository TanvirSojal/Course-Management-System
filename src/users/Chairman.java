package users;

public class Chairman {
    private String chairmanInitial;
    private String chairmanName;
    private String chairmanEmail;
    private String chairmanBloodGroup;
    private String chairmanContactNumber;
    private String chairmanAddress;

    public Chairman(String chairmanInitial, String chairmanName, String chairmanEmail, String chairmanBloodGroup, String chairmanContactNumber, String chairmanAddress) {
        this.chairmanInitial = chairmanInitial;
        this.chairmanName = chairmanName;
        this.chairmanEmail = chairmanEmail;
        this.chairmanBloodGroup = chairmanBloodGroup;
        this.chairmanContactNumber = chairmanContactNumber;
        this.chairmanAddress = chairmanAddress;
    }

    public String getChairmanInitial() {
        return chairmanInitial;
    }

    public void setChairmanInitial(String chairmanInitial) {
        this.chairmanInitial = chairmanInitial;
    }

    public String getChairmanName() {
        return chairmanName;
    }

    public void setChairmanName(String chairmanName) {
        this.chairmanName = chairmanName;
    }

    public String getChairmanEmail() {
        return chairmanEmail;
    }

    public void setChairmanEmail(String chairmanEmail) {
        this.chairmanEmail = chairmanEmail;
    }

    public String getChairmanBloodGroup() {
        return chairmanBloodGroup;
    }

    public void setChairmanBloodGroup(String chairmanBloodGroup) {
        this.chairmanBloodGroup = chairmanBloodGroup;
    }

    public String getChairmanContactNumber() {
        return chairmanContactNumber;
    }

    public void setChairmanContactNumber(String chairmanContactNumber) {
        this.chairmanContactNumber = chairmanContactNumber;
    }

    public String getChairmanAddress() {
        return chairmanAddress;
    }

    public void setChairmanAddress(String chairmanAddress) {
        this.chairmanAddress = chairmanAddress;
    }

    @Override
    public String toString() {
        return String.format("%s (%s)",
                this.chairmanName,
                this.chairmanInitial);
    }
}
