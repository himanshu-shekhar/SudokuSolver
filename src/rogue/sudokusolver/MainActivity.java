package rogue.sudokusolver;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.text.InputFilter;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;


public class MainActivity extends Activity {

	List< List< List<Integer> > > arr=new ArrayList<List <List <Integer>>>();
	int bf=0;
	int test=-1;
	boolean debugView=false;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		createDesign();
		createArray(arr);
		
		final Button solveButton=(Button)findViewById(R.id.SolveButton);
		solveButton.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//startSolving();
				//TableRow tr=(TableRow)((TableLayout)findViewById(R.id.sudoku)).getChildAt(0);
				//solveButton.setText(((EditText)tr.getChildAt(0)).getText().toString());
				fillArray();
				startSolving();
			}
		});
	}

	void startSolving()
	{
		Button solveButton=(Button)findViewById(R.id.SolveButton);
		
		if(solve())
		{
			//System.out.println("Solved:\n\n");
			fillSudoku();
			solveButton.setText("Solved using :"+String.valueOf(bf)+" Assumptions");
			
		}
		else
		{
			//System.out.println("Cannot be solved:\n\n");
			fillSudoku();
			solveButton.setText("Cannot be solved. Tried :"+String.valueOf(bf)+" Assumptions");
		}
		//System.out.println("Total wrong assumptions: " + String.valueOf(bf));
		bf=0;
	}
	void createDesign()
	{
		TableLayout tl=(TableLayout)findViewById(R.id.sudoku);
		int i,j;
		for(i=0; i<9; i++)
		{
			TableRow tr=new TableRow(this);
			for(j=0; j<9; j++)
			{
				tr.addView(new EditText(this));
				final EditText et=(EditText)tr.getChildAt(j);;
				et.setText(testCase());
				et.setBackgroundColor(getColor(i,j));
				et.setOnClickListener(new OnClickListener(){

					@Override
					public void onClick(View arg0) {
						// TODO Auto-generated method stub
						et.setText("");
					}
					
				});
				
			}
			tl.addView(tr);
		}	
	}
	
	int getColor(int i,int j)
	{
		int gray=Color.LTGRAY;
		int white=Color.WHITE;
		if(i>=0 && i<=2 && j>=3 && j<=5)
			return gray;
		if(i>=3 && i<=5 && j>=0 && j<=2)
			return gray;
		if(i>=3 && i<=5 && j>=6 && j<=8)
			return gray;
		if(i>=6 && i<=8 && j>=3 && j<=5)
			return gray;
		return white;
	}
	String testCase()
	{
		String[] s=new String[9];
		s[0]="0 0 0 0 0 0 8 0 0";
		s[1]="0 7 0 0 0 3 0 0 9";
		s[2]="9 8 3 4 0 0 0 0 0";
		s[3]="0 3 8 1 7 0 0 0 0";
		s[4]="0 5 0 0 0 0 0 2 0";
		s[5]="0 0 0 0 2 6 3 7 0";
		s[6]="0 0 0 0 0 9 7 8 4";
		s[7]="7 0 0 2 0 0 0 9 0";
		s[8]="0 0 4 0 0 0 0 0 0";
		for(int i=0; i<9; i++)
			s[i]=s[i].replace(" ", "");
		test++;
		return "0";
		//return String.valueOf(s[(int)(test/9)].charAt(test%9));
		
	}
	void fillArray()
	{
		int i,j,k;
		TableLayout tl=(TableLayout)findViewById(R.id.sudoku);
		TableRow tr;
		EditText et;
		String s;
		for(i=0; i<9; i++)
		{
			tr=(TableRow)tl.getChildAt(i);
			for(j=0; j<9; j++)
			{
				et=(EditText)tr.getChildAt(j);
				s=et.getText().toString().replace(" ", "");
				if(s.contentEquals(" ") || s.contentEquals("") ||s.contentEquals("0"))
				{
					for(k=0; k<10; k++)
					{
						arr.get(i).get(j).add(k);
					}
				}
				else
					arr.get(i).get(j).add(Integer.parseInt(s));
			}
		}
	}
	
	void fillSudoku()
	{
		int i,j;
		TableLayout tl=(TableLayout)findViewById(R.id.sudoku);
		TableRow tr;
		EditText et;
		for(i=0; i<9; i++)
		{
			tr=(TableRow)tl.getChildAt(i);
			for(j=0; j<9; j++)
			{
				et=(EditText)tr.getChildAt(j);
				et.setText(String.valueOf(arr.get(i).get(j).get(0)));
			}
		}
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	void createArray(List<List<List<Integer>>> a)
	{
		int i,j;
		for(i=0; i<9; i++)
		{
			List<List<Integer>> tmp1=new ArrayList<List<Integer> >();
			for(j=0; j<9; j++)
			{
				List<Integer> tmp2=new ArrayList<Integer>();
				tmp1.add(tmp2);
			}
			a.add(tmp1);
		}
		
		
	}
	
	void display()
	{
		if(debugView)
		{
			int i,j;
			for(i=0; i<9; i++)
			{
				for(j=0; j<9; j++)
				{
					System.out.print(String.valueOf(arr.get(i).get(j).get(0))+"\t");
				}
				System.out.println();
			}
		}
	}
	boolean update(int x, int y)
	{
		//dnt know y i used flag :(
		boolean flag=false;
		int i,j,n;
		int tmp;
		n=arr.get(x).get(y).get(0);
		for(i=0; i<9; i++)
		{
			//same row
			tmp=arr.get(x).get(i).get(0);
			if(tmp==0)
			{
				arr.get(x).get(i).remove(new Integer(n));
				if(arr.get(x).get(i).size()==2)
				{
					flag=true;
					if(!allowed(x,i,arr.get(x).get(i).get(1)))
						return false;
					arr.get(x).get(i).remove(0);
					update(x,i);
				}
				else if(arr.get(x).get(i).size()==1)
					return false;
			}
			//same column
			tmp=arr.get(i).get(y).get(0);
			if(tmp==0)
			{
				arr.get(i).get(y).remove(new Integer(n));
				if(arr.get(i).get(y).size()==2)
				{
					flag=true;
					if(!allowed(i,y,arr.get(i).get(y).get(1)))
						return false;
					arr.get(i).get(y).remove(0);
					update(i,y);
				}
				else if(arr.get(i).get(y).size()==1)
					return false;
			}
		}
		
		//same block
		for(i=x-(x%3); i<x-(x%3)+3 ; i++)
		{
			for(j=y-(y%3); j<y-(y%3)+3 ; j++)
			{
				tmp=arr.get(i).get(j).get(0);
				if(tmp==0)
				{
					arr.get(i).get(j).remove(new Integer(n));
					if(arr.get(i).get(j).size()==2)
					{
						flag=true;
						if(!allowed(i,j,arr.get(i).get(j).get(1)))
							return false;
						arr.get(i).get(j).remove(0);
						update(i,j);
					}
					else if(arr.get(i).get(j).size()==1)
						return false;
				}
			}
		}
		return true;
	}
	
	void copyState(List< List< List <Integer > > > dest, List< List< List <Integer > > > src)
	{
		int i,j,k;
		for(i=0; i<9; i++)
		{
			for(j=0; j<9; j++)
			{
				dest.get(i).get(j).clear();
				for(k=0; k<src.get(i).get(j).size(); k++)
				{
					dest.get(i).get(j).add(src.get(i).get(j).get(k));
				}
			}
		}
		debug(dest,"dest:");
	}
	
	boolean solve()
	{
		int i,j,k;
		boolean flag=true;
		for(i=0; i<9; i++)
		{
			for(j=0; j<9; j++)
			{
				if(arr.get(i).get(j).get(0)!=0)
				{
					update(i,j);
				}
			}
		}
		if(complete())
			return true;
		return bruteForce();
	}
	
	boolean complete()
	{
		int i,j;
		for(i=0; i<9; i++)
		{
			for(j=0; j<9; j++)
			{
				if(arr.get(i).get(j).get(0)==0)
					return false;
			}
		}
		return valid();
	}
	
	boolean allowed(int x,int y,int n)
	{
		int i,j;
		for(i=0; i<n ;i++)
		{
			if(arr.get(x).get(i).get(0)==n)
				return false;
			if(arr.get(i).get(y).get(0)==n)
				return false;
		}
		for(i=x-(x%3); i<x-(x%3)+3; i++)
		{
			for(j=y-(y%3); j<y-(y%3)+3; j++)
				if(arr.get(i).get(j).get(0)==n)
					return false;
		}
		return true;
	}
	
	boolean valid()
	{
		Set row=new HashSet();
		Set col=new HashSet();
		Set block=new HashSet();
		int i,j,k,l;
		for(i=0 ;i<9 ;i++)
		{
			row.clear();
			col.clear();
			for(j=0; j<9; j++)
			{
				row.add(arr.get(i).get(j).get(0));
				col.add(arr.get(j).get(i).get(0));
			}
			if(row.size()!=9 || col.size()!=9)
				return false;
		}
		for(k=0; k<9; k+=3)
		{
			for(l=0; l<9; l+=3)
			{
				block.clear();
				for(i=k; i<k+3; i++)
				{
					for(j=l; j<l+3; j++)
					{
						block.add(arr.get(i).get(j).get(0));
					}
				}
				if(block.size()!=9)
					return false;
			}
		}
		return true;
	}
	boolean bruteForce()
	{
		//debug();
		bf++;
		int i,j;
		int min=10,x=0,y=0;
		boolean flag=false;
		for(i=0; i<9; i++)
		{
			for(j=0; j<9; j++)
			{
				if(arr.get(i).get(j).size()<min && arr.get(i).get(j).get(0)==0)
				{
					min=arr.get(i).get(j).size();
					x=i;
					y=j;
				}
			}
		}
		
		if(min==10)
			return false;
		List<List<List<Integer> > > currState= new ArrayList<List<List<Integer> > >();
		createArray(currState);
		copyState(currState, arr);
		debug(arr,"real:");
		//debug(currState,"curr:");
		for(i=1; i<arr.get(x).get(y).size(); i++)
		{
			System.out.println("Setting :"+x+" "+y+" as "+ arr.get(x).get(y).toString()+" to "+arr.get(x).get(y).get(i));
			arr.get(x).get(y).set(0, arr.get(x).get(y).get(i));
			flag=update(x,y);
			if(flag)
			{
				bruteForce();
				if(complete())
					return true;
			}
			else
				System.out.println("Wrong assumption:");
			copyState(arr,currState);
		}
		currState.clear();
		//debug(currState,"saved:");
		return false;
	}
	void debug(List<List<List<Integer>>> a, String line)
	{
		if(debugView==true)
		{
			int i,j;
			System.out.println(line);
			System.out.println("Current bf :"+bf);
			for(i=0; i<9; i++)
			{
				for(j=0; j<9; j++)
					System.out.print(a.get(i).get(j).toString()+"\t");
				System.out.println();
			}
		}
	}
}
