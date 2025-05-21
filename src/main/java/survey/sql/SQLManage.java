package survey.sql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SQLManage {

    Connection con;

    public SQLManage() throws SQLException {
        String url = "jdbc:mysql://localhost:3306/survey";
        String usr = "admin";
        String pass = "password";
        con = DriverManager.getConnection(url, usr, pass);
    }

    public void newUser(String name, String uname, String pass) throws SQLException {
        String str = "INSERT INTO actors(fname, uname, pass) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = con.prepareStatement(str)) {
            stmt.setString(1, name);
            stmt.setString(2, uname);
            stmt.setString(3, pass);
            stmt.executeUpdate();
        }
    }

    public int authUser(String uname, String pass) throws SQLException {
        String str = "SELECT * FROM actors WHERE uname = ?";
        ResultSet rst;
        try (PreparedStatement stmt = con.prepareStatement(str)) {
            stmt.setString(1, uname);
            rst = stmt.executeQuery();
            if (!rst.next()) return -1;
            return rst.getString("pass").equals(pass) ? rst.getInt("id") : 0;
        }
    }

    public void newQuestion(String code, String question, String op1, String op2, String op3, String op4) throws SQLException {
        String str = "INSERT INTO questions(surveycode, question, option1, option2, option3, option4) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = con.prepareStatement(str)) {
            stmt.setString(1, code);
            stmt.setString(2, question);
            stmt.setString(3, op1);
            stmt.setString(4, op2);
            stmt.setString(5, op3);
            stmt.setString(6, op4);
            stmt.executeUpdate();
        }

    }

    public void userQuestionAdd(int id, String surveycode) throws SQLException {
        String str = "INSERT INTO userQuestions(id, surveycode, total) VALUES (?, ?, 0)";
        try (PreparedStatement stmt = con.prepareStatement(str)) {
            stmt.setInt(1, id);
            stmt.setString(2, surveycode);
            stmt.executeUpdate();
        }
    }

    public void answerUpdt(String surveycode, int qno, int option) throws SQLException {
        String str = "INSERT INTO surveyquestions(surveycode, qno, opno) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = con.prepareStatement(str)) {
            stmt.setString(1, surveycode);
            stmt.setInt(2, qno);
            stmt.setInt(3, option);
            stmt.executeUpdate();
        }

    }

    public ResultSet getQuestions(String surveycode) throws SQLException {
        String str = "SELECT * FROM questions WHERE surveycode = ?";
        PreparedStatement stmt = con.prepareStatement(str);
        stmt.setString(1, surveycode);
        return stmt.executeQuery();
    }

    public ResultSet surveys(int id, String search) throws SQLException {
        String str = "SELECT * FROM userQuestions WHERE id = ? AND surveycode LIKE ?";
        PreparedStatement stmt = con.prepareStatement(str);
        stmt.setInt(1, id);
        stmt.setString(2, "%" + search + "%");
        return stmt.executeQuery();


    }

    public void addTotal() throws SQLException {
        String str = "UPDATE userQuestions SET total = total + 1";
        try (PreparedStatement stmt = con.prepareStatement(str)) {
            stmt.executeUpdate();
        }

    }

    public boolean check(String search) throws SQLException {
        String str = "SELECT * FROM userQuestions WHERE surveycode = ?";
        ResultSet rst;
        try (PreparedStatement stmt = con.prepareStatement(str)) {
            stmt.setString(1, search);
            rst = stmt.executeQuery();
            return rst.next();
        }
    }

    public void removeSurvey(String surveycode) throws SQLException {
        String deleteQuestionQuery = "DELETE FROM questions WHERE surveycode = ?";
        try (PreparedStatement stmt = con.prepareStatement(deleteQuestionQuery)) {
            stmt.setString(1, surveycode);
            stmt.executeUpdate();
        }

        String deleteSurveyQuestionQuery = "DELETE FROM surveyquestions WHERE surveycode = ?";
        try (PreparedStatement stmt2 = con.prepareStatement(deleteSurveyQuestionQuery)) {
            stmt2.setString(1, surveycode);
            stmt2.executeUpdate();
        }

        String deleteUserQuestionQuery = "DELETE FROM userQuestions WHERE surveycode = ?";
        try (PreparedStatement stmt3 = con.prepareStatement(deleteUserQuestionQuery)) {
            stmt3.setString(1, surveycode);
            stmt3.executeUpdate();
        }
    }

    public int getCount(String surveycode, int qno, int op) throws SQLException {
        String str = "SELECT COUNT(opno) AS cnt FROM surveyquestions WHERE surveycode = ? AND qno = ? AND opno = ?";
        ResultSet rst;
        try (PreparedStatement stmt = con.prepareStatement(str)) {
            stmt.setString(1, surveycode);
            stmt.setInt(2, qno + 1);  // assuming qno is 0-indexed
            stmt.setInt(3, op);
            rst = stmt.executeQuery();
            return rst.next() ? rst.getInt("cnt") : 0;
        }
    }
}