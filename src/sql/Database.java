package sql;

import java.sql.*;

public class Database {
    private Connection connect;
    private Statement statement;
    //private PreparedStatement preparedStatement;
    private ResultSet resultSet;

    public void pripojeni() throws Exception {
        try {
            //Class.forName("com.mysql.cj.jdbc.Driver");
            connect = DriverManager.getConnection("jdbc:mysql://139.59.139.93:3306/pocasi?user=app&password=pocasi");
            statement = connect.createStatement();
            
//            // Result set get the result of the SQL query
//            resultSet = statement
//                    .executeQuery("select * from pocasi.test");
//            writeResultSet(resultSet);

//            // PreparedStatements can use variables and are more efficient
//            preparedStatement = connect
//                    .prepareStatement("insert into  feedback.comments values (default, ?, ?, ?, ? , ?, ?)");
//            // "myuser, webpage, datum, summary, COMMENTS from feedback.comments");
//            // Parameters start with 1
//            preparedStatement.setString(1, "Test");
//            preparedStatement.setString(2, "TestEmail");
//            preparedStatement.setString(3, "TestWebpage");
//            preparedStatement.setDate(4, new java.sql.Date(2009, 12, 11));
//            preparedStatement.setString(5, "TestSummary");
//            preparedStatement.setString(6, "TestComment");
//            preparedStatement.executeUpdate();
//
//            preparedStatement = connect
//                    .prepareStatement("SELECT myuser, webpage, datum, summary, COMMENTS from feedback.comments");
//            resultSet = preparedStatement.executeQuery();
//            writeResultSet(resultSet);
//
//            // Remove again the insert comment
//            preparedStatement = connect
//            .prepareStatement("delete from feedback.comments where myuser= ? ; ");
//            preparedStatement.setString(1, "Test");
//            preparedStatement.executeUpdate();
//
            resultSet = statement
            .executeQuery("select * from pocasi.test");
            writeMetaData(resultSet);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            close();
        }

    }

    private void writeMetaData(ResultSet resultSet) throws SQLException {

        System.out.println("The columns in the table are: ");

        System.out.println("Table: " + resultSet.getMetaData().getTableName(1));
        for  (int i = 1; i<= resultSet.getMetaData().getColumnCount(); i++){
            System.out.println("Column " +i  + " "+ resultSet.getMetaData().getColumnName(i));
        }
    }



    // You need to close the resultSet
    private void close() {
        try {
            if (resultSet != null) {
                resultSet.close();
            }

            if (statement != null) {
                statement.close();
            }

            if (connect != null) {
                connect.close();
            }
        } catch (Exception e) {

        }
    }
    
    public static void main(String[] args) throws Exception {
        Database d = new Database();
        d.pripojeni();
    }

}