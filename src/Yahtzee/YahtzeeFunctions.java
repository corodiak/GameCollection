package Yahtzee;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Jann-Niklas on 02.09.2017.
 */
public class YahtzeeFunctions {

    public static void throwDice(ArrayList<JCheckBox> throwableDice) {
        Random rn = new Random();
        int newDieValue = 0;
        for (JCheckBox die : throwableDice) {
            newDieValue = rn.nextInt(6) + 1;
            die.setName(Integer.toString(newDieValue));
        }
    }
}
