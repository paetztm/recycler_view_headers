package timothypaetz.com.recyclersectionheader;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this,
                                                              LinearLayoutManager.VERTICAL,
                                                              false));

        List<Person> people = getPeople();

        RecyclerSectionItemDecoration sectionItemDecoration =
            new RecyclerSectionItemDecoration(getResources().getDimensionPixelSize(R.dimen.recycler_section_header_height),
                                              false);

        sectionItemDecoration.setSections(getSectionsForPeople(people));
        recyclerView.addItemDecoration(sectionItemDecoration);

        recyclerView.setAdapter(new PersonAdapter(getLayoutInflater(),
                                                  people,
                                                  R.layout.recycler_row));
    }

    private List<RecyclerSectionItemDecoration.Section> getSectionsForPeople(List<Person> people) {

        List<RecyclerSectionItemDecoration.Section> sections = new ArrayList<>();
        CharSequence previousTitle = "";
        for (int i = 0, size = people.size(); i < size; i++) {
            Person person = people.get(i);
            CharSequence section = person.getLastName()
                                         .subSequence(0,
                                                      1);
            sections.add(new RecyclerSectionItemDecoration.Section(section,
                                                                   !previousTitle.equals(section)));
            previousTitle = section;
        }
        return sections;
    }

    private List<Person> getPeople() {
        PersonRepo personRepo = new PersonRepo();
        List<Person> people = personRepo.getPeople();
        Collections.sort(people);
        return people;
    }
}
