package model;

public class Teacher {
    private int id;
    private String name;
    private String subject;
    private String photoUrl;
    private String phone;
    private String password;

    public Teacher() {}

    public Teacher(int id, String name, String subject, String photoUrl, String phone, String password) {
        this.id = id;
        this.name = name;
        this.subject = subject;
        this.photoUrl = photoUrl;
        this.phone = phone;
        this.password = password;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getSubject() { return subject; }
    public void setSubject(String subject) { this.subject = subject; }
    public String getPhotoUrl() { return photoUrl; }
    public void setPhotoUrl(String photoUrl) { this.photoUrl = photoUrl; }
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    @Override
    public String toString() {
        return "Teacher{" + "id=" + id + ", name='" + name + '\'' + ", subject='" + subject + '\'' + '}';
    }
}
