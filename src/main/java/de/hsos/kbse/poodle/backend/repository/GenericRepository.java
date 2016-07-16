/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.hsos.kbse.poodle.backend.repository;

import de.hsos.kbse.poodle.backend.model.Model;

import java.io.Serializable;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

public abstract class GenericRepository<T extends Model> implements Serializable{
    @PersistenceContext(unitName="DB",type=PersistenceContextType.TRANSACTION)
    protected EntityManager em;
    
    protected final Class<T> type;
    
    protected GenericRepository(Class<T> type){
        this.type = type;
    }
    
    protected Object getPrimaryKey(T entity){
        return this.em.getEntityManagerFactory().getPersistenceUnitUtil().getIdentifier(entity);
    }
    
    public T newEntityInstance(){
        try {
            return this.type.newInstance();
        } catch (InstantiationException | IllegalAccessException ex) {
            Logger.getLogger(GenericRepository.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return null;
    }
    
    public T find(Object key){
        if(key == null){
            return null;
        }
        return this.em.find(this.type,key);
    }
    
    public boolean add(T entity){
        Object pk = this.getPrimaryKey(entity);
        if(pk != null && this.find(pk) != null){
            return false;
        }
        
        this.em.persist(entity);
        return true;
    }
    
    public T update(T entity){
        return this.em.merge(entity);
    }
    
    public void remove(T entity){
        T merged = this.em.merge(entity);
        this.em.remove(merged);
    }

    public void refresh(T entity){
        this.em.refresh(entity);
    }

    public T reattach(T entity){
        return this.find(this.getPrimaryKey(entity));
    }
    
    public Collection<T> findAll(){
        CriteriaQuery<T> query = this.em.getCriteriaBuilder().createQuery(this.type);
        Root<T> rootQuery = query.from(this.type);
        
        query.select(rootQuery);
        TypedQuery<T> tq = this.em.createQuery(query);
        
        return tq.getResultList();
    }
}
