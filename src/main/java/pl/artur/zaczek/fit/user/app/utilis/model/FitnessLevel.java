package pl.artur.zaczek.fit.user.app.utilis.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum FitnessLevel {
    BEGINNER ("beginner, a person who is new to exercise or who has not been active for a long time."),
    NOVICE ("novice, a person who is somewhat active but still has room for improvement."),
    INTERMEDIATE ("intermediate, a person who is active and has a good level of fitness."),
    ADVANCED ("advanced, a person who is very active and has a high level of fitness."),
    ELITE ("elite, a person who is at the top of their fitness level and is competing at a high level.");

    private String desc;

}
