package projeto.primeiro.com.br.primeiroprojeto;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;



public class LoginActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseUser user;

    private EditText loginTextView, senhaTextView;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();

        loginTextView = (EditText) findViewById(R.id.email);
        senhaTextView = (EditText) findViewById(R.id.senha);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        mAuth = FirebaseAuth.getInstance();

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    Log.d("AUTH", "onAuthStateChanged:signed_in:" + user.getUid());
                } else {
                    Log.d("AUTH", "onAuthStateChanged:signed_out - Usuário null");
                }
            }
        };
    }

    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }

    public void btnLoginClick(View view) {
        if(regraEmailESenha()) {
            progressBar.setVisibility(View.VISIBLE);
            Task<AuthResult> authResultTask = mAuth.signInWithEmailAndPassword(loginTextView.getText().toString(), senhaTextView.getText().toString())
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(LoginActivity.this, "Login com sucesso.", Toast.LENGTH_SHORT).show();
                                updateUI(user);
                            } else {
                                Toast.makeText(LoginActivity.this, "Login com falha" + task.getException().toString(), Toast.LENGTH_SHORT).show();
                                updateUI(null);
                            }
                        }
                    });
            regraLogin();
        }

    }

    private void updateUI(FirebaseUser user) {
        this.user = user;
    }

    private void regraLogin(){
        if(user != null) {
            Intent intent = new Intent(this, PerguntasActivity.class);
            startActivity(intent);
            finish();
        } else{
            Toast.makeText(LoginActivity.this, "Falha no Login", Toast.LENGTH_SHORT).show();
        }
    }

    private Boolean regraEmailESenha(){
        Boolean isValido = true;
        if (TextUtils.isEmpty(loginTextView.getText().toString())) {
            loginTextView.setError("Digite o email");
            isValido = false;
        }

        if (TextUtils.isEmpty(senhaTextView.getText().toString())) {
            senhaTextView.setError("Digite a senha");
            isValido = false;
        }

        if (senhaTextView.getText().toString().length() < 6) {
            senhaTextView.setError("Senha não pode ter menos que 6 caracteres.");
            isValido = false;
        }
        return isValido;
    }

    public void btnCriarUsuario(View view) {
        if(regraEmailESenha()) {
            progressBar.setVisibility(View.VISIBLE);
            mAuth.createUserWithEmailAndPassword(loginTextView.getText().toString(), senhaTextView.getText().toString())
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(LoginActivity.this, "Usuário criado com sucesso", Toast.LENGTH_SHORT).show();
                                user = mAuth.getCurrentUser();
                                updateUI(user);
                            } else {
                                Toast.makeText(LoginActivity.this, "Usuário erro na criação", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
            regraLogin();
        }
    }
}
