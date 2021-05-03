package marketing.entities;


import java.io.Serializable;

import javax.persistence.*;



@Entity
@Table(name = "banneduser", schema = "marketingdb")

public class BannedUser implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	// one-to-one association to User
	@OneToOne
	@JoinColumn(name = "user")
	private User user;

	public BannedUser() {
	}

	public BannedUser(User user) {
		this.user = user;
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
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