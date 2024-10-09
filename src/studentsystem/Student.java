package studentsystem;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Student {
    private String ID;
    private String name;
    private Date birthday;
    private boolean gender;
    private String phone;
    private String email;
    private String location;
    private int age;
    private double toan;
    private double van;
    private double anh;

    // Constructor
    public Student(String ID, String name, String birthdayStr, boolean gender, String phone, String email, String location, double toan, double van, double anh) {
        this.ID = ID;
        this.name = name;
        this.gender = gender;
        this.phone = phone;
        this.email = email;
        this.location = location;
        this.toan = toan;
        this.van = van;
        this.anh = anh;
        
        // Chuyển đổi chuỗi ngày sinh thành đối tượng Date
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        try {
            this.birthday = dateFormat.parse(birthdayStr);
        } catch (ParseException e) {
            e.printStackTrace(); // Xử lý lỗi nếu có
        }
    }

    // Getter và Setter
    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public boolean isGender() {
        return gender;
    }

    public void setGender(boolean gender) {
        this.gender = gender;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    
    public String getLocation() {
        return location;
    }
    
    public void setLocation(String location) {
        this.location = location;
    }

//    public int getAge() {
//        return age;
//    }
//
//    public void setAge(int age) {
//        this.age = age;
//    }

    public double getToan() {
        return toan;
    }

    public void setToan(double toan) {
        this.toan = toan;
    }

    public double getVan() {
        return van;
    }

    public void setVan(double van) {
        this.van = van;
    }

    public double getAnh() {
        return anh;
    }

    public void setAnh(double anh) {
        this.anh = anh;
    }
    
    public double diemTB() {
        return Math.round((toan + van + anh) / 3 * 100.0) / 100.0;
    }

    @Override
    public String toString() {
        return "Student{" + "ID=" + ID + ", name=" + name + ", birthday=" + birthday + ", gender=" + gender + ", phone=" + phone + ", email=" + email + ", location=" + location + ", age=" + age + ", toan=" + toan + ", van=" + van + ", anh=" + anh + '}';
    }
}