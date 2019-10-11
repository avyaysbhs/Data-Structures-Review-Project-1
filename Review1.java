import java.io.*;
import java.util.List;
import java.util.ArrayList;

public class Review1 {
	public static DSUtil.NumberSet[] convertFileIntoSets(File f) {
		ArrayList<DSUtil.NumberSet> ns = new ArrayList<>();

		if (f.exists()) {
            Assignment.announceInput();
			try {
				String fileContents = DSUtil.readFileContents(f, (s) -> {
					DSUtil.NumberSet new_set = DSUtil.NumberSet.readFromString(s);
					ns.add(new_set);
                });
                
                Assignment.printForInput("\t" + fileContents.replace("\n", "\n\t"));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		DSUtil.NumberSet[] output = new DSUtil.NumberSet[ns.size()];

		for (int i=0;i<ns.size();i++) {
			output[i] = ns.get(i);
		}

		return output;
	}

	public static List<DSUtil.NumberSet[]> splitIntoGroups(DSUtil.NumberSet[] all_sets, int group_count) {
		ArrayList<DSUtil.NumberSet[]> set_of_sets_of_sets = new ArrayList<>();

		DSUtil.NumberSet[] current_set = new DSUtil.NumberSet[3];
		int c = 0;

		for (int i=0;i<all_sets.length;i++) {
			current_set[c++] = all_sets[i];
			if (c > (group_count - 1)) {
				set_of_sets_of_sets.add(current_set);
				current_set = new DSUtil.NumberSet[3];
				c = 0;
			}
		}

		return set_of_sets_of_sets;
	}

	public static void main(String[] args) {
		Assignment.announceAssignment("\"Game-Set-Match\"", 1);

		List<DSUtil.NumberSet[]> all_sets = Review1.splitIntoGroups(
			Review1.convertFileIntoSets(new File("Input Files/Prob1Input.txt")), 3
        );
		
		Assignment.announceOutput();

		for (DSUtil.NumberSet[] sets: all_sets) {
			DSUtil.NumberSet set = sets[0];

			for (int i=0;i<sets.length;i++) {
				Assignment.printForOutput(String.format("Set %d: %s", i + 1, sets[i]));
			}

			for (DSUtil.NumberSet _set: sets) {
				if (_set != null) {
					if (set == null) {
						set = _set;
					} else {
						set = new DSUtil.NumberSet(set.getIntersection(_set));
					}
				}
			}

			if (!set.isEmpty())
				Assignment.out.print("\tIntersection of sets: ");
                Assignment.out.println(set);
        }

        Assignment.out.println("\r\n");
	}
}