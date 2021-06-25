package Model.DataTypes.User;

import java.io.Serializable;

public enum SecurityQuestions implements Serializable {
    ELEMENTARY_SCHOOL_NAME("What was your elementary school's name?"),
    CHILDHOOD_FAVORITE_GAME("What was your favorite game in your childhood?"),
    FAVORITE_FOOD("What is your favorite food?"),
    FAVORITE_SINGER("Who is your favorite singer?");
    public static final long serialVersionUID = 7461736147836113681L;

    private final String question;

    SecurityQuestions(String question) {
        this.question = question;
    }

    public String getQuestion() {
        return question;
    }
}
