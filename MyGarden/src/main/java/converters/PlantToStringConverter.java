
package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.Plant;

@Component
@Transactional
public class PlantToStringConverter implements Converter<Plant, String> {

	@Override
	public String convert(final Plant plant) {
		String result;

		if (plant == null)
			result = null;
		else
			result = String.valueOf(plant.getId());
		return result;
	}
}
