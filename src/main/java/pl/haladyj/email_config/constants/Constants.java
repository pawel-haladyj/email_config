package pl.haladyj.email_config.constants;

import org.springframework.beans.factory.annotation.Value;

public final class Constants {
    public static final String ACTIVATION_MAIL_SUBJECT = "Rejestracja konta - klucz aktywacyjny";
    public static final String ACTIVATION_MAIL_CONTENT = "W celu aktywacji konta kliknij w poniższy link: ";
    public static final String ACTIVATION_MAIL_SENDER = "graphicdesignjava@gmail.com";

    public static final Integer ACTIVATION_KEY_LENGTH = 30;

    private Constants(){}
}
