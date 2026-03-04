package OnlineReservation.Model;
import OnlineReservation.Exception.InternalServerException;

import java.time.LocalDate;
import java.time.LocalTime;

public class UserTicket {
    final  private  int PNR;
    final private LocalDate SourceDate;
    final private LocalTime SourceTime;
    final private String SourceLocation;
    final private TrainClass classtype;
    final private Integer TrainNumber;

    final private LocalDate DestinationDate;
    final  private  LocalTime DestinationTime;
    final private String DestinationLocation;

    public int getPNR() {
        return PNR;
    }

    public LocalDate getSourceDate() {
        return SourceDate;
    }

    public LocalTime getSourceTime() {
        return SourceTime;
    }

    public String getSourceLocation() {
        return SourceLocation;
    }

    public TrainClass getClasstype() {
        return classtype;
    }

    public LocalDate getDestinationDate() {
        return DestinationDate;
    }

    public LocalTime getDestinationTime() {
        return DestinationTime;
    }

    public String getDestinationLocation() {
        return DestinationLocation;
    }

    public UserTicket(UserTicket other) {
        Date(other);
        this.PNR = other.PNR;
        this.SourceDate = other.SourceDate;
        this.DestinationDate = other.DestinationDate;

        this.SourceTime = other.SourceTime;
        this.DestinationTime = other.DestinationTime;
        this.classtype=other.classtype;
        this.SourceLocation=other.SourceLocation;
        this.DestinationLocation=other.DestinationLocation;
    this.TrainNumber= other.TrainNumber;
    }
    //setter Constructor
   private static void Date(UserTicket ut){
        if(ut.SourceDate.isBefore(LocalDate.now())) throw new InternalServerException("Journey date cannot be in the past\n");
        if(ut.DestinationDate.isBefore(ut.SourceDate))  throw new InternalServerException("Destination date cannot be before source date\n");
    }
}
class TicketValidator{

}

