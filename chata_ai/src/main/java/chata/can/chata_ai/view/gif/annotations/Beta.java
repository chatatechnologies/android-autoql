package chata.can.chata_ai.view.gif.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.CLASS)
@Target({
	ElementType.ANNOTATION_TYPE,
	ElementType.CONSTRUCTOR,
	ElementType.FIELD,
	ElementType.METHOD,
	ElementType.TYPE })
@Documented
@Beta
public @interface Beta {
}