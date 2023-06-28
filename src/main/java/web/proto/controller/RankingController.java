package web.proto.controller;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import web.proto.model.Project;
import web.proto.service.ProjectService;

@Controller
@RequestMapping("/ranking")
public class RankingController {

    @Autowired
    private ProjectService projectService;
    
    @GetMapping
    public String showRanking(Model model) {
        List<Project> projects = projectService.read();
        Map<Project, Integer> scores = new HashMap<>();

        for (Project project : projects) {
            int totalScore = projectService.getProjectScore(project);
            scores.put(project, totalScore);
        }

        List<Map.Entry<Project, Integer>> sortedScores = scores.entrySet().stream()
            .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
            .collect(Collectors.toList());

        model.addAttribute("scores", sortedScores);
        return "ranking";
    }

}
