package ui;

import entitiy.StateMachine;

import javax.swing.*;

/**
 * Created by mohamadamin on 1/15/17.
 */
public class MainList extends JFrame {

    private StateMachine stateMachine;
    private JLabel infoLabel;

    public MainList(StateMachine stateMachine) {
        this.stateMachine = stateMachine;
        showBox();
    }

    private void showBox() {
        Box box = Box.createVerticalBox();
        add(box);

        infoLabel = new JLabel(String.format("State machine with %d states", stateMachine.getStates()));
        infoLabel.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        box.add(infoLabel);

        JButton adjustList = new JButton("Adjacent list");
        adjustList.addActionListener(e -> new AdjacentTable(stateMachine));
        adjustList.setAlignmentX(JButton.CENTER_ALIGNMENT);
        box.add(adjustList);

        JButton showGraph = new JButton("Graph");
        showGraph.addActionListener(e -> stateMachine.drawGraph());
        showGraph.setAlignmentX(JButton.CENTER_ALIGNMENT);
        box.add(showGraph);

        JButton checkString = new JButton("Check String");
        checkString.addActionListener(e -> {
            String input = JOptionPane.showInputDialog("Enter the string to check:");
            JOptionPane.showMessageDialog(this,
                    String.format("%s is%s valid", input, stateMachine.hasString(input) ? "" : " not"));
        });
        checkString.setAlignmentX(JButton.CENTER_ALIGNMENT);
        box.add(checkString);

        JButton hasLoop = new JButton("Has Loop?");
        hasLoop.addActionListener(e -> JOptionPane.showMessageDialog(this,
                String.format("This state machine %s loop", stateMachine.hasLoop() ? "has" : "doesn't have")));
        hasLoop.setAlignmentX(JButton.CENTER_ALIGNMENT);
        box.add(hasLoop);

        JButton removeLoop = new JButton("Remove Loop");
        removeLoop.addActionListener(e -> {
            stateMachine.removeLoops();
            JOptionPane.showMessageDialog(this, "All loops removed :D");
        });
        removeLoop.setAlignmentX(JButton.CENTER_ALIGNMENT);
        box.add(removeLoop);

        pack();
        setVisible(true);

    }

}
