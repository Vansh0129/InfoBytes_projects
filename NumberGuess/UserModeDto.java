package NumberGuess;

public class UserModeDto {


   private  String Username;
   private  Integer totalMatchWon;
   private  Integer totalMatchPlayed;
   private  Integer accuracy;
   private  Enum GameMode;

    public Enum getGameMode() {
        return GameMode;
    }

    public UserModeDto(String username, int totalMatchWon, int totalMatchPlayed, int accuracy, Enum gameMode) {
        this.Username = username;
        this.totalMatchWon = totalMatchWon;
        this.totalMatchPlayed = totalMatchPlayed;
        this.accuracy = accuracy;
        this.GameMode = gameMode;
    }
    public UserModeDto(){
        this.totalMatchPlayed = 0;
        this.totalMatchWon = 0;
        this.accuracy = 100;
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public Integer getTotalMatchWon() {
        return totalMatchWon;
    }

    public void setTotalMatchWon(Integer totalMatchWon) {
        this.totalMatchWon = totalMatchWon;
    }

    public Integer getTotalMatchPlayed() {
        return totalMatchPlayed;
    }

    public void setTotalMatchPlayed(Integer totalMatchPlayed) {
        this.totalMatchPlayed = totalMatchPlayed;
    }

    public Integer getAccuracy() {
        return accuracy;
    }

    @Override
    public String toString() {
        return "UserModeDto{" +
                "Username='" + Username + '\'' +
                ", totalMatchWon=" + totalMatchWon +
                ", totalMatchPlayed=" + totalMatchPlayed +
                ", accuracy=" + accuracy +
                ", GameMode=" + GameMode +
                '}';
    }

    public void setAccuracy(Integer accuracy) {
        this.accuracy = accuracy;
    }



    public void setGameMode(Enum gameMode) {
        GameMode = gameMode;
    }
}
