package com.belak.timetable.component;

import com.belak.timetable.professor.entity.ProfessorEntity;
import com.belak.timetable.professor.repository.ProfessorRepository;
import com.belak.timetable.student.entity.StudentEntity;
import com.belak.timetable.student.repository.StudentRepository;
import com.belak.timetable.user.entity.UserEntity;
import com.belak.timetable.user.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    private final UserDetailsService userDetailsService ;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final ProfessorRepository professorRepository ;
    private final StudentRepository studentRepository ;
    public DataInitializer(UserRepository userRepository,
                           PasswordEncoder passwordEncoder ,
                           ProfessorRepository professorRepository,
                           UserDetailsService userDetailsService , StudentRepository studentRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.professorRepository=professorRepository ;
        this.userDetailsService=userDetailsService ;
        this.studentRepository=studentRepository ;
    }

    @Override
    public void run(String... args) throws Exception {
        // Vérifie si un utilisateur avec userId "admin" existe
        if (userRepository.findByUserId("BJ240005").isEmpty()) {
            // Crée un nouvel admin
            UserEntity admin = new UserEntity();
            admin.setUsername("BJ240005"); // identifiant admin
            admin.setPassword(passwordEncoder.encode("BJ240005")); // mot de passe hashé
            admin.setRole(UserEntity.Role.valueOf("ADMIN")); // rôle
            admin.setEmail("akabeb.com@gmail.com");
            admin.setPrenom("Yelongnise Kaleb Renaud Gerald");
            admin.setNom("AKAKPO");
            // Sauvegarde dans la base
            userRepository.save(admin);
            System.out.println("Admin créé avec succès !");
        }
        if (userRepository.findByUserId("BJ340005").isEmpty()) {

            ProfessorEntity professor = new ProfessorEntity();

            // Champs hérités de UserEntity
            professor.setUsername("BJ340005");
            professor.setPassword(passwordEncoder.encode("BJ340005"));
            professor.setRole(UserEntity.Role.PROFESSOR);
            professor.setEmail("akakpokaleb@gmail.com");
            professor.setPrenom("Abdelhakim");
            professor.setNom("Bouajila");

            // Champs propres à ProfessorEntity
            professor.setGrade("MC");
            professor.setSpecialite("Géologie");
            professor.setSchoolStatus("Permanent");

            professorRepository.save(professor);

            System.out.println("Professeur créé avec succès !");
        }
        if (userRepository.findByUserId("BJ440005").isEmpty()) {

            StudentEntity student = new StudentEntity();

            // Champs hérités de UserEntity
            student.setUsername("BJ440005");
            student.setPassword(passwordEncoder.encode("BJ440005"));
            student.setRole(UserEntity.Role.STUDENT);
            student.setEmail("akakpo09@gmail.com");
            student.setPrenom("AKAKPO");
            student.setNom("Kaleb");

            // Champs propres à ProfessorEntity
            student.setNiveau(2);
            student.setGroup("A");
            student.setFiliere("LGLSI");
            student.setDepartment("Informatique");
             studentRepository.save(student);
            System.out.println("Etudiant créé avec succès !");
        }
    }

}
