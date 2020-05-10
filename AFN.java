import java.io.*;
import java.util.*;

/*
	Utilice esta clase para guardar la informacion de su
	AFN. NO DEBE CAMBIAR LOS NOMBRES DE LA CLASE NI DE LOS 
	METODOS que ya existen, sin embargo, usted es libre de 
	agregar los campos y metodos que desee.
*/
public class AFN{
	LinkedList<String> Alfabeto;
	int Nstates;
	LinkedList<Integer> FStates;
	LinkedList<LinkedList<LinkedList<String>>>Transitions;

	/*
		Implemente el constructor de la clase AFN
		que recibe como argumento un string que 
		representa el path del archivo que contiene
		la informacion del AFN (i.e. "Documentos/archivo.AFN").
		Puede utilizar la estructura de datos que desee
	*/
	public AFN(String path){
		try{ 
			BufferedReader leerA = new BufferedReader(new FileReader(path));
			String Aaux = leerA.readLine();
			this.Alfabeto= new LinkedList<String>();
			for(int i=0;i<(Aaux.length());i= i+2){
				this.Alfabeto.add(Character.toString(Aaux.charAt(i)));
			}

			this.Nstates = Integer.parseInt(leerA.readLine());

			LinkedList<Integer> Eaux = new LinkedList<Integer>();
			String S  = leerA.readLine(); 
			
			for(int i=0;i<(S.length());i= i+2){
				Eaux.add(Integer.parseInt(""+S.charAt(i)));
			}
			this.FStates =Eaux;

			String aux ="";
			this.Transitions = new LinkedList<LinkedList<LinkedList<String>>>();
			LinkedList<LinkedList<String>> Taux =new LinkedList<LinkedList<String>>();
			String[] Tx;
			LinkedList<String> stx = new LinkedList<String>();
			while(aux != null){
				aux = leerA.readLine();
				Tx = aux.split(",");
				for(int i=0;i<(Tx.length);i++){
					String[] x = Tx[i].split(";");
					for(int n=0;n<(x.length);n++){
						stx.add(x[n]);
					 }
					Taux.add(stx);
					stx = new LinkedList<String>();
				}

				this.Transitions.add(Taux);
				stx = new LinkedList<String>();
				Taux = new LinkedList<LinkedList<String>>();
			}
		}catch(Exception e){
			System.err.println("--------------------");
		}
	}

	public LinkedList<String> Transition(int i,int j){
		return this.Transitions.get(i).get(j);
	}

	public LinkedList<String> lambda(String i){
		return this.Transitions.get(0).get(Integer.parseInt(i));
	}

	int q =0;
	public LinkedList<String> Lunion(LinkedList<String> x,LinkedList<String> F){
		LinkedList<String> o = (LinkedList<String>) F.clone();
		if((x.size()==1)&&!(F.contains(x.getFirst()))){

			o.add(x.getFirst());
		}else{

			for(int i=1;i<x.size();i++){
				if(!(o.contains(x.getFirst()))){
					o.add(x.get(0));
					q++;
					o=(Lunion(lambda(x.get(i)),o));
					F=new LinkedList<String>();
			}
				}
	   }
	 return o;
	   
	}
	
	public LinkedList<String> Cunion(LinkedList<LinkedList<String>> n){
		LinkedList<String> m = new LinkedList<String>();
		for(int i=0;i<(n.size());i++){
			for(int j=0;j<(n.get(i).size());j++){
				if(!(m.contains(n.get(i).get(j)))){
					m.add(n.get(i).get(j));
				}
			}
		}

		return m;
	}
	public LinkedList<String> Alf(){
		return this.Alfabeto;
	}

	public LinkedList<String> getT(int a,LinkedList<String> b){
		LinkedList<LinkedList<String>> n2 = new LinkedList<LinkedList<String>>();
		for(int i=0;i<(b.size());i++){
				n2.add(this.Transitions.get(a).get(Integer.parseInt(b.get(i))));
		}

		return this.Cunion(n2);
	}

	public void cambio(LinkedList<LinkedList<String>> G,LinkedList<LinkedList<Integer>> E,LinkedList<Integer> u){
		LinkedList<String> F = new LinkedList<String>();
		for(int k=0;k<(G.size());k++){ 
			for(int i=0;i<(this.Alfabeto.size());i++){
				LinkedList<String> L = this.Lunion(this.getT(i+1,G.get(k)),F);
				if(!(G.contains(L))){
					G.add(L);
					for(int h=0;h<(L.size());h++){
						if(this.FStates.contains(Integer.parseInt(L.get(h)))){
							u.add(G.indexOf(L)+1);
						}
					}
					E.get(i).add(G.indexOf(L)+1);
				}else{
					for(int h=0;h<(L.size());h++){
						if(this.FStates.contains(Integer.parseInt(L.get(h)))){
							u.add(G.indexOf(L)+1);
						}
					}
					E.get(i).add(G.indexOf(L)+1);
				}
				
			}
		}
		
	}

	public String toString(){
		String R = ("Alfabeto: "+this.Alfabeto+"\nNumero de Estados: "+this.Nstates+"\nEstados Finales: "+ this.FStates+"\nTransiciones: "+this.Transitions.toString());
		return R;
	}

	/*
		Implemente el metodo accept, que recibe como argumento
		un String que representa la cuerda a evaluar, y devuelve
		un boolean dependiendo de si la cuerda es aceptada o no 
		por el AFN. Recuerde lo aprendido en el proyecto 1.
	*/
	public boolean accept(String string){
		return false;
	}

	/*
		El metodo main debe recibir como primer argumento el path
		donde se encuentra el archivo ".afd", como segundo argumento 
		una bandera ("-f" o "-i"). Si la bandera es "-f", debe recibir
		como tercer argumento el path del archivo con las cuerdas a 
		evaluar, y si es "-i", debe empezar a evaluar cuerdas ingresadas
		por el usuario una a una hasta leerA una cuerda vacia (""), en cuyo
		caso debe terminar. Tiene la libertad de implementar este metodo
		de la forma que desee. 
	*/
	public static void main(String[] args) throws Exception{
		String f = args[0];
		AFN x = new AFN(f);
		System.out.println(x.toString());
		LinkedList<String> F = new LinkedList<String>();
		//System.out.println(x.lambda("2"));
		System.out.println(x.Lunion(x.getT(1,x.lambda("1")),F));
		
	}
}


