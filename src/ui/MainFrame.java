package ui;

import java.awt.*;
import java.rmi.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.MalformedURLException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;

import rmi.RemoteHelper;
import service.UserService;
import ui.MainFrame.MenuListener;
import ui.MainFrame.VersionListener;
import ui.MainFrame.loginActionListener;


public class MainFrame extends JFrame {
	private RemoteHelper remoteHelper;
	JTextArea codeField=new JTextArea();
	JTextArea inputField=new JTextArea();
	JTextArea outputField=new JTextArea();
	static JMenu versionMenu =new JMenu("version");
	static String project;//项目的地址
	static Toolkit uiTool=Toolkit.getDefaultToolkit();
	static Dimension screenSize=uiTool.getScreenSize();
	static JButton signIn;
	JTextField idField;
	JTextField keyField;
	JFrame login;
	static JMenu userMenu;
	static String userId;
	static String fileName;
	static JMenuItem loginin = new JMenuItem("login");
	static JMenuItem logout = new JMenuItem("logout");
	JMenu open =new JMenu("open");
	
	private static boolean isSuccess;
	
	public boolean getIsSuccess(){
		return isSuccess;
	}
	
	public  void createLogin(){
		login =new JFrame("登陆:（admin 123456）");
		JPanel panel =new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		JLabel idIput =new JLabel("账号");
		idField = new JTextField();
		JLabel keyInput =new JLabel("密码");
		keyField =new JTextField();
		
		JPanel buttonPanel =new JPanel();
		JButton signIn = new JButton("登陆");
		signIn.addActionListener(new SignInActionListener());
		buttonPanel.add(signIn);
		panel.add(idIput);
		panel.add(idField);
		panel.add(keyInput);
		panel.add(keyField);
		panel.add(buttonPanel);
		login.add(panel);
		
		login.setSize(screenSize.width/4, screenSize.height/4);
		login.setDefaultCloseOperation(HIDE_ON_CLOSE);
		login.setVisible(true);
		
/*	唉，没看清需求啊，白设计的注册功能；
 * 	signUp.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				File usersList = new File(usersBasePath +"/usersList.txt");
				try {
				while(!usersList.exists()){
						usersList.createNewFile();
					
				}} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
			
					try {
						BufferedWriter writer=new BufferedWriter(new FileWriter(usersList,true));
						writer.write(idField.getText()+" "+keyField.getText());
						writer.newLine();
						writer.close();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				File user =new File(usersBasePath+"/" +idField.getText());
				while(!user.exists()){
					user.mkdirs();
				}
			}});*/
	}
	
