package com.treak.treak;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;


public class ListViewActivity extends Activity{

	ListView list;
	String[] itemname ={
			"1. Taylor",
			"2. Chris",
			"3. YOU",
			"4. Srijan",
			"5. Adway",
			"6. Sara",
			"7. Sam",
			"9. Carolyn",
			"10. Abel",
			"11. Teo"
		};

	Integer[] imgid={
			R.drawable.taylor,
			R.drawable.chris,
			R.drawable.daryl_buzz,
			R.drawable.srijan,
			R.drawable.adway,
			R.drawable.sara,
			R.drawable.sam,
			R.drawable.carolyn,
			R.drawable.abel,
			R.drawable.teo,
	};

	String[] descriptions ={
			"Taylor has a streak of 215 days. She has earned $225 so far",
			"Chris has a streak of 204 days. He has earned $205 so far",
			"You have a streak of 200 days. You have earned $200 so far",
			"Srijan has a streak of 180 days. He has earned $175 so far",
			"Adway has a streak of 165 days. He has earned $145 so far",
			"Sara has a streak of 130 days. She has earned $110 so far",
			"Sam has a streak of 125 days. He has earned $110 so far",
			"Carolyn has a streak of 100 days. She has earned $85 so far",
			"Abel has a streak of 80 days. He has earned $65 so far",
			"Teo has a streak of 75 days. He has earned $55 so far",
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_list);

		CustomListAdapter adapter=new CustomListAdapter(this, itemname, imgid, descriptions);
		list=(ListView)findViewById(R.id.list);
		list.setAdapter(adapter);

		list.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				if (position > 2) {
					Toast.makeText(getApplicationContext(), "Congrats!  You are beating " + descriptions[+position].split(" ")[0] + ". Keep it up!", Toast.LENGTH_SHORT).show();
				} else if (position < 2) {
					Toast.makeText(getApplicationContext(), "You are losing to " + descriptions[+position].split(" ")[0] + ". Keep working hard and catch up!", Toast.LENGTH_SHORT).show();
				} else {
					Toast.makeText(getApplicationContext(), "This is you!", Toast.LENGTH_SHORT).show();

				}
			}
		});
	}
}
