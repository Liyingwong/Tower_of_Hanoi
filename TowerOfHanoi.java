
package TowerOfHanoi;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class TowerOfHanoi extends JPanel {

    static int tower[][];
    static int top[];
    static int from, to;
    static int disk;
    static int a, b, c, d;
    static Color colors[] = {Color.WHITE, Color.PINK, Color.RED, Color.ORANGE, Color.YELLOW, Color.GREEN, Color.BLUE, Color.GRAY};

    public TowerOfHanoi() {
        tower = new int[3][10];
        top = new int[3];
    }

    static int pop(int from) {
        return (tower[from - 1][top[from - 1]--]);
    }

    static void push(int to, int diskNo) {
        tower[to - 1][++top[to - 1]] = diskNo;
    }

    Color getColor(int diskNum) {
        return colors[diskNum % 5];
    }

    void drawStill(Graphics K) {
        int j, i, disk;
        K.clearRect(0, 0, getWidth(), getHeight());
        for (j = 1; j <= 3; j++) {
            K.setColor(Color.LIGHT_GRAY);
            K.fillRoundRect(j * b, d, 5, c - d, 1, 1);
            for (i = 0; i <= top[j - 1]; i++) {
                disk = tower[j - 1][i];
                K.setColor(getColor(disk));
                K.fillRect(j * b - 15 - disk * 5, c - (i + 1) * 10, 35 + disk * 10, 10);
            }
        }
    }

    void drawFrame(Graphics K, int x, int y) {
        try {
            drawStill(K);
            K.setColor(getColor(disk));
            K.fillRect(x - 15 - disk * 5, y - 10, 35 + disk * 10, 10);
            Thread.sleep(35);
        } catch (InterruptedException ex) {
        }
    }

    void moveTopN(Graphics k, int n, int a, int b, int c) throws InterruptedException {
        if (n >= 1) {
            moveTopN(k, n - 1, a, c, b);
            drawStill(k);
            Thread.sleep(700);
            from = a;
            to = c;

            animator(k);
            moveTopN(k, n - 1, b, a, c);
        }
    }

    void animator(Graphics K) {
        int x, y, dif, sign;
        disk = pop(from);
        x = from * b;
        y = c - (top[from - 1] + 1) * 10;

        for (; y > d - 20; y -= 8) {
            drawFrame(K, x, y);
        }

        y = d - 20;
        dif = to * b - x;
        sign = dif / Math.abs(dif);

        for (; Math.abs(x - to * b) >= 24; x += sign * 12) {
            drawFrame(K, x, y);
        }
        x = to * b;

        for (; y < c - (top[to - 1] + 1) * 10; y += 8) {
            drawFrame(K, x, y);
        }
        push(to, disk);
        drawStill(K);
    }

    public static void main(String[] args) {

        int i;
        String r = "";
        r += "Rules \n\n";
        r += "\ni.   Only one disk can be moved at a time.";
        r += "\nii.  Each move consists of taking the upper disk from one of the stacks and placing it on top of another stack, that is a disk can only be moved if it is the uppermost disk on a stack.";
        r += "\niii. No disk may be placed on top of a smaller disk.\n\n";
        JOptionPane.showMessageDialog(null, r, "Tower Of Hanoi", JOptionPane.WARNING_MESSAGE);
        String q = "Tower Of Hanoi\n";
        q += "\nThe Tower of Hanoi is a mathematical puzzle involving three rods and n disc.";
        q += "\nThe objective of the puzzle is to move the entire stack of disk from the left first rod to right first rod.\n";
        q += "\nUser is to choose a total number of disk.";
        q += "\n(The number of disk should be in range of 3 - 10)\n\n";
        String s = JOptionPane.showInputDialog(q);
        a = Integer.parseInt(s);
        TowerOfHanoi hanoi = new TowerOfHanoi();

        for (i = 0; i < 3; i++) {
            top[i] = -1;
        }

        for (i = a; i > 0; i--) {
            push(1, i);
        }

        JFrame fr = new JFrame();
        fr.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        fr.setLayout(new BorderLayout());
        fr.setSize(400, 350);
        fr.setTitle("TOWER OF HANOI");
        fr.setBackground(Color.black);
        fr.add(hanoi);
        hanoi.setSize(fr.getSize());
        fr.setVisible(true);
        fr.setLocationRelativeTo(null);

        b = hanoi.getWidth() / 4;
        c = hanoi.getHeight() - 40;
        d = c - a * 12;

        try {
            hanoi.moveTopN(hanoi.getGraphics(), a, 1, 2, 3);
        } catch (Exception ex) {
        }
        JOptionPane.showMessageDialog(null, "SUCCESS!");
    }

}