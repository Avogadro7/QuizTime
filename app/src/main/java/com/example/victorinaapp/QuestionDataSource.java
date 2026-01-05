package com.example.victorinaapp;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class QuestionDataSource {
    public static List<QuizData> getAllQuestions(boolean isShuffled){
        List<QuizData> quizDataList = new ArrayList<>();
        quizDataList.add(new QuizData("O'zbekiston poytaxti?", "Samarqand", "Toshkent", "Sirdaryo", "Buxoro", "Toshkent"));
        quizDataList.add(new QuizData("Turkiya poytaxti?", "Ankara", "Izmir", "Istanbul", "Antalya", "Istanbul"));
        quizDataList.add(new QuizData("Pokiston poytaxti?", "Islomobod", "Bhurban", "Haydarobod", "Peshovar", "Islomobod"));
        quizDataList.add(new QuizData("Misr poytaxti?", "Bilbeis", "Al-Najyla", "Fayyum", "Qohira", "Qohira"));
        quizDataList.add(new QuizData("Hindiston poytaxti?", "Mumbay", "Nyu Dehli", "Pune", "Bangladesh", "Nyu Dehli"));
        quizDataList.add(new QuizData("Afg'oniston poytaxti?", "Hirot", "Talukan", "Qandahor", "Kobul", "Kobul"));
        quizDataList.add(new QuizData("Amerika poytaxti?", "Vashington", "Alabama", "Kansas", "Florida", "Vashington"));

        quizDataList.add(new QuizData("Rossiya poytaxti?", "Moskva", "Sankt-Peterburg", "Kansas", "Kiev", "Moskva"));
        quizDataList.add(new QuizData("Xitoy poytaxti?", "Gon-Kong", "Shanxay", "Tokio", "Pekin", "Pekin"));
        quizDataList.add(new QuizData("Janubiy Koreya poytaxti?", "Ulsan", "Goyang", "Seul", "Tegu", "Seul"));

       if (isShuffled){
           Collections.shuffle(quizDataList);
       }

        return  quizDataList;
    }
}
