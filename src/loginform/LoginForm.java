package loginform;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.table.DefaultTableModel;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.awt.event.ActionEvent;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class LoginForm {

	private JFrame frmFormDataBarang;
	private JTextField txtUsername;
	private JPasswordField txtPassword;
	JButton tblSimpan = new JButton("Login");
	
	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	static final String DB_URL = "jdbc:mysql://127.0.0.1/test";
	static final String USER = "root";
	static final String PASS = "";
	
	static Connection conn;
	static Statement stmt;
	static ResultSet rs;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LoginForm window = new LoginForm();
					window.frmFormDataBarang.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public LoginForm() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmFormDataBarang = new JFrame();
		frmFormDataBarang.addWindowListener(new WindowAdapter() {
			@Override
			public void windowOpened(WindowEvent e) {
			}
		});
		frmFormDataBarang.setTitle("Form Login");
		frmFormDataBarang.setBounds(100, 100, 518, 593);
		frmFormDataBarang.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmFormDataBarang.getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Login Form");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblNewLabel.setBounds(35, 21, 118, 41);
		frmFormDataBarang.getContentPane().add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Username");
		lblNewLabel_1.setBounds(35, 73, 84, 14);
		frmFormDataBarang.getContentPane().add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("Password");
		lblNewLabel_2.setBounds(35, 98, 84, 14);
		frmFormDataBarang.getContentPane().add(lblNewLabel_2);
		
		
		txtUsername = new JTextField();
		txtUsername.setBounds(129, 73, 86, 20);
		frmFormDataBarang.getContentPane().add(txtUsername);
		txtUsername.setColumns(10);
		
		txtPassword = new JPasswordField();
		txtPassword.setBounds(129, 95, 86, 20);
		frmFormDataBarang.getContentPane().add(txtPassword);
		txtPassword.setColumns(10);

		tblSimpan.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String username = txtUsername.getText();
				String password = txtPassword.getText();
				
				select(username, password);
			}
		});
		tblSimpan.setBounds(35, 198, 89, 23);
		frmFormDataBarang.getContentPane().add(tblSimpan);
		
	}
	
	public void select(String username, String password)
	{
		try {
			Class.forName(JDBC_DRIVER);
			conn = DriverManager.getConnection(DB_URL,USER,PASS);
			stmt = conn.createStatement();
			
			String sql = "SELECT * FROM tbl_user WHERE username = ? AND password = ? AND active = 1";
			PreparedStatement ps = conn.prepareStatement(sql);
		
			ps.setString(1, username);
			ps.setString(2, md5(password));
			
			rs = ps.executeQuery();
                        if(rs.next()){
                            JOptionPane.showMessageDialog(frmFormDataBarang, "Login Success!");
                        }else{
                            JOptionPane.showMessageDialog(frmFormDataBarang, "Login Failed!");
                        }
			
			stmt.close();
			conn.close();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	
        public String md5(String s) {
           try {
                 // Create MD5 Hash
                 MessageDigest digest = java.security.MessageDigest.getInstance("MD5");
                 digest.update(s.getBytes());
                 byte messageDigest[] = digest.digest();
 
                 // Create Hex String
                 StringBuffer hexString = new StringBuffer();
                 for (int i=0; i<messageDigest.length; i++)
                   hexString.append(Integer.toHexString(0xFF & messageDigest[i]));
 
                 return hexString.toString();
               }catch (NoSuchAlgorithmException e) {
                 e.printStackTrace();
               }
               return "";
          }
	
}
