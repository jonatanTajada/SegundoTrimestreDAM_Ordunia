package com.gestorventasapp.dao;

import com.gestorventasapp.model.DetalleVenta;
import com.gestorventasapp.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class DetalleVentaDAOImpl implements DetalleVentaDAO {

	@Override
	public void save(DetalleVenta detalleVenta) {
		Transaction transaction = null;
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			transaction = session.beginTransaction();
			session.persist(detalleVenta);
			transaction.commit();
		} catch (Exception e) {
			if (transaction != null) transaction.rollback();		
			throw new RuntimeException("Error al guardar el detalle de la venta: " + e.getMessage(), e);
		}
	}

	@Override
	public void update(DetalleVenta detalleVenta) {
		Transaction transaction = null;
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			transaction = session.beginTransaction();
			session.merge(detalleVenta);
			transaction.commit();
		} catch (Exception e) {
			if (transaction != null) transaction.rollback();			
			throw new RuntimeException("Error al actualizar el detalle de la venta: " + e.getMessage(), e);
		}
	}

	@Override
	public void delete(int idDetalle) {
		Transaction transaction = null;
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			transaction = session.beginTransaction();
			DetalleVenta detalleVenta = session.find(DetalleVenta.class, idDetalle);
			if (detalleVenta != null) {
				session.remove(detalleVenta);
			}
			transaction.commit();
		} catch (Exception e) {
			if (transaction != null) transaction.rollback();
			throw new RuntimeException("Error al eliminar el detalle de la venta: " + e.getMessage(), e);
		}
	}

	@Override
	public DetalleVenta findById(int idDetalle) {
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			return session.find(DetalleVenta.class, idDetalle);
		} catch (Exception e) {
			throw new RuntimeException("Error al buscar el detalle de la venta por ID: " + e.getMessage(), e);
		}
	}

	@Override
	public List<DetalleVenta> findAll() {
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			return session.createQuery("FROM DetalleVenta", DetalleVenta.class).list();
		} catch (Exception e) {
			throw new RuntimeException("Error al obtener todos los detalles de las ventas: " + e.getMessage(), e);
		}
	}
}
