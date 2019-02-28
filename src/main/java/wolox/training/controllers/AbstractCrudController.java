package wolox.training.controllers;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.server.ResponseStatusException;
import wolox.training.models.BaseEntity;

public abstract class AbstractCrudController<T extends BaseEntity<X>, X> {

  abstract JpaRepository<T, X> getRepository();

  @GetMapping
  public Iterable findAll() {
    return getRepository().findAll();
  }


  @GetMapping("/{id}")
  public T findOne(@PathVariable X id) throws ResponseStatusException {
    return getRepository().findById(id)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Not found"));
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public T create(@RequestBody T entity) {
    return getRepository().save(entity);
  }

  @DeleteMapping("/{id}")
  public void delete(@PathVariable X id) throws ResponseStatusException {
    getRepository().findById(id)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Not found"));

    getRepository().deleteById(id);
  }

  @PutMapping("/{id}")
  public T update(@RequestBody T entity, @PathVariable X id) {
    if (entity.getId() != id) {
      return null;
    }
    return getRepository().save(entity);
  }

}
