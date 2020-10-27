import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

public class MMNSimulator extends JFrame {
    JTextArea outputText;

    JTextField arrive;
    JTextField need;
    JTextField people;
    JTextField server;
    JTextField max;

    JLabel stay;
    JLabel waitTime;
    JLabel waitNum;
    JLabel left;
    JTextArea usage;

    JButton create;
    JButton begin;
    JButton clear;

    Center center;

    public MMNSimulator() {
        JPanel inputPanel = new JPanel();
        inputPanel.setBorder(new TitledBorder("Input"));
        inputPanel.setPreferredSize(new Dimension(200, 500));
        Box inputBox = Box.createVerticalBox();
        inputPanel.add(inputBox);
        inputBox.add(Box.createVerticalStrut(70));
        inputBox.add(new JLabel("Average Gap Time:"));
        inputBox.add(Box.createVerticalStrut(5));
        arrive = new JTextField();
        inputBox.add(arrive);
        inputBox.add(Box.createVerticalStrut(10));
        inputBox.add(new JLabel("Average Serve Time:"));
        need = new JTextField();
        inputBox.add(Box.createVerticalStrut(5));
        inputBox.add(need);
        inputBox.add(Box.createVerticalStrut(10));
        inputBox.add(new JLabel("Total People:"));
        people = new JTextField();
        inputBox.add(Box.createVerticalStrut(5));
        inputBox.add(people);
        inputBox.add(Box.createVerticalStrut(10));
        inputBox.add(new JLabel("Total Server:"));
        server = new JTextField();
        inputBox.add(Box.createVerticalStrut(5));
        inputBox.add(server);
        inputBox.add(Box.createVerticalStrut(10));
        inputBox.add(new JLabel("Max People Waiting:"));
        max = new JTextField();
        inputBox.add(Box.createVerticalStrut(5));
        inputBox.add(max);
        inputBox.add(Box.createVerticalStrut(20));
        inputBox.add(new JLabel("@Copyright: 18373187"));

        JPanel outputPanel = new JPanel();
        outputPanel.setBorder(new TitledBorder("Result"));
        outputPanel.setPreferredSize(new Dimension(200, 500));
        Box outputBox = Box.createVerticalBox();
        outputPanel.add(outputBox);
        outputBox.add(Box.createVerticalStrut(60));
        outputBox.add(new JLabel("Average Stay Time:"));
        outputBox.add(Box.createVerticalStrut(5));
        stay = new JLabel("N/A");
        outputBox.add(stay);
        outputBox.add(Box.createVerticalStrut(10));
        outputBox.add(new JLabel("Average Wait Time:"));
        waitTime = new JLabel("N/A");
        outputBox.add(Box.createVerticalStrut(5));
        outputBox.add(waitTime);
        outputBox.add(Box.createVerticalStrut(10));
        outputBox.add(new JLabel("Average Wait People:"));
        waitNum = new JLabel("N/A");
        outputBox.add(Box.createVerticalStrut(5));
        outputBox.add(waitNum);
        outputBox.add(Box.createVerticalStrut(10));
        outputBox.add(new JLabel("People Leave"));
        outputBox.add(new JLabel("Without Served:"));
        left = new JLabel("N/A");
        outputBox.add(Box.createVerticalStrut(5));
        outputBox.add(left);
        outputBox.add(Box.createVerticalStrut(10));
        outputBox.add(new JLabel("Server Usage:"));
        outputBox.add(Box.createVerticalStrut(5));
        usage = new JTextArea();
        usage.setText("N/A");
        JScrollPane outScroll = new JScrollPane(usage);
        outputBox.add(outScroll);
        outScroll.setPreferredSize(new Dimension(40, 120));
        outScroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        outScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        usage.setEditable(false);

        JPanel buttonPanel = new JPanel();
        Box buttonBox = Box.createHorizontalBox();
        create = new JButton("Create People");
        create.addActionListener(new ReadAndCreate());
        begin = new JButton("Begin Simulation");
        begin.addActionListener(new SimulationEvent());
        clear = new JButton("Clear Text");
        clear.addActionListener(new ClearEvent());
        buttonBox.add(create);
        buttonBox.add(Box.createVerticalStrut(50));
        buttonBox.add(Box.createHorizontalStrut(50));
        buttonBox.add(begin);
        buttonBox.add(Box.createHorizontalStrut(50));
        buttonBox.add(clear);
        buttonPanel.add(buttonBox);

        JPanel textPanel = new JPanel();
        textPanel.setBorder(new TitledBorder("Outputs"));
        outputText = new JTextArea();
        JScrollPane textScroll = new JScrollPane(outputText);
        textPanel.add(textScroll);
        textScroll.setPreferredSize(new Dimension(450, 460));
        textScroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        textScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        outputText.setEditable(false);

        center = null;

        this.setLocation(400, 200);
        this.setTitle("MMN Simulator");
        this.setSize(new Dimension(1000, 600));
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.add(inputPanel, BorderLayout.WEST);
        this.add(outputPanel, BorderLayout.EAST);
        this.add(buttonPanel, BorderLayout.SOUTH);
        this.add(textPanel, BorderLayout.CENTER);
        this.setResizable(false);
        this.setVisible(true);
    }

    class ReadAndCreate implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            int averageGap;
            int averageNeed;
            int totalPeople;
            int serverNumber;
            int maxQueueLong;
            try {
                averageGap = Integer.parseInt(arrive.getText());
                averageNeed = Integer.parseInt(need.getText());
                totalPeople = Integer.parseInt(people.getText());
                serverNumber = Integer.parseInt(server.getText());
                maxQueueLong = Integer.parseInt(max.getText());
            } catch (NumberFormatException e) {
                outputText.append("=============================================================\n");
                outputText.append("Please input integer for all inputs;\nThe integer must be over zero.\n");
                outputText.append("=============================================================\n");
                return;
            }
            center = new Center(averageGap, averageNeed, totalPeople, serverNumber, maxQueueLong, outputText);
        }
    }

    class SimulationEvent implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            if (center == null) {
                outputText.append("=============================================================\n");
                outputText.append("Please create people and servers first\n");
                outputText.append("=============================================================\n");
                return;
            }
            center.beginServe();
            stay.setText("" + center.getAverageTotalTime());
            waitTime.setText("" + center.getAverageWaitingTime());
            waitNum.setText("" + center.getAverageWaitingNumber());
            left.setText("" + center.getLeftNumber());
            usage.setText("");
            HashMap<Integer, Float> idToUse = center.getServerUtilizationRate();
            for (int id : idToUse.keySet()) {
                usage.append(String.format("%02d", id) + ": " + idToUse.get(id) + "\n");
            }
        }
    }

    class ClearEvent implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            outputText.setText("");
            arrive.setText("");
            need.setText("");
            people.setText("");
            server.setText("");
            max.setText("");

            stay.setText("N/A");
            waitTime.setText("N/A");
            waitNum.setText("N/A");
            left.setText("N/A");
            usage.setText("N/A");

            center = null;
        }
    }

    public static void main(String[] args) {
        new MMNSimulator();
    }
}