	public void start(){
		JFrame bfFrame=new JFrame();
		bfFrame.setLayout(new BorderLayout());
		int bfFrameWidth=screenSize.width/2;
		int bfFrameHeight=screenSize.height/2;
		//想换第一行的图标  Image icon=uiTool.getImage("捕获.PNG");
		
		JMenuBar menuBar =new JMenuBar();
		
		//添加file菜单
		JMenu fileMenu =new JMenu("flie");
		JMenuItem newItem =new JMenuItem("new");
		JMenuItem saveItem =new JMenuItem("save");
		//JMenu open =new JMenu("open");
		JMenuItem exitItem =new JMenuItem("exit");
		fileMenu.add(newItem);
		fileMenu.add(saveItem);
		fileMenu.add(open);
		fileMenu.add(exitItem);
		newItem.addActionListener(new MenuListener());
		saveItem.addActionListener(new MenuListener());
		open.addActionListener(new MenuListener());
		exitItem.addActionListener(new MenuListener());
		menuBar.add(fileMenu);
		
		//添加run菜单
		JMenu runMenu =new JMenu("run");
		JMenuItem execute =new JMenuItem("execute");
		runMenu.add(execute);
		menuBar.add(runMenu);
		execute.addActionListener(new MenuListener());
		
		//添加user彩蛋
		userMenu =new JMenu("user");
		
		
		userMenu.add(loginin);
		userMenu.add(logout);
		loginin.addActionListener(new loginActionListener());
		logout.addActionListener(new logoutActionListener());
		menuBar.add(userMenu);
		
		//添加Version菜单
		menuBar.add(versionMenu);
		/*添加已有版本（txt文件）
		File dir =new File(versionBasePath);
		if(!dir.exists())
			dir.mkdirs();
		File[] dirFiles = dir.listFiles();
		for(File temp : dirFiles){
			if(temp.isFile() && temp.getAbsolutePath().endsWith(".txt") ){
				JMenuItem version =new JMenuItem(versionBasePath+"/"+temp.getName());
				versionMenu.add(version);
				MainFrame frame =new MainFrame();
				version.addActionListener(new VersionListener());
				}
		}*/
		
		
		
		//加入panel code区
		JPanel codePanel =new JPanel();
		codePanel.setLayout(new BorderLayout());
		codePanel.setBounds(0,0,bfFrameWidth,bfFrameHeight/2);
		codePanel.setVisible(true); 
		JLabel codeLabel =new JLabel("code");
		//zheyix=hang
		JScrollPane codeFieldScroll =new JScrollPane(codeField);
		codeFieldScroll.setBounds(0,bfFrameHeight/2,bfFrameWidth/2,bfFrameHeight/2);
		codeField.setLineWrap(true);
		codeFieldScroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		codeFieldScroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		codeField.setBounds(0,0,bfFrameWidth,bfFrameHeight/2);
		codePanel.add(codeFieldScroll,BorderLayout.CENTER);
		codePanel.add(codeLabel, BorderLayout.PAGE_START);
		
		//加入panel 输入区
		JPanel inputPanel =new JPanel();
		inputPanel.setLayout(new BorderLayout());
		inputPanel.setBounds(0,bfFrameHeight/2,bfFrameWidth/2,bfFrameHeight/2);
		inputPanel.setVisible(true);
		JLabel inputLabel =new JLabel("input");
		//zheyihang
		JScrollPane inputFieldScroll =new JScrollPane(inputField);
		inputFieldScroll.setBounds(0,bfFrameHeight/2,bfFrameWidth/2,bfFrameHeight/2);
		inputField.setLineWrap(true);
		inputFieldScroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		inputFieldScroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		inputField.setBounds(0,0,bfFrameWidth/2,bfFrameHeight/2);
		inputPanel.add(inputLabel,BorderLayout.PAGE_START);
		inputPanel.add(inputFieldScroll,BorderLayout.CENTER);
	
		//加入panel 输出区
		JPanel outputPanel =new JPanel();
		outputPanel.setLayout(new BorderLayout());
		outputPanel.setBounds(bfFrameWidth/2,bfFrameHeight/2,bfFrameWidth/2,bfFrameHeight/2);
		outputPanel.setVisible(true);
		JLabel outputLabel =new JLabel("output");
		//
		JScrollPane outputFieldScroll =new JScrollPane(outputField);
		outputFieldScroll.setBounds(bfFrameWidth/2,bfFrameHeight/2,bfFrameWidth/2,bfFrameHeight/2);
		outputField.setLineWrap(true);
		outputField.setEditable(false);
		outputFieldScroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		outputFieldScroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		outputField.setBounds(0,0,bfFrameWidth/2,bfFrameHeight/2);
		outputPanel.add(outputLabel,BorderLayout.PAGE_START);
		outputPanel.add(outputFieldScroll,BorderLayout.CENTER);
		
		JPanel xiamian =new JPanel();
		xiamian.add(inputPanel);
		xiamian.add(outputPanel);
		xiamian.setLayout(new BorderLayout());
		bfFrame.add(codePanel);
		bfFrame.add(xiamian);
		bfFrame.setJMenuBar(menuBar);	
		bfFrame.setVisible(true);
		bfFrame.setTitle("BrainFuck");
		bfFrame.setDefaultCloseOperation(EXIT_ON_CLOSE);
		bfFrame.setSize(bfFrameWidth/24*25,bfFrameHeight/6*7);
	}
	
	
	class SignInActionListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
		try {
			remoteHelper = RemoteHelper.getInstance();
			isSuccess=remoteHelper.getUserService().login(idField.getText(), keyField.getText());
			System.out.println(isSuccess);
			userId=idField.getText();
			if(isSuccess){
				userMenu.setText("user_"+userId);
			}else userMenu.setText("失败,请重试");
			idField.setText("");
			keyField.setText("");
			login.setVisible(false);
			/*File dir =new File(versionBasePath);
		if(!dir.exists())
			dir.mkdirs();
		File[] dirFiles = dir.listFiles();
		for(File temp : dirFiles){
			if(temp.isFile() && temp.getAbsolutePath().endsWith(".txt") ){
				JMenuItem version =new JMenuItem(versionBasePath+"/"+temp.getName());
				versionMenu.add(version);
				MainFrame frame =new MainFrame();
				version.addActionListener(new VersionListener());
				}
		}*/
		} catch (RemoteException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		//下面可能要删掉
		RemoteHelper remoteHelper=RemoteHelper.getInstance(); 
		//open=new JMenu();
		try {
			String result=remoteHelper.getIOService().readFileList(userId);
			//outputField.setText(remoteHelper.getIOService().readFileList(userId));
			String[] str =result.split(" ");
			JMenuItem[] fileItem =new JMenuItem[str.length];
			//open=new JMenu();
			for(int i=0;i<str.length;i++){
				fileItem[i]=new JMenuItem(str[i]);
				fileItem[i].addActionListener(new fileItemListener());
				open.add(fileItem[i]);
			}
			userMenu.remove(loginin);
			userMenu.add(logout);
		} catch (RemoteException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	}

	
	class MenuListener implements ActionListener{
		RemoteHelper remoteHelper;
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			String cmd =e.getActionCommand();
				if(cmd.equals("new")){
					codeField.setText("");
					JFrame frame =new JFrame("新建");
					JPanel panel= new JPanel();
					panel.setSize(screenSize.width/4, screenSize.height/8);
					panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
					JLabel label =new JLabel("FileName");
					JTextField text =new JTextField();
					JButton button =new JButton("确定");
					button.addActionListener(new ActionListener(){

						@Override
						public void actionPerformed(ActionEvent e) {
							try {
								project = new String("\\"+text.getText());
								//System.out.println(project);
								RemoteHelper.getInstance().getIOService().createNewProject(userId,text.getText());
								frame.setVisible(false);
							} catch (RemoteException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
								
							}
							//projectFile=new File(userId).getAbsolutePath()+"/"+text.getText();
							
				
							
						}
						
					});
					panel.add(label);
					panel.add(text);
					panel.add(button);
					frame.add(panel);
					frame.setSize(screenSize.width/4, screenSize.height/7);
					frame.setDefaultCloseOperation(HIDE_ON_CLOSE);
					frame.setVisible(true);
					
					
				}
				if(cmd.equals("save")){
					SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");//设置日期格式
					String time =df.format(new Date());// new Date()为获取当前系统时间
					fileName=project+"\\"+time+".txt";
					//codeField.setText(fileName);
					RemoteHelper remoteHelper=RemoteHelper.getInstance();
					String code = codeField.getText();
					
					try {
						boolean isSuccess=remoteHelper.getIOService().writeFile(code, userId, fileName);
						if(isSuccess){
							JMenuItem newVersion =new JMenuItem(time+".txt");
							newVersion.addActionListener(new VersionListener());
							versionMenu.add(newVersion);
						}
					} catch (RemoteException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					//System.out.print(project);
				}
			/*	if(cmd.equals("open")){
					RemoteHelper remoteHelper=RemoteHelper.getInstance();
						try {
							String result=remoteHelper.getIOService().readFileList(userId);
							outputField.setText(remoteHelper.getIOService().readFileList(userId));
							String[] str =result.split(" ");
							JMenuItem[] fileItem =new JMenuItem[str.length];
							//open=new JMenu();
							for(int i=0;i<str.length;i++){
								fileItem[i]=new JMenuItem(str[i]);
								fileItem[i].addActionListener(new fileItemListener());
								open.add(fileItem[i]);
							}
		
						} catch (RemoteException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}*/
						
				if(cmd.equals("exit")){
					System.exit(0);
				}
				if(cmd.equals("execute")){
					RemoteHelper remoteHelper=RemoteHelper.getInstance();
					try {
						outputField.setText(remoteHelper.getExecuteService().execute(codeField.getText(), inputField.getText()));
					} catch (RemoteException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}//.getUserService()
				}
		}
		
		
		
		//保存版本
				private  void newVersion(String code) throws FileNotFoundException{
					SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");//设置日期格式
					//String time =df.format(new Date());// new Date()为获取当前系统时间
					//String fileName =versionBasePath+"/"+time+".txt";
					File newFile =new File(fileName);
					try {
						while(!newFile.exists()){
							newFile.createNewFile();
							}
					
					FileWriter writer = new FileWriter(newFile);
				//	BufferedWriter br =new BufferedWriter(writer);
					writer.write(code);
					writer.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					JMenuItem version =new JMenuItem(fileName);
					versionMenu.add(version);
					MainFrame frame =new MainFrame();
					version.addActionListener(new VersionListener());
				}
	}
	class fileItemListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			String cmd=e.getActionCommand();
			project ="\\"+cmd;
			File file =new File(cmd);
			JFrame frame =new JFrame("提示");
			frame.setSize(screenSize.width/2, screenSize.height/8);
			frame.setLayout(new BorderLayout());
			JPanel panel =new JPanel();
			panel.setSize(screenSize.width/2, screenSize.height/8);
			JLabel label=new JLabel("提示：请到version菜单中选择版本");
			panel.add(label);
			frame.add(panel);
			frame.setVisible(true);
			frame.setDefaultCloseOperation(HIDE_ON_CLOSE);
			versionMenu.removeAll();
			try {
				String str =RemoteHelper.getInstance().getIOService().readFileList(userId+"\\"+cmd);
				//outputField.setText(str);//到此正确。以后此行删除
				String[] versions =str.split(" ");
				for(String cell:versions){
					if(cell.endsWith(".txt")){
						JMenuItem menuItem =new JMenuItem(cell);
						menuItem.addActionListener(new VersionListener());
						versionMenu.add(menuItem);
					}else continue;
				}
			} catch (RemoteException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}//到此
			
	/*		File[] versions =file.listFiles();
			for(File cell:versions){
				JMenuItem menuItem =new JMenuItem(cell.getName());
				menuItem.addActionListener(new VersionListener());
				versionMenu.add(menuItem);*/
			}
		}
		
	
	
	class VersionListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
			String cmd =e.getActionCommand();
			
			try {
				RemoteHelper remoteHelper=RemoteHelper.getInstance();
				codeField.setText(remoteHelper.getIOService().read_File(userId+"\\"+project+"\\"+cmd));
			} catch (RemoteException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		
	}
	class loginActionListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			createLogin();
			}
	}
	
	
	class logoutActionListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			userId=null;
			userMenu.setText("User");
			open.removeAll();
			userMenu.add(loginin);
			userMenu.remove(logout);
		}
	}
}


