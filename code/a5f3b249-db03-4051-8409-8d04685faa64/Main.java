import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Main {

    public static void main(String[] args) throws IOException {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        String NM = br.readLine();
        int N = Integer.parseInt(NM.split(" ")[0]);
        int M = Integer.parseInt(NM.split(" ")[1]);

        List<String> namesList = new ArrayList<>();
        List<String> namesSet = new ArrayList<>();

        for (int i = 0; i < N; i++) {
			namesSet.add(br.readLine());
		}

		for (int i = 0; i < M; i++) {
			String str = br.readLine();
			if (namesSet.contains(str)) {
				namesList.add(str);
			}

		}

        Collections.sort(namesList);
        bw.write(namesList.size() + "\n");
        for(String name : namesList){
            bw.write(name + "\n");
        }
        bw.flush();
        bw.close();
    }
}