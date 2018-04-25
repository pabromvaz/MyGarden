
package converters;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import repositories.TimeTableRepository;
import domain.TimeTable;

@Component
@Transactional
public class StringToTimeTableConverter implements Converter<String, TimeTable> {

	@Autowired
	TimeTableRepository	timeTableRepository;


	@Override
	public TimeTable convert(final String text) {
		TimeTable result;
		int id;

		try {
			if (StringUtils.isEmpty(text))
				result = null;
			else {
				id = Integer.valueOf(text);
				result = this.timeTableRepository.findOne(id);
			}
		} catch (final Throwable oops) {
			throw new IllegalArgumentException(oops);
		}

		return result;
	}

}
