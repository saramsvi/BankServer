package server.Configurations;



import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static server.Configurations.ConstUser.USERS_TABLE;
import static server.Configurations.ConstUser.USERS_USERNAME;
import static server.Configurations.DataBaseHandler.getDbConnection;

public class UserHandler {
    private static PreparedStatement prSt;


    public static boolean checkLogin(Connection connection, String login, String password){
        ResultSet resultSet;
        String query = "select * from " + USERS_TABLE + " where Username = ? and Password = ?";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, login);
            preparedStatement.setString(2, password);
            resultSet = preparedStatement.executeQuery();
            resultSet.next();
            if(login.equals(resultSet.getString("Username"))){
                return true;
            }else{
                return  false;
            }

        } catch (SQLException throwables) {
            return false;
        }
    }



    public static boolean addUser(Connection connection, String username, String name, String surname, String password, String balance)
    {
        String insert = "INSERT INTO " + USERS_TABLE + "(" +
                ConstUser.USERS_USERNAME +","+
                ConstUser.USERS_NAME +","+
                ConstUser.USERS_SURNAME+","+
                ConstUser.USERS_PASSWORD +","+
                ConstUser.USERS_BALANCE +")"+
                "VALUES(?,?,?,?,?)";
        try{
            PreparedStatement prSt = getDbConnection().prepareStatement(insert);
            prSt.setString(1, username);
            prSt.setString(2, name);
            prSt.setString(3, surname);
            prSt.setString(4, password);
            prSt.setInt(5, Integer.valueOf(balance));
//добавить данные
            prSt.executeUpdate();
        }
        catch (SQLException e){ return false;}
        catch (ClassNotFoundException e){ return false; }
        return true;
    }


    public static String checkRepeat(Connection connection, String login){
        ResultSet resultSet;
        String log;
        String flag = "0";

        String select = " SELECT * FROM  "+ USERS_TABLE + "WHERE" + ConstUser.USERS_USERNAME +"=?" ;

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(select);
            preparedStatement.setString(1, login);
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                log = resultSet.getString("Username");
                if(log == login){
                    flag = "1";
                    return flag;
                }
            }
        } catch (SQLException e) {
            System.out.println("login exception");
            e.printStackTrace();
        }
        return flag;
    }



     public static List<String> getList(Connection connection){
        List<String> list=new ArrayList<>();
        ResultSet resSet= null;
        //checking all users
        String query =" SELECT * FROM Users_info ORDER BY idUser";
        try{
            prSt = connection.prepareStatement(query);
            //получить данные
            resSet=prSt.executeQuery();
            while (resSet.next()){
                StringBuilder str = new StringBuilder();
                        str.append(resSet.getInt("idUser")).append("|").
                                append(resSet.getString("Username")).append("|").
                        append(resSet.getString("Name")).append("|").
                        append(resSet.getString("Surname")).append("|").
                        append(resSet.getString("Password")).append("|").
                        append(resSet.getInt("Balance"));

                list.add(str.toString());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally{
            return list;
        }
    }


    public static boolean delete(Connection connection,String id){

        String delete = " DELETE FROM Users_info WHERE idUser=? ";
        try {
            prSt = connection.prepareStatement(delete);
            prSt.setInt(1,Integer.valueOf(id));
            prSt.executeUpdate(delete);
        } catch (SQLException e) { return false; }

        return true;
    }


    public static boolean update(Connection connection, String id,String username,String name, String surname,  String password,
                                 String balance){
        String update = " UPDATE Users_info SET Username=?, Name=?, Surname=?, Password=?,Balance=? WHERE idUser =?";
        try {
            prSt = connection.prepareStatement(update);
            prSt.setString(1, username);
            prSt.setString(2, name);
            prSt.setString(3, surname);
            prSt.setString(4, password);
            prSt.setString(5, balance);
            prSt.setInt(6, Integer.valueOf(id));
            prSt.executeUpdate();
        } catch (SQLException e) {
            return false;
        }
        return true;
    }


    public static List<String> searchSurname(Connection connection, String surname){
        List<String> users = new ArrayList<>();
        String query = "SELECT * FROM "+ USERS_TABLE+ " WHERE "+ ConstUser.USERS_SURNAME+ " LIKE '" + surname + "'";
        System.out.println(surname);
        try {
            prSt = connection.prepareStatement(query);
            ResultSet rs = prSt.executeQuery(query);

            while(rs.next()){
                StringBuilder str = new StringBuilder();
                str.append(rs.getInt("idUser")).append("|").
                        append(rs.getString("Userame")).append("|").
                        append(rs.getString("Name")).append("|").
                        append(rs.getString("Surname")).append("|").
                        append(rs.getString("Password")).append("|").
                        append(rs.getString("Balance"));
                users.add(str.toString());
            }
        } catch (SQLException e) {
            System.out.println(surname);
            e.printStackTrace();
        }finally{
            return users;
        }
    };

    public static List<String>  getIdUser(Connection connection){
        ResultSet resultSet;
        List<String> list = new ArrayList<>();
        String query = "select idUser, Name, Surname from " + USERS_TABLE;

        try {
            prSt = connection.prepareStatement(query);
            resultSet=prSt.executeQuery();

            while (resultSet.next()){
                StringBuilder str = new StringBuilder();
                str.append(resultSet.getInt("idUser")).append(" ").
                        append(resultSet.getString("Name")).append(" ").
                        append(resultSet.getString("Surname"));

                list.add(str.toString());
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return list;
    }


}
