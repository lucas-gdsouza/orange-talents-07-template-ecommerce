package br.com.zupacademy.mercadolivre.annotations.impl;

import br.com.zupacademy.mercadolivre.annotations.UniqueValue;
import org.springframework.util.Assert;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import java.util.List;

public class UniqueValueValidator implements ConstraintValidator<UniqueValue, Object> {

    private String fieldName;
    private Class<?> domainClass;

    @PersistenceContext
    private EntityManager manager;

    @Override
    public void initialize(UniqueValue uniqueValue) {
        this.fieldName = uniqueValue.fieldName();
        this.domainClass = uniqueValue.domainClass();
    }

    @Override
    public boolean isValid(Object o, ConstraintValidatorContext constraintValidatorContext) {
        Query query = manager.createQuery("SELECT 1 FROM " + this.domainClass.getName() + " WHERE " + this.fieldName + "=:value");
        query.setParameter("value", o);

        List<?> list = query.getResultList();
        Assert.state(list.size() <= 1, "O elemento " + this.fieldName + " já está em uso");

        return list.isEmpty();
    }
}