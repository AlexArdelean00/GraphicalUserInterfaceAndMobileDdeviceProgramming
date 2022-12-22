package view;

import controller.ControllerForView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.concurrent.TimeUnit;

public class RenderingProgress extends JDialog implements ActionListener, WindowListener {

	private final int updateDelay = 50;
	private final int MAX_RENDERING_DISPLAY_RESOLUTION = 600;

	private JProgressBar progressBar;
	private JLabel renderingInformation;
	private Timer timer;
	private JPanel container;
	private JLabel imageLabel;
	private JPanel barContainer;
	private JPanel buttonsContainer;
	private ImageIcon icon;
	private JLabel timeLeft;
	private int timerClock;

	private JButton saveBut;
	private JButton closeBut;

	public RenderingProgress() {
		super();
		this.timer = new Timer(updateDelay, this);
		this.timer.start();

		timerClock=0;
		icon = new ImageIcon();

		this.saveBut = new JButton("Save");
		this.closeBut = new JButton("Terminate");
		this.closeBut.addActionListener(this);

		this.progressBar = new JProgressBar();
		this.progressBar.setStringPainted(true);
		this.renderingInformation = new JLabel("Rendering in progress...");

		this.imageLabel = new JLabel();

		this.container = new JPanel();
		this.container.setLayout(new BorderLayout());

		this.barContainer = new JPanel();
		this.barContainer.add(this.renderingInformation);
		this.barContainer.add(this.progressBar);
		this.timeLeft = new JLabel();
		JPanel containerUp = new JPanel();
		containerUp.setLayout(new BorderLayout());
		containerUp.add(this.barContainer, BorderLayout.NORTH);
		containerUp.add(timeLeft, BorderLayout.SOUTH);

		this.container.add(containerUp, BorderLayout.NORTH);
		this.container.add(this.imageLabel, BorderLayout.CENTER);
		this.buttonsContainer = new JPanel();
		//buttonsContainer.add(saveBut);
		this.buttonsContainer.add(this.closeBut);
		this.container.add(this.buttonsContainer, BorderLayout.PAGE_END);
		this.add(this.container);

		this.addWindowListener(this);
		this.pack();
		this.setResizable(false);
		this.setModal(true);
		this.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == timer) {
			//System.out.println("timer");
			progressBar.setValue(ControllerForView.getInstance().getRenderingProgress());
			BufferedImage image = ControllerForView.getInstance().getRenderingPartialImage();

			double percent;
			if(image.getWidth()>image.getHeight())
				percent= (double)MAX_RENDERING_DISPLAY_RESOLUTION/image.getWidth();
			else
				percent = (double)MAX_RENDERING_DISPLAY_RESOLUTION/image.getHeight();

			Image resizedImage = image.getScaledInstance((int)(image.getWidth()*percent),
					(int)(image.getHeight()*percent), Image.SCALE_DEFAULT);
			icon.setImage(resizedImage);
			this.imageLabel.setIcon(icon);
			this.repaint();
			this.pack();

			timerClock++;
			this.timeLeft.setText("Time: " + convertTime(timerClock*updateDelay)
							+ " Remaining: " + convertTime(timerClock*updateDelay/(progressBar.getValue()+1)*(100-progressBar.getValue())));

			if (progressBar.getValue() == 100) {
				this.renderingInformation.setText("Rendering Completed!");
				this.buttonsContainer.add(this.saveBut);
				this.saveBut.addActionListener(this);
				this.revalidate();
				this.timer.stop();
			}

			//System.out.println(progressBar.getValue());
		}
		if(e.getSource() == saveBut) {
			//System.out.println("save Image");
			JFileChooser fc = new JFileChooser();
			fc.setCurrentDirectory(new File(System.getProperty("user.home") + System.getProperty("file.separator")+ "Desktop")); 
			fc.showDialog(null, "Save");
			ControllerForView.getInstance().saveRenderedImage(fc.getSelectedFile());


		}
		if(e.getSource() == closeBut) {
			stopRendering();
			this.dispose();
		}
	}

	private String convertTime(int millis) {
		return String.format("%dm %ds",
				TimeUnit.MILLISECONDS.toMinutes(millis),
				TimeUnit.MILLISECONDS.toSeconds(millis) -
				TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis)));
	}

	private void stopRendering() {
		//System.out.println("interrupt rendering");
		ControllerForView.getInstance().stopRenderer();
	}

	// WindowListener Interface
	@Override
	public void windowOpened(WindowEvent e) {

	}

	@Override
	public void windowClosing(WindowEvent e) {
		stopRendering();
	}

	@Override
	public void windowClosed(WindowEvent e) {

	}

	@Override
	public void windowIconified(WindowEvent e) {

	}

	@Override
	public void windowDeiconified(WindowEvent e) {

	}

	@Override
	public void windowActivated(WindowEvent e) {

	}

	@Override
	public void windowDeactivated(WindowEvent e) {

	}
}
