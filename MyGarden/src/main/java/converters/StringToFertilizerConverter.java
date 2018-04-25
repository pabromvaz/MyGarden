
package converters;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import repositories.FertilizerRepository;
import domain.Fertilizer;

@Component
@Transactional
public class StringToFertilizerConverter implements Converter<String, Fertilizer> {

	@Autowired
	FertilizerRepository	fertilizerRepository;


	@Override
	public Fertilizer convert(final String text) {
		Fertilizer result;
		int id;

		try {
			if (StringUtils.isEmpty(text))
				result = null;
			else {
				id = Integer.valueOf(text);
				result = this.fertilizerRepository.findOne(id);
			}
		} catch (final Throwable oops) {
			throw new IllegalArgumentException(oops);
		}

		return result;
	}

}
