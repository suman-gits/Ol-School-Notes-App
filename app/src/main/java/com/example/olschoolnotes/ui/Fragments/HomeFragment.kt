package com.example.olschoolnotes.ui.Fragments

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.*
import android.widget.SearchView
import androidx.appcompat.app.ActionBar
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.olschoolnotes.Model.Notes
import com.example.olschoolnotes.R
import com.example.olschoolnotes.ViewModel.NotesViewModel
import com.example.olschoolnotes.databinding.FragmentHomeBinding
import com.example.olschoolnotes.ui.Adapter.NotesAdapter


class HomeFragment : Fragment() {
    lateinit var binding: FragmentHomeBinding
    val viewModel: NotesViewModel by viewModels()
    var oldMyNotes= arrayListOf<Notes>()
    lateinit var adapter: NotesAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        // Inflate the layout for this fragment
        binding=FragmentHomeBinding.inflate(layoutInflater,container,false)
        setHasOptionsMenu(true)

        val staggeredGridLayoutManager= StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL)
                binding.rcvAllNotes.layoutManager= StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL)

        viewModel.getNotes().observe(viewLifecycleOwner) { notesList ->
            oldMyNotes=notesList as ArrayList<Notes>
            adapter=NotesAdapter(requireContext(),notesList)
            binding.rcvAllNotes.adapter=adapter

        }


        binding.btnAddNotes.setOnClickListener{
            Navigation.findNavController(it).navigate(R.id.action_homeFragment_to_createNotesFragment)
        }

        binding.filterIcon.setOnClickListener {
            viewModel.getNotes().observe(viewLifecycleOwner) { notesList ->
                oldMyNotes=notesList as ArrayList<Notes>
                adapter=NotesAdapter(requireContext(),notesList)
                binding.rcvAllNotes.adapter=adapter

            }

        }


        binding.filterHigh.setOnClickListener {
            viewModel.getHighNotes().observe(viewLifecycleOwner) { notesList ->
                oldMyNotes=notesList as ArrayList<Notes>
                adapter=NotesAdapter(requireContext(),notesList)
                binding.rcvAllNotes.adapter=adapter

            }
        }
        binding.filterMedium.setOnClickListener {
            viewModel.getMediumNotes().observe(viewLifecycleOwner) { notesList ->
                oldMyNotes=notesList as ArrayList<Notes>
                adapter=NotesAdapter(requireContext(),notesList)
                binding.rcvAllNotes.adapter=adapter

            }

        }
        binding.filterLow.setOnClickListener {
            viewModel.getLowNotes().observe(viewLifecycleOwner) { notesList ->
                oldMyNotes=notesList as ArrayList<Notes>
                adapter=NotesAdapter(requireContext(),notesList)
                binding.rcvAllNotes.adapter=adapter

            }

        }
        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.search_menu,menu)
        val item= menu.findItem(R.id.app_bar_search)
        val searchView=item.actionView as SearchView
        searchView.queryHint="Search Notes..."
        searchView.setOnQueryTextListener(object:SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(p0: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                NotesFiltering(p0)
                return true
            }
        })
        super.onCreateOptionsMenu(menu, inflater)
    }

    private fun NotesFiltering(p0: String?) {
        val newFilterList= arrayListOf<Notes>()
        for (i in oldMyNotes){
            if (i.title!!.contains(p0!!) || i.subTitle!!.contains(p0!!))
            {
                newFilterList.add(i)
            }
        }
        adapter.filtering(newFilterList)


    }


}


