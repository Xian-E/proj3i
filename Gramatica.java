import java.io.BufferedReader;
import java.io.FileReader;
import java.util.LinkedList;

public class Gramatica{

	LinkedList<Character> NotTerminals;
	LinkedList<Character> Terminals;
	char Start;
	LinkedList<LinkedList<String>> Prules;

	public Gramatica(String Path){
		try{
			BufferedReader leer = new BufferedReader(new FileReader(Path));
			String aux = leer.readLine();
			LinkedList<Character> auxx = new LinkedList<Character>();
			String[] T = aux.split(",");

			for(int i=0;i<T.length;i++){
				auxx.add(T[i].charAt(0));
			}

			this.NotTerminals = auxx;

			aux = leer.readLine();
			auxx = new LinkedList<Character>();
			T = aux.split(",");

			for(int i=0;i<T.length;i++){
				auxx.add(T[i].charAt(0));
			}
			this.Terminals = auxx;

			aux = leer.readLine();
			this.Start = aux.charAt(0);

			LinkedList<LinkedList<String>> P = new LinkedList<LinkedList<String>>();
			LinkedList<String> r = new LinkedList<String>();
			aux = leer.readLine();
			int i = 0;
			
			while(aux != null){
				if(Character.toString(aux.charAt(0)).equals(Character.toString(this.NotTerminals.get(i)))){
					r.add(aux.substring(3,aux.length()));
					aux = leer.readLine();
					if(aux == null){
						P.add(r);
					}
					

				}else{
					i++;
					P.add(r);
					r = new LinkedList<String>();
				}

			}
			
			this.Prules=P;
			

			


		}catch(Exception e){
			System.err.println("--------------------");
		}
	}


	public void AFD(){
		
	}

	public void AFN(){

	}

	public void CHECK(){
		
	}

	public String toString(){
		String y= "\nReglas de Produccion: ";
		for(int i=0;i<(this.NotTerminals.size());i++){
			y = y + "\n"+this.NotTerminals.get(i)+"-->"+this.Prules.get(i);
		}

		String x = ("No Terminales: "+ this.NotTerminals+ "\nTerminles: "+this.Terminals+"\nInicial: "+this.Start+y);
		return x;
	}


	public static void main(String[] args) throws Exception{
		String gramatica = args[0];
	/*	String bandera = args[1];
		String salida = args[2];
		boolean n1 = bandera.equals("-afn");
		boolean n2 = bandera.equals("-afd");
		boolean n3 = bandera.equals("-check");
		if(n1){

		}else if(n2){

		}else if(n3){
			String cuerdas = args[3];
		}else{
			System.out.println("NO SEAN PENDEJOS PS");
		}*/

		Gramatica G = new Gramatica(gramatica);
		System.out.println(G.toString());
	}
}