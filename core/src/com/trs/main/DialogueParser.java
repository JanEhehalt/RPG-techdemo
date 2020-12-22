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
	
	public Dialogue firstDialogue() {
		Dialogue result = new Dialogue();
		
		result.question = file[line];
		ArrayList<String> ans = new ArrayList<>();
		for(int i = line + 1; i < file.length; i++) {
			String tempAns = file[i];
			if(tempAns.equals("")) {
				break;
			}
			else {
                            String[] split = tempAns.split("#");
                            ans.add(split[0]);   
			}
		}
		String[] answers = new String[ans.size()];
		for(int i = 0; i < answers.length; i++) {
			answers[i] = ans.get(i);
		}
		result.ans = answers;
		
		return result;
	}
	
	public Dialogue nextDialogue(int selectedAns) {
		Dialogue result = new Dialogue();
		String s = file[line + selectedAns];
		String[] newLine = s.split("#");
		line = Integer.parseInt(newLine[1]) - 1;
		
		if(line < 0) {
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
                            String[] split = tempAns.split("#");
                            ans.add(split[0]);    
			}
		}
                
                String[] answer = new String[ans.size()];
                for(int i = 0; i < ans.size(); i++){
                    answer[i] = ans.get(i);
                }
		result.ans = answer;
                
		return result;
	}
}
