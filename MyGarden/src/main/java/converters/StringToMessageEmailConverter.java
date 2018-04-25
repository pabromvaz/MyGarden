
package converters;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import repositories.MessageEmailRepository;
import domain.MessageEmail;

@Component
@Transactional
public class StringToMessageEmailConverter implements Converter<String, MessageEmail> {

	@Autowired
	MessageEmailRepository	messageEmailRepository;


	@Override
	public MessageEmail convert(final String text) {
		MessageEmail result;
		int id;

		try {
			if (StringUtils.isEmpty(text))
				result = null;
			else {
				id = Integer.valueOf(text);
				result = this.messageEmailRepository.findOne(id);
			}
		} catch (final Throwable oops) {
			throw new IllegalArgumentException(oops);
		}

		return result;
	}

}
