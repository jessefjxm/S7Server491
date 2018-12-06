package com.bdoemu.gameserver.model.chat;

import com.bdoemu.commons.model.enums.EAccessLevel;

import java.lang.annotation.*;

@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface CommandHandlerMethod {
    EAccessLevel accessLevel() default EAccessLevel.BANNED;
}
