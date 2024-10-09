package studentsystem;

import java.awt.Color;
import java.awt.Font;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Base64;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.plaf.FontUIResource;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

public class User extends javax.swing.JFrame {
    public static String ten;
    public static String pass;
    public static String tk;
    
    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
    NumberFormat numberFormat = new DecimalFormat("#.##");
    
    List<Student> filteredList = new ArrayList<>();
    List<Student> list = new ArrayList<Student>();
    static int pos = 0;
    Student x;
    static int check=0;
    JPanel panel;
    
    public User() {
        initComponents();
        rdMale.setSelected(true);
        this.jPanel1.setBackground(new Color(173,216,230));
        this.list.add(new Student("1111","Đinh Duy Vinh","09/01/2004",true,"0914482517","dinhduyvinh69","Quảng Ngãi",9,10,7));
        this.list.add(new Student("2222","Lê Anh Nam","18/08/2003",true,"0389620050","namlelak","Đăk Lăk",6,8,10));
        this.list.add(new Student("3333","Phạm Ngọc Vũ","26/04/2006",true,"0385998065","ngocvu2604","Quảng Nam",10,10,10));
        this.list.add(new Student("4444","Trần Nguyễn Quốc Huy","21/03/2005",false,"0337987563","tnquochuy","Quảng Ngãi",10,9,8));
        ChangeInfo();
        View();
        ViewTable(this.txtSearchName.getText());
        setResizable(false);
        setLocationRelativeTo(null); // Đưa app sau khi chạy vào giữa màn hình
    }
    
    public String decodeBase64(String encodedPass) {
        byte[] decodedBytes = Base64.getDecoder().decode(encodedPass);
        return new String(decodedBytes);
    }
    
    public String encodeBase64(String password) {
        return Base64.getEncoder().encodeToString(password.getBytes());
    }
    
    private String hidePhoneNumber(String phone) {
        if (phone.length() < 10)
            return phone;
        String firstPart = phone.substring(0, 4);
        String lastPart = phone.substring(phone.length() - 2);
        String hiddenPart = "******";
        return firstPart + hiddenPart + lastPart;
    }
    
    public void View() {
        if (pos >= 0 && pos < filteredList.size()) {
            x = filteredList.get(pos);
            this.txtID.setText(x.getID());
            this.txtName.setText(x.getName());
            Date birthday = x.getBirthday();
            String formattedBirthday = dateFormat.format(birthday);
            this.txtBirthday.setText(formattedBirthday);

            boolean gender = x.isGender(); 
            if (gender) {
                this.rdMale.setSelected(true);
            } else {
                this.rdFemale.setSelected(true);
            }

            Calendar cal = Calendar.getInstance();
            Calendar birthCal = Calendar.getInstance();
            birthCal.setTime(birthday);
            int age = cal.get(Calendar.YEAR) - birthCal.get(Calendar.YEAR);
            if (cal.get(Calendar.MONTH) < birthCal.get(Calendar.MONTH) ||
                (cal.get(Calendar.MONTH) == birthCal.get(Calendar.MONTH) && cal.get(Calendar.DAY_OF_MONTH) < birthCal.get(Calendar.DAY_OF_MONTH))) {
                age--;
            }

            String hiddenPhone = hidePhoneNumber(x.getPhone());
            this.txtPhone.setText(hiddenPhone);
            this.txtEmail.setText(x.getEmail());
            this.txtLocation.setText(x.getLocation());
            this.txtAge.setText("" + age);
            this.txtToan.setText(numberFormat.format(x.getToan()));
            this.txtVan.setText(numberFormat.format(x.getVan()));
            this.txtAnh.setText(numberFormat.format(x.getAnh()));
            this.txtDiemTB.setText(numberFormat.format(x.diemTB()));

            this.txtID.setEditable(false); 
            this.txtName.setEditable(false);
            this.txtBirthday.setEditable(false);
            this.txtPhone.setEditable(false);
            this.txtEmail.setEditable(false);
            this.txtLocation.setEditable(false);
            this.txtAge.setEditable(false);
            this.txtToan.setEditable(false);
            this.txtVan.setEditable(false);
            this.txtAnh.setEditable(false);
            this.txtDiemTB.setEditable(false);
            OnOff(true, false); // Bật / tắt các ô điều khiển
        }
    }
    
