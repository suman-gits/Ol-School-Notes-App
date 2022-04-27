package com.example.olschoolnotes.ui.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.olschoolnotes.Model.Notes
import com.example.olschoolnotes.R
import com.example.olschoolnotes.databinding.ItemNotesBinding
import com.example.olschoolnotes.ui.Fragments.HomeFragmentDirections



class NotesAdapter(val requireContext: Context, var notesList: List<Notes>) :RecyclerView.Adapter<NotesAdapter.notesViewHolder>() {

    fun filtering(newFilterList: ArrayList<Notes>) {
        notesList=newFilterList
        notifyDataSetChanged()
    }

    class notesViewHolder(val binding:ItemNotesBinding) : RecyclerView.ViewHolder(binding.root)



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): notesViewHolder {
        return notesViewHolder(ItemNotesBinding.inflate(LayoutInflater.from(parent.context),parent,false))

    }

    override fun onBindViewHolder(holder: notesViewHolder, position: Int) {
        val data= notesList[position]
        holder.binding.notesTitle.text =data.title
        holder.binding.notesSubTitle.text= data.subTitle
        holder.binding.notesDate.text=data.date

        when(data.priority){
            "1"->{
                holder.binding.notesPriority.setBackgroundResource(R.drawable.red_shape)
            }
            "2"->{
                holder.binding.notesPriority.setBackgroundResource(R.drawable.yellow_shape)

            }
            "3"->{
                holder.binding.notesPriority.setBackgroundResource(R.drawable.green_shape)

            }
        }

        holder.binding.root.setOnClickListener {
            val action = HomeFragmentDirections.actionHomeFragmentToEditNotesFragment(data)
            Navigation.findNavController(it).navigate(action)
        }

    }

    override fun getItemCount()=notesList.size
}