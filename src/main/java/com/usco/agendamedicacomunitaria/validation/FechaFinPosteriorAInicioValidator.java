package com.usco.agendamedicacomunitaria.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.BeanWrapperImpl;
import java.time.LocalDateTime;

public class FechaFinPosteriorAInicioValidator implements ConstraintValidator<FechaFinPosteriorAInicio, Object> {

    private String fechaInicioFieldName;
    private String fechaFinFieldName;
    private String message;

    @Override
    public void initialize(FechaFinPosteriorAInicio constraintAnnotation) {
        this.fechaInicioFieldName = constraintAnnotation.fechaInicioField();
        this.fechaFinFieldName = constraintAnnotation.fechaFinField();
        this.message = constraintAnnotation.message();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        if (value == null) {
            return true; // Null values are usually handled by @NotNull
        }

        BeanWrapperImpl beanWrapper = new BeanWrapperImpl(value);
        LocalDateTime fechaInicio = (LocalDateTime) beanWrapper.getPropertyValue(fechaInicioFieldName);
        LocalDateTime fechaFin = (LocalDateTime) beanWrapper.getPropertyValue(fechaFinFieldName);

        if (fechaInicio == null || fechaFin == null) {
            return true; // Let @NotNull handle null dates if needed
        }

        if (fechaFin.isAfter(fechaInicio)) {
            return true;
        } else {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(message)
                    .addPropertyNode(fechaFinFieldName)
                    .addConstraintViolation();
            return false;
        }
    }
}