package SDES;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import javax.swing.*;

class SDESView extends JFrame{
	Container c = getContentPane();
	//edk
	JPanel pnl1 = new JPanel();
	
	//edk_encryption
	JPanel pnlE = new JPanel();
	JLabel[] lblE = new JLabel[7];
	String[] lblES = {"Plain Text", "IP", "fk", "Switch", "fk", "IP-1", "Cipher Text"};
	JTextField[] tfE = new JTextField[7];
	
	//edk_key
	JPanel pnlK = new JPanel();
	JLabel[] lblK = new JLabel[7];
	String[] lblKS = {"Key(0~1023)", "Key(Binary Number)", "P10", "1bit Left Shift", "K1", "2bit Left Shift", "K2"};
	JTextField[] tfK = new JTextField[7];
	
	//edk_decryption
	JPanel pnlD = new JPanel();
	JLabel[] lblD = new JLabel[7];
	String[] lblDS = {"Decrypt Text", "IP-1", "fk", "Switch", "fx", "IP", "Encrypted Text"};
	JTextField[] tfD = new JTextField[7];
	
	//button
	JPanel pnl2 = new JPanel();
	JButton[] btn = new JButton[2];
	String[] btnS = {"½ÇÇà", "ÃÊ±âÈ­"};
	
	SDESView() {
		//edk
		c.setLayout(new BorderLayout());
		c.add(pnl1, BorderLayout.CENTER);
		pnl1.setLayout(new GridLayout(0, 3));
		
		//edk_encryption
		pnlE.setLayout(new GridLayout(0, 1));
		for(int i = 0; i < lblES.length; i++) {
			lblE[i] = new JLabel(lblES[i]);
			tfE[i] = new JTextField(15);
			pnlE.add(lblE[i]);
			pnlE.add(tfE[i]);
		}
		for(int i = 1; i < lblES.length; i++) {
			tfE[i].setEnabled(false);
		}
		pnl1.add(pnlE);
		
		//edk_key
		pnlK.setLayout(new GridLayout(0, 1));
		for(int i = 0; i < lblKS.length; i++) {
			lblK[i] = new JLabel(lblKS[i]);
			tfK[i] = new JTextField(15);
			pnlK.add(lblK[i]);
			pnlK.add(tfK[i]);
		}
		for(int i = 1; i < lblKS.length; i++) {
			tfK[i].setEnabled(false);
		}
		pnl1.add(pnlK);
		
		//edk_decryption
		pnlD.setLayout(new GridLayout(0, 1));
		for(int i = 0; i < lblDS.length; i++) {
			lblD[i] = new JLabel(lblDS[i]);
			tfD[i] = new JTextField(15);
			pnlD.add(lblD[i]);
			pnlD.add(tfD[i]);
			tfD[i].setEnabled(false);
		}
		pnl1.add(pnlD);
		
		//button
		for(int i = 0; i < btnS.length; i++) {
			btn[i] = new JButton(btnS[i]);
			pnl2.add(btn[i]);
		}
		c.add(pnl2, BorderLayout.SOUTH);
		
		//setFrame
		setTitle("S-DES");
		setSize(800, 500);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}
}

public class SDES{
	SDESView v = new SDESView();
	ActionHandler AHandler = new ActionHandler();
	//Key-------------------------------------------------------------------------
	//P10
	int P10[] = new int[10];
	int P10S[] = new int[] {3, 5, 2, 7, 4, 10, 1, 9, 8, 6};
	//1bit Left Shift
	int input[] = new int[10];
	//K1, K2
	int inputK10[] = new int[10];
	int K1[] = new int[8];
	int K2[] = new int[8];
	int P8[] = new int[] {6, 3, 7, 4, 8, 5, 10, 9};
	
