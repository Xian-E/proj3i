import java.io.*;
import java.util.*;

public class Gramatica{

	LinkedList<String> NotTerminals;
	LinkedList<String> Terminals;
	char Start;
	LinkedList<LinkedList<String>> Prules;

	public Gramatica(String Path){
		try{
			BufferedReader leer = new BufferedReader(new FileReader(Path));
			String aux = leer.readLine();
			LinkedList<String> auxx = new LinkedList<String>();
			String[] T = aux.split(",");

			for(int i=0;i<T.length;i++){
				auxx.add(T[i]);
			}

			this.NotTerminals = auxx;

			aux = leer.readLine();
			auxx = new LinkedList<String>();
			T = aux.split(",");

			for(int i=0;i<T.length;i++){
				auxx.add(T[i]);
			}
			this.Terminals = auxx;

			aux = leer.readLine();
			this.Start = aux.charAt(0);

			LinkedList<LinkedList<String>> P = new LinkedList<LinkedList<String>>();
			LinkedList<String> r = new LinkedList<String>();
			aux = leer.readLine();
			int i = 0;
			
			while(aux != null){
				if(aux.substring(0,1).equals(this.NotTerminals.get(i))){
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
			if(this.Prules.size()!=this.NotTerminals.size()){
				this.NotTerminals.removeLast();
			}
			

			


		}catch(Exception e){
			System.err.println("--------------------");
		}
	}
	
	LinkedList<String> ag;
	public void RC(){
		for(int i=0;i<(this.NotTerminals.size());i++){
			for(int j=0;j<(this.Prules.get(i).size());j++){
				if(this.Prules.get(i).get(j).length()!=1){ 
					if(!(this.NotTerminals.contains(this.Prules.get(i).get(j).substring(1,2)))){
						if(this.NotTerminals.get(i).length()==1){
							String au = this.NotTerminals.get(i)+(j+1);
							this.NotTerminals.add(au);
							ag = new LinkedList<String>();
							ag.add(this.Prules.get(i).get(j).substring(1,this.Prules.get(i).get(j).length()));
							this.Prules.get(i).set(j,this.Prules.get(i).get(j).substring(0,1)+au);
							this.Prules.add(ag);
							
						}else if((this.NotTerminals.get(i).length())>1){
							System.out.println("j: "+j);
							String au = this.NotTerminals.get(i).substring(0,1)+(Integer.parseInt(this.NotTerminals.get(i).substring(1,this.NotTerminals.get(i).length()))+(j+1));
							this.NotTerminals.add(au);
							ag = new LinkedList<String>();
							ag.add(this.Prules.get(i).get(j).substring(1,this.Prules.get(i).get(j).length()));
							this.Prules.get(i).set(j,this.Prules.get(i).get(j).substring(0,1)+au);
							this.Prules.add(ag);
						}

				}
			  }else{this.Prules.get(i).set(j,(this.Prules.get(i).get(j)+"F"));}
			}
		}
		this.NotTerminals.add("F");
	}


	public void AFD(String D,String A)throws Exception{
		//"D" es el nombre del archivo afd a crear y "A" es la direccion del archivo afn a convertir.
		AFN a = new AFN(A);
		File archivo = new File(D);

		PrintWriter write = new PrintWriter(D);
		String alfabeto = "";

		for(int i=0;i<(a.Alf().size());i++){
			if(!(i == (a.Alf().size()-1))){
				alfabeto = alfabeto+ a.Alf().get(i)+",";
			}else{
				alfabeto = alfabeto+ a.Alf().get(i);
			}
		}

		write.println(alfabeto);

		

		LinkedList<String> F = new LinkedList<String>();
		LinkedList<LinkedList<String>> Estados = new LinkedList<LinkedList<String>>();
		Estados.add(a.Lunion(a.lambda("1"),F));
		LinkedList<LinkedList<Integer>> T = new LinkedList<LinkedList<Integer>>();
		LinkedList<Integer> Finales = new LinkedList<Integer>();
		for(int i=0; i<(a.Alfabeto.size());i++){
			T.add(new LinkedList<Integer>());
		}
		a.cambio(Estados, T,Finales);
		write.println(Estados.size()+1);
		String aux = "";
		for(int i=0;i<(Finales.size());i++){
			if(i==(Finales.size()-1)){
				aux = aux+Finales.get(i);
			}else{aux = aux+Finales.get(i)+",";}
		}
		write.println(aux);
		aux ="0,";
		for(int i=0;i<(T.size());i++){
			for(int k=0;k<(T.get(i).size());k++){
				if(k==(T.get(i).size()-1)){
					aux = aux+T.get(i).get(k);
				}else{aux = aux+T.get(i).get(k)+",";}
			}
			write.println(aux);
			aux ="0,";
		}
		
		write.close();
		
	}

	public void AFN(String path)throws Exception{
		this.RC();
		//RC es para convertir a forma normal la gramatica, solo por si te da curiosidad xD, pero no toques nada de nada :)
		File archivo = new File(path);
		PrintWriter write = new PrintWriter(path);
		String aux = "";
		
		for(int i=0;i<(this.Terminals.size());i++){
			if(i == (this.Terminals.size()-1)){
				aux = aux + this.Terminals.get(i);
			}else{aux = aux + this.Terminals.get(i)+",";}
		}
		write.println(aux);
		aux ="";
		write.println(this.NotTerminals.size()+1);
		write.println(this.NotTerminals.indexOf("F")+1);
		LinkedList<LinkedList<LinkedList<String>>> Tran = new LinkedList<LinkedList<LinkedList<String>>>();
		for(int i=0;i<(this.Terminals.size()+2);i++){
			LinkedList<LinkedList<String>> a = new LinkedList<LinkedList<String>>();
			for(int j=0;j<(this.NotTerminals.size()+1);j++){
				if(i==0){
					LinkedList<String> b = new LinkedList<String>();
					b.add(""+j);
					a.add(b);
				}else{ 
					LinkedList<String> b = new LinkedList<String>();
					if(j==0){
						b.add("0");
					}
					
					a.add(b);
				 }
			}
			Tran.add(a);
		}
		
		

		for(int i=0;i<(this.Prules.size());i++){
			for(int k=0;k<(this.Prules.get(i).size());k++){
				String au = this.Prules.get(i).get(k);
				if(!(this.NotTerminals.contains(au.substring(0,1)))){
					int p= this.Terminals.indexOf(au.substring(0,1));
					Tran.get(p+1).get(i+1).add(""+(this.NotTerminals.indexOf(au.substring(1,au.length()))+1));
				}else{ Tran.get(0).get(i+1).add(""+(this.NotTerminals.indexOf(au.substring(0,1))+1));}
			}
			
		}

		for(int k=0;k<(Tran.size()-1);k++){
			LinkedList<LinkedList<String>> u = Tran.get(k);
			for(int i=0;i<(u.size());i++){
				LinkedList<String> o = u.get(i);
				String x = "";
				if(!(o.isEmpty())){ 
					for(int j=0;j<(o.size());j++){
						if(j==(o.size()-1)){
							x = x+o.get(j);
						}else{
							x = x+o.get(j)+";";
						}
					}
				}else{x = x+"0";}

				if(i==(u.size()-1)){
					aux = aux+x;
				}else{
					aux = aux+x+",";
				}
			}
			write.println(aux);
			aux = "";	
		}




		write.close();
	}

	public void CHECK(String usepath, String pathtoprove, String nombreoriginala){
		try{
			AFD aff = new AFD(usepath);
			PrintWriter write = new PrintWriter(nombreoriginala);
			String aux = "";
			Scanner in = new Scanner(new File(pathtoprove));
			while(in.hasNextLine()){
				String linea = in.nextLine();
				int lol = linea.indexOf(",");
				//System.out.println(linea);
				//System.out.println(lol);
				String fund = linea.substring(0, lol);
				//System.out.println(fund); 
				boolean afk = aff.accept(fund);
				if(afk){
					aux = "Aceptada";
				}else{
					aux = "Rechazada";
				}
				//System.out.println(aux);
				write.println(aux);
			} 
			write.close();
		}catch(Exception e){
			System.out.println("--------------------");
		}

	}

	public String toString(){
		String y= "\nReglas de Produccion: ";
		for(int i=0;i<(this.Prules.size());i++){
			y = y + "\n"+this.NotTerminals.get(i)+"-->"+this.Prules.get(i);
		}

		String x = ("No Terminales: "+ this.NotTerminals+ "\nTerminles: "+this.Terminals+"\nInicial: "+this.Start+y);
		return x;
	}


	public static void main(String[] args) throws Exception{
		System.out.println("Bienvenidos al codigo :3");


		String gramatica = args[0];
		String bandera = args[1];
		String salida = args[2];
		boolean n1 = bandera.equals("-afn");
		boolean n2 = bandera.equals("-afd");
		boolean n3 = bandera.equals("-check");
		Gramatica G = new Gramatica(gramatica);
		if(n1){
			G.AFN(salida);	
		}else if(n2){
			String op = G.creadordearchivo(gramatica,"n");
			String jo = "./" + op;
			G.AFN(op);   
			G.AFD(salida, jo);	
		}else if(n3){
			if(args.length == 3){
				System.out.println(gramatica);
				String tt = G.creadordearchivo(gramatica, "n");
				String ar = G.creadordearchivo(gramatica, "d");
				System.out.println(tt);
				System.out.println(ar);
				G.AFN(tt);  
				String yy = "./" + tt ; 
				//System.out.println(yy);
				G.AFD(ar,yy);
				String nuevo = "./" + ar;
				String cuerdas = G.mamamia(tt) ;
				System.out.println(cuerdas);
				G.CHECK(nuevo, salida, cuerdas);

			}else{
				System.out.println(gramatica);
				String cuerdas = args[3];
				System.out.println(cuerdas);
				String tt = G.creadordearchivo(gramatica, "n");
				String ar = G.creadordearchivo(gramatica, "d");
				System.out.println(tt);
				System.out.println(ar);
				G.AFN(tt);  
				String yy = "./" + tt ; 
				//System.out.println(yy);
				G.AFD(ar,yy);
				String nuevo = "./" + ar;
				G.CHECK(nuevo, cuerdas, salida);
			}

		}else{
			System.out.println("NO SEAN PENDEJOS PS");
		}
		
		//G.AFN("prueba.afn");  //aqui creo el 
		//G.AFD("prueba.afd", ""); //aqui se crea el afd con el afn creado anteriormente
	 }

	public String creadordearchivo(String name,String letter){
		Random rd = new Random();
		String nombre = "";
		int kk = name.length();
		String a = name.substring(19, kk - 4);
		nombre = a + ".af" + letter;
		return nombre;
	}
	
	public String mamamia(String tt){
		int off = tt.indexOf(".");
		String flash = tt.substring(0,off);
		String cuerdas = "comprobacion_" + flash + ".txt";
		System.out.println(cuerdas);
		return cuerdas;
	}
		
}