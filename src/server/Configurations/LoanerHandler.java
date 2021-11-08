package server.Configurations;



import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static server.Configurations.DataBaseHandler.getDbConnection;

public class LoanerHandler {
    private static PreparedStatement prSt;

    public static boolean addNewLoan(Connection connection,String username, String reason,String amount,String time,String occupation,
                              String salary,String mstatus,String kids)
    {
        String insertt = "INSERT INTO " + ConstLoaner.LOAN_TABLE+ "(" +
                ConstLoaner.LOAN_USERNAME +","+
                ConstLoaner.LOAN_REASON+","+
                ConstLoaner.LOAN_AMOUNT+","+
                ConstLoaner.LOAN_TIME+","+
                ConstLoaner.LOAN_OCCUPATION+","+
                ConstLoaner.LOAN_SALARY+","+
                ConstLoaner.LOAN_MSTATUS+","+
                ConstLoaner.LOAN_KIDS+")"+
                "VALUES(?,?,?,?,?,?,?,?)";
        try{
            PreparedStatement prStt = getDbConnection().prepareStatement(insertt);
            prStt.setInt(1, Integer.valueOf(username));
            prStt.setString(2, reason);
            prStt.setInt(3, Integer.valueOf(amount));
            prStt.setString(4, time);
            prStt.setString(5, occupation);
            prStt.setInt(6, Integer.valueOf(salary));
            prStt.setString(7, mstatus);
            prStt.setInt(8, Integer.valueOf(kids));

//добавить данные
            prStt.executeUpdate();
        } catch (SQLException e){
            return false;
        } catch (ClassNotFoundException e)
        {
            return false;
        }
        return true;
    }
    public static List<String> getListLoaner(Connection connection){
        List<String> list=new ArrayList<>();
        ResultSet resSet= null;
        String query =" SELECT * FROM  Loans_Table  ORDER BY  idLoan ";
        try{
            prSt = connection.prepareStatement(query);
            ResultSet rs = prSt.executeQuery(query);
            //получить данные
            resSet=prSt.executeQuery();
            while (resSet.next()){
                StringBuilder str = new StringBuilder();
                str.append(resSet.getInt("idLoan")).append("|").
                        append(resSet.getInt("Username")).append("|").
                        append(resSet.getString("Reason")).append("|").
                        append(resSet.getInt("Amount_Needed")).append("|").
                        append(resSet.getString("Repayment_Time")).append("|").
                        append(resSet.getString("Occupation")).append("|").
                        append(resSet.getInt("Salary")).append("|").
                        append(resSet.getString("Marital_Status")).append("|").
                        append(resSet.getInt("Number_of_Kids"));

                list.add(str.toString());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally{
            return list;
        }
    }
    public static boolean delete(Connection connection, String id){

        String delete = " DELETE FROM Loans_Table WHERE idLoan=? ";
        try {
            prSt = connection.prepareStatement(delete);
            prSt.setInt(1,Integer.valueOf(id));
            prSt.executeUpdate(delete);
        } catch (SQLException e) { return false; }

        return true;
    }

    public static boolean update(Connection connection,String id,String reason, String amount,  String time,String occupation,
                                 String salary,String Mstatus, String Kids){
        String update = " UPDATE Loans_Table SET Reason=? , Amount_Needed=? , Repayment_Time=? , Occupation=?  , Salary=? , Marital_Status=? ,Number_of_Kids=? WHERE Username=? ";

        try {
            prSt = connection.prepareStatement(update);
            prSt.setString(1, reason);
            prSt.setString(2, amount);
            prSt.setString(3, time);
            prSt.setString(4, occupation);
            prSt.setString(5, salary);
            prSt.setString(6, Mstatus);
            prSt.setString(7, Kids);
            prSt.setInt(8, Integer.valueOf(id));
            prSt.executeUpdate();
        } catch (SQLException e) {
            return false;
        }
        return true;
    }

}


