package survey.page;

import survey.sql.SQLManage;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;

import static survey.constant.Constant.FONT_TYPE;
import static survey.constant.Constant.INFO_TITLE;
import static survey.constant.Constant.WARNING_TITLE;

public class SignUp {
    public void signUpView() {
        JFrame frame = new JFrame();
        frame.setSize(450, 450);
        frame.setLayout(null);
        frame.setLocationRelativeTo(null);

        JLabel heading = new JLabel("HỆ THỐNG KHẢO SÁT");
        heading.setBounds(0, 50, 450, 50);
        heading.setHorizontalAlignment(SwingConstants.CENTER);
        heading.setFont(new Font(FONT_TYPE, Font.BOLD, 40));
        frame.add(heading);

        JLabel fName = new JLabel("Họ tên : ");
        fName.setBounds(50, 120, 150, 50);
        frame.add(fName);

        JTextField fNameField = new JTextField();
        fNameField.setBounds(50, 160, 350, 30);
        frame.add(fNameField);

        JLabel uName = new JLabel("Tên người dùng : ");
        uName.setBounds(50, 190, 150, 50);
        frame.add(uName);

        JTextField uNameField = new JTextField();
        uNameField.setBounds(50, 230, 350, 30);
        frame.add(uNameField);

        JLabel uPass = new JLabel("Mật khẩu : ");
        uPass.setBounds(50, 260, 150, 50);
        frame.add(uPass);

        JPasswordField uPassField = new JPasswordField();
        uPassField.setBounds(50, 300, 150, 30);
        frame.add(uPassField);

        JLabel uPass2 = new JLabel("Xác nhận mật khẩu : ");
        uPass2.setBounds(250, 260, 150, 50);
        frame.add(uPass2);

        JPasswordField uPassField2 = new JPasswordField();
        uPassField2.setBounds(250, 300, 150, 30);
        frame.add(uPassField2);

        JButton submit = new JButton("Đăng ký");
        submit.setBounds(175, 350, 100, 40);
        frame.add(submit);
        submit.addActionListener(e -> {
            String fname = fNameField.getText();
            String uname = uNameField.getText();
            String pass1 = uPassField.getText();
            String pass2 = uPassField2.getText();
            if (fname.isEmpty() || uname.isEmpty() || pass1.isEmpty() || pass2.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Vui lòng điền đầy đủ thông tin", WARNING_TITLE, JOptionPane.WARNING_MESSAGE);
            } else {
                if (pass1.equals(pass2)) {
                    try {
                        SQLManage manage = new SQLManage();
                        manage.newUser(fname, uname, pass1);
                        fNameField.setText("");
                        uNameField.setText("");
                        uPassField.setText("");
                        uPassField2.setText("");
                        JOptionPane.showMessageDialog(frame, "Tạo người dùng thành công", INFO_TITLE, JOptionPane.PLAIN_MESSAGE);
                        frame.dispose();

                    } catch (SQLException e1) {
                        JOptionPane.showMessageDialog(frame, "Lỗi hệ thống!", WARNING_TITLE, JOptionPane.WARNING_MESSAGE);
                    }

                } else {
                    JOptionPane.showMessageDialog(frame, "Xác nhận mật khẩu không thành công", WARNING_TITLE, JOptionPane.WARNING_MESSAGE);
                }
            }
        });

        frame.setVisible(true);
    }
}