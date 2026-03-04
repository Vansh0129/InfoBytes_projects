package OnlineReservation.dao;

import OnlineReservation.Model.UserTicket;
import OnlineReservation.Model.userInfo;

public class UserRepo implements UserSesstion {


    @Override
    public Boolean CreateUser(userInfo e) {
        return true;  //not done yet!
    }

    @Override
    public Boolean CreateBooking(String username, UserTicket ticket) {
        return null;
    }

    @Override
    public Boolean DeleteBooking(String username, int PNR) {
        return null;
    }
}
