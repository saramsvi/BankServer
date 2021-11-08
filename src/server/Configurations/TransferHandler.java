package server.Configurations;

import com.sun.jdi.IntegerValue;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static server.Configurations.ConstUser.USERS_TABLE;
import static server.Configurations.DataBaseHandler.getDbConnection;

public class TransferHandler {

    private static PreparedStatement prSt;
    public static boolean addNewTR(Connection connection, String username,String cardnum, String Date,String amount)
    {
        String insert = "INSERT INTO send (Senderid,CardNumber,Date,Amount) VALUES(?,?,?,?)";

        try{
            PreparedStatement prStt = getDbConnection().prepareStatement(insert);
            prStt.setInt(1, Integer.valueOf(username));
            prStt.setString(2, cardnum);
            prStt.setString(3, Date);
            prStt.setString(4, amount);

            prStt.executeUpdate();
        } catch (SQLException e){
            return false;
        } catch (ClassNotFoundException e)
        {
            return false;
        }
        return true;
    }
    public static List<String> getListTR(Connection connection){

        List<String> list=  new ArrayList<>();
        ResultSet resSet= null;
        String query =" SELECT * FROM  send ORDER  BY Transferid";
        try{
            prSt = connection.prepareStatement(query);
            ResultSet rs = prSt.executeQuery(query);
            //получить данные
            resSet=prSt.executeQuery();
            while (resSet.next()){
                StringBuilder str = new StringBuilder();
                str.append(resSet.getInt("Transferid")).append("|").
                        append(resSet.getInt("Senderid")).append("|").
                        append(resSet.getString("CardNumber")).append("|").
                        append(resSet.getString("Date")).append("|").
                        append(resSet.getString("Amount"));
                ;

                list.add(str.toString());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally{
            return list;
        }
    }

    public static boolean update(Connection connection,String id,String cardnumb, String date,  String amount){
        String update = " UPDATE send SET CardNumber=?, Date=? , Amount=? WHERE Senderid=?";
        try {
            prSt = connection.prepareStatement(update);
            prSt.setString(1, cardnumb);
            prSt.setString(2, date);
            prSt.setString(3, amount);
            prSt.setInt(4, Integer.valueOf(id));
            prSt.executeUpdate();
        } catch (SQLException e) {
            return false;
        }
        return true;
    }


    public static List<String> searchSurname(Connection connection, String surname){
        List<String> users = new ArrayList<>();
        String query = "SELECT * FROM "+ ConstTransfer.TRANSFER_TABLE+ " WHERE "+
                ConstTransfer.TRANSFER_IDSENDER+ " LIKE '" + surname + "'";
        System.out.println(surname);
        try {
            prSt = connection.prepareStatement(query);
            ResultSet rs = prSt.executeQuery(query);

            while(rs.next()){
                StringBuilder str = new StringBuilder();
                str.append(rs.getInt("Transferid")).append("|").
                        append(rs.getString("Senderid")).append("|").
                        append(rs.getString("CardNumber")).append("|").
                        append(rs.getString("Date")).append("|").
                                append(rs.getString("Amount"));
                users.add(str.toString());
            }
        } catch (SQLException e) {
            System.out.println(surname);
            e.printStackTrace();
        }finally{
            return users;
        }
    };

    public static boolean delete(Connection connection, String id){

        String delete = " DELETE FROM send WHERE Transferid=? ";
        try {
            prSt = connection.prepareStatement(delete);
            prSt.setInt(1,Integer.valueOf(id));
            prSt.executeUpdate(delete);
        } catch (SQLException e) { return false; }

        return true;
    }
    public static List<String> search(Connection connection, String id){
        List<String> cars = new ArrayList<>();
        String query = " SELECT * FROM send WHERE Senderid=? " ;

        try {
            prSt = connection.prepareStatement(query);
            ResultSet rs = prSt.executeQuery(query);

            while(rs.next()){
                StringBuilder str = new StringBuilder();
                str.append(rs.getInt("Transferid")).append("|").
                        append(rs.getString("Senderid")).append("|").
                        append(rs.getString("CardNumber")).append("|").
                        append(rs.getString("Date")).append("|").
                                append(rs.getString("Amount")
                                );
                cars.add(str.toString());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally{
            return cars;
        }
    }
}
