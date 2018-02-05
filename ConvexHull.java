import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

class ConvexHull {
	public static void main(String[] args) throws Exception {
		JFrame f = new JFrame();
		f.setSize(300, 300);
		f.setLocation(300, 300);
		f.setResizable(false);
		JPanel jp = new JPanel() {
			public LinkedList<Point> list = new LinkedList<Point>();
			{
				addMouseListener(new MouseAdapter() {
					public void mouseClicked(MouseEvent e) {
						list.add(e.getPoint());
						repaint();
					}
				});
				this.add(new JButton(new AbstractAction("Clear") {
					@Override
					public void actionPerformed(ActionEvent e) {
						list.clear();
						repaint();
					}
				}));
				this.add(new JButton(new AbstractAction("Compute") {
					@Override
					public void actionPerformed(ActionEvent e) {
						list.clear();
						repaint();
					}
				}));
			}
			public void paint(Graphics g) {
				super.paint(g);
				g.setColor(Color.RED);
				for (Point p: list) {
					g.fillOval((int)p.getX(), (int)p.getY(), 5, 5);
				}
			}
		};
		f.add(jp);
		f.setVisible(true);
	}
}