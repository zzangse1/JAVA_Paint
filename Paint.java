package Paint;

import java.awt.Color;
import java.awt.Container;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;


public class Paint extends JFrame implements ActionListener {
	JPanel jf = new JPanel();
	JButton btn1, btn2;
	Point startPt, endPt;
	boolean mode = true;
	BufferedImage mainImg;;
	private Vector<Point> vStart = new Vector<Point>();
	private Vector<Point> vEnd = new Vector<Point>();
	MyPanel me;
	MyPanel meh;
	
	Container c = getContentPane();
	public Paint() {
		c.setLayout(null);

		c.add(jf);
		mainImg = new BufferedImage(700, 700, BufferedImage.TYPE_INT_RGB);
		mainImg.getGraphics().setColor(Color.green);
		mainImg.getGraphics().fillRect(0, 0, 700, 700);
		
		me = new MyPanel(jf, mainImg);
		jf.addMouseListener(meh);
		jf.addMouseMotionListener(meh);

		btn1 = new JButton("펜 그리기");
		btn1.setOpaque(true);
		btn1.setBackground(Color.YELLOW);
		btn1.setLocation(250, 0);
		btn1.setSize(100, 50);
		btn1.addActionListener(this);
		c.add(btn1);

		btn2 = new JButton("페인트 통");
		btn2.setOpaque(true);
		btn2.setBackground(Color.GREEN);
		btn2.setLocation(400, 0);
		btn2.setSize(100, 50);
		btn2.addActionListener(this);
		c.add(btn2);

		MyPanel handlerObj = new MyPanel(jf, mainImg);
		
		this.addMouseListener(handlerObj);
		this.addMouseMotionListener(handlerObj);

		setSize(800,800);
		setLocation(300, 300);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);
		g.drawImage(mainImg, 100, 100, 700, 700, 100, 100, 700, 700, jf);

		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (e.getSource() == btn1) {
			System.out.println("펜 모드");
			mode = true;
			System.out.println(mode);
		} else if (e.getSource() == btn2) {
			System.out.println("페인트 통");
			mode = false;
			System.out.println(mode);
		}
	}

	class MyPanel extends MouseAdapter {
		BufferedImage img;
		JPanel jf;
		int rgb;

		// Color co1; Color co2;
		public MyPanel(JPanel j, BufferedImage i) {
			jf = j;
			img = i;

		}

		// int ipx, ipy;
		public void colorstart(Point pt) {
			int mainColor;
			int myC = 255;
			Queue<Point> q = new LinkedList<>();
			q.add(pt);
			// 비교기준 색상
			mainColor = img.getRGB(pt.x, pt.y);
			//img.setRGB(120, 255, myC);
			// q.size();
			// for (int i = 0; i < 100 && q.size() > 0; i++) {
			while (q.size() > 0) {
				Point p = q.poll();
				Point up = new Point(p.x, p.y - 1);
				Point down = new Point(p.x, p.y + 1);
				Point left = new Point(p.x - 1, p.y);
				Point right = new Point(p.x + 1, p.y);
		
//				img.setRGB(100, 100, myC);
//				img.setRGB(699, 100, myC);
//				img.setRGB(699, 699, myC);
//				img.setRGB(100, 699, myC);
				if(up.y>=100 && mainColor == img.getRGB(up.x, up.y)) {
					img.setRGB(up.x, up.y, myC);
					q.add(up);
					
				}
				if(down.y<700) {
					if (mainColor == img.getRGB(down.x, down.y)) {
						img.setRGB(down.x, down.y, myC);
						q.add(down);
					}
				}
				
				if(left.x>=100) {
					if (mainColor == img.getRGB(left.x, left.y)) {
						img.setRGB(left.x, left.y, myC);
						q.add(left);
					}
				}
				if(right.x<700) {
					if (mainColor == img.getRGB(right.x, right.y)) {
						img.setRGB(right.x, right.y, myC);
						q.add(right);
					}
				}
				repaint();
			}
		}

		public void mouseDragged(MouseEvent e) {
			if (mode == true) {
				vStart.add(e.getPoint());
				// repaint();
			}
		}

		public void mousePressed(MouseEvent e) {
			if (mode == true) {
				vStart.add(null);
				startPt = e.getPoint();
				vStart.add(startPt);
			} else {
				// System.out.println("mP: "+e.getPoint());
//				mainColor = img.getRGB(e.getX(), e.getY());
//				System.out.println("MP: "+mainColor);
				colorstart(e.getPoint());

			}
		}

		public void mouseMoved(MouseEvent e) {
			// TODO Auto-generated method stub
			// System.out.println(e.getX() + " , " + e.getY());
		}

		public void mouseReleased(MouseEvent e) {
			Graphics g2d = (Graphics) img.getGraphics();
			g2d.setColor(Color.black);

			if (mode == true) {
				for (int i = 1; i < vStart.size(); i++) {
					if (vStart.get(i - 1) == null)

						continue;
					else if (vStart.get(i) == null)
						continue;
					else
						g2d.drawLine((int) vStart.get(i - 1).getX(), (int) vStart.get(i - 1).getY(),
								(int) vStart.get(i).getX(), (int) vStart.get(i).getY());
				}
				endPt = e.getPoint();
				vEnd.add(endPt);
				repaint();
			}
		}
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		Paint app = new Paint();

	}
}