package com.cgi.bonnie.businessrules;

import com.cgi.bonnie.businessrules.order.Order;
import com.cgi.bonnie.businessrules.user.User;
import com.openpojo.reflection.PojoClass;
import com.openpojo.reflection.coverage.impl.Jacoco;
import com.openpojo.reflection.coverage.service.PojoCoverageFilterServiceFactory;
import com.openpojo.reflection.impl.PojoClassFactory;
import com.openpojo.validation.Validator;
import com.openpojo.validation.ValidatorBuilder;
import com.openpojo.validation.rule.impl.GetterMustExistRule;
import com.openpojo.validation.rule.impl.SetterMustExistRule;
import com.openpojo.validation.test.impl.GetterTester;
import com.openpojo.validation.test.impl.SetterTester;

import java.util.Arrays;
import java.util.List;

public class PojoTests {

    //@Test
    public void testWithOpenPojo() {
        PojoCoverageFilterServiceFactory.createPojoCoverageFilterServiceWith(Jacoco.getInstance());

        List<PojoClass> pojoClasses =
                Arrays.asList(
                        PojoClassFactory.getPojoClass(Order.class),
                        PojoClassFactory.getPojoClass(User.class)
                );

        Validator validator = ValidatorBuilder.create()
                .with(new GetterMustExistRule())
                .with(new SetterMustExistRule())
                .with(new SetterTester())
                .with(new GetterTester())
                .build();

        validator.validate(pojoClasses);
    }

}
