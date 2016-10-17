import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.concurrent.TimeUnit;

import javax.swing.*;

public class GUI {

	private JTextField tf_gen;
	private JTextField tf_ant;
	private JTextField tf_time;
	private long startTime;
	
	public GUI(){
		setFrame();
		startTime = System.currentTimeMillis();
	}
	
	private void setFrame(){
		JFrame frame = new JFrame();
		JPanel panel = new JPanel();
		
		JLabel lbl_gen = new JLabel("Generation: ");
		JLabel lbl_ant = new JLabel("Ant: ");
		JLabel lbl_time = new JLabel("Elapsed Time: ");
		
		tf_gen = new JTextField(8);
		tf_gen.setEditable(false);
		tf_ant = new JTextField(8);
		tf_ant.setEditable(false);
		tf_time = new JTextField(8);
		tf_time.setEditable(false);
		
		
		GroupLayout layout = new GroupLayout(panel);
		panel.setLayout(layout);

		layout.setAutoCreateGaps(true);
		layout.setAutoCreateContainerGaps(true);

		layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addGroup(layout.createSequentialGroup()
						.addComponent(lbl_gen, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(tf_gen, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
				.addGroup(layout.createSequentialGroup()
						.addComponent(lbl_ant, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(tf_ant, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
				.addGroup(layout.createSequentialGroup()
						.addComponent(lbl_time, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(tf_time, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));

		layout.setVerticalGroup(layout.createSequentialGroup()
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
						.addComponent(lbl_gen)
						.addComponent(tf_gen))
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
						.addComponent(lbl_ant)
						.addComponent(tf_ant))
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
						.addComponent(lbl_time)
						.addComponent(tf_time)));

		frame.add(panel);
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(true);
		frame.setTitle("Overview");

		frame.pack();
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		frame.setLocation(dim.width / 2 - frame.getSize().width / 2, dim.height / 2 - frame.getSize().height / 2);

		frame.setVisible(true);
		
		
	}
	
	public void updateGen(int gen){
		tf_gen.setText(Integer.toString(gen));
		updateTime();
	}
	
	public void updateAnt(int ant){
		tf_ant.setText(Integer.toString(ant));
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