	//Encryption, Descryption-----------------------------------------------------
	//IP
	String tempIP = new String();
	int IP8[] = new int[] {2, 6, 3, 1, 4, 8, 5, 7};
	int inputP[] = new int[8];
	int inputIP[] = new int[8];
	int IP[] = new int[8];
	//IP_1
	int IP8_1[] = new int[] {4, 1, 3, 5, 7, 2, 8, 6};
	int inputP_1[] = new int[8];
	int inputIP_1[] = new int[8];
	int tempIP_1[] = new int[8];
	int IP_1[] = new int [8];
	//fk
	//fk_EP
	int IPl[] = new int[4];
	int IPr[] = new int[4];
	int EP[] = new int[8];
	int EP8[] = new int[] {4, 1, 2, 3, 2, 3, 4, 1};
	//fk_xor
	int fk[] = new int[8];
	//switch
	//S-Box
	int fkl[] = new int[4];
	int fkr[] = new int[4];
	int S0[][] = new int[][] {{1, 0, 3, 2}, {3, 2, 1, 0}, {0, 2, 1, 3}, {3, 1, 3, 2}};
	int S1[][] = new int[][] {{0, 1, 2, 3}, {2, 0, 1, 3}, {3, 0, 1, 0}, {2, 1, 0, 3}};
	int S0Row[] = new int[2];
	int S0Col[] = new int[2];
	int S1Row[] = new int[2];
	int S1Col[] = new int[2];
	String S0RS = new String();
	String S0CS = new String();
	String S1RS = new String();
	String S1CS = new String();
	int S0RC = 0;
	int S1RC = 0;
	String bnumS0 = new String();
	String bnumS1 = new String();
	//P4
	int P4[] = new int[4];
	int P4S[] = new int[] {2, 4, 3, 1};
	//SW
	int tempSW[] = new int[4];
	int SW[] = new int[8];
	//D
	String D_Sentence = new String();
	String D_HextoKor = new String();
	//Kor
	String Uni = new String();
	String[] tempbin = new String[4];
	String[] bin = new String[2];
	String wordsum = new String();
	int[] words = new int[2];
	String[] chgs = new String[2];
	String[] tl = new String[2];
	
