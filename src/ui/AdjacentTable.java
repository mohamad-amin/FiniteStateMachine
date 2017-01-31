package ui;

import entitiy.StateMachine;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.util.ArrayList;

/**
 * Created by mohamadamin on 1/16/17.
 */
public class AdjacentTable extends JFrame {

    private StateMachine stateMachine;

    public static final int CELL_HEIGHT = 40;
    private String[] columns = {"State", "Adjacent(s)"};
    private JTable table;
    private String[][] data;

    public AdjacentTable(StateMachine stateMachine) {
        this.stateMachine = stateMachine;
        fillData();
        fantasizeTable();
        showTable();
    }

    private void fillData() {
        data = new String[stateMachine.getStates()][stateMachine.getStates()];
        for (int from=0; from<stateMachine.getStates(); from++) {
            data[from][0] = String.valueOf(from);
            data[from][1] = "";
            ArrayList<Integer> adjacentNodes = stateMachine.getAdjacentNodesWithSelf(from);
            for (int j=0; j<adjacentNodes.size(); j++) {
                int to = adjacentNodes.get(j);
                data[from][1] +=  "(" + to + ", " + stateMachine.getTransition(from, to) + ") ";
                if (j+1 != adjacentNodes.size()) {
                    data[from][1] += " | ";
                }
            }
        }
        table = new JTable(data, columns);
    }

    private void fantasizeTable() {

        // Center alignment and changing height
//        DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
//        rightRenderer.setHorizontalAlignment(SwingConstants.CENTER);
//        for (int i=0; i<stateMachine.getStates(); i++) {
//            table.getColumnModel().getColumn(i).setCellRenderer(rightRenderer);
//        }
//        table.setRowHeight(CELL_HEIGHT);

    }

    private void showTable() {
        Box box = Box.createVerticalBox();
        add(box);
        JButton button = new JButton("Done");
        button.setAlignmentX(JButton.CENTER_ALIGNMENT);
        button.addActionListener(e -> {
            setVisible(false);
            dispose();
        });
        box.add(new JScrollPane(table));
        box.add(button);
        pack();
        setVisible(true);
    }

}
