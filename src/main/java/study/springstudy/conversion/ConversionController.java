package study.springstudy.conversion;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/conversion")
public class ConversionController {

    @GetMapping("/number")
    public String convertNumber(@RequestParam Integer value) {
        return "value = " + value + ", type = " + value.getClass().getSimpleName();
    }

    @GetMapping("/date")
    public String convertDat(@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date) {
        return "date = " + date + ", tomorrow = " + date.plusDays(1);
    }

    @GetMapping("/members/{memberId}")
    public String convertMemberId(
            @PathVariable
            MemberId memberId
    ) {
        return "memberId = " + memberId.value() + ", type = " + memberId.getClass().getSimpleName();
    }
}
