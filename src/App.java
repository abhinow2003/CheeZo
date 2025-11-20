import com.ust.pos.bean.ProfileBean;
import com.ust.pos.ui.LoginPage;
import com.ust.pos.ui.AdminPanel;
import com.ust.pos.ui.UserPanel;  // your user UI

public class App {

    public static void main(String[] args) {

        javax.swing.SwingUtilities.invokeLater(() -> {

            new LoginPage(user -> {

                // CLOSE LOGIN PAGE
                // (only if LoginPage constructor accepts listener and opens UI)
                
                // AUTH LOGIC: If firstName is Abhinav → Admin
                if (user.getFirstName().equalsIgnoreCase("Abhinav")) {
                    showAdminUI(user);
                } else {
                    showUserUI(user);
                }

            }).setVisible(true);
        });
    }

    private static void showAdminUI(ProfileBean user) {
        System.out.println("Logged in as admin: " + user.getFirstName());
        AdminPanel admin = new AdminPanel();
        admin.setVisible(true);
    }

    private static void showUserUI(ProfileBean user) {
        System.out.println("Logged in as user: " + user.getFirstName());
        UserPanel panel = new UserPanel(); // pass user if needed
        panel.setVisible(true);
    }
}
