package com.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;
public class App 
{
    private final String url="jdbc:postgresql://localhost:5432/stud24_db";
    private final String user="postgres";
    private final String password="112263kenedy";
    
    public Connection connect_filmdb(){
        Connection connection=null;
        Statement statement=null;
        try{
        connection=DriverManager.getConnection(url, user, password);
        System.out.println("Verbindung zum Server erfolgreich hergestellt");
        statement=connection.createStatement();
        String erhaltenAnzahlVonFilme="SELECT COUNT(*) FROM filmdb.film";
        ResultSet rs=statement.executeQuery(erhaltenAnzahlVonFilme);
        while(rs.next()){
            int anzahl=rs.getInt("count");
            System.out.println("Anzahl des Filme: "+anzahl);
        }
        String erhaltenTitelUndJahr="SELECT * FROM (SELECT film.titel,film.jahr FROM filmdb.film ORDER BY film.jahr DESC LIMIT 3)"+
        " AS query ORDER BY query.jahr";
        ResultSet rs2=statement.executeQuery(erhaltenTitelUndJahr);
        while(rs2.next()){
            int jahr=rs2.getInt("jahr");
            String titel=rs2.getString("titel");
            System.out.println(titel+" erschien im Jahr "+jahr);
        }
        rs.close();
        statement.close();
        connection.close();
        }
        catch(SQLException exception){
            System.err.println(exception.getMessage());
        }
        return connection;
    }

    public Connection connect_ausleihe(){
        Connection connection=null;
        Statement statement=null;
        try{
        connection=DriverManager.getConnection(url, user, password);
        System.out.println("Verbindung zum Server erfolgreich hergestellt");
        String leserid="";
        Scanner scanner=new Scanner(System.in);
        System.out.print("Eingeben das Leser ID:");
        leserid=scanner.nextLine();
        System.out.println("Das Leser ID ist "+leserid);
        statement=connection.createStatement();
        String erhaltenBuchTitel="SELECT buchex.titel FROM ausleihe.buchex INNER JOIN ausleihe.ausleihe ON ausleihe.isbn=buchex.isbn WHERE ausleihe.leserid="+leserid;
        ResultSet rs=statement.executeQuery(erhaltenBuchTitel);
        while(rs.next()){
            String titel=rs.getString("titel");
            System.out.println("Titel der Büch von leser mit ID "+leserid+" "+titel);
        }

        //Zweite Programme
        String leser_id="";
        String isbn="";
        System.out.print("Eingeben das Leser ID: ");
        leser_id=scanner.nextLine();
        System.out.println();
        System.out.print("Eingeben das ISBN : ");
        isbn=scanner.nextLine();
        String eintragAusleihe="INSER INTO ausleihe.ausleihe VALUES()";

        scanner.close();
        rs.close();
        statement.close();
        connection.close();
        }
        catch(SQLException exception){
            System.err.println(exception.getMessage());
        }
        return connection;
    }
    public static void main( String[] args )
    {
        App app=new App();
       // app.connect_filmdb();
        app.connect_ausleihe();
    }
}
