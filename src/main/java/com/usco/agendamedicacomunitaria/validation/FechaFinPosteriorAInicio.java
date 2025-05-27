package com.usco.agendamedicacomunitaria.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = FechaFinPosteriorAInicioValidator.class)
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface FechaFinPosteriorAInicio {
    String message() default "La fecha y hora de fin deben ser posteriores a la fecha y hora de inicio.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

    String fechaInicioField();
    String fechaFinField();
}