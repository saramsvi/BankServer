package server.Configurations;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static server.Configurations.DataBaseHandler.getDbConnection;

public class BankStatementHandler {
    private static PreparedStatement prSt;
    public static boolean addNewBS(Connection connection, String username,String Date)
    {
        String insert = "INSERT INTO " + ConstBankStatement.BS_TABLE+ "(" + ConstBankStatement.BS_USERNAME+","+
                ConstBankStatement.BS_DATE  +")"+
                "VALUES(?,?)";
        try{
            PreparedStatement prStt = getDbConnection().prepareStatement(insert);
            prStt.setString(1, username);
            prStt.setString(2, Date);

            prStt.executeUpdate();
        } catch (SQLException e){
            return false;
        } catch (ClassNotFoundException e)
        {
            return false;
        }
        return true;
    }

    public static List<String> getListBS(Connection connection){
        List<String> list=new ArrayList<>();
        ResultSet resSet= null;
        String query =" SELECT * FROM report ORDER BY idreport ";
        try{
            prSt = connection.prepareStatement(query);
            ResultSet rs = prSt.executeQuery(query);
            resSet=prSt.executeQuery();
            while (resSet.next()){
                StringBuilder str = new StringBuilder();
                str.append(resSet.getInt("idreport")).append("|").
                        append(resSet.getInt("iduser")).append("|").
                        append(resSet.getString("date"));
                list.add(str.toString());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally{
            return list;
        }
    }

    public static boolean update(Connection connection,String id,String date){
        String update = "UPDATE report SET date=? WHERE iduser=?";
        try {
            prSt = connection.prepareStatement(update);
            prSt.setString(1, date);
            prSt.setInt(2, Integer.valueOf(id));
            prSt.executeUpdate();
        } catch (SQLException e) {
            return false;
        }
        return true;
    }
    public static boolean delete(Connection connection, String id) {

        String delete = " DELETE FROM report WHERE idreport=? ";

        try {
            prSt = connection.prepareStatement(delete);
            prSt.setInt(1, Integer.valueOf(id));
            prSt.executeUpdate(delete);
        } catch (SQLException e) {
            return false;
        }
        return true;
    }

        public static List<String> search(Connection connection, String id){
            List<String> BS = new ArrayList<>();
            String search = " SELECT * FROM report WHERE iduser=? ";

            try {
                prSt = connection.prepareStatement(search);
                ResultSet rs = prSt.executeQuery(search);

                while (rs.next()) {
                    StringBuilder str = new StringBuilder();
                    str.append(rs.getInt("idreport")).append("|").
                            append(rs.getInt("iduser")).append("|").
                            append(rs.getString("date")).append("|");
                    BS.add(str.toString());
                }
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                return BS;
            }
        }
    }


