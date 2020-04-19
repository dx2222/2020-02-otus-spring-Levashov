package ru.otus.spring.homework_04.shell;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.Availability;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellMethodAvailability;
import ru.otus.spring.homework_04.domain.TxtConst;
import ru.otus.spring.homework_04.service.ExaminationService;

@ShellComponent
@RequiredArgsConstructor
public class ExecuteShell {

    private int isName;
    private int isAnswer;
    private final ExaminationService exam;

    @ShellMethod(value = "Start Testing", key = {"starttesting", "start", "s"})
    public String startTesting() {
        isName   = 0;
        isAnswer = 0;
        return  "Test started!";
    }

    @ShellMethod(value = "Ask student information", key = {"name","n"})
    public String askName() {
        exam.askName();
        isName = 1;
        return  "Can answer questions!";
    }

    @ShellMethod(value = "Ask questions", key = {"questions", "q"})
    @ShellMethodAvailability(value = "isAskQuestionAvailable")
    public String askQuestions() {
        exam.askQuestions();
        isAnswer = 1;
        return  "Test is over!";
    }

    private Availability isAskQuestionAvailable() {
        return isName == 0? Availability.unavailable(TxtConst.EXAM_ERRNAME): Availability.available();
    }

    @ShellMethod(value = "Print result", key = {"result", "r"})
    @ShellMethodAvailability(value = "isPrintResultAvailable")
    public void printResult() {
        exam.printResult();
    }

    private Availability isPrintResultAvailable() {
        if (isName == 0){
            return Availability.unavailable(TxtConst.EXAM_ERRNAME);
        } else  if (isAnswer == 0) {
            return  Availability.unavailable(TxtConst.EXAM_ERRQUESTION);
        } else {
            return Availability.available();
        }
    }
}
