import java.sql.Array;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Scanner;

public class Cliente {
    public String codc, nome_cliente, citta;
    public boolean clienteEsiste = false;

    public ArrayList<Errore> listaErrori;
    Statement stmt = null;
    String sql;
    ResultSet rs;

    public Cliente() {
        codc = "";
        nome_cliente = "";
        citta = "";
    }

    public void visualizzaErrori(Connection cn) throws Exception {
        try {
            sql = "SELECT errore.*, cliente.nome_cliente FROM errore, cliente WHERE errore.codc = cliente.codc AND errore.codc = '" + this.codc + "' ;";
            rs = stmt.executeQuery(sql);
            this.listaErrori = new ArrayList<Errore>(); // inizializzazione arrayList
            while (rs.next()) {  // creiamo un nuovo oggetto Esame, lanciandogli tutti i campi oggetto della query
                Errore e = new Errore(rs.getString("errore"), rs.getString("codc"), rs.getString("descrizione"), rs.getString("costo"), rs.getString("nome_cliente"));
                System.out.println(e);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public String toString() {
        return "Cliente: " +
                "Code:'" + codc + '\'' +
                ", Full Name:'" + nome_cliente + '\'' +
                ", City: '" + citta + '\'' +
                ';';
    }

    public void removeClient(Connection cn) throws Exception {
        try {
            stmt = cn.createStatement();
            sql = "SELECT codc FROM cliente WHERE codc = '" + this.codc + "'";
            rs = stmt.executeQuery(sql);
            if (!rs.next()) {
                System.out.println("Cliente non trovato");
            } else {
                sql = "DELETE FROM cliente WHERE codc = '" + this.codc + "'";
                int result = stmt.executeUpdate(sql);
                if (result > 0) {
                    System.out.println("Cliente eliminato con successo");
                } else {
                    System.out.println("Nessun cliente eliminato");
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    public void insertClient(Connection cn) throws Exception {
        try {
            stmt = cn.createStatement();
            sql = " SELECT cliente.codc FROM cliente WHERE cliente.codc = '" + this.codc + "'";
            rs = stmt.executeQuery(sql);
            if (!rs.next()) {
                sql = "INSERT INTO cliente(codc, nome_cliente, citta) VALUES ('" + this.codc + "'" + ",'" + this.nome_cliente + "' ," + "'" + this.citta + "');";
                stmt.executeUpdate(sql);
            } else {
                System.out.println("Cliente già esistente, cambia codice cliente ");
                sql = "UPDATE cliente SET  citta = '" + this.citta + "' , nome_cliente = '" + this.nome_cliente + "' WHERE codc = '" + this.codc + "' ;";
                stmt.executeUpdate(sql);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());

        }
    }

    public void chooseError(Connection cn, String errore) throws Exception {
        try {
            sql = "SELECT cliente.codc, cliente.nome_cliente, errore.errore FROM cliente, errore WHERE cliente.codc = errore.codc AND errore.errore LIKE '" + errore +
            "' ;";
            rs = stmt.executeQuery(sql);


        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public Cliente(Connection cn, String codice_cliente) throws Exception {
        try {

            stmt = cn.createStatement();
            sql = "SELECT cliente.* FROM cliente WHERE cliente.codc = '" + codice_cliente + "' ;";
            rs = stmt.executeQuery(sql);
            if (rs.next()) {
                this.codc = rs.getString("codc");
                this.nome_cliente = rs.getString("nome_cliente");
                this.citta = rs.getString("citta");
                this.clienteEsiste = true;
            } else {
                codc = "";
                nome_cliente = "";
                citta = "";
                clienteEsiste = false;
            }
            rs.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