    public void ChangeInfo() {
        lblInfo.setText(" Thông tin tài khoản của " + ten);
        this.txtUser.setText(tk);
        this.txtPass.setText(pass);
        this.txtUser.setEditable(false);
        this.txtPass.setEditable(false);
        OnOff(true, false);
    }
    
    public void OnOff(boolean a, boolean b) {
        this.btnSave.show(b);
        this.btnCancel.show(b);
        //----------------
        this.btnEdit.show(a);
    }
    
    public void ViewTable(String name) {
        DefaultTableModel model = new DefaultTableModel();
        model.setColumnIdentifiers(new Object[]{"STT", "ID", "Tên", "Ngày sinh", "Giới tính", "Toán", "Văn", "Anh", "ĐTB"});
        model.setNumRows(0); // Đặt số hàng bằng 0 để xóa dữ liệu cũ
        int n = 1;
        Calendar cal = Calendar.getInstance();
        filteredList.clear();

        for (Student x : list) {
            if (x.getName().toLowerCase().contains(name.toLowerCase()) || x.getID().toLowerCase().contains(name.toLowerCase())) {
                filteredList.add(x); // Thêm sinh viên vào danh sách lọc
                String gender = x.isGender() ? "Nam" : "Nữ";
                Date birthday = x.getBirthday();
                String formattedBirthday = dateFormat.format(birthday); // Định dạng ngày sinh
                String formattedToan = numberFormat.format(x.getToan());
                String formattedVan = numberFormat.format(x.getVan());
                String formattedAnh = numberFormat.format(x.getAnh());
                String formattedDiemTB = numberFormat.format(x.diemTB());
                model.addRow(new Object[]{n++, x.getID(), x.getName(), formattedBirthday, gender, formattedToan, formattedVan, formattedAnh, formattedDiemTB});
            }
        }
        this.tblStudent.setModel(model);
        this.tblStudent.setRowHeight(22);
        this.tblStudent.getColumnModel().getColumn(0).setWidth(10);
        this.tblStudent.getColumnModel().getColumn(1).setPreferredWidth(100);
        this.tblStudent.getColumnModel().getColumn(2).setPreferredWidth(300);
        this.tblStudent.getColumnModel().getColumn(3).setPreferredWidth(200);
        this.tblStudent.getColumnModel().getColumn(4).setPreferredWidth(100);
        this.tblStudent.getColumnModel().getColumn(5).setPreferredWidth(100);
        this.tblStudent.getColumnModel().getColumn(6).setPreferredWidth(100);
        this.tblStudent.getColumnModel().getColumn(7).setPreferredWidth(100);
        this.tblStudent.getColumnModel().getColumn(8).setPreferredWidth(100);
        DefaultTableCellRenderer cellRenderer = new DefaultTableCellRenderer();
        cellRenderer.setHorizontalAlignment(JLabel.CENTER);
        tblStudent.getColumnModel().getColumn(0).setCellRenderer(cellRenderer);
        tblStudent.getColumnModel().getColumn(1).setCellRenderer(cellRenderer);
        tblStudent.getColumnModel().getColumn(3).setCellRenderer(cellRenderer);
        tblStudent.getColumnModel().getColumn(4).setCellRenderer(cellRenderer);
        tblStudent.getColumnModel().getColumn(5).setCellRenderer(cellRenderer);
        tblStudent.getColumnModel().getColumn(6).setCellRenderer(cellRenderer);
        tblStudent.getColumnModel().getColumn(7).setCellRenderer(cellRenderer);
        tblStudent.getColumnModel().getColumn(8).setCellRenderer(cellRenderer);
        tblStudent.setDefaultEditor(Object.class, null); // Đặt bảng không thể chỉnh sửa
        if (!filteredList.isEmpty()) {
            pos = 0;
            tblStudent.setRowSelectionInterval(pos, pos);
            View();
        } else {
            pos = -1;
        }
    }
    
