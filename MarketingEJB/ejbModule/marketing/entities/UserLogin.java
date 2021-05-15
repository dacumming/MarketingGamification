package marketing.entities;


import java.io.Serializable;

import javax.persistence.*;
import java.util.Date;
import java.sql.Timestamp;

@Entity
@Table(name = "userlogin", schema = "marketingdb")

public class UserLogin implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date datetime;
	
	// Bi-directional many-to-one association to User. UserLogin is the owner
	// entity
	@ManyToOne
	@JoinColumn(name = "user")
	private User user;

	public UserLogin() {
	}

	public UserLogin(User user) {
		this.datetime = new Timestamp(System.currentTimeMillis());
		this.user = user;
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public Date getDatetime() {
		return this.datetime;
	}

	public void setDatetime(Date datetime) {
		this.datetime = datetime;
	}
	
	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		/*
		 * for debugging and transaction tracing only
		 * System.out.println("Method setMission of Expense Entity");
		 * JPATxUtils.printTxId(); // prints the JTA hash of the transaction object
		 * JPATxUtils.printTxStatus(); // prints the JTA status of the transaction
		 */
		this.user = user;
	}

}