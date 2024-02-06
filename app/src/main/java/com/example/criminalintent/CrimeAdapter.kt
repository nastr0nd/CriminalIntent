package com.example.criminalintent

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

class CrimeAdapter(
    diffCallback: CrimeDiffCallback,
    private val callbacks: CrimeListFragment.Callbacks?
) : ListAdapter<Crime, CrimeAdapter.CrimeViewHolder>(diffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CrimeViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item_crime, parent, false)
        return CrimeViewHolder(view)
    }

    override fun onBindViewHolder(holder: CrimeViewHolder, position: Int) {
        holder.bind(getItem(position))
    }


    inner class CrimeViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val titleTextView: TextView = itemView.findViewById(R.id.crime_title)
        private val dateTextView: TextView = itemView.findViewById(R.id.crime_date)
        private val solvedImageView: ImageView = itemView.findViewById(R.id.crime_solved)

        fun bind(crime: Crime) {
            titleTextView.text = crime.title
            dateTextView.text = crime.date.toString()
            solvedImageView.isVisible = crime.isSolved
            itemView.setOnClickListener { callbacks?.onItemSelected(crime.id) }
        }
    }
}