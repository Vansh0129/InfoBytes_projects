package OnlineReservation.dao;

import OnlineReservation.Model.UserTicket;
import OnlineReservation.Model.userInfo;

public interface UserSesstion {
     Boolean CreateUser(userInfo e);
     Boolean CreateBooking(String username, UserTicket ticket);
     Boolean DeleteBooking(String username,int PNR);



}
