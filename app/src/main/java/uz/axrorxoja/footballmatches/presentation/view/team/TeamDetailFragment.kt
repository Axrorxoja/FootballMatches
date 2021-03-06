package uz.axrorxoja.footballmatches.presentation.view.team

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import coil.ImageLoader
import coil.decode.SvgDecoder
import coil.load
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.filterNot
import kotlinx.coroutines.launch
import timber.log.Timber
import uz.axrorxoja.data.model.Team
import uz.axrorxoja.footballmatches.R
import uz.axrorxoja.footballmatches.databinding.FragmentTeamDetailBinding
import uz.axrorxoja.footballmatches.presentation.vm.team.ITeamViewModel
import uz.axrorxoja.footballmatches.presentation.vm.team.TeamScreenState
import uz.axrorxoja.footballmatches.presentation.vm.team.TeamViewModel
import javax.inject.Inject

@AndroidEntryPoint
class TeamDetailFragment : Fragment(R.layout.fragment_team_detail) {

    private val viewModel: ITeamViewModel by viewModels<TeamViewModel>()
    private var _binding: FragmentTeamDetailBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.loadData()
        viewLifecycleOwnerLiveData.observe(this) { lifeCycOwner ->
            if (lifeCycOwner != null) {
                lifeCycOwner.lifecycleScope.launch {
                    viewModel.screenState
                        .filterNot { it.default }
                        .collect(::setUpData)
                }
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
        Timber.d("setUpData $data")
        when {
            data.success != null -> setData(data.success)
            data.progress != null -> binding.groupProgress.visibility = View.VISIBLE
            else -> {
                binding.groupProgress.visibility = View.GONE
                binding.groupError.visibility = View.VISIBLE
                binding.tvErrorMsg.text = data.error
                binding.groupAll.visibility = View.INVISIBLE
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.buttonTryAgain.setOnClickListener { viewModel.loadData() }
    }

    private fun setData(data: Team) {
        binding.groupProgress.visibility = View.GONE
        binding.groupError.visibility = View.GONE
        binding.groupAll.visibility = View.VISIBLE

        val imageLoader = ImageLoader.Builder(requireContext())
            .componentRegistry { add(SvgDecoder(requireContext())) }
            .build()
        binding.imageView.load(data.crestUrl, imageLoader)
        binding.tvName.text = data.name
        binding.tvShortName.text = data.shortName
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