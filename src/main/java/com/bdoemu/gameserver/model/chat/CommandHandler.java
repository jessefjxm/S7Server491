package com.bdoemu.gameserver.model.chat;

import com.bdoemu.commons.model.enums.EAccessLevel;
import org.atteo.classindex.IndexAnnotated;

import java.lang.annotation.*;

@Inherited
@IndexAnnotated
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
public @interface CommandHandler {
    String prefix();

    EAccessLevel accessLevel() default EAccessLevel.USER;
}
