package marketing.entities;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2021-05-26T01:24:19.943+0200")
@StaticMetamodel(Question.class)
public class Question_ {
	public static volatile SingularAttribute<Question, Integer> id;
	public static volatile SingularAttribute<Question, String> question;
	public static volatile ListAttribute<Question, Answer> reviews;
	public static volatile SingularAttribute<Question, Product> product;
}
