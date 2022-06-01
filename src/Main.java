import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class Main extends JFrame implements ActionListener {
    JButton attack = new JButton("RUN");
    Boolean myflag = true;
    JPanel cont;
    JPanel p3;
    JTextArea t1;
    JTextArea t2;
    String total ="";

    public Main(){
        this.setTitle("ISA COMPILER");
        this.setSize(new Dimension(1300, 700));
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocation(0, 0);

        cont = new JPanel();
        cont.setPreferredSize(this.getSize());
        cont.setVisible(true);
        cont.setBackground(new Color(0x202124));

        JPanel p1 = new JPanel();
        p1.setBackground(Color.BLACK);
        p1.setPreferredSize(new Dimension(600, 500));
        p1.setLayout(null);
        JLabel head1 = new JLabel("Write Code Here");
        head1.setBounds(0, 0, 600, 30);
        head1.setFont(new Font("Verdana", Font.BOLD, 19));
        head1.setHorizontalAlignment(JLabel.CENTER);
        head1.setVerticalAlignment(JLabel.NORTH);
        head1.setForeground(Color.white);
        t1 = new JTextArea();
        t1.setBackground(new Color(0x303134));
        t1.setFont(new Font("Helvetica", Font.BOLD, 16));
        t1.setForeground(Color.white);
        t1.setBounds(0, 30, 600, 470);
        t1.setEditable(true);
        JScrollPane s1 = new JScrollPane(t1);
        s1.setBounds(0,30,600,470);
        s1.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        s1.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        p1.add(head1);
        p1.add(s1);
        cont.add(p1);

        JPanel p2 = new JPanel();
        p2.setBackground(Color.BLACK);
        p2.setPreferredSize(new Dimension(600, 500));
        p2.setLayout(null);
        JLabel head2 = new JLabel("Code Output");
        head2.setBounds(0, 0, 600, 30);
        head2.setFont(new Font("Verdana", Font.BOLD, 19));
        head2.setHorizontalAlignment(JLabel.CENTER);
        head2.setVerticalAlignment(JLabel.NORTH);
        head2.setForeground(Color.white);
        t2 = new JTextArea();
        t2.setBackground(new Color(0x303134));
        t2.setFont(new Font("Helvetica", Font.BOLD, 16));
        t2.setBounds(0, 30, 600, 470);
        t2.setEditable(false);
        t2.setForeground(Color.cyan);
        JScrollPane s2 = new JScrollPane(t2);
        s2.setBounds(0, 30, 600,470 );
        s2.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        s2.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        p2.add(head2);
        p2.add(s2);
        cont.add(p2);

        attack.addActionListener(this);
        attack.setPreferredSize(new Dimension(120, 50));
        attack.setBackground(new Color(0x9D8023));
        attack.setForeground(Color.white);
        cont.add(attack);
        this.add(cont);
        this.setVisible(true);
    }

    public static void main(String[] args) {
        Main m = new Main();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(attack)) {
            Processor processor = new Processor();
            try {
                processor.runProgram(t1.getText());
                t2.setText(processor.outputString);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }
    }
}