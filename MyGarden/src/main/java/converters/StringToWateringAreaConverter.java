
package converters;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import repositories.WateringAreaRepository;
import domain.WateringArea;

@Component
@Transactional
public class StringToWateringAreaConverter implements Converter<String, WateringArea> {

	@Autowired
	WateringAreaRepository	wateringAreaRepository;


	@Override
	public WateringArea convert(final String text) {
		WateringArea result;
		int id;

		try {
			if (StringUtils.isEmpty(text))
				result = null;
			else {
				id = Integer.valueOf(text);
				result = this.wateringAreaRepository.findOne(id);
			}
		} catch (final Throwable oops) {
			throw new IllegalArgumentException(oops);
		}

		return result;
	}

}
