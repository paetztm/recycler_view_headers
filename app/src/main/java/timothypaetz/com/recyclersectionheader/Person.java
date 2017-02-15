package timothypaetz.com.recyclersectionheader;

import android.support.annotation.NonNull;
import java.util.Locale;

/**
 * Created by paetztm on 2/6/2017.
 */

public class Person implements Comparable<Person> {
    private final CharSequence firstName;
    private final CharSequence lastName;
    private static final String NAME_DISPLAY = "%s, %s";

    public Person(@NonNull CharSequence firstName, @NonNull CharSequence lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public CharSequence getLastName() {
        return lastName;
    }

    public CharSequence getFirstName() {
        return firstName;
    }

    public String getFullName() {
        return String.format(Locale.getDefault(),
                             NAME_DISPLAY,
                             getLastName(),
                             getFirstName());
    }

    @Override
    public int compareTo(@NonNull Person person) {
        return getLastName().toString()
                            .compareTo(person.getLastName()
                                             .toString());
    }
}
