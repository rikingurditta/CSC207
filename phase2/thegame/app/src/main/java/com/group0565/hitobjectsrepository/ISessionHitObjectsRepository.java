package com.group0565.hitobjectsrepository;

import com.group0565.basepatterns.repository.IRepository;

import java.util.List;

/** An interface for the repository of user-specific HitObjects */
public interface ISessionHitObjectsRepository extends IRepository<SessionHitObjects> {
  /**
   * Pushes a list of HitObjects to the repository Also empties the current list of HitObjects
   *
   * @param sessionLists The list of HitObjects to push to the DB
   */
  void pushList(List<SessionHitObjects> sessionLists);
}
