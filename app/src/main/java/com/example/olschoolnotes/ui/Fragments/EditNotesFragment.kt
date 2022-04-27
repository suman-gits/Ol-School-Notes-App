package com.example.olschoolnotes.ui.Fragments

import android.os.Bundle
import android.text.format.DateFormat
import android.view.*
import android.widget.TextView
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import com.example.olschoolnotes.Model.Notes
import com.example.olschoolnotes.R
import com.example.olschoolnotes.ViewModel.NotesViewModel
import com.example.olschoolnotes.databinding.FragmentEditNotesBinding
import com.google.android.material.bottomsheet.BottomSheetDialog
import java.util.*

class EditNotesFragment : Fragment() {

    val oldNotes by navArgs<EditNotesFragmentArgs>()
    lateinit var binding:FragmentEditNotesBinding
    var priority:String="1"
    val viewModel: NotesViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        binding= FragmentEditNotesBinding.inflate(layoutInflater,container,false)
        setHasOptionsMenu(true)

        binding.edtTitle.setText(oldNotes.data.title)
        binding.editSubTitle.setText(oldNotes.data.subTitle)
        binding.edtNotes.setText(oldNotes.data.notes)

        when (oldNotes.data.priority){
            "1"->{
                priority="1"
                binding.pRed.setImageResource(R.drawable.ic_check)
                binding.pGreen.setImageResource(0)
                binding.pYellow.setImageResource(0)

            }
            "2"->{
                priority="2"
                binding.pYellow.setImageResource(R.drawable.ic_check)
                binding.pGreen.setImageResource(0)
                binding.pRed.setImageResource(0)


            }
            "3"->{
                priority="3"
                binding.pGreen.setImageResource(R.drawable.ic_check)
                binding.pRed.setImageResource(0)
                binding.pYellow.setImageResource(0)


            }
        }

        binding.pRed.setOnClickListener{
            priority="1"
            binding.pRed.setImageResource(R.drawable.ic_check)
            binding.pGreen.setImageResource(0)
            binding.pYellow.setImageResource(0)
        }
        binding.pYellow.setOnClickListener{
            priority="2"
            binding.pYellow.setImageResource(R.drawable.ic_check)
            binding.pGreen.setImageResource(0)
            binding.pRed.setImageResource(0)
        }
        binding.pGreen.setOnClickListener{
            priority="3"
            binding.pGreen.setImageResource(R.drawable.ic_check)
            binding.pRed.setImageResource(0)
            binding.pYellow.setImageResource(0)
        }

        binding.btnEditNotes.setOnClickListener{
            updateNotes(it)
        }





        return binding.root
    }

    private fun updateNotes(it: View?) {
        val title= binding.edtTitle.text.toString()
        val subTitle=binding.editSubTitle.text.toString()
        val notes= binding.edtNotes.text.toString()

        val d = Date()
        val notesDate: CharSequence = DateFormat.format("MMMM d, yyyy ", d.time)

        val data= Notes(
            oldNotes.data.Id,
            title = title,
            subTitle =subTitle,
            notes =notes,
            date =notesDate.toString(),
            priority
        )

        viewModel.updateNotes(data)

        Toast.makeText(requireContext(), "Note Updated successfully", Toast.LENGTH_SHORT).show()

        Navigation.findNavController(it!!).navigate(R.id.action_editNotesFragment_to_homeFragment2)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.delete_menu,menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId==R.id.menu_delete){

            val bottomSheet:BottomSheetDialog= BottomSheetDialog(requireContext(),R.style.BottomSheetStyle)
            bottomSheet.setContentView(R.layout.dialog_delete)

            val bottomSheetYes= bottomSheet.findViewById<TextView>(R.id.dialog_yes)
            val bottomSheetNo= bottomSheet.findViewById<TextView>(R.id.dialog_no)

            bottomSheetYes?.setOnClickListener {
                viewModel.deleteNotes(oldNotes.data.Id!!)
                bottomSheet.dismiss()

            }
            bottomSheetNo?.setOnClickListener {
                bottomSheet.dismiss()

            }

            bottomSheet.show()
        }



        return super.onOptionsItemSelected(item)
    }
}


