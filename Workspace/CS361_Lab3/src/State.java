/**
 * File Name: ${FILE_NAME}
 * Created by: Alexander Molodyh
 * Western Oregon University
 * Class: CS260
 * Created: 6/1/2017
 * Assignment:
 */
public class State
{
    private boolean acceptState = false;
    private String q = "q";
    private int stateNum;

    private State() {}

    private State(int stateNum) {this.stateNum = stateNum;}

    private State(int stateNum, boolean acceptState)
    {
        this.stateNum = stateNum;
        this.acceptState = acceptState;
    }

    public boolean isAcceptState() {return acceptState;}

    public String getState() {return q + stateNum;}

    public char getStateChar()
    {
        char c = q.charAt(0);
        return c;
    }

    public int getStateNum() {return stateNum;}

    @Override
    public boolean equals(Object o)
    {
        State tempState = (State) o;

        if(tempState.getState().equalsIgnoreCase(getState()) && tempState.isAcceptState() == acceptState)
            return true;
        else
            return false;
    }

    @Override
    public int hashCode()
    {
        int result = 17;
        result = 31 * result + getState().hashCode();
        result = 31 * result + (acceptState ? 1 : 0);
        return result;
    }

    @Override
    public String toString()
    {
        String temp = "State: " + getState() + "\nAcceptState: " + acceptState;
        return temp;
    }

    public static State makeState(int stateNum, boolean acceptState) {return new State(stateNum, acceptState);}

    public static State makeState(int stateNum) {return new State(stateNum);}
}
