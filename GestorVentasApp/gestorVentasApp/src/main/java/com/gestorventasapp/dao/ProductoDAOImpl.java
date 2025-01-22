package com.gestorventasapp.dao;

import java.util.List;

import javax.swing.JOptionPane;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.gestorventasapp.model.Producto;
import com.gestorventasapp.util.HibernateUtil;



public class ProductoDAOImpl implements ProductoDAO {

	@Override
	 public void save(Producto producto) {
		
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.persist(producto);
            transaction.commit();
            
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }

	@Override
	public void update(Producto producto) {
		
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.merge(producto);
            transaction.commit();
            
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }

	@Override
	public void delete(int idProducto) {
		
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            Producto producto = session.get(Producto.class, idProducto);
            
            if (producto != null) {
                session.remove(producto);
            }
            
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }

	@Override
	public Producto findById(int idProducto) {
		
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(Producto.class, idProducto);
            
        } catch (Exception e) {
            e.printStackTrace();
            return null; 
        }
    }

	@Override
	public List<Producto> findAll() {
		
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("from Producto", Producto.class).list();
            
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

	

}
