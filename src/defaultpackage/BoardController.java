package defaultpackage;

import java.awt.Color;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;

public class BoardController implements ActionListener {
	private BoardGui gui;
	private int row, col;

	public BoardController(BoardGui gui, int row, int col) {
		this.gui = gui;
		this.row = row;
		this.col = col;
	}

	public void actionPerformed(ActionEvent e) {
		JButton button = (JButton) e.getSource();
		if (button.getIcon() == null) {
			try {
				setButtonImage(button);
			} catch (IOException exception) {
				exception.printStackTrace();
			}
		}
	}

	public void setButtonImage(JButton button) throws IOException {
		if (gui.pressedOnButton(row, col)) {
			button.removeActionListener(this);
			Image img = ImageIO.read(getClass().getResource(
					"/images/tent48.png"));
			button.setIcon(new ImageIcon(img));
			button.setBackground(Color.GREEN);
		} else {
			gui.showErrorMessageDialog();
		}
		if (gui.winCheck()) {
			gui.showWinDialog();
		} else if (gui.lossCheck()) {
			gui.showFailDialog();
		}
	}
}
