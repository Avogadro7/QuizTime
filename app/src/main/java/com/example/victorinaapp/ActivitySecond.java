package com.example.victorinaapp;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.victorinaapp.databinding.ActivitySecondBinding;
import com.example.victorinaapp.databinding.CustomDialogBinding;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ActivitySecond extends AppCompatActivity {
    ActivitySecondBinding binding;
    MediaPlayer mediaPlayer;

    List<QuizData> quizDataList;
    int counterTrue = 0;
    int score = 0;
    int scoreMinus = 0;
    int index = 0;
    boolean shuffleMode = true;
    boolean isMute = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivitySecondBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        binding.answerBtn.setText("Confirm");
        mediaPlayer = MediaPlayer.create(this, R.raw.music);

        binding.muteIv.setOnClickListener(v -> {
                    isMute = true;
                    mediaPlayer.start();

                }
        );

        binding.imuteIv.setOnClickListener(v -> {
            if (isMute) {
                mediaPlayer.pause();
            }

        });


        binding.exit.setOnClickListener(v -> {

            mediaPlayer.pause();
            onBackPressed();
        });


        quizDataList = new ArrayList<>();

        loadQuizData();
        setActiveQuestion(index);

        binding.answerBtn.setOnClickListener(v -> {

            int checkedRadioBtn = binding.variantrg.getCheckedRadioButtonId();
            if (checkedRadioBtn == -1) {
                Toast.makeText(this,
                        "select an option!", Toast.LENGTH_SHORT).show();
            } else {
                RadioButton selectedRadioBtn = findViewById(checkedRadioBtn);
                String radioBtnText = selectedRadioBtn.getText().toString();

                if (radioBtnText.equals(quizDataList.get(index).getAnswer())) {
                    binding.checknumber1Tv.setText(String.valueOf(++score));
                    Toast.makeText(this, "✅", Toast.LENGTH_SHORT).show();
                    ++counterTrue;
                } else {
                    binding.checknumberTv.setText(String.valueOf(++scoreMinus));
                    Toast.makeText(this, "❌", Toast.LENGTH_SHORT).show();
                }


                if (index >= quizDataList.size() - 1) {
//                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
//                    builder.setTitle("O'yin tugadi!");
//                    builder.setMessage("Yakuniy natija: " + (index + 1) + "ta savoldan \n" + score + "ta to'g'ri ✅\n " + scoreMinus + "ta xato ❌");
//                    builder.setPositiveButton("Qayta boshlash", (dialog, which) -> {
//                        restartGame();
//                    });
//                    builder.setNegativeButton("O'yindan chiqish", (dialog, which) -> {
//                        mediaPlayer.pause();
//                        finish();
//                    });
//
//                    Dialog dialog = builder.create();
//                    dialog.setCancelable(false);
//                    dialog.show();
                    showDialog();
                } else {
                    binding.variantrg.clearCheck();
                    setActiveQuestion(++index);

                }


            }


        });


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        // Toast ko'rsatish

        if (isMute) {
            mediaPlayer.pause();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        // Foydalanuvchi boshqa aktivlikka o‘tganida chaqiriladi
        //  Log.d("MainActivity", "onPause: Aktivlik to'xtatildi");
        if (isMute) {
            mediaPlayer.pause();
        }
        // Bu yerda resurslarni bo'shatish yoki ma'lumotlarni saqlash mumkin
    }

    void loadQuizData() {
        quizDataList.addAll(QuestionDataSource.getAllQuestions(shuffleMode));
    }

    void setActiveQuestion(int questionIndex) {
        binding.savol.setText(quizDataList.get(questionIndex).getQuestion());
        binding.variant1.setText(quizDataList.get(questionIndex).getOption1());
        binding.variant2.setText(quizDataList.get(questionIndex).getOption2());
        binding.variant3.setText(quizDataList.get(questionIndex).getOption3());
        binding.variant4.setText(quizDataList.get(questionIndex).getOption4());
        binding.questionTv.setText(String.valueOf(questionIndex + 1));

    }

    void restartGame() {
        counterTrue=0;
        index = 0;
        score = 0;
        scoreMinus = 0;
        if (shuffleMode) {
            Collections.shuffle(quizDataList);
        }
        setActiveQuestion(index);
        binding.checknumber1Tv.setText("0");
        binding.checknumberTv.setText("0");
        binding.questionTv.setText(String.valueOf(index + 1));
        binding.variantrg.clearCheck();

    }

    @SuppressLint("SetTextI18n")
    void showDialog() {
        CustomDialogBinding customDialogBinding = CustomDialogBinding.inflate(LayoutInflater.from(getBaseContext()));
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(customDialogBinding.getRoot());
builder.setCancelable(false);
        Dialog dialog = builder.create();
        dialog.show();

        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        customDialogBinding.customTitle.setText("Game Over");

        customDialogBinding.customExitGame.setOnClickListener(v -> {
            finish();
        });

        customDialogBinding.customRestartGame.setOnClickListener(v -> {
            restartGame();

            dialog.dismiss();
        });


        customDialogBinding.customScoreIcon.setImageResource(getIconByScore(counterTrue));
        customDialogBinding.customDescription.setText("You have been "+ counterTrue*10 +"%");
        customDialogBinding.customTitle.setBackgroundColor(getTitleByScore(counterTrue));
        customDialogBinding.ratingScore.setRating(getStarByScore(counterTrue));
    }

    int getIconByScore(int score) {
        if (score > 7) {
            return R.drawable.icon_cool;
        } else if (score >= 3) {
            return R.drawable.icon_middle;
        } else if (score >=1){
            return R.drawable.icon_sad;
        }else return R.drawable.icon_zero;
    }

    int getTitleByScore(int score){
        if (score > 7) {
            return Color.parseColor("#3b8132");
        } else if (score >= 3) {
            return Color.parseColor("#FEE135");
        } else if (score >=1){
            return Color.parseColor("#d1001f");
        }else return Color.parseColor("#c30010");
    }

    int getStarByScore(int counterTrue){
        if (score > 7) {
            return 3;
        } else if (score >= 3) {
            return 2;
        } else if (score >=1){
            return 1;
        }else return 0;
    }
}


