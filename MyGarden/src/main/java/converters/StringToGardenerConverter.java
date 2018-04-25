
package converters;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import repositories.GardenerRepository;
import domain.Gardener;

@Component
@Transactional
public class StringToGardenerConverter implements Converter<String, Gardener> {

	@Autowired
	GardenerRepository	gardenerRepository;


	@Override
	public Gardener convert(final String text) {
		Gardener result;
		int id;

		try {
			if (StringUtils.isEmpty(text))
				result = null;
			else {
				id = Integer.valueOf(text);
				result = this.gardenerRepository.findOne(id);
			}
		} catch (final Throwable oops) {
			throw new IllegalArgumentException(oops);
		}

		return result;
	}

}
