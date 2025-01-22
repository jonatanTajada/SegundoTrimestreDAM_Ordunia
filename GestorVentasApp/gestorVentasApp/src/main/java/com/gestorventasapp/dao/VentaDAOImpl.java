package com.gestorventasapp.dao;

import com.gestorventasapp.model.Venta;
import com.gestorventasapp.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class VentaDAOImpl implements VentaDAO {

	@Override
	public void save(Venta venta) {
	    Transaction transaction = null;
	    try (Session session = HibernateUtil.getSessionFactory().openSession()) {
	        transaction = session.beginTransaction();
	        session.persist(venta);
	        transaction.commit();
	    } catch (Exception e) {
	        if (transaction != null && transaction.getStatus().canRollback()) {
	            transaction.rollback();
	        }
	        throw new RuntimeException("Error al guardar la venta: " + e.getMessage(), e);
	    }
	}



	
	
	@Override
	public void update(Venta venta) {
		Transaction transaction = null;
		
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			transaction = session.beginTransaction();
			session.merge(venta);
			transaction.commit();
			
		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
			throw new RuntimeException("Error al actualizar la venta: " + e.getMessage(), e);
		}
	}

	
	
	@Override
	public void delete(int idVenta) {
		
		Transaction transaction = null;
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			transaction = session.beginTransaction();
			
			Venta venta = session.find(Venta.class, idVenta);
			
			if (venta != null) {
				session.remove(venta);
			}
			transaction.commit();
			
		} catch (Exception e) {
			if (transaction != null) transaction.rollback();
			throw new RuntimeException("Error al eliminar la venta: " + e.getMessage(), e);
		}
	}

	
	
	@Override
	public Venta findById(int idVenta) {
		
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			return session.find(Venta.class, idVenta);
		} catch (Exception e) {
			throw new RuntimeException("Error al buscar la venta por ID: " + e.getMessage(), e);
		}
	}

	
	
	@Override
	public List<Venta> findAll() {
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			return session.createQuery("FROM Venta", Venta.class).list();
		} catch (Exception e) {
			throw new RuntimeException("Error al obtener todas las ventas: " + e.getMessage(), e);
		}
	}
}
