package com.portal.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import com.portal.model.ClassifierModel;
import com.portal.model.Contact;
import com.portal.model.PersonModel;

public class ContactMapper implements ResultSetExtractor<List<Contact>> {
	
	public List<Contact> extractData(ResultSet rs) throws SQLException, DataAccessException {
		
		List<Contact> contacts = new LinkedList<Contact>();
		
		if (rs != null ) {
			while( rs.next() ) {
				ClassifierModel type = new ClassifierModel();
				type.setId( rs.getInt("cla_id"));
				type.setValue(rs.getString("cla_value"));
				type.setType(rs.getString("cla_type"));
				
				PersonModel person = PersonModel.builder().id( rs.getInt( "per_id" ) ).build();
				
				Contact contactModel = new Contact();
				contactModel.setId( rs.getInt( "cot_id" ) );
				contactModel.setValue( rs.getString( "value" ) );
				contactModel.setComplement(rs.getString("complement"));
				contactModel.setType(type);
				contactModel.setPerson(person);
							
				contacts.add( contactModel );
			}
		}
		
		return contacts;
	}
}
