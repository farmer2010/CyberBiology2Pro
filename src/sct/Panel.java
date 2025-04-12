package sct;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

import javax.swing.*;

public class Panel extends JPanel {
	Random rand = new Random();
	Timer timer;
	//
	public World world;
	public int panel_type;
	//
	public int options_panel_index = 0;
	public OptionsPanel[] options_panels = new OptionsPanel[2];
	//
	JButton back_button = new JButton("< Back");
	JButton next_button = new JButton("Next >");
	//
	Compilator c = new Compilator();
	//
	public Panel(World w, int new_panel_type) {
		world = w;
		panel_type = new_panel_type;
		//
		//timer = new Timer(10, new Listener());
		//
		if (panel_type == 1) {
			back_button.addActionListener(e -> back());
	        back_button.setBounds(0, 1010, 125, 20);
	        back_button.setEnabled(false);
	        add(back_button);
	        //
	        next_button.addActionListener(e -> next());
	        next_button.setBounds(175, 1010, 125, 20);
	        add(next_button);
			//
			for (int i = 0; i < options_panels.length; i++) {
				options_panels[i] = new OptionsPanel(this, i);
				options_panels[i].setBackground(new Color(90, 90, 90));
				options_panels[i].setBounds(0, 27, 300, 1010);
			}
			add(options_panels[0]);
		}else if (panel_type == 2) {
			JSlider red_slider = new JSlider(0, 255, rand.nextInt(256));
			red_slider.setBounds(0, 70, 200, 40);
			red_slider.setPaintLabels(true);
			red_slider.setMajorTickSpacing(50);
			add(red_slider);
			//
			JSlider green_slider = new JSlider(0, 255, rand.nextInt(256));
			green_slider.setBounds(0, 140, 200, 40);
			green_slider.setPaintLabels(true);
			green_slider.setMajorTickSpacing(50);
			add(green_slider);
			//
			JSlider blue_slider = new JSlider(0, 255, rand.nextInt(256));
			blue_slider.setBounds(0, 210, 200, 40);
			blue_slider.setPaintLabels(true);
			blue_slider.setMajorTickSpacing(50);
			add(blue_slider);
			//
			JSlider energy_slider = new JSlider(0, 1000, 1000);
			energy_slider.setBounds(0, 300, 200, 40);
			energy_slider.setPaintLabels(true);
			energy_slider.setMajorTickSpacing(200);
			add(energy_slider);
			//
			JSlider minerals_slider = new JSlider(0, 1000, 0);
			minerals_slider.setBounds(0, 370, 200, 40);
			minerals_slider.setPaintLabels(true);
			minerals_slider.setMajorTickSpacing(200);
			add(minerals_slider);
			//
			JSlider age_slider = new JSlider(0, 1000, 1000);
			age_slider.setBounds(0, 440, 200, 40);
			age_slider.setPaintLabels(true);
			age_slider.setMajorTickSpacing(200);
			add(age_slider);
			//
			ButtonGroup group = new ButtonGroup();
			JRadioButton bot_button = new JRadioButton("bot", true);
			bot_button.setBounds(0, 510, 150, 20);
			group.add(bot_button);
			add(bot_button);
			//
			JRadioButton org_fall_button = new JRadioButton("falling organics", false);
			org_fall_button.setBounds(0, 530, 150, 20);
			group.add(org_fall_button);
			add(org_fall_button);
			//
			JRadioButton org_button = new JRadioButton("static organics", false);
			org_button.setBounds(0, 550, 150, 20);
			group.add(org_button);
			add(org_button);
			//
			for (int i = 0; i < getComponentCount(); i++) {
				getComponent(i).setVisible(false);
			}
		}else if (panel_type == 3) {
			JTextArea text_area = new JTextArea();
			text_area.setBounds(5, 30, 250, 500);
			add(text_area);
			//
			JTextField sl_code = new JTextField();
			sl_code.setBounds(0, 560, 250, 20);
			add(sl_code);
			//
			JButton save_code_button = new JButton("Save");
	        //save_code_button.addActionListener(e -> save_bot());
			save_code_button.setBounds(0, 585, 120, 20);
			add(save_code_button);
			//
			JButton load_code_button = new JButton("Load");
	        //load_code_button.addActionListener(new select());
			load_code_button.setBounds(125, 585, 120, 20);
			add(load_code_button);
			//
			JTextField sl_bot = new JTextField();
			sl_bot.setBounds(0, 625, 250, 20);
			add(sl_bot);
			//
			JButton save_bot_button = new JButton("Save");
	        save_bot_button.addActionListener(e -> save_bot());
			save_bot_button.setBounds(0, 650, 120, 20);
			add(save_bot_button);
			//
			JButton load_bot_button = new JButton("Load to buffer");
	        load_bot_button.addActionListener(e -> load_bot());
			load_bot_button.setBounds(125, 650, 120, 20);
			add(load_bot_button);
		}
		//
		//timer.start();
	}
	//
	public void paintComponent(Graphics canvas) {
		super.paintComponent(canvas);
		canvas.setColor(new Color(0, 0, 0));
		canvas.setFont(new Font("arial", Font.BOLD, 18));
		canvas.drawString(String.valueOf(world.panel_index + 1) + "/" + String.valueOf(world.panels.length), 140, 1060);
		if (panel_type == 0) {
			canvas.drawString("Main: ", 0, 20);
			canvas.drawString("version 1.9.1", 0, 40);
			canvas.drawString("steps: " + String.valueOf(world.steps), 0, 60);
			canvas.drawString("objects: " + String.valueOf(world.obj_count) + ", bots: " + String.valueOf(world.b_count), 0, 80);
			String txt = "";
			if (world.draw_type == 0) {
				txt = "predators view";
			}else if (world.draw_type == 1) {
				txt = "color view";
			}else if (world.draw_type == 2) {
				txt = "energy view";
			}else if (world.draw_type == 3) {
				txt = "minerals view";
			}else {
				txt = "age view";
			}
			canvas.drawString("render type: " + txt, 0, 100);
			if (world.mouse == 0) {
				txt = "select";
			}else if (world.mouse == 1) {
				txt = "set";
			}else {
				txt = "remove";
			}
			canvas.drawString("mouse function: " + txt, 0, 120);
			canvas.drawString("Render types:", 0, 180);
			canvas.drawString("Selection:", 0, 275);
			canvas.drawString("enter name:", 0, 405);
			canvas.drawString("Mouse functions:", 0, 445);
			canvas.drawString("Load:", 0, 490);
			canvas.drawString("enter name:", 0, 510);
			canvas.drawString("Controls:", 0, 580);
			if (world.selection != null) {
				canvas.drawString("energy: " + String.valueOf((int)(world.selection.energy)) + ", minerals: " + String.valueOf(world.selection.minerals), 0, 295);
				canvas.drawString("age: " + String.valueOf(world.selection.age), 0, 315);
				canvas.drawString("position: " + "[" + String.valueOf(world.selection.xpos) + ", " + String.valueOf(world.selection.ypos) + "]", 0, 335);
				canvas.drawString("color: " + "(" + String.valueOf(world.selection.color.getRed()) + ", " + String.valueOf(world.selection.color.getGreen()) + ", " + String.valueOf(world.selection.color.getBlue()) + ")", 0, 355);
			}else {
				canvas.drawString("none", 0, 295);
			}
		}else if (panel_type == 1) {
			canvas.drawString("Settings:", 0, 20);
			canvas.fillRect(0, 25, 300, 2);
			canvas.fillRect(0, 1037, 300, 2);
		}else if (panel_type == 2) {
			canvas.drawString("Edit loaded object:", 0, 20);
			if (world.for_set != null) {
				canvas.drawString("Color:", 0, 40);
				canvas.drawString("red:", 0, 60);
				canvas.drawString("green:", 0, 130);
				canvas.drawString("blue:", 0, 200);
				canvas.drawString("Main parameters:", 0, 270);
				canvas.drawString("energy:", 0, 290);
				canvas.drawString("minerals:", 0, 360);
				canvas.drawString("age:", 0, 430);
				canvas.drawString("state:", 0, 500);
				//
				canvas.fillRect(250, 140, 50, 50);
				canvas.fillRect(250, 300, 50, 50);
				canvas.fillRect(250, 370, 50, 50);
				canvas.fillRect(250, 440, 50, 50);
				//
				canvas.setColor(new Color(get_JSlider(0).getValue(), get_JSlider(1).getValue(), get_JSlider(2).getValue()));
				canvas.fillRect(255, 145, 40, 40);
				//
				canvas.setColor(new Color(255, 255 - (int)(get_JSlider(3).getValue() / 1000.0 * 255.0), 0));
				canvas.fillRect(255, 305, 40, 40);
				//
				int rg = 255 - (int)(get_JSlider(4).getValue() / 1000.0 * 255.0);
				canvas.setColor(new Color(rg, rg, 255));
				canvas.fillRect(255, 375, 40, 40);
				//
				int ag = (int)(get_JSlider(5).getValue() / 1000.0 * 255.0);
				canvas.setColor(new Color(ag, ag, 255 - ag));
				canvas.fillRect(255, 445, 40, 40);
			}else {
				canvas.drawString("No loaded object", 0, 40);
			}
		}else if (panel_type == 3) {
			canvas.drawString("Compilator:", 0, 20);
			canvas.drawString("Save / load code:", 0, 550);
			canvas.drawString("Save / load bot:", 0, 620);
			canvas.drawString("Console:", 0, 690);
			canvas.fillRect(5, 700, 250, 100);
			canvas.setColor(new Color(255, 255, 255));
			canvas.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
			for (int i = 0; i < 5; i++) {
				canvas.drawString(c.console[i], 6, 710 + 10 * i);
			}
		}
	}
	//
	public OptionsPanel get_OptionsPanel(int index) {
		return((OptionsPanel) getComponent(index));
	}
	public JTextArea get_JTextArea(int index) {
		return((JTextArea) getComponent(index));
	}
	public JTextField get_JTextField(int index) {
		return((JTextField) getComponent(index));
	}
	public JSlider get_JSlider(int index) {
		return((JSlider) getComponent(index));
	}
	public JRadioButton get_JRadioButton(int index) {
		return((JRadioButton) getComponent(index));
	}
	//
	public void back() {
		remove(options_panels[options_panel_index]);
		options_panel_index--;
		next_button.setEnabled(true);
		if (options_panel_index <= 0) {
			options_panel_index = 0;
			back_button.setEnabled(false);
		}else {
			back_button.setEnabled(true);
		}
		add(options_panels[options_panel_index]);
	}
	public void next() {
		remove(options_panels[options_panel_index]);
		options_panel_index++;
		back_button.setEnabled(true);
		if (options_panel_index >= options_panels.length - 1) {
			options_panel_index = options_panels.length - 1;
			next_button.setEnabled(false);
		}else {
			next_button.setEnabled(true);
		}
		add(options_panels[options_panel_index]);
	}
	//
	public void save_bot() {
    	int[] genome = c.compilate(get_JTextArea(0).getText());
    	//
    	String txt = "";
		for (int i = 0; i < 64; i++) {
			txt += String.valueOf(genome[i]) + " ";
		}
		try {
            FileWriter fileWriter = new FileWriter("saved objects/" + get_JTextField(4).getText() +".dat");
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.write(txt);
            bufferedWriter.close();
        } catch (IOException ex) {
            System.out.println("Ошибка при записи в файл");
            ex.printStackTrace();
        }
	}
	//
	public void load_bot() {
		world.for_set = c.compilate(get_JTextArea(0).getText());
		//
        for (int i = 0; i < world.panels[2].getComponentCount(); i++) {
        	world.panels[2].getComponent(i).setVisible(true);
        }
	}
	//
	private class Listener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			if (panel_type == 2) {
				if (world.panel_index != 2) {
					
				}
			}
		}
	}
}
