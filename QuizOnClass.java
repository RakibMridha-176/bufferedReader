package FileHandlaing;


import java.io.*;
import java.util.*;


public class QuizOnClass {
    public static void main(String[] args) {
        String inputFilePath = "Students.txt";
        String outputFilePath = "StudentId_results.txt";
        List<Student> students = new ArrayList<>();

        try {
            FileReader fileReader = new FileReader(new File(inputFilePath));
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line;

            while ((line = bufferedReader.readLine()) != null) {
                String[] parts = line.split(",");
                String name = parts[0].trim();
                int score = Integer.parseInt(parts[1].trim());
                students.add(new Student(name, score));
            }
            bufferedReader.close();
        } catch (IOException exception) {
            exception.printStackTrace();
        }

        double averageScore = AverageScore(students);

        Map<String, Integer> topScorers = findTopScorers(students);

        try {
            FileWriter fileWriter = new FileWriter(new File(outputFilePath));
            fileWriter.write(String.format("Average Score: %.2f%n", averageScore));
            fileWriter.write("Top Scorer(s):\n");
            for (Map.Entry<String, Integer> entry : topScorers.entrySet()) {
                fileWriter.write(String.format("%s - %d%n", entry.getKey(), entry.getValue()));
            }
            fileWriter.close();
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    private static double AverageScore(List<Student> students) {
        int totalScore = 0;
        for (Student student : students) {
            totalScore += student.getScore();
        }
        return (double) totalScore / students.size();
    }

    private static Map<String, Integer> findTopScorers(List<Student> students) {
        Map<String, Integer> topScorers = new HashMap<>();
        int highestScore = Integer.MIN_VALUE;

        for (Student student : students) {
            if (student.getScore() > highestScore) {
                highestScore = student.getScore();
                topScorers.clear();
                topScorers.put(student.getName(), student.getScore());
            } else if (student.getScore() == highestScore) {
                topScorers.put(student.getName(), student.getScore());
            }
        }
        return topScorers;
    }
}

