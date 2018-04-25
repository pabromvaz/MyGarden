
package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.Measurement;

@Component
@Transactional
public class MeasurementToStringConverter implements Converter<Measurement, String> {

	@Override
	public String convert(final Measurement measurement) {
		String result;

		if (measurement == null)
			result = null;
		else
			result = String.valueOf(measurement.getId());
		return result;
	}
}
