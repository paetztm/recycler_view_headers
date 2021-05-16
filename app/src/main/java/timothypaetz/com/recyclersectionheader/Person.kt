package timothypaetz.com.recyclersectionheader

import java.util.*

/**
 * Created by paetztm on 2/6/2017.
 */
data class Person(val firstName: CharSequence, val lastName: CharSequence) : Comparable<Person> {
    val fullName = String.format(
            Locale.getDefault(),
            NAME_DISPLAY,
            lastName,
            firstName
        )

    override fun compareTo(other: Person): Int {
        return lastName.toString()
            .compareTo(
                other.lastName
                    .toString()
            )
    }

    companion object {
        private const val NAME_DISPLAY = "%s, %s"
    }
}