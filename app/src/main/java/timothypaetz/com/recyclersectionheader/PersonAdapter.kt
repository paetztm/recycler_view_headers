package timothypaetz.com.recyclersectionheader

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView

/**
 * Created by paetztm on 2/6/2017.
 */
class PersonAdapter(
    private val layoutInflater: LayoutInflater,
    private val people: List<Person>,
    @param:LayoutRes private val rowLayout: Int
) : RecyclerView.Adapter<PersonAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = layoutInflater.inflate(
            rowLayout,
            parent,
            false
        )
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val person = people[position]
        holder.fullName.text = person.fullName
    }

    override fun getItemCount(): Int {
        return people.size
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val fullName: TextView = view.findViewById(R.id.full_name_tv)
    }
}