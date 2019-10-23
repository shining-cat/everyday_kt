package fr.shining_cat.everyday.repository

import org.mockito.Mockito

abstract class AbstractBaseTest {

    fun <T> any(): T {
        Mockito.any<T>()
        return uninitialized()
    }

    fun <T> uninitialized(): T = null as T
}