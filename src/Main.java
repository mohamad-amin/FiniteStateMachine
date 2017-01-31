import entitiy.StateMachine;
import ui.InputTable;
import ui.MainList;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by mohamadamin on 1/11/17.
 */
public class Main implements Runnable, InputTable.InputGotListener {

    private MainList mainList;
    private InputTable inputTable;
    private StateMachine stateMachine;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Main());
    }

    @Override
    public void run() {

        int states = Integer.valueOf(JOptionPane.showInputDialog("Enter the number of the states:"));
        inputTable = new InputTable(states, Main.this);

        SwingUtilities.invokeLater(() -> inputTable.showTable());

    }

    @Override
    public void gotInput(String[][] transitions, int initialState, ArrayList<Integer> finalStates) {
        for (int i=0; i<transitions.length; i++) {
            System.out.println(Arrays.toString(transitions[i]));
        }
        this.stateMachine = new StateMachine(transitions, transitions.length, initialState, finalStates);
        mainList = new MainList(stateMachine);
    }

}
