package view;

public class WelcomeSplashFactory {

    private static WelcomeSplash welcomeSplash;

    public static WelcomeSplash getInstance(){
        if (welcomeSplash == null){
            welcomeSplash = new WelcomeSplash();
        }
        return welcomeSplash;
    }
}
