
package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.Taste;

@Component
@Transactional
public class TasteToStringConverter implements Converter<Taste, String> {

	@Override
	public String convert(final Taste taste) {
		String result;

		if (taste == null)
			result = null;
		else
			result = String.valueOf(taste.getId());
		return result;
	}
}
