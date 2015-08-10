package mainPackage;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 * The Class MainFrame.
 * 
 * @author Derek Sanders
 * @version 1.0
 * @since August 10th, 2015
 */
public class MainFrame extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private SquareMatrix loadedMatrix = null;
	private Container container;

	/**
	 * Instantiates a new main frame.
	 */
	public MainFrame() {

		super("Matrix Calculator");

		setSize(800, 75);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(true);
		setLayout(new BorderLayout());

		Menu menu = new Menu();

		container = getContentPane();

		container.add(menu, BorderLayout.CENTER);
		setVisible(true);
	}

	/**
	 * The Class Menu.
	 */
	private class Menu extends JPanel {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		/**
		 * Instantiates a new menu.
		 */
		public Menu() {

			super();
			setLayout(new GridBagLayout());
			GridBagConstraints c = new GridBagConstraints();

			final JButton loadMatrix = new JButton("Load Matrix");
			final JButton exportInverseAsCSV = new JButton(
					"Export Inverse of Matrix as .csv");
			final JButton exportAsWolframInput = new JButton(
					"Export as Wolfram Alpha Input");

			loadMatrix.addMouseListener(new MouseListener() {

				@Override
				public void mouseClicked(MouseEvent arg0) {
					loadedMatrix = IOManager.LoadFile(loadMatrix);
				}

				@Override
				public void mouseEntered(MouseEvent arg0) {

				}

				@Override
				public void mouseExited(MouseEvent arg0) {

				}

				@Override
				public void mousePressed(MouseEvent arg0) {

				}

				@Override
				public void mouseReleased(MouseEvent arg0) {

				}
			});

			exportInverseAsCSV.addMouseListener(new MouseListener() {

				@Override
				public void mouseClicked(MouseEvent arg0) {
					if (loadedMatrix != null) {
						IOManager.exportInverseAsCSV(loadedMatrix,
								"matrixInverse");
						repaint();
					} else {
						JOptionPane.showMessageDialog(exportInverseAsCSV,
								"No matrix is currently loaded.",
								"Export Failed", JOptionPane.WARNING_MESSAGE);
					}
				}

				@Override
				public void mouseEntered(MouseEvent arg0) {

				}

				@Override
				public void mouseExited(MouseEvent arg0) {

				}

				@Override
				public void mousePressed(MouseEvent arg0) {

				}

				@Override
				public void mouseReleased(MouseEvent arg0) {

				}
			});

			exportAsWolframInput.addMouseListener(new MouseListener() {

				@Override
				public void mouseClicked(MouseEvent e) {
					if (loadedMatrix != null) {
						IOManager.ExportAsWolframInput(loadedMatrix,
								"wolframinput");
					} else {
						JOptionPane.showMessageDialog(exportAsWolframInput,
								"No matrix is currently loaded.",
								"Export Failed", JOptionPane.WARNING_MESSAGE);
					}
				}

				@Override
				public void mouseEntered(MouseEvent e) {

				}

				@Override
				public void mouseExited(MouseEvent e) {

				}

				@Override
				public void mousePressed(MouseEvent e) {

				}

				@Override
				public void mouseReleased(MouseEvent e) {

				}
			});

			c.gridx = 0;
			c.gridy = 1;
			c.fill = GridBagConstraints.BOTH;
			c.weightx = 1.0;
			c.weighty = 1.0;
			add(loadMatrix, c);

			c.gridx = 1;
			c.gridy = 1;
			add(exportInverseAsCSV, c);

			c.gridx = 2;
			c.gridy = 1;
			add(exportAsWolframInput, c);
		}
	}
}
