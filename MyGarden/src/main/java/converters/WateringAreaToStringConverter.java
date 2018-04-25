
package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.WateringArea;

@Component
@Transactional
public class WateringAreaToStringConverter implements Converter<WateringArea, String> {

	@Override
	public String convert(final WateringArea wateringArea) {
		String result;

		if (wateringArea == null)
			result = null;
		else
			result = String.valueOf(wateringArea.getId());
		return result;
	}
}
