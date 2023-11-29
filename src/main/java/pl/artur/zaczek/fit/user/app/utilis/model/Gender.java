package pl.artur.zaczek.fit.user.app.utilis.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Gender {
    M ("male"),
    F ("female");

    private String desc;
}
