package com.group0565.hitObjectsRepository;

import com.group0565.repository.IRepository;
import com.group0565.tsu.gameObjects.HitObject;

import java.util.List;

/**
 * An interface for the repository of user-specific HitObjects
 */
public interface IHitObjectsRepository extends IRepository<HitObject> {
    /**
     * Pushes a list of HitObjects to the repository
     * Also empties the current list of HitObjects
     *
     * @param hitObjects The list of HitObjects to push to the DB
     */
    void pushList(List<HitObject> hitObjects);
}
