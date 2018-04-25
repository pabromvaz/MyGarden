
package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.Fertilizer;

@Component
@Transactional
public class FertilizerToStringConverter implements Converter<Fertilizer, String> {

	@Override
	public String convert(final Fertilizer fertilizer) {
		String result;

		if (fertilizer == null)
			result = null;
		else
			result = String.valueOf(fertilizer.getId());
		return result;
	}
}
