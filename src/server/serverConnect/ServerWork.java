package server.serverConnect;

import server.Configurations.*;


import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.sql.SQLException;

public class ServerWork implements Runnable{

    private Socket clientSocket;
    private ObjectOutputStream output = null;
    private ObjectInputStream input = null;

    public ServerWork(Socket clientSocket){
        this.clientSocket = clientSocket;
    }

    @Override
    public void run() {
        try {
            output = new ObjectOutputStream(clientSocket.getOutputStream());
            input = new ObjectInputStream(clientSocket.getInputStream());

            while(true){
                try{
                    String task = input.readObject().toString();
                    output.writeObject(chooseTask(task));
                }catch (SocketException socketException){
                    System.out.println("Client " + clientSocket + "exit!!!");
                    break;
                }
            }
        } catch (IOException | ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    private Object chooseTask(String task) throws SQLException, ClassNotFoundException {
        String[] strTask = task.split("\\|", 2);
        String[] parameters;
        Object res = false;
        System.out.println("Enter string: " + task);
        switch(strTask[0]) {

            case "checkLogin":
                parameters = task.split("\\|", 3);
                res = UserHandler.checkLogin(DataBaseHandler.getDbConnection(), parameters[1], parameters[2]);
                break;
            case "addUser":
                parameters = task.split("\\|", 6);
                res = UserHandler.addUser(DataBaseHandler.getDbConnection(), parameters[1], parameters[2], parameters[3], parameters[4], parameters[5]);
                break;
            case "checkLogRep":
                parameters = task.split("\\|", 2);
                res = UserHandler.checkRepeat(DataBaseHandler.getDbConnection(), parameters[1]);
                break;
            case "addClient":
                parameters = task.split("\\|", 7);
                res = UserHandler.addUser(DataBaseHandler.getDbConnection(), parameters[1], parameters[2], parameters[3],
                        parameters[4], parameters[5]);
                break;

            case "updateUser":
                parameters = task.split("\\|", 7);
                res = UserHandler.update(DataBaseHandler.getDbConnection(), parameters[1], parameters[2], parameters[3],
                        parameters[4], parameters[5], parameters[6]);
                break;
            case "searchUser":
                parameters = task.split("\\|", 2);
                res = UserHandler.searchSurname(DataBaseHandler.getDbConnection(), parameters[1]);
                break;
            case "getIdUser":
                res = UserHandler.getIdUser(DataBaseHandler.getDbConnection());
                break;
            case "addLoan":
                parameters = task.split("\\|", 9);
                res = LoanerHandler.addNewLoan(DataBaseHandler.getDbConnection(), parameters[1],  parameters[2],parameters[3],
                        parameters[4],parameters[5],parameters[6],parameters[7],parameters[8]);
                break;
            case "addBS":
                parameters = task.split("\\|", 3);
                res = BankStatementHandler.addNewBS(DataBaseHandler.getDbConnection(), parameters[1], parameters[2]);
                break;
            case "addTR":
                parameters = task.split("\\|", 5);
                res = TransferHandler.addNewTR(DataBaseHandler.getDbConnection(), parameters[1], parameters[2],parameters[3], parameters[4]);
                break;
            case "updateTR":
                parameters = task.split("\\|", 5);
                res = TransferHandler.update(DataBaseHandler.getDbConnection(), parameters[1], parameters[2],parameters[3],parameters[4]);
                break;
            case "updateLoan":
                parameters = task.split("\\|", 9);
                res = LoanerHandler.update(DataBaseHandler.getDbConnection(), parameters[1], parameters[2], parameters[3],
                        parameters[4], parameters[5], parameters[6], parameters[7], parameters[8]);
                break;
            case "updateBS":
                parameters = task.split("\\|", 3);
                res = BankStatementHandler.update(DataBaseHandler.getDbConnection(), parameters[1], parameters[2]);
                break;
            case "getListBS":
                parameters = task.split("\\|", 1);
                res = BankStatementHandler.getListBS(DataBaseHandler.getDbConnection());
                break;
            case "getListTR":
                parameters = task.split("\\|", 1);
                res = TransferHandler.getListTR(DataBaseHandler.getDbConnection());
                break;
            case "getListL":
                parameters = task.split("\\|", 1);
                res = LoanerHandler.getListLoaner(DataBaseHandler.getDbConnection());
                break;
            case "getListUser":
                parameters = task.split("\\|", 1);
                res = UserHandler.getList(DataBaseHandler.getDbConnection());
                break;
            case "deleteUser":
                parameters = task.split("\\|", 2);
                res = UserHandler.delete(DataBaseHandler.getDbConnection(),parameters[1]);
                break;
            case "deleteLoan":
                parameters = task.split("\\|", 2);
                res = LoanerHandler.delete(DataBaseHandler.getDbConnection(),parameters[1]);
                break;
            case "deleteTR":
                parameters = task.split("\\|", 2);
                res = TransferHandler.delete(DataBaseHandler.getDbConnection(),parameters[1]);
                break;
            case "deleteBS":
                parameters = task.split("\\|", 2);
                res = BankStatementHandler.delete(DataBaseHandler.getDbConnection(),parameters[1]);
                break;


            case "searchBS":
                parameters = task.split("\\|", 3);
                res = BankStatementHandler.search(DataBaseHandler.getDbConnection(), parameters[1]);
                break;
        }
        System.out.println("Exit string: " + res);
        return res;
    }
}























