package oogasalad.GamePlayer.util;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.util.Arrays;

/***
 * Utility class for creating instances
 */
public class ClassCreator {
  private ClassCreator() {
    // Should not be instantiated as this is a utility class
  }

  /***
   * @return instance of String className
   */
  public static Object createInstance(String className,
      Object[] parameters) throws IOException {
    try {
      Class<?> clazz = Class.forName(className);
      Constructor<?> constructor = clazz.getConstructor(getClasses(parameters));
      return constructor.newInstance(parameters);
    } catch (Error | Exception e) {
      throw new IOException(String.format("Class parsing failed: %s", className));
    }
  }

  /***
   * Converts array of objects into array of classes for class creation
   *
   * @param objects to map to classes
   * @return array of classes corresponding to each object
   */
  private static Class<?>[] getClasses(Object[] objects) {
    return Arrays.stream(objects).map(Object::getClass).toArray(Class<?>[]::new);
  }
}
