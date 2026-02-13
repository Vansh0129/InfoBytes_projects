package OnlineReservation.DataPAck;
import OnlineReservation.Exception.*;
import java.sql.Connection;
import java.sql.DriverManager;

public class DB_transaction {
    String url = "jdbc:mysql://localhost:3306/";
    String username = "root";
    String password = "Vansh_0129";
    Connection con;
    public DB_transaction(){
        try {
            con = DriverManager.getConnection(url, username, password);
            System.out.println("Connected Successfully");
        }
        catch (Exception e) {
            System.out.println("Connection Failed");
            e.printStackTrace();
        }

    }


}
