// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.gameserver.model.ai.deprecated;

import org.atteo.classindex.IndexAnnotated;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@IndexAnnotated
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
//@Deprecated
public @interface IAIName {
    String value();
}
