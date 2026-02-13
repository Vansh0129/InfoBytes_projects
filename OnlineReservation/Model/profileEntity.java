package OnlineReservation.Model;

import java.util.Objects;


public class profileEntity   {
    private String User_Name = "";
    private String Password = "";


    public profileEntity(){}


    public profileEntity(profileEntity object) {
        if(new profileValidator().UsernameValidator(object.User_Name) && new profileValidator().PasswordValidator(object.Password))
        {
            this.User_Name=object.User_Name;
            this.Password=object.Password;

        }
        else {
            throw new IllegalArgumentException("Invalid Credentials");
        }

    }

    protected profileEntity getprofile() {
        return new profileEntity(this);
    }

    protected boolean ValidateCredential(String name, String password) {
        if (Objects.equals(this.User_Name, name) && Objects.equals(this.Password, password)) {
            return true;
        }
        return false;

    }   //check the password

}
class profileValidator {
    public boolean PasswordValidator(String password) {
        if (password.length() < 5 || password.length() > 20) {
            throw new RuntimeException("Password must be between 5 to 20 length only !");
        }   //validate
        boolean flag1 = false;
        boolean flag2 = false;
        boolean flag3 = false;
        for (Character c :password.toCharArray()) {
            if (c >= 'A' && c <= 'Z') flag1 = true;
            else if (Character.isDigit(c)) {
                flag2 = true;
            } else if (!Character.isLetterOrDigit(c)) {
                flag3 = true;
            }

        }
        if (flag2 && flag2 && flag3) {
            return true;
        } else {
            throw new RuntimeException("Password must contain atleast one specialCharacter,Alphabet and Digit !");
        }

    }
    public boolean UsernameValidator(String name) {
        for(char c:name.toCharArray()){
            if(Character.isLetterOrDigit(c)||c=='_'){

            }else{
                return false;
            }


        }
        return true;
    }
}