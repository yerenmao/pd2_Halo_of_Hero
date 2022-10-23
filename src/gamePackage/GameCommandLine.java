package gamePackage;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import playerPackage.Player;

public class GameCommandLine extends JFrame implements ActionListener {
	
	private static final long serialVersionUID = 1L;
	
	public final int WIDTH = 600;
	public final int HEIGHT = 100;
	
	private JButton button;
	private JTextField textField;
	private JLabel text;
	
	private boolean verified = false;
	
	private Player player;
	private GameObjectController objectController;
	
	private boolean enterCommand(String input) {
		try {
			String[] command = input.split(" ");
			switch(command[0]) {
			case "coin":
				if(command.length != 3) return false;
				if(command[1].equals("get")) GameSetting.coinNumber += Integer.parseInt(command[2]);
				else if(command[1].equals("set")) GameSetting.coinNumber = Integer.parseInt(command[2]);
				else return false; break;
			case "player":
				if(command.length != 4) return false;
				if(command[1].equals("dimond")) {
					if(command[2].equals("get")) player.gainDimond(Integer.parseInt(command[3]));
					else if(command[2].equals("set")) player.setDimondAmount(Integer.parseInt(command[3]));
					else return false; break;
				} else if(command[1].equals("hp")) {
					if(command[2].equals("get")) player.recoverHp(Integer.parseInt(command[3]));
					else if(command[2].equals("set")) player.setHp(Integer.parseInt(command[3]));
					else return false; break;
				} else return false;
			case "clear":
				if(command.length != 2) return false;
				if(command[1].equals("enemy")) objectController.enemyList.clear();
				else return false; break;
			case "drink":
				if(command.length != 4) return false;
				if(command[1].equals("potion")) {
					if(command[2].equals("less") && command[3].equals("damage")) player.drinkLessDamagePotion();
					else if(command[2].equals("recover") && command[3].equals("hp")) player.drinkRecoverPotion();
					else return false; break;
				} else return false;
			default: return false;
			}
		} catch(Exception e) {
			return false;
		}
		return true;
	}
	
	public GameCommandLine(Player player, GameObjectController objectController) {
		
		this.player = player;
		this.objectController = objectController;
		
		this.setTitle("Command Line");
		this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.getContentPane().setBackground(Color.black);
		this.setLocationRelativeTo(null);
		this.setLayout(new FlowLayout());
		
		textField = new JTextField();
		textField.setPreferredSize(new Dimension(500, 40));
		textField.setFont(new Font("monospaced", Font.PLAIN, 12));
		textField.setForeground(Color.green);
		textField.setBackground(Color.black);
		textField.setCaretColor(Color.white);
		textField.setText("password: ");
		
		button = new JButton("Submit");
		button.addActionListener(this);
		
		text = new JLabel("");
		text.setSize(new Dimension(600, 40));
		text.setForeground(Color.white);
		text.setHorizontalAlignment(SwingConstants.LEFT);
		
		textField.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_ENTER) {
					button.doClick();
				}
			}
		});
		
		this.add(textField);
		this.add(button);
		this.add(text);
		
		this.pack();
		
		this.setVisible(true);
	}

	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==button) {
			String input = textField.getText();
			if(verified == false) {
				if(input.equalsIgnoreCase("password: PD2")) {
					text.setText("Welcome");
					textField.setText("");
					verified = true;
				}
			} else {
				if(enterCommand(input)) {
					text.setForeground(Color.white);
					text.setText("success: "+ input);
				} else {
					text.setForeground(Color.red);
					text.setText("command not found");
				}
			}
		}
	}
}
