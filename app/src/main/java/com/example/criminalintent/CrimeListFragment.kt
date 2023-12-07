package com.example.criminalintent

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

private const val TAG = "CrimeListFragment"

class CrimeListFragment: Fragment() {                                                                                   // Фрагмент списка преступлений

    private lateinit var crimeRecyclerView: RecyclerView
    private var adapter: CrimeAdapter? = null

    private val crimeListViewModel: CrimeListViewModel by lazy {
        ViewModelProviders.of(this).get(CrimeListViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
        ): View? {
        val view = inflater.inflate(R.layout.fragment_crime_list, container, false)

        crimeRecyclerView = view.findViewById(R.id.crime_recycler_view) as RecyclerView
        crimeRecyclerView.layoutManager = LinearLayoutManager(context)

        updateUI()

        return view
    }

    private fun updateUI() {                                                                                            // Инициализация адаптера и установка его в RecyclerView
        val crimes = crimeListViewModel.crimes
        adapter = CrimeAdapter(crimes)
        crimeRecyclerView.adapter = adapter
    }

    open class Holder(view:View): RecyclerView.ViewHolder(view) {
        open fun bind (crime: Crime) {
        }
    }

    private inner class CrimeHolder(view: View) : Holder(view), OnClickListener {                // Внутренний класс, представляющий элемент списка в RecyclerView

        private lateinit var crime: Crime
        private val titleTextView: TextView = itemView.findViewById(R.id.crime_title)
        private val dateTextView: TextView = itemView.findViewById(R.id.crime_date)
        private val solvedImageView: ImageView = itemView.findViewById(R.id.crime_solved)

        init {
            itemView.setOnClickListener(this)
        }

        override fun bind(crime: Crime) {                                                                                        // Заполняет данные элемента списка данными из объекта Crime
            this.crime = crime
            titleTextView.text = this.crime.title
            dateTextView.text = this.crime.date.toString()
            solvedImageView.visibility = if (crime.isSolved) {
                View.VISIBLE
            } else {
                View.GONE
            }
        }

        override fun onClick(v: View) {
            Toast.makeText(context, "${crime.title} pressed!", Toast.LENGTH_SHORT)
                .show()
        }
    }

    private inner class PoliceCrimeHolder(view:View): Holder(view), OnClickListener {

        private val contactPoliceButton: Button = itemView.findViewById(R.id.contactPoliceButton)
        private lateinit var crime: Crime
        private val titleTextView: TextView = itemView.findViewById(R.id.crime_title)
        private val dateTextView: TextView = itemView.findViewById(R.id.crime_date)
        private val solvedImageView: ImageView = itemView.findViewById(R.id.crime_solved)

        init {
            itemView.setOnClickListener(this)
            contactPoliceButton.setOnClickListener{
                Toast.makeText(context, "Calling 911!", Toast.LENGTH_SHORT)
                    .show()
            }
        }

        override fun bind(crime: Crime) {                                                                                        // Заполняет данные элемента списка данными из объекта Crime
            this.crime = crime
            titleTextView.text = this.crime.title
            dateTextView.text = this.crime.date.toString()
            solvedImageView.visibility = if (crime.isSolved) {
                View.VISIBLE
            } else {
                View.GONE
            }
        }

        override fun onClick(v: View?) {
            Toast.makeText(context, "${crime.title} pressed!", Toast.LENGTH_SHORT)
                .show()
        }

    }

    private inner class CrimeAdapter(var crimes: List<Crime>) : RecyclerView.Adapter<Holder>() {                    // Адаптер, управляющий данными для RecyclerView

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {                                 // Создаётся новый CrimeHolder для каждого элемента списка
            return when (viewType) {
                NORMAL_VIEW_TYPE -> {
                    val view = layoutInflater.inflate(R.layout.list_item_crime, parent, false)
                    CrimeHolder(view)
                }
                else -> {
                    val view = layoutInflater.inflate(R.layout.list_item_crime_police, parent, false)
                    PoliceCrimeHolder(view)
                }
            }
        }

        override fun onBindViewHolder(holder: Holder, position: Int) {                                               // Передаются данные из списка для конкретной позиции в CrimeHolder
            val crime = crimes[position]
            holder.bind(crime)
        }

        override fun getItemCount(): Int {
            return crimes.size
        }

        override fun getItemViewType(position: Int): Int {
            val crime = crimes[position]
            return if (crime.requiresPolice) {
                POLICE_VIEW_TYPE
            } else {
                NORMAL_VIEW_TYPE
            }
        }

    }

    companion object {
        fun newInstance(): CrimeListFragment {
            return CrimeListFragment()
        }
        private const val NORMAL_VIEW_TYPE = 0
        private const val POLICE_VIEW_TYPE = 1
    }
}


// CrimeListFragment отображает список преступлений в вертикальном списке с использованием RecyclerView.
// Каждый элемент списка представлен вложенным классом CrimeHolder, который содержит информацию о заголовке и дате преступления.
// Адаптер CrimeAdapter управляет данными для каждого элемента списка, а CrimeListViewModel служит для хранения данных о преступлениях.