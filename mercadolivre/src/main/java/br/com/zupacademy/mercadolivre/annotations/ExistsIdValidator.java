package br.com.zupacademy.mercadolivre.annotations;

import org.springframework.util.Assert;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;

public class ExistsIdValidator implements ConstraintValidator<ExistsId, Long> {

    private String fieldName;
    private Class<?> domainClass;

    @PersistenceContext
    private EntityManager manager;

    @Override
    public void initialize(ExistsId existsId) {
        this.fieldName = existsId.fieldName();
        this.domainClass = existsId.domainClass();
    }

    @Override
    public boolean isValid(Long id, ConstraintValidatorContext constraintValidatorContext) {
        if (id == null) {
            return true;
        }

        Query query = manager.createQuery("SELECT 1 FROM " + this.domainClass.getName() + " WHERE " + this.fieldName + "=:id");
        query.setParameter("id", id);

        List<?> list = query.getResultList();
        Assert.state(list.size() <= 1, "O valor de 'id' é utilizado por múltiplas vezes");

        return !list.isEmpty();
    }
}