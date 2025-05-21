package survey.page;

import survey.sql.SQLManage;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;

import static survey.constant.Constant.FONT_TYPE;
import static survey.constant.Constant.WARNING_TITLE;

public class Login {

    int id;

    public void loginView() throws SQLException {
        SQLManage manage = new SQLManage();
        JFrame frame = new JFrame();
        frame.setSize(500, 450);
        frame.setLayout(null);
        frame.setLocationRelativeTo(null);

        JLabel heading = new JLabel("HỆ THỐNG KHẢO SÁT");
        heading.setBounds(25, 50, 450, 50);
        heading.setHorizontalAlignment(SwingConstants.CENTER);
        heading.setFont(new Font(FONT_TYPE, Font.BOLD, 40));
        frame.add(heading);

        JLabel uname = new JLabel("Tên người dùng : ");
        uname.setBounds(50, 130, 150, 50);
        frame.add(uname);

        JTextField name = new JTextField();
        name.setBounds(50, 170, 350, 30);
        frame.add(name);

        JLabel upass = new JLabel("Mật khẩu : ");
        upass.setBounds(50, 200, 150, 50);
        frame.add(upass);

        JPasswordField pass = new JPasswordField();
        pass.setBounds(50, 240, 350, 30);
        frame.add(pass);

        JButton login = new JButton("ĐĂNG NHẬP");
        login.setBounds(100, 300, 120, 40);
        frame.add(login);
        login.addActionListener(e -> {
            String username = name.getText();
            String password = pass.getText();
            if (username.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Thông tin đăng nhập chưa đầy đủ", WARNING_TITLE, JOptionPane.WARNING_MESSAGE);
            } else {
                try {
                    SQLManage manage1 = new SQLManage();
                    id = manage1.authUser(username, password);
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
                if (id == -1) {
                    JOptionPane.showMessageDialog(frame, "Kiểm tra lại thông tin đăng nhập", WARNING_TITLE, JOptionPane.WARNING_MESSAGE);
                } else if (id == 0) {
                    JOptionPane.showMessageDialog(frame, "Kiểm tra lại thông tin đăng nhập", WARNING_TITLE, JOptionPane.WARNING_MESSAGE);
                } else {
                    MainPage mainPage = new MainPage();
                    try {
                        mainPage.mainPageView(id);
                    } catch (SQLException e1) {
                        e1.printStackTrace();
                    }
                    frame.dispose();
                }
            }
        });

        JButton signUp = new JButton("ĐĂNG KÝ");
        signUp.setBounds(250, 300, 100, 40);
        frame.add(signUp);
        signUp.addActionListener(e -> {
            SignUp signup = new SignUp();
            signup.signUpView();
        });

        JButton attend = new JButton("THAM DỰ KHẢO SÁT ẨN DANH");
        attend.setBounds(100, 350, 250, 40);
        frame.add(attend);
        attend.addActionListener(e -> {
            String surveyCode = JOptionPane.showInputDialog("Điền mã khảo sát : ");
            try {
                if (surveyCode.length() == 5) {
                    if (manage.check(surveyCode)) {
                        Guest guest = new Guest();
                        guest.guestView(surveyCode);
                    } else {
                        JOptionPane.showMessageDialog(frame, "Không tìm thấy khảo sát", WARNING_TITLE, JOptionPane.WARNING_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(frame, "Mã khảo sát không hợp lệ", WARNING_TITLE, JOptionPane.WARNING_MESSAGE);
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        });

        frame.setVisible(true);
    }
}