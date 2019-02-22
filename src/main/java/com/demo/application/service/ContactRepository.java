package com.demo.application.service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.demo.application.model.Contact;

@Repository("ContactRepository")
public class ContactRepository {
	
	private ContactRowMapper contactRowMapper;

	private class ContactRowMapper implements RowMapper {
		@Override
		public Object mapRow(ResultSet arg0, int arg1) throws SQLException {
			Contact contact = new Contact();

			contact.setId(arg0.getInt("contact_id"));
			contact.setName(arg0.getString("name"));
			contact.setEmail(arg0.getString("email"));
			contact.setAddress(arg0.getString("address"));
			contact.setTelephone(arg0.getString("telephone"));

			return contact;
		}
	}
	
	public RowMapper getRowMapper() {
		if (this.contactRowMapper == null) {
			contactRowMapper = new ContactRowMapper();
		}
		
		return contactRowMapper;
	}

	private JdbcTemplate jdbcTemplate;

	public ContactRepository(DataSource dataSource) {
		jdbcTemplate = new JdbcTemplate(dataSource);
	}

	public void save(Contact contact) {
		String sql = "INSERT INTO contact (name, email, address, telephone)" + " VALUES (?, ?, ?, ?)";
		jdbcTemplate.update(sql, new Object[] { contact.getName(), contact.getEmail(), contact.getAddress(), contact.getTelephone() });
	}

	public void update(Contact contact) {
		String sql = "UPDATE contact SET name=?, email=?, address=?, " + "telephone=? WHERE contact_id=?";
		jdbcTemplate.update(sql, new Object[] { contact.getName(), contact.getEmail(), contact.getAddress(), contact.getTelephone(), contact.getId() });
	}

	public void delete(int contactId) {
		String sql = "DELETE FROM contact WHERE contact_id=?";
		jdbcTemplate.update(sql, new Object[] { contactId });
	}

	public List<Contact> list() {
		String sql = "SELECT * FROM contact";
		return jdbcTemplate.query(sql, getRowMapper());
	}

	public Contact get(int contactId) {
		String sql = "SELECT * FROM contact WHERE contact_id=" + contactId;
		return (Contact) jdbcTemplate.queryForObject(sql, getRowMapper());
	}

}
