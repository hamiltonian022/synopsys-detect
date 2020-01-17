package com.synopsys.integration.configuration.property.base

import com.synopsys.integration.configuration.parse.ValueParser

abstract class NullableProperty<T>(key: String, parser: ValueParser<T>) : TypedProperty<T>(key, parser) {
    // This is a property with a key and a value, but the value is optional (nullable).
}