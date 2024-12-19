    package com.example.tp7;

    import android.app.AlertDialog;
    import android.os.Bundle;
    import android.util.Log;
    import android.view.Menu;
    import android.view.MenuItem;
    import android.view.View;
    import android.widget.EditText;
    import android.widget.Toast;

    import androidx.annotation.NonNull;
    import androidx.appcompat.app.ActionBarDrawerToggle;
    import androidx.appcompat.app.AppCompatActivity;
    import androidx.core.view.GravityCompat;

    import com.example.tp7.databinding.ActivityMainBinding;
    import com.google.android.material.navigation.NavigationView;

    import java.util.ArrayList;
    import java.util.Collections;
    import java.util.List;

    public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

        ActivityMainBinding bind;
        boolean enseig = false;
        boolean about = false;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            bind = ActivityMainBinding.inflate(getLayoutInflater());
            setContentView(bind.getRoot());
            setTitle("My App");

            setSupportActionBar(bind.toolbar);
            bind.navView.setNavigationItemSelectedListener(this);

            ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                    this, bind.drawerLayout, bind.toolbar, R.string.open_drawer, R.string.close_drawer);
            bind.drawerLayout.addDrawerListener(toggle);
            toggle.syncState();

            if (savedInstanceState == null) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, new AboutFragment()).commit();
                bind.navView.setCheckedItem(R.id.nav_home);
            }
        }

        @Override
        public boolean onCreateOptionsMenu(Menu menu) {
            getMenuInflater().inflate(R.menu.menu_main_about, menu);
            return super.onCreateOptionsMenu(menu);
        }

        @Override
        public boolean onPrepareOptionsMenu(Menu menu) {
            menu.clear();
            if (enseig) {
                getMenuInflater().inflate(R.menu.menu_main_enseig, menu);
            } else if (about) {
                getMenuInflater().inflate(R.menu.menu_main_about, menu);
            }
            return super.onPrepareOptionsMenu(menu);
        }

        private void showAddTeacherDialog() {
            View dialogView = getLayoutInflater().inflate(R.layout.dialog_add_teacher, null);
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Add New Teacher");
            builder.setView(dialogView);

            EditText teacherNameInput = dialogView.findViewById(R.id.teacherNameInput);
            EditText teacherEmailInput = dialogView.findViewById(R.id.teacherEmailInput);

            builder.setPositiveButton("Add", (dialog, which) -> {
                String name = teacherNameInput.getText().toString().trim();
                String email = teacherEmailInput.getText().toString().trim();

                if (!name.isEmpty() && !email.isEmpty()) {
                    Teacher newTeacher = new Teacher(name, email);
                    addTeacherToHomeFragment(newTeacher);
                    Toast.makeText(this, "New teacher added", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "Please enter both name and email", Toast.LENGTH_SHORT).show();
                }
            });

            builder.setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss());
            builder.create().show();
        }

        private void addTeacherToHomeFragment(Teacher teacher) {
            homeFragment fragment = (homeFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_container);
            if (fragment != null) {
                homeFragment.addTeacher(teacher);
            }
        }

        @Override
        public boolean onOptionsItemSelected(@NonNull MenuItem item) {
            int id = item.getItemId();
            if (id == R.id.a_z) {
                homeFragment fragment = (homeFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_container);
                if (fragment != null && fragment.mAdapter != null) {
                    fragment.mAdapter.sortTeachers(true);
                }
                return true;
            } else if (id == R.id.z_a) {
                homeFragment fragment = (homeFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_container);
                if (fragment != null && fragment.mAdapter != null) {
                    fragment.mAdapter.sortTeachers(false);
                }
                return true;
            } else if (id == R.id.add) {
                showAddTeacherDialog();
                return true;
            } else {
                return super.onOptionsItemSelected(item);
            }
        }

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            int id = item.getItemId();
            if (id == R.id.nav_home) {
                enseig = true;
                about = false;
                bind.toolbar.setTitle("Enseignants");
                invalidateOptionsMenu();
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, new homeFragment()).commit();
            } else if (id == R.id.nav_about) {
                enseig = false;
                about = true;
                bind.toolbar.setTitle("About");
                invalidateOptionsMenu();
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, new AboutFragment()).commit();
            } else if (id == R.id.nav_logout) {
                Log.i("tag", "exit");
                Toast.makeText(this, "Exit", Toast.LENGTH_LONG).show();
                finish();
            }
            bind.drawerLayout.closeDrawer(GravityCompat.START);
            return true;
        }
    }

