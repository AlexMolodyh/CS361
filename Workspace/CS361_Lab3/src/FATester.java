import java.util.ArrayList;

/**
 * File Name: ${FILE_NAME}
 * Created by: Alexander Molodyh
 * Western Oregon University
 * Class: CS260
 * Created: 6/3/2017
 * Assignment:
 */
public class FATester
{


    public static void main(String[] args)
    {
        State q0 = State.makeState(0);
        State q1 = State.makeState(1);
        State q2 = State.makeState(2);
        State q3 = State.makeState(3, true);

        ArrayList<State> Q = new ArrayList<>();
        Q.add(q0);
        Q.add(q1);
        Q.add(q2);
        Q.add(q3);

        ArrayList<State> F = new ArrayList<>();
        F.add(q3);

        String language = "00001001";

        State[][] states = {{q0, q1}, {q0, q1}};
        State[][][] nfaStates = {
                {{q0}, {q0, q1}, null},
                {{q2}, null, {q2}},
                {null, {q3}, null},
                {{q3}, {q3}, null}
        };

        FA dfa = new FA(Q, language, states, q0, F);
        FA nfa = new FA(Q, language, nfaStates, q0, F);

        //System.out.print("Does DFA accept " + language + "? " + dfa.isValidString(language, true));
        System.out.print("\nDoes NFA accept " + language + " ? " + nfa.isValidString(language, false));
    }
}
