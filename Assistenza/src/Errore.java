import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

public class Errore {
    public String errore, codc, descrizione, costo, nome_cliente;


    public Errore(String errore, String codc, String descrizione, String costo, String nome_cliente) {
        this.errore = errore;
        this.codc = codc;
        this.descrizione = descrizione;
        this.costo = costo;
        this.nome_cliente = nome_cliente;
    }


    @Override
    public String toString() {
        return "Lista Errori {" +
                "Errore= '" + errore + '\'' +
                ",Codice Cliente = '" + codc + '\'' +
                ", Descrizione= '" + descrizione + '\'' +
                ", Costo= '" + costo + '\'' +
                ", Nome del Cliente = '" + nome_cliente + '\'' +
                '}';
    }
}
