
package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.MessageEmail;

@Component
@Transactional
public class MessageEmailToStringConverter implements Converter<MessageEmail, String> {

	@Override
	public String convert(final MessageEmail messageEmail) {
		String result;

		if (messageEmail == null)
			result = null;
		else
			result = String.valueOf(messageEmail.getId());
		return result;
	}
}
