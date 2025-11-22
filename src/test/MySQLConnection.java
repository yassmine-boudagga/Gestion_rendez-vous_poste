package test;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySQLConnection {

    // Informations de connexion à la base de données
    private static final String URL = "jdbc:mysql://localhost:3306/";
    private static final String USER = "votre_utilisateur";
    private static final String PASSWORD = "votre_mot_de_passe";

    // Méthode statique pour obtenir la connexion
    public static Connection getConnection() {
        Connection connection = null;
        try {
            // Charge le driver MySQL (non nécessaire avec les versions récentes de JDBC)
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Établit la connexion
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Connexion à la base de données réussie !");
        } catch (ClassNotFoundException e) {
            System.err.println("Driver MySQL introuvable !");
            e.printStackTrace();
        } catch (SQLException e) {
            System.err.println("Erreur de connexion à la base de données !");
            e.printStackTrace();
        }
        return connection;
    }
}