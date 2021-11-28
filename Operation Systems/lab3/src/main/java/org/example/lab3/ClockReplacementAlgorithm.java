package org.example.lab3;

import java.util.List;

public interface ClockReplacementAlgorithm {
    static int replacePage(List<Page> mem,
                            int virtualPageNumber,
                            int replacePageNum,
                            Integer handIndex,
                            ControlPanel controlPanel) {
        Page page = mem.get( handIndex );
        Page nextPage = mem.get( replacePageNum );
        controlPanel.removePhysicalPage( handIndex );
        nextPage.physical = page.physical;
        controlPanel.addPhysicalPage( nextPage.physical , replacePageNum );
        page.inMemTime = 0;
        page.lastTouchTime = 0;
        page.R = 0;
        page.M = 0;
        page.physical = -1;
        int counter = handIndex + 1;
        while (counter != handIndex) {
            if (mem.get(counter).physical != -1) {
                if (mem.get(counter).M != 1 && counter != replacePageNum) {
                    break;
                }
            }
            counter++;
            if (counter == virtualPageNumber) {
                counter = 0;
            }
        }
        if (counter != handIndex){
            return counter;
        } else {
            counter++;
            while(mem.get(counter).physical == -1){
                counter++;
                if (counter == virtualPageNumber) {
                    counter = 0;
                }
            }
            return counter;
        }
    }


}
