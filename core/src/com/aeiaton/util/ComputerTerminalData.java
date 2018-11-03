package com.aeiaton.util;

import java.util.HashMap;

public class ComputerTerminalData {
    
    private HashMap<Integer, TerminalData> map = new HashMap<>();
    
    public ComputerTerminalData() {
        map.put(0, new TerminalData("Access denied"));
        map.put(1, new TerminalData("Lorem ipsum dolor sit amet, mucius instructior eum ei, ad facer nostro nostrud pro, ea tation accumsan eam. Modus ornatus per cu, vim an dignissim voluptaria. Verear delicata dissentias ei eum, soleat delectus vis cu, mutat mucius prompta ne mea. Et sea mucius eruditi disputando, alia probo patrioque et mei, ut sea audire reformidans. Iudico alterum fierent sea in, at per fabulas interesset. Eam ut saepe tollit.\r\n"));
    }
    
    public String getPrompt(int i) {
        return map.get(i).prompt;
    }
    
    private class TerminalData {
        
        public String prompt;
        
        public TerminalData(String s) {
            prompt = s;
        }
        
    }

}
