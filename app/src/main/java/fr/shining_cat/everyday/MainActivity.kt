package fr.shining_cat.everyday

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import fr.shining_cat.everyday.utils.extensions.*

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

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        val id = item?.itemId
        when(id){
            R.id.action_settings -> {
//                startActivity(Intent(this, SettingsActivity.class))
                return true
            }
            R.id.action_infos -> {

                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }
}
