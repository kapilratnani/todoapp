package net.ripper.todoapp.api.misc;

import org.openapitools.jackson.nullable.JsonNullable;

import javax.validation.valueextraction.ExtractedValue;
import javax.validation.valueextraction.UnwrapByDefault;
import javax.validation.valueextraction.ValueExtractor;

/**
 * ValueExtractor for Hibernate. It helps to extract values wrapped inside JsonNullable. The value extracted
 * can then be validated.
 */
@UnwrapByDefault // This applies the constraints on the extracted value
public class JsonNullableValueExtractor implements ValueExtractor<JsonNullable<@ExtractedValue ?>> {
    @Override
    public void extractValues(JsonNullable<?> originalValue, ValueReceiver receiver) {
        receiver.value(null, originalValue.isPresent() ? originalValue.get() : null);
    }
}
