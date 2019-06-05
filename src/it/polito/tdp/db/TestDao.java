package it.polito.tdp.db;

public class TestDao {

	public static void main(String[] args) {
		EventsDao dao = new EventsDao();
		for(int e : dao.listAllYears())
			System.out.println(e);
	}

}
