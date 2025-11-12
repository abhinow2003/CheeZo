import com.ust.pos.bean.ProfileBean;
import com.ust.pos.ui.LoginPage;

public class App {

    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(() -> {
            new LoginPage(user -> {
                if (user.getFirstName().equalsIgnoreCase("Abhinav")) {
                    showAdminUI(user);
                } else {
                    showUserUI(user);
                }
            }).setVisible(true);
        });
    }

    // Use your existing admin and user panel methods here
    private static void showAdminUI(ProfileBean user) {
        // existing admin UI code from your previous App.java
    }

    private static void showUserUI(ProfileBean user) {
        // existing user UI code from your previous App.java
    }
}
