package fr.shining_cat.everyday.screens.views.home

import android.os.Bundle
import android.view.*
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import fr.shining_cat.everyday.commons.Logger
import fr.shining_cat.everyday.screens.R
import fr.shining_cat.everyday.screens.views.ScreenActivity
import kotlinx.android.synthetic.main.home_fragment.*
import org.koin.android.ext.android.get
import org.koin.android.viewmodel.ext.android.viewModel

class HomeFragment : Fragment() {

    private val LOG_TAG = HomeFragment::class.java.simpleName

    private val logger: Logger = get()
    private val homeViewModel: HomeViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.statistics_fragment, container, false)
        logger.d(LOG_TAG, "onCreateView")
        //
        setupToolbar(root)
        //
        val textView: TextView = root.findViewById(R.id.fake_fragment_text)
        homeViewModel.initReadyLiveData.observe(viewLifecycleOwner, Observer {
            textView.text = "This is ${LOG_TAG}\n $it loaded!"
        })
        homeViewModel.initViewModel()
        return root
    }


    private fun setupToolbar(root: View){
        logger.d(LOG_TAG, "setupToolbar")
        val toolbar: Toolbar = root.findViewById(R.id.toolbar)
        (activity as ScreenActivity).setSupportActionBar(toolbar)
        setHasOptionsMenu(true)
    }
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        logger.d(LOG_TAG, "onCreateOptionsMenu")
        inflater.inflate(R.menu.toolbar_menu_home, menu)
        return super.onCreateOptionsMenu(menu, inflater)
    }

}
