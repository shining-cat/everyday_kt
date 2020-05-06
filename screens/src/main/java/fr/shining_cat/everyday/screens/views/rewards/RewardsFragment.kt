package fr.shining_cat.everyday.screens.views.rewards

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import fr.shining_cat.everyday.commons.Logger
import fr.shining_cat.everyday.screens.R
import org.koin.android.ext.android.get
import org.koin.android.viewmodel.ext.android.viewModel

class RewardsFragment : Fragment() {

    private val LOG_TAG = RewardsFragment::class.java.simpleName

    private val logger: Logger = get()
    private val rewardsViewModel: RewardsViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.statistics_fragment, container, false)
        val textView: TextView = root.findViewById(R.id.fake_fragment_text)
        rewardsViewModel.initReadyLiveData.observe(viewLifecycleOwner, Observer {
            textView.text = "This is ${LOG_TAG}\n $it loaded!"
        })
        rewardsViewModel.initViewModel()
        return root
    }
}
