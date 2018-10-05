package com.skilldistillery.filmquery.entities;

import java.util.List;

public class Actor {
	
	private int id;
	private String fName;
	private String lName;
	private List<Film> films;
	
	// constructors
	
	public Actor() {
		
	}
	
	public Actor(int id, String fName, String lName, List<Film> films) {
		super();
		this.id = id;
		this.fName = fName;
		this.lName = lName;
		this.films = films;
	}


	// equals and hashcode

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((fName == null) ? 0 : fName.hashCode());
		result = prime * result + ((films == null) ? 0 : films.hashCode());
		result = prime * result + id;
		result = prime * result + ((lName == null) ? 0 : lName.hashCode());
		return result;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Actor other = (Actor) obj;
		if (fName == null) {
			if (other.fName != null)
				return false;
		} else if (!fName.equals(other.fName))
			return false;
		if (films == null) {
			if (other.films != null)
				return false;
		} else if (!films.equals(other.films))
			return false;
		if (id != other.id)
			return false;
		if (lName == null) {
			if (other.lName != null)
				return false;
		} else if (!lName.equals(other.lName))
			return false;
		return true;
	}

	// toString

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Actor \nid: ");
		builder.append(id);
		builder.append(" \nfName: ");
		builder.append(fName);
		builder.append(" \nlName: ");
		builder.append(lName);
		builder.append(" \nfilms: ");
		builder.append(films);
		builder.append("\n");
		return builder.toString();
	}
	


	// setters and getters
	public int getId() {
		return id;
	}




	public void setId(int id) {
		this.id = id;
	}


	public String getfName() {
		return fName;
	}


	public void setfName(String fName) {
		this.fName = fName;
	}


	public String getlName() {
		return lName;
	}


	public void setlName(String lName) {
		this.lName = lName;
	}


	public List<Film> getFilms() {
		return films;
	}


	public void setFilms(List<Film> films) {
		this.films = films;
	}
	
	
	
	
	

}
