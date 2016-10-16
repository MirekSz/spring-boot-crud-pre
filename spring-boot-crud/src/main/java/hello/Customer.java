package hello;

import javax.validation.constraints.NotNull;

public class Customer {
	private String firstName;
	@NotNull
	private String lastName;
	private Integer age;
	private Long id;

	public Customer(long id, String firstName, String lastName, int age) {
		this.setId(id);
		this.firstName = firstName;
		this.lastName = lastName;
		this.age = age;
	}

	public Customer() {
		// TODO Auto-generated constructor stub
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
}
