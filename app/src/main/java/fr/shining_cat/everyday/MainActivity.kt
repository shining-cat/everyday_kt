package fr.shining_cat.everyday

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import fr.shining_cat.everyday.commons.extensions.logD

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        logD("onCreate -> log DEBUG")
        logD("onCreate -> log DEBUG", "custom TAG")

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        when(id){
            R.id.action_settings -> {
                //TODO: open settings
                return true
            }
            R.id.action_infos -> {
                //TODO: show "about" Dialog
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }
}
