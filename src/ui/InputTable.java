package ui;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by mohamadamin on 1/11/17.
 */
public class InputTable extends JFrame {

    public static final int
            CELL_HEIGHT = 40,
            CELL_WIDTH = 60;

    private int states;
    private Object[][] data;
    private String[] columns;
    private JTable table;

    private InputGotListener listener;

    public InputTable(int states, InputGotListener listener) {
        this.states = states;
        fillColumns();
        fillData();
        fantasizeTable();
        this.listener = listener;
    }

    private void fillColumns() {
        columns = new String[states+3];
        columns[0] = "Insert";
        for (int i=1; i<=states; i++) {
            columns[i] = String.valueOf(i-1);
        }
        columns[states+1] = "Initial";
        columns[states+2] = "Final";
    }

    private void fillData() {
        data = new Object[states][states+3];
        for (int i=0; i<states; i++) {
            data[i][0] = String.valueOf(i);
            for (int j=1; j<states+1; j++) {
                data[i][j] = " - ";
            }
        }
        for (int i=0; i<states; i++) {
            for (int j=states+1; j<states+3; j++) {
                data[i][j] = false;
            }
        }
        DefaultTableModel model = new DefaultTableModel(data, columns);
        table = new JTable(model) {
            private static final long serialVersionUID = 1L;
            @Override
            public Class getColumnClass(int column) {
                if (column > states ) {
                    return Boolean.class;
                } else {
                    return String.class;
                }
            }
        };
    }

    public void showTable() {
        Box box = Box.createVerticalBox();
        add(box);
        JButton button = new JButton("Done");
        button.setAlignmentX(JButton.CENTER_ALIGNMENT);
        button.addActionListener(e -> {
            listener.gotInput(getTransitions(), getInitialState(), getFinalStates());
            setVisible(false);
            dispose();
        });
        box.add(new JScrollPane(table));
        box.add(button);
        pack();
        setLocationRelativeTo( null );
        setSize((states+4)*CELL_WIDTH, (states+2)*CELL_HEIGHT);
        setVisible(true);
    }

    private void fantasizeTable() {

        // Center alignment and changing height
        DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
        rightRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        for (int i=0; i<=states; i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(rightRenderer);
            table.getColumnModel().getColumn(i).setPreferredWidth(CELL_WIDTH);
        }
        table.setRowHeight(CELL_HEIGHT);

    }

    private String[][] getTransitions() {
        String[][] matrix = new String[states][states];
        for (int i=0; i<states; i++) {
            for (int j=0; j<states; j++) {
                String item = String.valueOf(table.getValueAt(i, j+1));
                System.out.print(item + " ");
                if (item != null && !item.equalsIgnoreCase(" - ")) {
                    matrix[i][j] = item.trim().replaceAll(" ", "");
                } else {
                    matrix[i][j] = null;
                }
            }
        }
        return matrix;
    }

    private ArrayList<Integer> getFinalStates() {
        ArrayList<Integer> finalStates = new ArrayList<>();
        for (int i=0; i<states; i++) {
            if ((Boolean) table.getModel().getValueAt(i, states+2)) {
                finalStates.add(i);
            }
        }
        return finalStates;
    }

    private int getInitialState() {
        for (int i=0; i<states; i++) {
            if ((Boolean) table.getModel().getValueAt(i, states+1)) {
                return i;
            }
        }
        return -1;
    }

    public interface InputGotListener {
        void gotInput(String[][] transitions, int initialState, ArrayList<Integer> finalStates);
    }

}
