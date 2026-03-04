package OnlineReservation.Model;


import java.util.List;

public class userInfo {
    private String username;        //act as id
    private String name;
    private int age;
    private Gender sex;
    private String contactNo;
    private String gmail;
    private List<UserTicket> userTickets;

    public userInfo() {}

    public userInfo(String name, int age, Gender sex, String contactNo, String gmail) {
        username=name+name.length()+(age*18)%20+contactNo.charAt(9);
        setName(name);
        setAge(age);
        setSex(sex);
        setContactNo(contactNo);
        setGmail(gmail);
    }

    @Override
    public String toString() {
        return "\nuserInfo{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", sex=" + sex +
                ", contactNo='" + contactNo + '\'' +
                ", gmail='" + gmail + '\'' +
              userTickets +
                "}\n";
    }

    // Getters & Setters with validation calls

    public String getName() {
        return name;
    }

    public void setName(String name) {
        UserInfoValidator.validateName(name);
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        UserInfoValidator.validateAge(age);
        this.age = age;
    }

    public Gender getSex() {
        return sex;
    }

    public void setSex(Gender sex) {
        if (sex == null) {
            throw new IllegalArgumentException("Gender cannot be null");
        }
        this.sex = sex;
    }

    public String getContactNo() {
        return contactNo;
    }

    public void setContactNo(String contactNo) {
        UserInfoValidator.validateContact(contactNo);
        this.contactNo = contactNo;
    }

    public String getGmail() {
        return gmail;
    }

    public void setGmail(String gmail) {
        UserInfoValidator.validateEmail(gmail);
        this.gmail = gmail;
    }
}
 class UserInfoValidator {

    public static void validateName(String name) {
        if (name == null || name.trim().length() < 3) {
            throw new IllegalArgumentException("Name must be at least 3 characters long");
        }
    }

    public static void validateAge(int age) {
        if (age < 18 || age > 100) {
            throw new IllegalArgumentException("Age must be between 18 and 100");
        }
    }

    public static void validateContact(String contact) {
        if (!contact.matches("\\d{10}")) {
            throw new IllegalArgumentException("Contact number must be exactly 10 digits");
        }
    }

    public static void validateEmail(String email) {
        if (!email.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
            throw new IllegalArgumentException("Invalid email format");
        }
    }

}
