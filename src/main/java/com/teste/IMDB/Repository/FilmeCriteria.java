package com.teste.IMDB.Repository;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import com.teste.IMDB.Dto.FilmeDtoIn;
import com.teste.IMDB.Model.Filme;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
public class FilmeCriteria {

    @PersistenceContext
    private EntityManager manager;

    public Page<Filme> findAllByCriteriaWithPage(FilmeDtoIn filtro, Pageable page) {
        CriteriaBuilder builder = manager.getCriteriaBuilder();
        CriteriaQuery<Filme> criteria = builder.createQuery(Filme.class);
        Root<Filme> root = criteria.from(Filme.class);
        Predicate[] predicates = restricoes(filtro, builder, root);
        criteria.where(predicates);
        TypedQuery<Filme> query = manager.createQuery(criteria);
        int totalRows = query.getResultList().size();
        query.setFirstResult(page.getPageNumber() * page.getPageSize());
        query.setMaxResults(page.getPageSize());
        return new PageImpl<>(query.getResultList(), page, totalRows);
    }

    public List<Filme> findAllByCriteria(FilmeDtoIn filtro) {
        CriteriaBuilder builder = manager.getCriteriaBuilder();
        CriteriaQuery<Filme> criteria = builder.createQuery(Filme.class);
        Root<Filme> root = criteria.from(Filme.class);
        Predicate[] predicates = restricoes(filtro, builder, root);
        criteria.where(predicates);
        TypedQuery<Filme> query = manager.createQuery(criteria);
        return query.getResultList();
    }
    
    private Predicate[] restricoes(FilmeDtoIn filtro, CriteriaBuilder builder, Root<Filme> root) {
        List<Predicate> predicates = new ArrayList<>();
        if (filtro.getNome() != null && !filtro.getNome().isEmpty()) {
            predicates.add(builder.like(builder.lower(root.get("nome")), "%" + filtro.getNome().toLowerCase() + "%"));
        }
        if (filtro.getDiretor() != null && !filtro.getDiretor().isEmpty()) {
            predicates.add(builder.like(builder.lower(root.get("diretor")), "%" + filtro.getDiretor().toLowerCase() + "%"));
        }
        if (filtro.getDiretor() != null && !filtro.getGenero().isEmpty()) {
            predicates.add(builder.like(builder.lower(root.get("genero")), "%" + filtro.getGenero().toLowerCase() + "%"));
        }
        if (filtro.getDiretor() != null && !filtro.getAtores().isEmpty()) {
            predicates.add(builder.like(builder.lower(root.get("atores")), "%" + filtro.getAtores().toLowerCase() + "%"));
        }
        return predicates.toArray(new Predicate[predicates.size()]);
    }

}
