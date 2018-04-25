
package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.TimeTable;

@Component
@Transactional
public class TimeTableToStringConverter implements Converter<TimeTable, String> {

	@Override
	public String convert(final TimeTable timeTable) {
		String result;

		if (timeTable == null)
			result = null;
		else
			result = String.valueOf(timeTable.getId());
		return result;
	}
}
