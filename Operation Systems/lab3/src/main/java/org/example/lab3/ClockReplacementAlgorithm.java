package org.example.lab3;

import java.util.List;

public interface ClockReplacementAlgorithm {
    static int replacePage(List<Page> mem,
                           List<Integer> clock,
                           int replacePageNum,
                           Integer handIndex,
                           ControlPanel controlPanel) {
        while (true) {
            if(handIndex == clock.size())
                handIndex = 0;
            if (mem.get(clock.get(handIndex)).R == 1){
                mem.get(clock.get(handIndex)).R = 0;
            } else {
                break;
            }
            handIndex++;
        }
        Page page = mem.get(clock.get(handIndex));
        Page nextPage = mem.get( replacePageNum );
        controlPanel.removePhysicalPage( page.id );
        nextPage.physical = page.physical;
        controlPanel.addPhysicalPage( nextPage.physical , replacePageNum );
        page.inMemTime = 0;
        page.lastTouchTime = 0;
        page.R = 0;
        page.M = 0;
        page.physical = -1;
        clock.set(handIndex, nextPage.id);
        return handIndex;
    }
}
