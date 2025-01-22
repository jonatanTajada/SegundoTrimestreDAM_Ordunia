package com.gestorventasapp.dao;

import com.gestorventasapp.model.Cliente;
import com.gestorventasapp.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class ClienteDAOImpl implements ClienteDAO {

	@Override
	public void save(Cliente cliente) {
		
		Transaction transaction = null;
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			transaction = session.beginTransaction();
			session.persist(cliente);
			transaction.commit();
		} catch (Exception e) {
			if (transaction != null) transaction.rollback();
			throw e;
		}
	}

	@Override
	public void update(Cliente cliente) {
		Transaction transaction = null;
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			transaction = session.beginTransaction();
			session.merge(cliente);
			transaction.commit();
		} catch (Exception e) {
			if (transaction != null) transaction.rollback();
			throw e;
		}
	}

	@Override
	public void delete(int idCliente) {
		Transaction transaction = null;
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			
			transaction = session.beginTransaction();
			Cliente cliente = session.find(Cliente.class, idCliente);
			
			if (cliente != null) {
				session.remove(cliente);
			}
			transaction.commit();
		} catch (Exception e) {
			if (transaction != null) transaction.rollback();
			throw e;
		}
	}

	@Override
	public Cliente findById(int idCliente) {
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			return session.find(Cliente.class, idCliente);
		}
	}

	@Override
	public List<Cliente> findAll() {
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			return session.createQuery("FROM Cliente", Cliente.class).list();
		}
	}
}
