import java.sql.*;
import java.util.Properties;
import java.util.Scanner;

public class Main {

    static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";

    public static void main(String[] args) throws Exception {
        try {
            Connection con;
            Class.forName(JDBC_DRIVER);
            String URL = "jdbc:mysql://localhost/jdbc_assistenza";
            Properties info = new Properties();
            info.put("user", "root");
            info.put("password", "root");
            info.put("autoReconnect", "true");
            info.put("useSSL", "false");
            info.put("serverTimezone", "Europe/Amsterdam");
            con = DriverManager.getConnection(URL, info);
            con.createStatement();
            System.out.println("Connected to MySQL\n");
            Scanner input = new Scanner(System.in);
            //--------------------------------------------------//
            System.out.println("Scrivi il codice cliente da visualizzare");
            String codc = input.nextLine();
            Cliente cliente = new Cliente(con, codc);
            if (cliente.clienteEsiste == true) {
                System.out.println("Trovato il " + cliente);

            } else {
                System.out.println("Cliente con codice: " + codc + " non trovato");
            }
            // ---------------------------------------------------//
            System.out.println("Verifica la disponibilita di un codice cliente: ");
            String cod2;
            cod2 = input.nextLine();
            Cliente cliente1 = new Cliente(con, cod2);
            System.out.println("Digita 1 per inserire, 2 per rimuovere: ");
            int choise = input.nextInt();
            if (choise == 1) {
                if (cliente1.clienteEsiste == true) {
                    System.out.println("Cliente esistente, codice non disponibile; ");
                } else {
                    System.out.println("Codice libero, Inserisci nuovi dati: ");
                    System.out.println("Inserisci nuovo codice cliente:  ");
                    cliente1.codc = input.nextLine();
                    System.out.println("Inserisci il nome completo: ");
                    cliente1.nome_cliente = input.nextLine();
                    System.out.println("Inserisci la nuova citta: ");
                    cliente1.citta = input.nextLine();
                    cliente1.insertClient(con);
                    System.out.println("Cliente inserito con successo \n" + cliente1);
                }
            } else if (choise == 2) {
                input.nextLine(); // ma che ne so, serve per leggere il newline dopo il nextInt(). Intanto senza questo non funziona
                System.out.println("Digita il codice cliente che vuoi eliminare: ");
                cliente1.codc = input.nextLine();
                cliente1.removeClient(con);
            }

            System.out.println("Visualizza i dati del cliente: ");
            cliente1.codc = input.nextLine();
            cliente1.visualizzaErrori(con);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
