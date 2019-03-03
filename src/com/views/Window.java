package com.views;

import java.awt.Color;
import java.awt.Font;
import java.awt.SystemColor;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

import com.config.Config;

public class Window {

	public JFrame frmTool;
	public JPanel panel ;
	public JLabel label ;
	public JButton beginBtn ;
	public JLabel label_1 ;
	public JLabel label_2 ;
	public JLabel proxyCount ;
	public JLabel successCount ;
	public JLabel label_3 ;
	public JLabel isNet ;
	public JLabel label_4 ;
	public JLabel isBegin ;
	public JPanel panel_1 ;
	public JButton button ;
	public JButton button_1 ;
	public JTable table ;
	public DefaultTableModel model ;
	
	public static Window window = null;
	
//	public static void main(String[] args) {
//		Window.getInstance();
//		window.changeNetStatus("检查中...");
//		window.addColumn(Config.proxyList);
//	}
	
	public static Window getInstance(){
		if(window == null){
			window = new Window();
		}
		return window ;
	}

	/**
	 * Create the application.
	 */
	private Window() {
		initialize();
		frmTool.setVisible(true);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmTool = new JFrame();
		frmTool.setTitle("\u535A\u5BA2-\u8BBF\u5BA2 tool");
		frmTool.setResizable(false);
		frmTool.setBackground(Color.WHITE);
		frmTool.setBounds(100, 100, 450, 509);
		//frmTool.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmTool.getContentPane().setLayout(null);
		
		panel = new JPanel();
		panel.setBackground(SystemColor.inactiveCaptionBorder);
		panel.setBounds(0, 0, 444, 111);
		frmTool.getContentPane().add(panel);
		panel.setLayout(null);
		
		label = new JLabel("\u5237\u8BBF\u5BA2\u5DE5\u5177");
		label.setForeground(Color.RED);
		label.setFont(new Font("宋体", Font.PLAIN, 14));
		label.setBounds(178, 0, 76, 22);
		panel.add(label);
		
		beginBtn = new JButton("\u5F00\u59CB");
		beginBtn.setBounds(0, 23, 106, 88);
		panel.add(beginBtn);
		
		label_1 = new JLabel("\u4EE3\u7406\u6570\u91CF\uFF1A");
		label_1.setBounds(116, 39, 69, 15);
		panel.add(label_1);
		
		label_2 = new JLabel("\u6210\u529F\u6B21\u6570\uFF1A");
		label_2.setBounds(119, 86, 66, 15);
		panel.add(label_2);
		
		proxyCount = new JLabel("0");
		proxyCount.setBounds(188, 39, 54, 15);
		panel.add(proxyCount);
		
		successCount = new JLabel("0");
		successCount.setBounds(188, 86, 54, 15);
		panel.add(successCount);
		
		label_3 = new JLabel("\u662F\u5426\u8054\u7F51");
		label_3.setBounds(312, 39, 54, 15);
		panel.add(label_3);
		
		isNet = new JLabel("\u5426");
		isNet.setBounds(380, 39, 54, 15);
		panel.add(isNet);
		
		label_4 = new JLabel("\u662F\u5426\u5F00\u59CB");
		label_4.setBounds(312, 86, 54, 15);
		panel.add(label_4);
		
		isBegin = new JLabel("\u5426");
		isBegin.setBounds(380, 86, 54, 15);
		panel.add(isBegin);
		
		panel_1 = new JPanel();
		panel_1.setBackground(new Color(255, 245, 238));
		panel_1.setBounds(0, 110, 444, 371);
		frmTool.getContentPane().add(panel_1);
		panel_1.setLayout(null);
		
		button = new JButton("\u4EE3\u7406\u5217\u8868");
		button.setBounds(224, 0, 220, 23);
		panel_1.add(button);
		
		button_1 = new JButton("\u4EE3\u5237\u535A\u5BA2");
		button_1.setBounds(0, 0, 225, 23);
		panel_1.add(button_1);
		
		model = new DefaultTableModel() ;
		table = new JTable(model);
		table.setEnabled(false);
		table.setBounds(0, 22, 444, 349);
		JScrollPane jscpanel = new JScrollPane(table) ;
		jscpanel.setBounds(0, 22, 444, 349);
		panel_1.add(jscpanel);
	}
	/**
	 * 封装对ui进行操作的方法
	 */
	//开始按钮内容改变
	public void changeBegin2Stop(){
		beginBtn.setText("暂停");
	}
	public void changeStop2Begin(){
		beginBtn.setText("开始");
	}
	//改变代理数量显示
	public void changeProxyCount(int i){
		proxyCount.setText(String.valueOf(i));
	}
	//取出当前显示代理数量
	public int showProxyCount(){
		return Integer.parseInt(proxyCount.getText()) ;
	}
	//改变当前成功次数
	public void changeSuccessCount(int i){
		successCount.setText(String.valueOf(i));
	}
	//获取当前显示成功数量
	public int getSuccessCount(){
		return Integer.parseInt(successCount.getText());
	}
	//成功次数自增
	public synchronized void addCount(){
		Window window = Window.getInstance() ;
		int count = window.getSuccessCount() ;
		window.changeSuccessCount(++count);
	}
	//更改联网状态
	public void changeNetStatus(String code){
		isNet.setText(code);
	}
	//获取当前联网状态
	public String getNetStatus(){
		return isNet.getText();
	}
	//改变开始状态
	public void changeBeginStatus(String code){
		isBegin.setText(code);
	}
	//获取开始状态
	public String getBeginStatus(){
		return isBegin.getText() ;
	}
	//操作Jtable
	public synchronized void addColumn(String[] strs){
		model = new DefaultTableModel() ;
		for (String string : strs) {
			model.addColumn(string);
		}
		//model.addRow(strs);
		table.setModel(model);
	}
	//添加行
	public synchronized Integer addRow(String[] strs){
		model.addRow(strs);
		return model.getRowCount() ;
	}
}
