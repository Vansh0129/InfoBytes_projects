package OnlineReservation.util;
import java.sql.Connection;
import java.sql.DriverManager;
import OnlineReservation.Exception.*;
public class DB_transaction {
   final  private String url = "jdbc:mysql://localhost:3306/";
    final private String username = "root";
    final private String password = "Vansh_0129";
    static Connection con;
    protected DB_transaction(){

        try {
            con = DriverManager.getConnection(url, username, password);
            System.out.println("Connected Successfully");
        }
        catch (Exception e) {
            System.out.println("Connection Failed");
            throw new InternalServerException(e.getLocalizedMessage());
        }

    }

    protected Connection sesstion(){
        return con;
    }


}
//done