import java.util.ArrayList;
import java.util.Iterator;

/**
 * File Name: ${FILE_NAME}
 * Created by: Alexander Molodyh
 * Western Oregon University
 * Class: CS260
 * Created: 6/1/2017
 * Assignment:
 */
public class FA
{
    private ArrayList<State> Q;
    private ArrayList<State> F;
    private State curState;

    private String language = "";
    private State[][] transFunction;
    private State[][][] nfaTransFunc;

    private final char E = 'E';
    private char curInput;

    private boolean isDFA = true;

    public FA(ArrayList<State> Q, String language, State[][] transFunction,
              State startState, ArrayList<State> F)
    {
        this.Q = Q;
        this.language = language;
        this.transFunction = transFunction;
        this.curState = startState;
        this.F = F;
    }

    public FA(ArrayList<State> Q, String language, State[][][] nfaTransFunc,
              State startState, ArrayList<State> F)
    {
        this.Q = Q;
        this.language = language;
        this.nfaTransFunc = nfaTransFunc;
        this.curState = startState;
        this.F = F;
    }

    /**
     * isValidString checks to see if the given string is a valid language of the current DFA.
     * @param language A string representing 0's and 1's.
     * @param isDFA A boolean  that determines if the current machine is a DFA or NFA.
     * @return true if the string is valid and false otherwise.
     */
    public boolean isValidString(String language, boolean isDFA)
    {
        this.language = language;
        this.isDFA = isDFA;

        if(this.language != null)
        {
            if(!isDFA)//If current FA is not a DFA
            {
                //Check to see if language is not empty "E"
                if(language.length() < 2 && language.charAt(0) == 'E')
                    return true;
            }
            else
            {
                //If language is empty and current machine is a DFA, return false
                if(language.length() < 2 && language.charAt(0) == 'E')
                    return false;
                else
                    return run(isDFA);
            }
        }
        return false;
    }

    private boolean run(boolean isDFA)
    {
        if(isDFA)
        {
            return runDFA(language);
        }
        else
        {
            if(runNFA(language, this.curState) != null)
                return true;
        }
        return false;
    }

    private boolean runDFA(String language)
    {
        //Loop through language string
        for(int i = 0; i < language.length(); i++)
        {
            this.curInput = language.charAt(i);
            int col = Character.getNumericValue(this.curInput);
            this.curState = getNextState(curState.getStateNum(), col);

            if(this.curState == null)
                return false;
        }

        //If we are not in the accept state when language sting is finished,
        //then we don't return true.
        if(F.contains(this.curState))
            return true;

        return false;
    }

    //region old runDFA
/*    private boolean runNFA(String language, State curState)
    {
        for(int i = 0; i < language.length(); i++)
        {
            curInput = language.charAt(i);
            int col = Character.getNumericValue(curInput);
            int row = this.curState.getStateNum();

            System.out.println("\n\n\n" + "In input loop, curInput is: " + curInput + "\n");

            for(int j = 0; j < nfaTransFunc[row][col].length; j++)
            {
                //Get the epsilon column
                int eCol = nfaTransFunc[row].length - 1;

                System.out.println("    In current row column index array, Current row: " + row + " current column: " + j + " Current state: " + this.curState.getState());

                if(nfaTransFunc[row][eCol] != null)//If there's a State in epsilon column, then proceed
                {
                    for(int k = 0; k < nfaTransFunc[row][eCol].length; k++)
                    {

                        System.out.println("        In eCol, current state " + nfaTransFunc[row][eCol][k]);

                        //Create a substring from current index of the input language
                        String curString = language.substring(j);

                        //Get state from the current index of epsilon column
                        this.curState = nfaTransFunc[row][eCol][k];

                        System.out.println("        Before if runNFA, curState is: " + this.curState);
                        if(this.curState != null)
                            runNFA(curString, this.curState);

                        if(F.contains(this.curState))
                            return true;
                    }
                }

                this.curState = nfaTransFunc[row][col][j];
               // System.out.println("    After epsilon loop, curState is: " + this.curState.getState() + " with j = " + j);
                if(F.contains(this.curState))
                    return true;
            }
        }
        return false;
    }*/
    //endregion

    private State runNFA(String language, State state)
    {
        State localState = state;

        if(state != null)
        {
            System.out.println("\nIn Check if state, state is not null, State is: " + state.getState());
            System.out.println("language length is " + language.length());

            for(int i = 0; i < language.length(); i++)
            {
                System.out.println("In input loop, i = " + i + " input = " + language.charAt(i) + " state = " + state.getState());
                int row = state.getStateNum();
                int col = Character.getNumericValue(language.charAt(i));
                int epsilonCol = nfaTransFunc[row].length - 1;

                //Create substring from the next input to be read from
                String languageSub = language.substring(i + 1);
                String eLanguageSub = language.substring(i);




                if(nfaTransFunc[row][col] != null)//Check if the input column of the current State row is not null
                {
                    System.out.println("row " + row + " col " + col + " is not null");
                    //Loop through the states of the input column of the current State row
                    for(int j = nfaTransFunc[row][col].length - 1; j >= 0; j--)
                    {
                        System.out.println("Looping through row " + row + " col " + col + " j = " + j);

                        //Check if the epsilon column is not null
                        if(nfaTransFunc[row][epsilonCol] != null)
                        {
                            localState = checkEpsilonPath(eLanguageSub, row, epsilonCol);
                            if(localState != null)
                                return localState;
                        }

                        if(j > 0)
                        {
                            localState = runNFA(languageSub, nfaTransFunc[row][col][j]);
                            if(F.contains(localState))
                                return localState;
                        }
                        else
                        {
                            localState = nfaTransFunc[row][col][j];
                        }
                    }

                    //Must return null, otherwise it will just keep skipping over unwanted input
                    if(localState == null)
                        return null;
                }
                else if(nfaTransFunc[row][epsilonCol] != null)
                {
                    //Check if the epsilon column is not null
                    localState = checkEpsilonPath(eLanguageSub, row, epsilonCol);
                    if(localState != null)
                        return localState;
                }
                else return null;

                if(F.contains(localState))
                    return localState;
            }
        }
        return null;
    }

    private State checkEpsilonPath(String language, int row, int epsilonCol)
    {
        System.out.println("epsilon col is not null");
        //Loop through the states of the epsilon column of the current State row
        for(int k = 0; k < nfaTransFunc[row][epsilonCol].length; k++)
        {
            System.out.println("Looping through row " + row + " epsilon column k = " + k);
            System.out.println("The state is " + nfaTransFunc[row][epsilonCol][k].getState());
            State s = nfaTransFunc[row][epsilonCol][k];
            State eTemp = runNFA(language, s);
            if(F.contains(eTemp))
                return eTemp;
        }

        return null;
    }

    private State getNextState(int row, int col)
    {
        if(transFunction != null && transFunction[row][col] != null)
            return transFunction[row][col];
        else
            return null;
    }
}















