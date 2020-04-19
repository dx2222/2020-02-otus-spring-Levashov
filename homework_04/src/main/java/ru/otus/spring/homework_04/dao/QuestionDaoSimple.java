package ru.otus.spring.homework_04.dao;

import java.io.BufferedReader;

import org.springframework.stereotype.Service;
import ru.otus.spring.homework_04.domain.Question;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import ru.otus.spring.homework_04.config.ApplicationSettings;

@Service
public class QuestionDaoSimple implements QuestionDao {

    private ArrayList<Question> questions;

    public QuestionDaoSimple(ApplicationSettings settings) {
        try {
            InputStream inputStream = getClass().getClassLoader().getResourceAsStream(settings.getFileName()+"_"+settings.getLocale().toString()+".csv");
            if (inputStream != null) {
                this.questions = new ArrayList<Question>();
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                String line;
                int index = 0;
                while ((line = reader.readLine()) != null) {
                    index++;
                    String[] listString = line.split(";");
                    try {
                        questions.add(new Question(listString[0], listString[1].toLowerCase().split("#"), listString[2]));
                    } catch (Exception e) {
                        System.out.println("Ошибка загрузки вопроса №" + index + " "+ e + " "+line);
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("Error loading file "+settings.getFileName());
        }
    }

    public Question findByNumber(int number) {
        return questions.get(number);
    }

    public int getCountQuestion(){
        return questions.size();
    }
}
