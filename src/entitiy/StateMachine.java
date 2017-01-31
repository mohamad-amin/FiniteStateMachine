package entitiy;

import graphviz.GraphDrawer;

import java.util.ArrayList;

/**
 * Created by mohamadamin on 1/15/17.
 */
public class StateMachine {

    private boolean[][] matrix;
    private String[][] transitions;
    private int states, initialState;
    private ArrayList<Integer> finalStates;
    private ArrayList<Integer> adjacentNodes[];

    public StateMachine(String[][] transitions, int states, int initialState, ArrayList<Integer> finalStates) {
        this.transitions = transitions;
        this.states = states;
        this.initialState = initialState;
        this.finalStates = finalStates;
        adjacentNodes = new ArrayList[states];
        matrix = new boolean[states][states];
        for (int i=0; i<states; i++) {
            for (int j=0; j<states; j++) {
                if (transitions[i][j] != null) {
                    matrix[i][j] = true;
                } else {
                    matrix[i][j] = false;
                }
            }
        }
        updateAdjacentNodes();
    }

    private void updateAdjacentNodes() {
        for (int i=0; i<states; i++) {
            adjacentNodes[i] = new ArrayList<>();
            for (int j=0; j<states; j++) {
                if (matrix[i][j] && j != i) {
                    adjacentNodes[i].add(j);
                }
            }
        }
    }

    public int getStates() {
        return states;
    }

    public int getInitialState() {
        return initialState;
    }

    public ArrayList<Integer> getFinalStates() {
        return finalStates;
    }

    private boolean hasLoopHelper(int source, int[] marks) {
        marks[source] = 1;
        ArrayList<Integer> adjacentNodes = getAdjacentNodesWithSelf(source);
        for (int i=0; i<adjacentNodes.size(); i++) {
            if (marks[adjacentNodes.get(i)] == 1) {
                return true;
            } else {
                if (hasLoopHelper(adjacentNodes.get(i), marks)) {
                    return true;
                }
            }
        }
        marks[source] = 2;
        return false;
    }

    public boolean isConnected(int from, int to) {
        return matrix[from][to];
    }

    public boolean hasLoop() {
        int[] marks = new int[states];
        return hasLoopHelper(initialState, marks);
    }

    public ArrayList<Integer> getAdjacentNodes(int source) {
        return adjacentNodes[source];
    }

    public ArrayList<Integer> getAdjacentNodesWithSelf(int source) {
        ArrayList<Integer> result = new ArrayList<>(adjacentNodes[source]);
        if (matrix[source][source]) {
            result.add(source);
        }
        return result;
    }

    public String getTransition(int from, int to) {
        return transitions[from][to];
    }

    private void removeTransition(int from, int to) {
        transitions[from][to] = null;
        matrix[from][to] = false;
        adjacentNodes[from].remove((Object) to);
        for (int i=0; i<adjacentNodes[from].size(); i++) {
            if (adjacentNodes[from].contains(to)) {
                System.out.println("BUG");
            }
        }
    }

    private void removeLoopsHelper(int source, int[] marks) {
        marks[source] = 1;
        ArrayList<Integer> adjacentNodes = getAdjacentNodesWithSelf(source);
        for (int i=0; i<adjacentNodes.size(); i++) {
            int node = adjacentNodes.get(i);
            if (marks[node] == 1) {
                removeTransition(source, node);
            } else {
                removeLoopsHelper(node, marks);
            }
        }
        marks[source] = 2;
    }

    public void removeLoops() {
        while (hasLoop()) {
            int[] marks = new int[states];
            removeLoopsHelper(initialState, marks);
        }
    }

    private boolean arrayContains(String[] array, String key) {
        for (String i : array) {
            if (key.equals(i)) {
                return true;
            }
        }
        return false;
    }

    private ArrayList<Integer> hasStringHelper(String transition, ArrayList<Integer> currentStates) {
        ArrayList<Integer> result = new ArrayList<>();
        for (int i=0; i<currentStates.size(); i++) {
            int node = currentStates.get(i);
            for (int brother : getAdjacentNodesWithSelf(node)) {
                if (transitions[node][brother] == null) {
                    System.out.println("WTF BUG");
                } else {
                    if (arrayContains(transitions[node][brother].split(","), (transition))) {
                        result.add(brother);
                    }
                }
            }
        }
        return result;
    }


    public boolean hasString(String string) {
        String[] transitions = string.split("");
        ArrayList<Integer> currentStates = new ArrayList<>();
        currentStates.add(initialState);
        for (String transition : transitions) {
            currentStates = hasStringHelper(transition, currentStates);
            if (currentStates.size() == 0) {
                return false;
            }
        }
        for (int currentState : currentStates) {
            if (finalStates.contains(currentState)) {
                return true;
            }
        }
        return false;
    }

    public void drawGraph() {
        String graphStr =
                "rankdir=LR;" + "\n" +
                        "node [shape = doublecircle]; " + initialState + ";\n"+
                        "node [shape = circle];" + "\n";
        for (int i = 0; i < states; i++) {
            ArrayList<Integer> adjacentNodes = getAdjacentNodesWithSelf(i);
            for (int j=0; j<adjacentNodes.size(); j++) {
                int node = adjacentNodes.get(j);
                graphStr = graphStr + i + " -> " + node + " [ label = \"" + transitions[i][node] + "\" ];\n";
            }
        }
        graphStr = graphStr + "}";
        GraphDrawer gd = new GraphDrawer();
        gd.draw("test.", graphStr);
    }

}
