package OnlineReservation.Model;

import java.util.Objects;


public class profileEntity   {
    private String name = "";
    private String Password = "";
    public profileEntity(){}
    public  profileEntity(String name, String password) {
        this.name = name;
        this.Password = password;
    }

    protected boolean setprofile(String name, String password) {
       if(new profileValidator().UsernameValidator(name) && new profileValidator().PasswordValidator(password))
       {
           this.name=name;
           this.Password=password;
           return true;
       }
       return false;

    }            

    protected profileEntity getCredential() {
        return new profileEntity(this.name, this.Password);
    }

    protected boolean ValidateCredential(String name, String password) {
        if (Objects.equals(this.name, name) && Objects.equals(this.Password, password)) {
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