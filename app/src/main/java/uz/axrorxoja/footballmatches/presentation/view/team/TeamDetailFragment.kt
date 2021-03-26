package uz.axrorxoja.footballmatches.presentation.view.team

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.whenResumed
import coil.load
import dagger.android.support.AndroidSupportInjection
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.filterNot
import kotlinx.coroutines.launch
import uz.axrorxoja.data.model.Team
import uz.axrorxoja.footballmatches.R
import uz.axrorxoja.footballmatches.databinding.FragmentTeamDetailBinding
import uz.axrorxoja.footballmatches.presentation.vm.team.ITeamViewModel
import uz.axrorxoja.footballmatches.presentation.vm.team.TeamScreenState
import uz.axrorxoja.footballmatches.presentation.vm.team.TeamViewModel
import javax.inject.Inject

class TeamDetailFragment : Fragment(R.layout.fragment_team_detail) {

    @Inject
    lateinit var factory: ViewModelProvider.Factory

    private val viewModel: ITeamViewModel by viewModels<TeamViewModel> { factory }
    private var _binding: FragmentTeamDetailBinding? = null
    private val binding get() = _binding!!

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewLifecycleOwner.lifecycleScope.launch {
            whenResumed {
                viewModel.screenState
                    .filterNot { it.default }
                    .collect(::setUpData)
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTeamDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    private fun setUpData(data: TeamScreenState) {
        when {
            data.success != null -> setData(data.success)
            data.progress != null -> binding.groupProgress.visibility = View.VISIBLE
            else -> {
                binding.buttonTryAgain.visibility = View.VISIBLE
                binding.group.visibility = View.GONE
            }
        }
    }

    private fun setData(data: Team) {
        binding.groupProgress.visibility = View.GONE

        binding.imageView.load(data.cresUrl)
        binding.tvName.text = data.name
        binding.tvAddress.text = data.address
        binding.tvPhone.text = data.phone
        binding.tvWebsite.text = data.website
        binding.tvEmail.text = data.email
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}