package com.testtask.notesservice;
import com.testtask.notesservice.accessdatamysql.Entry;
import com.testtask.notesservice.accessdatamysql.EntryRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.thymeleaf.util.StringUtils;

@Controller
public class MainController {

    @Autowired
    private EntryRepo entryRepo;

    @GetMapping("/main")
    public String main(Model model) {
        model.addAttribute("entries", entryRepo.findAll());
        return "main";
    }

    @GetMapping("/entry")
    public String entry(@RequestParam(name="id") Integer id, Model model) {
        model.addAttribute("entry", entryRepo.getById(id));
        return "entry";
    }

    @PostMapping("/add")
    public String add(String title, String content, Model model) {
        if (StringUtils.isEmptyOrWhitespace(content)) {
            content = "пустая заметка";
        }
        if (StringUtils.isEmptyOrWhitespace(title)) {
            title = "";
        }
        Entry e = new Entry(title, content);
        entryRepo.save(e);
        model.addAttribute("entries", entryRepo.findAll());
        return "redirect:main";
    }

    @PostMapping("/update")
    public String update(@RequestParam(name="id") Integer id, String title, String content, Model model) {
        if (StringUtils.isEmptyOrWhitespace(content)) {
            content = "пустая заметка";
        }
        if (StringUtils.isEmptyOrWhitespace(title)) {
            title = "";
        }
        Entry e = entryRepo.getById(id);
        e.setContent(content);
        e.setTitle(title);

        entryRepo.save(e);
        model.addAttribute("entries", entryRepo.findAll());
        return "redirect:main";
    }

    @GetMapping("/new")
    public String newEntry() {
        return "new";
    }

    @GetMapping("/search")
    public String search(String str, Model model) {
        model.addAttribute("entries", entryRepo.findByTitleContainingOrContentContaining(str, str));
        return "main";
    }

    @PostMapping("/delete")
    public String delete(@RequestParam(name="id")Integer id, Model model) {
        entryRepo.delete(entryRepo.getById(id));
        return "redirect:main";
    }

}
