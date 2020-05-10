/*
	Utilice esta clase para guardar la informacion de su
	AFD. NO DEBE CAMBIAR LOS NOMBRES DE LA CLASE NI DE LOS 
	METODOS que ya existen, sin embargo, usted es libre de 
	agregar los campos y metodos que desee.
*/
import java.io.*;
import java.util.*;
public class AFD{
	LinkedList<String> Alfabeto;
	int Nstates;
	LinkedList<Integer> FStates;
	LinkedList<LinkedList<String>> Transitions;
	
	/*
		Implemente el constructor de la clase AFD
		que recibe como argumento un string que
		Puede utilizar la estructura de datos que desee
	*/
	public AFD(String path){
		try{ 
			BufferedReader leer = new BufferedReader(new FileReader(path));

			String Aaux = leer.readLine();
			this.Alfabeto= new LinkedList<String>();
			for(int i=0;i<(Aaux.length());i= i+2){
				this.Alfabeto.add(Character.toString(Aaux.charAt(i)));
			}

			this.Nstates = Integer.parseInt(leer.readLine());

			LinkedList<Integer> Eaux = new LinkedList<Integer>();
			String S  = leer.readLine(); 
			
			for(int i=0;i<(S.length());i= i+2){
				Eaux.add(Integer.parseInt(""+S.charAt(i)));
			}
			this.FStates =Eaux;

			String aux ="";
			this.Transitions = new LinkedList<LinkedList<String>>();
			LinkedList<String> Taux =new LinkedList<String>();
			while(aux != null){
				aux = leer.readLine();
				for(int i=0;i<(aux.length());i= i+2){
					Taux.add(Character.toString(aux.charAt(i)));
				}
				this.Transitions.add(Taux);
				Taux = new LinkedList<String>();
			}

	}catch(Exception e){
		System.err.println("--------------------");
	}
		

	}

	public String toString(){
		String R = ("Alfabeto: "+this.Alfabeto+"\nNumero de Estados: "+this.Nstates+"\nEstados Finales: "+ this.FStates+"\nTransiciones: "+this.Transitions.toString());
		return R;
	}

	/*
		Implemente el metodo transition, que recibe de argumento
		un entero que representa el estado actual del AFD y un
		caracter que representa el simbolo a consumir, y devuelve 
		un entero que representa el siguiente estado
	*/
	public int getPos(String L){
		int p = this.Alfabeto.indexOf(L);
		return p;
	}

	public int getTransition(int currentState, char symbol){
		int next = 0;
		int pos = getPos(Character.toString(symbol));
		next = Integer.parseInt(this.Transitions.get(pos).get(currentState));
		return next;
	}

	public boolean Exist(String s){
		boolean x = false;
		int aux = 0;

		for(int i=0;i<(s.length());i++){
			
			aux = this.Alfabeto.indexOf(Character.toString(s.charAt(i)));
			if(aux == -1){
				i = s.length();
			}

		}

		if(aux != -1){
			x = true;
		}

		return x;
	}

	/*represifenta el path del archivo que contiene
		la informacion del afd (i.e. "Documentos/archivo.afd").
		Implemente el metodo accept, que recibe como argumento
		un String que representa la cuerda a evaluar, y devuelve
		un boolean dependiendo de si la cuerda es aceptada o no 
		por el afd
	*/
	public boolean accept(String string){
		boolean Acept = false;
		int currentS = 1;

		if(Exist(string)){


			for(int i=0;i<(string.length());i++){
				//System.out.println("Evaluando: "+string.charAt(i)+" en posicion: "+ currentS);
				currentS = getTransition(currentS,string.charAt(i));
			}

			if(string.length()==1){
				currentS = getTransition(currentS,string.charAt(0));
				
			}

			if(this.FStates.indexOf(currentS) != -1){
				Acept = true;
			}
		}else{Acept = false;}
		//System.out.println(Acept);

		return Acept;	
	}

	/*
		El metodo main debe recibir como primer argumento el path
		donde se encuentra el archivo ".afd", como segundo argumento 
		una bandera ("-f" o "-i"). Si la bandera es "-f", debe recibir
		como tercer argumento el path del archivo con las cuerdas a 
		evaluar, y si es "-i", debe empezar a evaluar cuerdas ingresadas
		por el usuario una a una hasta leer una cuerda vacia (""), en cuyo
		caso debe terminar. Tiene la libertad de implementar este metodo
		de la forma que desee. 
	*/
	public static void main(String[] args){
		try{ 
			String File = args[0];
			String Flag = args[1];
			AFD x = new AFD(File);
			boolean i = Flag.equals("-i");
			boolean f = Flag.equals("-f");
			if(f){
				System.out.println("Se evaluaran cuerdas del archivo...\n");
				String PP = args[2];
				try{
					Scanner in = new Scanner(new File(PP));
					while(in.hasNextLine()){
						String pele = in.nextLine();
						Boolean dc = x.accept(pele);
						if(dc){ 
							System.out.println("Cuerda("+ pele+")----------Aceptada! :)\n");
						}else{System.out.println("Cuerda("+ pele+")----------No Aceptada :(\n");}
					}
					in.close();
				}catch(Exception e){
					System.out.println("No pusiste bien la direccion del archivo");
				}
					
			}else if(i){
				Scanner sc = new Scanner(System.in);
				System.out.println("Instrucciones: ");
				System.out.println("*Escriba una cuerda a evaluar y se desplegara si fue aceptada o no.");
				System.out.println("*Para salir de este  modo evaluacion, Escriba la palabra `T3RM1N4R`. :)");
				String cuerda;
				boolean ac;
				while(true){
					System.out.println("\nIngresa una cuerda: ");
					cuerda = sc.nextLine();
					boolean hh = cuerda.equals("T3RM1N4R");
					if(hh){
						break;
					}else{
						ac = x.accept(cuerda);
						if(ac){
							System.out.println("\nCuerda Aceptada :)");
						}else{System.out.println("\nCuerda no Aceptada :(");}

					}
				}
				System.out.println("Gracias por usar el programa :)");
			}else{
				System.out.println("Bandera equivocada");
			}
		 }catch(java.lang.ArrayIndexOutOfBoundsException x){
			 System.out.println("\n*****No se ingreso Algun Parametro*****\n"+"\n****Intente de Nuevo****\n");
		 }
		
	}

}
