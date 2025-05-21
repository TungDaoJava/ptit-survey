package survey.page;

import survey.sql.SQLManage;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Random;

import static survey.constant.Constant.FONT_TYPE;
import static survey.constant.Constant.INFO_TITLE;
import static survey.constant.Constant.WARNING_TITLE;

public class MainPage {

    private SQLManage manage;
    private JButton submit;
    private String[] questionsArray;
    private String[] option1Array;
    private String[] option2Array;
    private String[] option3Array;
    private String[] option4Array;
    private static DefaultTableModel model;
    private String cd;
    private final Random random = new Random();

    private int i=0;
    private int h=0;
    private final String[] queStr = new String[50];
    private final String[] op1Str = new String[50];
    private final String[] op2Str = new String[50];
    private final String[] op3Str = new String[50];
    private final String[] op4Str = new String[50];
    private int id;

    public void mainPageView(int id) throws SQLException {
        this.id=id;
        questionsArray = new String[25];
        option1Array = new String[25];
        option2Array = new String[25];
        option3Array = new String[25];
        option4Array = new String[25];

        manage = new SQLManage();

        JFrame frame = new JFrame();
        frame.setSize(800, 600);
        frame.setLayout(null);
        frame.setLocationRelativeTo(null);

        //-------------------------------------ADD PANEL--------------------------------------------------
        JPanel addPanel = new JPanel();
        addPanel.setBounds(250, 0, 550, 600);
        addPanel.setLayout(null);

        JLabel question = new JLabel("Câu hỏi : ");
        question.setBounds(50, 100, 100, 20);
        addPanel.add(question);
        JTextField questionField = new JTextField();
        questionField.setBounds(50, 125, 450, 30);
        addPanel.add(questionField);

        JLabel option1 = new JLabel("Lựa chọn 1 : ");
        option1.setBounds(50, 165, 100, 20);
        addPanel.add(option1);
        JTextField option1Field = new JTextField();
        option1Field.setBounds(50, 190, 200, 30);
        addPanel.add(option1Field);

        JLabel option2 = new JLabel("Lựa chọn 2 : ");
        option2.setBounds(50, 230, 100, 20);
        addPanel.add(option2);
        JTextField option2Field = new JTextField();
        option2Field.setBounds(50, 255, 200, 30);
        addPanel.add(option2Field);

        JLabel option3 = new JLabel("Lựa chọn 3 : ");
        option3.setBounds(50, 295, 100, 20);
        addPanel.add(option3);
        JTextField option3Field = new JTextField();
        option3Field.setBounds(50, 320, 200, 30);
        addPanel.add(option3Field);

        JLabel option4 = new JLabel("Lựa chọn 4 : ");
        option4.setBounds(50, 360, 100, 20);
        addPanel.add(option4);
        JTextField option4Field = new JTextField();
        option4Field.setBounds(50, 385, 200, 30);
        addPanel.add(option4Field);

        JLabel start = new JLabel("TẠO KHẢO SÁT");
        start.setBounds(0, 10, 550, 50);
        start.setHorizontalAlignment(SwingConstants.CENTER);
        start.setFont(new Font(FONT_TYPE, Font.BOLD, 40));
        addPanel.add(start);

        JButton next = new JButton("THÊM CÂU HỎI >");
        next.setBounds(50, 440, 450, 35);
        addPanel.add(next);
        next.addActionListener(e -> {
            String qn = questionField.getText();
            String op1 = option1Field.getText();
            String op2 = option2Field.getText();
            String op3 = option3Field.getText();
            String op4 = option4Field.getText();
            if(qn.isEmpty() || op1.isEmpty() || op2.isEmpty() || op3.isEmpty() || op4.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Vui lòng điền đầy đủ nội dung!", WARNING_TITLE, JOptionPane.WARNING_MESSAGE);
            }
            else {
                questionField.setText("");
                option1Field.setText("");
                option2Field.setText("");
                option3Field.setText("");
                option4Field.setText("");
                queStr[i] = qn;
                op1Str[i] = op1;
                op2Str[i] = op2;
                op3Str[i] = op3;
                op4Str[i] = op4;
                i++;
                submit.setEnabled(true);
            }
        });

        submit = new JButton("LƯU");
        submit.setBounds(50, 490, 200, 50);
        submit.setEnabled(false);
        addPanel.add(submit);
        submit.addActionListener(e -> {
            String code = stringGenerator();
            String qn = questionField.getText();
            String op1 = option1Field.getText();
            String op2 = option2Field.getText();
            String op3 = option3Field.getText();
            String op4 = option4Field.getText();
            if(!(qn.isEmpty() && op1.isEmpty() && op2.isEmpty() && op3.isEmpty() && op4.isEmpty())) {
                JOptionPane.showMessageDialog(frame, "Bạn đang điền dở câu hỏi tiếp theo. Nếu không muốn tạo thêm câu hỏi, vui lòng xoá toàn bộ các ô phía trên", WARNING_TITLE, JOptionPane.WARNING_MESSAGE);
            }
            else {
                if(i==0) {
                    JOptionPane.showMessageDialog(frame, "Bạn chưa thêm bất kì câu hỏi nào", WARNING_TITLE, JOptionPane.WARNING_MESSAGE);
                }
                else {
                    try {
                        manage.userQuestionAdd(id, code);
                        for(int j=0; j<i; j++) {
                            manage.newQuestion(code, queStr[j], op1Str[j], op2Str[j], op3Str[j], op4Str[j]);
                        }
                        JOptionPane.showMessageDialog(frame, "Bộ khảo sát đã hoàn thành. Mã khảo sát : " + code, INFO_TITLE, JOptionPane.PLAIN_MESSAGE);
                    }
                    catch (SQLException e1) {
                        e1.printStackTrace();
                    }
                }
            }
            submit.setEnabled(false);
        });

        JButton cancel = new JButton("HUỶ");
        cancel.setBounds(300, 490, 200, 50);
        addPanel.add(cancel);
        cancel.addActionListener(e -> {
            questionField.setText("");
            option1Field.setText("");
            option2Field.setText("");
            option3Field.setText("");
            option4Field.setText("");
            i=0;
        });

        frame.add(addPanel);
        //---------------------------------------------------------------------------------------------------

        //----------------------------------------------------VIEW PANEL---------------------------------------------
        JPanel viewPanel = new JPanel();
        viewPanel.setBounds(250, 0, 550, 600);
        viewPanel.setLayout(null);
        JLabel searchLabel = new JLabel("Tìm kiếm : ");
        searchLabel.setBounds(100, 20, 100, 50);
        viewPanel.add(searchLabel);

        JTextField search = new JTextField();
        search.setBounds(160, 30, 290, 30);
        viewPanel.add(search);
        search.addKeyListener(new KeyListener() {
            @Override
            public void keyReleased(KeyEvent e) {
                updateTable(search.getText());
            }

            @Override
            public void keyTyped(KeyEvent e) {
                //To avoid errors.
            }

            @Override
            public void keyPressed(KeyEvent e) {
                //To avoid errors.
            }
        });

        JTable table=new JTable(){
            @Override
            public boolean isCellEditable(int row,int column){
                return false;
            }
        };
        model = (DefaultTableModel)table.getModel();
        table.setBackground(Color.decode("#f9d6c4"));
        model.addColumn("Khảo sát của bạn");
        updateTable("");
        JScrollPane scPane=new JScrollPane(table);
        scPane.setBounds(100, 70, 350, 225);
        viewPanel.add(scPane);

        JLabel quesView = new JLabel();
        quesView.setBounds(50, 340, 150, 50);
        viewPanel.add(quesView);

        JLabel op1View = new JLabel();
        op1View.setBounds(70, 380, 150, 50);
        viewPanel.add(op1View);

        JLabel op2View = new JLabel();
        op2View.setBounds(70, 420, 150, 50);
        viewPanel.add(op2View);

        JLabel op3View = new JLabel();
        op3View.setBounds(70, 460, 150, 50);
        viewPanel.add(op3View);

        JLabel op4View = new JLabel();
        op4View.setBounds(70, 500, 150, 50);
        viewPanel.add(op4View);

        JLabel op1Select = new JLabel();
        op1Select.setBounds(100, 400, 150, 50);
        viewPanel.add(op1Select);

        JLabel op2Select = new JLabel();
        op2Select.setBounds(100, 440, 150, 50);
        viewPanel.add(op2Select);

        JLabel op3Select = new JLabel();
        op3Select.setBounds(100, 480, 150, 50);
        viewPanel.add(op3Select);

        JLabel op4Select = new JLabel();
        op4Select.setBounds(100, 520, 150, 50);
        viewPanel.add(op4Select);

        JButton delete = new JButton("XOÁ");
        delete.setBounds(210, 300, 130, 50);
        delete.setEnabled(false);
        viewPanel.add(delete);

        JButton viewPrev = new JButton("TRƯỚC");
        viewPrev.setBounds(100, 300, 100, 50);
        viewPrev.setEnabled(false);
        viewPanel.add(viewPrev);
        viewPrev.addActionListener(e -> {
            if(h>=0) {
                h--;
                actionListener(quesView, op1View, op2View, op3View, op4View, op1Select, op2Select, op3Select, op4Select);
            }
        });

        delete.addActionListener(e -> {
            try {
                manage.removeSurvey(cd);
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            updateTable(search.getText());
        });

        JButton viewNext = new JButton("TIẾP");
        viewNext.setBounds(350, 300, 100, 50);
        viewNext.setEnabled(false);
        viewPanel.add(viewNext);
        viewNext.addActionListener(e -> {
            h++;
            actionListener(quesView, op1View, op2View, op3View, op4View, op1Select, op2Select, op3Select, op4Select);
        });


        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                h=0;
                delete.setEnabled(true);
                viewNext.setEnabled(true);
                viewPrev.setEnabled(true);
                int row = table.getSelectedRow();
                cd = String.valueOf(model.getValueAt(row, 0));
                try {
                    ResultSet rst1 = manage.getQuestions(cd);
                    for(int x=0; rst1.next(); x++) {
                        questionsArray[x] = rst1.getString("question");
                        option1Array[x] = rst1.getString("option1");
                        option2Array[x] = rst1.getString("option2");
                        option3Array[x] = rst1.getString("option3");
                        option4Array[x] = rst1.getString("option4");
                    }
                    mouseClickListener(op1Select, op2Select, op3Select, op4Select);
                }
                catch (SQLException e1) {
                    e1.printStackTrace();
                }
                quesView.setText(questionsArray[h]);
                op1View.setText(option1Array[h]);
                op2View.setText(option2Array[h]);
                op3View.setText(option3Array[h]);
                op4View.setText(option4Array[h]);
            }
        });


        frame.add(viewPanel);

        //--------------------------------------------------------------------------------------------------------------

        //-----------------------------------------------SIDE PANEL-------------------------------------------------------
        JPanel optionPanel = new JPanel();
        optionPanel.setBounds(0, 0, 250, 600);
        optionPanel.setBackground(Color.gray);
        optionPanel.setLayout(null);
        frame.add(optionPanel);

        JButton addSurvey = new JButton("THÊM KHẢO SÁT");
        addSurvey.setBounds(50, 113, 150, 50);
        optionPanel.add(addSurvey);
        addSurvey.addActionListener(e -> {
            viewPanel.setVisible(false);
            addPanel.setVisible(true);
        });

        JButton viewSurvey = new JButton("XEM KHẢO SÁT");
        viewSurvey.setBounds(50, 276, 150, 50);
        optionPanel.add(viewSurvey);
        viewSurvey.addActionListener(e -> {
            updateTable(search.getText());
            viewPanel.setVisible(true);
            addPanel.setVisible(false);
        });

        JButton logout = new JButton("THOÁT");
        logout.setBounds(50, 440, 150, 50);
        optionPanel.add(logout);
        logout.addActionListener(e -> {
            Login login = new Login();
            try {
                login.loginView();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            frame.dispose();
        });
        //-------------------------------------------------------------------------------------------------------

        viewPanel.setVisible(false);
        frame.setVisible(true);
    }

    private void actionListener(JLabel quesView, JLabel op1View, JLabel op2View, JLabel op3View, JLabel op4View, JLabel op1Select, JLabel op2Select, JLabel op3Select, JLabel op4Select) {
        quesView.setText(questionsArray[h]);
        op1View.setText(option1Array[h]);
        op2View.setText(option2Array[h]);
        op3View.setText(option3Array[h]);
        op4View.setText(option4Array[h]);
        try {
            mouseClickListener(op1Select, op2Select, op3Select, op4Select);
        } catch (SQLException e1) {
            e1.printStackTrace();
        }
    }

    private void mouseClickListener(JLabel op1Select, JLabel op2Select, JLabel op3Select, JLabel op4Select) throws SQLException {
        op1Select.setText(String.valueOf(manage.getCount(cd, h, 1)));
        op2Select.setText(String.valueOf(manage.getCount(cd, h, 2)));
        op3Select.setText(String.valueOf(manage.getCount(cd, h, 3)));
        op4Select.setText(String.valueOf(manage.getCount(cd, h, 4)));
    }

    public String stringGenerator() {
        String characterList = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        StringBuilder sb = new StringBuilder();
        for (int j = 0; j < 5; j++) {
            sb.append(characterList.charAt(random.nextInt(characterList.length())));
        }
        return sb.toString();
    }

    private void updateTable(String str) {
        try {
            SQLManage man = new SQLManage();
            ResultSet res = man.surveys(id, str);
            int rowCount = model.getRowCount();
            int i;
            for (i = rowCount - 1; i >= 0; i--)
                model.removeRow(i);
            for(i=0; res.next(); i++) {
                model.addRow(new Object[0]);
                model.setValueAt(res.getString("surveycode"), i, 0);
            }
        } catch (SQLException e1) {
            e1.printStackTrace();
        }
    }

}