
package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.Gardener;

@Component
@Transactional
public class GardenerToStringConverter implements Converter<Gardener, String> {

	@Override
	public String convert(final Gardener gardener) {
		String result;

		if (gardener == null)
			result = null;
		else
			result = String.valueOf(gardener.getId());
		return result;
	}
}
