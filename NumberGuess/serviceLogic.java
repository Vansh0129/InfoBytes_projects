package NumberGuess;

public class serviceLogic {

    public void StarterService(){
        UserModeDto userModeDto=new UserModeDto();
        userModeDto.setGameMode(GameMode.Challenge);
        new InputUi().Storing_Input(userModeDto);
    }

}
