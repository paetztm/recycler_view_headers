package timothypaetz.com.recyclersectionheader

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import timothypaetz.com.recyclersectionheader.RecyclerSectionItemDecoration.SectionCallback
import java.util.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val recyclerView: RecyclerView = findViewById(R.id.recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(
            this,
            LinearLayoutManager.VERTICAL,
            false
        )
        val presidents = PersonRepo().people.sorted()
        val sectionItemDecoration = RecyclerSectionItemDecoration(
            resources.getDimensionPixelSize(R.dimen.recycler_section_header_height),
            true,
            getSectionCallback(presidents)
        )
        recyclerView.addItemDecoration(sectionItemDecoration)
        recyclerView.adapter = PersonAdapter(
            layoutInflater,
            presidents,
            R.layout.recycler_row
        )
    }

    private fun getSectionCallback(people: List<Person>): SectionCallback {
        return object : SectionCallback {
            override fun isSection(position: Int): Boolean {
                return (position == 0 || people[position].lastName[0] != people[position - 1].lastName[0])
            }

            override fun getSectionHeader(position: Int): CharSequence {
                return people[position].lastName.subSequence(0, 1)
            }
        }
    }
}