    public Student Search(String s) {
        for (Student x:list) {
            if (x.getID().equalsIgnoreCase(s) || x.getName().equalsIgnoreCase(s))
                return x;
        }
        return x;
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        txtID = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        txtAge = new javax.swing.JTextField();
        txtName = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        btnContact = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        txtBirthday = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        rdMale = new javax.swing.JRadioButton();
        rdFemale = new javax.swing.JRadioButton();
        jLabel8 = new javax.swing.JLabel();
        txtPhone = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        txtEmail = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        txtToan = new javax.swing.JTextField();
        txtVan = new javax.swing.JTextField();
        txtAnh = new javax.swing.JTextField();
        jLabel17 = new javax.swing.JLabel();
        txtDiemTB = new javax.swing.JTextField();
        btnFirst = new javax.swing.JButton();
        btnBack = new javax.swing.JButton();
        btnNext = new javax.swing.JButton();
        btnLast = new javax.swing.JButton();
        lblLocal = new javax.swing.JLabel();
        txtLocation = new javax.swing.JTextField();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblStudent = new javax.swing.JTable();
        jLabel5 = new javax.swing.JLabel();
        txtSearchName = new javax.swing.JTextField();
        btnSearch = new javax.swing.JButton();
        btnExit = new javax.swing.JButton();
        txtUser = new javax.swing.JTextField();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        txtPass = new javax.swing.JTextField();
        lblInfo = new javax.swing.JLabel();
        btnEdit = new javax.swing.JButton();
        btnCancel = new javax.swing.JButton();
        btnSave = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Chương trình quản lý sinh viên");

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setForeground(new java.awt.Color(102, 204, 255));
        jPanel2.setToolTipText("");

        txtID.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        jLabel2.setText("MSSV");

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        jLabel3.setText("Tuổi");

        txtAge.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

        txtName.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        jLabel4.setText("Họ và tên");

        btnContact.setBackground(new java.awt.Color(0, 204, 255));
        btnContact.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        btnContact.setForeground(new java.awt.Color(255, 255, 255));
        btnContact.setIcon(new javax.swing.ImageIcon(getClass().getResource("/studentsystem/contact.png"))); // NOI18N
        btnContact.setText(" Contact");
        btnContact.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnContactActionPerformed(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        jLabel6.setText("Ngày sinh");

        txtBirthday.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

        jLabel7.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        jLabel7.setText("Giới tính");

        buttonGroup1.add(rdMale);
        rdMale.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        rdMale.setText("NAM");
        rdMale.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdMaleActionPerformed(evt);
            }
        });

        buttonGroup1.add(rdFemale);
        rdFemale.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        rdFemale.setText("NỮ");

        jLabel8.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        jLabel8.setText("Điện thoại");

        txtPhone.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

        jLabel9.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        jLabel9.setText("Email");

        txtEmail.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

        jLabel10.setFont(new java.awt.Font("Tahoma", 1, 26)); // NOI18N
        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/studentsystem/student.png"))); // NOI18N
        jLabel10.setText(" Thông tin cơ bản:");

        jLabel11.setFont(new java.awt.Font("Tahoma", 1, 26)); // NOI18N
        jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel11.setIcon(new javax.swing.ImageIcon(getClass().getResource("/studentsystem/book.png"))); // NOI18N
        jLabel11.setText(" Tình hình học tập:");

        jLabel12.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        jLabel12.setText("Điểm");

        jLabel13.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        jLabel13.setText("Môn");
        jLabel13.setToolTipText("");

        jLabel14.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        jLabel14.setText("Toán");

        jLabel15.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        jLabel15.setText("Văn");

        jLabel16.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        jLabel16.setText("Anh");

        txtToan.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

        txtVan.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

        txtAnh.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

        jLabel17.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        jLabel17.setText("Điểm TB:");

        txtDiemTB.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

        btnFirst.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnFirst.setText("|<");
        btnFirst.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFirstActionPerformed(evt);
            }
        });

        btnBack.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnBack.setText("<<");
        btnBack.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBackActionPerformed(evt);
            }
        });

        btnNext.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnNext.setText(">>");
        btnNext.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNextActionPerformed(evt);
            }
        });

        btnLast.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnLast.setText(">|");
        btnLast.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLastActionPerformed(evt);
            }
        });

        lblLocal.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        lblLocal.setText("Địa chỉ");

        txtLocation.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSeparator1)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(145, 145, 145)
                .addComponent(btnBack)
                .addGap(62, 62, 62)
                .addComponent(btnNext)
                .addGap(134, 134, 134))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel2Layout.createSequentialGroup()
                            .addGap(32, 32, 32)
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jLabel7, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel9, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel8, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 102, Short.MAX_VALUE)
                                    .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(lblLocal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(txtEmail)
                                .addComponent(txtPhone)
                                .addComponent(txtBirthday)
                                .addComponent(txtName)
                                .addComponent(txtID)
                                .addComponent(txtLocation)
                                .addGroup(jPanel2Layout.createSequentialGroup()
                                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(txtAge, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGroup(jPanel2Layout.createSequentialGroup()
                                            .addComponent(rdMale, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                            .addComponent(rdFemale, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addGap(0, 0, Short.MAX_VALUE))))
                        .addGroup(jPanel2Layout.createSequentialGroup()
                            .addGap(15, 15, 15)
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel10)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addGap(18, 18, 18)
                                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(jLabel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(jPanel2Layout.createSequentialGroup()
                                                .addGap(66, 66, 66)
                                                .addComponent(jLabel14)
                                                .addGap(86, 86, 86)
                                                .addComponent(jLabel15)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(jLabel16))
                                            .addGroup(jPanel2Layout.createSequentialGroup()
                                                .addGap(38, 38, 38)
                                                .addComponent(txtToan, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(35, 35, 35)
                                                .addComponent(txtVan, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(89, 89, 89)))
                                        .addGap(32, 32, 32))
                                    .addComponent(jLabel11)
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addGap(256, 256, 256)
                                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(txtAnh, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGroup(jPanel2Layout.createSequentialGroup()
                                                .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(18, 18, 18)
                                                .addComponent(txtDiemTB, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)))))))
                        .addGroup(jPanel2Layout.createSequentialGroup()
                            .addContainerGap()
                            .addComponent(btnContact)))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(15, 15, 15)
                        .addComponent(btnFirst))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(415, 415, 415)
                        .addComponent(btnLast)))
                .addGap(12, 12, 12))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jLabel10)
                .addGap(16, 16, 16)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(14, 14, 14)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(txtName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(txtBirthday, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(rdMale)
                    .addComponent(rdFemale))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(txtPhone, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(txtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblLocal)
                    .addComponent(txtLocation, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txtAge, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel11)
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel15, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel16, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel13)
                        .addComponent(jLabel14)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel12)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtToan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtVan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtAnh, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel17)
                    .addComponent(txtDiemTB, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnFirst)
                    .addComponent(btnBack)
                    .addComponent(btnNext)
                    .addComponent(btnLast))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnContact)
                .addGap(87, 87, 87))
        );

        jPanel3.setToolTipText("");

        tblStudent.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        tblStudent.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "STT", "ID", "Tên", "Ngày sinh", "Giới tính", "Toán", "Văn", "Anh", "ĐTB"
            }
        ));
        tblStudent.setSelectionBackground(new java.awt.Color(0, 102, 204));
        tblStudent.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                tblStudentFocusGained(evt);
            }
        });
        tblStudent.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblStudentMouseClicked(evt);
            }
        });
        tblStudent.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                tblStudentPropertyChange(evt);
            }
        });
        tblStudent.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tblStudentKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tblStudentKeyReleased(evt);
            }
        });
        tblStudent.addVetoableChangeListener(new java.beans.VetoableChangeListener() {
            public void vetoableChange(java.beans.PropertyChangeEvent evt)throws java.beans.PropertyVetoException {
                tblStudentVetoableChange(evt);
            }
        });
        jScrollPane1.setViewportView(tblStudent);

        jLabel5.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/studentsystem/searchUser.png"))); // NOI18N
        jLabel5.setText(" Tìm kiếm tên SV:");

        txtSearchName.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N

        btnSearch.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        btnSearch.setIcon(new javax.swing.ImageIcon(getClass().getResource("/studentsystem/search.png"))); // NOI18N
        btnSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSearchActionPerformed(evt);
            }
        });

        btnExit.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        btnExit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/studentsystem/logout.png"))); // NOI18N
        btnExit.setText("Đăng Xuất");
        btnExit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExitActionPerformed(evt);
            }
        });

        txtUser.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

        jLabel18.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        jLabel18.setText("Tài Khoản:");

        jLabel19.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        jLabel19.setText("Mật Khẩu:");

        txtPass.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

        lblInfo.setFont(new java.awt.Font("Tahoma", 1, 26)); // NOI18N
        lblInfo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblInfo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/studentsystem/infomation.png"))); // NOI18N
        lblInfo.setText(" Thông tin tài khoản");

        btnEdit.setBackground(new java.awt.Color(242, 242, 242));
        btnEdit.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        btnEdit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/studentsystem/edit.png"))); // NOI18N
        btnEdit.setText("Sửa");
        btnEdit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditActionPerformed(evt);
            }
        });

        btnCancel.setBackground(new java.awt.Color(255, 0, 102));
        btnCancel.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        btnCancel.setForeground(new java.awt.Color(255, 255, 255));
        btnCancel.setText("Hủy");
        btnCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelActionPerformed(evt);
            }
        });

        btnSave.setBackground(new java.awt.Color(102, 255, 0));
        btnSave.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        btnSave.setText("Lưu");
        btnSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtSearchName, javax.swing.GroupLayout.PREFERRED_SIZE, 558, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btnSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(8, 8, 8))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtUser, javax.swing.GroupLayout.PREFERRED_SIZE, 232, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(71, 71, 71)
                                .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtPass, javax.swing.GroupLayout.PREFERRED_SIZE, 232, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(btnEdit)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnSave)
                                .addGap(18, 18, 18)
                                .addComponent(btnCancel)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btnExit))))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addComponent(lblInfo))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 829, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel5)
                        .addComponent(txtSearchName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 455, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblInfo)
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtUser, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel18)
                    .addComponent(jLabel19)
                    .addComponent(txtPass, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnEdit)
                            .addComponent(btnCancel)
                            .addComponent(btnSave))
                        .addContainerGap(52, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnExit)
                        .addGap(15, 15, 15))))
        );

        jLabel1.setBackground(java.awt.SystemColor.inactiveCaption);
        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 36)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 51, 153));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/studentsystem/internship.png"))); // NOI18N
        jLabel1.setText(" THÔNG TIN SINH VIÊN");
        jLabel1.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 718, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(280, 280, 280))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(49, 49, 49)
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnExitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExitActionPerformed
        // TODO add your handling code here:
        //        System.exit(0);
        UIManager.put("OptionPane.messageFont", new FontUIResource(new Font("Arial", Font.BOLD, 20)));
        int n = JOptionPane.showConfirmDialog(panel,"Bạn có chắc chắn muốn đăng xuất không?","Thông Báo",JOptionPane.YES_NO_OPTION);
        if(n == JOptionPane.YES_OPTION) {
            Login a = new Login();
            this.setVisible(false);//an form dnng nhap
            a.setVisible(true); //Hien thi form calculator
        }
    }//GEN-LAST:event_btnExitActionPerformed

    private void btnSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSearchActionPerformed
        // TODO add your handling code here:
        ViewTable(this.txtSearchName.getText());
        if (tblStudent.getRowCount() > 0) {
            tblStudent.setRowSelectionInterval(0, 0); // Chọn dòng đầu tiên
            tblStudentMouseClicked(null); // Gọi phương thức để hiển thị thông tin dòng được chọn
        }
    }//GEN-LAST:event_btnSearchActionPerformed

    private void tblStudentVetoableChange(java.beans.PropertyChangeEvent evt)throws java.beans.PropertyVetoException {//GEN-FIRST:event_tblStudentVetoableChange
        // TODO add your handling code here:
    }//GEN-LAST:event_tblStudentVetoableChange

    private void tblStudentKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tblStudentKeyReleased
        // TODO add your handling code here:
        //pos = this.tblStudent.getSelectedRow();
        int row = this.tblStudent.getSelectedRow();
        int col = 1;
        String s = this.tblStudent.getModel().getValueAt(row, col).toString();
        pos = list.indexOf(Search(s));
        View();
    }//GEN-LAST:event_tblStudentKeyReleased

    private void tblStudentKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tblStudentKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_tblStudentKeyPressed

    private void tblStudentPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_tblStudentPropertyChange
        // TODO add your handling code here:
    }//GEN-LAST:event_tblStudentPropertyChange

    private void tblStudentMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblStudentMouseClicked
        // TODO add your handling code here:
        //pos = this.tblStudent.getSelectedRow();
        int row = this.tblStudent.getSelectedRow();
        int col = 1;
        String s = this.tblStudent.getModel().getValueAt(row,col).toString();
        pos = list.indexOf(Search(s));
        View();
    }//GEN-LAST:event_tblStudentMouseClicked

    private void tblStudentFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_tblStudentFocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_tblStudentFocusGained

    private void rdMaleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdMaleActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_rdMaleActionPerformed

    private void btnContactActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnContactActionPerformed
        Contact a = new Contact();
        a.setVisible(true);
        OnOff(true, false);
        check = 1;
    }//GEN-LAST:event_btnContactActionPerformed

    private void btnCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelActionPerformed
        // TODO add your handling code here:
        ChangeInfo();
    }//GEN-LAST:event_btnCancelActionPerformed

    private void btnSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveActionPerformed
        // TODO add your handling code here:
        String newUser = this.txtUser.getText().trim();
        String newPass = this.txtPass.getText().trim();
        if (newUser.isEmpty() || newPass.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin rồi mới lưu", "Thông báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        File file = new File("./Data/TaiKhoan.txt");
        List<String> lines = new ArrayList<>();
        boolean found = false;

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                String username = line.trim();
                String encodedPassword = br.readLine().trim();
                String password = decodeBase64(encodedPassword); // Giải mã mật khẩu
                String fullname = br.readLine().trim();

                if (username.equals(tk) && password.equals(pass)) {
                    lines.add(newUser);
                    lines.add(encodeBase64(newPass));
                    lines.add(fullname);
                    found = true;
                } else {
                    lines.add(username);
                    lines.add(encodedPassword);
                    lines.add(fullname);
                }
            }
        } catch (Exception e) {}
        if (found) {
            try (BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {
                for (String line : lines) {
                    bw.write(line);
                    bw.newLine();
                }
                JOptionPane.showMessageDialog(this, "Cập nhật tài khoản thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                tk = newUser;
                pass = newPass;
                this.txtPass.setText(pass);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Đã có lỗi xảy ra khi ghi file!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        } else
            JOptionPane.showMessageDialog(this, "Tài khoản không khớp hoặc không tồn tại!", "Thông báo", JOptionPane.WARNING_MESSAGE);
        ChangeInfo();
    }//GEN-LAST:event_btnSaveActionPerformed

    private void btnEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditActionPerformed
        // TODO add your handling code here:
        check = -1;
        this.txtUser.setEditable(true);
        this.txtPass.setEditable(true);
        OnOff(false, true);
    }//GEN-LAST:event_btnEditActionPerformed

    private void btnFirstActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFirstActionPerformed
        // TODO add your handling code here:
        if (!filteredList.isEmpty()) {
            pos = 0;
            View();
            tblStudent.setRowSelectionInterval(pos, pos);
        }
    }//GEN-LAST:event_btnFirstActionPerformed

    private void btnLastActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLastActionPerformed
        // TODO add your handling code here:
        if (!filteredList.isEmpty()) {
            pos = filteredList.size() - 1;
            View();
            tblStudent.setRowSelectionInterval(pos, pos);
        }
    }//GEN-LAST:event_btnLastActionPerformed

    private void btnBackActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBackActionPerformed
        // TODO add your handling code here:
        if (pos > 0) {
            pos--;
            View();
            tblStudent.setRowSelectionInterval(pos, pos);
        }
    }//GEN-LAST:event_btnBackActionPerformed

    private void btnNextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNextActionPerformed
        // TODO add your handling code here:
        if (pos < filteredList.size() - 1) {
            pos++;
            View();
            tblStudent.setRowSelectionInterval(pos, pos);
        }
    }//GEN-LAST:event_btnNextActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(User.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(User.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(User.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(User.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Login().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBack;
    private javax.swing.JButton btnCancel;
    private javax.swing.JButton btnContact;
    private javax.swing.JButton btnEdit;
    private javax.swing.JButton btnExit;
    private javax.swing.JButton btnFirst;
    private javax.swing.JButton btnLast;
    private javax.swing.JButton btnNext;
    private javax.swing.JButton btnSave;
    private javax.swing.JButton btnSearch;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JLabel lblInfo;
    private javax.swing.JLabel lblLocal;
    private javax.swing.JRadioButton rdFemale;
    private javax.swing.JRadioButton rdMale;
    private javax.swing.JTable tblStudent;
    private javax.swing.JTextField txtAge;
    private javax.swing.JTextField txtAnh;
    private javax.swing.JTextField txtBirthday;
    private javax.swing.JTextField txtDiemTB;
    private javax.swing.JTextField txtEmail;
    private javax.swing.JTextField txtID;
    private javax.swing.JTextField txtLocation;
    private javax.swing.JTextField txtName;
    private javax.swing.JTextField txtPass;
    private javax.swing.JTextField txtPhone;
    private javax.swing.JTextField txtSearchName;
    private javax.swing.JTextField txtToan;
    private javax.swing.JTextField txtUser;
    private javax.swing.JTextField txtVan;
    // End of variables declaration//GEN-END:variables
}
