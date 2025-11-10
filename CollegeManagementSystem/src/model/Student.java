package model;

public class Student {
    private int id;
    private String name;
    private String rollno;
    private String phone;
    private String address;
    private String photoUrl;
    private String password;

    private int COA;
    private int DSA;
    private int PDC;
    private int POM;
    private int WEBDEV;
    private int DMS;

    public Student() {}

    public Student(int id, String name, String rollno, String phone, String address, String photoUrl, String password,
                   int COA, int DSA, int PDC, int POM, int WEBDEV, int DMS) {
        this.id = id;
        this.name = name;
        this.rollno = rollno;
        this.phone = phone;
        this.address = address;
        this.photoUrl = photoUrl;
        this.password = password;
        this.COA = COA;
        this.DSA = DSA;
        this.PDC = PDC;
        this.POM = POM;
        this.WEBDEV = WEBDEV;
        this.DMS = DMS;
    }

    
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getRollno() { return rollno; }
    public void setRollno(String rollno) { this.rollno = rollno; }
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }
    public String getPhotoUrl() { return photoUrl; }
    public void setPhotoUrl(String photoUrl) { this.photoUrl = photoUrl; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public int getCOA() { return COA; }
    public void setCOA(int COA) { this.COA = COA; }
    public int getDSA() { return DSA; }
    public void setDSA(int DSA) { this.DSA = DSA; }
    public int getPDC() { return PDC; }
    public void setPDC(int PDC) { this.PDC = PDC; }
    public int getPOM() { return POM; }
    public void setPOM(int POM) { this.POM = POM; }
    public int getWEBDEV() { return WEBDEV; }
    public void setWEBDEV(int WEBDEV) { this.WEBDEV = WEBDEV; }
    public int getDMS() { return DMS; }
    public void setDMS(int DMS) { this.DMS = DMS; }

    @Override
    public String toString() {
        return "Student{" + "id=" + id + ", name='" + name + '\'' + ", rollno='" + rollno + '\'' + '}';
    }
}
