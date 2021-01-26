package com.company;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws IOException{
	    if(args.length != 1){
	        throw new IllegalArgumentException("Enter as argument name of bytecode");
        }

	    File bytecode_file = new File(args[0]);

	    Vm vm = new Vm(bytecode_file);
	    vm.run();

    }
}

class Vm {
    private int register[] = new int[6];
    private byte bytecode[] = new byte[0x1000];
    private boolean exit_now = false;
    private int instruction_pointer = 0;
    private boolean jump = false;

    public Vm(File bytecode_file) throws IOException {
        FileInputStream fileInputStream = new FileInputStream(bytecode_file);
        fileInputStream.read(bytecode, 0, 0x1000);
        fileInputStream.close();
    }

    public void run(){

        while(!exit_now && instruction_pointer < 0x1000){

            if(!jump)
                instruction_pointer ++;
            else
                jump = false;

            switch (bytecode[instruction_pointer - 1]){
                case 0x00: Z(); break;
                case 0x01: S(); break;
                case 0x10: T(); break;
                case 0x20: I(); break;
                case 0x6f: IN(); break;
                case 0x5f: OUT(); break;
                case 0x7f: EXIT(); break;
            }
        }
    }
    /*
    * exit function
    * */
    private void EXIT(){
        exit_now = false;
    }

    /*
    * method for input a number
    * */
    private void IN(){
        Scanner scanner = new Scanner(System.in);
        register[bytecode[instruction_pointer]] = scanner.nextInt();
        instruction_pointer++;
    }
    /*
    * Println method
    * */
    private void OUT(){
        System.out.println(register[bytecode[instruction_pointer]]);
        instruction_pointer++;
    }
    /*
    * Z(n) => z[n] = 0 ; resetting the registry
    */
    private void Z(){
        register[bytecode[instruction_pointer]] = 0;
        instruction_pointer++;
    }

    /*
    * S(n) => z[n] = z[n] + 1 ; iterate registry
    * */
    private void S(){
        register[bytecode[instruction_pointer]]++;
        instruction_pointer++;
    }

    /*
    * T(m, n) => z[n] = z[m]
    * */
    private void T(){
        register[bytecode[instruction_pointer+1]] = register[bytecode[instruction_pointer]];
        instruction_pointer+=2;
    }

    /*
    * I(m, n, q) => if z[m] == z[n] then go to q
    * */
    private void I(){
        if(register[bytecode[instruction_pointer]] == register[bytecode[instruction_pointer + 1]]) {
            instruction_pointer += bytecode[instruction_pointer + 2];
            jump = true;
        }
        else
            instruction_pointer+=3;
    }

}
