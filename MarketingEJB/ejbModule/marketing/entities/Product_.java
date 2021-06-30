package marketing.entities;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2021-06-29T21:36:38.859+0200")
@StaticMetamodel(Product.class)
public class Product_ {
	public static volatile SingularAttribute<Product, Integer> id;
	public static volatile SingularAttribute<Product, String> name;
	public static volatile SingularAttribute<Product, String> brand;
	public static volatile SingularAttribute<Product, Date> date;
	public static volatile SingularAttribute<Product, String> image;
	public static volatile ListAttribute<Product, Questionnaire> questionnaires;
	public static volatile ListAttribute<Product, Question> questions;
}
