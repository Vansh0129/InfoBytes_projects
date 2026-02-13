package OnlineReservation.Model;
import java.time.LocalDate;
import java.time.LocalTime;

public class UserTicket {
    private int PNR;
    private LocalDate SourceDate;
    private LocalTime SourceTime;
    private Locations SourceLocation;
    private LocalDate DestinationDate;

    private LocalTime DestinationTime;


    public UserTicket GetDetail(){
        return new UserTicket(this);
    }     //instance been returned
    public UserTicket(UserTicket other) {
        this.PNR = other.PNR;
        this.SourceDate = other.SourceDate;
        this.DestinationDate = other.DestinationDate;
        this.SourceTime = other.SourceTime;
        this.DestinationTime = other.DestinationTime;
    }         //setter Constructor






}

