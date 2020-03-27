package fr.shining_cat.everyday.testutils

import org.mockito.Mockito

//extend this class in test classes in need of Mockito any() to avoid NPE from any returning null and used in a non-nullable place...
abstract class AbstractBaseTest {

    fun <T> any(): T {
        Mockito.any<T>()
        return uninitialized()
    }

    fun <T> uninitialized(): T = null as T
}