package com.example.doughcalculator

import androidx.room.Room
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.doughcalculator.database.DoughRecipe
import com.example.doughcalculator.database.DoughRecipeDao
import com.example.doughcalculator.database.DoughRecipesDatabase
import org.junit.After

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import org.junit.Before
import java.io.IOException

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {
    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("com.example.doughcalculator", appContext.packageName)
    }
}

@RunWith(AndroidJUnit4::class)
class DatabaseTest {

    private lateinit var recipeDao: DoughRecipeDao
    private lateinit var db: DoughRecipesDatabase

    @Before
    fun createDb() {
        val context= InstrumentationRegistry.getInstrumentation().targetContext
        db = Room.inMemoryDatabaseBuilder(context, DoughRecipesDatabase::class.java)
            .allowMainThreadQueries()
            .build()
        recipeDao = db.recipeDao
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    @Throws(Exception::class)
    fun insertAndGetRecipe() {
        val recipe = DoughRecipe(title = "багет", isFavorite = true)
        recipeDao.insert(recipe)
        val readRecipe = recipeDao.get("багет")
        assertEquals(readRecipe.isFavorite, true)
    }
}