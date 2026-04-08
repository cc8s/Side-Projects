package com.example.mygame;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ResultActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_result);

        TextView tvResult = findViewById(R.id.tv_result);
        Button btnRestart = findViewById(R.id.btn_restart);

        // Intent에서 데이터 가져오기
        int score = getIntent().getIntExtra("score", 1);
        boolean success = getIntent().getBooleanExtra("success", false);

        // 결과 텍스트 설정
        if (success) {
            tvResult.setText("🎉 성공!\n최종 점수: " + (score -1));
        } else {
            tvResult.setText("😭 실패!! ㅠㅠ\n최종 점수: " + (score - 1));
        }

        // 다시하기 버튼 클릭 리스너 설정
        btnRestart.setOnClickListener(v -> {
            Intent intent = new Intent(ResultActivity.this, MenuActivity.class); // MainActivity 대신 메뉴로 이동
            startActivity(intent);
            finish();
        });
    }
}
