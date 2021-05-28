package propensi.d06.sihedes.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import propensi.d06.sihedes.model.ArtikelModel;
import propensi.d06.sihedes.model.UserModel;
import propensi.d06.sihedes.service.ArticleService;
import propensi.d06.sihedes.service.UserService;

import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/knowledgebase")
public class ArticleController {
    @Autowired
    ArticleService articleService;

    @Autowired
    UserService userService;

    @RequestMapping("/")
    public String knowledgeBase(Model model){
        List<ArtikelModel> articles = articleService.findAll();
        model.addAttribute("articles", articles);
        return "knowledge-base";
    }

    @GetMapping("/add")
    public String addArticle(Model model){
        model.addAttribute("article", new ArtikelModel());
        UserModel user = userService.getUserbyUsername(SecurityContextHolder.getContext().getAuthentication().getName());

        model.addAttribute("user", user);

        return "add-article";
    }

    @PostMapping("/add")
    public String addArticlePost(Model model,
                                 @ModelAttribute ArtikelModel article,
                                 RedirectAttributes redir){

        article.setCreated_date(new Date());
        articleService.addArticle(article);



        return "redirect:/knowledgebase/";
    }

    @GetMapping("/{id_article}")
    public String detailArticle(Model model,
                                @PathVariable(value = "id_article") Long id_article){
        ArtikelModel article = articleService.findArticleById(id_article);
        model.addAttribute("article", article);
        return "detail-article";
    }



}
