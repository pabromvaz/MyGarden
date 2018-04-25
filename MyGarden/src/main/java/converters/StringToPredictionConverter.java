
package converters;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import repositories.PredictionRepository;
import domain.Prediction;

@Component
@Transactional
public class StringToPredictionConverter implements Converter<String, Prediction> {

	@Autowired
	PredictionRepository	predictionRepository;


	@Override
	public Prediction convert(final String text) {
		Prediction result;
		int id;

		try {
			if (StringUtils.isEmpty(text))
				result = null;
			else {
				id = Integer.valueOf(text);
				result = this.predictionRepository.findOne(id);
			}
		} catch (final Throwable oops) {
			throw new IllegalArgumentException(oops);
		}

		return result;
	}

}
