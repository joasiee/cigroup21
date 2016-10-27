import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.concurrent.TimeUnit;

import javax.swing.*;
import javax.swing.text.JTextComponent;

public class GUI {

	private JTextField tf_gen;
	private JTextField tf_ant;
	private JTextField tf_time;
	private JTextField tf_size;
	private JTextField tf_path;
	private JTextField tf_genG;
	private JTextField tf_genC;
	private long startTime;
	
	public GUI(){
		setFrame();
		startTime = System.currentTimeMillis();
	}
	
	private void setFrame(){
		JFrame frame = new JFrame();
		JPanel panel = new JPanel();

		JLabel lbl_path = new JLabel("Path: ");
		JLabel lbl_gen = new JLabel("Generation: ");
		JLabel lbl_ant = new JLabel("Ant: ");
		JLabel lbl_time = new JLabel("Elapsed Time: ");
		JLabel lbl_size = new JLabel("Best Size: ");
		
		JLabel lbl_geneticGen = new JLabel("Genetic generation: ");
		JLabel lbl_geneticChrom = new JLabel("Genetic Chromosome: ");

		tf_path = new JTextField(8);
		tf_path.setEditable(false);
		tf_gen = new JTextField(8);
		tf_gen.setEditable(false);
		tf_ant = new JTextField(8);
		tf_ant.setEditable(false);
		tf_time = new JTextField(8);
		tf_time.setEditable(false);
		tf_size = new JTextField(8);
		tf_size.setEditable(false);
		tf_genG = new JTextField(8);
		tf_genG.setEditable(false);
		tf_genC = new JTextField(8);
		tf_genC.setEditable(false);
		
		
		GroupLayout layout = new GroupLayout(panel);
		panel.setLayout(layout);

		layout.setAutoCreateGaps(true);
		layout.setAutoCreateContainerGaps(true);

		layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addGroup(layout.createSequentialGroup()
						.addComponent(lbl_path, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(tf_path, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
				.addGroup(layout.createSequentialGroup()
						.addComponent(lbl_gen, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(tf_gen, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
				.addGroup(layout.createSequentialGroup()
						.addComponent(lbl_ant, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(tf_ant, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
				.addGroup(layout.createSequentialGroup()
						.addComponent(lbl_size, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(tf_size, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
				.addGroup(layout.createSequentialGroup()
						.addComponent(lbl_time, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(tf_time, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
				.addGroup(layout.createSequentialGroup()
						.addComponent(lbl_geneticGen, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(tf_genG, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
				.addGroup(layout.createSequentialGroup()
						.addComponent(lbl_geneticChrom, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(tf_genC, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));

		layout.setVerticalGroup(layout.createSequentialGroup()
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
						.addComponent(lbl_path)
						.addComponent(tf_path))
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
						.addComponent(lbl_gen)
						.addComponent(tf_gen))
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
						.addComponent(lbl_ant)
						.addComponent(tf_ant))
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
						.addComponent(lbl_size)
						.addComponent(tf_size))
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
						.addComponent(lbl_time)
						.addComponent(tf_time))
				.addGroup(layout.createSequentialGroup()
						.addComponent(lbl_geneticGen, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(tf_genG, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
				.addGroup(layout.createSequentialGroup()
						.addComponent(lbl_geneticChrom, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(tf_genC, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));

		frame.add(panel);
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(true);
		frame.setTitle("Overview");

		frame.pack();
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		frame.setLocation(dim.width / 2 - frame.getSize().width / 2, dim.height / 2 - frame.getSize().height / 2);

		frame.setVisible(true);
		
		
	}
	
	public int getPath(){
		return Integer.parseInt(tf_path.getText());
	}
	
	public void updatePath(int path){
		tf_path.setText(Integer.toString(path));
		updateTime();
	}
	
	public void updateGen(int gen){
		tf_gen.setText(Integer.toString(gen));
		updateTime();
	}
	
	public void updateAnt(int ant){
		tf_ant.setText(Integer.toString(ant));
		updateTime();
	}
	
	public void updateSize(int size){
		tf_size.setText(Integer.toString(size));
		updateTime();
	}
	
	public void updategenG(int genG){
		tf_genG.setText(Integer.toString(genG));
		updateTime();
	}
	
	public void updategenC(int genC){
		tf_genC.setText(Integer.toString(genC));
		updateTime();
	}
	
	private void updateTime(){
		long millis = System.currentTimeMillis()-startTime;
		
		String time = String.format("%02d:%02d:%02d ", 
			    TimeUnit.MILLISECONDS.toMinutes(millis),
			    TimeUnit.MILLISECONDS.toSeconds(millis) - 
			    TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis)),
			    TimeUnit.MILLISECONDS.toMillis(millis)-
			    TimeUnit.SECONDS.toMillis(TimeUnit.MILLISECONDS.toSeconds(millis))
			);
		
		tf_time.setText(time);
	}
	
}
