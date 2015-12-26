package kazukisaima.kithub.model.moshi

import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.JsonReader
import com.squareup.moshi.JsonWriter
import org.joda.time.DateTime
import org.joda.time.format.DateTimeFormat
import java.util.*

/**
 * Created by Kazuki Saima on 15/12/27.
 * All rights reserved by Vapes Inc.
 */

class DateAdapter: JsonAdapter<Date>() {

    companion object {
        val FACTORY: Factory = Factory { type, annotation, moshi ->
            if (type === Date::class.java) {
                DateAdapter()
            } else {
                null
            }
        }
        private val format = "yyyy-MM-dd'T'HH:mm:ssZ"
    }

    override fun fromJson(reader: JsonReader?): Date? {
        if (reader != null) {
            val dateTimeString = reader.nextString()
            return DateTimeFormat.forPattern(format).parseDateTime(dateTimeString).toDate()
        }
        throw UnsupportedOperationException()
    }

    override fun toJson(writer: JsonWriter?, value: Date?) {
        if (writer != null && value != null) {
            val datetime = DateTime(value)
            writer.value(datetime.toString(format))
        }
        throw UnsupportedOperationException()
    }
}
