package fr.shining_cat.everyday.screens.views.statistics

import android.os.Bundle
import android.view.*
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import fr.shining_cat.everyday.commons.Logger
import fr.shining_cat.everyday.screens.R
import fr.shining_cat.everyday.screens.views.ScreenActivity
import org.koin.android.ext.android.get
import org.koin.android.viewmodel.ext.android.viewModel

class StatisticsFragment : Fragment() {

    private val LOG_TAG = StatisticsFragment::class.java.simpleName

    private val logger: Logger = get()
    private val statisticsViewModelViewModel: StatisticsViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.statistics_fragment, container, false)
        //
        setupToolbar(root)
        //
        val textView: TextView = root.findViewById(R.id.fake_fragment_text)
        statisticsViewModelViewModel.initReadyLiveData.observe(viewLifecycleOwner, Observer {
            textView.text = "This is ${LOG_TAG}\n $it loaded!"
        })
        statisticsViewModelViewModel.initViewModel()
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
