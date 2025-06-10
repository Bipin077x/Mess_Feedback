package com.messfeedback;

import com.messfeedback.ui.FeedbackMenu;
// Uncomment below only when running vote fixer
import com.messfeedback.util.VoteFixer;

public class Main {
    public static void main(String[] args) {

        // 🔧 OPTION 1: Fix votes.txt if old entries are missing 'mealType'
        // Run this only ONCE to fix your file, then comment it back.

            String voteFilePath = "votes.txt";
            VoteFixer.fixVotes(voteFilePath);  // run once to fix file

            // return;  <-- Comment or remove this after first run

            FeedbackMenu menu = new FeedbackMenu();
            menu.start();
        }

}


