package Model.DataTypes.User;

public enum SecurityQuestions {
    ELEMENTARY_SCHOOL_NAME("What was your elementary school's name?"),
    CHILDHOOD_FAVORITE_GAME("What was your favorite game in your childhood?"),
    FAVORITE_FOOD("What is your favorite food?"),
    FAVORITE_SINGER("Who is your favorite singer?");

    private final String question;

    SecurityQuestions(String question) {
        this.question = question;
    }

    public String getQuestion() {
        return question;
    }
}
