package fr.shining_cat.everyday

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.crashlytics.android.Crashlytics
import fr.shining_cat.everyday.utils.extensions.logD

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
            R.id.action_force_crash -> {
                Crashlytics.getInstance().crash() // Force a crash
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }
}
