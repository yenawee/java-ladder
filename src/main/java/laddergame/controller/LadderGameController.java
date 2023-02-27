package laddergame.controller;

import laddergame.domain.*;
import laddergame.util.LadderStateException;
import laddergame.util.TrueOrFalseGenerator;
import laddergame.util.Validator;
import laddergame.view.InputView;
import laddergame.view.OutputView;

import java.util.List;
import java.util.stream.Collectors;

public class LadderGameController {
    private static final String NAME_INPUT_REQUEST = "참여할 사람 이름을 입력하세요. (이름은 쉼표(,)로 구분하세요)";
    private static final String HEIGHT_INPUT_REQUEST = "최대 사다리 높이는 몇 개인가요?";
    private static final String REWARD_INPUT_REQUEST = "실행 결과를 입력하세요. (결과는 쉼표(,)로 구분하세요)";
    private static final String TARGET_INPUT_REQUEST = "결과를 보고 싶은 사람은? (종료하려면 Q를 입력하세요!)";


    private final InputView inputView;
    private final OutputView outputView;
    private final TrueOrFalseGenerator trueOrFalseGenerator;
    private final Validator validator = new Validator();

    public LadderGameController(InputView inputView, OutputView outputView, TrueOrFalseGenerator trueOrFalseGenerator) {
        this.inputView = inputView;
        this.outputView = outputView;
        this.trueOrFalseGenerator = trueOrFalseGenerator;
    }

    public void run() {
        Players players = requestUserNames();
        Rewards rewards = requestRewards(players.getPlayersCount());
        Height height = requestLadderHeight();
        Ladder ladder = makeLadder(players, height, trueOrFalseGenerator);
        LadderGame ladderGame = new LadderGame(players, rewards, ladder);
        ladderGame.start();
        List<String> playerNames = players.getPlayers().stream().map(Player::getName).collect(Collectors.toList());
        outputView.printResult(playerNames, ladder.getLines(), players.getMaxPlayerNameLength(), rewards.getRewards());
        showLadderRunResult(playerNames, players);
    }

    private void showLadderRunResult(List<String> playerNames, Players players) {
        Target target = requestTarget(playerNames);
        if (checkShowAllOrQuit(playerNames, players, target)) {
            return;
        }
        Player targetPlayer = players.getTargetPlayer(target.getName());
        outputView.printPlayerResult(targetPlayer);
        showLadderRunResult(playerNames, players);
    }

    private boolean checkShowAllOrQuit(List<String> playerNames, Players players, Target target) {
        if (target.isQuit()) {
            return true;
        }
        if (target.isAll()) {
            outputView.printAllResult(players);
            showLadderRunResult(playerNames, players);
            return true;
        }
        return false;
    }

    private Players requestUserNames() {
        try {
            outputView.printMessage(NAME_INPUT_REQUEST);
            return new Players(inputView.readUserNames());
        } catch (IllegalArgumentException e) {
            outputView.printErrormessage(e.getMessage());
            return requestUserNames();
        }
    }

    private Height requestLadderHeight() {
        try {
            outputView.printMessage(HEIGHT_INPUT_REQUEST);
            String heightStr = inputView.readHeight();
            Height height = new Height(validator.validateHeight(heightStr));
            return height;
        } catch (IllegalArgumentException e) {
            outputView.printErrormessage(e.getMessage());
            return requestLadderHeight();
        }
    }

    private Rewards requestRewards(int playerCount) {
        try {
            outputView.printMessage(REWARD_INPUT_REQUEST);
            List<String> rewardNames = inputView.readRewards();
            validator.validateRewards(rewardNames, playerCount);
            Rewards rewards = new Rewards(rewardNames);
            return rewards;
        } catch (IllegalArgumentException e) {
            outputView.printErrormessage(e.getMessage());
            return requestRewards(playerCount);
        }
    }

    private Target requestTarget(List<String> playerNames) {
        try {
            outputView.printMessage(TARGET_INPUT_REQUEST);
            Target target = new Target(inputView.readTarget());
            target.checkNotPlayerNameOrNotKeyword(playerNames);
            return target;
        } catch (IllegalArgumentException e) {
            outputView.printErrormessage(e.getMessage());
            return requestTarget(playerNames);
        }
    }

    private Ladder makeLadder(Players players, Height height, TrueOrFalseGenerator trueOrFalseGenerator) {
        try {
            Ladder ladder = new Ladder(players, height, trueOrFalseGenerator);
            validator.validateLadder(ladder, height.getHeight());
            return ladder;

        } catch (LadderStateException e) {
            return makeLadder(players, height, trueOrFalseGenerator);
        }
    }
}
