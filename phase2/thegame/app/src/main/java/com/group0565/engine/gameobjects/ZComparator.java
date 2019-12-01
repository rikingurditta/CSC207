package com.group0565.engine.gameobjects;

import java.util.Comparator;
import java.util.UUID;

/** A comparator implementation of display z values */
public class ZComparator implements Comparator<UUID> {

  /**
   * Compare the two GameObjectUUIDs based on the z value of the GameObjects represented by the
   * UUIDs. To maintain consistency with equals, if the z value of the two objects are equal, the
   * UUIDs are compared using the compareTo method of the UUIDs.
   *
   * <p>Note that since the uuid is only a weak reference to the GameObjects, the GameObject they
   * reference may be null. UUIDs with null objects will always be smaller than those with objects.
   *
   * @param o1 First uuid
   * @param o2 Second uuid
   * @return 1, 0, -1 based on the z value and UUID of the objects the UUIDs reference.
   */
  @Override
  public int compare(UUID o1, UUID o2) {
    // Obtain the two GameObjects
    GameObject obj1 = GameObject.getObj(o1);
    GameObject obj2 = GameObject.getObj(o2);
    // If both objects are null, compare the UUIDs
    if (obj1 == null && obj2 == null) return o1.compareTo(o2);
    // Objects that are null is smaller than non null objects
    if (obj1 == null) return -1;
    if (obj2 == null) return 1;
    // Find which is smaller
    int sign = (int) Math.signum(obj1.getZ() - obj2.getZ());
    // If the same, compare UUIDs
    if (sign == 0) return o1.compareTo(o2);
    return sign;
  }
}
