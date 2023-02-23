package laddergame.util;

import laddergame.domain.Ladder;
import laddergame.domain.Line;

import java.util.HashMap;
import java.util.List;

public class Validator {
    public static void checkNull(String str) {
        if (str == null || str.trim().isEmpty()) {
            throw new IllegalArgumentException("입력에는 공백이 들어올 수 없습니다");
        }
    }

    public void validateLadder(Ladder ladder, int height) {
        List<Line> lines = ladder.getLines();
        if (height != 1) {
            HashMap<Integer, Integer> falseIndexCountmap = new HashMap<>();
            for (Line line : lines) {
                falseIndexCountmap = line.checkFalseIndex(falseIndexCountmap);
            }
            if (falseIndexCountmap.containsValue(height)) {
                throw new LadderStateException("잘못된 사다리가 만들어졌습니다.");
            }
        }
    }
}
