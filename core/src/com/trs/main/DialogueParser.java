package com.trs.main;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

public class DialogueParser {
	private int line;
	private String[] file;
	
	public DialogueParser(String filename) {
		line = 0;
		FileHandle fileHandle = Gdx.files.internal(filename);
		String text = fileHandle.readString();
		file = text.split("\\r?\\n");
	}
	
	public DialogueParser(String filename, int line) {
		this.line = line - 1;
		FileHandle fileHandle = Gdx.files.internal(filename);
		String text = fileHandle.readString();
		file = text.split("\\r?\\n");
	}
	
	public Dialogue nextDialog(int selectedAns) {
		Dialogue result = new Dialogue();
		String s = file[line + selectedAns];
		String[] newLine = s.split("#");
		line = Integer.parseInt(newLine[1]);
		
		if(line == -1) {
			return null;
		}
		
		result.question = file[line];
		ArrayList<String> ans = new ArrayList<>();
		for(int i = line + 1; i < file.length; i++) {
			String tempAns = file[i];
			if(tempAns.equals("")) {
				break;
			}
			else {
				ans.add(tempAns);
			}
		}
		result.ans = (String[]) ans.toArray();
		
		return result;
	}
}
