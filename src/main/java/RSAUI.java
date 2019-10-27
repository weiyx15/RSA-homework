import rsa.RSA;

import javax.swing.*;
import java.awt.*;

public class RSAUI extends JFrame {
    private RSA rsa = new RSA(1024);

    private final int WIDTH = 900;
    private final int HEIGHT = 900;

    private JPanel content;

    private JPanel genKeyPanel;
    private JPanel upperGenKeyPanel;
    private JPanel lowerGenKeyPanel;
    private JLabel bitLengthLabel;
    private JComboBox<Integer> bitLengthCombo;
    private JButton genKeyButton;
    private JLabel publicKeyLabel;
    private JLabel privateKeyLabel;
    private JTextArea publicKeyDisp;
    private JTextArea privateKeyDisp;

    private JPanel encryptPanel;
    private JLabel encryptLabel;
    private JLabel encryptInLabel;
    private JLabel encryptOutLabel;
    private JTextArea encryptInput;
    private JTextArea encryptOutput;

    private JPanel decryptPanel;
    private JLabel decryptLabel;
    private JLabel decryptInLabel;
    private JLabel decryptOutLabel;
    private JTextArea decryptInput;
    private JTextArea decryptOuput;

    public RSAUI() {
        super("RSA by weiyuxuan");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(WIDTH, HEIGHT));
        setResizable(false);

        content = new JPanel();

        content.setLayout(new GridLayout(1, 3));

        genKeyPanel = new JPanel(new GridLayout(5, 1));
        encryptPanel = new JPanel();
        decryptPanel = new JPanel();

        upperGenKeyPanel = new JPanel();
//        lowerGenKeyPanel = new JPanel();


//        content.setLayout(new GridLayout(1, 3));
//
//        genKeyPanel = new JPanel(new GridLayout(2, 1));
//        encryptPanel = new JPanel(new GridLayout(5, 1));
//        decryptPanel = new JPanel(new GridLayout(5, 1));
//
//        upperGenKeyPanel = new JPanel(new GridLayout(1, 3));
//        lowerGenKeyPanel = new JPanel(new GridLayout(3, 1));

        bitLengthLabel = new JLabel("bit length");
        bitLengthCombo = new JComboBox<Integer>(new Integer[]{1024, 2048});
        genKeyButton = new JButton("gen key");
        publicKeyLabel = new JLabel("public key");
        privateKeyLabel = new JLabel("private key");
        publicKeyDisp = new JTextArea(20, 20);
        privateKeyDisp = new JTextArea(20, 20);

        encryptLabel = new JLabel("Encrypt");
        encryptInLabel = new JLabel("Input (Unicode in UTF-8)");
        encryptOutLabel = new JLabel("Output (Hex)");
        encryptInput = new JTextArea(20, 20);
        encryptOutput = new JTextArea(20, 20);

        decryptLabel = new JLabel("Decrypt");
        decryptInLabel = new JLabel("Input (Hex)");
        decryptOutLabel = new JLabel("Output (Unicode in UTF-8)");
        decryptInput = new JTextArea(20, 20);
        decryptOuput = new JTextArea(20, 20);

        upperGenKeyPanel.add(bitLengthLabel);
        upperGenKeyPanel.add(bitLengthCombo);
        upperGenKeyPanel.add(genKeyButton);

//        lowerGenKeyPanel.add(publicKeyLabel);
//        lowerGenKeyPanel.add(publicKeyDisp);
//        lowerGenKeyPanel.add(privateKeyLabel);
//        lowerGenKeyPanel.add(privateKeyDisp);

        genKeyPanel.add(upperGenKeyPanel);
//        genKeyPanel.add(lowerGenKeyPanel);
        genKeyPanel.add(publicKeyLabel);
        genKeyPanel.add(publicKeyDisp);
        genKeyPanel.add(privateKeyLabel);
        genKeyPanel.add(privateKeyDisp);

        encryptPanel.add(encryptLabel);
        encryptPanel.add(encryptInLabel);
        encryptPanel.add(encryptInput);
        encryptPanel.add(encryptOutLabel);
        encryptPanel.add(encryptOutput);

        decryptPanel.add(decryptLabel);
        decryptPanel.add(decryptInLabel);
        decryptPanel.add(decryptInput);
        decryptPanel.add(decryptOutLabel);
        decryptPanel.add(decryptOuput);

        content.add(genKeyPanel);
        content.add(encryptPanel);
        content.add(decryptPanel);

        this.add(content);
    }
}
