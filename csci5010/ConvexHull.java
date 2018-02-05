package csci5010;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class ConvexHull {
	private static Collection<Point> giftWrap(Collection<Point> points) {
		LinkedList<Point> extreme = new LinkedList<Point>();
		Stack<Point> upperhull = new Stack<Point>();
		Stack<Point> lowerhull = new Stack<Point>();
		Point[] list = new Point[points.size()];
		points.toArray(list);
		Arrays.sort(list, new Comparator<Point>() {
			public int compare(Point x, Point y) {
				return (int)(x.getX() - y.getX());
			}
		});
		
		upperhull.push(list[0]);
		upperhull.push(list[1]);
		for (int i = 2; i < list.length; i++) {
			upperhull.push(list[i]);
			while (upperhull.size() > 2) {
				Point r = upperhull.pop();
				Point q = upperhull.pop();
				Point p = upperhull.pop();
				if (orient(p, q, r) < 0) {
					upperhull.push(p);
					upperhull.push(q);
					upperhull.push(r);
					break;
				} else {
					upperhull.push(p);
					upperhull.push(r);
				}
			}
		}
		upperhull.pop();
		extreme.addAll(upperhull);

		lowerhull.push(list[list.length - 1]);
		lowerhull.push(list[list.length - 2]);
		for (int i = list.length - 3; i >= 0; i--) {
			lowerhull.push(list[i]);
			while (lowerhull.size() > 2) {
				Point r = lowerhull.pop();
				Point q = lowerhull.pop();
				Point p = lowerhull.pop();
				if (orient(p, q, r) < 0) {
					lowerhull.push(p);
					lowerhull.push(q);
					lowerhull.push(r);
					break;
				} else {
					lowerhull.push(p);
					lowerhull.push(r);
				}
			}
		}
		lowerhull.pop();
		extreme.addAll(lowerhull);
		return extreme;
	}
	
	private static double orient(Point p, Point q, Point r) {
		double px = p.getX();
		double py = p.getY();
		double qx = q.getX();
		double qy = q.getY();
		double rx = r.getX();
		double ry = r.getY();
		return qx * ry - qy * rx - px * ry + py * rx + px * qy - py * qx;
	}
	
	// The plotting part does not contains any geometric algorithm, feel free to skip it.
	public static void main(String[] args) throws Exception {
		JFrame f = new JFrame();
		f.setSize(300, 300);
		f.setLocation(300, 300);
		f.setResizable(false);
		JPanel jp = new JPanel() {
			public Collection<Point> list;
			public Collection<Point> extreme;
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
						extreme.clear();
						repaint();
					}
				}));
				this.add(new JButton(new AbstractAction("Compute") {
					@Override
					public void actionPerformed(ActionEvent e) {
						if (list.size() < 3) {
							System.out.println("Please give at least 3 points");
							return;
						}
						extreme = giftWrap(new ArrayList(list));
						repaint();
					}
				}));
				this.list = new LinkedList<Point>();
				this.extreme = new LinkedList<Point>();
			}
			public void paint(Graphics g) {
				super.paint(g);
				g.setColor(Color.RED);
				for (Point p: list) {
					g.fillOval((int)p.getX(), (int)p.getY(), 5, 5);
				}
				if (!extreme.isEmpty()) {
					int[] pointX = new int[extreme.size()];
					int[] pointY = new int[extreme.size()];
					int cnt = 0;
					for (Point p: extreme) {
						pointX[cnt] = (int)p.getX();
						pointY[cnt] = (int)p.getY();
						cnt++;
					}
					g.setColor(Color.BLUE);
					g.drawPolygon(pointX, pointY, extreme.size());
					g.setColor(Color.RED);
					for (Point p: extreme) {
						g.fillOval((int)p.getX(), (int)p.getY(), 5, 5);
					}
				}
			}
		};
		f.add(jp);
		f.setVisible(true);
	}
}