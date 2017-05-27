package com.doing.annotation.ioc;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static android.R.attr.value;
import static android.icu.lang.UCharacter.GraphemeClusterBreak.T;

/**
 * Class description her
 *
 * @author doing
 * @version 1.0.0
 * @since 2017-05-25.
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ViewInject {
    int value();
}
