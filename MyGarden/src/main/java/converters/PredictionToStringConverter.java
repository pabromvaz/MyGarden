
package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.Prediction;

@Component
@Transactional
public class PredictionToStringConverter implements Converter<Prediction, String> {

	@Override
	public String convert(final Prediction prediction) {
		String result;

		if (prediction == null)
			result = null;
		else
			result = String.valueOf(prediction.getId());
		return result;
	}
}
