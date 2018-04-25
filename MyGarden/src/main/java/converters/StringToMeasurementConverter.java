
package converters;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import repositories.MeasurementRepository;
import domain.Measurement;

@Component
@Transactional
public class StringToMeasurementConverter implements Converter<String, Measurement> {

	@Autowired
	MeasurementRepository	measurementRepository;


	@Override
	public Measurement convert(final String text) {
		Measurement result;
		int id;

		try {
			if (StringUtils.isEmpty(text))
				result = null;
			else {
				id = Integer.valueOf(text);
				result = this.measurementRepository.findOne(id);
			}
		} catch (final Throwable oops) {
			throw new IllegalArgumentException(oops);
		}

		return result;
	}

}
