package fr.istic.taa.jaxrs.dao.generic;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

import java.io.Serializable;
import java.util.List;

import static java.util.Objects.requireNonNull;

public abstract class AbstractJpaDao<K, T extends Serializable> implements IGenericDao<K, T> {

	private final Class<T> clazz;

	public AbstractJpaDao(Class<T> clazz) {
		this.clazz = requireNonNull(clazz);
	}

	protected EntityManager getEntityManager() {
		return EntityManagerHelper.getEntityManager();
	}

	public T findOne(K id) {
		return getEntityManager().find(clazz, id);
	}

	public List<T> findAll() {
		return getEntityManager().createQuery("select e from " + clazz.getName() + " as e",clazz).getResultList();
	}

	public void save(T entity) {
		getEntityManager().persist(entity);
	}

	public T update(final T entity) {
		return getEntityManager().merge(entity);
	}

	public void delete(T entity) {
		getEntityManager().remove(entity);
	}

	public void deleteById(K entityId) {
		T entity = findOne(entityId);
		delete(entity);
	}
}