	//button.addActionListener
	public SDES() {
		for(int i = 0; i < v.btnS.length; i++) {
			v.btn[i].addActionListener(AHandler);
		}
	}
	//ActionListener
	class ActionHandler implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			//½ÇÇà
			if(e.getSource() == v.btn[0] && v.tfE[0].getText() != null && v.tfK[0].getText() != null) {
				//Å°°ª ¹üÀ§ È®ÀÎ_Á¤»ó ¹üÀ§
				if(Integer.parseInt(v.tfK[0].getText()) >= 0 && 
						Integer.parseInt(v.tfK[0].getText()) <= 1023) {
					System.out.println("------------------------------------");
					//Å°»ý¼º
					Key();
					System.out.println("------------------------------------");
					
					//Sentence(sentence, word, letter)
					String sentence = new String(v.tfE[0].getText());
					System.out.println("\nPlain Text : " + sentence);
					
					//ÇÑ ¹®ÀÚ¾¿ ¾ÏÈ£È­->º¹È£È­, Æò¹® ¹®ÀÚ¿­ÀÇ ±æÀÌ¸¸Å­ ¹Ýº¹
					for(int i = 0; i < sentence.length(); i++) {
						//letter select from sentence
						char chg = sentence.charAt(i);
						//ÇÑ±Û
						if(java.util.regex.Pattern.matches("[¤¡-¤¾°¡-ÆR]", String.valueOf(chg))){
							//Encryption
							try {
								//Korean -> UniCode
								Uni = KtoU(String.valueOf(chg));
							} catch (Exception e1) {
								e1.printStackTrace();
							}
							//UniCode_16Áø¼ö -> 2Áø¼ö
							HtoB(Uni);
							//HtoB => bin[]¿¡ 16Áø¼ö->2Áø¼ö [0]8ºñÆ® + [1]8ºñÆ®
							for(int j = 0; j < bin.length; j++) {
								tempIP = bin[j];
								Encryption_Kor();
								CT_Kor(j);
							}
							//2Áø¼ö 2°³¸¦ 16Áø¼ö·Î º´ÇÕ
							wordsum = "\\" + "u" + BtoH(chgs[0]) + BtoH(chgs[1]);
							v.tfE[6].setText(v.tfE[6].getText() + UtoK(wordsum));
							
							//Decryption
							//UniCode_16Áø¼ö -> 2Áø¼ö
							HtoB(wordsum);
							//HtoB => bin[]¿¡ 16Áø¼ö->2Áø¼ö [0]8ºñÆ® + [1]8ºñÆ®
							D_HextoKor = "\\" + "u";
							for(int j = 0; j < bin.length; j++) {
								tempIP = bin[j];
								Decryption();
								D_HextoKor += DT_toUni();
							}
							//D_HextoKor : º¹È£È­ ³¡³½ 2Áø¼ö -> 16Áø¼ö 2°³ º´ÇÕ
							System.out.println(D_HextoKor);
							try {
								//UtoK : UniCode -> ÇÑ±Û º¯È¯
								v.tfD[0].setText(v.tfD[0].getText() + UtoK(D_HextoKor));
								} catch (Exception e1) {
								e1.printStackTrace();
								}
							D_HextoKor = "";					
						}
						//¿µ¾î, ¼ýÀÚ, Æ¯¼ö¹®ÀÚ, °ø¹é
						else {
							System.out.print("\n" + (i+1) + "¹øÂ° ¹®ÀÚ : " + "\"" + chg + "\"");
							//¹®ÀÚ -> 2Áø¼ö
							int chgnum = (int)chg;
							tempIP = String.format("%08d", Integer.parseInt(Integer.toBinaryString(chgnum)));

							//Encryption -> Decryption
							Encryption();
							tempIP = takeletter(i);
							Decryption();
							D_Sentence = DT();
							v.tfD[0].setText(v.tfD[0].getText() + D_Sentence);
						}
					}
					System.out.println("\nDecrypt Text : " + v.tfD[0].getText());
					
					System.out.println("------------------------------------");
				}
				//Å°°ª ¹üÀ§ È®ÀÎ_¹üÀ§ ¹þ¾î³²
				else if(Integer.parseInt(v.tfK[0].getText()) < 0 || 
							Integer.parseInt(v.tfK[0].getText()) > 1023){
					System.out.println("Å°°ªÀÌ ¹üÀ§¸¦ ¹þ¾î³µ½À´Ï´Ù!");
					JOptionPane.showMessageDialog(null, "Å°°ªÀÌ ¹üÀ§¸¦ ¹þ¾î³µ½À´Ï´Ù!", "Key_Out_of_Range", JOptionPane.ERROR_MESSAGE);
					v.tfK[0].setText("");
				}
			}
			//ÃÊ±âÈ­
			else if(e.getSource() == v.btn[1]) {
				for(int i = 0; i < v.tfE.length; i++) {
					v.tfE[i].setText("");
					v.tfK[i].setText("");
					v.tfD[i].setText("");
				}
			}
		}
	}
	
	//Key-------------------------------------------------------------------------
	//KeyToBinaryNumber
	public void KtoB(){
		String inum = v.tfK[0].getText();
		String bnum = String.format("%010d", Integer.parseInt(Integer.toBinaryString(Integer.parseInt(inum))));
		v.tfK[1].setText(bnum);
	}
	//P10
	public void P10() {
		String key = v.tfK[1].getText();
		for(int i = 0; i < 10; i++) {
			input[i] = key.charAt(i) - '0';
		}
		for(int i = 0; i < 10; i++) {
			P10[i] = input[P10S[i]-1];
		}
		for(int i = 0; i < 10; i++) {
			v.tfK[2].setText(v.tfK[2].getText() + P10[i]);
		}
		System.out.println("P10 = " + Arrays.toString(P10));
	}
	//1bit Left Shift
	public void leftShift1() {
		int templ, tempr;
		templ = P10[0];
		tempr = P10[5];
		
		for(int i = 0; i < 4; i++) {
			P10[i] = P10[i+1];
			P10[i+5] = P10[i+6];
		}
		P10[4] = templ;
		P10[9] = tempr;
		for(int i = 0; i < 10; i++) {
			v.tfK[3].setText(v.tfK[3].getText() + P10[i]);
		}
		System.out.println("P10(1bit L-Shift) = " + Arrays.toString(P10));
	}
	//K1
	public void K1() {
		String tempK = new String(v.tfK[2].getText());
		for(int i = 0; i < 10; i++) {
			inputK10[i] = tempK.charAt(i) - '0'; 
		}
		for(int i = 0; i < 8; i++) {
			K1[i] = inputK10[P8[i]-1];
		}
		for(int i = 0; i < 8; i++) {
			v.tfK[4].setText(v.tfK[4].getText() + K1[i]);
		}
		System.out.println("K1 = " + Arrays.toString(K1));
	}
	//2bit Left Shift
	public void leftShift2() {
		int templ, tempr;
		templ = P10[0];
		tempr = P10[5];
		
		for(int i = 0; i < 4; i++) {
			P10[i] = P10[i+1];
			P10[i+5] = P10[i+6];
		}
		P10[4] = templ;
		P10[9] = tempr;
		for(int i = 0; i < 4; i++) {
			P10[i] = P10[i+1];
			P10[i+5] = P10[i+6];
		}
		P10[4] = templ;
		P10[9] = tempr;
		for(int i = 0; i < 10; i++) {
			v.tfK[5].setText(v.tfK[5].getText() + P10[i]);
		}
		System.out.println("P10(2bit L-Shift) = " + Arrays.toString(P10));
	}
	//K2
	public void K2() {
		String tempK = new String(v.tfK[5].getText());
		for(int i = 0; i < 10; i++) {
			inputK10[i] = tempK.charAt(i) - '0'; 
		}
		for(int i = 0; i < 8; i++) {
			K2[i] = inputK10[P8[i]-1];
		}
		for(int i = 0; i < 8; i++) {
			v.tfK[6].setText(v.tfK[6].getText() + K2[i]);
		}
		System.out.println("K2 = " + Arrays.toString(K2));
	}
	
	//Encryption-------------------------------------------------------------------------
	public void IP() {
		for(int i = 0; i < 8; i++) {
			inputP[i] = tempIP.charAt(i) - '0';
		}
		v.tfE[1].setText("");
		for(int i = 0; i < 8; i++) {
			inputIP[i] = inputP[IP8[i]-1];
			v.tfE[1].setText(v.tfE[1].getText() + inputIP[i]);
		}
		System.out.println("IP   = " + Arrays.toString(inputIP));
	}
	//fk
	public void fk() {
		//EP
		for(int i = 0; i < 4; i++) {
			IPr[i] = inputIP[i+4];
		}
		for(int i = 0; i < 8; i++) {
			EP[i] = IPr[EP8[i]-1];
		}
		//xor
		for(int i = 0; i < 8; i++) {
			fk[i] = EP[i] ^ K1[i];
		}
		v.tfE[2].setText("");
		for(int i = 0; i < 8; i++) {
			v.tfE[2].setText(v.tfE[2].getText() + fk[i]);
		}
		System.out.println("fk   = " + Arrays.toString(fk));
	}	
	//S-Box
	public void SBox() {
		for(int i = 0; i < 4; i++) {
			fkl[i] = fk[i];
			fkr[i] = fk[i+4];
		}
		S0Row[0] = fkl[0];
		S0Row[1] = fkl[3];
		S0Col[0] = fkl[1];
		S0Col[1] = fkl[2];
		S0RS = String.valueOf(S0Row[0]) + String.valueOf(S0Row[1]);
		S0CS = String.valueOf(S0Col[0]) + String.valueOf(S0Col[1]);
		S0RC = S0[Integer.valueOf(S0RS, 2)][Integer.valueOf(S0CS, 2)];
		bnumS0 = String.format("%02d", Integer.parseInt(Integer.toBinaryString(S0RC)));
		S1Row[0] = fkr[0];
		S1Row[1] = fkr[3];
		S1Col[0] = fkr[1];
		S1Col[1] = fkr[2];
		S1RS = String.valueOf(S1Row[0]) + String.valueOf(S1Row[1]);
		S1CS = String.valueOf(S1Col[0]) + String.valueOf(S1Col[1]);
		S1RC = S1[Integer.valueOf(S1RS, 2)][Integer.valueOf(S1CS, 2)];
		bnumS1 = String.format("%02d", Integer.parseInt(Integer.toBinaryString(S1RC)));
	}
	//P4
	public void P4() {
		P4[0] = bnumS0.charAt(1) - '0';
		P4[1] = bnumS1.charAt(1) - '0';
		P4[2] = bnumS1.charAt(0) - '0';
		P4[3] = bnumS0.charAt(0) - '0';
	}
	//SW
	public void SW() {
		for(int i = 0; i < 4; i++) {
			IPl[i] = inputIP[i];
		}
		for(int i = 0; i < 4; i++) {
			tempSW[i] = IPl[i] ^ P4[i];
		}
		for(int i = 0; i < 4; i++) {
			SW[i] = IPr[i];
		}
		for(int i = 0; i < 4; i++) {
			SW[i+4] = tempSW[i];
		}
		v.tfE[3].setText("");
		for(int i = 0; i < 8; i++) {
			v.tfE[3].setText(v.tfE[3].getText() + SW[i]);
		}
		System.out.println("SW   = " + Arrays.toString(SW));
	}
	//fk2
	public void fk2() {
		//EP
		for(int i = 0; i < 4; i++) {
			IPr[i] = SW[i+4];
		}
		for(int i = 0; i < 8; i++) {
			EP[i] = IPr[EP8[i]-1];
		}
		//xor
		for(int i = 0; i < 8; i++) {
			fk[i] = EP[i] ^ K2[i];
		}
		v.tfE[4].setText("");
		for(int i = 0; i < 8; i++) {
			v.tfE[4].setText(v.tfE[4].getText() + fk[i]);
		}
		System.out.println("fk2  = " + Arrays.toString(fk));
	}
	//IP_1
	public void IP_1() {
		int IP_1l[] = new int [4];
		for(int i = 0; i < 4; i++) {
			IPl[i] = SW[i];
		}
		for(int i = 0; i < 4; i++) {
			IP_1l[i] = IPl[i] ^ P4[i];
		}
		for(int i = 0; i < 4; i++) {
			tempIP_1[i] = IP_1l[i];
		}
		for(int i = 0; i < 4; i++) {
			tempIP_1[i+4] = SW[i+4];
		}
		v.tfE[5].setText("");
		for(int i = 0; i < 8; i++) {
			IP_1[i] = tempIP_1[IP8_1[i]-1];
			v.tfE[5].setText(v.tfE[5].getText() + IP_1[i]);
		}
		System.out.println("IP_1 = " + Arrays.toString(IP_1));
	}
	//Cipher Text
	public void CT() {
		String tempCT = new String();
		for(int i = 0; i < 8; i++) {
			tempCT += IP_1[i];
		}
		int chg = Integer.valueOf(tempCT, 2);
		char word = (char)chg;
		tempCT = String.valueOf(word);
		v.tfE[6].setText(v.tfE[6].getText() + tempCT);
	}
	//Cipher Text_Kor
	public void CT_Kor(int k) {
		String tempCT = new String();

		for(int i = 0; i < 8; i++) {
			tempCT += IP_1[i];
		}
		chgs[k] = tempCT;
	}
	
	//Descryption-------------------------------------------------------------------------
	//take one letter from encrypt sentence
	public String takeletter(int i) {
		String tempIP = new String(v.tfE[6].getText());
		char chg = tempIP.charAt(i);
		int chgnum = (int)chg;
		
		tempIP = String.format("%08d", Integer.parseInt(Integer.toBinaryString(chgnum)));
		
		return tempIP;
	}
	//Set_EncryptionText, D_IP
	public void D_IP() {
		v.tfD[6].setText(v.tfE[6].getText());
		
		for(int i = 0; i < 8; i++) {
			inputP[i] = tempIP.charAt(i) - '0'; 
		}
		v.tfD[5].setText("");
		for(int i = 0; i < 8; i++) {
			inputIP[i] = inputP[IP8[i]-1];
			v.tfD[5].setText(v.tfD[5].getText() + inputIP[i]);
		}
		System.out.println("IP   = " + Arrays.toString(inputIP));
	}
	//D_fk
	public void D_fk() {
		//EP
		for(int i = 0; i < 4; i++) {
			IPr[i] = inputIP[i+4];
		}
		for(int i = 0; i < 8; i++) {
			EP[i] = IPr[EP8[i]-1];
		}
		//xor
		for(int i = 0; i < 8; i++) {
			fk[i] = EP[i] ^ K2[i];
		}
		v.tfD[4].setText("");
		for(int i = 0; i < 8; i++) {
			v.tfD[4].setText(v.tfD[4].getText() + fk[i]);
		}
		System.out.println("fk   = " + Arrays.toString(fk));
	}
	//D_SW
	public void D_SW() {
		for(int i = 0; i < 4; i++) {
			IPl[i] = inputIP[i];
		}
		for(int i = 0; i < 4; i++) {
			tempSW[i] = IPl[i] ^ P4[i];
		}
		for(int i = 0; i < 4; i++) {
			SW[i] = IPr[i];
		}
		for(int i = 0; i < 4; i++) {
			SW[i+4] = tempSW[i];
		}
		v.tfD[3].setText("");
		for(int i = 0; i < 8; i++) {
			v.tfD[3].setText(v.tfD[3].getText() + SW[i]);
		}
		System.out.println("SW   = " + Arrays.toString(SW));
	}
	//D_fk2
	public void D_fk2() {
		//EP
		for(int i = 0; i < 4; i++) {
			IPr[i] = SW[i+4];
		}
		for(int i = 0; i < 8; i++) {
			EP[i] = IPr[EP8[i]-1];
		}
		//xor
		for(int i = 0; i < 8; i++) {
			fk[i] = EP[i] ^ K1[i];
		}
		v.tfD[2].setText("");
		for(int i = 0; i < 8; i++) {
			v.tfD[2].setText(v.tfD[2].getText() + fk[i]);
		}
		System.out.println("fk2  = " + Arrays.toString(fk));
	}
	//D_IP_1
	public void D_IP_1() {
		int IP_1l[] = new int [4];
		
		for(int i = 0; i < 4; i++) {
			IPl[i] = SW[i];
		}
		for(int i = 0; i < 4; i++) {
			IP_1l[i] = IPl[i] ^ P4[i];
		}
		for(int i = 0; i < 4; i++) {
			tempIP_1[i] = IP_1l[i];
		}
		for(int i = 0; i < 4; i++) {
			tempIP_1[i+4] = SW[i+4];
		}
		v.tfD[1].setText("");
		for(int i = 0; i < 8; i++) {
			IP_1[i] = tempIP_1[IP8_1[i]-1];
			v.tfD[1].setText(v.tfD[1].getText() + IP_1[i]);
		}
		System.out.println("IP_1 = " + Arrays.toString(IP_1));
	}
	//Descrypt Text
	public String DT() {
		String tempCT = new String("");
		
		for(int i = 0; i < 8; i++) {
			tempCT += IP_1[i];
		}
		
		int chg = Integer.valueOf(tempCT, 2);
		char word = (char)chg;
		
		tempCT = String.valueOf(word);
		
		return tempCT;
	}
	
	//Descrypt Text_Unicode
	public String DT_toUni() {
		String tempCT = new String();

		for(int i = 0; i < 8; i++) {
			tempCT += IP_1[i];
		}
		String tempCTl = new String();
		String tempCTr = new String();
		for(int i = 0; i < 4; i++) {
			tempCTl += String.valueOf(tempCT.charAt(i));
			tempCTr += String.valueOf(tempCT.charAt(i+4));
		}
		//2Áø¼ö -> 10Áø¼ö
		int bintohexl = Integer.parseInt(tempCTl, 2);
		int bintohexr = Integer.parseInt(tempCTr, 2);
		//10Áø¼ö -> 16Áø¼ö
		String hexStringl = new String();
		String hexStringr = new String();
		hexStringl = Integer.toHexString(bintohexl);
		hexStringr = Integer.toHexString(bintohexr);
		
		String hexString = new String();
		hexString = hexStringl + hexStringr;
		
		return hexString;
	}
	
	//Key
	public void Key() {
		System.out.println("Å°»ý¼º");
		KtoB();
		P10();
		leftShift1();
		K1();
		leftShift2();
		K2();
	}
	//Encryption
	public void Encryption() {
		System.out.println("\n¾ÏÈ£È­");
		IP();
		fk();
		//switch
		SBox();
		P4();
		SW();
		//fk2
		fk2();
		SBox();
		P4();
		IP_1();
		CT();
	}
	//Decryption
	public void Decryption() {
		System.out.println("º¹È£È­");
		D_IP();
		D_fk();
		//switch
		SBox();
		P4();
		D_SW();
		//fk2
		D_fk2();
		SBox();
		P4();
		D_IP_1();
	}
	//Encryption_Kor
	public void Encryption_Kor() {
		System.out.println("\n¾ÏÈ£È­");
		IP();
		fk();
		//switch
		SBox();
		P4();
		SW();
		//fk2
		fk2();
		SBox();
		P4();
		IP_1();
	}
	
	//Kor to Uni
    public static String KtoU(String kor)throws Exception {
        StringBuffer uni = new StringBuffer();

        for (int i = 0; i < kor.length(); i++) {
            if(((int) kor.charAt(i) == 32)) {
                uni.append(" ");
                continue;
            }
            uni.append("\\u");
            uni.append(Integer.toHexString((int) kor.charAt(i)));
        }
        return uni.toString();
    }
    //Uni to Kor
    public String UtoK(String uni){
        StringBuffer kor = new StringBuffer();
        
        for(int i=0; i<uni.length(); i++){
            if(uni.charAt(i) == '\\' &&  uni.charAt(i+1) == 'u'){    
                Character c = (char)Integer.parseInt(uni.substring(i+2, i+6), 16);
                kor.append(c);
                i+=5;
            }else{
                kor.append(uni.charAt(i));
            }
        }
        return kor.toString();
    }
    //16 to 2
    public void HtoB(String hex) {
    	String[] hexa =
    		{ "0000", "0001", "0010", "0011", "0100",
    		"0101", "0110", "0111", "1000", "1001", 
    		"1010", "1011", "1100", "1101", "1110", "1111" };

    	for (int i = 0; i < tempbin.length; i++) {
    		switch (hex.charAt(i+2)) {
    		case '0':
    			tempbin[i] = hexa[0];
    			break;
    		case '1':
    			tempbin[i] = hexa[1];
    			break;
    		case '2':
    			tempbin[i] = hexa[2];
    			break;
    		case '3':
    			tempbin[i] = hexa[3];
    			break;
    		case '4':
    			tempbin[i] = hexa[4];
    			break;
    		case '5':
    			tempbin[i] = hexa[5];
    			break;
    		case '6':
    			tempbin[i] = hexa[6];
    			break;
    		case '7':
    			tempbin[i] = hexa[7];
    			break;
    		case '8':
    			tempbin[i] = hexa[8];
    			break;
    		case '9':
    			tempbin[i] = hexa[9];
    			break;
    		case 'a':
    			tempbin[i] = hexa[10];
    			break;
    		case 'b':
    			tempbin[i] = hexa[11];
    			break;
    		case 'c':
    			tempbin[i] = hexa[12];
    			break;
    		case 'd':
    			tempbin[i] = hexa[13];
    			break;
    		case 'e':
    			tempbin[i] = hexa[14];
    			break;
    		case 'f':
    			tempbin[i] = hexa[15];
    			break;
    		default:
    			System.out.println("wrong value");
    		}
    	}
    	bin[0] = tempbin[0] + tempbin[1];
    	bin[1] = tempbin[2] + tempbin[3];
    }
    //2 to 16
    public String BtoH(String bnum) {
		String tempCT = new String();
		for(int i = 0; i < 8; i++) {
			tempCT += bnum.charAt(i);
		}
		String tempCTl = new String();
		String tempCTr = new String();
		for(int i = 0; i < 4; i++) {
			tempCTl += String.valueOf(tempCT.charAt(i));
			tempCTr += String.valueOf(tempCT.charAt(i+4));
		}
		//2Áø¼ö -> 10Áø¼ö
		int bintohexl = Integer.parseInt(tempCTl, 2);
		int bintohexr = Integer.parseInt(tempCTr, 2);
		//10Áø¼ö -> 16Áø¼ö
		String hexStringl = new String();
		String hexStringr = new String();
		hexStringl = Integer.toHexString(bintohexl);
		hexStringr = Integer.toHexString(bintohexr);
		
		String hexString = new String();
		hexString = hexStringl + hexStringr;
		
		return hexString;
    }
    
	public static void main(String[] args) {
		new SDES();
	}
}
