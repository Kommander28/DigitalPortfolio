/*
 * ----- THIS CODE IS AN ORIGINAL PRODUCTION OF THE AUTHOR: Tim Peterson ------
 */

import javax.swing.border.BevelBorder;
import javax.swing.*;
import java.awt.*;

public class QuizFrame extends JFrame {
    // initialize variables
    public boolean[] isClicked;
    public JButton[] buttonGroup;
    public JProgressBar progressBar;
    public JPanel screen;
    public JLabel header, text;


    // set color scheme - color names are from https://colornames.org/
    static final Color FALSE_WORLD_SKY = new Color(0x5082C8);
    static final Color DROWNED_DEPTHS = new Color(0x000A14);
    static final Color INFINITE_BLUE = new Color(0x94B1FF);
    static final Color JEOPARDY_BLUE = new Color(0x0000AF);
    static final Color SOFT_SLATE = new Color(0x3C5064);
    static final Color TUNGSTEN = new Color(0xF0F0F0);

    // class constructor
    QuizFrame(String titleString) {
        // initialize variables
        GridBagConstraints c;
        JLabel title = new JLabel(titleString);
        progressBar = new JProgressBar();
        screen = new JPanel();
        header = new JLabel("Header");
        text = new JLabel("Hello World!");
        this.buttonGroup = new JButton[4];
        this.isClicked = new boolean[4];

        // set up frame
        this.setSize(800, 600);
        this.setResizable(false);
        this.setLayout(new GridBagLayout());
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.getContentPane().setBackground(SOFT_SLATE);
        this.setTitle(titleString);

        // add title
        title.setFont(new Font(Font.MONOSPACED, Font.BOLD + Font.ITALIC, 28));
        title.setForeground(DROWNED_DEPTHS);
        // set constraints and add
        c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 2;
        c.fill = GridBagConstraints.VERTICAL;
        c.anchor = GridBagConstraints.PAGE_START;
        c.weighty = 0;
        this.add(title, c);

        // add progress bar
        progressBar.setBackground(TUNGSTEN);
        progressBar.setForeground(FALSE_WORLD_SKY);
        progressBar.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
        progressBar.setMinimum(0);
        progressBar.setStringPainted(false);
        progressBar.setIndeterminate(true);
        // set constraints and add
        c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 1;
        c.gridwidth = 2;
        c.fill = GridBagConstraints.BOTH;
        c.weighty = 0;
        this.add(progressBar, c);

        //add screen
        screen.setLayout(new GridBagLayout());
        screen.setBackground(JEOPARDY_BLUE);
        screen.setBorder(BorderFactory.createMatteBorder(4, 4, 4, 4, SOFT_SLATE));
        // set constraints and add
        c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 2;
        c.gridwidth = 2;
        c.fill = GridBagConstraints.BOTH;
        c.weighty = 1;
        this.add(screen, c);

        // add header to screen
        header.setFont(new Font(Font.MONOSPACED, Font.ITALIC, 18));
        header.setForeground(TUNGSTEN);
        // set constraints and add to panel
        c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 0;
        c.fill = GridBagConstraints.VERTICAL;
        c.anchor = GridBagConstraints.PAGE_START;
        c.weighty = 0.25;
        this.screen.add(header, c);

        // add text to screen
        text.setFont(new Font(Font.MONOSPACED, Font.BOLD, 16));
        text.setForeground(TUNGSTEN);
        // set constraints and add to panel
        c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 1;
        c.fill = GridBagConstraints.VERTICAL;
        c.anchor = GridBagConstraints.CENTER;
        c.weighty = 1;
        this.screen.add(text, c);

        // add buttons
        for (int i = 0; i < buttonGroup.length; i++) {
            buttonGroup[i] = new JButton();
            buttonGroup[i].setFont(new Font(Font.MONOSPACED, Font.PLAIN, 14));
            buttonGroup[i].setForeground(DROWNED_DEPTHS);
            buttonGroup[i].setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, SOFT_SLATE));
            buttonGroup[i].setFocusable(false);
            int fin = i;
            buttonGroup[i].addActionListener(e -> isClicked[fin] = true);
            isClicked[i] = false;
        }
        // set constraints for all buttons
        c = new GridBagConstraints();
        c.gridwidth = 1;
        c.gridheight = 1;
        c.fill = GridBagConstraints.BOTH;
        c.anchor = GridBagConstraints.CENTER;
        c.weightx = 1;
        c.weighty = 0.5;
        // add button1
        c.gridx = 0;
        c.gridy = 3;
        this.add(buttonGroup[0], c);
        // add button2
        c.gridx = 1;
        c.gridy = 3;
        this.add(buttonGroup[1], c);
        // add button3
        c.gridx = 0;
        c.gridy = 4;
        this.add(buttonGroup[2], c);
        // add button4
        c.gridx = 1;
        c.gridy = 4;
        this.add(buttonGroup[3], c);

        // show frame
        this.setVisible(true);
    }
}
