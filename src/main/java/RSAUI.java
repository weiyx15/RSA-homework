import rsa.RSA;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;

public class RSAUI extends JFrame {
    private RSA rsa = new RSA(1024);

    private final int WIDTH = 1200;
    private final int HEIGHT = 900;

    private Box contentBox;

    private Box genKeyBox;
    private Box genKeyUpperBox;
    private Box genKeyLowerBox;
    private Box publicKeyBox;
    private Box privateKeyBox;
    private JLabel bitLengthLabel;
    private JComboBox<Integer> bitLengthCombo;
    private JButton genKeyButton;
    private JLabel publicKeyLabel;
    private JLabel privateKeyLabel;
    private JTextArea publicKeyDisp;
    private JTextArea privateKeyDisp;

    private Box encryptBox;
    private JButton encryptButton;
    private JLabel encryptInLabel;
    private JLabel encryptOutLabel;
    private JTextArea encryptInput;
    private JTextArea encryptOutput;

    private Box decryptBox;
    private JButton decryptButton;
    private JLabel decryptInLabel;
    private JLabel decryptOutLabel;
    private JTextArea decryptInput;
    private JTextArea decryptOutput;

    public RSAUI() {
        super("RSA by weiyuxuan");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(WIDTH, HEIGHT));
        setResizable(false);

        contentBox = Box.createHorizontalBox();

        genKeyBox = Box.createVerticalBox();
        encryptBox = Box.createVerticalBox();
        decryptBox = Box.createVerticalBox();

        genKeyUpperBox = Box.createHorizontalBox();
        genKeyLowerBox = Box.createHorizontalBox();

        publicKeyBox = Box.createVerticalBox();
        privateKeyBox = Box.createVerticalBox();

        bitLengthLabel = new JLabel("bit length");
        bitLengthCombo = new JComboBox<Integer>(new Integer[]{1024, 2048});
        genKeyButton = new JButton("gen key");
        publicKeyLabel = new JLabel("public key (Hex)");
        publicKeyLabel.setLabelFor(publicKeyDisp);
        privateKeyLabel = new JLabel("private key (Hex)");
        privateKeyLabel.setLabelFor(privateKeyDisp);
        publicKeyDisp = new JTextArea(18, 30);
        privateKeyDisp = new JTextArea(18, 30);
        publicKeyDisp.setEditable(false);
        privateKeyDisp.setEditable(false);
        publicKeyDisp.setLineWrap(true);
        privateKeyDisp.setLineWrap(true);

        encryptButton = new JButton("Encrypt");
        encryptInLabel = new JLabel("Input (Unicode in UTF-8)");
        encryptInLabel.setLabelFor(encryptInput);
        encryptOutLabel = new JLabel("Output (Hex)");
        encryptOutLabel.setLabelFor(encryptOutput);
        encryptInput = new JTextArea(18, 18);
        encryptOutput = new JTextArea(18, 18);
        encryptInput.setLineWrap(true);
        encryptOutput.setLineWrap(true);

        decryptButton = new JButton("Decrypt");
        decryptInLabel = new JLabel("Input (Hex)");
        decryptInLabel.setLabelFor(decryptInput);
        decryptOutLabel = new JLabel("Output (Unicode in UTF-8)");
        decryptOutLabel.setLabelFor(decryptOutput);
        decryptInput = new JTextArea(18, 18);
        decryptOutput = new JTextArea(18, 18);
        decryptInput.setLineWrap(true);
        decryptOutput.setLineWrap(true);

        genKeyUpperBox.add(bitLengthLabel);
        genKeyUpperBox.add(bitLengthCombo);
        genKeyUpperBox.add(genKeyButton);

        publicKeyBox.add(publicKeyLabel);
        publicKeyBox.add(Box.createVerticalStrut(4));
        publicKeyBox.add(publicKeyDisp);
        publicKeyBox.add(privateKeyLabel);
        publicKeyBox.add(Box.createVerticalStrut(4));
        publicKeyBox.add(privateKeyDisp);

        genKeyLowerBox.add(publicKeyBox);
        genKeyLowerBox.add(privateKeyBox);

        genKeyBox.add(genKeyUpperBox);
        genKeyBox.add(genKeyLowerBox);

        encryptBox.add(encryptButton);
        encryptBox.add(encryptInLabel);
        encryptBox.add(Box.createVerticalStrut(4));
        encryptBox.add(encryptInput);
        encryptBox.add(Box.createVerticalStrut(4));
        encryptBox.add(encryptOutLabel);
        encryptBox.add(Box.createVerticalStrut(4));
        encryptBox.add(encryptOutput);

        decryptBox.add(decryptButton);
        decryptBox.add(decryptInLabel);
        decryptBox.add(Box.createVerticalStrut(4));
        decryptBox.add(decryptInput);
        decryptBox.add(Box.createVerticalStrut(4));
        decryptBox.add(decryptOutLabel);
        decryptBox.add(Box.createVerticalStrut(4));
        decryptBox.add(decryptOutput);

        contentBox.add(Box.createHorizontalStrut(4));
        contentBox.add(genKeyBox);
        contentBox.add(Box.createHorizontalStrut(4));
        contentBox.add(encryptBox);
        contentBox.add(Box.createHorizontalStrut(4));
        contentBox.add(decryptBox);
        contentBox.add(Box.createHorizontalStrut(4));

        this.add(contentBox);

        bitLengthCombo.addActionListener(e -> {
            Integer bitLength = (Integer) bitLengthCombo.getSelectedItem();
            rsa.setBitLength(bitLength==null? 1024: bitLength);
        });

        genKeyButton.addActionListener(e -> {
            HashMap<String, String> kp = rsa.genKeyPair();
            publicKeyDisp.setText(kp.get("publicKey"));
            privateKeyDisp.setText(kp.get("privateKey"));
        });

        encryptButton.addActionListener(e -> {
            String msg = encryptInput.getText();
            String enc = rsa.encrypt(msg);
            encryptOutput.setText(enc);
        });

        decryptButton.addActionListener(e -> {
            String msg = decryptInput.getText();
            String dec = rsa.decrypt(msg);
            decryptOutput.setText(dec);
        });

        initDisp("楠姐最美");
    }

    private void initDisp(String msg) {
        HashMap<String, String> kp = rsa.genKeyPair();
        publicKeyDisp.setText(kp.get("publicKey"));
        privateKeyDisp.setText(kp.get("privateKey"));

        encryptInput.setText(msg);
        String enc = rsa.encrypt(msg);
        encryptOutput.setText(enc);

        decryptInput.setText(enc);
        String dec = rsa.decrypt(enc);
        decryptOutput.setText(dec);
    }
}
